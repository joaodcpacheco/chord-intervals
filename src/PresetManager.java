import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PresetManager
{
	public static File presetsFile;

	public static final String PRESETS_FILENAME = "presets.txt";

	public static List<Instrument> presets;
	
	public static void init()
	{
		presets = new ArrayList<Instrument>();
		
		try
		{
			if (presetsFile == null)
				presetsFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().resolve(PRESETS_FILENAME));
			
			boolean fileExists = presetsFile.exists();

			if (!fileExists)
				presetsFile.createNewFile();
			
			if (fileExists)
			{
				Scanner rd = new Scanner(new FileReader(presetsFile));

				while (rd.hasNextLine())
				{
					String specs = rd.nextLine();

					Instrument model = getPreset(specs);
					if (model != null)
						presets.add(model);
				}

				rd.close();
			}
			else
			{
				FileWriter wr = new FileWriter(presetsFile);

				for (DefaultPreset defaultPreset : DefaultPreset.values())
				{
					Instrument preset = getPreset(defaultPreset.specs);
					if (preset != null)
						presets.add(preset);

					wr.write(defaultPreset.specs + "\n");
				}

				wr.flush();
				wr.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Instrument[] values()
	{
		return presets.stream().toArray(Instrument[]::new);
	}

	public static Instrument getPreset(String specs)
	{
		Instrument preset = new Instrument();

		// if an instrument was selected
		if (specs != null)
		{
			// parse specs into instrument
			try
			{
				String[] s = specs.split(";");

				// if specs has strings, frets and tuning
				if (s.length >= 4)
				{
					// parse display name, strings and frets
					preset.toString = s[0];
					preset.strings = Integer.parseInt(s[1]);
					preset.frets = Integer.parseInt(s[2]) + 1;

					// if strings and frets are positive integers
					if (preset.strings >= 0 && preset.frets >= 0)
					{
						// parse tuning
						preset.tuning = Arrays.stream(s[3].split("-")).mapToInt(Integer::parseInt).toArray();

						// if tuning contains as many notes as there are strings
						// and all notes are positive integers
						if (preset.tuning.length == preset.strings && Arrays.stream(preset.tuning).allMatch(x -> x >= 0))
						{
							// if fret marking specs are present
							if (s.length >= 5)
							{
								// parse fret marks
								preset.marks = parseMarks(s[4], preset.frets);
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		// if parsing failed or specs was null
		if (preset.tuning == null)
			return getDefault();
		else
			return preset;
	}

	public static Instrument getDefault()
	{
		Instrument preset = new Instrument();
		Instrument defaultPreset = !presets.isEmpty() ? presets.get(0) : getPreset(DefaultPreset.getDefault().specs);

		preset.toString = defaultPreset.toString;
		preset.strings = defaultPreset.strings;
		preset.frets = defaultPreset.frets;
		preset.tuning = defaultPreset.tuning;
		preset.marks = defaultPreset.marks;

		return preset;
	}

	public static List<Instrument> deletePreset(String specs)
	{
		List<Instrument> deleted = new ArrayList<Instrument>();

		try
		{
			// clear file just in case
			FileWriter wr = new FileWriter(presetsFile);
			wr.write("");
			wr.flush();
			wr.close();

			wr = new FileWriter(presetsFile);

			for (Iterator<Instrument> iterator = presets.iterator(); iterator.hasNext();)
			{
				Instrument preset = iterator.next();
				String specs_ = preset.getSpecs();

				if (specs_.equals(specs))
				{
					deleted.add(preset);
					iterator.remove();
				}
				else
					wr.write(specs_ + "\n");
			}

			wr.flush();
			wr.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return deleted;
	}

	public static void savePreset(String specs)
	{
		try
		{
			FileWriter wr = new FileWriter(presetsFile, true);

			wr.append(specs + "\n");

			wr.flush();
			wr.close();

			presets.add(getPreset(specs));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String getSpecs(Instrument preset)
	{
		return String.format("%s;%d;%d;%s;%s", 
				preset.toString,
				preset.strings,
				preset.frets,
				Arrays.stream(preset.tuning).boxed().map(i -> i.toString()).collect(Collectors.joining("-")),
				preset.marks.stream().map(i -> i.toString()).collect(Collectors.joining("-"))
				);
	}

	public static List<Integer> parseMarks(String s, int frets)
	{
		List<Integer> marks = new ArrayList<Integer>();

		try
		{
			int[] marks_ = Arrays.stream(s.split("-")).mapToInt(Integer::parseInt).toArray();

			// if all fret marks are positive integers below
			// the number of frets
			if (Arrays.stream(marks_).allMatch(x -> (x >= 0 && x < frets + 1)))
			{
				marks = Arrays.stream(marks_).boxed().distinct().sorted().collect(Collectors.toList());
			}
		}
		catch (NumberFormatException e)
		{

		}

		return marks;
	}
}

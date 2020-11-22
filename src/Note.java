
// the 12 notes of the 12-TET system

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum Note
{
	C(0), C$(1), D(2), D$(3), E(4), F(5), F$(6), G(7), G$(8), A(9), A$(10), B(11);

	public static final List<Note> values = new ArrayList<Note>(Arrays.asList(Note.values()));
	public static final int SIZE = 12;

	// A4 frequency and index
	public static final double REFERENCE_FREQUENCY = 440d;
	public static final int REFERENCE_INDEX = 57;

	public final int phase;

	Note(int phase)
	{
		this.phase = phase;
	}

	/*
	 	parses a String of note names into a List of Note objects, with
	 	no order or duplicates
	 */
	public static List<Note> parseNotes(String s0)
	{
		Set<Note> ret = new HashSet<Note>();
		String s = s0.replace('#', '$');

		int i = 0;
		for (;;)
		{
			if (i == s.length())
				break;
			switch (s.charAt(i))
			{
				case 'A':
					if (s.charAt(i + 1) == '$')
					{
						ret.add(A$);
						i++;
					}
					else
						ret.add(A);
					break;
				case 'B':
					ret.add(B);
					break;
				case 'C':
					if (s.charAt(i + 1) == '$')
					{
						ret.add(C$);
						i++;
					}
					else
						ret.add(C);
					break;
				case 'D':
					if (s.charAt(i + 1) == '$')
					{
						ret.add(D$);
						i++;
					}
					else
						ret.add(D);
					break;
				case 'E':
					ret.add(E);
					break;
				case 'F':
					if (s.charAt(i + 1) == '$')
					{
						ret.add(F$);
						i++;
					}
					else
						ret.add(F);
					break;
				case 'G':
					if (s.charAt(i + 1) == '$')
					{
						ret.add(G$);
						i++;
					}
					else
						ret.add(G);
					break;
				default:
					break;
			}
			i++;
		}
		return new ArrayList<Note>(ret);
	}

	// static util methods

	public static double frequencyFromIndex(int index)
	{
		return REFERENCE_FREQUENCY * Math.pow(2, (index - REFERENCE_INDEX) / 12.0d);
	}

	public static Note noteFromIndex(int index)
	{
		return values.get(index % SIZE);
	}

	public static int indexFromNote(Note note, int octave)
	{
		return note.phase + (SIZE * octave);
	}

	public static int octaveFromIndex(int index)
	{
		return index / 12;
	}

	@Override
	public String toString()
	{
		return name().replace('$', '#');
	}
}
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Intervals
{
	/*
	   returns, for every note N in notes, the intervals of every other note relative to N

	   e.g. the result of analyse for the notes {C, E, G} is a String array
	   		containing {"C: R M3 P5", "E: R m3 m6", "G: R P4 M6"}
	 */
	public static String[] analyse(List<Note> notes)
	{
		String[] ret = new String[notes.size()];
		int i = 0;
		for (Note a : notes)
		{
			ret[i] = a.toString() + ": ";
			List<Integer> l = new ArrayList<Integer>();
			for (Note b : notes)
			{
				int x = a.phase;
				int y = b.phase;
				if (y >= x)
					l.add(y - x);
				else
					l.add(y - x + 12);
			}
			Collections.sort(l);
			for (int x : l)
			{
				ret[i] += Interval.values()[x] + " ";
			}
			i++;
		}
		return ret;
	}

	public static String[] analyse(String s)
	{
		return analyse(Note.parseNotes(s));
	}

	// note intervals
	public enum Interval
	{
		R,			// root
		m2,			// minor second
		M2,			// major second
		m3,			// minor third
		M3,			// major third
		P4,			// perfect fourth
		TT,			// tritone or diminished fifth
		P5,			// perfect fifth
		m6,			// minor sixth
		M6,			// major sixth
		m7,			// minor seventh
		M7;			// major seventh
	}
}
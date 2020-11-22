import java.util.ArrayList;
import java.util.List;

public class Instrument
{
	String toString; // display name

	int strings, frets; // frets includes fret 0 (open string)
	int[] tuning; // backwards
	List<Integer> marks;

	public Instrument()
	{
		toString = "";

		strings = 0;
		frets = 0;
		tuning = null;
		marks = new ArrayList<Integer>();
	}

	public String getSpecs()
	{
		return PresetManager.getSpecs(this);
	}

	@Override
	public String toString()
	{
		return toString;
	}
}

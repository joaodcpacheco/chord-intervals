import java.awt.EventQueue;
import java.io.File;

public class Main
{
	// the instrument model is specified as a command line argument
	public static void main(String[] args)
	{
		EventQueue.invokeLater(() ->
		{
			try
			{
				if (args.length > 0)
					PresetManager.presetsFile = new File(args[0], PresetManager.PRESETS_FILENAME);

				PresetManager.init();
				new GUI();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
}
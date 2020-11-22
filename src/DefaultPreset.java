/*
	several instruments with information regarding
	number of strings, number of frets, open tuning notes and fret marks
 */

public enum DefaultPreset
{
	// E4 B3 G3 D3 A2 E2
	GUITAR_E_STANDARD("Standard tuning;6;21;52-47-43-38-33-28" + Const.GMARKS),

	// E4 B3 G3 D3 A2 D2
	GUITAR_DROP_D("Drop D;6;21;52-47-43-38-33-26" + Const.GMARKS),

	// E4 B3 G3 D3 A2 B1
	GUITAR_E_STANDARD_B_BASS("E standard / B bass;6;21;52-47-43-38-33-23" + Const.GMARKS),

	// D#4 A#3 F#3 C#3 G#2 D#2
	GUITAR_D$_STANDARD("D# standard;6;21;51-46-42-37-32-27" + Const.GMARKS),

	// D#4 A#3 F#3 C#3 G#2 C#2
	GUITAR_DROP_C$("Drop C#;6;21;51-46-42-37-32-25" + Const.GMARKS),

	// D#4 A#3 F#3 C#3 G#2 A#1
	GUITAR_D$_STANDARD_A$_BASS("D# standard / A# bass;6;21;51-46-42-37-32-22" + Const.GMARKS),

	// D4 A3 F3 C3 G2 D2
	GUITAR_D_STANDARD("D standard;6;21;50-45-41-36-31-26" + Const.GMARKS),

	// D4 A3 F3 C3 G2 C2
	GUITAR_DROP_C("Drop C;6;21;50-45-41-36-31-24" + Const.GMARKS),

	// D4 A3 F3 C3 G2 A1
	GUITAR_D_STANDARD_A_BASS("D standard / A bass;6;21;50-45-41-36-31-21" + Const.GMARKS),

	// C#3 G#3 E3 G2 F#2 C#2
	GUITAR_C$_STANDARD("C# standard;6;21;49-44-40-35-30-25" + Const.GMARKS),

	// C#3 G#3 E3 G2 F#2 B1
	GUITAR_DROP_B("Drop B;6;21;49-44-40-35-30-23" + Const.GMARKS),

	// C3 G3 D#3 A#2 F2 C2
	GUITAR_C_STANDARD("C standard;6;21;48-43-39-34-29-24" + Const.GMARKS),

	// C3 G3 D#3 A#2 F2 A#1
	GUITAR_DROP_A$("Drop A#;6;21;48-43-39-34-29-22" + Const.GMARKS),

	// B3 F#3 D3 A2 E2 B1
	GUITAR_B_STANDARD("B standard;6;21;47-42-38-33-28-23" + Const.GMARKS),

	// B3 F#3 D3 A2 E2 A1
	GUITAR_DROP_A("Drop A;6;21;47-42-38-33-28-21" + Const.GMARKS),

	// E4 C3 G3 C3 A2 C2
	GUITAR_OPEN_C6("Open C6;6;21;52-48-43-36-33-24" + Const.GMARKS),

	// C3 G3 D#3 C3 G2 C2
	GUITAR_OPEN_C_MINOR("Open C minor;6;21;48-43-39-36-31-24" + Const.GMARKS),

	// C3 G3 D#3 C3 G2 C2
	GUITAR_OPEN_C_MINOR_D$_BASS("Open C minor / D# bass;6;21;48-43-39-36-31-27" + Const.GMARKS),

	// D4 A3 F$3 D3 A2 D2
	GUITAR_OPEN_D("Open D;6;21;50-45-42-38-33-26" + Const.GMARKS),

	// D4 A3 G3 D3 A2 D2
	GUITAR_OPEN_DSUS4("Open Dsus4;6;21;50-45-43-38-33-26" + Const.GMARKS),

	// D4 A3 F#3 D3 A2 D2
	GUITAR_OPEN_D_MAJOR("Open D major;6;21;50-45-42-38-33-26" + Const.GMARKS),

	// D4 A3 F3 D3 A2 D2
	GUITAR_OPEN_D_MINOR("Open D minor;6;21;50-45-41-38-33-26" + Const.GMARKS),

	// D4 A3 F3 D3 A2 F2
	GUITAR_OPEN_D_MINOR_F_BASS("Open D minor / F bass;6;21;50-45-41-38-33-29" + Const.GMARKS),

	// D4 B3 G3 D3 G2 D2
	GUITAR_OPEN_G("Open G;6;21;50-47-43-38-31-26" + Const.GMARKS),

	// E4 B3 G3 D3 A2 E2
	GUITAR_ALL_FOURTHS("All-fourths tuning;6;22;53-48-43-38-33-28" + Const.GMARKS),
	
	// G4 E4 A3 D3 G2 C2
	GUITAR_NST("New standard tuning;6;22;55-52-45-38-31-24" + Const.GMARKS),

	// E4 B3 G3 D3 A2 E2 B1
	GUITAR7_B_STANDARD("7-string B standard;7;24;52-47-43-38-33-28-23" + Const.GMARKS24),

	// E4 B3 G3 D3 A2 E2 A1
	GUITAR7_DROP_A("7-string drop A;7;24;52-47-43-38-33-28-21" + Const.GMARKS24),

	// D#4 A#3 F#3 C#3 G#2 D#2 A#1
	GUITAR7_A$_STANDARD("7-string A# standard;7;24;51-46-42-37-32-27-22" + Const.GMARKS24),

	// D#4 A#3 F#3 C#3 G#2 D#2 G#1
	GUITAR7_DROP_G$("7-string drop G#;7;24;51-46-42-37-32-27-20" + Const.GMARKS24),

	// D4 A3 F3 C3 G2 D2 A1
	GUITAR7_A_STANDARD("7-string A standard;7;24;50-45-41-36-31-26-21" + Const.GMARKS24),

	// D4 A3 F3 C3 G2 D2 G1
	GUITAR7_DROP_G("7-string drop G;7;24;50-45-41-36-31-26-19" + Const.GMARKS24),

	// C#3 G#3 E3 G2 F#2 C#2 G#1
	GUITAR7_G$_STANDARD("7-string G# standard;6;24;49-44-40-35-30-25-20" + Const.GMARKS24),

	// C#3 G#3 E3 G2 F#2 B1 F#1
	GUITAR7_DROP_F$("7-string drop F#;6;24;49-44-40-35-30-23-18" + Const.GMARKS24),

	// C3 G3 D#3 A#2 F2 C2 G1
	GUITAR7_G_STANDARD("7-string G standard;6;24;48-43-39-34-29-24-19" + Const.GMARKS24),

	// C3 G3 D#3 A#2 F2 A#1 F1
	GUITAR7_DROP_F("7-string drop F;6;24;48-43-39-34-29-22-17" + Const.GMARKS24),

	// E4 B3 G3 D3 A2 E2 B1 F#1
	GUITAR8_F$_STANDARD("8-string F# standard;8;24;52-47-43-38-33-28-23-18" + Const.GMARKS24),

	// E4 B3 G3 D3 A2 E2 B1 E1
	GUITAR8_DROP_E("8-string drop E;8;24;52-47-43-38-33-28-23-16" + Const.GMARKS24),

	// D#4 A#3 F#3 C#3 G#2 D#2 A#1 F1
	GUITAR8_F_STANDARD("8-string F standard;8;24;51-46-42-37-32-27-22-17" + Const.GMARKS24),

	// D#4 A#3 F#3 C#3 G#2 D#2 A#1 D#1
	GUITAR8_DROP_D$("8-string drop D#;8;24;51-46-42-37-32-27-22-15" + Const.GMARKS24),

	// E4 B3 G3 D3 A2 E2 B1 F#1 C#1
	GUITAR9_C$_STANDARD("9-string C# standard;9;24;52-47-43-38-33-28-23-18-13" + Const.GMARKS24),

	// E4 B3 G3 D3 A2 E2 B1 F#1 B0
	GUITAR9_DROP_B("9-string drop B;9;24;52-47-43-38-33-28-23-18-11" + Const.GMARKS24),

	// G2 D2 A1 E1
	BASS_E_STANDARD("Bass standard tuning;4;20;31-26-21-16" + Const.BMARKS),
	
	// G2 D2 A1 D1
	BASS_DROP_D("Bass drop D;4;20;31-26-21-14" + Const.BMARKS),

	// F#2 C#2 G#1 D#1
	BASS_D$_STANDARD("Bass D# standard;4;21;30-25-20-15" + Const.BMARKS),

	// F#2 C#2 G#1 C#1
	BASS_DROP_C$("Bass drop C#;4;21;30-25-20-13" + Const.BMARKS),

	// F2 C2 G1 D1
	BASS_D_STANDARD("Bass D standard;4;21;29-24-19-14" + Const.BMARKS),

	// F2 C2 G1 C1
	BASS_DROP_C("Bass drop C;4;21;29-24-19-12" + Const.BMARKS),

	// E2 B1 F#1 C#1
	BASS_C$_STANDARD("Bass C# standard;4;22;28-23-18-13" + Const.BMARKS),

	// E2 B1 A#1 B0
	BASS_DROP_B("Bass drop B;4;22;28-23-18-11" + Const.BMARKS),

	// D#2 A#1 F1 C1
	BASS_C_STANDARD("Bass C standard;4;22;27-22-17-12" + Const.BMARKS),

	// D#2 A#1 F1 A#0
	BASS_DROP_A$("Bass drop A#;4;22;27-22-17-10" + Const.BMARKS),

	// G2 D2 A1 E1 B0
	BASS5_B_STANDARD("5-string bass B standard;5;20;31-26-21-16-11" + Const.BMARKS),

	// G2 D2 A1 E1 A0
	BASS5_DROP_A("5-string bass drop A;5;20;31-26-21-16-9" + Const.BMARKS),
	
	// A4 E4 C4 G4
	UKULELE_STANDARD("Ukulele standard tuning;4;18;57-52-48-55" + Const.UMARKS),

	// A4 E4 A3 G4
	UKULELE_DROP_A("Ukulele drop A;4;18;57-52-45-55" + Const.UMARKS),;
	
	String specs;

	DefaultPreset(String specs)
	{
		this.specs = specs;
	}

	public static DefaultPreset get(String specs)
	{
		try
		{
			return DefaultPreset.valueOf(specs.replace('#', '$').toUpperCase());
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static DefaultPreset getDefault()
	{
		return DefaultPreset.GUITAR_E_STANDARD;
	}

	class Const
	{
		private static final String GMARKS = ";3-5-7-9-12-15-17-19-21";
		private static final String GMARKS24 = ";3-5-7-9-12-15-17-19-21-24";
		private static final String BMARKS = ";3-5-7-9-12-15-17-19";
		private static final String UMARKS = ";5-7-10-12";
	}
}
/*
Adapted from
https://gist.github.com/fsmv/4173480

handles sound generation
 */

import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundSandbox
{
	public static final int SAMPLE_RATE = 44100;
	public static final int BITS_PER_SAMPLE = 16;

	public static final int FILE_LENGTH = 2;

	private static AudioFormat mAudioFormat;
	private static SourceDataLine mDataLine;

	public static void generateTones(List<Double> frequencies)
	{
		if (frequencies.isEmpty())
			return;

		double[][] samples = new double[frequencies.size()][SAMPLE_RATE * FILE_LENGTH];

		for (int i = 0; i < frequencies.size(); i++)
		{
			for (int j = 0; j < samples[0].length; j++)
			{
				samples[i][j] = (getSampleAtFrequency(frequencies.get(i), j));
			}
		}

		playSamples(samples);
	}

	public static double getSampleAtFrequency(double frequency, int sampleNum)
	{
		// double amplitude = (sampleNum > (SAMPLE_RATE * FILE_LENGTH) * (1d / 10)) ? ((SAMPLE_RATE * FILE_LENGTH) - sampleNum) / (SAMPLE_RATE * FILE_LENGTH) : 0xfff;

		return 0xfff * Math.sin(frequency * (2 * Math.PI) * sampleNum / SAMPLE_RATE);
	}

	public static void playSamples(double[][] samples)
	{
		AudioFormat format = new AudioFormat(SAMPLE_RATE, BITS_PER_SAMPLE, 1, true, false);
		Info info = new Info(SourceDataLine.class, format);
		SourceDataLine dataLine = null;
		try
		{
			dataLine = (SourceDataLine) AudioSystem.getLine(info);
			dataLine.open(format);
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}

		if (dataLine != null)
		{
			dataLine.start();

			byte[] sampleBytes = convertSamplesToBytes(samples);

			int pos = 0;
			while (pos < sampleBytes.length)
			{
				pos += dataLine.write(sampleBytes, pos, Math.min(dataLine.getBufferSize(), sampleBytes.length - pos));
			}

			dataLine.drain();
			dataLine.close();
		}
	}

	public static void initRealTime()
	{
		mAudioFormat = new AudioFormat(SAMPLE_RATE, BITS_PER_SAMPLE, 1, true, false);
		Info info = new Info(SourceDataLine.class, mAudioFormat);
		mDataLine = null;
		try
		{
			mDataLine = (SourceDataLine) AudioSystem.getLine(info);
			mDataLine.open(mAudioFormat);
			mDataLine.start();
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}

	public static void stopRealTime()
	{
		mDataLine.drain();
		mDataLine.close();
	}

	public static void playSampleInRealTime(short[] sample)
	{
		if (mDataLine != null)
		{
			byte[] sampleByte =
				{0, 0};
			for (int i = 0; i < sample.length; i++)
			{
				byte[] temp = shortToByteArray(sample[i]);
				sampleByte[0] += temp[0];
				sampleByte[1] += temp[1];
			}

			mDataLine.write(sampleByte, 0, sampleByte.length);
		}
	}

	public static byte[] convertSamplesToBytes(double[][] samples)
	{
		byte[] sampleBytes;
		sampleBytes = new byte[samples[0].length * 2];

		/*
	 	splits up the shorts into separate bytes for the audio stream.
		Adds the channels together in this step as well so they both play
		at the same time
		 */
		for (int i = 0; i < sampleBytes.length; i++)
		{
			int it = i / 2;

			double sample = 0;
			for (int j = 0; j < samples.length; j++)
			{
				sample += samples[j][it];
			}

			byte[] sampleS = shortToByteArray((short) sample);
			sampleBytes[i] = sampleS[0];
			i++;
			sampleBytes[i] = sampleS[1];
		}
		return sampleBytes;
	}

	public static byte[] shortToByteArray(short data)
	{
		return new byte[]
				{(byte) (data & 0xff), (byte) ((data >>> 8) & 0xff)};
	}
}
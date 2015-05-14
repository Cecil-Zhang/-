package View;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * 播放声音
 *
 */
/**
 * @ClassName AudioPlayer
 * @Discription
 * @author Fred Xue
 * @Date 2014-6-6
 */
public class AudioPlayer implements Runnable{

	private AudioFormat format;
	private byte[] samples;
	private boolean stop;
	
	static double volume = 2.0; //声音电压在0.0~2.0之间
	// TODO 新增2：用于控制声音的浮点值范围
	static FloatControl floatControl = null;

	public AudioPlayer(String filename) {
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
			format = stream.getFormat();
			samples = getSamples(stream);
		} catch (UnsupportedAudioFileException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		new Thread(this).start();
	}

	/**
	 * 循环播放
	 * @param string
	 * @param i
	 */
	public AudioPlayer(String string, int i) {
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(
					string));
			format = stream.getFormat();
			samples = getSamples(stream);
		} catch (UnsupportedAudioFileException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		new Thread(new LoopPlay()).start();
	}

	public byte[] getSamples() {
		return samples;
	}

	private byte[] getSamples(AudioInputStream audioStream) {
		int length = (int) (audioStream.getFrameLength() * format
				.getFrameSize());
		byte[] samples = new byte[length];
		DataInputStream is = new DataInputStream(audioStream);
		try {
			is.readFully(samples);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return samples;
	}

	private void audioPlay(InputStream source) {
		stop=false;
		int bufferSize = format.getFrameSize()
				* Math.round(format.getSampleRate() / 10);
		byte[] buffer = new byte[bufferSize];
		SourceDataLine line;
		try {
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine) AudioSystem.getLine(info);		
			line.open(format, bufferSize);
			floatControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
			// TODO 设置为默认的音量大小
			setVolume(volume,false);
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
			return;
		}
		line.start();
		try {
			int numBytesRead = 0;
			while (numBytesRead != -1 && !stop) {
				numBytesRead = source.read(buffer, 0, buffer.length);
				if (numBytesRead != -1) {
					line.write(buffer, 0, numBytesRead);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		line.drain();
		line.close();
	}
	
	public void stop(){
		stop=true;
	}

	@Override
	public void run() {
		InputStream stream = new ByteArrayInputStream(this.getSamples());
		//System.out.println("stream");
		this.audioPlay(stream);
		
	}
	
	class LoopPlay implements Runnable{

		@Override
		public void run() {
			while(!stop){
			InputStream stream = new ByteArrayInputStream(getSamples());
			audioPlay(stream);
			}
		}
		
		
	}
	
	public static void setVolume(Double newVolume,boolean isPercent) {
	    if(isPercent){
		// 将百分比转化到0.0~2.0
			volume = newVolume / 50.0;
	    }
		// 详细见FloatControl.Type.MASTER_GAIN的API文档
		float dB = (float) (Math.log(volume == 0.0 ? 0.0001 : volume)
				/ Math.log(10.0) * 20.0);
		floatControl.setValue(dB);
	}

}
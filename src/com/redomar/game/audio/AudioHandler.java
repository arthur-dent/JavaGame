package com.redomar.game.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class AudioHandler {

	private Clip clip;

	public AudioHandler(String path){
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
			AudioFormat baseformat = audioInputStream.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseformat.getSampleRate(), 16,
					baseformat.getChannels(),
					baseformat.getChannels() * 2,
					baseformat.getSampleRate(),
					false
			);
			AudioInputStream decodedAudioInputStream = AudioSystem.getAudioInputStream(
					decodeFormat, audioInputStream);
			clip = AudioSystem.getClip();
			clip.open(decodedAudioInputStream);
		} catch (Exception e){
			System.err.println(e.getStackTrace());
		}
	}

	public void play(){
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}

	public void stop() {
		if (clip.isRunning()) clip.stop();
	}

	public void close(){
		stop();
		clip.close();
	}

}

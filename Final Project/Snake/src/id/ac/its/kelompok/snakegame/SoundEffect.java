package id.ac.its.kelompok.snakegame;

import java.io.*;
import javax.sound.sampled.*;

public class SoundEffect {
		
    Clip clip;
    
    public void setFile(String soundFileName){
        
        try{
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);	
            clip = AudioSystem.getClip();
            clip.open(sound);
        }
        catch(Exception e){
            
        }
    }
    
    public void play(){

        if(clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
    public void stop(){
        clip.stop();
    }

    public void playMusic(double gain) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
          

        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
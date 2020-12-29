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

}
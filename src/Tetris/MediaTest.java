package Tetris;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class MediaTest {
   public void abc() {
        File bgm;
        AudioInputStream stream;
        AudioFormat format;
        DataLine.Info info;
        
        bgm = new File("Music/TetrisBGM.wav"); // 사용시에는 개별 폴더로 변경할 것
        
        Clip clip;
        
        try {
               stream = AudioSystem.getAudioInputStream(bgm);
               format = stream.getFormat();
               info = new DataLine.Info(Clip.class, format);
               clip = (Clip)AudioSystem.getLine(info);
               clip.open(stream);
               clip.start();
               
        } catch (Exception e) {
               System.out.println("err : " + e);
               }
        
 }

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      MediaTest test = new MediaTest();
      while(true) {
         try {
            test.abc();
            Thread.sleep(3000);
         } catch(Exception e) {
            
         }
      }

   }

}
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class Printer{
    public static final int FAST = 50;
    public static final int AVERAGE = 125;
    public static final int SLOW = 250;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static String note1 = "note1.wav";
    private static String note2 = "note2.wav";
    private static String note3 = "note3.wav";

    public static void printInColor(String string,int speed,String color){
        for(char x:string.toCharArray()){
            System.out.print(color+x);
            try{
                Thread.sleep(speed);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }    
        System.out.print(ANSI_RESET);
    }

    public static void printInColorWithAudio(String string,int speed,String color){
        String noteChoice = note1;
        switch(speed){
            case SLOW: noteChoice=note1;break;
            case AVERAGE: noteChoice=note2;break;
            case FAST: noteChoice=note3;break;
        }
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(noteChoice));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            for(char x:string.toCharArray()){
                if(x!='\n' && x!='\t')clip.start();
                System.out.print(color+x);
                try{
                    Thread.sleep(speed);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                clip.stop();
                clip.setMicrosecondPosition(0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.print(ANSI_RESET);
    }
}
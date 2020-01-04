import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Main{
    public static void main(String[] args){
        Printer.printInColor("Randomizing . . .\n\n", Printer.SLOW, Printer.ANSI_GREEN);
        HashMap<RoomRLogic.Room,ArrayList<RoomRLogic.Person>> result =  new RoomRLogic().assign();
        for(Map.Entry<RoomRLogic.Room,ArrayList<RoomRLogic.Person>> x : result.entrySet() ){
            Printer.printInColorWithAudio(x.getKey().name+" ("+x.getKey().groupName+"):\n", Printer.SLOW, Printer.ANSI_GREEN);
            for(RoomRLogic.Person p : x.getValue())Printer.printInColorWithAudio("\t-"+p.name+"\n", Printer.FAST, Printer.ANSI_CYAN);
        }
    }
}
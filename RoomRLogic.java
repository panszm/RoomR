import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class RoomRLogic{

    public HashMap<Room,ArrayList<Person>> assign(){
        String[] names;
        ArrayList<Room> rooms = new ArrayList<Room>();
        ArrayList<Person> people = new ArrayList<Person>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String read = br.readLine();
            String[] sizesS = read.split(",");
            read = br.readLine();
            names = read.split(",");
            for(int i = 0;i<sizesS.length;i++)rooms.add(new Room(names[i],Integer.parseInt(sizesS[i])));
            while((read=br.readLine())!=null){
                sizesS = read.split(",");
                sizesS[1] = sizesS[1].trim();
                people.add(new Person(sizesS[0],sizesS[1]));
            }
            br.close();
        }catch(Exception e){e.printStackTrace();}
        return assignLogic(rooms,people);
    }

    private HashMap<Room,ArrayList<Person>> assignLogic(ArrayList<Room> rooms, ArrayList<Person> people){
        ArrayList<Room> groups = new ArrayList<Room>();
        for(Person entry : people){
            Integer found = -1;
            for(Room r:groups)if(r.name.equals(entry.group))found=groups.indexOf(r);
            if(found.equals(-1)){groups.add(new Room(entry.group,1));}
            else{groups.get(found).size+=1;}
        }
        int ammount = 0;
        for(Room e : rooms){
            ammount+=e.size;
        }
        if(ammount<people.size()){Printer.printInColorWithAudio("Not enough places",Printer.FAST,Printer.ANSI_RED);return null;}
        
        for(int i = 0;i<groups.size();i++){
            Room min = groups.get(i);
            for(int j = i+1;j<groups.size();j++)if(groups.get(j).size<min.size)min=groups.get(j);
            groups.add(i,groups.remove(groups.indexOf(min)));
        }
        for(int i = 0;i<rooms.size();i++){
            Room max = rooms.get(i);
            for(int j = i+1;j<rooms.size();j++)if(rooms.get(j).size>max.size)max=rooms.get(j);
            rooms.add(i,rooms.remove(rooms.indexOf(max)));
        }

        rooms = splitRooms(rooms, groups);
        HashMap<Room,ArrayList<Person>> result = new HashMap<Room,ArrayList<Person>>();
        for(int i = rooms.size()-1;i>=0;i--)if(rooms.get(i).groupName.isEmpty()){rooms.remove(i);}
            else{result.put(rooms.get(i),new ArrayList<Person>());}
        Random rand = new Random(System.currentTimeMillis());
        while(!people.isEmpty()){
            Person randomPerson = people.remove(rand.nextInt(people.size()));
            for(Map.Entry<RoomRLogic.Room,ArrayList<RoomRLogic.Person>> x : result.entrySet()){
                if(x.getKey().size>x.getValue().size() && x.getKey().groupName.equals(randomPerson.group)){
                    x.getValue().add(randomPerson);break;
                }
            }
        }
        return result;
    }

    private ArrayList<Room> splitRooms(ArrayList<Room> rooms, ArrayList<Room> groups){
        ArrayList<ArrayList<Room>> options = new ArrayList<ArrayList<Room>>();
        int options2 = 0;
        options.add((ArrayList) rooms.clone());
        for(Room r:groups){
            options2 = options.size();
            for(int i = 0;i<options2;i++){
                findRoomOptions(r,0, options.get(0),0, options);
            }
        }
        if(options.size()==0){Printer.printInColorWithAudio("No possible option for such combination\n", Printer.FAST, Printer.ANSI_RED);return null;}
        Random rand = new Random(System.currentTimeMillis());
        return options.get(rand.nextInt(options.size()));
    } 
    
    private void findRoomOptions(Room currentGroup,int currentSize, ArrayList<Room> indexObject,int optionIndex,ArrayList<ArrayList<Room>> options){
        if(currentSize>=currentGroup.size)return;
        ArrayList<Room> currentOption = options.remove(options.indexOf(indexObject));
        for(int i = optionIndex;i<currentOption.size();i++){
            if(currentOption.get(i).groupName.isEmpty()){
                ArrayList<Room> copy = new ArrayList<Room>();for(Room z:currentOption)copy.add(new Room(z.name,z.size,z.groupName));
                options.add(copy);
                options.get(options.size()-1).get(i).groupName=currentGroup.name;
                findRoomOptions(currentGroup, currentSize+options.get(options.size()-1).get(i).size, options.get(options.size()-1), i+1, options);
            }
        }
    }

    class Person{
        String name;
        String group;
        protected Person(String name,String group){
            this.name=name;
            this.group=group;
        }
    }
    class Room{
        String name;
        int size;
        String groupName;
        protected Room(String name,int size){
            this.size=size;
            this.name=name;
            this.groupName = "";
        }
        protected Room(String name,int size,String groupName){
            this.size=size;
            this.name=name;
            this.groupName = groupName;
        }
        
    }
}
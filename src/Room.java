import java.lang.reflect.Array;
import java.util.*;
import java.util.HashMap;

public class Room
{
    private String description;
    private HashMap<String, Room> exits = new HashMap<String, Room>();
    private ArrayList<Item> things = new ArrayList<>();

    //define array of Item instances

    public Room(String description)
    {
        this.description = description;
    }

    public void setExits(String direction, Room val)
    {
        if(val != null) {
            exits.put(direction, val);
        }
    }

    public ArrayList<Item> getThings(){
        return things;
    }

    public Room getExit(String key){
        return exits.get(key);
    }

    public String getDescription()
    {
        return description;
    }

    public void printKeys(){
        Iterator iter = exits.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry elem = (Map.Entry)iter.next();
            System.out.print(elem.getKey() + " ");
        }
    }
}
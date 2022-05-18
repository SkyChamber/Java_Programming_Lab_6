package mainPart;

import java.io.Serializable;

public class Chapter implements Serializable {
    public Chapter(String name, String world){
        this.Name = name;
        this.World = world;
        this.legion = Name + " from " + World;
    }
    private String Name; //Поле не может быть null, Строка не может быть пустой
    private String World; //Поле может быть null
    private String legion;

    public String getName(){
        return this.Name;
    }
    public String getWorld(){
        return this.World;
    }
    public String getLegion(){
        if (this.World == null){
            return this.Name;
        }else {
            return this.legion;
        }
    }
}
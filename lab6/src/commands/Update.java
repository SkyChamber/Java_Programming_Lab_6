package commands;

import mainPart.*;

import java.util.Date;
import java.util.TreeSet;

public class Update {
    public Update(TreeSet<Object> collect, long Id){
        this.ID = Id;
        this.collect = collect;
        this.size = collect.size();
        this.arr = collect.toArray(new SpaceMarine[0]);
    }
    private TreeSet collect;
    private long ID;
    private int size;
    private SpaceMarine[] arr;

    public void updating(TreeSet collection, SpaceMarine spaceMarine){

        TreeSet<SpaceMarine> temp = new TreeSet<SpaceMarine>();

        int tCount = 0;
        while (tCount != size){
            if (this.arr[tCount].getId() == this.ID){
                temp.add(spaceMarine);
            }else {
                temp.add(this.arr[tCount]);
            }
            tCount++;
            System.out.println(tCount);
        }
        collection.clear();
        collection.addAll(temp);
    }
}

package commands;

import mainPart.*;

import java.util.TreeSet;

public class Remove_by_id {
    public Remove_by_id(TreeSet<Object> collect, long Id){
        this.collect = collect;
        this.size = collect.size();
        this.ID = Id;
        this.arr = collect.toArray(new SpaceMarine[0]);
    }
    private TreeSet collect;
    private long ID;
    private int size;
    private SpaceMarine[] arr;
    private int counter = 0;
    private int checker;



    public int Check(){
        checker = 0;
        while (counter != size){
            if (ID == arr[counter].getId()){
                checker = 1;
                break;
            }else {
                counter++;
            }
        }
        return checker;
    }

    public void remove(TreeSet collection){
        if (checker == 0){
            System.out.println("There is no object with such ID");
        }else {
            TreeSet<SpaceMarine> temp = new TreeSet<SpaceMarine>();

            int tcount = 0;
            while (tcount != size){
                if (tcount == counter){
                    tcount++;
                }else {
                    temp.add(arr[tcount]);
                    tcount++;
                }
            }
            collection.clear();
            collection.addAll(temp);
        }
    }
}
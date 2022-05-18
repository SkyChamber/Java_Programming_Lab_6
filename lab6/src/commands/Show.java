package commands;
import mainPart.SpaceMarine;

import java.util.TreeSet;

public class Show {
    public Show(TreeSet<Object> collect){
        this.arr = collect.toArray(new SpaceMarine[0]);
    }
    private SpaceMarine[] arr;

    public void tell(){
        for (SpaceMarine value:this.arr){
            value.Test();
        }
    }
}

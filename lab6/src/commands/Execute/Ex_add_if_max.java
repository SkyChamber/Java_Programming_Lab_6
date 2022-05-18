package commands.Execute;

import mainPart.SpaceMarine;

import java.util.TreeSet;

public class Ex_add_if_max {
    public Ex_add_if_max(TreeSet<Object> collect, SpaceMarine marine){
        this.arr = collect.toArray(new SpaceMarine[0]);
        this.marine = marine;
        this.lenOfObj = marine.getTest().length();
    }
    private SpaceMarine[] arr;
    private SpaceMarine marine;
    private int lenOfObj;


    public void addifmax(TreeSet collected, long ID){
        int coin = 0;
        for (SpaceMarine value: this.arr){
            if (this.lenOfObj < value.getTest().length()){
                coin = 1;
            }
        }
        if (coin == 0){
            collected.add(this.marine);
            ID++;
        }
    }
}

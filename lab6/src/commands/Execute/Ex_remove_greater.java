package commands.Execute;

import mainPart.SpaceMarine;

import java.util.TreeSet;

public class Ex_remove_greater {
    public Ex_remove_greater(TreeSet<Object> collect, SpaceMarine marine){
        this.arr = collect.toArray(new SpaceMarine[0]);
        this.marine = marine;
        this.lenOfObj = this.marine.getTest().length();
    }
        private SpaceMarine[] arr;
    private SpaceMarine marine;
    private int lenOfObj;

    private TreeSet<SpaceMarine> temporary = new TreeSet<SpaceMarine>();

    public void removing(TreeSet collect){
        for (SpaceMarine value: this.arr){
            if (value.getTest().length() < this.lenOfObj){
                this.temporary.add(value);
            }
        }
        collect.clear();
        collect.addAll(this.temporary);
    }
}

package commands;

import mainPart.SpaceMarine;

import java.util.TreeSet;

public class Filter_greater_than_chapter {
    public Filter_greater_than_chapter(TreeSet<Object> collect, int chapterLen){
        this.chapterLen = chapterLen;
        this.arr = collect.toArray(new SpaceMarine[0]);
    }
    private SpaceMarine[] arr;
    private int chapterLen;

    public void tell(){
        for (SpaceMarine value: this.arr){
            if (value.getLegion().length() > this.chapterLen){
                System.out.println(value.getName());
            }
        }
    }
}

package commands;

import mainPart.SpaceMarine;

import java.util.TreeSet;

public class Filter_less_than_health {
    public Filter_less_than_health(TreeSet<Object> collect, int hp){
        this.health = hp;
        this.arr = collect.toArray(new SpaceMarine[0]);
    }
    private SpaceMarine[] arr;
    private int health;

    public void tell(){
        for (SpaceMarine value : this.arr){
            if (value.getHealth() < health){
                System.out.println(value.getName());
            }
        }
    }
}

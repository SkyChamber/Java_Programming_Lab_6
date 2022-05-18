package commands;

import mainPart.SpaceMarine;

import java.util.TreeSet;
import java.util.regex.Pattern;

public class Filter_starts_with_name {
    public Filter_starts_with_name(TreeSet<Object> collection){
        this.arr = collection.toArray(new SpaceMarine[0]);
        this.lenOfObj = this.arr.length;
    }
    private SpaceMarine[] arr;
    private int lenOfObj;

    public void tell(String start){
        String regex = "^" + start + "(\\w*)";
        Pattern pattern = Pattern.compile(regex);
        int counter = 0;
        while (counter < lenOfObj){
            boolean matcher = Pattern.matches(regex,this.arr[counter].getName());
            if (matcher){
                System.out.println(this.arr[counter].getName());
            }
            counter++;
        }
    }
}

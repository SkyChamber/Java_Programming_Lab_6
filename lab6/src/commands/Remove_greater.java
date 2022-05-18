package commands;

import mainPart.*;

import java.util.Date;
import java.util.TreeSet;

public class Remove_greater {

    public void removing(TreeSet<Object> collection, SpaceMarine spaceMarine){
        Object[] arr = collection.toArray();
        int lenOfObj = spaceMarine.getTest().length();
        TreeSet temporary = new TreeSet();

        for (Object value: arr){
            SpaceMarine marine = (SpaceMarine) value;
            if (marine.getTest().length() < lenOfObj){
                temporary.add(value);
            }
        }
        collection.clear();
        collection.addAll(temporary);
    }
}

package commands;

import mainPart.*;

import java.util.Date;
import java.util.TreeSet;

public class Add_if_max {

    public boolean addifmaximum(TreeSet<Object> collection, SpaceMarine spaceMarine){
        int coin = 0;
        int counter = 0;
        Object[] arr = collection.toArray();
        int lenOfObj = spaceMarine.getTest().length();

        while (counter < collection.size()){
            SpaceMarine tempObj = (SpaceMarine) arr[counter];
            if (lenOfObj < tempObj.getTest().length()){
                coin = 1;
            }
            counter++;
        }

        if (coin == 0){
            collection.add(spaceMarine);
            return true;
        }else {
            return false;
        }
    }
}

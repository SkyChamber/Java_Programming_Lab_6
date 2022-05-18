package commands;

import mainPart.SpaceMarine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;

public class Save {
    public Save(TreeSet<Object> collect, String outputFileName){
        this.arr = collect.toArray(new SpaceMarine[0]);

        String path = new java.io.File(".").getAbsolutePath();
        StringBuffer sb = new StringBuffer(path);
        int da = path.length();
        sb.delete(da-1,da);
        this.regex = sb + outputFileName + ".txt";
        System.out.println(regex);
    }
    private String regex;
    private SpaceMarine[] arr;

    public void save(){
        try  (BufferedWriter writter = new BufferedWriter(new FileWriter(regex))){
            writter.write("");
            for (SpaceMarine value : arr) {
                writter.write("<Spacemarine>\n"+
                        "  <id>"+value.getId()+"</id>\n"+
                        "  <name>"+value.getName()+"</name>\n"+
                        "  <cords>"+value.getSCoordinates()+"</cords>\n"+
                        "  <date>"+value.getSDate()+"</date>\n"+
                        "  <hp>"+value.getHealth()+"</hp>\n"+
                        "  <category>"+value.getCategory()+"</category>\n"+
                        "  <weapon>"+value.getWeaponType()+"</weapon>\n"+
                        "  <mele>"+value.getMeleeWeapon()+"</mele>\n"+
                        "  <chapter>"+value.getLegion()+"</chapter>\n"+
                        "</Spacemarine>\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Ok");
    }
}

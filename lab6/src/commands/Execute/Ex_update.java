package commands.Execute;

import mainPart.SpaceMarine;

import java.util.Date;
import java.util.TreeSet;

public class Ex_update {
    public Ex_update(TreeSet<Object> collect, String ID, Date date){
        this.Marcus = new SpaceMarine(Long.parseLong(ID), date);
        this.arr = collect.toArray(new SpaceMarine[0]);
        this.collect = collect;
        this.data = date;
        try {
            this.ID = Long.parseLong(ID);
        }catch (IllegalArgumentException e){
            this.breaker = 1;
            this.ID = 149148134;
        }

        this.size = arr.length;
    }
    private SpaceMarine Marcus;

    private TreeSet collect;
    private long ID;
    private int size;
    private SpaceMarine[] arr;
    private int counter = 0;
    private int checker = 0;
    private Date data;

    private int breaker = 0;

    public int Check(){
        checker = 0;
        while (counter != size){
            if (ID == arr[counter].getId()){
                checker = 1;
                data = arr[counter].getCreationDate();
                break;
            }else {
                counter++;
            }
        }
        if (breaker == 1){
            checker = 0;
        }
        return checker;
    }
    public void updating(TreeSet collection,String name, String xcord, String ycord, String hp, String acategory,
                         String weapon, String mele, String cname, String wname){
        if (checker == 0){
            int ddf = 0;
        }else {
            Ex_add ex_add = new Ex_add(this.ID,name,xcord,ycord,this.data,hp,acategory,weapon,mele,cname,wname);
            TreeSet<SpaceMarine> temp = new TreeSet<SpaceMarine>();

            for (SpaceMarine value : this.arr){
                if (value.getId() == this.ID){
                    temp.add(ex_add.adding());
                }else {
                    temp.add(value);
                }
            }

            collection.clear();
            collection.addAll(temp);
        }
    }
}

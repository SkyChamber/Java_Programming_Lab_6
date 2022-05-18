package commands.Execute;

import mainPart.*;

import java.util.Date;

public class Ex_add {
    public Ex_add(long ID, String name, String xcord, String ycord, Date date, String hp, String acategory,
                  String weapon, String mele, String cname, String wname){
        this.Marcus = new SpaceMarine(ID, date);
        Marcus.setName(name);

        double x;
        try{
            x = Double.parseDouble(xcord);
        }catch (IllegalArgumentException e){
            breaker = 1;
            x = 0;
        }
        double y;
        try {
            y = Double.parseDouble(ycord);
        }catch (IllegalArgumentException e){
            breaker = 1;
            y = 0;
        }
        Coordinates cord = new Coordinates(x,y);
        Marcus.setCoordinates(cord);

        int health;
        try {
            health = Integer.parseInt(hp);
        }catch (IllegalArgumentException e){
            breaker = 1;
            health = 0;
        }
        Marcus.setHealth(health);

        Enum_explainer enum_explainer = new Enum_explainer(acategory,weapon,mele);
        if (enum_explainer.getAstsrtes() == null || enum_explainer.getWeapon() == null || enum_explainer.getMele() == null){
            breaker = 1;
        }
        Marcus.setCategory(enum_explainer.getAstsrtes());
        Marcus.setWeaponType(enum_explainer.getWeapon());
        Marcus.setMeleeWeapon(enum_explainer.getMele());

        if (cname == null){
            breaker = 1;
        }
        String tw;
        if (wname == ""){
            tw = null;
        }else {
            tw = wname;
        }
        Chapter chapter = new Chapter(cname,tw);
        Marcus.setChapter(chapter);
    }
    private SpaceMarine Marcus;
    private int breaker = 0;

    public int getBreaker() {
        return breaker;
    }

    public SpaceMarine adding(){
        return this.Marcus;
    }
}

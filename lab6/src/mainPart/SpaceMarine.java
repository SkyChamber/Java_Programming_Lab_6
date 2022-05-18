package mainPart;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SpaceMarine extends human implements Comparable<SpaceMarine>{
    public SpaceMarine(long ID, Date date){
        this.id = ID;
        this.creationDate = date;
    }
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int health; //Значение поля должно быть больше 0
    private AstartesCategory category; //Поле не может быть null
    private Weapon weaponType; //Поле не может быть null
    private MeleeWeapon meleeWeapon; //Поле не может быть null
    private Chapter chapter; //Поле не может быть null

    SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");


    //Это ID
    public long getId(){
        return this.id;
    }

    //Это имя
    public void setName(String name){
        if (name == "Horus"){
            this.name = "Hyila";
        }else {
        this.name = name;
        }
    }
    public String getName(){
        return this.name;
    }

    //Это корды
    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }
    public String getSCoordinates(){
        return coordinates.getCoords();
    }

    //Это дата
    public String getSDate(){
        return formatForDateNow.format(this.creationDate);
    }
    public Date getCreationDate(){
        return this.creationDate;
    }

    //Это здоровье
    public void setHealth(int health){
        this.health = health;
    }
    public int getHealth(){
        return this.health;
    }

    //Это принадлежность
    public void setCategory(AstartesCategory category){
        this.category = category;
    }
    public AstartesCategory getCategory(){
        return this.category;
    }

    //Это оновное оружие
    public void setWeaponType(Weapon weaponType){
        this.weaponType = weaponType;
    }
    public Weapon getWeaponType(){
        return this.weaponType;
    }

    //Это мили оружие
    public void setMeleeWeapon(MeleeWeapon meleeWeapon){
        this.meleeWeapon = meleeWeapon;
    }
    public MeleeWeapon getMeleeWeapon(){
        return this.meleeWeapon;
    }

    //Это легион
    public void setChapter(Chapter chapter){
        this.chapter = chapter;
    }
    public String getLegion(){
        return this.chapter.getLegion();
    }

    @Override
    public int compareTo(SpaceMarine obj) {
        return (int) (this.id - obj.getId());
    }

    @Override
    public String toString() {
        return name;
    }

    public void Test(){
        System.out.println(id+"\n"+name+"\n"+coordinates.getX()+"   "+coordinates.getY()
                +"\n"+formatForDateNow.format(this.creationDate)+"\n"+health+"\n"+
                category+"\n"+weaponType+"\n"+meleeWeapon+"\n"+chapter.getLegion());
    }
    public String getTest(){
        return id+"\n"+name+"\n"+coordinates.getX()+"   "+coordinates.getY()
                +"\n"+formatForDateNow.format(this.creationDate)+"\n"+health+"\n"+
                category+"\n"+weaponType+"\n"+meleeWeapon+"\n"+chapter.getLegion();
    }
}

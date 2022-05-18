package mainPart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Read_collection {
    public Read_collection(String filename, long ID){
        String path = new java.io.File(".").getAbsolutePath();
        StringBuffer sb = new StringBuffer(path);
        int da = path.length();
        sb.delete(da-1,da);
        this.regex = sb + filename;
        this.gID = ID;
        System.out.println(regex);
    }
    private String regex;
    private long gID;
    private int coin = 1;
    private int breaker = 0;
    private int amount = 0;

    public void reading(TreeSet<Object> collect, TreeSet IDset){
        BufferedReader fileReader;
        TreeSet<Long> tempIDset = new TreeSet<>();
        TreeSet<Object> tempCollection = new TreeSet<>();
        try {
            fileReader = new BufferedReader(new FileReader(this.regex));
            String input = fileReader.readLine();
            while (input != null){
                amount ++;

                String regexstart = "^<Spacemarine>";
                boolean smatcher = Pattern.matches(regexstart,input);
                if (!smatcher){
                    System.out.println("start");
                    breaker = 1;
                    break;
                }

                String idin = fileReader.readLine();
                String idmatch;
                long id;
                Matcher idmatcher = Pattern.compile("<id>\\d+</id>").matcher(idin);
                if (idmatcher.find()){
                    idmatch = idmatcher.group();
                    StringBuffer ID = new StringBuffer(idmatch);
                    ID.delete(0, 4);
                    ID.delete(ID.length()-5,ID.length());
                    id = Long.parseLong(String.valueOf(ID));
                    tempIDset.add(id);
                    if (tempIDset.size() != amount){
                        System.out.println("ID");
                        breaker = 1;
                        break;
                    }
                }else {
                    System.out.println("ID");
                    breaker = 1;
                    break;
                }



                String namein = fileReader.readLine();
                String namematch = "";
                String name;
                Matcher namematcher = Pattern.compile("<name>.+</name>").matcher(namein);
                if (namematcher.find()){
                    namematch = namematcher.group();
                    StringBuffer names = new StringBuffer(namematch);
                    names.delete(0, 6);
                    names.delete(names.length()-7,names.length());
                    name = String.valueOf(names);
                }else {
                    System.out.println("name");
                    breaker = 1;
                    break;
                }

                String cordsin = fileReader.readLine();
                String cordsmatch = "";
                Double xcord;
                Double ycord;
                String[] cordarr;
                Coordinates coordinates;
                Matcher cordsmatcher = Pattern.compile("<cords>\\d+\\.\\d+\\s+\\d+\\.\\d+</cords>").matcher(cordsin);
                if (cordsmatcher.find()){
                    cordsmatch = cordsmatcher.group();
                    StringBuffer cords = new StringBuffer(cordsmatch);
                    cords.delete(0, 7);
                    cords.delete(cords.length()-8,cords.length());
                    cordarr = String.valueOf(cords).split("[ ]+");
                    xcord = Double.parseDouble(cordarr[0]);
                    ycord = Double.parseDouble(cordarr[1]);
                    coordinates = new Coordinates(xcord,ycord);
                }else {
                    System.out.println("cords");
                    breaker = 1;
                    break;
                }



                String datein = fileReader.readLine();
                String datematch;
                Date date;
                Matcher datematcher = Pattern.compile("<date>\\d+\\.\\d+\\.\\d+</date>").matcher(datein);
                if (datematcher.find()){
                    datematch = datematcher.group();
                    StringBuffer data = new StringBuffer(datematch);
                    data.delete(0, 6);
                    data.delete(data.length()-7,data.length());
                    date = new SimpleDateFormat("yyyy.MM.dd").parse(String.valueOf(data));
                }else {
                    System.out.println("date");
                    breaker = 1;
                    break;
                }



                String hpin = fileReader.readLine();
                String hpmatch;
                int hp;
                Matcher hpmatcher = Pattern.compile("<hp>\\d+</hp>").matcher(hpin);
                if (hpmatcher.find()){
                    hpmatch = hpmatcher.group();
                    StringBuffer hpvalue = new StringBuffer(hpmatch);
                    hpvalue.delete(0,4);
                    hpvalue.delete(hpvalue.length()-5,hpvalue.length());
                    hp = Integer.parseInt(String.valueOf(hpvalue));
                }else {
                    System.out.println("hp");
                    breaker = 1;
                    break;
                }


                String astartesin = fileReader.readLine();
                String astartesmatch;
                AstartesCategory astartesCategory;
                Matcher astartesmatcher = Pattern.compile("<category>.+</category>").matcher(astartesin);
                if (astartesmatcher.find()){
                    astartesmatch = astartesmatcher.group();
                    StringBuffer astartes = new StringBuffer(astartesmatch);
                    astartes.delete(0,10);
                    astartes.delete(astartes.length()-11, astartes.length());
                    switch (String.valueOf(astartes)){
                        case "AGGRESSOR":
                            astartesCategory = AstartesCategory.AGGRESSOR;
                            break;
                        case "CHAPLAIN":
                            astartesCategory = AstartesCategory.CHAPLAIN;
                            break;
                        case "HELIX":
                            astartesCategory = AstartesCategory.HELIX;
                            break;
                        default:
                            astartesCategory = null;
                    }
                    if (astartesCategory == null){
                        breaker = 1;
                        break;
                    }
                }else {
                    System.out.println("astartes");
                    breaker = 1;
                    break;
                }


                String weaponin = fileReader.readLine();
                String waeponmatch;
                Weapon weapon;
                Matcher weaponmatcher = Pattern.compile("<weapon>.+</weapon>").matcher(weaponin);
                if (weaponmatcher.find()){
                    waeponmatch = weaponmatcher.group();
                    StringBuffer weapons = new StringBuffer(waeponmatch);
                    weapons.delete(0,8);
                    weapons.delete(weapons.length()-9, weapons.length());
                    switch (String.valueOf(weapons)){
                        case "BOLTGUN":
                            weapon = Weapon.BOLTGUN;
                            break;
                        case "HEAVY_BOLTGUN":
                            weapon = Weapon.HEAVY_BOLTGUN;
                            break;
                        case "COMBI_FLAMER":
                            weapon = Weapon.COMBI_FLAMER;
                            break;
                        case "PLASMA_GUN":
                            weapon = Weapon.PLASMA_GUN;
                            break;
                        case "MULTI_MELTA":
                            weapon = Weapon.MULTI_MELTA;
                            break;
                        default:
                            weapon = null;
                    }
                    if (weapon == null){
                        breaker = 1;
                        break;
                    }
                } else {
                    System.out.println("weapon");
                    breaker = 1;
                    break;
                }


                String melein = fileReader.readLine();
                String melematch;
                MeleeWeapon meleeWeapon;
                Matcher melematcher = Pattern.compile("<mele>.+</mele>").matcher(melein);
                if (melematcher.find()){
                    melematch = melematcher.group();
                    StringBuffer mele = new StringBuffer(melematch);
                    mele.delete(0,6);
                    mele.delete(mele.length()-7, mele.length());
                    switch (String.valueOf(mele)){
                        case "CHAIN_SWORD":
                            meleeWeapon = MeleeWeapon.CHAIN_SWORD;
                            break;
                        case "POWER_SWORD":
                            meleeWeapon = MeleeWeapon.POWER_SWORD;
                            break;
                        case "CHAIN_AXE":
                            meleeWeapon = MeleeWeapon.CHAIN_AXE;
                            break;
                        case "MANREAPER":
                            meleeWeapon = MeleeWeapon.MANREAPER;
                            break;
                        default:
                            meleeWeapon = null;
                    }
                    if (meleeWeapon == null){
                        breaker = 1;
                        break;
                    }
                }else {
                    System.out.println("mele");
                    breaker = 1;
                    break;
                }


                String chapterin = fileReader.readLine();
                Chapter chapter;
                String chapters;
                String cname;
                String wname;
                Matcher chaptermatcher = Pattern.compile("<chapter>.+</chapter>").matcher(chapterin);
                if (chaptermatcher.find()){
                    StringBuffer chaptervalue = new StringBuffer(chaptermatcher.group());
                    chaptervalue.delete(0,9);
                    chaptervalue.delete(chaptervalue.length()-10, chaptervalue.length());
                    chapters = String.valueOf(chaptervalue);
                    boolean matcher = Pattern.matches("from", chapters);
                    if (matcher){
                        String[] chapterarr = chapterin.split(" from ");
                        cname = chapterarr[0];
                        System.out.println(cname);
                        wname = chapterarr[1];
                        System.out.println(wname);
                    } else {
                        cname = chapters;
                        wname = null;
                    }
                    chapter = new Chapter(cname,wname);
                }else {
                    System.out.println("chapter");
                    breaker = 1;
                    break;
                }

                SpaceMarine marine = new SpaceMarine(id,date);
                marine.setName(name);
                marine.setCoordinates(coordinates);
                marine.setHealth(hp);
                marine.setCategory(astartesCategory);
                marine.setWeaponType(weapon);
                marine.setMeleeWeapon(meleeWeapon);
                marine.setChapter(chapter);

                marine.Test();

                tempCollection.add(marine);

                String endinginput = fileReader.readLine();
                String regexend = "^</Spacemarine>";
                boolean ematcher = Pattern.matches(regexend,endinginput);
                if (!ematcher){
                    breaker = 1;
                    break;
                }

                input = fileReader.readLine();
            }
        }catch (IOException | ParseException e){
            System.out.println("There is no such file");
            this.coin = 0;
        }
        if (breaker != 1){
            collect.addAll(tempCollection);
            IDset.addAll(tempIDset);
        }else {
            System.out.println("Голимый файл");
        }
    }

    public long getgID() {
        return gID;
    }

    public int getcoin(){
        return this.coin;
    }
}

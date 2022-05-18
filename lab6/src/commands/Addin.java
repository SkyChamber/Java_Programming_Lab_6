package commands;

import mainPart.*;

import java.util.Date;
import java.util.Scanner;
import java.util.TreeSet;

public class Addin {
    public Addin(long Id, Date date){
        this.Id = Id;
        this.date = date;
    }
    private long Id;
    private Date date;

    public Object adding(){
        SpaceMarine Marcus = new SpaceMarine(Id, date);
        Scanner hear = new Scanner(System.in);


        String namein;
        do {
            System.out.println("Enter the name");
            namein = hear.nextLine();
            if (namein.equals("")){
                namein = null;
            }
        }while (namein == null);

        Marcus.setName(namein);



        Double x = null;
        Double y = null;
        do {
            System.out.println("Enter x coordinate");
            String xin = hear.nextLine();
            if (xin.equals("")){
                x = null;
            }else {
                try{
                    x = Double.parseDouble(xin);
                }catch (IllegalArgumentException e){
                    System.out.println("incorrect input, try again");
                }
            }
        }while (x == null);

        do {
            System.out.println("Enter y coordinate");
            String yin = hear.nextLine();
            if (yin.equals("")){
                y = null;
            }else {
                try{
                    y = Double.parseDouble(yin);
                }catch (IllegalArgumentException e){
                    System.out.println("incorrect input, try again");
                }
            }
        }while (y == null);

        Coordinates cord = new Coordinates(x,y);
        Marcus.setCoordinates(cord);


        String hpin;
        Integer hp = null;
        do {
            System.out.println("Enter health level");
            hpin = hear.nextLine();
            try {
                hp = Integer.parseInt(hpin);
            }catch (IllegalArgumentException e){
                System.out.println("incorrect input, try again");
            }
        }while (hp == null);
        if (hp < 1){
            hp = 1;
        }
        Marcus.setHealth(hp);


        System.out.println("Enter Astartes category (AGGRESSOR, CHAPLAIN or HELIX)");
        int coin = 1;
        while (coin == 1) {
            String Astartesin = hear.nextLine();
            switch (Astartesin) {
                case "AGGRESSOR":
                    Marcus.setCategory(AstartesCategory.AGGRESSOR);
                    coin = 0;
                    break;
                case "CHAPLAIN":
                    Marcus.setCategory(AstartesCategory.CHAPLAIN);
                    coin = 0;
                    break;
                case "HELIX":
                    Marcus.setCategory(AstartesCategory.HELIX);
                    coin = 0;
                    break;
                default:
                    System.out.println("Invalid input, please try again (AGGRESSOR, CHAPLAIN or HELIX)");
            }
        }

        coin = 1;
        System.out.println("Enter Weapon (BOLTGUN, HEAVY_BOLTGUN, COMBI_FLAMER, PLASMA_GUN, MULTI_MELTA)");
        while (coin == 1){
            String Weaponin = hear.nextLine();
            switch (Weaponin){
                case "BOLTGUN":
                    Marcus.setWeaponType(Weapon.BOLTGUN);
                    coin = 0;
                    break;
                case "HEAVY_BOLTGUN":
                    Marcus.setWeaponType(Weapon.HEAVY_BOLTGUN);
                    coin = 0;
                    break;
                case "COMBI_FLAMER":
                    Marcus.setWeaponType(Weapon.COMBI_FLAMER);
                    coin = 0;
                    break;
                case "PLASMA_GUN":
                    Marcus.setWeaponType(Weapon.PLASMA_GUN);
                    coin = 0;
                    break;
                case "MULTI_MELTA":
                    Marcus.setWeaponType(Weapon.MULTI_MELTA);
                    coin = 0;
                    break;
                default:
                    System.out.println("Invalid input, please try again (BOLTGUN, HEAVY_BOLTGUN, COMBI_FLAMER, PLASMA_GUN, MULTI_MELTA)");
            }
        }
        coin = 1;
        System.out.println("Enter mele weapon (CHAIN_SWORD, POWER_SWORD, CHAIN_AXE, MANREAPER)");
        while (coin == 1){
            String MweleWeaponin = hear.nextLine();
            switch (MweleWeaponin){
                case "CHAIN_SWORD":
                    Marcus.setMeleeWeapon(MeleeWeapon.CHAIN_SWORD);
                    coin = 0;
                    break;
                case "POWER_SWORD":
                    Marcus.setMeleeWeapon(MeleeWeapon.POWER_SWORD);
                    coin = 0;
                    break;
                case "CHAIN_AXE":
                    Marcus.setMeleeWeapon(MeleeWeapon.CHAIN_AXE);
                    coin = 0;
                    break;
                case "MANREAPER":
                    Marcus.setMeleeWeapon(MeleeWeapon.MANREAPER);
                    coin = 0;
                    break;
                default:
                    System.out.println("Invalid input, please try again (CHAIN_SWORD, POWER_SWORD, CHAIN_AXE, MANREAPER)");
            }
        }


        String Cnamein;
        do {
            System.out.println("Enter chapter name");
            Cnamein = hear.nextLine();
            if (Cnamein.equals("")){
                Cnamein = null;
            }
        }while (Cnamein == null);

        System.out.println("Enter world name");
        String Wnamein = hear.nextLine();
        if (Wnamein.equals("")){
            Wnamein = null;
        }
        Chapter chapter = new Chapter(Cnamein, Wnamein);
        Marcus.setChapter(chapter);


        Marcus.Test();


        return Marcus;
    }
}

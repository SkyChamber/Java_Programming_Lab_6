package commands.Execute;

import mainPart.*;

public class Enum_explainer {
    public Enum_explainer(String astartes, String weapon, String meleeWeapon){
        this.astartes = astartes;
        this.weapon = weapon;
        this.meleeWeapon = meleeWeapon;
    }
    private String astartes;
    private String weapon;
    private String meleeWeapon;

    public AstartesCategory getAstsrtes() {
        switch (this.astartes) {
            case "AGGRESSOR":
                return AstartesCategory.AGGRESSOR;
            case "CHAPLAIN":
                return AstartesCategory.CHAPLAIN;
            case "HELIX":
                return AstartesCategory.HELIX;
            default:
                return null;
        }
    }
    public Weapon getWeapon(){
        switch (this.weapon){
            case "BOLTGUN":
                return Weapon.BOLTGUN;
            case "HEAVY_BOLTGUN":
                return Weapon.HEAVY_BOLTGUN;
            case "COMBI_FLAMER":
                return Weapon.COMBI_FLAMER;
            case "PLASMA_GUN":
                return Weapon.PLASMA_GUN;
            case "MULTI_MELTA":
                return Weapon.MULTI_MELTA;
            default:
                return null;
        }
    }
    public MeleeWeapon getMele(){
        switch (this.meleeWeapon){
            case "CHAIN_SWORD":
                return MeleeWeapon.CHAIN_SWORD;
            case "POWER_SWORD":
                return MeleeWeapon.POWER_SWORD;
            case "CHAIN_AXE":
                return MeleeWeapon.CHAIN_AXE;
            case "MANREAPER":
                return MeleeWeapon.MANREAPER;
            default:
                return null;
        }
    }
}

package serverStuff;

import mainPart.SpaceMarine;

import java.io.Serializable;
import java.util.TreeSet;

public class Transfer implements Serializable {
    private String command;
    private TreeSet SpaceMarineSet;
    private SpaceMarine spaceMarine;
    private long ID;
    private boolean Case;
    private int number;
    private String string;

    public void setCommand(String string){
        this.command = string;
    }

    public String getCommand(){
        return this.command;
    }

    public void setSpaceMarine(SpaceMarine spaceMarine) {
        this.spaceMarine = spaceMarine;
    }

    public SpaceMarine getSpaceMarine() {
        return spaceMarine;
    }

    public void setTreeSet(TreeSet treeSet) {
        this.SpaceMarineSet = treeSet;
    }

    public TreeSet getTreeSet() {
        return SpaceMarineSet;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getID() {
        return ID;
    }

    public void setCase(boolean aCase) {
        Case = aCase;
    }

    public boolean getCase() {
        return Case;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}

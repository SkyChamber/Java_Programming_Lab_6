package commands;

public class History {

    private String[] lore = new String[5];

    public void update(String note){
        int counter = 4;
        while (counter > 0){
            lore[counter] = lore[counter-1];
            counter--;
        }
        lore[0] = note;
    }

    public void tell(){
        int counter = 0;
        while (counter < 5){
            System.out.println(lore[counter]);
            counter++;
        }
    }

    public String getLore(){
        int counter = 0;
        String teller = "";
        while (counter < 5){
            teller = teller + lore[counter] + "\n";
            counter++;
        }
        return teller;
    }
}
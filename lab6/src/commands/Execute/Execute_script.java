package commands.Execute;

import commands.*;
import mainPart.SpaceMarine;
import serverStuff.ObjectSender;
import serverStuff.Transfer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

public class Execute_script {
    public Execute_script(String filename){
        String path = new java.io.File(".").getAbsolutePath();

        System.out.println(path);

        int da = path.length();
        StringBuffer sb = new StringBuffer(path);
        sb.delete(da-1,da);
        this.regex = sb + filename;

        System.out.println(this.regex);
    }
    private String regex;
    SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");

    public void execute(TreeSet<Object> collection, long ID, Date date, DatagramChannel datagramChannel, InetSocketAddress address){
        BufferedReader fileReader;
        TreeSet temp = collection;
        int errorBreaker = 0;
        try {
            fileReader = new BufferedReader(new FileReader(this.regex));
            History history = new History();
            String input = fileReader.readLine();
            history.update(input);
            while (input != null && input != "exit") {
                switch (input){
                    case "exit":
                        break;
                    case "help":
                        Help help = new Help();
                        help.tell();
                        break;
                    case "info":
                        System.out.println("collection type: TreeSet\n"+formatForDateNow.format(date)+"\n"+"collection size: "+temp.size());
                        break;
                    case "show":
                        Show show = new Show(temp);
                        show.tell();
                        break;
                    case "history":
                        history.tell();
                        break;
                    case "save":
                        String fname = fileReader.readLine();
                        Save save = new Save(temp, fname);
                        save.save();
                        break;
                    case "filter_starts_with_name":
                        Filter_starts_with_name filter_starts_with_name = new Filter_starts_with_name(temp);
                        String filtStartin = fileReader.readLine();
                        filter_starts_with_name.tell(filtStartin);
                        break;
                    case "clear":
                        temp.clear();
                        break;
                    case "remove_by_id":
                        String removein = fileReader.readLine();
                        long remover;
                        try{
                            remover = Long.parseLong(removein);
                            Remove_by_id remove_by_id = new Remove_by_id(temp, remover);
                            remove_by_id.Check();
                            remove_by_id.remove(temp);
                        }catch (IllegalArgumentException e){
                            errorBreaker = 1;
                        }
                        break;
                    case "add":
                        String nameadd = fileReader.readLine();
                        String xadd = fileReader.readLine();
                        String yadd = fileReader.readLine();
                        String hpadd = fileReader.readLine();
                        String astadd = fileReader.readLine();
                        String weapadd = fileReader.readLine();
                        String meleadd = fileReader.readLine();
                        String chapadd = fileReader.readLine();
                        String worldadd = fileReader.readLine();
                        Ex_add ex_add = new Ex_add(ID,nameadd,xadd,yadd,date,hpadd,astadd,weapadd,meleadd,chapadd,worldadd);
                        if (ex_add.getBreaker() == 0){
                            temp.add(ex_add.adding());
                            ID++;
                            System.out.println(ex_add.adding().getTest());
                        }else {
                            errorBreaker = 1;
                        }
                        break;
                    case "update":
                        String upid = fileReader.readLine();
                        String upname = fileReader.readLine();
                        String upx = fileReader.readLine();
                        String upy = fileReader.readLine();
                        String uphp = fileReader.readLine();
                        String upast = fileReader.readLine();
                        String upweap = fileReader.readLine();
                        String upmele = fileReader.readLine();
                        String upchap = fileReader.readLine();
                        String upworld = fileReader.readLine();
                        Ex_update ex_update = new Ex_update(temp,upid,date);
                        if (ex_update.Check() == 1){
                            ex_update.updating(temp,upname,upx,upy,uphp,upast,upweap,upmele,upchap,upworld);
                        }else {
                            errorBreaker = 1;
                        }
                        break;
                    case "Filter_less_than_health":
                        String filtHinput = fileReader.readLine();
                        int filtHin;
                        try {
                            filtHin = Integer.parseInt(filtHinput);
                            Filter_less_than_health filtH = new Filter_less_than_health(temp,filtHin);
                            filtH.tell();
                        } catch (IllegalArgumentException e){
                            errorBreaker = 1;
                        }
                        break;
                    case "Filter_greater_than_chapter":
                        String filtChapterInput = fileReader.readLine();
                        int filtChapterin;
                        try {
                            filtChapterin = Integer.parseInt(filtChapterInput);
                            Filter_greater_than_chapter filtChapter = new Filter_greater_than_chapter(temp, filtChapterin);
                            filtChapter.tell();
                        } catch (IllegalArgumentException e){
                            errorBreaker = 1;
                        }
                        break;
                    case "add_if_max":
                        String nameaddm = fileReader.readLine();
                        String xaddm = fileReader.readLine();
                        String yaddm = fileReader.readLine();
                        String hpaddm = fileReader.readLine();
                        String astaddm = fileReader.readLine();
                        String weapaddm = fileReader.readLine();
                        String meleaddm = fileReader.readLine();
                        String chapaddm = fileReader.readLine();
                        String worldaddm = fileReader.readLine();
                        Ex_add ex_addm = new Ex_add(ID,nameaddm,xaddm,yaddm,date,hpaddm,astaddm,weapaddm,meleaddm,chapaddm,worldaddm);
                        if (ex_addm.getBreaker() == 0){
                            Ex_add_if_max ex_add_if_max = new Ex_add_if_max(temp,ex_addm.adding());
                            ex_add_if_max.addifmax(temp,ID);
                        }else {
                            errorBreaker = 1;
                        }
                        break;
                    case "remove_greater":
                        String namerem = fileReader.readLine();
                        String xrem = fileReader.readLine();
                        String yrem = fileReader.readLine();
                        String hprem = fileReader.readLine();
                        String astrem = fileReader.readLine();
                        String weaprem = fileReader.readLine();
                        String melerem = fileReader.readLine();
                        String chaprem = fileReader.readLine();
                        String worldrem = fileReader.readLine();
                        Ex_add ex_rem = new Ex_add(ID,namerem,xrem,yrem,date,hprem,astrem,weaprem,melerem,chaprem,worldrem);
                        if (ex_rem.getBreaker() == 0){
                            Ex_remove_greater ex_remove_greater = new Ex_remove_greater(temp,ex_rem.adding());
                            ex_remove_greater.removing(temp);
                        }else {
                            errorBreaker = 1;
                        }
                        break;
                    default:
                        errorBreaker = 1;
                }
                if (errorBreaker == 1){
                    break;
                }
                input = fileReader.readLine();
            }

            Transfer collectionSizeTransfer = new Transfer();
            collectionSizeTransfer.setCommand("execution");
            collectionSizeTransfer.setNumber(temp.size());

            ObjectSender filterLessThanHealthSender = new ObjectSender(collectionSizeTransfer);
            filterLessThanHealthSender.send(datagramChannel, address);

            Thread.sleep(100);

            temp.stream().forEach(e->{
                Transfer executeTransfer = new Transfer();
                executeTransfer.setSpaceMarine((SpaceMarine) e);

                ObjectSender objectSender = new ObjectSender(executeTransfer);
                objectSender.send(datagramChannel, address);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            });


        }catch (IOException | InterruptedException e){
            System.out.println("There is no such file");
        }
        if (errorBreaker == 1){
            System.out.println("There is an error in file");
        }
    }
}

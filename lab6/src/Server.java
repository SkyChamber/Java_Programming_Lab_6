import commands.*;
import mainPart.*;
import serverStuff.ObjectReceiver;
import serverStuff.ObjectSender;
import serverStuff.Transfer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public class Server {
    public static void main(String[] args){
        long count = 4;
        Date today = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");

        TreeSet<Object> Ultramarines = new TreeSet<>();

        TreeSet<Long> IDcollect = new TreeSet<>();

        Scanner scanner = new Scanner(System.in);

        int readercoin = 0;
        String filein;
//        filein = args[0];
//        Read_collection read_collection = new Read_collection(filein, count);
//        read_collection.reading(Ultramarines, IDcollect);
//        count = read_collection.getgID();
//        readercoin = read_collection.getcoin();

        if (readercoin == 0){
            do {
                System.out.println("Enter the file name");
                filein = scanner.nextLine();
                Read_collection read_collection2 = new Read_collection(filein, count);
                read_collection2.reading(Ultramarines, IDcollect);
                count = read_collection2.getgID();
                readercoin = read_collection2.getcoin();
            }while (readercoin == 0);
        }


        try (DatagramChannel server = DatagramChannel.open()){
            InetSocketAddress iAdd = new InetSocketAddress("localhost", 40000);
            server.bind(iAdd);

            System.out.println("Server Started: " + iAdd);

            server.configureBlocking(false);

            boolean deathToken = false;
            History history = new History();

            while(!deathToken) {
                ByteBuffer commandReceiverBuffer = ByteBuffer.allocate(1048576);
                SocketAddress remoteAddress = server.receive(commandReceiverBuffer);
                commandReceiverBuffer.flip();

                String entry;
                Transfer transfer;

                if (commandReceiverBuffer.limit() == 0){
                    entry = "nothing";
                    transfer = new Transfer();
                } else {
                    ObjectReceiver objectReceiver = new ObjectReceiver(commandReceiverBuffer);
                    transfer = objectReceiver.unpack();

                    entry = transfer.getCommand();

                    if (entry != "netTest"){
                        System.out.println(entry);
                    }
                }

                boolean historyCoin = true;

                switch (entry){
                    case "netTest":
                        Transfer netTestTransfer = new Transfer();
                        netTestTransfer.setCommand(entry);
                        ObjectSender netTestSender = new ObjectSender(netTestTransfer);
                        netTestSender.send(server, (InetSocketAddress) remoteAddress);
                        historyCoin = false;
                        break;
                    case "exit":
                        Save exitSave = new Save(Ultramarines, "fname");
                        exitSave.save();
                        System.out.println("Client initialize connections suicide ...");
                        deathToken = true;
                        break;
                    case "kill_server":
                        Save killSave = new Save(Ultramarines, "fname");
                        killSave.save();
                        System.out.println("Client initialize suicide ...");
                        deathToken = true;
                        break;
                    case "help":
                        break;
                    case "info":
                        String infoOut = "collection type: TreeSet\n"+formatForDateNow.format(today)+"\n"+"collection size: "+Ultramarines.size();
                        ByteBuffer infoSendBuffer = ByteBuffer.wrap(infoOut.getBytes());
                        server.send(infoSendBuffer, remoteAddress);
                        infoSendBuffer.clear();
                        break;
                    case "show":
                        Transfer showTransfer = new Transfer();
                        showTransfer.setNumber(Ultramarines.size());

                        ObjectSender showSender = new ObjectSender(showTransfer);
                        showSender.send(server, (InetSocketAddress) remoteAddress);

                        Ultramarines.stream().forEach(e->{
                            try {
                                ByteBuffer showBuffer = ByteBuffer.allocate(1048576);
                                ByteArrayOutputStream showByteArrayOS = new ByteArrayOutputStream();
                                ObjectOutputStream showObjectOS = new ObjectOutputStream(showByteArrayOS);

                                showObjectOS.writeObject(e);
                                showObjectOS.flush();

                                showBuffer.put(showByteArrayOS.toByteArray());
                                showBuffer.flip();
                                server.send(showBuffer, remoteAddress);

                                showByteArrayOS.close();
                                showObjectOS.close();
                                Thread.sleep(100);
                            } catch (IOException | InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        });

                        break;
                    case "IDClaim":
                        long tempID = 1;
                        for (long value:IDcollect){
                            if (value == tempID){
                                tempID ++;
                            }
                        }

                        Transfer IDClaimTransfer = new Transfer();
                        IDClaimTransfer.setID(tempID);

                        ObjectSender IDClaimSender = new ObjectSender(IDClaimTransfer);
                        IDClaimSender.send(server, (InetSocketAddress) remoteAddress);

                        historyCoin = false;
                        break;
                    case "add":
                        Ultramarines.add(transfer.getSpaceMarine());
                        IDcollect.add(transfer.getSpaceMarine().getId());
                        break;
                    case "IDCheck":
                        boolean updateToken = IDcollect.stream().anyMatch(e->e== transfer.getID());
                        Transfer IDCheckerTransfer = new Transfer();
                        IDCheckerTransfer.setCase(updateToken);

                        ObjectSender IDCheckSender = new ObjectSender(IDCheckerTransfer);
                        IDCheckSender.send(server, (InetSocketAddress) remoteAddress);

                        historyCoin = false;
                        break;
                    case "update":
                        Update update = new Update(Ultramarines, transfer.getID());
                        update.updating(Ultramarines, transfer.getSpaceMarine());
                        break;
                    case "remove_by_id":
                        long removeID = transfer.getID();
                        Remove_by_id remove_by_id = new Remove_by_id(Ultramarines, removeID);
                        remove_by_id.Check();
                        remove_by_id.remove(Ultramarines);
                        break;
                    case "clear":
                        Ultramarines.clear();
                        break;
                    case "history":
                        String historyOut = history.getLore();
                        ByteBuffer historySendBuffer = ByteBuffer.wrap(historyOut.getBytes());
                        server.send(historySendBuffer, remoteAddress);
                        historySendBuffer.clear();
                        break;
                    case "add_if_max":
                        Add_if_max add_if_max = new Add_if_max();
                        boolean add_if_maxCase = add_if_max.addifmaximum(Ultramarines, transfer.getSpaceMarine());
                        Transfer addIfMaxTransfer = new Transfer();
                        addIfMaxTransfer.setCase(add_if_maxCase);
                        ObjectSender addIfMaxSender = new ObjectSender(addIfMaxTransfer);
                        addIfMaxSender.send(server, (InetSocketAddress) remoteAddress);
                        break;
                    case "remove_greater":
                        Remove_greater remove_greater = new Remove_greater();
                        remove_greater.removing(Ultramarines, transfer.getSpaceMarine());
                        break;
                    case "filter_starts_with_name":
                        int filterStartsWithCounter = 0;
                        TreeSet<SpaceMarine> filterStartsWithSet = new TreeSet<SpaceMarine>();

                        Object[] filterStartsWithArray = Ultramarines.toArray();

                        for (Object value : filterStartsWithArray){
                            String regex = "^" + transfer.getString() + "(\\w*)";
                            SpaceMarine marine = (SpaceMarine) value;
                            boolean matcher = Pattern.matches(regex,marine.getName());
                            if (matcher){
                                filterStartsWithCounter ++;
                                filterStartsWithSet.add(marine);
                            }
                        }
                        Transfer filterStartsWithTransfer = new Transfer();
                        filterStartsWithTransfer.setNumber(filterStartsWithCounter);

                        ObjectSender filterStartsWithSender = new ObjectSender(filterStartsWithTransfer);
                        filterStartsWithSender.send(server, (InetSocketAddress) remoteAddress);

                        Thread.sleep(100);

                        filterStartsWithSet.stream().forEach(e->{
                            try {
                                Transfer filterStartsWithSendTransfer = new Transfer();
                                filterStartsWithSendTransfer.setString(e.getName());

                                ObjectSender filterStartsWithSendSender = new ObjectSender(filterStartsWithSendTransfer);
                                filterStartsWithSendSender.send(server, (InetSocketAddress) remoteAddress);

                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        });
                        break;
                    case "filter_less_than_health":
                        int filterLessThanHealthCounter = 0;
                        TreeSet<SpaceMarine> filterLessThanHealthSet = new TreeSet<SpaceMarine>();

                        Object[] filterLessThanHealthArray = Ultramarines.toArray();

                        for (Object value : filterLessThanHealthArray){
                            if (transfer.getNumber() > ((SpaceMarine) value).getHealth()){
                                filterLessThanHealthCounter ++;
                                filterLessThanHealthSet.add((SpaceMarine) value);
                            }
                        }
                        Transfer filterLessThanHealthTransfer = new Transfer();
                        filterLessThanHealthTransfer.setNumber(filterLessThanHealthCounter);

                        ObjectSender filterLessThanHealthSender = new ObjectSender(filterLessThanHealthTransfer);
                        filterLessThanHealthSender.send(server, (InetSocketAddress) remoteAddress);

                        Thread.sleep(100);

                        filterLessThanHealthSet.stream().forEach(e->{
                            try {
                                Transfer filterLessThanHealthSendTransfer = new Transfer();
                                filterLessThanHealthSendTransfer.setString(e.getName());

                                ObjectSender filterLessThanHealthSendSender = new ObjectSender(filterLessThanHealthSendTransfer);
                                filterLessThanHealthSendSender.send(server, (InetSocketAddress) remoteAddress);

                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        });
                        break;
                    case "filter_greater_than_chapter":
                        int filterGreaterThanChapterCounter = 0;
                        TreeSet<SpaceMarine> filterGreaterThanChapterSet = new TreeSet<SpaceMarine>();

                        Object[] filterGreaterThanChapterArray = Ultramarines.toArray();

                        for (Object value : filterGreaterThanChapterArray){
                            if (transfer.getNumber() < ((SpaceMarine) value).getLegion().length()){
                                filterGreaterThanChapterCounter ++;
                                filterGreaterThanChapterSet.add((SpaceMarine) value);
                            }
                        }
                        Transfer filterGreaterThanChapterTransfer = new Transfer();
                        filterGreaterThanChapterTransfer.setNumber(filterGreaterThanChapterCounter);

                        ObjectSender filterGreaterThanChapterSender = new ObjectSender(filterGreaterThanChapterTransfer);
                        filterGreaterThanChapterSender.send(server, (InetSocketAddress) remoteAddress);

                        Thread.sleep(100);

                        filterGreaterThanChapterSet.stream().forEach(e->{
                            try {
                                Transfer filterGreaterThanChapterSendTransfer = new Transfer();
                                filterGreaterThanChapterSendTransfer.setString(e.getName());

                                ObjectSender filterGreaterThanChapterSendSender = new ObjectSender(filterGreaterThanChapterSendTransfer);
                                filterGreaterThanChapterSendSender.send(server, (InetSocketAddress) remoteAddress);

                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        });
                        break;
                    case "callCollection":
                        count = Collections.max(IDcollect);

                        Transfer callCollectionSizeTransfer = new Transfer();
                        callCollectionSizeTransfer.setNumber(Ultramarines.size());
                        callCollectionSizeTransfer.setID(count);

                        ObjectSender callCollectionSizeSender = new ObjectSender(callCollectionSizeTransfer);
                        callCollectionSizeSender.send(server, (InetSocketAddress) remoteAddress);

                        Ultramarines.stream().map(e->(SpaceMarine) e).forEach(e->{
                            Transfer callCollectionTransfer = new Transfer();
                            callCollectionTransfer.setSpaceMarine(e);

                            ObjectSender callCollectionSender = new ObjectSender(callCollectionTransfer);
                            callCollectionSender.send(server, (InetSocketAddress) remoteAddress);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        });

                        break;
                    case "execution":
                        Long[] executeReceiveTempArr = new Long[transfer.getNumber()];

                        TreeSet<Object> executeReceiveSet = new TreeSet<>();

                        server.configureBlocking(true);

                        Arrays.stream(executeReceiveTempArr).forEach(e->{
                            try {
                                ByteBuffer executeBuffer = ByteBuffer.allocate(1048576);
                                server.receive(executeBuffer);

                                ObjectReceiver executeReceiver = new ObjectReceiver(executeBuffer);
                                executeReceiveSet.add(executeReceiver.unpack().getSpaceMarine());
                            } catch (IOException ex){
                                ex.printStackTrace();
                            }
                        });

                        server.configureBlocking(false);

                        Ultramarines.clear();
                        Ultramarines.addAll(executeReceiveSet);

                        Ultramarines.stream().map(e->(SpaceMarine)e).forEach(e->IDcollect.add(e.getId()));
                        count = Collections.max(IDcollect);

                        break;
                    case "hlt":
                        historyCoin = false;
                        boolean hltDeathToken = false;
                        while (!hltDeathToken){
                            System.out.println("Enter the command");
                            String hltCommand = scanner.nextLine();
                            switch (hltCommand){
                                case "save":
                                    System.out.println("Enter the file name");
                                    String fname = scanner.nextLine();
                                    Save save = new Save(Ultramarines, fname);
                                    save.save();
                                    history.update("save");
                                    break;
                                case "exit":
                                    Save hltExitSave = new Save(Ultramarines, "fname");
                                    hltExitSave.save();
                                    deathToken = true;
                                    hltDeathToken = true;
                                    break;
                                case "exit_hlt":
                                    hltDeathToken = true;
                                default:
                                    int kekwsgkejsntghbdfyhjdxfhgjdxfghjsry6j4r6hjertyh;
                            }
                        }
                        boolean safeHltToken = false;

                        ByteBuffer safeHltBuffer;

                        int tokren = 100;

                        do {
                            safeHltBuffer = ByteBuffer.allocate(1048576);
                            server.receive(commandReceiverBuffer);
                            commandReceiverBuffer.flip();
                            tokren --;
                        } while (tokren != 0);

                        break;
                    default:
                        historyCoin = false;
                }
                if (historyCoin){
                    history.update(entry);
                }
            }
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

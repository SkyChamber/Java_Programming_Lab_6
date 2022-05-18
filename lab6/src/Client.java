import commands.*;
import commands.Execute.Execute_script;
import mainPart.SpaceMarine;
import serverStuff.ObjectReceiver;
import serverStuff.ObjectSender;
import serverStuff.Transfer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.text.SimpleDateFormat;
import java.util.*;

public class Client {
    public static void main(String[] args){
        boolean serverCoin = false;
        try (DatagramChannel client = DatagramChannel.open()) {
            client.bind(null);
            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 40000);

            Scanner scanner = new Scanner(System.in);

            serverCoin = true;

            Date today = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");

            boolean deathToken = false;

            while (!deathToken) {
                System.out.println("Enter the command");
                String clientCommand = scanner.nextLine();

                if (clientCommand != "kill_self"){
                    Transfer netTestTransfer = new Transfer();
                    netTestTransfer.setCommand("netTest");

                    ObjectSender netTestSender = new ObjectSender(netTestTransfer);
                    netTestSender.send(client, serverAddress);

                    client.configureBlocking(false);

                    Thread.sleep(100);

                    ByteBuffer netTestReceiverBuffer = ByteBuffer.allocate(1048576);
                    client.receive(netTestReceiverBuffer);

                    if (netTestReceiverBuffer.position() == 0){
                        clientCommand = "hui";
                    }
                    client.configureBlocking(true);
                }

                switch (clientCommand){
                    case "hui":
                        System.out.println("Server unavailable, please try again later");
                        break;
                    case "exit":
                        Transfer exitTransfer = new Transfer();
                        exitTransfer.setCommand(clientCommand);

                        ObjectSender exitSender = new ObjectSender(exitTransfer);
                        exitSender.send(client, serverAddress);

                        System.out.println("Shutting down the server");
                        deathToken = true;
                        break;
                    case "kill_self":
                        System.out.println("Killing the client");
                        deathToken = true;
                        break;
                    case "kill_server":
                        Transfer killTransfer = new Transfer();
                        killTransfer.setCommand(clientCommand);

                        ObjectSender killSender = new ObjectSender(killTransfer);
                        killSender.send(client, serverAddress);
                        System.out.println("Killing the server");
                        break;
                    case "help":
                        Help help = new Help();
                        help.tell();
                        Transfer helpTransfer = new Transfer();
                        helpTransfer.setCommand(clientCommand);

                        ObjectSender helpSender = new ObjectSender(helpTransfer);
                        helpSender.send(client, serverAddress);
                        break;
                    case "info":
                        Transfer infoTransfer = new Transfer();
                        infoTransfer.setCommand(clientCommand);

                        ObjectSender infoSender = new ObjectSender(infoTransfer);
                        infoSender.send(client, serverAddress);

                        ByteBuffer infoReceiverBuffer = ByteBuffer.allocate(1024);
                        client.receive(infoReceiverBuffer);
                        infoReceiverBuffer.flip();
                        int infoInLimits = infoReceiverBuffer.limit();
                        byte[] infoInBytes = new byte[infoInLimits];
                        infoReceiverBuffer.get(infoInBytes, 0, infoInLimits);
                        String infoIn = new String(infoInBytes);

                        System.out.println(infoIn);
                        break;
                    case "show":
                        Transfer showTransfer = new Transfer();
                        showTransfer.setCommand(clientCommand);

                        ObjectSender showSender = new ObjectSender(showTransfer);
                        showSender.send(client, serverAddress);

                        ByteBuffer showNumberReceiverBuffer = ByteBuffer.allocate(1048576);
                        client.receive(showNumberReceiverBuffer);
                        ObjectReceiver showNumberReceiver = new ObjectReceiver(showNumberReceiverBuffer);
                        Long[] showTempArr = new Long[showNumberReceiver.unpack().getNumber()];

                        Arrays.stream(showTempArr).forEach(e->{
                            try {
                                ByteBuffer showReceiverBuffer = ByteBuffer.allocate(1048576);
                                client.receive(showReceiverBuffer);
                                ByteArrayInputStream showByteArrayIS = new ByteArrayInputStream(showReceiverBuffer.array());
                                ObjectInputStream showObjectIS = new ObjectInputStream(showByteArrayIS);

                                SpaceMarine showSpaceMarine = (SpaceMarine) showObjectIS.readObject();
                                showSpaceMarine.Test();

                                showObjectIS.close();
                                showByteArrayIS.close();
                            } catch (IOException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        });

                        break;
                    case "add":
                        Transfer IDClaimTransfer = new Transfer();
                        IDClaimTransfer.setCommand("IDClaim");

                        ObjectSender IDClaimSender = new ObjectSender(IDClaimTransfer);
                        IDClaimSender.send(client, serverAddress);

                        ByteBuffer IDClaimReceiverBuffer = ByteBuffer.allocate(1048576);
                        client.receive(IDClaimReceiverBuffer);
                        ObjectReceiver IDClaimReceiver = new ObjectReceiver(IDClaimReceiverBuffer);

                        long tempID = IDClaimReceiver.unpack().getID();

                        Addin addin = new Addin(tempID,today);

                        Transfer addTransfer = new Transfer();
                        addTransfer.setCommand(clientCommand);
                        addTransfer.setSpaceMarine((SpaceMarine) addin.adding());

                        ObjectSender addSender = new ObjectSender(addTransfer);
                        addSender.send(client, serverAddress);

                        System.out.println("Object successfully added to collection");
                        break;
                    case "update":
                        Transfer IDCheckTransfer = new Transfer();
                        IDCheckTransfer.setCommand("IDCheck");
                        System.out.println("Enter ID of the object to change");
                        String updateIn = scanner.nextLine();
                        IDCheckTransfer.setID(Long.parseLong(updateIn));
                        ObjectSender IDCheckSender = new ObjectSender(IDCheckTransfer);
                        IDCheckSender.send(client, serverAddress);

                        ByteBuffer IDCheckReceiverBuffer = ByteBuffer.allocate(1048576);
                        client.receive(IDCheckReceiverBuffer);
                        ObjectReceiver IDCheckReceiver = new ObjectReceiver(IDCheckReceiverBuffer);

                        if (IDCheckReceiver.unpack().getCase()){
                            Addin updateAdd = new Addin(Long.parseLong(updateIn), today);
                            Transfer updateTransfer = new Transfer();
                            updateTransfer.setCommand(clientCommand);
                            updateTransfer.setSpaceMarine((SpaceMarine) updateAdd.adding());
                            updateTransfer.setID(Long.parseLong(updateIn));

                            ObjectSender updateSender = new ObjectSender(updateTransfer);
                            updateSender.send(client, serverAddress);
                            System.out.println("Object successfully updated");
                        }else {
                            System.out.println("There is no object with such ID");
                        }

                        break;
                    case "remove_by_id":
                        Transfer removeByIDTransfer = new Transfer();
                        removeByIDTransfer.setCommand(clientCommand);
                        System.out.println("Enter ID of the object to remove");
                        removeByIDTransfer.setID(Long.parseLong(scanner.nextLine()));

                        ObjectSender removeByIDSender = new ObjectSender(removeByIDTransfer);
                        removeByIDSender.send(client, serverAddress);
                        break;
                    case "clear":
                        Transfer clearTransfer = new Transfer();
                        clearTransfer.setCommand(clientCommand);

                        ObjectSender clearSender = new ObjectSender(clearTransfer);
                        clearSender.send(client, serverAddress);
                    case "history":
                        Transfer historyTransfer = new Transfer();
                        historyTransfer.setCommand(clientCommand);

                        ObjectSender historySender = new ObjectSender(historyTransfer);
                        historySender.send(client, serverAddress);

                        ByteBuffer historyReceiverBuffer = ByteBuffer.allocate(1024);
                        client.receive(historyReceiverBuffer);
                        historyReceiverBuffer.flip();
                        int historyInLimits = historyReceiverBuffer.limit();
                        byte[] historyInBytes = new byte[historyInLimits];
                        historyReceiverBuffer.get(historyInBytes, 0, historyInLimits);
                        String historyIn = new String(historyInBytes);

                        System.out.println(historyIn);
                        break;
                    case "add_if_max":
                        Transfer addIfMaxIDClaimTransfer = new Transfer();
                        addIfMaxIDClaimTransfer.setCommand("IDClaim");

                        ObjectSender addIfMaxIDClaimSender = new ObjectSender(addIfMaxIDClaimTransfer);
                        addIfMaxIDClaimSender.send(client, serverAddress);

                        ByteBuffer addIfMaxIDClaimReceiverBuffer = ByteBuffer.allocate(1048576);
                        client.receive(addIfMaxIDClaimReceiverBuffer);
                        ObjectReceiver addIfMaxIDClaimReceiver = new ObjectReceiver(addIfMaxIDClaimReceiverBuffer);

                        long addIfMaxTempID = addIfMaxIDClaimReceiver.unpack().getID();

                        Addin addIfMaxAddIn = new Addin(addIfMaxTempID,today);

                        Transfer addIfMaxTransfer = new Transfer();
                        addIfMaxTransfer.setCommand(clientCommand);
                        addIfMaxTransfer.setSpaceMarine((SpaceMarine) addIfMaxAddIn.adding());

                        ObjectSender addIfMaxSender = new ObjectSender(addIfMaxTransfer);
                        addIfMaxSender.send(client, serverAddress);

                        ByteBuffer addIfMaxCaseBuffer = ByteBuffer.allocate(1048576);
                        client.receive(addIfMaxCaseBuffer);
                        ObjectReceiver addIfMaxCaseReceiver = new ObjectReceiver(addIfMaxCaseBuffer);
                        boolean addIfMaxCase = addIfMaxCaseReceiver.unpack().getCase();

                        if (addIfMaxCase){
                            System.out.println("Object successfully added");
                        } else {
                            System.out.println("Object is too small");
                        }
                        break;
                    case "remove_greater":
                        Transfer removeGreaterIDClaimTransfer = new Transfer();
                        removeGreaterIDClaimTransfer.setCommand("IDClaim");

                        ObjectSender removeGreaterIDClaimSender = new ObjectSender(removeGreaterIDClaimTransfer);
                        removeGreaterIDClaimSender.send(client, serverAddress);

                        ByteBuffer removeGreaterIDClaimReceiverBuffer = ByteBuffer.allocate(1048576);
                        client.receive(removeGreaterIDClaimReceiverBuffer);
                        ObjectReceiver removeGreaterIDClaimReceiver = new ObjectReceiver(removeGreaterIDClaimReceiverBuffer);

                        long removeGreaterTempID = removeGreaterIDClaimReceiver.unpack().getID();

                        Addin removeGreaterAddIn = new Addin(removeGreaterTempID,today);

                        Transfer removeGreaterTransfer = new Transfer();
                        removeGreaterTransfer.setCommand(clientCommand);
                        removeGreaterTransfer.setSpaceMarine((SpaceMarine) removeGreaterAddIn.adding());

                        ObjectSender removeGreaterSender = new ObjectSender(removeGreaterTransfer);
                        removeGreaterSender.send(client, serverAddress);
                        break;
                    case "filter_starts_with_name":
                        System.out.println("Enter the pattern");
                        String filterStartsWithPattern = scanner.nextLine();

                        Transfer filterStartsWithTransfer = new Transfer();
                        filterStartsWithTransfer.setCommand(clientCommand);
                        filterStartsWithTransfer.setString(filterStartsWithPattern);

                        ObjectSender filterStartsWithSender = new ObjectSender(filterStartsWithTransfer);
                        filterStartsWithSender.send(client, serverAddress);

                        ByteBuffer filterStartsWithNumberBuffer = ByteBuffer.allocate(1048576);
                        client.receive(filterStartsWithNumberBuffer);
                        ObjectReceiver filterStartsWithNumberReceiver = new ObjectReceiver(filterStartsWithNumberBuffer);
                        Long[] filterStartsWithTempArr = new Long[filterStartsWithNumberReceiver.unpack().getNumber()];

                        Arrays.stream(filterStartsWithTempArr).forEach(e->{
                            try {
                                ByteBuffer filterStartsWithReceiverBuffer = ByteBuffer.allocate(1048576);
                                client.receive(filterStartsWithReceiverBuffer);

                                ObjectReceiver filterStartsWithReceiver = new ObjectReceiver(filterStartsWithReceiverBuffer);
                                System.out.println(filterStartsWithReceiver.unpack().getString());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                        break;
                    case "filter_less_than_health":
                        System.out.println("Enter health level");
                        Transfer filterLessThanHealthTransfer = new Transfer();
                        filterLessThanHealthTransfer.setCommand(clientCommand);
                        filterLessThanHealthTransfer.setNumber(Integer.parseInt(scanner.nextLine()));

                        ObjectSender filterLessThanHealthSender = new ObjectSender(filterLessThanHealthTransfer);
                        filterLessThanHealthSender.send(client, serverAddress);

                        ByteBuffer filterLessThanHealthNumberBuffer = ByteBuffer.allocate(1048576);
                        client.receive(filterLessThanHealthNumberBuffer);
                        ObjectReceiver filterLessThanHealthNumberReceiver = new ObjectReceiver(filterLessThanHealthNumberBuffer);
                        Long[] filterLessThanHealthTempArr = new Long[filterLessThanHealthNumberReceiver.unpack().getNumber()];

                        Arrays.stream(filterLessThanHealthTempArr).forEach(e->{
                            try {
                                ByteBuffer filterLessThanHealthReceiverBuffer = ByteBuffer.allocate(1048576);
                                client.receive(filterLessThanHealthReceiverBuffer);

                                ObjectReceiver filterLessThanHealthReceiver = new ObjectReceiver(filterLessThanHealthReceiverBuffer);
                                System.out.println(filterLessThanHealthReceiver.unpack().getString());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                        break;
                    case "filter_greater_than_chapter":
                        System.out.println("Enter the value");
                        Transfer filterGreaterThanChapterTransfer = new Transfer();
                        filterGreaterThanChapterTransfer.setCommand(clientCommand);
                        filterGreaterThanChapterTransfer.setNumber(Integer.parseInt(scanner.nextLine()));

                        ObjectSender filterGreaterThanChapterSender = new ObjectSender(filterGreaterThanChapterTransfer);
                        filterGreaterThanChapterSender.send(client, serverAddress);

                        ByteBuffer filterGreaterThanChapterNumberBuffer = ByteBuffer.allocate(1048576);
                        client.receive(filterGreaterThanChapterNumberBuffer);
                        ObjectReceiver filterGreaterThanChapterNumberReceiver = new ObjectReceiver(filterGreaterThanChapterNumberBuffer);
                        Long[] filterGreaterThanChapterTempArr = new Long[filterGreaterThanChapterNumberReceiver.unpack().getNumber()];

                        Arrays.stream(filterGreaterThanChapterTempArr).forEach(e->{
                            try {
                                ByteBuffer filterGreaterThanChapterReceiverBuffer = ByteBuffer.allocate(1048576);
                                client.receive(filterGreaterThanChapterReceiverBuffer);

                                ObjectReceiver filterGreaterThanChapterReceiver = new ObjectReceiver(filterGreaterThanChapterReceiverBuffer);
                                System.out.println(filterGreaterThanChapterReceiver.unpack().getString());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                        break;
                    case "execute_script":
                        TreeSet callCollectionSet = new TreeSet();
                        Transfer callCollectionTransfer = new Transfer();
                        callCollectionTransfer.setCommand("callCollection");
                        ObjectSender callCollectionSender = new ObjectSender(callCollectionTransfer);
                        callCollectionSender.send(client, serverAddress);

                        System.out.println("sxdtghsdgfhsdgfh");

                        ByteBuffer callCollectionNumberBuffer = ByteBuffer.allocate(1048576);
                        client.receive(callCollectionNumberBuffer);
                        ObjectReceiver callCollectionNumberReceiver = new ObjectReceiver(callCollectionNumberBuffer);
                        Long[] callCollectionTempArr = new Long[callCollectionNumberReceiver.unpack().getNumber()];

                        System.out.println(callCollectionNumberReceiver.unpack().getNumber());

                        Arrays.stream(callCollectionTempArr).forEach(e->{
                            try {
                                ByteBuffer callCollectionBuffer = ByteBuffer.allocate(1048576);
                                client.receive(callCollectionBuffer);

                                ObjectReceiver callCollectionReceiver = new ObjectReceiver(callCollectionBuffer);
                                callCollectionSet.add(callCollectionReceiver.unpack().getSpaceMarine());
                                callCollectionReceiver.unpack().getSpaceMarine().Test();
                            } catch (IOException ex){
                                ex.printStackTrace();
                            }
                        });

                        System.out.println("Enter the file name");
                        String scrname = scanner.nextLine();

                        Execute_script execute_script = new Execute_script(scrname);
                        execute_script.execute(callCollectionSet ,callCollectionNumberReceiver.unpack().getID(),today, client, serverAddress);

                        break;
                    case "hlt":
                        Transfer hltTransfer = new Transfer();
                        hltTransfer.setCommand(clientCommand);
                        ObjectSender hltSender = new ObjectSender(hltTransfer);
                        hltSender.send(client, serverAddress);
                        break;
                    default:
                        System.out.println("invalid command input. Please try again.");
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (serverCoin == false) {
                System.out.println("Server is offline, try again later");
            } else {
                System.out.println("Server lost");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

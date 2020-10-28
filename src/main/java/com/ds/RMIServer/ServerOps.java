package com.ds.RMIServer;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.ds.Auth.AES_encryption;
import com.ds.Auth.User;
import com.ds.InterfaceRmiServer.RMIInterface;


public class ServerOps extends UnicastRemoteObject implements RMIInterface {
    
    private static final long serialVersionUID = 1L;
    private static final int PORT = 2020;
    private List<Printer> printers;
    private User validatedUser;
    private boolean loggedin;
    private List<User> users;
    private UserFile userFile;
    private AES_encryption aes;

    protected ServerOps(List<Printer> printers) throws RemoteException, Exception {
        super(PORT);
        this.printers = printers;
        this.loggedin = false;
        this.aes = new AES_encryption();

        this.userFile = new UserFile("src/users.txt");
        this.userFile.writeUsersToFile(this.userFile.genMockUsers());
        this.users = this.userFile.readUsersFromFile("src/users.txt");

    }

    @Override
    public String print(String filename, String printer) throws RemoteException {
        if(!loggedin) {
            throw new SecurityException("Not authorized");
        }
        System.err.println("Print function requested by: " + validatedUser.getUsername());
        System.err.println("Adding file to printer: " + printer);
        for (Printer p : printers) {
            if (p.getName().equals(printer)) {
                System.err.println("Adding file: " + filename + " To printer: " + printer);
                Deque<String> q = p.getQueue();
                q.add(filename);
                p.setQueue(q);
                System.err.println("New queue" + p.getQueue());
            }
        }
        return "Job: " + filename + " added to printer: " + printer;
    }

    @Override
    public String queue(String printer) throws RemoteException {
        if(!loggedin) {
            throw new SecurityException("Not authorized");
        }
        System.err.println("Queue function requested by: " + validatedUser.getUsername());
        Queue<String> q = new LinkedList<>();
        String ret = "";
        for (Printer p : printers) {
            if (p.getName().equals(printer)) {
                System.err.println("Fetching queue from: " + printer);
                q = p.getQueue();
                String j = "";
                for (int i = 0; i < p.getQueue().size(); i++) {
                    j = q.poll();
                    ret = ret + "<Job number: " + i + ">, <" + j + ">, \n";
                    q.add(j);
                }
            }
        }
        return ret;
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        if(!loggedin) {
            throw new SecurityException("Not authorized");
        }
        System.err.println("ReadConfig function requested by: " + validatedUser.getUsername());
        return "This is the config";
    }

    @Override
    public void restart() throws RemoteException {
        if(!loggedin) {
            throw new SecurityException("Not authorized");
        }
        System.err.println("Restart function requested by: " + validatedUser.getUsername());
        System.err.println("restart");
    }

    @Override
    public void setConfig(String parameter, String value) throws RemoteException {
        if(!loggedin) {
            throw new SecurityException("Not authorized");
        }
        System.err.println("setConfig function requested by: " + validatedUser.getUsername());
        System.err.println("Set config");
    }

    @Override
    public void start() throws RemoteException {
        if(!loggedin) {
            throw new SecurityException("Not authorized");
        }
        System.err.println("Start function requested by: " + validatedUser.getUsername());
        System.err.println("Start");
    }

    @Override
    public String status(String printer) throws RemoteException {
        if(!loggedin) {
            throw new SecurityException("Not authorized");
        }
        System.err.println("Status function requested by: " + validatedUser.getUsername());
        String status = "";
        for (Printer p : printers) {
            if (p.getName().equals(printer)) {
                System.err.println("Fetching Status from: " + printer);
                status = p.getStatus();
            }
        }
        return status;
    }

    @Override
    public void stop() throws RemoteException {
        if(!loggedin) {
            throw new SecurityException("Not authorized");
        }
        System.err.println("Stop function requested by: " + validatedUser.getUsername());
        System.err.println("Stop");
    }

    @Override
    public boolean topQueue(String printer, int job) throws RemoteException {
        if(!loggedin) {
            throw new SecurityException("Not authorized");
        }
        System.err.println("topQueue function requested by: " + validatedUser.getUsername());
        Deque<String> q = new LinkedList<>();
        Deque<String> new_q = new LinkedList<>();

        for (Printer p : printers) {
            if (p.getName().equals(printer)) {
                System.err.println("Fetching queue from: " + printer);
                System.err.println("Exiting query: " + p.getQueue() + ". Job: " + job + " should be on top");
                q = p.getQueue();
                String j = "";
                int size = p.getQueue().size();
                for (int i = 0; i < size; i++) {
                    j = q.poll();
                    if (i == job) {
                        new_q.addFirst(j);
                    } else {
                        new_q.addLast(j);
                    }
                }
                p.setQueue(new_q);
                System.err.println("New query: " + p.getQueue());
            }
        }
        return true;
    }
    
    @Override
    public boolean auth(String username, String password) throws RemoteException {
        System.err.println("The following users try to login: " + username);
        for(User user: users) {
            if(username.equals(user.getUsername())){
                System.err.println("User Found: " + user.getUsername());
                if(aes.validatePassword(password, user.getSalt(), user.getEncryptedPassword())) {
                    System.err.println("Valid password");
                    loggedin = true;
                    validatedUser = user;
                } else {
                    System.err.println("Invalid password");
                    loggedin = false;
                }
            } 
        }
        if(!loggedin) {
            System.err.println("User could not login: " + username); 
        }
        return loggedin;
    }

    private static List<Printer> iniList() {
        List<Printer> list = new ArrayList<>();
        list.add(new Printer("p1", "Ready"));
        list.add(new Printer("p2", "Ilde"));
        list.add(new Printer("p3", "Dead"));
        return list;
    }

    public static void main(String[] args) {

        try {
            LocateRegistry.createRegistry(2020);
            ServerOps server = new ServerOps(iniList());

            Naming.rebind("//localhost:2020/PrintServer", server);
            System.err.println("PrintServer bound to registry on port 2020");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}



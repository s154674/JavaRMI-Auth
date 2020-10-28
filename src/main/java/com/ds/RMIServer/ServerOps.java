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

import com.ds.InterfaceRmiServer.RMIInterface;

public class ServerOps extends UnicastRemoteObject implements RMIInterface {

    private static final long serialVersionUID = 1L;
    private List<Printer> printers;

    protected ServerOps(List<Printer> printers) throws RemoteException {
        super();
        this.printers = printers;
    }

    @Override
    public String print(String filename, String printer) throws RemoteException {
        System.err.println("Adding file to printer: " + printer);

        for(Printer p : printers) {
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
        Queue<String> q = new LinkedList<>();
        String ret = "";
        for(Printer p : printers) {
            if (p.getName().equals(printer)) {
                System.err.println("Fetching queue from: " + printer);
                q = p.getQueue();
                String j = "";
                for(int i = 0; i < p.getQueue().size(); i++) {
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
        return "This is the config";
    }

    @Override
    public void restart() throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setConfig(String parameter, String value) throws RemoteException {
        // TODO Auto-generated method stub
    }

    @Override
    public void start() throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public String status(String printer) throws RemoteException {
        String status = "";
        for(Printer p : printers) {
            if (p.getName().equals(printer)) {
                System.err.println("Fetching Status from: " + printer);
                status = p.getStatus();
            }
        }
        return status;
    }

    @Override
    public void stop() throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean topQueue(String printer, int job) throws RemoteException {
        Deque<String> q = new LinkedList<>();
        Deque<String> new_q = new LinkedList<>();

        for(Printer p : printers) {
            if (p.getName().equals(printer)) {
                System.err.println("Fetching queue from: " + printer);
                System.err.println("Exiting query: " + p.getQueue() + ". Job: " + job + " should be on top");
                q = p.getQueue();
                String j = "";
                int size = p.getQueue().size();
                for(int i = 0; i < size; i++) {
                    j = q.poll();
                    if(i == job){
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
            Naming.rebind("//localhost:2020/RMIServer", new ServerOps(iniList()));

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}



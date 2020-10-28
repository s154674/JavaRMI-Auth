package com.ds.RMIClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import com.ds.InterfaceRmiServer.RMIInterface;

public class ClientOps {
    
    private static RMIInterface server;

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        server = (RMIInterface) Naming.lookup("//localhost:2020/RMIServer");

        boolean Active = true;
        while(Active) {
            String opt = JOptionPane.showInputDialog("1: print, 2: queue, 3: topQueue, 4: start, 5: stop, 6: restart, 7: status, 8: readConfig, 9: setConfig");
            switch(opt){
                case "1":
                    String printer = JOptionPane.showInputDialog("Printer (p1, p2, p3)?");
                    String filename = JOptionPane.showInputDialog("filename?");
                    String response = server.print(filename, printer);
                    JOptionPane.showMessageDialog(null, response);
                    break;
                case "2":
                    printer = JOptionPane.showInputDialog("Printer (p1, p2, p3)?");
                    response = server.queue(printer);
                    JOptionPane.showMessageDialog(null, response);
                    break;
                case "3":
                    printer = JOptionPane.showInputDialog("Printer (p1, p2, p3)?");
                    int job = Integer.parseInt(JOptionPane.showInputDialog("job number?"));
                    boolean r3 = server.topQueue(printer, job);
                    JOptionPane.showMessageDialog(null, r3);
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    printer = JOptionPane.showInputDialog("Printer (p1, p2, p3)?");
                    response = server.status(printer);
                    JOptionPane.showMessageDialog(null, response);
                    break;
                case "8":
                    break;
                case "9":
                    break;
                case "q": 
                    Active = false;
                    break; 

            }

        }
        
        



 

    }
}

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
            boolean isloggedin = false;
            server = (RMIInterface) Naming.lookup("//localhost:2020/PrintServer");
            String username = "";
            String pass = "";

            while(!isloggedin) {
                username = JOptionPane.showInputDialog("Username");
                pass = JOptionPane.showInputDialog("Password");
                isloggedin = server.auth(username, pass);
                if(!isloggedin) {
                    JOptionPane.showMessageDialog(null, "Invalid credentials, try again! ");
                }
            }

            boolean Active = true;
            while(Active && isloggedin ) {
                String opt = JOptionPane.showInputDialog("1: print, 2: queue, 3: topQueue, 4: start, 5: stop, 6: restart, 7: status, 8: readConfig, 9: setConfig");
                switch(opt){
                    case "1":
                        String printer = JOptionPane.showInputDialog("Printer (p1, p2, p3)?");
                        String filename = JOptionPane.showInputDialog("filename?");
                        try {
                        String response = server.print(filename, printer, username, pass);
                        JOptionPane.showMessageDialog(null, response);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        break;
                    case "2":
                        printer = JOptionPane.showInputDialog("Printer (p1, p2, p3)?");
                        try {
                        String response = server.queue(printer, username, pass);
                        JOptionPane.showMessageDialog(null, response);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        break;
                    case "3":
                        printer = JOptionPane.showInputDialog("Printer (p1, p2, p3)?");
                        int job = Integer.parseInt(JOptionPane.showInputDialog("job number?"));
                        try {
                        boolean r3 = server.topQueue(printer, job, username, pass);
                        JOptionPane.showMessageDialog(null, r3);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        break;
                    case "4":
                        try {
                        server.start(username, pass);
                        JOptionPane.showMessageDialog(null, "Server started");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        break;
                    case "5":
                        try {
                        server.stop(username, pass);
                        JOptionPane.showMessageDialog(null, "Server stopped");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        break;
                    case "6":
                        try {
                        server.restart(username, pass);
                        JOptionPane.showMessageDialog(null, "Server restarted");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        break;
                    case "7":
                        try {
                        printer = JOptionPane.showInputDialog("Printer (p1, p2, p3)?");
                        String response = server.status(printer, username, pass);
                        JOptionPane.showMessageDialog(null, response);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        break;
                    case "8":
                        try {
                        String r8 = server.readConfig("param", username, pass);
                        JOptionPane.showMessageDialog(null, r8);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        break;
                    case "9":
                         try {
                        server.setConfig("param", "bruh", username, pass);
                        JOptionPane.showMessageDialog(null, "Config updated");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        break;
                    case "q": 
                        Active = false;
                        break; 
                }
            }
    }
        
}

package com.ds.InterfaceRmiServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {

    public boolean auth(String username, String password) throws RemoteException;
    public String print(String filename, String printer, String username, String password)  throws RemoteException;  // prints file filename on the specified printer
    public String queue(String printer, String username, String password) throws RemoteException;                   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
    public boolean topQueue(String printer, int job, String username, String password) throws RemoteException;           // moves job to the top of the queue
    public void start(String username, String password) throws RemoteException;                                     // starts the print server
    public void stop(String username, String password) throws RemoteException;                                      // stops the print server
    public void restart(String username, String password) throws RemoteException;                                   // stops the print server, clears the print queue and starts the print server again
    public String status(String printer, String username, String password) throws RemoteException;                    // prints status of printer on the user's display
    public String readConfig(String parameter, String username, String password) throws RemoteException;              // prints the value of the parameter on the user's display
    public void setConfig(String parameter, String value, String username, String password) throws RemoteException;   // sets the parameter to value
}

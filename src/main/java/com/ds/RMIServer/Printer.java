package com.ds.RMIServer;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;


public class Printer implements Serializable {

    private static final long serialVersionUID = 2;
    private String name;
    private String status;
    private Deque<String> queue;
    
    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Printer(String name, String status) {
        this.name = name;
        this.status = status;
        this.queue = new LinkedList<>();
    }

    public Deque<String> getQueue() {
        return queue;
    }

    public void setQueue(Deque<String> queue) {
        this.queue = queue;
    }






    
}

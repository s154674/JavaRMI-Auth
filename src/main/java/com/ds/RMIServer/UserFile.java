package com.ds.RMIServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.ds.Auth.User;

public class UserFile {
    
    private String usersFilepath;

    protected UserFile(String usersFilepath) throws Exception {
        super();
        this.usersFilepath = usersFilepath;
    }


    public void writeUsersToFile(List<User> users) {
        try {
            //clear contents
            new FileOutputStream(usersFilepath).close();

            FileOutputStream fileOut = new FileOutputStream(usersFilepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            for (Object user : users) {
                try {
                    objectOut.writeObject(user);
                } catch (NotSerializableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                objectOut.writeObject(null);
            } catch (NotSerializableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> readUsersFromFile(String path) {
        List<User> userList = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream(path);
            boolean cont = true;
            try {
                ObjectInputStream input = new ObjectInputStream(fileIn);
                while (cont) {
                    Object obj = input.readObject();
                    if (obj != null)
                        userList.add((User) obj);
                    else
                        cont = false;
                }
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public List<User> genMockUsers() {
        List<User> tempUserList = new ArrayList<>();
        tempUserList.add(new User("Alice","al123"));
        tempUserList.add(new User("Bob","bo123"));
        tempUserList.add(new User("Connie","co123"));
        tempUserList.add(new User("Ahmed","lars2020"));
        tempUserList.add(new User("Admin","admin1234"));
        tempUserList.add(new User("Johnny","FitnessIsMyPassion2020"));
        tempUserList.add(new User("Jonathan","ViSesDrenge#PH"));
        tempUserList.add(new User("John","Doe#¤%&92kjGH03"));
        return tempUserList;
    } 

}

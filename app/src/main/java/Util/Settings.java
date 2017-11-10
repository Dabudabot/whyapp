package Util;

import group_6_model_sequential.Content;
import group_6_model_sequential.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Group-6 on 07.11.17.
 * Current machine status holder
 * SINGLETON CLASS
 */
public class Settings {

    private static volatile Settings instance;
    private Integer currentId;
    private Map<Integer, Content> contents;
    private Map<Integer, User> users;

    public static Settings getInstance() {
        Settings localInstance = instance;
        if (localInstance == null) {
            synchronized (Settings.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Settings();
                }
            }
        }
        return localInstance;
    }

    private Settings() {
        contents = new HashMap<>();
        users = new HashMap<>();
    }

    public Integer getCurrentId() {
        return currentId;
    }

    public void setCurrentId(Integer currentId) {
        this.currentId = currentId;
    }

    public Map<Integer, Content> getContents() {
        return contents;
    }

    public void setContents(Map<Integer, Content> contents) {
        this.contents = contents;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public void setUsers(Map<Integer, User> users) {
        this.users = users;
    }

    public Integer generateCurrentId() {
        return (int) (System.currentTimeMillis() / 1000);
    }
}
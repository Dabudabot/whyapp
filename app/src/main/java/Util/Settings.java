package Util;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

import eventb_prelude.BRelation;
import eventb_prelude.Pair;
import group_6_model_sequential.Content;
import group_6_model_sequential.MachineWrapper;
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
    private User currentUser;
    private Map<Integer, Content> contents;
    private Map<Integer, User> users;
    private MachineWrapper myMachine;
    private Map<Integer, MachineWrapper> machines;
    private MachineWrapper mergedMachine;

    private ChildEventListener newUserListener;
    private ChildEventListener newContentListener;
    private ChildEventListener newMachineListener;
    private Map<Integer, ValueEventListener> machineChangeListeners;

    private int idGen;

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
        machines = new HashMap<>();
        machineChangeListeners = new HashMap<>();
        idGen = 0;
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

    public int getIdGen() {
        return idGen;
    }

    public void setIdGen(int id) {
        if (id >= 100) {
            System.exit(100);
        }
        this.idGen = id;
    }

    public Map<Integer, MachineWrapper> getMachines() {
        return machines;
    }

    public void putMachines(Integer key, MachineWrapper machine) {
        machines.put(key, machine);
        this.mergedMachine = SimpleMapper.merge(myMachine, machines.values());
    }

    public void setMachines(Map<Integer, MachineWrapper> machines) {
        this.machines = machines;
    }

    public ChildEventListener getNewUserListener() {
        return newUserListener;
    }

    public void setNewUserListener(ChildEventListener newUserListener) {
        this.newUserListener = newUserListener;
    }

    public ChildEventListener getNewContentListener() {
        return newContentListener;
    }

    public void setNewContentListener(ChildEventListener newContentListener) {
        this.newContentListener = newContentListener;
    }

    public ChildEventListener getNewMachineListener() {
        return newMachineListener;
    }

    public void setNewMachineListener(ChildEventListener newMachineListener) {
        this.newMachineListener = newMachineListener;
    }

    public Map<Integer, ValueEventListener> getMachineChangeListeners() {
        return machineChangeListeners;
    }

    public void setMachineChangeListeners(Map<Integer, ValueEventListener> machineChangeListeners) {
        this.machineChangeListeners = machineChangeListeners;
    }

    public MachineWrapper getMyMachine() {
        return myMachine;
    }

    public void setMyMachine(MachineWrapper myMachine) {
        this.myMachine = myMachine;
    }

    public void merge() {
        this.mergedMachine = SimpleMapper.merge(myMachine, machines.values());
    }

    public MachineWrapper getMergedMachine() {
        //merge();
        return mergedMachine;
    }

    public void setMergedMachine(MachineWrapper mergedMachine) {
        this.mergedMachine = mergedMachine;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

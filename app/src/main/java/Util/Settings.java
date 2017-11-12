package Util;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import com.inno.dabudabot.whyapp.controller.ReceiveContentController;
import com.inno.dabudabot.whyapp.controller.listing.GetListingsController;
import com.inno.dabudabot.whyapp.controller.sync.GetChatContentController;
import com.inno.dabudabot.whyapp.listener.GetChatContentView;
import com.inno.dabudabot.whyapp.listener.ReceiveContentView;

import eventb_prelude.BRelation;
import eventb_prelude.Pair;
import group_6_model_sequential.Content;
import group_6_model_sequential.machine3;
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
    private machine3 machine;

    private ChildEventListener newUserListener;
    private ChildEventListener addContentListener;
    private ValueEventListener chatcontentChangeListener;
    private ValueEventListener mutedChangeListener;

    private int idGen;
    private volatile Boolean busy;

    private Integer forwardingContent;
    private Content broadcastingContent;

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
        idGen = 0;
        busy = false;
    }

    public void commitMachine() {
        SimpleMapper.toDatabaseReference(machine);
    }

    public void commitMachine(machine3 machine) {
        this.machine = machine;
        SimpleMapper.toDatabaseReference(machine);
    }

    public void uptDataMachine(machine3 machine) {
        this.machine.set_chatcontent(machine.get_chatcontent());
        this.machine.set_chat(machine.get_chat());
        this.machine.set_chatcontentseq(machine.get_chatcontentseq());
        this.machine.set_content(machine.get_content());
        this.machine.set_contentsize(machine.get_contentsize());
        this.machine.set_owner(machine.get_owner());
        this.machine.set_toread(machine.get_toread());
        this.machine.set_toreadcon(machine.get_toreadcon());
    }

    public void uptMuted(BRelation<Integer, Integer> muted) {
        machine.set_muted(muted);
    }

    public void uptUser(Integer userId) {
        machine.get_user().add(userId);
    }

    public void uptContent(Integer contentId) {
        machine.get_content().add(contentId);
        for (Pair<Integer, Integer> p : machine.get_owner()) {
            if (p.fst().equals(contentId)) {
                ReceiveContentController.sendNotify(
                        Settings.getInstance().getCurrentUser().getId(),
                        p.snd());
            }
        }
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

    public ChildEventListener getNewUserListener() {
        return newUserListener;
    }

    public void setNewUserListener(ChildEventListener newUserListener) {
        this.newUserListener = newUserListener;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public machine3 getMachine() {
        return machine;
    }

    public void setMachine(machine3 machine) {
        this.machine = machine;
    }

    public Boolean getBusy() {
        return busy;
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    public ValueEventListener getChatcontentChangeListener() {
        return chatcontentChangeListener;
    }

    public void setChatcontentChangeListener(ValueEventListener chatcontentChangeListener) {
        this.chatcontentChangeListener = chatcontentChangeListener;
    }

    public ValueEventListener getMutedChangeListener() {
        return mutedChangeListener;
    }

    public void setMutedChangeListener(ValueEventListener mutedChangeListener) {
        this.mutedChangeListener = mutedChangeListener;
    }

    public Integer getForwardingContent() {
        return forwardingContent;
    }

    public void setForwardingContent(Integer forwardingContent) {
        this.forwardingContent = forwardingContent;
    }

    public Content getBroadcastingContent() {
        return broadcastingContent;
    }

    public void setBroadcastingContent(Content broadcastingContent) {
        this.broadcastingContent = broadcastingContent;
    }

    public ChildEventListener getAddContentListener() {
        return addContentListener;
    }

    public void setAddContentListener(ChildEventListener addContentListener) {
        this.addContentListener = addContentListener;
    }
}

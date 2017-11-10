package Util;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import eventb_prelude.BRelation;
import eventb_prelude.BSet;
import eventb_prelude.Pair;
import group_6_model_sequential.MachineWrapper;

/**
 * Created by Group-6 on 08.11.17.
 * machine mapper to firebase
 */
public class SimpleMapper {

    private static Pair<Integer, Integer> toBPair(DataSnapshot dataSnapshot) {
        Integer fst = dataSnapshot.child(Constants.NODE_FST)
                .getValue(Integer.class);
        Integer snd = dataSnapshot.child(Constants.NODE_SND)
                .getValue(Integer.class);
        return new Pair<>(fst, snd);
    }

    private static Pair<Integer, BRelation<Integer, Integer>> toBPairBRelation(DataSnapshot dataSnapshot) {
        Integer fst = dataSnapshot.child(Constants.NODE_FST)
                .getValue(Integer.class);
        Iterator<DataSnapshot> iSnd = dataSnapshot.child(Constants.NODE_SND)
                .getChildren().iterator();
        BRelation<Integer, Integer> snd = new BRelation<>();
        while (iSnd.hasNext()) {
            DataSnapshot child = iSnd.next();
            Pair<Integer, Integer> cPair = toBPair(child);
            snd.add(cPair);
        }

        return new Pair<>(fst, snd);
    }

    private static Pair<Integer, BRelation<Integer, BRelation<Integer, Integer>>> toBPairBRelationRelation(DataSnapshot dataSnapshot) {
        Integer fst = dataSnapshot.child(Constants.NODE_FST)
                .getValue(Integer.class);
        Iterator<DataSnapshot> iSnd = dataSnapshot.child(Constants.NODE_SND)
                .getChildren().iterator();
        BRelation<Integer, BRelation<Integer, Integer>> snd = new BRelation<>();
        while (iSnd.hasNext()) {
            DataSnapshot child = iSnd.next();
            Pair<Integer, BRelation<Integer, Integer>> cPair = toBPairBRelation(child);
            snd.add(cPair);
        }

        return new Pair<>(fst, snd);
    }

    private static BSet<Integer> toBSet(DataSnapshot dataSnapshot, String node) {
        if (dataSnapshot.hasChild(node)) {
            Iterator<DataSnapshot> ints = dataSnapshot.getChildren().iterator();
            BSet<Integer> result = new BSet<>();
            while (ints.hasNext()) {
                DataSnapshot child = ints.next();
                result.add(child.getValue(Integer.class));
            }
            return result;
        } else {
            return new BSet<>();
        }
    }

    private static BRelation<Integer, Integer> toBRelation(DataSnapshot dataSnapshot,
                                                    String node) {
        if (dataSnapshot.hasChild(node)) {
            Iterator<DataSnapshot> pairs =
                    dataSnapshot.child(node).getChildren().iterator();
            BRelation<Integer, Integer> result = new BRelation<>();
            while (pairs.hasNext()) {
                DataSnapshot child = pairs.next();
                Pair<Integer, Integer> cPair = toBPair(child);
                result.add(cPair);
            }
            return result;
        } else {
            return new BRelation<>();
        }
    }

    private static BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> toBRelationRelationRelation(DataSnapshot dataSnapshot, String node) {
        if (dataSnapshot.hasChild(node)) {
            Iterator<DataSnapshot> pairs =
                    dataSnapshot.child(node).getChildren().iterator();
            BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> result = new BRelation<>();
            while (pairs.hasNext()) {
                DataSnapshot child = pairs.next();
                Pair<Integer, BRelation<Integer, BRelation<Integer, Integer>>> cPair =
                        toBPairBRelationRelation(child);
                result.add(cPair);
            }
            return result;
        } else {
            return new BRelation<>();
        }
    }

    private static BRelation<Integer, BRelation<Integer, Integer>> toBRelationRelation(DataSnapshot dataSnapshot, String node) {
        if (dataSnapshot.hasChild(node)) {
            Iterator<DataSnapshot> pairs = dataSnapshot.child(node).getChildren().iterator();
            BRelation<Integer, BRelation<Integer, Integer>> result = new BRelation<>();
            while (pairs.hasNext()) {
                DataSnapshot child = pairs.next();
                Pair<Integer, BRelation<Integer, Integer>> cPair =
                        toBPairBRelation(child);
                result.add(cPair);
            }
            return result;
        } else {
            return new BRelation<>();
        }
    }

    private static Integer toInteger(DataSnapshot dataSnapshot, String node) {
        if (dataSnapshot.hasChild(node)) {
            return dataSnapshot.child(node).getValue(Integer.class);
        } else {
            return 0;
        }
    }

    private static void fromBRelation(BRelation<Integer, Integer> relation,
                                      Integer currentId,
                                      String node) {
        int iterator = 0;
        for (Pair<Integer, Integer> pair : relation) {
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.NODE_MACHINES)
                    .child(String.valueOf(currentId))
                    .child(node)
                    .child(String.valueOf(iterator))
                    .child(Constants.NODE_FST)
                    .setValue(pair.fst());
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.NODE_MACHINES)
                    .child(String.valueOf(currentId))
                    .child(node)
                    .child(String.valueOf(iterator))
                    .child(Constants.NODE_SND)
                    .setValue(pair.snd());
            iterator++;
        }
    }

    private static void fromBRelationRelation(BRelation<Integer, BRelation<Integer, Integer>> relation,
                                                      Integer currentId,
                                                      String node) {
        int iterator = 0;
        for (Pair<Integer, BRelation<Integer, Integer>> pair : relation) {
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.NODE_MACHINES)
                    .child(String.valueOf(currentId))
                    .child(node)
                    .child(String.valueOf(iterator))
                    .child(Constants.NODE_FST)
                    .setValue(pair.fst());
            int iterator1 = 0;
            for (Pair<Integer, Integer> pair1 : pair.snd()) {
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child(Constants.NODE_MACHINES)
                        .child(String.valueOf(currentId))
                        .child(node)
                        .child(String.valueOf(iterator))
                        .child(Constants.NODE_SND)
                        .child(String.valueOf(iterator1))
                        .child(Constants.NODE_FST)
                        .setValue(pair1.fst());
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child(Constants.NODE_MACHINES)
                        .child(String.valueOf(currentId))
                        .child(node)
                        .child(String.valueOf(iterator))
                        .child(Constants.NODE_SND)
                        .child(String.valueOf(iterator1))
                        .child(Constants.NODE_SND)
                        .setValue(pair1.snd());
                iterator1++;
            }
            iterator++;
        }
    }

    private static void fromBRelationRelationRelation(BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> relation,
                                      Integer currentId,
                                      String node) {
        int iterator = 0;
        for (Pair<Integer, BRelation<Integer, BRelation<Integer, Integer>>> pair : relation) {
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.NODE_MACHINES)
                    .child(String.valueOf(currentId))
                    .child(node)
                    .child(String.valueOf(iterator))
                    .child(Constants.NODE_FST)
                    .setValue(pair.fst());
            int iterator1 = 0;
            for (Pair<Integer, BRelation<Integer, Integer>> pair1 : pair.snd()) {
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child(Constants.NODE_MACHINES)
                        .child(String.valueOf(currentId))
                        .child(node)
                        .child(String.valueOf(iterator))
                        .child(Constants.NODE_SND)
                        .child(String.valueOf(iterator1))
                        .child(Constants.NODE_FST)
                        .setValue(pair1.fst());
                int iterator2 = 0;
                for (Pair<Integer, Integer> pair2 : pair1.snd()) {
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child(Constants.NODE_MACHINES)
                            .child(String.valueOf(currentId))
                            .child(node)
                            .child(String.valueOf(iterator))
                            .child(Constants.NODE_SND)
                            .child(String.valueOf(iterator1))
                            .child(Constants.NODE_SND)
                            .child(String.valueOf(iterator2))
                            .child(Constants.NODE_FST)
                            .setValue(pair2.fst());
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child(Constants.NODE_MACHINES)
                            .child(String.valueOf(currentId))
                            .child(node)
                            .child(String.valueOf(iterator))
                            .child(Constants.NODE_SND)
                            .child(String.valueOf(iterator1))
                            .child(Constants.NODE_SND)
                            .child(String.valueOf(iterator2))
                            .child(Constants.NODE_SND)
                            .setValue(pair2.snd());
                    iterator2++;
                }
                iterator1++;
            }
            iterator++;
        }
    }

    private static void fromInteger(Integer value, Integer currentId, String node) {
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_MACHINES)
                .child(String.valueOf(currentId))
                .child(node)
                .setValue(value);
    }

    public static MachineWrapper toMachine(DataSnapshot dataSnapshot) {
        MachineWrapper machineWrapper = new MachineWrapper();
        machineWrapper.set_chat(toBRelation(dataSnapshot, Constants.NODE_CHAT));
        machineWrapper.set_active(toBRelation(dataSnapshot, Constants.NODE_ACTIVE));
        machineWrapper.set_muted(toBRelation(dataSnapshot, Constants.NODE_MUTED));
        machineWrapper.set_chatcontent(toBRelationRelationRelation(
                dataSnapshot, Constants.NODE_CHATCONTENT));
        machineWrapper.set_toread(toBRelation(dataSnapshot, Constants.NODE_TOREAD));
        machineWrapper.set_inactive(toBRelation(dataSnapshot, Constants.NODE_INACTIVE));
        machineWrapper.set_toreadcon(toBRelationRelation(dataSnapshot, Constants.NODE_TOREADCON));
        machineWrapper.set_owner(toBRelation(dataSnapshot, Constants.NODE_OWNER));
        machineWrapper.set_contentsize(toInteger(dataSnapshot, Constants.NODE_CONTENTSIZE));
        machineWrapper.set_chatcontentseq(toBRelationRelationRelation(dataSnapshot, Constants.NODE_CHATCONTENTSEQ));
        machineWrapper.set_readChatContentSeq(toBRelation(dataSnapshot, Constants.NODE_READCHATCONTENTSEQ));
        machineWrapper.set_content(toBSet(dataSnapshot, Constants.NODE_CONTENT));
        machineWrapper.set_content(toBSet(dataSnapshot, Constants.NODE_USER));

        return machineWrapper;
    }

    public static void toDatabaseReference(MachineWrapper machineWrapper, Integer currentId) {
        fromBRelation(machineWrapper.get_chat(), currentId, Constants.NODE_CHAT);
        fromBRelation(machineWrapper.get_active(), currentId, Constants.NODE_ACTIVE);
        fromBRelation(machineWrapper.get_muted(), currentId, Constants.NODE_MUTED);
        fromBRelationRelationRelation(machineWrapper.get_chatcontent(), currentId, Constants.NODE_CHATCONTENT);
        fromBRelation(machineWrapper.get_toread(), currentId, Constants.NODE_TOREAD);
        fromBRelation(machineWrapper.get_inactive(), currentId, Constants.NODE_INACTIVE);
        fromBRelationRelation(machineWrapper.get_toreadcon(), currentId, Constants.NODE_TOREADCON);
        fromBRelation(machineWrapper.get_owner(), currentId, Constants.NODE_OWNER);
        fromInteger(machineWrapper.get_contentsize(), currentId, Constants.NODE_CONTENTSIZE);
        fromBRelationRelationRelation(machineWrapper.get_chatcontentseq(), currentId, Constants.NODE_CHATCONTENTSEQ);
        fromBRelation(machineWrapper.get_readChatContentSeq(), currentId, Constants.NODE_READCHATCONTENTSEQ);
    }

    private static BRelation<Integer, Integer> mergeBRelation(BRelation<Integer, Integer> one,
                                                              Collection<BRelation<Integer, Integer>> sets) {
        BRelation<Integer, Integer> relation = new BRelation<>();
        relation.addAll(one);
        for (BRelation<Integer, Integer> bRelation : sets) {
            relation.addAll(bRelation);
        }
        return relation;
    }

    private static BRelation<Integer, BRelation<Integer, Integer>> mergeBRelationRelation(BRelation<Integer, BRelation<Integer, Integer>> one,
                                                                                          Collection<BRelation<Integer, BRelation<Integer, Integer>>> sets) {
        BRelation<Integer, BRelation<Integer, Integer>> relation = new BRelation<>();
        relation.addAll(one);
        for (BRelation<Integer, BRelation<Integer, Integer>> bRelation : sets) {
            relation.addAll(bRelation);
        }
        return relation;
    }

    private static BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> mergeBRelationRelationRelation(BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> one,
                                                                                          Collection<BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>>> sets) {
        BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> relation = new BRelation<>();
        relation.addAll(one);
        for (BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> bRelation : sets) {
            relation.addAll(bRelation);
        }
        return relation;
    }

    public static MachineWrapper merge(MachineWrapper one,
                                       Collection<MachineWrapper> machines) {
        MachineWrapper merged = new MachineWrapper();
//CRAP!
        Set<BRelation<Integer, Integer>> chats = new HashSet<>();
        for (MachineWrapper machineWrapper : machines) {
            chats.add(machineWrapper.get_chat());
        }
        Set<BRelation<Integer, Integer>> actives = new HashSet<>();
        for (MachineWrapper machineWrapper : machines) {
            actives.add(machineWrapper.get_active());
        }
        Set<BRelation<Integer, Integer>> muteds = new HashSet<>();
        for (MachineWrapper machineWrapper : machines) {
            muteds.add(machineWrapper.get_muted());
        }
        Set<BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>>> chatcontents = new HashSet<>();
        for (MachineWrapper machineWrapper : machines) {
            chatcontents.add(machineWrapper.get_chatcontent());
        }
        Set<BRelation<Integer, Integer>> toreads = new HashSet<>();
        for (MachineWrapper machineWrapper : machines) {
            toreads.add(machineWrapper.get_toread());
        }
        Set<BRelation<Integer, Integer>> inactives = new HashSet<>();
        for (MachineWrapper machineWrapper : machines) {
            inactives.add(machineWrapper.get_inactive());
        }
        Set<BRelation<Integer, BRelation<Integer, Integer>>> toreadcons = new HashSet<>();
        for (MachineWrapper machineWrapper : machines) {
            toreadcons.add(machineWrapper.get_toreadcon());
        }
        Set<BRelation<Integer, Integer>> owners = new HashSet<>();
        for (MachineWrapper machineWrapper : machines) {
            owners.add(machineWrapper.get_owner());
        }
        Integer cs = one.get_contentsize();
        for (MachineWrapper machineWrapper : machines) {
            cs += machineWrapper.get_contentsize();
        }
        Set<BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>>> chatcontentseq = new HashSet<>();
        for (MachineWrapper machineWrapper : machines) {
            chatcontentseq.add(machineWrapper.get_chatcontentseq());
        }
        Set<BRelation<Integer, Integer>> readChatcontentSeqs = new HashSet<>();
        for (MachineWrapper machineWrapper : machines) {
            readChatcontentSeqs.add(machineWrapper.get_readChatContentSeq());
        }

        merged.set_chat(mergeBRelation(one.get_chat(), chats));
        merged.set_active(mergeBRelation(one.get_active(), actives));
        merged.set_muted(mergeBRelation(one.get_muted(), muteds));
        merged.set_chatcontent(mergeBRelationRelationRelation(one.get_chatcontent(), chatcontents));
        merged.set_toread(mergeBRelation(one.get_toread(), toreads));
        merged.set_inactive(mergeBRelation(one.get_inactive(), inactives));
        merged.set_toreadcon(mergeBRelationRelation(one.get_toreadcon(), toreadcons));
        merged.set_owner(mergeBRelation(one.get_owner(), owners));
        merged.set_contentsize(cs);
        merged.set_chatcontentseq(mergeBRelationRelationRelation(one.get_chatcontentseq(), chatcontentseq));
        merged.set_readChatContentSeq(mergeBRelation(one.get_readChatContentSeq(), readChatcontentSeqs));

        BSet<Integer> users = new BSet<>();
        users.addAll(one.get_user());
        for (MachineWrapper machineWrapper : machines) {
            users.addAll(machineWrapper.get_user());
        }

        BSet<Integer> content = new BSet<>();
        content.addAll(one.get_content());
        for (MachineWrapper machineWrapper : machines) {
            content.addAll(machineWrapper.get_content());
        }

        merged.set_content(content);
        merged.set_user(users);
        //CRAP ENDS
        return merged;
    }
}

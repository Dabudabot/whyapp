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
import group_6_model_sequential.Content;
import group_6_model_sequential.machine3;
import group_6_model_sequential.User;

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
            Iterator<DataSnapshot> ints = dataSnapshot.child(node).getChildren().iterator();
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

    public static BRelation<Integer, Integer> toBRelation(DataSnapshot dataSnapshot) {
        Iterator<DataSnapshot> pairs =
                dataSnapshot.getChildren().iterator();
        BRelation<Integer, Integer> result = new BRelation<>();
        while (pairs.hasNext()) {
            DataSnapshot child = pairs.next();
            Pair<Integer, Integer> cPair = toBPair(child);
            result.add(cPair);
        }
        return result;
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

    public static BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> toBRelationRelationRelation(DataSnapshot dataSnapshot) {

        Iterator<DataSnapshot> pairs =
                dataSnapshot.getChildren().iterator();
        BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> result =
                new BRelation<>();
        while (pairs.hasNext()) {
            DataSnapshot child = pairs.next();
            Pair<Integer, BRelation<Integer, BRelation<Integer, Integer>>> cPair =
                    toBPairBRelationRelation(child);
            result.add(cPair);
        }
        return result;
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
                                      String node) {
        if (relation.isEmpty()) {
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.NODE_MACHINE)
                    .child(node)
                    .removeValue();
        }

        int iterator = 0;
        for (Pair<Integer, Integer> pair : relation) {
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.NODE_MACHINE)
                    .child(node)
                    .child(String.valueOf(iterator))
                    .child(Constants.NODE_FST)
                    .setValue(pair.fst());
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.NODE_MACHINE)
                    .child(node)
                    .child(String.valueOf(iterator))
                    .child(Constants.NODE_SND)
                    .setValue(pair.snd());
            iterator++;
        }
    }

    private static void fromBRelationRelation(BRelation<Integer, BRelation<Integer, Integer>> relation,
                                                      String node) {
        int iterator = 0;
        for (Pair<Integer, BRelation<Integer, Integer>> pair : relation) {
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.NODE_MACHINE)
                    .child(node)
                    .child(String.valueOf(iterator))
                    .child(Constants.NODE_FST)
                    .setValue(pair.fst());
            int iterator1 = 0;
            for (Pair<Integer, Integer> pair1 : pair.snd()) {
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child(Constants.NODE_MACHINE)
                        .child(node)
                        .child(String.valueOf(iterator))
                        .child(Constants.NODE_SND)
                        .child(String.valueOf(iterator1))
                        .child(Constants.NODE_FST)
                        .setValue(pair1.fst());
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child(Constants.NODE_MACHINE)
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
                                                  String node) {
        int iterator = 0;
        for (Pair<Integer, BRelation<Integer, BRelation<Integer, Integer>>> pair : relation) {
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.NODE_MACHINE)
                    .child(node)
                    .child(String.valueOf(iterator))
                    .child(Constants.NODE_FST)
                    .setValue(pair.fst());
            int iterator1 = 0;
            for (Pair<Integer, BRelation<Integer, Integer>> pair1 : pair.snd()) {
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child(Constants.NODE_MACHINE)
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
                            .child(Constants.NODE_MACHINE)
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
                            .child(Constants.NODE_MACHINE)
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

    private static void fromInteger(Integer value, String node) {
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_MACHINE)
                .child(node)
                .setValue(value);
    }

    public static machine3 toMachine(DataSnapshot dataSnapshot) {
        machine3 machine = new machine3();
        machine.set_chat(toBRelation(dataSnapshot, Constants.NODE_CHAT));
        machine.set_active(new BRelation<Integer, Integer>());
        machine.set_muted(toBRelation(dataSnapshot, Constants.NODE_MUTED));
        machine.set_chatcontent(toBRelationRelationRelation(
                dataSnapshot, Constants.NODE_CHATCONTENT));
        machine.set_toread(toBRelation(dataSnapshot, Constants.NODE_TOREAD));
        machine.set_inactive(new BRelation<Integer, Integer>());
        machine.set_toreadcon(toBRelationRelation(dataSnapshot, Constants.NODE_TOREADCON));
        machine.set_owner(toBRelation(dataSnapshot, Constants.NODE_OWNER));
        machine.set_contentsize(toInteger(dataSnapshot, Constants.NODE_CONTENTSIZE));
        machine.set_chatcontentseq(toBRelationRelationRelation(dataSnapshot, Constants.NODE_CHATCONTENTSEQ));
        machine.set_readChatContentSeq(new BRelation<Integer, Integer>());
        machine.set_content(toBSet(dataSnapshot, Constants.NODE_CONTENT));
        machine.set_user(toBSet(dataSnapshot, Constants.NODE_USER));

        return machine;
    }

    public static void toDatabaseReference(machine3 machine) {
        fromBRelation(machine.get_chat(), Constants.NODE_CHAT);
        fromBRelation(machine.get_active(), Constants.NODE_ACTIVE);
        fromBRelation(machine.get_muted(), Constants.NODE_MUTED);
        fromBRelationRelationRelation(machine.get_chatcontent(), Constants.NODE_CHATCONTENT);
        fromBRelation(machine.get_toread(), Constants.NODE_TOREAD);
        fromBRelation(machine.get_inactive(), Constants.NODE_INACTIVE);
        fromBRelationRelation(machine.get_toreadcon(), Constants.NODE_TOREADCON);
        fromBRelation(machine.get_owner(), Constants.NODE_OWNER);
        fromInteger(machine.get_contentsize(), Constants.NODE_CONTENTSIZE);
        fromBRelationRelationRelation(machine.get_chatcontentseq(), Constants.NODE_CHATCONTENTSEQ);
        fromBRelation(machine.get_readChatContentSeq(), Constants.NODE_READCHATCONTENTSEQ);
    }

    private static String fromBR(BRelation<Integer, Integer> rel, String node) {
        String result = "\"" + node + "\":[";

        StringBuilder builder = new StringBuilder();
        for (Pair<Integer, Integer> pair : rel) {
            builder.append("{\"fst\":");
            builder.append(pair.fst());
            builder.append(",\"snd\":");
            builder.append(pair.snd());
            builder.append("},");
        }
        try {
            builder.deleteCharAt(builder.length() - 1);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result + builder.toString()+ "]";
    }

    private static String fromBRR(BRelation<Integer, BRelation<Integer, Integer>> rel,
                                  String node) {

        String result = "\"" + node + "\":[";

        StringBuilder builder = new StringBuilder();
        for (Pair<Integer, BRelation<Integer, Integer>> pair : rel) {
            builder.append("{\"fst\":");
            builder.append(pair.fst());
            builder.append(",");
            builder.append(fromBR(pair.snd(), "snd"));
            //builder.deleteCharAt(builder.length()-1);
            builder.append("}],");
        }
        try {
            builder.deleteCharAt(builder.length() - 1);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result + builder.toString();
    }

    private static String fromBRRR(BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> rel,
                                  String node) {

        String result = "\"" + node + "\":[";

        StringBuilder builder = new StringBuilder();
        for (Pair<Integer, BRelation<Integer, BRelation<Integer, Integer>>> pair : rel) {
            builder.append("{\"fst\":");
            builder.append(pair.fst());
            builder.append(",");
            builder.append(fromBRR(pair.snd(), "snd"));
            //builder.deleteCharAt(builder.length()-1);
            builder.append("}],");
        }
        try {
            builder.deleteCharAt(builder.length() - 1);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result + builder.toString();
    }

    private static String fromBSet(BSet<Integer> rel, String node) {
        String result = "\"" + node + "\":[";

        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Integer val : rel) {
            builder.append("{" + String.valueOf(i) + ":");
            builder.append(val);
            builder.append("},");
            i++;
        }
        try {
            builder.deleteCharAt(builder.length() - 1);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result + builder.toString() + "]";
    }

    public static void toFirebaseString(machine3 machine) {

        String value = machineToString(machine);

        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_MACHINE)
                .child(Constants.NODE_DATA)
                .setValue(value);
    }

    public static String machineToString(machine3 machine) {
        StringBuilder result = new StringBuilder();

        System.out.println(fromBR(machine.get_chat(), ""));

        result.append("{");
        result.append(fromBR(machine.get_chat(), "chat") + ",");
        result.append(fromBRRR(machine.get_chatcontent(), "chatcontent") + ",");
        result.append(fromBRRR(machine.get_chatcontentseq(), "chatcontentseq") + ",");
        result.append(fromBSet(machine.get_content(), "content") + ",");
        result.append("\"contentsize\":" + String.valueOf(machine.get_contentsize()) + ",");
        result.append(fromBR(machine.get_owner(), "owner") + ",");
        result.append(fromBR(machine.get_toread(), "toread") + ",");
        result.append(fromBRR(machine.get_toreadcon(), "toreadcon")+ ",");
        result.append("}");

        return result.toString();
    }

    public static machine3 fromFirebaseString(
            machine3 machine, String value) {



        return machine;
    }


    public static void main(String[] args) {
        machine3 machine = new machine3();
        System.out.println(machineToString(machine));


        BRelation<Integer, Integer> rel = new BRelation<>();
        rel.add(new Pair<Integer, Integer>(1,2));
        rel.add(new Pair<Integer, Integer>(3,4));
        System.out.println(rel);
        System.out.println(fromBR(rel, ""));

        BRelation<Integer, BRelation<Integer, Integer>> rel_rel = new BRelation<>();
        rel_rel.add(new Pair<Integer, BRelation<Integer, Integer>>(10,rel));
        //rel_rel.add(new Pair<Integer, BRelation<Integer, Integer>>(20,rel));
        System.out.println(rel_rel);
        System.out.println(fromBRR(rel_rel, ""));

        BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> rel_rel_rel = new BRelation<>();
        rel_rel_rel.add(new Pair<Integer, BRelation<Integer, BRelation<Integer, Integer>>>(100,rel_rel));
        // 2nd
        System.out.println(rel_rel_rel);
        System.out.println(fromBRRR(rel_rel_rel, ""));

        BSet<Integer> set = new BSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        System.out.println(set);
        System.out.println(fromBSet(set, ""));
    }
}
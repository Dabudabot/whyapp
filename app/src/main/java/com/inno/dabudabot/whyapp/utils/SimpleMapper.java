package com.inno.dabudabot.whyapp.utils;

import com.google.firebase.database.DataSnapshot;

import java.util.Iterator;

import eventb_prelude.BRelation;
import eventb_prelude.BSet;
import eventb_prelude.Pair;

/**
 * Created by dabudabot on 08.11.17.
 */

public class SimpleMapper {

    public static Pair<Integer, Integer> toBPair(DataSnapshot dataSnapshot) {
        Integer fst = dataSnapshot.child(Constants.ARG_FST)
                .getValue(Integer.class);
        Integer snd = dataSnapshot.child(Constants.ARG_SND)
                .getValue(Integer.class);
        return new Pair<>(fst, snd);
    }

    public static Pair<Integer, BRelation<Integer, Integer>> toBPairBRelation(DataSnapshot dataSnapshot) {
        Integer fst = dataSnapshot.child(Constants.ARG_FST)
                .getValue(Integer.class);
        Iterator<DataSnapshot> iSnd = dataSnapshot.child(Constants.ARG_SND)
                .getChildren().iterator();
        BRelation<Integer, Integer> snd = new BRelation<>();
        while (iSnd.hasNext()) {
            DataSnapshot child = iSnd.next();
            Pair<Integer, Integer> cPair = toBPair(child);
            snd.add(cPair);
        }

        return new Pair<>(fst, snd);
    }

    public static Pair<Integer, BRelation<Integer, BRelation<Integer, Integer>>> toBPairBRelationRelation(DataSnapshot dataSnapshot) {
        Integer fst = dataSnapshot.child(Constants.ARG_FST)
                .getValue(Integer.class);
        Iterator<DataSnapshot> iSnd = dataSnapshot.child(Constants.ARG_SND)
                .getChildren().iterator();
        BRelation<Integer, BRelation<Integer, Integer>> snd = new BRelation<>();
        while (iSnd.hasNext()) {
            DataSnapshot child = iSnd.next();
            Pair<Integer, BRelation<Integer, Integer>> cPair = toBPairBRelation(child);
            snd.add(cPair);
        }

        return new Pair<>(fst, snd);
    }

    public static BRelation<Integer, Integer> toBRelation(DataSnapshot dataSnapshot) {
        Iterator<DataSnapshot> pairs = dataSnapshot.getChildren().iterator();
        BRelation<Integer, Integer> result = new BRelation<>();
        while (pairs.hasNext()) {
            DataSnapshot child = pairs.next();
            Pair<Integer, Integer> cPair = toBPair(child);
            result.add(cPair);
        }
        return result;
    }

    public static BRelation<Integer, BRelation<Integer, Integer>> toBRelationRelation(DataSnapshot dataSnapshot) {
        Iterator<DataSnapshot> pairs = dataSnapshot.getChildren().iterator();
        BRelation<Integer, BRelation<Integer, Integer>> result = new BRelation<>();
        while (pairs.hasNext()) {
            DataSnapshot child = pairs.next();
            Pair<Integer, BRelation<Integer, Integer>> cPair =
                    toBPairBRelation(child);
            result.add(cPair);
        }
        return result;
    }

    public static BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> toBRelationRelationRelation(DataSnapshot dataSnapshot) {
        Iterator<DataSnapshot> pairs = dataSnapshot.getChildren().iterator();
        BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> result = new BRelation<>();
        while (pairs.hasNext()) {
            DataSnapshot child = pairs.next();
            Pair<Integer, BRelation<Integer, BRelation<Integer, Integer>>> cPair =
                    toBPairBRelationRelation(child);
            result.add(cPair);
        }
        return result;
    }

    public static BSet<Integer> toBSet(DataSnapshot dataSnapshot) {
        Iterator<DataSnapshot> ints = dataSnapshot.getChildren().iterator();
        BSet<Integer> result = new BSet<>();
        while (ints.hasNext()) {
            DataSnapshot child = ints.next();
            result.add(child.getValue(Integer.class));
        }
        return result;
    }
}

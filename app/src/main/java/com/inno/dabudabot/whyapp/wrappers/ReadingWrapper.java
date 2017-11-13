package com.inno.dabudabot.whyapp.wrappers;

import java.util.Iterator;

import Util.Settings;
import eventb_prelude.BRelation;
import eventb_prelude.Pair;
import group_6_model_sequential.machine3;
import group_6_model_sequential.reading;

public class ReadingWrapper {
    
    public ReadingWrapper() {}

    public void runReading(Integer u1, Integer u2, machine3 m) {
        reading reading = new reading(m);
        if(reading.guard_reading(u1, u2)) {
            Settings.getInstance().setBusy(true);
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>
                                chatcontentseq_tmp = m.get_chatcontentseq();

            reading.run_reading(u1, u2);
            m.set_readChatContentSeq(
                    modifyReadChatcontentSeq(u1, u2, chatcontentseq_tmp));
            Settings.getInstance().commitMachine(m);
            Settings.getInstance().setBusy(false);
        }
    }

    protected BRelation<Integer,Integer> modifyReadChatcontentSeq(Integer u1, Integer u2,
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontentseq_tmp) {
        
        BRelation<Integer,Integer> rccSeq_new =
                                new BRelation<Integer,Integer>();
        
        Iterator<Integer> numIter = chatcontentseq_tmp.domain().iterator();
        while (numIter.hasNext()) {
            BRelation<Integer, BRelation<Integer, Integer>> content_new = 
                    new BRelation<Integer, BRelation<Integer, Integer>>();
            Integer i = numIter.next();
            BRelation<Integer, BRelation<Integer, Integer>> content_old =
                    chatcontentseq_tmp.elementImage(i).iterator().next();
            
            Iterator<Integer> contentIter = content_old.domain().iterator();
            System.out.println(content_old.domain().size());
            if (content_old.domain().size() != 1) {
                continue;
            }

            while (contentIter.hasNext()) {
                Integer c = contentIter.next();
                BRelation<Integer, Integer> chats = 
                        content_old.elementImage(c).iterator().next();
                if (chats.has(new Pair<Integer,Integer>(u1, u2))) {
                    rccSeq_new.add(new Pair<Integer,Integer>(i, c));
                }
            }
        }
        
        return rccSeq_new;
    }
}

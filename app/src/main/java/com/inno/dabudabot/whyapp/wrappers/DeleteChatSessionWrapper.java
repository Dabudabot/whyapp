package com.inno.dabudabot.whyapp.wrappers;

import Util.Settings;
import group_6_model_sequential.machine3;
import group_6_model_sequential.delete_chat_session;
import group_6_model_sequential.machine3;

import java.util.Iterator;

import eventb_prelude.*;

public class DeleteChatSessionWrapper {

    public DeleteChatSessionWrapper() {}

    public void runDeleteChatSession(Integer dcs_u1, Integer dcs_u2, machine3 m){
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> 
                            chatcontent_tmp = m.get_chatcontent();
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>
                            chatcontentseq_tmp = m.get_chatcontentseq();
        BRelation<Integer,BRelation<Integer,Integer>>
                            toreadcon_tmp = m.get_toreadcon();
        delete_chat_session delete_chat_session = new delete_chat_session(m);
        if(delete_chat_session.guard_delete_chat_session(dcs_u1,dcs_u2)) {
            Settings.getInstance().setBusy(true);
            delete_chat_session.run_delete_chat_session(dcs_u1, dcs_u2);
            
            m.set_chatcontent(
                    modifyChatcontent(dcs_u1, dcs_u2, chatcontent_tmp));
            m.set_chatcontentseq(
                    modifyChatcontentSeq(dcs_u1, dcs_u2, chatcontentseq_tmp));
            m.set_toreadcon(
                    modifyToreadcon(dcs_u1, dcs_u2, toreadcon_tmp));
            Settings.getInstance().commitMachine(m);
            Settings.getInstance().setBusy(false);
        }
    }
    
    
    /**
     * Toreadcon change through set comprehension implementation.
     * @param u1
     * @param u2
     * @param toreadcon_tmp
     * @return
     */
    protected BRelation<Integer,BRelation<Integer,Integer>> modifyToreadcon(Integer u1, Integer u2,
            BRelation<Integer,BRelation<Integer,Integer>> toreadcon_tmp) {
        
        BRelation<Integer,BRelation<Integer,Integer>> trc_new = 
                new BRelation<Integer,BRelation<Integer,Integer>>();
        
        Iterator<Integer> contentIter = toreadcon_tmp.domain().iterator();

        while (contentIter.hasNext()) {
            Integer c = contentIter.next();
            BRelation<Integer, Integer> chats = 
                    toreadcon_tmp.elementImage(c).iterator().next();
            
            if (!chats.equals(new BRelation<Integer,Integer>(
                    new Pair<Integer,Integer>(u1, u2)))) {
                
                trc_new.add(new Pair<Integer, BRelation<Integer, Integer>>(
                    c, chats.difference(
                            new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2)))));
            }
        }

        return trc_new;
    }
    
    
    /**
     * ChatcontentSeq change through set comprehension implementation.
     * @param u1
     * @param u2
     * @param chatcontentseq_tmp
     * @return
     */
    protected BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>
            modifyChatcontentSeq(Integer u1, Integer u2,
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontentseq_tmp) {
        
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chcSeq_new =
                new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>();
        
        Iterator<Integer> numIter = chatcontentseq_tmp.domain().iterator();
        while (numIter.hasNext()) {
            BRelation<Integer, BRelation<Integer, Integer>> content_new = 
                    new BRelation<Integer, BRelation<Integer, Integer>>();
            Integer i = numIter.next();
            BRelation<Integer, BRelation<Integer, Integer>> content_old =
                    chatcontentseq_tmp.elementImage(i).iterator().next();
            
            Iterator<Integer> contentIter = content_old.domain().iterator();

            while (contentIter.hasNext()) {
                Integer c = contentIter.next();
                BRelation<Integer, Integer> chats = content_old.elementImage(c).iterator().next();
                content_new.add(new Pair<Integer, BRelation<Integer, Integer>>(
                    c, chats.difference(
                            new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2)))));
            }
            
            chcSeq_new.add(
                new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(
                    i, content_new));
        }
        
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chcSeq_new2 =
                new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>();
        numIter = chatcontentseq_tmp.domain().iterator();
        while (numIter.hasNext()) {
            BRelation<Integer, BRelation<Integer, Integer>> content_new = 
                    new BRelation<Integer, BRelation<Integer, Integer>>();
            Integer i = numIter.next();
            BRelation<Integer, BRelation<Integer, Integer>> content_old =
                    chatcontentseq_tmp.elementImage(i).iterator().next();
            
            Iterator<Integer> contentIter = content_old.domain().iterator();

            while (contentIter.hasNext()) {
                Integer c = contentIter.next();
                BRelation<Integer, Integer> chats = content_old.elementImage(c).iterator().next();
                System.out.println("CHATS: " + chats);
                System.out.println(new Pair<Integer, Integer>(u1, u2));
                if (chats.equals(new BRelation<Integer,Integer>(
                           new Pair<Integer, Integer>(u1, u2)))) {
                    
                    chcSeq_new2.add(
                            new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(
                                i, content_new));
                }
            }
        }
        
        return chcSeq_new.override(chcSeq_new2);
    }
    
    /**
     * Chatcontent change through set comprehension implementation.
     * @param u1
     * @param u2
     * @param chatcontent_tmp
     * @return
     */
    protected BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> 
            modifyChatcontent(Integer u1, Integer u2,
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp) {
        
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chc_new = 
                new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>();
        
        Iterator<Integer> userIter = chatcontent_tmp.domain().iterator();
        while (userIter.hasNext()) {
            BRelation<Integer, BRelation<Integer, Integer>> content_new = 
                    new BRelation<Integer, BRelation<Integer, Integer>>();
            Integer u = userIter.next();
            BRelation<Integer, BRelation<Integer, Integer>> content_old =
                    chatcontent_tmp.elementImage(u).iterator().next();
            
            Iterator<Integer> contentIter = content_old.domain().iterator();

            while (contentIter.hasNext()) {
                Integer c = contentIter.next();
                BRelation<Integer, Integer> chats = content_old.elementImage(c).iterator().next();
                
                if (!chats.equals(new BRelation<Integer,Integer>(
                        new Pair<Integer,Integer>(u1, u2)))) {
                    content_new.add(new Pair<Integer, BRelation<Integer, Integer>>(
                        c, chats.difference(
                                new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2)))));
                }
            }
            
            chc_new.add(
                new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(
                    u, content_new));
        }
        
        return chc_new;
    }
}
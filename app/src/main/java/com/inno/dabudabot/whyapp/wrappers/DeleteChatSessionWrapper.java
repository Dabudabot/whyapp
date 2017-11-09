package com.inno.dabudabot.whyapp.wrappers;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.delete_chat_session;
import group_6_model_sequential.machine3;

import java.util.Iterator;

import eventb_prelude.*;

public class DeleteChatSessionWrapper extends delete_chat_session {
    protected MachineWrapper machineWrapper;

    public DeleteChatSessionWrapper(MachineWrapper m) {
        super(m);
        machineWrapper = m;
    }
    
    @Override
    public void run_delete_chat_session(Integer dcs_u1, Integer dcs_u2){
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> 
                            chatcontent_tmp = machineWrapper.get_chatcontent();
        
        if(guard_delete_chat_session(dcs_u1,dcs_u2)) {
            super.run_delete_chat_session(dcs_u1, dcs_u2);
            
            machineWrapper.set_chatcontent(
                    modifyChatcontent(dcs_u1, dcs_u2, chatcontent_tmp));                        
        }
    }
    
    
    /**
     * Chatcontent change through set comprehension implementation.
     * @param u1
     * @param u2
     * @param chatcontent_tmp
     * @return
     */
    private BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> 
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
                content_new.add(new Pair<Integer, BRelation<Integer, Integer>>(
                    c, chats.difference(
                            new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2)))));
            }
            
            chc_new.add(
                new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(
                    u, content_new));
        }
        
        return chc_new;
    }
}
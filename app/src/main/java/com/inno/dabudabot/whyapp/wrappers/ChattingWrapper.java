package com.inno.dabudabot.whyapp.wrappers;

import Util.Settings;
import group_6_model_sequential.machine3;
import group_6_model_sequential.chatting;

import java.util.Iterator;

import eventb_prelude.*;

public class ChattingWrapper {
    
	public ChattingWrapper() {}

	public boolean guardChatting(Integer ch_c, Integer ch_u1, Integer ch_u2, machine3 m) {
        return new chatting(m).guard_chatting(ch_c, ch_u1, ch_u2);
    }

	public void runChatting(Integer ch_c, Integer ch_u1, Integer ch_u2, machine3 m){
	    chatting chatting = new chatting(m);
        if(chatting.guard_chatting(ch_c,ch_u1,ch_u2)) {
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> 
                                chatcontent_tmp = m.get_chatcontent();

            chatting.run_chatting(ch_c, ch_u1, ch_u2);
            m.set_chatcontent(
                    modifyChatcontent(ch_u1, ch_u2, ch_c, chatcontent_tmp));

            Settings.getInstance().commitMachine(m);
        }
	}
	
	
	/**
     * Chatcontent change through set comprehension implementation.
     * @param u1
     * @param u2
     * @param new_c
     * @param chatcontent_tmp
     * @return
     */
    protected BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> 
    modifyChatcontent(Integer u1, Integer u2, Integer new_c,
    BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp) {
        
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chc_new = 
                new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>();
        
        BRelation<Integer, BRelation<Integer, Integer>> content_new = 
                new BRelation<Integer, BRelation<Integer, Integer>>();
        
        if (chatcontent_tmp.domain().has(u1)) {            
            BRelation<Integer, BRelation<Integer, Integer>> content_old =
                    chatcontent_tmp.elementImage(u1).iterator().next();
            Iterator<Integer> contentIter = content_old.domain().iterator();
            
            while (contentIter.hasNext()) {
                Integer c = contentIter.next();
                content_new.add(
                        new Pair<Integer, BRelation<Integer,Integer>>(c, content_old.apply(c)));
            }
        }
        
        BRelation<Integer,Integer> chats_new = 
                new BRelation<Integer,Integer>();
        chats_new.add(new Pair<Integer, Integer>(u1, u2));
        chats_new.add(new Pair<Integer, Integer>(u2, u1));
        content_new.add(new Pair<Integer, BRelation<Integer,Integer>>(new_c, chats_new));
        chc_new.add(u1, content_new);
        
        return chatcontent_tmp.override(chc_new);
    }
}

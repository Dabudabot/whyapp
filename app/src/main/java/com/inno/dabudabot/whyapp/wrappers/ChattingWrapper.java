package com.inno.dabudabot.whyapp.wrappers;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.chatting;
import group_6_model_sequential.machine3;

import java.util.Iterator;

import eventb_prelude.*;

public class ChattingWrapper extends chatting {
    protected MachineWrapper machineWrapper;
    
	public ChattingWrapper(MachineWrapper m) {
		super(m);
		machineWrapper = m;
	}
	
	
	@Override
	public void run_chatting(Integer ch_c, Integer ch_u1, Integer ch_u2){
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> 
                                chatcontent_tmp = machineWrapper.get_chatcontent();
        
        if(guard_chatting(ch_c,ch_u1,ch_u2)) {
            super.run_chatting(ch_c, ch_u1, ch_u2);
            
            machineWrapper.set_chatcontent(modifyChatcontent(ch_u1, ch_u2, ch_c, chatcontent_tmp));
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
    private BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> 
    modifyChatcontent(Integer u1, Integer u2, Integer new_c,
    BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp) {
        
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chc_new = 
                new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>();
        
        BRelation<Integer, BRelation<Integer, Integer>> content_new = 
                new BRelation<Integer, BRelation<Integer, Integer>>();
        
        if (chatcontent_tmp.domain().has(u1)) {
            System.out.println("u1 is in chc");
            
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
        
        System.out.println("content_new: " + content_new);
        
        chc_new.add(u1, content_new);
        
        System.out.println("chc_new: " + chc_new);
        
        chatcontent_tmp.override(chc_new);
        return chatcontent_tmp;
    }
}

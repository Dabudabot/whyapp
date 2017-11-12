package com.inno.dabudabot.whyapp.wrappers;

import java.util.Iterator;

import Util.Settings;
import eventb_prelude.BRelation;
import eventb_prelude.BSet;
import eventb_prelude.Pair;
import group_6_model_sequential.machine3;
import group_6_model_sequential.forward;
import group_6_model_sequential.machine3;

public class ForwardWrapper {
    
    public ForwardWrapper() {}

    public void runForward(Integer f_c,
                            Integer f_u,
                            BSet<Integer> f_ul,
                            machine3 m) {
        forward forward = new forward(m);
        if(forward.guard_forward(f_c,f_u,f_ul)) {
            Settings.getInstance().setBusy(true);
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>
                                chatcontent_tmp = m.get_chatcontent();
            BRelation<Integer,BRelation<Integer,Integer>>
                                toreadcon_tmp = m.get_toreadcon();
            Integer contentsize_tmp = m.get_contentsize();
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>
                                chatcontentseq_tmp = m.get_chatcontentseq();
            
            forward.run_forward(f_c, f_u, f_ul);
            m.set_chatcontent(
                    modifyChatcontent(f_c, f_u, f_ul, chatcontent_tmp));
            m.set_toreadcon((toreadcon_tmp.override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(f_c,BRelation.cross(f_ul,new BSet<Integer>(f_u)).difference(m.get_active()))))));
            m.set_chatcontentseq((chatcontentseq_tmp.override(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Integer(contentsize_tmp + 1),new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(f_c,(BRelation.cross(new BSet<Integer>(f_u),f_ul).union(BRelation.cross(f_ul,new BSet<Integer>(f_u)))))))))));
            Settings.getInstance().commitMachine(m);
            Settings.getInstance().setBusy(false);
        }
    }
    
    
    /**
     * Chatcontent change through set comprehension implementation.
     * @param u
     * @param ul
     * @param new_c
     * @param chatcontent_tmp
     * @return
     */
    protected BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> 
    modifyChatcontent(Integer new_c, Integer u, BSet<Integer> ul,
    BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp) {
        
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chc_new = 
                new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>();
        
        BRelation<Integer, BRelation<Integer, Integer>> content_new = 
                new BRelation<Integer, BRelation<Integer, Integer>>();
        
        if (chatcontent_tmp.domain().has(u)) {            
            BRelation<Integer, BRelation<Integer, Integer>> content_old =
                    chatcontent_tmp.elementImage(u).iterator().next();
            Iterator<Integer> contentIter = content_old.domain().iterator();
            
            while (contentIter.hasNext()) {
                Integer c = contentIter.next();
                content_new.add(
                        new Pair<Integer, BRelation<Integer,Integer>>(c, content_old.apply(c)));
            }
        }
        
        BRelation<Integer,Integer> chats_new = 
                new BRelation<Integer,Integer>();
        BSet<Integer> uset = new BSet<Integer>();
        uset.add(u);
        chats_new.addAll(BRelation.cross(ul, uset).union(BRelation.cross(uset, ul)));
        content_new.add(new Pair<Integer, BRelation<Integer,Integer>>(new_c, chats_new));
        chc_new.add(u, content_new);
        
        return chatcontent_tmp.override(chc_new);
    }
}

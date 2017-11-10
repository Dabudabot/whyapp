package whyapp.extensions;

import java.util.Iterator;

import eventb_prelude.BRelation;
import eventb_prelude.BSet;
import eventb_prelude.Pair;
import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.machine3;
import group_6_model_sequential.remove_content;

public class RemoveContentWrapper extends remove_content {
    protected MachineWrapper machineWrapper;
    
    public RemoveContentWrapper(MachineWrapper m) {
        super(m);
        machineWrapper = m;
    }

    
    @Override
    public void run_remove_content(Integer dc_c, Integer dc_u1, Integer dc_u2, Integer dc_i){
        if(guard_remove_content(dc_c,dc_u1,dc_u2,dc_i)) {
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> 
                                    chatcontent_tmp = machineWrapper.get_chatcontent();
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>
                                    chatcontentseq_tmp = machineWrapper.get_chatcontentseq();

            super.run_remove_content(dc_c, dc_u1, dc_u2, dc_i);
            machineWrapper.set_chatcontent(
                    modifyChatcontent(dc_c, dc_u1, dc_u2, chatcontent_tmp));
            machineWrapper.set_chatcontentseq(
                    modifyChatcontentSeq(dc_c, dc_u1, dc_u2, dc_i, chatcontentseq_tmp));
        }
    }
    
    
    
    /**
     * ChatcontentSeq change through set comprehension implementation.
     * @param del_c
     * @param u1
     * @param u2
     * @param i
     * @param chatcontentseq_tmp
     * @return
     */
    protected BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>
            modifyChatcontentSeq(Integer del_c, Integer u1, Integer u2, Integer i,
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontentseq_tmp) {
        
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chcSeq_new =
                new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>();
        
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chcSeq_new2 =
                new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>();
        
        Iterator<Integer> numIter = chatcontentseq_tmp.domain().iterator();
        BRelation<Integer, BRelation<Integer, Integer>> content_new = 
                new BRelation<Integer, BRelation<Integer, Integer>>();
        
        BRelation<Integer,Integer> delChat = new BRelation<Integer,Integer>();
        delChat.add(new Pair<Integer,Integer>(u1, u2));
        
        BRelation<Integer, BRelation<Integer, Integer>> content_old =
                chatcontentseq_tmp.elementImage(i).iterator().next();
        
        Iterator<Integer> contentIter = content_old.domain().iterator();

        while (contentIter.hasNext()) {
            Integer c = contentIter.next();
            BRelation<Integer, Integer> chats = 
                    content_old.elementImage(c).iterator().next();
            
            if (c.equals(del_c)) {
                if (chats.equals(delChat)) {
                    chcSeq_new2.add(
                            new Pair<Integer, BRelation<Integer,BRelation<Integer,Integer>>>(
                                    i, new BRelation<Integer, BRelation<Integer,Integer>>()));
                }
                content_new.add(new Pair<Integer, BRelation<Integer,Integer>>(
                        del_c, chats.difference(delChat)));        
                
            } else {
                content_new.add(new Pair<Integer, BRelation<Integer,Integer>>(c, chats));
            }
        }
        
        chcSeq_new.add(
                new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(
                    i, content_new));
        
        return chatcontentseq_tmp.override(chcSeq_new.override(chcSeq_new2));
    }
    
    
    
    /**
     * Chatcontent change through set comprehension implementation.
     * @param del_c
     * @param u1
     * @param u2
     * @param chatcontent_tmp
     * @return
     */
    protected BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> 
            modifyChatcontent(Integer del_c, Integer u1, Integer u2,
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp) {
        
        BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chc_new = 
                new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>();
        
        BRelation<Integer, BRelation<Integer, Integer>> content_new = 
                new BRelation<Integer, BRelation<Integer, Integer>>();
        BRelation<Integer, BRelation<Integer, Integer>> content_old =
                chatcontent_tmp.elementImage(u1).iterator().next();
        
        Iterator<Integer> contentIter = content_old.domain().iterator();
        BSet<Integer> contentToSubtract = new BSet<Integer>();
        BRelation<Integer,Integer> delChat = new BRelation<Integer,Integer>();
        delChat.add(new Pair<Integer,Integer>(u1, u2));
        
        BRelation<Integer, Integer> del_c_chats = 
                content_old.elementImage(del_c).iterator().next();
        del_c_chats = del_c_chats.difference(delChat);
        
        while (contentIter.hasNext()) {
            Integer c = contentIter.next();
            BRelation<Integer, Integer> chats = 
                    content_old.elementImage(c).iterator().next();
            
            if (c.equals(del_c)) {
                if (chats.equals(delChat)) {
                    contentToSubtract.add(c);
                }
                content_new.add(new Pair<Integer, BRelation<Integer,Integer>>(
                        del_c, del_c_chats));        
                
            } else {
                content_new.add(new Pair<Integer, BRelation<Integer,Integer>>(c, chats));
            }
        }
        
        content_new = content_new.domainSubtraction(contentToSubtract);
        chc_new.add(
                new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(
                    u1, content_new));
                                
        return chatcontent_tmp.override(chc_new);
    }
}

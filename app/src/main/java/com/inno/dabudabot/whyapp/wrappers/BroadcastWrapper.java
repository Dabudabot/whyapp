package whyapp.extensions;

import java.util.Iterator;

import eventb_prelude.BRelation;
import eventb_prelude.BSet;
import eventb_prelude.Pair;
import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.broadcast;
import group_6_model_sequential.machine3;

public class BroadcastWrapper extends broadcast {
    protected MachineWrapper machineWrapper;
    
    public BroadcastWrapper(MachineWrapper m) {
        super(m);
        machineWrapper = m;
    }
    
    
    @Override
    public void run_broadcast(Integer b_c, Integer b_u, BSet<Integer> b_ul) {
        if(guard_broadcast(b_c,b_u,b_ul)) {
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>
                                chatcontent_tmp = machineWrapper.get_chatcontent();
            BRelation<Integer,BRelation<Integer,Integer>>
                                toreadcon_tmp = machineWrapper.get_toreadcon();
            Integer contentsize_tmp = machineWrapper.get_contentsize();
            BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>
                                chatcontentseq_tmp = machineWrapper.get_chatcontentseq();
            
            super.run_broadcast(b_c, b_u, b_ul);
            machineWrapper.set_chatcontent(
                    modifyChatcontent(b_c, b_u, b_ul, chatcontent_tmp));
            machineWrapper.set_toreadcon((toreadcon_tmp.override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(b_c,BRelation.cross(b_ul,new BSet<Integer>(b_u)).difference(machineWrapper.get_active()))))));
            machineWrapper.set_chatcontentseq((chatcontentseq_tmp.override(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Integer(contentsize_tmp + 1),new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(b_c,(BRelation.cross(new BSet<Integer>(b_u),b_ul).union(BRelation.cross(b_ul,new BSet<Integer>(b_u)))))))))));
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

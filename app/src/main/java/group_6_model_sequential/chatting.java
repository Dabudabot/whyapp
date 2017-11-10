package group_6_model_sequential; 

import eventb_prelude.*;
import Util.Utilities;

public class chatting{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public chatting(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(ch_u1) && machine.get_user().has(ch_u2) && !ch_u1.equals(ch_u2) && machine.get_chat().has(new Pair<Integer,Integer>(ch_u1,ch_u2)) && machine.get_active().has(new Pair<Integer,Integer>(ch_u1,ch_u2)) && machine.CONTENT.difference(machine.get_content()).has(ch_c) && !machine.get_muted().has(new Pair<Integer,Integer>(ch_u1,ch_u2)) && !machine.get_muted().has(new Pair<Integer,Integer>(ch_u2,ch_u1)) && !machine.get_toreadcon().domain().has(ch_c) &&  (\forall Integer ch_i;new Enumerated(new Integer(1),machine.get_contentsize()).has(ch_i) && machine.get_chatcontentseq().domain().has(ch_i) && !machine.get_chatcontentseq().apply(ch_i).domain().has(ch_c)) &&  (\forall Integer ch_u;machine.get_chatcontent().domain().has(ch_u) && !machine.get_chatcontent().apply(ch_u).domain().has(ch_c))); */
	public /*@ pure */ boolean guard_chatting( Integer ch_c, Integer ch_u1, Integer ch_u2) {
		return (machine.get_user().has(ch_u1) && machine.get_user().has(ch_u2) && !ch_u1.equals(ch_u2) && machine.get_chat().has(new Pair<Integer,Integer>(ch_u1,ch_u2)) && machine.get_active().has(new Pair<Integer,Integer>(ch_u1,ch_u2)) && machine.CONTENT.difference(machine.get_content()).has(ch_c) && !machine.get_muted().has(new Pair<Integer,Integer>(ch_u1,ch_u2)) && !machine.get_muted().has(new Pair<Integer,Integer>(ch_u2,ch_u1)) && !machine.get_toreadcon().domain().has(ch_c) && true && true);
	}

	/*@ public normal_behavior
		requires guard_chatting(ch_c,ch_u1,ch_u2);
		assignable machine.content, machine.chat, machine.chatcontent, machine.inactive, machine.toread, machine.toreadcon, machine.owner, machine.contentsize, machine.chatcontentseq;
		ensures guard_chatting(ch_c,ch_u1,ch_u2) &&  machine.get_content().equals(\old((machine.get_content().union(new BSet<Integer>(ch_c))))) &&  machine.get_chat().equals(\old((machine.get_chat().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_u2,ch_u1)))))) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().override(new BRelation<Integer,Pair<BRelation<Integer,Integer>,Integer>>(new Pair<Integer,Pair<BRelation<Integer,Integer>,BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>>>(ch_u1,(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (machine.get_chatcontent().domain().has(ch_u1) && machine.get_chatcontent().apply(ch_u1).domain().has(cc)); e.equals(new Pair<BRelation<Integer,Integer>,ERROR>(cc,machine.get_chatcontent().apply(ch_u1).apply(cc))))}).union(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(ch_c,(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_u1,ch_u2)).union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_u2,ch_u1)))))))))))))) &&  machine.get_inactive().equals(\old((machine.get_inactive().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_u2,ch_u1)).difference(machine.get_active()))))) &&  machine.get_toread().equals(\old((machine.get_toread().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_u2,ch_u1)).difference(machine.get_active()))))) &&  machine.get_toreadcon().equals(\old((machine.get_toreadcon().override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(ch_c,new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_u2,ch_u1)).difference(machine.get_active()))))))) &&  machine.get_owner().equals(\old((machine.get_owner().override(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_c,ch_u1)))))) &&  machine.get_contentsize() == \old(new Integer(machine.get_contentsize() + 1)) &&  machine.get_chatcontentseq().equals(\old((machine.get_chatcontentseq().override(new BRelation<Integer,BRelation<Integer,BSet<Integer>>>(new Pair<Integer,BRelation<Integer,BSet<Integer>>>(new Integer(machine.get_contentsize() + 1),new BRelation<Integer,BSet<Integer>>(new Pair<Integer,BSet<Integer>>(ch_c,(BRelation.cross(new BSet<Integer>(ch_u1),new BSet<Integer>(ch_u2)).union(BRelation.cross(new BSet<Integer>(ch_u2),new BSet<Integer>(ch_u1)))))))))))); 
	 also
		requires !guard_chatting(ch_c,ch_u1,ch_u2);
		assignable \nothing;
		ensures true; */
	public void run_chatting( Integer ch_c, Integer ch_u1, Integer ch_u2){
		if(guard_chatting(ch_c,ch_u1,ch_u2)) {
			BSet<Integer> content_tmp = machine.get_content();
			BRelation<Integer,Integer> chat_tmp = machine.get_chat();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,BRelation<Integer,Integer>> toreadcon_tmp = machine.get_toreadcon();
			BRelation<Integer,Integer> owner_tmp = machine.get_owner();
			Integer contentsize_tmp = machine.get_contentsize();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontentseq_tmp = machine.get_chatcontentseq();

			machine.set_content((content_tmp.union(new BSet<Integer>(ch_c))));
			machine.set_chat((chat_tmp.union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_u2,ch_u1)))));
			machine.set_chatcontent(null); // Set Comprehension: feature not supported by EventB2Java
			machine.set_inactive((inactive_tmp.union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_u2,ch_u1)).difference(machine.get_active()))));
			machine.set_toread((toread_tmp.union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_u2,ch_u1)).difference(machine.get_active()))));
			machine.set_toreadcon((toreadcon_tmp.override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(ch_c,new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_u2,ch_u1)).difference(machine.get_active()))))));
			machine.set_owner((owner_tmp.override(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ch_c,ch_u1)))));
			machine.set_contentsize(new Integer(contentsize_tmp + 1));
		//	machine.set_chatcontentseq((chatcontentseq_tmp.override(new BRelation<Integer,BRelation<Integer,BSet<Integer>>>(new Pair<Integer,BRelation<Integer,BSet<Integer>>>(new Integer(contentsize_tmp + 1),new BRelation<Integer,BSet<Integer>>(new Pair<Integer,BSet<Integer>>(ch_c,(BRelation.cross(new BSet<Integer>(ch_u1),new BSet<Integer>(ch_u2)).union(BRelation.cross(new BSet<Integer>(ch_u2),new BSet<Integer>(ch_u1)))))))))));

			System.out.println("chatting executed ch_c: " + ch_c + " ch_u1: " + ch_u1 + " ch_u2: " + ch_u2 + " ");
		}
	}

}

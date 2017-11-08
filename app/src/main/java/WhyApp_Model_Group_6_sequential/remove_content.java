package WhyApp_Model_Group_6_sequential; 

import eventb_prelude.*;
import Util.Utilities;

public class remove_content{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public remove_content(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(rc_u1) && machine.get_user().has(rc_u2) && machine.get_chat().has(new Pair<Integer,Integer>(rc_u1,rc_u2)) && machine.get_active().has(new Pair<Integer,Integer>(rc_u1,rc_u2)) && machine.get_content().has(rc_c) && machine.get_chatcontent().domain().has(rc_u1) && machine.get_chatcontent().apply(rc_u1).domain().has(rc_c) && new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2)).isSubset(machine.get_chatcontent().apply(rc_u1).apply(rc_c)) && machine.get_toreadcon().domain().has(rc_c) && rc_u1.equals(machine.get_owner().apply(rc_c)) && new Enumerated(new Integer(1),machine.get_contentsize()).has(rc_i) && machine.get_chatcontentseq().domain().has(rc_i) && machine.get_chatcontentseq().apply(rc_i).domain().has(rc_c)); */
	public /*@ pure */ boolean guard_remove_content( Integer rc_c, Integer rc_u1, Integer rc_u2, Integer rc_i) {
		return (machine.get_user().has(rc_u1) && machine.get_user().has(rc_u2) && machine.get_chat().has(new Pair<Integer,Integer>(rc_u1,rc_u2)) && machine.get_active().has(new Pair<Integer,Integer>(rc_u1,rc_u2)) && machine.get_content().has(rc_c) && machine.get_chatcontent().domain().has(rc_u1) && machine.get_chatcontent().apply(rc_u1).domain().has(rc_c) && new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2)).isSubset(machine.get_chatcontent().apply(rc_u1).apply(rc_c)) && machine.get_toreadcon().domain().has(rc_c) && rc_u1.equals(machine.get_owner().apply(rc_c)) && new Enumerated(new Integer(1),machine.get_contentsize()).has(rc_i) && machine.get_chatcontentseq().domain().has(rc_i) && machine.get_chatcontentseq().apply(rc_i).domain().has(rc_c));
	}

	/*@ public normal_behavior
		requires guard_remove_content(rc_c,rc_u1,rc_u2,rc_i);
		assignable machine.chatcontent, machine.toreadcon, machine.chatcontentseq;
		ensures guard_remove_content(rc_c,rc_u1,rc_u2,rc_i) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().override(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(rc_u1,(machine.get_chatcontent().apply(rc_u1).override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(rc_c,machine.get_chatcontent().apply(rc_u1).apply(rc_c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2))))))).domainSubtraction(new no_type(new JMLObjectSet {Integer cc | (\exists no_type e; (machine.get_chatcontent().apply(rc_u1).domain().has(null) && null.equals(rc_c) && machine.get_chatcontent().apply(rc_u1).apply(null).equals(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2)))); e.equals(null))})))))))) &&  machine.get_toreadcon().equals(\old((machine.get_toreadcon().override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(rc_c,machine.get_toreadcon().apply(rc_c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2))))))))) &&  machine.get_chatcontentseq().equals(\old((machine.get_chatcontentseq().override((new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(rc_i,new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(rc_c,machine.get_chatcontentseq().apply(rc_i).apply(rc_c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2))))))).override(new no_type(new JMLObjectSet {Integer cc | (\exists no_type e; (new BSet<ERROR>(null).equals(machine.get_chatcontentseq().apply(rc_i).domain()) && null.equals(rc_c) && machine.get_chatcontentseq().apply(rc_i).apply(null).equals(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2)))); e.equals(new Pair<Integer,ERROR>(rc_i,BRelation.EMPTY)))}))))))); 
	 also
		requires !guard_remove_content(rc_c,rc_u1,rc_u2,rc_i);
		assignable \nothing;
		ensures true; */
	public void run_remove_content( Integer rc_c, Integer rc_u1, Integer rc_u2, Integer rc_i){
		if(guard_remove_content(rc_c,rc_u1,rc_u2,rc_i)) {
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BRelation<Integer,BRelation<Integer,Integer>> toreadcon_tmp = machine.get_toreadcon();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontentseq_tmp = machine.get_chatcontentseq();

		//!	machine.set_chatcontent((chatcontent_tmp.override(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(rc_u1,(chatcontent_tmp.apply(rc_u1).override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(rc_c,chatcontent_tmp.apply(rc_u1).apply(rc_c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2))))))).domainSubtraction(new no_type(new JMLObjectSet {Integer cc | (\exists no_type e; (chatcontent_tmp.apply(rc_u1).domain().has(null) && null.equals(rc_c) && chatcontent_tmp.apply(rc_u1).apply(null).equals(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2)))); e.equals(null))})))))));
			machine.set_toreadcon((toreadcon_tmp.override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(rc_c,toreadcon_tmp.apply(rc_c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2))))))));
		//!	machine.set_chatcontentseq((chatcontentseq_tmp.override((new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(rc_i,new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(rc_c,chatcontentseq_tmp.apply(rc_i).apply(rc_c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2))))))).override(new no_type(new JMLObjectSet {Integer cc | (\exists no_type e; (new BSet<ERROR>(null).equals(chatcontentseq_tmp.apply(rc_i).domain()) && null.equals(rc_c) && chatcontentseq_tmp.apply(rc_i).apply(null).equals(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(rc_u1,rc_u2)))); e.equals(new Pair<Integer,ERROR>(rc_i,BRelation.EMPTY)))}))))));

			System.out.println("remove_content executed rc_c: " + rc_c + " rc_u1: " + rc_u1 + " rc_u2: " + rc_u2 + " rc_i: " + rc_i + " ");
		}
	}

}

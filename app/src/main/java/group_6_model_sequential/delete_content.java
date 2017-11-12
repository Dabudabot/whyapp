package group_6_model_sequential; 

import eventb_prelude.*;
import Util.Utilities;

public class delete_content{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public delete_content(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(dc_u1) && machine.get_user().has(dc_u2) && machine.get_chat().has(new Pair<Integer,Integer>(dc_u1,dc_u2)) && machine.get_active().has(new Pair<Integer,Integer>(dc_u1,dc_u2)) && machine.get_content().has(dc_c) && machine.get_chatcontent().domain().has(dc_u2) && machine.get_chatcontent().apply(dc_u2).domain().has(dc_c) && new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dc_u1,dc_u2)).isSubset(machine.get_chatcontent().apply(dc_u2).apply(dc_c)) && machine.get_toreadcon().domain().has(dc_c) && dc_u2.equals(machine.get_owner().apply(dc_c)) && new Enumerated(new Integer(1),machine.get_contentsize()).has(dc_i) && machine.get_chatcontentseq().domain().has(dc_i) && machine.get_chatcontentseq().apply(dc_i).domain().has(dc_c)); */
	public /*@ pure */ boolean guard_delete_content( Integer dc_c, Integer dc_u1, Integer dc_u2, Integer dc_i) {
		return (machine.get_user().has(dc_u1) && machine.get_user().has(dc_u2) && machine.get_chat().has(new Pair<Integer,Integer>(dc_u1,dc_u2)) && machine.get_active().has(new Pair<Integer,Integer>(dc_u1,dc_u2)) && machine.get_content().has(dc_c) && machine.get_chatcontent().domain().has(dc_u2) && machine.get_chatcontent().apply(dc_u2).domain().has(dc_c) && new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dc_u1,dc_u2)).isSubset(machine.get_chatcontent().apply(dc_u2).apply(dc_c)) && machine.get_toreadcon().domain().has(dc_c) && dc_u2.equals(machine.get_owner().apply(dc_c)) && new Enumerated(new Integer(1),machine.get_contentsize()).has(dc_i) && machine.get_chatcontentseq().domain().has(dc_i) && machine.get_chatcontentseq().apply(dc_i).domain().has(dc_c));
	}

	/*@ public normal_behavior
		requires guard_delete_content(dc_c,dc_u1,dc_u2,dc_i);
		assignable machine.chatcontent, machine.toreadcon, machine.chatcontentseq;
		ensures guard_delete_content(dc_c,dc_u1,dc_u2,dc_i) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().override(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(dc_u2,(machine.get_chatcontent().apply(dc_u2).override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(dc_c,machine.get_chatcontent().apply(dc_u2).apply(dc_c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dc_u1,dc_u2))))))).domainSubtraction(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (machine.get_chatcontent().apply(dc_u2).domain().has(cc) && cc.equals(dc_c) && machine.get_chatcontent().apply(dc_u2).apply(cc).equals(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dc_u1,dc_u2)))); e.equals(cc))})))))))) &&  machine.get_toreadcon().equals(\old((machine.get_toreadcon().override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(dc_c,machine.get_toreadcon().apply(dc_c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dc_u1,dc_u2))))))))) &&  machine.get_chatcontentseq().equals(\old((machine.get_chatcontentseq().override((new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(dc_i,new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(dc_c,machine.get_chatcontentseq().apply(dc_i).apply(dc_c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dc_u1,dc_u2))))))).override(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (new BSet<ERROR>(cc).equals(machine.get_chatcontentseq().apply(dc_i).domain()) && cc.equals(dc_c) && machine.get_chatcontentseq().apply(dc_i).apply(cc).equals(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dc_u1,dc_u2)))); e.equals(new Pair<Integer,ERROR>(dc_i,BRelation.EMPTY)))}))))))); 
	 also
		requires !guard_delete_content(dc_c,dc_u1,dc_u2,dc_i);
		assignable \nothing;
		ensures true; */
	public void run_delete_content( Integer dc_c, Integer dc_u1, Integer dc_u2, Integer dc_i){
		if(guard_delete_content(dc_c,dc_u1,dc_u2,dc_i)) {
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BRelation<Integer,BRelation<Integer,Integer>> toreadcon_tmp = machine.get_toreadcon();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontentseq_tmp = machine.get_chatcontentseq();

			machine.set_chatcontent(null); // Set Comprehension: feature not supported by EventB2Java
			machine.set_toreadcon((toreadcon_tmp.override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(dc_c,toreadcon_tmp.apply(dc_c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dc_u1,dc_u2))))))));
			machine.set_chatcontentseq(null); // Set Comprehension: feature not supported by EventB2Java

			System.out.println("delete_content executed dc_c: " + dc_c + " dc_u1: " + dc_u1 + " dc_u2: " + dc_u2 + " dc_i: " + dc_i + " ");
		}
	}

}

package WhyApp_Model_Group_6_sequential; 

import eventb_prelude.*;
import Util.Utilities;

public class reading{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public reading(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(r_u1) && machine.get_user().has(r_u2) && machine.get_chat().has(new Pair<Integer,Integer>(r_u1,r_u2))); */
	public /*@ pure */ boolean guard_reading( Integer r_u1, Integer r_u2) {
		return (machine.get_user().has(r_u1) && machine.get_user().has(r_u2) && machine.get_chat().has(new Pair<Integer,Integer>(r_u1,r_u2)));
	}

	/*@ public normal_behavior
		requires guard_reading(r_u1,r_u2);
		assignable machine.readChatContentSeq;
		ensures guard_reading(r_u1,r_u2) &&  machine.get_readChatContentSeq().equals(\old(new no_type(new JMLObjectSet {Integer iInteger c | (\exists no_type e; (new Enumerated(1,machine.get_contentsize()).has(null) && machine.get_chatcontentseq().domain().has(null) && machine.get_content().has(null) && new BSet<ERROR>(null).equals(machine.get_chatcontentseq().apply(null).domain()) && machine.get_chatcontentseq().apply(null).apply(null).has(new Pair<Integer,Integer>(r_u1,r_u2))); e.equals(new Pair<ERROR,ERROR>(null,null)))}))); 
	 also
		requires !guard_reading(r_u1,r_u2);
		assignable \nothing;
		ensures true; */
	public void run_reading( Integer r_u1, Integer r_u2){
		if(guard_reading(r_u1,r_u2)) {
			BRelation<Integer,Integer> readChatContentSeq_tmp = machine.get_readChatContentSeq();

		//!	machine.set_readChatContentSeq(new no_type(new JMLObjectSet {Integer iInteger c | (\exists no_type e; (new Enumerated(1,machine.get_contentsize()).has(null) && machine.get_chatcontentseq().domain().has(null) && machine.get_content().has(null) && new BSet<ERROR>(null).equals(machine.get_chatcontentseq().apply(null).domain()) && machine.get_chatcontentseq().apply(null).apply(null).has(new Pair<Integer,Integer>(r_u1,r_u2))); e.equals(new Pair<ERROR,ERROR>(null,null)))}));

			System.out.println("reading executed r_u1: " + r_u1 + " r_u2: " + r_u2 + " ");
		}
	}

}

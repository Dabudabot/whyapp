package WhyApp_Model_Group_6_sequential; 

import eventb_prelude.*;
import Util.Utilities;

public class create_chat_session{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public create_chat_session(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(ccs_u1) && machine.get_user().has(ccs_u2) && !ccs_u1.equals(ccs_u2) && !machine.get_chat().has(new Pair<Integer,Integer>(ccs_u1,ccs_u2)) && !machine.get_muted().has(new Pair<Integer,Integer>(ccs_u1,ccs_u2)) && !machine.get_toread().has(new Pair<Integer,Integer>(ccs_u1,ccs_u2)) && !machine.get_inactive().has(new Pair<Integer,Integer>(ccs_u1,ccs_u2)) && (machine.get_active().intersection(machine.get_inactive())).equals(machine.get_chat())); */
	public /*@ pure */ boolean guard_create_chat_session( Integer ccs_u1, Integer ccs_u2) {
		return (machine.get_user().has(ccs_u1) && machine.get_user().has(ccs_u2) && !ccs_u1.equals(ccs_u2) && !machine.get_chat().has(new Pair<Integer,Integer>(ccs_u1,ccs_u2)) && !machine.get_muted().has(new Pair<Integer,Integer>(ccs_u1,ccs_u2)) && !machine.get_toread().has(new Pair<Integer,Integer>(ccs_u1,ccs_u2)) && !machine.get_inactive().has(new Pair<Integer,Integer>(ccs_u1,ccs_u2)) && (machine.get_active().intersection(machine.get_inactive())).equals(machine.get_chat()));
	}

	/*@ public normal_behavior
		requires guard_create_chat_session(ccs_u1,ccs_u2);
		assignable machine.chat, machine.active;
		ensures guard_create_chat_session(ccs_u1,ccs_u2) &&  machine.get_chat().equals(\old((machine.get_chat().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ccs_u1,ccs_u2)))))) &&  machine.get_active().equals(\old((machine.get_active().override(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ccs_u1,ccs_u2)))))); 
	 also
		requires !guard_create_chat_session(ccs_u1,ccs_u2);
		assignable \nothing;
		ensures true; */
	public void run_create_chat_session( Integer ccs_u1, Integer ccs_u2){
		if(guard_create_chat_session(ccs_u1,ccs_u2)) {
			BRelation<Integer,Integer> chat_tmp = machine.get_chat();
			BRelation<Integer,Integer> active_tmp = machine.get_active();

			machine.set_chat((chat_tmp.union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ccs_u1,ccs_u2)))));
			machine.set_active((active_tmp.override(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(ccs_u1,ccs_u2)))));

			System.out.println("create_chat_session executed ccs_u1: " + ccs_u1 + " ccs_u2: " + ccs_u2 + " ");
		}
	}

}

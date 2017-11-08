package WhyApp_Model_Group_6_sequential; 

import eventb_prelude.*;
import Util.Utilities;

public class select_chat{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public select_chat(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(sc_u1) && machine.get_user().has(sc_u2) && machine.get_chat().has(new Pair<Integer,Integer>(sc_u1,sc_u2)) && !machine.get_active().has(new Pair<Integer,Integer>(sc_u1,sc_u2)) && !machine.get_muted().has(new Pair<Integer,Integer>(sc_u1,sc_u2)) && (machine.get_active().intersection(machine.get_inactive())).equals(machine.get_chat())); */
	public /*@ pure */ boolean guard_select_chat( Integer sc_u1, Integer sc_u2) {
		return (machine.get_user().has(sc_u1) && machine.get_user().has(sc_u2) && machine.get_chat().has(new Pair<Integer,Integer>(sc_u1,sc_u2)) && !machine.get_active().has(new Pair<Integer,Integer>(sc_u1,sc_u2)) && !machine.get_muted().has(new Pair<Integer,Integer>(sc_u1,sc_u2)) && (machine.get_active().intersection(machine.get_inactive())).equals(machine.get_chat()));
	}

	/*@ public normal_behavior
		requires guard_select_chat(sc_u1,sc_u2);
		assignable machine.active, machine.toread, machine.inactive;
		ensures guard_select_chat(sc_u1,sc_u2) &&  machine.get_active().equals(\old((machine.get_active().override(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(sc_u1,sc_u2)))))) &&  machine.get_toread().equals(\old(machine.get_toread().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(sc_u1,sc_u2))))) &&  machine.get_inactive().equals(\old(machine.get_inactive().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(sc_u1,sc_u2))))); 
	 also
		requires !guard_select_chat(sc_u1,sc_u2);
		assignable \nothing;
		ensures true; */
	public void run_select_chat( Integer sc_u1, Integer sc_u2){
		if(guard_select_chat(sc_u1,sc_u2)) {
			BRelation<Integer,Integer> active_tmp = machine.get_active();
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();

			machine.set_active((active_tmp.override(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(sc_u1,sc_u2)))));
			machine.set_toread(toread_tmp.difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(sc_u1,sc_u2))));
			machine.set_inactive(inactive_tmp.difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(sc_u1,sc_u2))));

			System.out.println("select_chat executed sc_u1: " + sc_u1 + " sc_u2: " + sc_u2 + " ");
		}
	}

}

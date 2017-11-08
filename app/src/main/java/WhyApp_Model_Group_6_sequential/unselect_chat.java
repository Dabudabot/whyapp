package WhyApp_Model_Group_6_sequential; 

import eventb_prelude.*;
import Util.Utilities;

public class unselect_chat{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public unselect_chat(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(usc_u1) && machine.get_user().has(usc_u2) && machine.get_chat().has(new Pair<Integer,Integer>(usc_u1,usc_u2)) && machine.get_active().has(new Pair<Integer,Integer>(usc_u1,usc_u2)) && !machine.get_inactive().has(new Pair<Integer,Integer>(usc_u1,usc_u2))); */
	public /*@ pure */ boolean guard_unselect_chat( Integer usc_u1, Integer usc_u2) {
		return (machine.get_user().has(usc_u1) && machine.get_user().has(usc_u2) && machine.get_chat().has(new Pair<Integer,Integer>(usc_u1,usc_u2)) && machine.get_active().has(new Pair<Integer,Integer>(usc_u1,usc_u2)) && !machine.get_inactive().has(new Pair<Integer,Integer>(usc_u1,usc_u2)));
	}

	/*@ public normal_behavior
		requires guard_unselect_chat(usc_u1,usc_u2);
		assignable machine.active, machine.inactive;
		ensures guard_unselect_chat(usc_u1,usc_u2) &&  machine.get_active().equals(\old(machine.get_active().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(usc_u1,usc_u2))))) &&  machine.get_inactive().equals(\old((machine.get_inactive().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(usc_u1,usc_u2)))))); 
	 also
		requires !guard_unselect_chat(usc_u1,usc_u2);
		assignable \nothing;
		ensures true; */
	public void run_unselect_chat( Integer usc_u1, Integer usc_u2){
		if(guard_unselect_chat(usc_u1,usc_u2)) {
			BRelation<Integer,Integer> active_tmp = machine.get_active();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();

			machine.set_active(active_tmp.difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(usc_u1,usc_u2))));
			machine.set_inactive((inactive_tmp.union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(usc_u1,usc_u2)))));

			System.out.println("unselect_chat executed usc_u1: " + usc_u1 + " usc_u2: " + usc_u2 + " ");
		}
	}

}

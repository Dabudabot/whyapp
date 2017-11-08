package WhyApp_Model_Group_6_sequential; 

import eventb_prelude.*;
import Util.Utilities;

public class delete_chat_session{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public delete_chat_session(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(dcs_u1) && machine.get_user().has(dcs_u2) && machine.get_chat().has(new Pair<Integer,Integer>(dcs_u1,dcs_u2)) && machine.get_active().has(new Pair<Integer,Integer>(dcs_u1,dcs_u2)) && machine.get_chatcontent().domain().has(dcs_u1) && machine.get_chatcontent().apply(dcs_u1).range().has(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2)))); */
	public /*@ pure */ boolean guard_delete_chat_session( Integer dcs_u1, Integer dcs_u2) {
		return (machine.get_user().has(dcs_u1) && machine.get_user().has(dcs_u2) && machine.get_chat().has(new Pair<Integer,Integer>(dcs_u1,dcs_u2)) && machine.get_active().has(new Pair<Integer,Integer>(dcs_u1,dcs_u2)) && machine.get_chatcontent().domain().has(dcs_u1) && machine.get_chatcontent().apply(dcs_u1).range().has(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))));
	}

	/*@ public normal_behavior
		requires guard_delete_chat_session(dcs_u1,dcs_u2);
		assignable machine.active, machine.muted, machine.chat, machine.chatcontent, machine.inactive, machine.toread, machine.toreadcon;
		ensures guard_delete_chat_session(dcs_u1,dcs_u2) &&  machine.get_active().equals(\old(machine.get_active().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))))) &&  machine.get_muted().equals(\old(machine.get_muted().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))))) &&  machine.get_chat().equals(\old(machine.get_chat().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))))) &&  machine.get_chatcontent().equals(\old(new no_type(new JMLObjectSet {Integer u | (\exists no_type e; (machine.get_chatcontent().domain().has(null)); e.equals(new Pair<Pair<BRelation<Integer,Integer>,BRelation<Integer,BRelation<Integer,Integer>>>,ERROR>(null,new no_type(new JMLObjectSet {Integer c | (\exists no_type e; (machine.get_chatcontent().apply(null).domain().has(null)); e.equals(new Pair<BRelation<Integer,Integer>,ERROR>(null,machine.get_chatcontent().apply(null).apply(null).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))))))}))))}))) &&  machine.get_inactive().equals(\old(machine.get_inactive().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))))) &&  machine.get_toread().equals(\old(machine.get_toread().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))))) &&  machine.get_toreadcon().equals(\old(new no_type(new JMLObjectSet {Integer c | (\exists no_type e; (machine.get_toreadcon().domain().has(null) && ); e.equals(new Pair<BRelation<Integer,Integer>,ERROR>(null,machine.get_toreadcon().apply(null).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))))))}))); 
	 also
		requires !guard_delete_chat_session(dcs_u1,dcs_u2);
		assignable \nothing;
		ensures true; */
	public void run_delete_chat_session( Integer dcs_u1, Integer dcs_u2){
		if(guard_delete_chat_session(dcs_u1,dcs_u2)) {
			BRelation<Integer,Integer> active_tmp = machine.get_active();
			BRelation<Integer,Integer> muted_tmp = machine.get_muted();
			BRelation<Integer,Integer> chat_tmp = machine.get_chat();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,BRelation<Integer,Integer>> toreadcon_tmp = machine.get_toreadcon();

			machine.set_active(active_tmp.difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))));
			machine.set_muted(muted_tmp.difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))));
			machine.set_chat(chat_tmp.difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))));
		//!	machine.set_chatcontent(new no_type(new JMLObjectSet {Integer u | (\exists no_type e; (chatcontent_tmp.domain().has(null)); e.equals(new Pair<Pair<BRelation<Integer,Integer>,BRelation<Integer,BRelation<Integer,Integer>>>,ERROR>(null,new no_type(new JMLObjectSet {Integer c | (\exists no_type e; (chatcontent_tmp.apply(null).domain().has(null)); e.equals(new Pair<BRelation<Integer,Integer>,ERROR>(null,chatcontent_tmp.apply(null).apply(null).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))))))}))))}));
			machine.set_inactive(inactive_tmp.difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))));
			machine.set_toread(toread_tmp.difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))));
		//!	machine.set_toreadcon(new no_type(new JMLObjectSet {Integer c | (\exists no_type e; (toreadcon_tmp.domain().has(null) && ); e.equals(new Pair<BRelation<Integer,Integer>,ERROR>(null,toreadcon_tmp.apply(null).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(dcs_u1,dcs_u2))))))}));

			System.out.println("delete_chat_session executed dcs_u1: " + dcs_u1 + " dcs_u2: " + dcs_u2 + " ");
		}
	}

}

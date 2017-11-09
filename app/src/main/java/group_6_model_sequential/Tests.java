package group_6_model_sequential;

import eventb_prelude.BRelation;
import eventb_prelude.BSet;
import eventb_prelude.Pair;

public class Tests {

	/**
	 * Test for FIX for set comprehension bug.
	 */
	private static void test_del_ch_ses_chc() {
		machine3 machine = MachineWrapper.getInstance();

		Integer u1 = 1;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer u2 = 2;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer u3 = 3;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer u4 = 4;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));		
		
		System.out.println("u1: " + u1 + ", u2: " + u2);
		
		BSet<Integer> users = new BSet<Integer>();
		users.add(u1);
		users.add(u2);
		users.add(u3);
		machine.set_user(users);
		System.out.println("USERS:" + machine.get_user());
		machine.evt_create_chat_session.run_create_chat_session(u1, u2);
		
		machine.evt_chatting.run_chatting(10, u1, u2);
		
		machine.evt_chatting.run_chatting(1, u1, u2);
		
		System.out.println("CHC: " + machine.get_chatcontent());
		
		machine.evt_delete_chat_session.run_delete_chat_session(u1, u2);
		System.out.println("CHC: " + machine.get_chatcontent());
	}

	
	public static void main(String[] args) {
		test_del_ch_ses_chc();
	}

}

package group_6_model_sequential;

import eventb_prelude.BRelation;
import eventb_prelude.BSet;
import eventb_prelude.Pair;

public class Tests {

	/**
	 * Test for set comprehension bug.
	 */
	private static void test_del_ch_ses_chc() {
		machine3 machine = new machine3();

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
		System.out.println("TOREAD: " + machine.get_toreadcon());
		System.out.println("CHC: " + machine.get_chatcontent());
		System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());

		machine.evt_chatting.run_chatting(1, u1, u2);
		System.out.println("TOREAD: " + machine.get_toreadcon());
		System.out.println("CHC: " + machine.get_chatcontent());
		System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());

		machine.evt_unselect_chat.run_unselect_chat(u1, u2);
        //System.out.println("ACTIVE: " + machine.get_active());

		machine.evt_delete_chat_session.run_delete_chat_session(u1, u2);
		System.out.println("TOREAD: " + machine.get_toreadcon());
		System.out.println("CHC: " + machine.get_chatcontent());
		System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());

	    machine.evt_unselect_chat.run_unselect_chat(u2, u1);
	    //System.out.println("ACTIVE: " + machine.get_active());

		machine.evt_delete_chat_session.run_delete_chat_session(u2, u1);
		System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());
        System.out.println("ACTIVE: " + machine.get_active());
	}


	private static void test_change_chc() {
        machine3 machine = new machine3();

        Integer u1 = 1;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
        Integer u2 = 2;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
        Integer u3 = 3;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
        Integer u4 = 4;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

        System.out.println("u1: " + u1 + ", u2: " + u2);

        BSet<Integer> users = new BSet<Integer>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        machine.set_user(users);
        System.out.println("USERS:" + machine.get_user());
        machine.evt_create_chat_session.run_create_chat_session(u1, u2);
        machine.evt_create_chat_session.run_create_chat_session(u1, u3);
        machine.evt_create_chat_session.run_create_chat_session(u1, u4);

        BSet<Integer> ulist = new BSet<Integer>();
        ulist.add(u2);
        ulist.add(u3);
        ulist.add(u4);

        machine.evt_broadcast.run_broadcast(90, u1, ulist);

        System.out.println(machine.CONTENT.difference(machine.get_content()).has(500));
        System.out.println(machine.CONTENT.has(500));

        System.out.println(machine.CONTENT.size());
        System.out.println(machine.CONTENT);


        machine.evt_select_chat.run_select_chat(u1, u2);
        machine.evt_chatting.run_chatting(91, u1, u2);
        machine.evt_select_chat.run_select_chat(u2, u1);
        machine.evt_chatting.run_chatting(92, u2, u1);


        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());
        machine.evt_select_chat.run_select_chat(u2, u1);
        machine.evt_remove_content.run_remove_content(92, u2, u1, 3);
        machine.evt_select_chat.run_select_chat(u1, u2);
        machine.evt_delete_content.run_delete_content(92, u1, u2, 3);

        machine.evt_reading.run_reading(u1, u2);
        System.out.println("CHATS: " + machine.get_chat());
        System.out.println("TOREADCON: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());
        System.out.println("READ_SEQ: " + machine.get_readChatContentSeq());
    }


	public static void main(String[] args) {
		test_change_chc();
	}

}

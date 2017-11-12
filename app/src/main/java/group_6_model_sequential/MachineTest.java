package group_6_model_sequential;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eventb_prelude.BSet;
import static org.junit.Assert.*;

public class MachineTest {
    private machine3 machine;
    private final Integer u1 = 1;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
    private final Integer u2 = 2;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
    private final Integer u3 = 3;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
    private final Integer u4 = 4;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

    @Before
    public void setUp() {
        machine = new machine3();
        BSet<Integer> users = new BSet<Integer>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        machine.set_user(users);
    }

    @After
    public void tearDown(){
        machine = null;
        System.out.println();
        System.out.println();
    }

    /**
     * Test for set comprehension bug.
     */
    @Test
    public void test_del_ch_ses_chc() {
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

    @Test
    public void test_chatting(){
        System.out.println("TEST CHATTING");

        machine.evt_create_chat_session.run_create_chat_session(u1, u2);

        machine.evt_chatting.run_chatting(10, u1, u2);
        assertEquals("[(10,[(2,1)])]", machine.get_toreadcon().toString());
        assertEquals("[(1,[(10,[(1,2), (2,1)])])]", machine.get_chatcontent().toString());
        assertEquals("[(1,[(10,[(1,2), (2,1)])])]", machine.get_chatcontentseq().toString());
        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());

        machine.evt_chatting.run_chatting(1, u1, u2);
        assertEquals("[(1,[(2,1)]), (10,[(2,1)])]", machine.get_toreadcon().toString());
        assertEquals("[(1,[(1,[(1,2), (2,1)]), (10,[(1,2), (2,1)])])]", machine.get_chatcontent().toString());
        assertEquals("[(1,[(10,[(1,2), (2,1)])]), (2,[(1,[(1,2), (2,1)])])]", machine.get_chatcontentseq().toString());
        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());
    }

    @Test
    public void test_delete_chat_session(){
        System.out.println("TEST DELETE CHAT SESSION");

        machine.evt_create_chat_session.run_create_chat_session(u1, u2);
        machine.evt_chatting.run_chatting(10, u1, u2);
        machine.evt_chatting.run_chatting(1, u1, u2);
        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());

        machine.evt_create_chat_session.run_create_chat_session(u1, u3);    //to make 1 2 invative
//        machine.evt_chatting.run_chatting(22, u1, u3);

        machine.evt_delete_chat_session.run_delete_chat_session(u1, u2);
        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());

        assertEquals("[(1,[(2,1)]), (10,[(2,1)])]", machine.get_toreadcon().toString());
        assertEquals("[(1,[(1,[(2,1)]), (10,[(2,1)])])]", machine.get_chatcontent().toString());
        assertEquals("[(1,[(10,[(2,1)])]), (2,[(1,[(2,1)])])]", machine.get_chatcontentseq().toString());
    }

    @Test
    public void test_select_unselect_chat() {
        System.out.println("TEST SELECT/UNSELECT");

        System.out.println("USERS:" + machine.get_user());
        System.out.println("ACTIVE:" + machine.get_active());
        assertEquals("[]", machine.get_active().toString());
        machine.evt_create_chat_session.run_create_chat_session(u1, u2);
        System.out.println("ACTIVE:" + machine.get_active());
        assertEquals("[(1,2)]", machine.get_active().toString());
        machine.evt_unselect_chat.run_unselect_chat(u1,u2);
        System.out.println("ACTIVE:" + machine.get_active());
        assertEquals("[]", machine.get_active().toString());
        machine.evt_select_chat.run_select_chat(u2, u3);
        System.out.println("ACTIVE:" + machine.get_active());
        assertEquals("[]", machine.get_active().toString());
        machine.evt_select_chat.run_select_chat(u1, u2);
        System.out.println("ACTIVE:" + machine.get_active());
        assertEquals("[(1,2)]", machine.get_active().toString());

    }

    @Test
    public void test_remove_content(){//remove done by sender, u1 is the sender of 10
        System.out.println("TEST REMOVE CONTENT");
        machine.evt_create_chat_session.run_create_chat_session(u1, u2);
        machine.evt_create_chat_session.run_create_chat_session(u2, u1);
        machine.evt_chatting.run_chatting(10, u1, u2);
        machine.evt_chatting.run_chatting(20, u2, u1);

        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());

        machine.evt_remove_content.run_remove_content(10,u1,u2,1);

        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());
        assertEquals("[(10,[]), (20,[])]", machine.get_toreadcon().toString());
        assertEquals("[(1,[(10,[(2,1)])]), (2,[(20,[(1,2), (2,1)])])]", machine.get_chatcontent().toString());
        assertEquals("[(1,[(10,[(2,1)])]), (2,[(20,[(1,2), (2,1)])])]", machine.get_chatcontentseq().toString());

    }

    @Test
    public void test_delete_content(){//delete done by reciever, u1 is the reciever of 20
        System.out.println("TEST DELETE CONTENT");
        machine.evt_create_chat_session.run_create_chat_session(u1, u2);
        machine.evt_create_chat_session.run_create_chat_session(u2, u1);
        machine.evt_chatting.run_chatting(10, u1, u2);
        machine.evt_chatting.run_chatting(20, u2, u1);

        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());

        machine.evt_delete_content.run_delete_content(20,u1,u2,2);

        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());
        assertEquals("[(10,[]), (20,[])]", machine.get_toreadcon().toString());
        assertEquals("[(1,[(10,[(1,2), (2,1)])]), (2,[(20,[(2,1)])])]", machine.get_chatcontent().toString());
        assertEquals("[(1,[(10,[(1,2), (2,1)])]), (2,[(20,[(2,1)])])]", machine.get_chatcontentseq().toString());

    }

    @Test
    public void test_mute() {
        System.out.println("TEST MUTE");
        machine.evt_create_chat_session.run_create_chat_session(u1, u2);
        machine.evt_create_chat_session.run_create_chat_session(u2, u1);
        machine.evt_chatting.run_chatting(10, u1, u2);
        machine.evt_chatting.run_chatting(20, u2, u1);
        machine.evt_mute_chat.run_mute_chat(u1, u2);
        machine.evt_chatting.run_chatting(10, u1, u2);
        machine.evt_chatting.run_chatting(20, u2, u1);
        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());
        machine.evt_unmute_chat.run_unmute_chat(u1, u2);
        machine.evt_select_chat.run_select_chat(u1, u2);
        machine.evt_chatting.run_chatting(11, u1, u2);
        machine.evt_chatting.run_chatting(21, u2, u1);
        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());
        assertEquals("[(10,[]), (11,[]), (20,[]), (21,[])]", machine.get_toreadcon().toString());
        assertEquals("[(1,[(10,[(1,2), (2,1)]), (11,[(1,2), (2,1)])]), (2,[(20,[(1,2), (2,1)]), (21,[(1,2), (2,1)])])]", machine.get_chatcontent().toString());
        assertEquals("[(1,[(10,[(1,2), (2,1)])]), (2,[(20,[(1,2), (2,1)])]), (3,[(11,[(1,2), (2,1)])]), (4,[(21,[(1,2), (2,1)])])]", machine.get_chatcontentseq().toString());
    }

    @Test
    public void test_broadcast() {
        System.out.println("TEST BROADCAST");
        BSet<Integer> recievers = new BSet<Integer>();
        recievers.add(u2);
        recievers.add(u3);
        recievers.add(u4);
        machine.evt_create_chat_session.run_create_chat_session(u1, u4);
        machine.evt_create_chat_session.run_create_chat_session(u4, u1);
        machine.evt_select_chat.run_select_chat(u4, u1);
        machine.evt_broadcast.run_broadcast(10, u1, recievers);
        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());
        assertEquals("[(10,[(2,1), (3,1)])]", machine.get_toreadcon().toString());
        assertEquals("[(1,[(10,[(1,2), (1,3), (1,4), (2,1), (3,1), (4,1)])])]", machine.get_chatcontent().toString());
        assertEquals("[(1,[(10,[(1,2), (1,3), (1,4), (2,1), (3,1), (4,1)])])]", machine.get_chatcontentseq().toString());
    }

    @Test
    public void test_forward() {
        System.out.println("TEST FORWARD");
        BSet<Integer> recievers = new BSet<Integer>();
        recievers.add(u2);
        recievers.add(u3);
        recievers.add(u4);
        machine.evt_create_chat_session.run_create_chat_session(u1, u2);
        machine.evt_create_chat_session.run_create_chat_session(u1, u4);
        machine.evt_create_chat_session.run_create_chat_session(u4, u1);
        machine.evt_select_chat.run_select_chat(u4, u1);
        machine.evt_forward.run_forward(11, u1, recievers); //this should not be executed
        machine.evt_create_chat_session.run_create_chat_session(u1, u3);
        machine.evt_forward.run_forward(10, u1, recievers);
        System.out.println("TOREAD: " + machine.get_toreadcon());
        System.out.println("CHC: " + machine.get_chatcontent());
        System.out.println("CHC_SEQ: " + machine.get_chatcontentseq());
        assertEquals("[(10,[(2,1), (3,1)])]", machine.get_toreadcon().toString());
        assertEquals("[(1,[(10,[(1,2), (1,3), (1,4), (2,1), (3,1), (4,1)])])]", machine.get_chatcontent().toString());
        assertEquals("[(1,[(10,[(1,2), (1,3), (1,4), (2,1), (3,1), (4,1)])])]", machine.get_chatcontentseq().toString());
    }
}

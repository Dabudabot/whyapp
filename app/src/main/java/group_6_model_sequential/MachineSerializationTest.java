package group_6_model_sequential;

import org.junit.Before;
import org.junit.Test;

import Util.SimpleMapper;
import eventb_prelude.BSet;

import static org.junit.Assert.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
public class MachineSerializationTest {
    private machine3 machine;
//    private BroadcastWrapper broadcastWrapper = new BroadcastWrapper();
//    private ChattingWrapper chattingWrapper = new ChattingWrapper();
//    private CreateChatSessionWrapper createChatSessionWrapper = new CreateChatSessionWrapper();
//    private DeleteChatSessionWrapper deleteChatSessionWrapper = new DeleteChatSessionWrapper();
//    private DeleteContentWrapper deleteContentWrapper = new DeleteContentWrapper();
//    private ForwardWrapper forwardWrapper = new ForwardWrapper();
//    private MuteChatWrapper muteChatWrapper = new MuteChatWrapper();
//    private ReadingWrapper readingWrapper = new ReadingWrapper();
//    private RemoveContentWrapper removeContentWrapper = new RemoveContentWrapper();
//    private SelectChatWrapper selectChatWrapper = new SelectChatWrapper();
//    private UnmuteChatWrapper unmuteChatWrapper = new UnmuteChatWrapper();
//    private UnselectChatWrapper unselectChatWrapper = new UnselectChatWrapper();
    private final Integer u1 = 1;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
    private final Integer u2 = 2;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
    private final Integer u3 = 3;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
    private final Integer u4 = 4;//Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

//    @Mock
//    Activity activityMock;

    @Before
    public void setUp(){
        machine = new machine3();
    }

    @Test
    public void testEmpty(){
        String machineString = SimpleMapper.machineToString(machine);
        machine3 deserializedMachine = SimpleMapper.fromFirebaseString(new machine3(), machineString);
        compareMachines(machine, deserializedMachine);
    }

    @Test
    public void testUsers(){
        BSet<Integer> users = new BSet<Integer>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        machine.set_user(users);

        String machineString = SimpleMapper.machineToString(machine);
        machine3 deserializedMachine = SimpleMapper.fromFirebaseString(new machine3(), machineString);
        compareMachines(machine, deserializedMachine);
    }

    @Test
    public void testChatting(){
        BSet<Integer> users = new BSet<Integer>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        machine.set_user(users);

        machine.evt_create_chat_session.run_create_chat_session(u1, u2);

        machine.evt_chatting.run_chatting(10, u1, u2);

        String machineString = SimpleMapper.machineToString(machine);
        machine3 deserializedMachine = SimpleMapper.fromFirebaseString(new machine3(), machineString);
        compareMachines(machine, deserializedMachine);
    }

    @Test
    public void testMute(){
        BSet<Integer> users = new BSet<Integer>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        machine.set_user(users);


        machine.evt_create_chat_session.run_create_chat_session(u1, u2);
        machine.evt_create_chat_session.run_create_chat_session(u2, u1);
        machine.evt_chatting.run_chatting(10, u1, u2);
        machine.evt_chatting.run_chatting(20, u2, u1);
        machine.evt_mute_chat.run_mute_chat(u1, u2);
        machine.evt_chatting.run_chatting(10, u1, u2);
        machine.evt_chatting.run_chatting(20, u2, u1);
        machine.evt_unmute_chat.run_unmute_chat(u1, u2);
        machine.evt_select_chat.run_select_chat(u1, u2);
        machine.evt_chatting.run_chatting(11, u1, u2);
        machine.evt_chatting.run_chatting(21, u2, u1);

        String machineString = SimpleMapper.machineToString(machine);
        machine3 deserializedMachine = SimpleMapper.fromFirebaseString(new machine3(), machineString);
        compareMachines(machine, deserializedMachine);
    }

    @Test
    public void testBroadcast(){
        BSet<Integer> users = new BSet<Integer>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        machine.set_user(users);

        BSet<Integer> recievers = new BSet<Integer>();
        recievers.add(u2);
        recievers.add(u3);
        recievers.add(u4);

        machine.evt_create_chat_session.run_create_chat_session(u1, u4);
        machine.evt_create_chat_session.run_create_chat_session(u4, u1);
        machine.evt_select_chat.run_select_chat(u4, u1);
        machine.evt_broadcast.run_broadcast(10, u1, recievers);

        String machineString = SimpleMapper.machineToString(machine);
        machine3 deserializedMachine = SimpleMapper.fromFirebaseString(new machine3(), machineString);
        compareMachines(machine, deserializedMachine);
    }

    @Test
    public void testForward(){
        BSet<Integer> users = new BSet<Integer>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        machine.set_user(users);

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

        String machineString = SimpleMapper.machineToString(machine);
        machine3 deserializedMachine = SimpleMapper.fromFirebaseString(new machine3(), machineString);
        compareMachines(machine, deserializedMachine);
    }

    private void compareMachines(machine3 m1, machine3 m2){
        assertEquals(m1.get_user(), m2.get_user());
        assertEquals(m1.get_content(), m2.get_content());
        assertEquals(m1.get_chat(), m2.get_chat());
        assertEquals(m1.get_active(), m2.get_active());
        assertEquals(m1.get_chatcontent(), m2.get_chatcontent());
        assertEquals(m1.get_muted(), m2.get_muted());
        assertEquals(m1.get_toread(), m2.get_toread());
        assertEquals(m1.get_inactive(), m2.get_inactive());
        assertEquals(m1.get_toreadcon(), m2.get_toreadcon());
        assertEquals(m1.get_owner(), m2.get_owner());
        assertEquals(m1.get_chatcontentseq(), m2.get_chatcontentseq());
        assertEquals(m1.get_readChatContentSeq(), m2.get_readChatContentSeq());
        assertEquals(m1.get_contentsize(), m2.get_contentsize());
    }
}

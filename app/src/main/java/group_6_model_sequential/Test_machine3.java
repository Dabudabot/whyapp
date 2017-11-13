package group_6_model_sequential;
import java.util.Random;
import Util.Utilities;
import eventb_prelude.*;

public class Test_machine3{


	static Random rnd = new Random();
	static Integer max_size_BSet = 10;
	Integer min_integer = Utilities.min_integer;
	Integer max_integer = Utilities.max_integer;

	public Integer GenerateRandomInteger(){
		BSet<Integer> S =  new BSet<Integer>(
				new Enumerated(min_integer, max_integer)
				);
		/** User defined code that reflects axioms and theorems: Begin **/

		/** User defined code that reflects axioms and theorems: End **/

		return (Integer) S.toArray()[rnd.nextInt(S.size())];
	}

	public boolean GenerateRandomBoolean(){
		boolean res = (Boolean) BOOL.instance.toArray()[rnd.nextInt(2)];

		/** User defined code that reflects axioms and theorems: Begin **/

		/** User defined code that reflects axioms and theorems: End **/

		return res;
	}


	public BSet<Integer> GenerateRandomIntegerBSet(){
		int size = rnd.nextInt(max_size_BSet);
		BSet<Integer> S = new BSet<Integer>();
		while (S.size() != size){
			S.add(GenerateRandomInteger());
		}

		/** User defined code that reflects axioms and theorems: Begin **/

		/** User defined code that reflects axioms and theorems: End **/

		return S;
	}

	public BSet<Boolean> GenerateRandomBooleanBSet(){
		int size = rnd.nextInt(2);
		BSet<Boolean> res = new BSet<Boolean>();
		if (size == 0){
			res = new BSet<Boolean>(GenerateRandomBoolean());
		}else{
			res = new BSet<Boolean>(true,false);
		}

		/** User defined code that reflects axioms and theorems: Begin **/

		/** User defined code that reflects axioms and theorems: End **/

		return res;
	}

	public BRelation<Integer,Integer> GenerateRandomBRelation(){
		BRelation<Integer,Integer> res = new BRelation<Integer,Integer>();
		int size = rnd.nextInt(max_size_BSet);
		while (res.size() != size){
			res.add(
					new Pair<Integer, Integer>(GenerateRandomInteger(), GenerateRandomInteger()));
		}
		/** User defined code that reflects axioms and theorems: Begin **/

		/** User defined code that reflects axioms and theorems: End **/

		return res;
	}

	public static void main(String[] args){
		Test_machine3 test = new Test_machine3();

		/** User defined code that reflects axioms and theorems: Begin **/
		/** User defined code that reflects axioms and theorems: End **/

		machine3 machine = new machine3();
		int n = 12; //the number of events in the machine
		//Init values for parameters in event: create_chat_session
		Integer ccs_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer ccs_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		//Init values for parameters in event: select_chat
		Integer sc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer sc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		//Init values for parameters in event: chatting
		Integer ch_c = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer ch_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer ch_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		//Init values for parameters in event: delete_content
		Integer dc_c = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer dc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer dc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer dc_i = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		//Init values for parameters in event: remove_content
		Integer rc_c = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer rc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer rc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer rc_i = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		//Init values for parameters in event: delete_chat_session
		Integer dcs_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer dcs_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		//Init values for parameters in event: mute_chat
		Integer mc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer mc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		//Init values for parameters in event: unmute_chat
		Integer umc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer umc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		//Init values for parameters in event: broadcast
		Integer b_c = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer b_u = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		BSet<Integer> b_ul = Utilities.someSet(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		//Init values for parameters in event: forward
		Integer f_c = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer f_u = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		BSet<Integer> f_ul = Utilities.someSet(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		//Init values for parameters in event: unselect_chat
		Integer usc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer usc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		//Init values for parameters in event: reading
		Integer r_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		Integer r_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));

		while (true){
			switch (rnd.nextInt(n)){
			case 0: if (machine.evt_create_chat_session.guard_create_chat_session(ccs_u1,ccs_u2))
				machine.evt_create_chat_session.run_create_chat_session(ccs_u1,ccs_u2); break;
			case 1: if (machine.evt_select_chat.guard_select_chat(sc_u1,sc_u2))
				machine.evt_select_chat.run_select_chat(sc_u1,sc_u2); break;
			case 2: if (machine.evt_chatting.guard_chatting(ch_c,ch_u1,ch_u2))
				machine.evt_chatting.run_chatting(ch_c,ch_u1,ch_u2); break;
			case 3: if (machine.evt_delete_content.guard_delete_content(dc_c,dc_u1,dc_u2,dc_i))
				machine.evt_delete_content.run_delete_content(dc_c,dc_u1,dc_u2,dc_i); break;
			case 4: if (machine.evt_remove_content.guard_remove_content(rc_c,rc_u1,rc_u2,rc_i))
				machine.evt_remove_content.run_remove_content(rc_c,rc_u1,rc_u2,rc_i); break;
			case 5: if (machine.evt_delete_chat_session.guard_delete_chat_session(dcs_u1,dcs_u2))
				machine.evt_delete_chat_session.run_delete_chat_session(dcs_u1,dcs_u2); break;
			case 6: if (machine.evt_mute_chat.guard_mute_chat(mc_u1,mc_u2))
				machine.evt_mute_chat.run_mute_chat(mc_u1,mc_u2); break;
			case 7: if (machine.evt_unmute_chat.guard_unmute_chat(umc_u1,umc_u2))
				machine.evt_unmute_chat.run_unmute_chat(umc_u1,umc_u2); break;
			case 8: if (machine.evt_broadcast.guard_broadcast(b_c,b_u,b_ul))
				machine.evt_broadcast.run_broadcast(b_c,b_u,b_ul); break;
			case 9: if (machine.evt_forward.guard_forward(f_c,f_u,f_ul))
				machine.evt_forward.run_forward(f_c,f_u,f_ul); break;
			case 10: if (machine.evt_unselect_chat.guard_unselect_chat(usc_u1,usc_u2))
				machine.evt_unselect_chat.run_unselect_chat(usc_u1,usc_u2); break;
			case 11: if (machine.evt_reading.guard_reading(r_u1,r_u2))
				machine.evt_reading.run_reading(r_u1,r_u2); break;
			}
			ccs_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			ccs_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			sc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			sc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			ch_c = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			ch_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			ch_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			dc_c = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			dc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			dc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			dc_i = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			rc_c = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			rc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			rc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			rc_i = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			dcs_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			dcs_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			mc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			mc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			umc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			umc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			b_c = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			b_u = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			b_ul = Utilities.someSet(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			f_c = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			f_u = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			f_ul = Utilities.someSet(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			usc_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			usc_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			r_u1 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
			r_u2 = Utilities.someVal(new BSet<Integer>((new Enumerated(1,Utilities.max_integer))));
		}
	}

}

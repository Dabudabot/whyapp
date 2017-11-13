package Util;
import java.util.Random;
import eventb_prelude.*;

public class Utilities {
	//Class Utilities implements the Bakery Algorithm and
	// the support for non-determinism assignment

	public volatile static int[] turn; // ticket number for event i; if turn[i] == 0 then event i has no turn allocated yet
	public volatile static boolean[] want;

	public Utilities(int size){
		want = new boolean[size];
		turn = new int[size];
		for (int i = 0; i < size; i++){
			turn[i] = 0;
		}
		for (int i = 0; i < size; i++){
			want[i] = false;
		}
	}

	public static Integer max(int[] array){
		Integer m;
		if (array.length <= 0)
			return null;
		else
			m = array[0];

		for(int i=0;i<array.length;i++){
			if (array[i] > m)
				m = array[i];
		}
		return m;
	}

	public static void lock(int eid){
		want[eid] = true;
		turn[eid] = max(turn) + 1;
		want[eid] = false;
		for (int j=0; j < turn.length; j++) {
			while (want[j]) {}
			while ((turn[j] != 0) &&
				((turn[j] < turn[eid]) ||
				((turn[j] == turn[eid]) && ((j < eid))))){}
		}
	}

	public static void unlock(int eid){
		turn[eid] = 0;
	}


	public static Integer min_integer = -100;
	public static Integer max_integer = 100;
	public static <T> T someVal(BSet<T> s){
		if (s.isEmpty()){
			return null;
		}else if (s.size() == 1){
			return s.choose();
		}
		Random rnd = new Random();
		int value = rnd.nextInt(s.size()-1);
		if (s instanceof BRelation){
			return (T) (((BRelation) s).toSet().toArray()[value]); 
		}else{
			return (T) (s.toArray()[value]); 
		}
	}
	public static <T> BSet<T> someSet(BSet<T> s){
		if (s.isEmpty()){
			return null;
		}else if (s.size() == 1){
			return s;
		}
		BSet<T> res = new BSet<T>();
		Random rnd = new Random();
		int iterations = rnd.nextInt(Utilities.max_integer);
		for (int i=0;i<iterations;i++){
			int value = rnd.nextInt(s.size()-1);
			if (s instanceof BRelation){
				res.add((T) (((BRelation) s).toSet().toArray()[value]));
			}else{
				res.add((T) (s.toArray()[value]));
			}
		}
		return res;
	}

	public static <T> T non_det_assignment(T var, boolean predicate){
		//No supported yet
		return null;
	}
}

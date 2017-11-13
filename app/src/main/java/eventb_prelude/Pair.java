package eventb_prelude;


/** representation of a pair of elements in Java
 * @author Tim Wahls & Nestor Catano & Victor Rivera
 */
import java.io.Serializable;


public class Pair<K,V> implements Comparable<Pair<K,V>>, Serializable {
	/*@ spec_public */ private final K fst;
	/*@ spec_public */ private final V snd;
	
	/*@ spec_public */ private final boolean fstComparable;
	/*@ spec_public */ private final boolean sndComparable;
	
	
	public Pair(K k, V v) {
		fst = k;
		snd = v;
		fstComparable = (k instanceof Comparable);
		sndComparable = (v instanceof Comparable);
	}

	
	/*@ requires true;
	 	assignable \nothing;
	 	ensures \result == snd;
	*/	
	public /*@ pure */ V snd() {
		return snd;
	}
	
	/*@ requires true;
 		assignable \nothing;
 		ensures \result == fst;
	 */	
	public /*@ pure */ K fst() {
		return fst;
	}
	
	public int compareTo(Pair<K,V> pair) {
		if(fstComparable) {
			int i = ((Comparable<K>) fst).compareTo(pair.fst);
			if (i > 0) return 1;
			if (i < 0) return -1;
		}
		
		if(sndComparable) {
			int i = ((Comparable<V>) snd).compareTo(pair.snd);
			if (i > 0) return 1;
			if (i < 0) return -1;
		}
		
		return 0;
	}	
	
	/*@ requires obj != null && (obj instanceof Pair);
 		assignable \nothing;
    	ensures fst().equals(((Pair<K,V>) obj).fst()) && snd().equals(((Pair<K,V>) obj).snd);
    	also
    	requires !(obj != null && (obj instanceof Pair));
 		assignable \nothing;
    	ensures false;
 */
	public /*@ pure */ boolean equals(Object obj) {
		if (obj != null && (obj instanceof Pair)) {
			Pair<K,V> pair = (Pair<K,V>) obj;
			return fst.equals(pair.fst) && snd.equals(pair.snd);
		}
		return false;
	}
	
	public String toString() {
		return "("+fst+","+snd+")";
	}

}
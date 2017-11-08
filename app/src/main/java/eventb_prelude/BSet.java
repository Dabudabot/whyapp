package eventb_prelude;

/** a class to model B sets in Java 
 * @author Tim Wahls & Nestor Catano & Victor Rivera
 */

import java.util.Iterator;
import java.util.TreeSet;
//@model import org.jmlspecs.models.JMLIterator;
//@model import org.jmlspecs.models.JMLObjectSet;

public class BSet<E> extends TreeSet<E> implements Comparable<E> {

	/*@ public static invariant EMPTY.int_size() == 0; */

	public static BSet EMPTY = new BSet();

	/*@ requires true;
	 	assignable \nothing;
	    ensures \result != null && \result.int_size() == 1 && \result.has(elem);
	 */
	public /*@ pure */ static <EE> BSet<EE> singleton(EE elem) {
		BSet<EE> res = new BSet<EE>();
		res.add(elem);
		return res;
	}
	
	/*@ public model BSet(JMLObjectSet S){
			this();
			JMLIterator iter = S.iterator();
			while (iter.hasNext()){
				add((E) iter.next());
			}
		}*/

	/*@ requires true;
	 	assignable \nothing;
	  	ensures \result == this.size();
	 */
	public /*@ pure */ int int_size(){
		return this.size();
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures \result <==> this.has(obj);
	*/
	public /*@ pure */ boolean has(Object obj) {
		return contains(obj);
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures \result <==> this.int_size() == 1;
	 */
	public /*@ pure */ boolean isSingleton(){
		return (size() == 1);
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures this.isEmpty();
	 */
	public /*@ pure */ BSet() {	}
		
	/*@ requires true;
	 	assignable this.*;
	 	ensures (\forall int i; i >= 0 && i < elems.length ==> this.has(elems[i]));
	 */
	public BSet(E ... elems) {
		this();
		for (E e : elems) {
			add(e);
		}
	}

	/*@ requires true;
	 	assignable this.*;
	 	ensures (\forall E e; elems.contains(e) ==> this.has(e));
	*/
	public BSet(TreeSet<E> elems) {
		this();
		for (E e : elems)
			add(e);
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures \result.equals(this.insert(elem));
	*/
	public /*@ pure */ BSet<E> insert(E elem) {
		BSet<E> res = new BSet<E>();
		res.addAll(this);

		res.add(elem);
		return res;
	}

	/*@ requires true;
	 	assignable this.*;
	 	ensures this.equals(\old(this).insert(elem));
	*/
	public void insertInPlace(E elem) {
		add(elem);
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures !\result.contains(elem);
	 */
	public /*@ pure */ BSet<E> delete(E elem) {
		BSet<E> res = new BSet<E>();
		for(E e : this)
			if(!e.equals(elem))
				res.add(e);
		return res;
	}

	/*@ requires true;
	 	assignable this.*;
	 	ensures !this.contains(elem);
	 */
	public void deleteInPlace(E elem) {
		remove(elem);
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures \result <==> (\forall E e; c.contains(e); this.has(e));
	*/
	public /*@ pure */ boolean containsAll(java.util.Collection<?> c) {
		return super.containsAll(c);
	}

	/*@ 	also
	  	requires true;
	 	assignable \nothing;
    	  ensures \result <==> obj instanceof BSet && 
              (\forall E e; ((BSet) obj).has(e) <==> this.has(e));
    */
	public /*@ pure */ boolean equals(Object obj) {
		if (obj instanceof BRelation) {
			BSet s = ((BRelation) obj).toSet();
			return(size() == s.size() && containsAll(s) && s.containsAll(this));
		} 
		else if (obj instanceof BSet) {
			BSet s = (BSet) obj;

			return(size() == s.size() && containsAll(s) && s.containsAll(this));
		}
		else return false;
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures \result <==> (\forall E e; this.has(e); s2.contains(e));
	*/
	public /*@ pure */ boolean isSubset(TreeSet<E> s2) {
		if (s2 instanceof INT) {
			return true;
		} else if (s2 instanceof NAT || s2 instanceof NAT1) {
			for (Object obj : this) {
				Integer i = (Integer) obj;
				if (i < 0) return false;
				else if (i == 0 && s2 instanceof NAT1) return false;
			}
			return true;
		} else {
			return s2.containsAll(this);
		}
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures \result <==> this.isSubset(s2) && !this.equals(s2);
	*/
	public /*@ pure */ boolean isProperSubset(BSet<E> s2) {
		if (s2 instanceof INT || s2 instanceof NAT || s2 instanceof NAT1) {
			return isSubset(s2);
		} else {
			return s2.containsAll(this) && !this.containsAll(s2);
		}
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures \result <==> s2.isSubset(this);
	*/
	public /*@ pure */ boolean isSuperset(BSet<E> s2) {
		return s2.isSubset(this);
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures \result <==> s2.isProperSubset(this);
	*/
	public /*@ pure */ boolean isProperSuperset(BSet<E> s2) {
		return s2.isProperSubset(this);
	}


	/*@ requires true;
	  	assignable \nothing;
	  	ensures \result.equals(this.first());
	 */
	public /*@ pure */ E choose() {
		//return iterator().next();
		return first();
	}
	
	/*@	requires true;
	 	assignable \nothing;
	 	ensures (\forall E e; \result.has(e) <==> this.has(e) || s2.contains(e));
	 */
	public /*@ pure */ BSet<E> union(TreeSet<E> s2) {
		if (s2 instanceof INT) {
			return (BSet<E>) s2;
		} else if (s2 instanceof NAT || s2 instanceof NAT1) {
			for (Object obj : this) {
				Integer i = (Integer) obj;
				if (i < 0) throw new UnsupportedOperationException("Error: can't union with NAT.");
				else if (i == 0 && s2 instanceof NAT1) throw new UnsupportedOperationException("Error: can't union with NAT1.");
			}
			return (BSet<E>) s2;
		} else {
			BSet<E> res = new BSet<E>();
			res.addAll(this);

			for (E e : s2)
				res.add(e);
			return res;
		}
	}

	/*@ requires true;
	 	assignable this.*;
	 	ensures this.equals(\old(this).union(s2));
	 */
	public void unionInPlace(TreeSet<E> s2) {
		if (s2 instanceof INT) {
			clear();
			addAll(s2);
		} else if (s2 instanceof NAT || s2 instanceof NAT1) {
			for (Object obj : this) {
				Integer i = (Integer) obj;
				if (i < 0) throw new UnsupportedOperationException("Error: can't union with NAT.");
				else if (i == 0 && s2 instanceof NAT1) throw new UnsupportedOperationException("Error: can't union with NAT1.");
			}
			clear();
			addAll(s2);
		} else {				
			for (E e : s2)
				add(e);
		}
	}

	/*@ requires true;
	  	assignable \nothing;
	  	ensures \result.isEmpty();
	*/
	public /*@ pure */ static <E> BSet<E> union() {
		return new BSet<E>();
	}

	/*@	requires true;
	 	assignable \nothing;
	 	ensures (\forall E e; \result.has(e) <==>
					(\exists int i; 0 <= 1 && i < sets.length; sets[i].has(e)));
	*/
	public /*@ pure */ static <E> BSet<E> union(BSet<E> ... sets) {
		BSet<E> res = new BSet<E>();
		for (BSet<E> set : sets) {
			res.unionInPlace(set);
		}
		return res;
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures (\forall E e; \result.has(e) <==> 
					(\exists int i; 0 <= 1 && i < elems.length; elems[i].equals(e)));*/
	public /*@ pure */ static <E> BSet<E> extension(E ... elems) {
		BSet<E> res = new BSet<E>();
		for (E e : elems) {
			res.add(e);
		}
		return res;
	}	

	/*@ requires true;
	 	assignable \nothing;
	 	ensures (\forall E e; \result.has(e) <==> this.has(e) && s2.contains(e));*/
	public /*@ pure */ BSet<E> intersection(TreeSet<E> s2) {
		if (s2 instanceof INT) {
			BSet<E> res = (BSet<E>) clone();
			return res;
		} else if (s2 instanceof NAT || s2 instanceof NAT1) {
			BSet<E> res = (BSet<E>) clone();
			for (E obj : this) {
				Integer i = (Integer) obj;
				if (i < 0) res.remove(obj);
				else if (i == 0 && s2 instanceof NAT1) res.remove(obj);				
			}
			return res;
		} else {
			BSet<E> res = new  BSet<E>(); // the empty set

			for (E e : s2) { 
				if (contains(e)) {
					res.add(e);
				}
			}
			return res;
		}
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures (\forall E e; this.has(e) <==> \old(this).has(e) && s2.contains(e));*/
	public /*@ pure */ void intersectionInPlace(TreeSet<E> s2) {
		if (s2 instanceof INT) {
			//
		} else if (s2 instanceof NAT || s2 instanceof NAT1) {
			for (E obj : this) {
				Integer i = (Integer) obj;
				if (i < 0) remove(obj);
				else if (i == 0 && s2 instanceof NAT1) remove(obj);				
			}
		} else {
			clear(); //TODO why clear?
			for (E e : s2) { 
				if (contains(e))
					add(e);
			}
		}
	}

	/*@	requires true;
	 	assignable \nothing;
	 	ensures (\forall E e; \result.has(e) <==>
          (\forall int i; 0 <= 1 && i < sets.length; sets[i].has(e)));
    */
	public /*@ pure */ static <E> BSet<E> intersection(BSet<E> ... sets) {
		BSet<E> res = sets[0];
		for (int i = 1; i < sets.length; i++) {
			res.intersectionInPlace(sets[i]);
		}
		return res;
	}

	/*@ public exceptional_behavior
	 	requires true;
	 	assignable \nothing;
	    signals (IllegalStateException);
	*/
	public /*@ pure */ static <E> BSet<E> intersection() {
		throw new IllegalStateException("Error: generalized intersection over 0 sets.");
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures (\forall E e; \result.has(e) <==> this.has(e) && !s2.contains(e));
	*/
	public /*@ pure */ BSet<E> difference(TreeSet<E> s2) {
		if (s2 instanceof INT) {
			return new BSet<E>(); // the empty set
		} else if (s2 instanceof NAT || s2 instanceof NAT1) {
			BSet<E> res = (BSet<E>) clone();
			for (E obj : this) {
				Integer i = (Integer) obj;
				if (i > 0) res.remove(obj);
				else if (i == 0 && s2 instanceof NAT) res.remove(obj);
			}
			return res;
		} else {
			BSet<E> res = (BSet<E>) clone();

			for (E e : s2) 
				if (has(e)) res.remove(e);
			return res;
		}
	}

	/*@ requires true;
	 	assignable this.*;
	 	ensures (\forall E i; s2.contains(i) ==> !this.has(i));
	 */
	public void differenceInPlace(TreeSet<E> s2) {
		if (s2 instanceof INT) {
			clear(); // the empty set
		} else if (s2 instanceof NAT || s2 instanceof NAT1) {
			for (E obj : this) {
				Integer i = (Integer) obj;
				if (i > 0) remove(obj);
				else if (i == 0 && s2 instanceof NAT) remove(obj);
			}
		} else {			
			for (E e : s2) 
				if (contains(e)) remove(e);
		}
	}

	/*@ public exceptional_behavior
	 	requires true;
	 	assignable \nothing;
		signals (UnsupportedOperationException);*/
	public /*@ pure */ TreeSet<TreeSet<E>> powerSet() {
		throw new UnsupportedOperationException("Error: do not call powerSet through a BSet.");
	}

	/*@ also
	  	requires true;
	  	assignable \nothing;
		ensures \result.equals(this.toString());*/
	public /*@ pure */ String toString() {
		return super.toString();
	}

	/*@ also
	 		requires true;
	 		assignable \nothing;
			ensures \result.equals(this.toArray());*/
	public /*@ pure */ Object [] toArray() {
		return super.toArray();
	}

	/*@ also
	 	requires true;
	 	assignable \nothing;
		ensures \result.equals(this.iterator());*/
	public /*@ pure */ Iterator<E> iterator() {
		return super.iterator();
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures (\forall BSet<E> es; \result.has(es) <==> es.isSubset(this));
	 */
	public /*@ pure */ BSet<BSet<E>> pow() {
		BSet<BSet<E>> ps = new BSet<BSet<E>>();
		ps.add(new BSet<E>());   // add the empty set 

		// for every item in the original set
		for(E item : this) {	
			BSet<BSet<E>> newPs = new BSet<BSet<E>>();

			for (BSet<E> subset : ps) {
				// copy all of the current powerset's subsets
				newPs.add(subset);

				// plus the subsets appended with the current item
				BSet<E> newSubset = new BSet<E>(subset);
				newSubset.add(item);
				newPs.add(newSubset);
			}
			ps = newPs;
		}

		return ps;
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures (\forall BSet<E> es; \result.has(es) <==> 
	  				es.isSubset(this) && !es.isEmpty());
	*/
	public /*@ pure */ BSet<BSet<E>> pow1() {
		BSet<BSet<E>> res = pow();
		BSet<E> empty = new BSet<E>();
		res.remove(empty);

		return res;
	}

	/* requires true;
	 	assignable \nothing; 
	 	ensures \result <==> 
	  		!(this instanceof INT || this instanceof NAT || this instanceof NAT1);*/
	public /*@ pure */ boolean finite() {
		//TODO to check
		return true;
	}

	/*TODO ensures \result <==> (\forall int i; 0 <= i && i < parts.length; 
	!(\exists int j; 0 <= j && j < parts.length; i != j && !parts[i].intersection(parts[j]).isEmpty()))
	&& (\forall E e; this.has(e) <==> (\exists int i; 0 <= i && i < parts.length; parts[i].has(e)));*/
	public /*@ pure */ static <E> boolean partition(BSet<E> ... parts) {
		BSet<E> tmp = new BSet<E>(); // the empty set

		if(parts.length == 0) return false;
		if(parts.length == 1) return true;

		for(BSet<E> part : parts) {
			if(!tmp.intersection(part).isEmpty()) return false;
			tmp.unionInPlace(part);
		}
		return true;
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures \result.equals(last());
	 */
	public /*@ pure */ E max() {
		return last();
	}

	/*@ requires true;
	 	assignable \nothing;
	 	ensures \result.equals(last());
	 */
	public /*@ pure */ E min() {
		return first();
	}

	public boolean contains(Object obj) {
		return super.contains(obj);
	}

	public int compareTo(Object o) {
		if(o instanceof BSet) {
			BSet<E> s = (BSet<E>) o;
			Iterator<E> i1 = this.iterator();
            Iterator<E> i2 = ((BSet<E>) o).iterator();
            while (i1.hasNext() && i2.hasNext()) {
                int res = ((Comparable<E>) i1.next()).compareTo(i2.next());
                if (res != 0) return res;
            }
            if (i1.hasNext()) return 1;
            if (i2.hasNext()) return -1;
            return 0;
		}
		throw new UnsupportedOperationException("Error: can only compare sets.");
	}
	
}
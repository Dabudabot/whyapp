package eventb_prelude;


/** representation of the EventB type INT as a BSet
 * @author Tim Wahls & Nestor Catano & Victor Rivera
 */

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;


public /*@ pure */ class INT extends BSet<Integer> {
	public static final INT instance = new INT();
	private java.util.Random rand = new java.util.Random();
	
	/*@ public initially (\forall int iv; INT.instance.has(iv)); */
	
	public INT() {}
	
	/*@ also requires true;
	 	assignable \nothing;
	    ensures \result <==> obj instanceof Integer;
	 */
	public /*@ pure */ boolean has(Object obj) {
		return obj instanceof Integer;
	}
	
	/*@ also requires true;
	 	assignable \nothing;
        ensures \result <==> (\forall Integer i; c.contains(i); this.has(i));
     */
	public /*@ pure */ boolean containsAll(java.util.Collection<?> c) {
		return true;
	}
	
	/*@ also requires other instanceof BSet;
	    assignable \nothing;
        ensures \result <==> (\forall Integer i; this.has(i) <==> ((BSet) other).has(i));
     */
	public /*@ pure */ boolean equals(Object other) {
		return other instanceof INT;
	}
	
	/*@ also 
	 	requires true;
	 	assignable \nothing;
        ensures \result <==> false;
     */
    public /*@ pure */ boolean isEmpty() {
    	return false;
    }
    
    /*@ also public exceptional_behavior
        requires true;
        assignable \nothing;
        signals (UnsupportedOperationException);
     */
    public /*@ pure */ int int_size() {
        throw new UnsupportedOperationException("Error: size of the integers not representable");
    }  
    
    /*@ also requires true;
     	assignable \nothing;
        ensures \result == "INT".hashCode();
     */
    public /*@ pure */ int hashCode() {
    	return "INT".hashCode();
    }
    
    /* inherited specifications should be correct for all set operations */
    
    public boolean isSubset(/*@ non_null @*/ TreeSet<Integer> s2) {
    	return s2 instanceof INT;
    }
    
    public boolean isProperSubset(/*@ non_null @*/ TreeSet<Integer> s2) {
    	return false;
    }
    
    public  boolean isSuperset(/*@ non_null @*/ TreeSet<Integer> s2) {
    	return true;
    }
    
    public boolean isProperSuperset(/*@ non_null @*/ TreeSet<Integer> s2) {
    	return !(s2 instanceof INT);
    }
    
    public Integer choose() {
    	return rand.nextInt();
    }
    
    public Object clone() {
    	return this;
    }
    
    /*@ also public exceptional_behavior
     	requires true;
        assignable \nothing;
        signals (UnsupportedOperationException);
     */
    public /*@ pure */ BSet<Integer> insert(Integer i) {
    	throw new UnsupportedOperationException("Error: can't insert into the integers");
    }
    
    /*@ also public exceptional_behavior
        requires true;
        assignable \nothing;
        signals (UnsupportedOperationException);
     */
    public /*@ pure */ BSet<Integer> remove(Integer i) {
    	throw new UnsupportedOperationException("Error: can't remove from the integers");
    }
    
    public BSet<Integer> intersection(TreeSet<Integer> s2) {
    	if (s2 instanceof BSet) {
    		return (BSet<Integer>) s2;
    	} else {
    		return new BSet<Integer>(s2);
    	}
    }
	 
    public BSet<Integer> union(TreeSet<Integer> s2) {
    	return this;
    }
    
    public BSet<Integer> difference(TreeSet<Integer> s2) {
    	if (s2 instanceof INT) {
    		return new BSet<Integer>();
    	} else {
    		throw new UnsupportedOperationException("Error: difference from the integers is infinite.");
    	}
    }
    
    /*@ also requires true;
     	assignable \nothing;
        ensures \result.equals("INT");
     */
    public /*@ pure */ String toString() {
    	return "INT";
    }
    
	public Iterator<Integer> toBag() {
    	throw new UnsupportedOperationException("Error: a bag cannot contain the integers.");		
	}
    
    public ArrayList<Integer> toSequence() {
    	throw new UnsupportedOperationException("Error: a sequence cannot contain the integers.");		
	}
	
    /*@ also public exceptional_behavior
        requires true;
        assignable \nothing;
        signals (UnsupportedOperationException);
     */
	public /*@ pure */ Object [] toArray() {
    	throw new UnsupportedOperationException("Error: an array cannot contain the integers.");		
	}
	
    /*@ also public exceptional_behavior
        requires true;
        assignable \nothing;
        signals (UnsupportedOperationException);
     */
	public /*@ pure */ Iterator<Integer> iterator() {
	   	throw new UnsupportedOperationException("Error: the integers are not iterable.");		
	}

	/*@ also requires true;
	 	assignable \nothing;
	    ensures \result <==> false;
	 */
    public /*@ pure */ boolean finite() {
    	return false;
    }
    
    /*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
     */
    public /*@ pure */ BSet<BSet<Integer>> pow() {
    	throw new UnsupportedOperationException("Error: can't compute POW(INT).");    	
    }
    
    /*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
     */
    public BSet<BSet<Integer>> pow1() {
    	throw new UnsupportedOperationException("Error: can't compute POW1(INT).");    	
    }
    
    /*@ also requires true;
     	assignable \nothing;
        ensures \result <==> parts.length == 1 && parts[0] instanceof INT;
     */
    public /*@ pure */ boolean INT_partition(BSet<Integer> ... parts) {
    	return parts.length == 1 && parts[0] instanceof INT;
    }
    
    /*@ also public exceptional_behavior
        requires true;
        assignable \nothing;
        signals (UnsupportedOperationException);
     */
    public /*@ pure */ Integer max() {
    	throw new UnsupportedOperationException("Error: can't compute max of INT.");    	    	
    }
    
    /*@ also public exceptional_behavior
        requires true;
        assignable \nothing;
        signals (UnsupportedOperationException);
     */
    public /*@ pure */ Integer min() {
    	throw new UnsupportedOperationException("Error: can't compute min of INT.");    	    	
    }
}
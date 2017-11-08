package eventb_prelude;


/** representation of the EventB type NAT1 as a BSet
 * @author Tim Wahls & Nestor Catano & Victor Rivera
 */


import java.util.ArrayList;
import java.util.Iterator;


public class NAT1 extends BSet<Integer> {
	public static final NAT1 instance = new NAT1();
	
	private NAT1() {}
	
	
	/*@ also requires true;
	 	assignable \nothing;
        ensures \result <==> obj instanceof Integer && ((Integer) obj) > 0;
     */
	public /*@ pure */ boolean has(Object obj) {
		return INT.instance.has(obj) && ((Integer) obj) > 0;
	}
	
	/*@ also requires true;
	 	assignable \nothing;
        ensures \result <==> (\forall Integer i; c.contains(i); this.has(i));
     */
	public /*@ pure */ boolean containsAll(java.util.Collection<?> c) {
		for (Object i : c) {
			if (i instanceof Integer) {
				if ((Integer)i <= 0) return false;
			} else return false;
		}
		return true;
	}
	
	/*@ also requires true;
	 	assignable \nothing;
        ensures \result <==> obj instanceof BSet && (\forall Integer i; this.has(i) <==> ((BSet) obj).has(i));
     */
	public /*@ pure */ boolean equals(Object obj) {
		return obj instanceof NAT1;
	}

	/*@ also requires true;
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
        throw new UnsupportedOperationException("Error: size of the NATs not representable");
    }  
    
    /*@ also requires true;
     	assignable \nothing;
        ensures \result == "NAT1".hashCode();
     */
    public /*@ pure */ int hashCode() {
    	return "NAT1".hashCode();
    }
    
    /** inherited specification should be correct for all set operations */
    
    public boolean isSubset(/*@ non_null @*/ BSet<Integer> s2) {
    	return s2 instanceof INT || s2 instanceof NAT || s2 instanceof NAT1;
    }
    
    public boolean isProperSubset(/*@ non_null @*/ BSet<Integer> s2) {
    	return s2 instanceof INT || s2 instanceof NAT;
    }
    
    public  boolean isSuperset(/*@ non_null @*/ BSet<Integer> s2) {
    	if (s2 instanceof INT || s2 instanceof NAT) {
    		return false;
    	} else if (s2 instanceof NAT1) {
    		return true;
    	} else  {
    		for (Integer i : s2) {
    			if (i <= 0) return false;
    		}
    		return true;
    	}
    }

    public boolean isProperSuperset(/*@ non_null @*/ BSet<Integer> s2) {
    	if (s2 instanceof INT || s2 instanceof NAT || s2 instanceof NAT1) {
    		return false;
    	} else {
    		for (Integer i : s2) {
    			if (i <= 0) return false;
    		}
    		return true;   		
    	}
    }

    public Integer choose() {
    	return 0;
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
    	throw new UnsupportedOperationException("Error: can't insert into the NAT1s");
    }
    
    /*@ also public exceptional_behavior
        requires true;
        assignable \nothing;
        signals (UnsupportedOperationException);
     */
    public /*@ pure */ BSet<Integer> remove(Integer i) {
    	throw new UnsupportedOperationException("Error: can't remove from the NAT1s");
    }
    
    public BSet<Integer> intersection(BSet<Integer> s2) {
    	if (s2 instanceof INT || s2 instanceof NAT || s2 instanceof NAT1) {
    		return this;
    	} else {
    		BSet<Integer> res = new BSet<Integer>();
    		for (Integer i : s2) {
    			if (i > 0) {
    				res = res.insert(i);
    			}
    		}
    		return res;
    	}
     }
	 
    public BSet<Integer> union(BSet<Integer> s2) {
    	if (s2 instanceof INT || s2 instanceof NAT || s2 instanceof NAT1) {
    		return (BSet<Integer>) s2;
    	} else {
    		for (Integer i : s2) {
    			if (i <= 0) {
    				throw new UnsupportedOperationException("Error: can't union with the NATs");
    			}
    		}
        	return this;
    	}
    }

    
    public BSet<Integer> difference(BSet<Integer> s2) {
    	if (s2 instanceof INT || s2 instanceof NAT || s2 instanceof NAT1) {
    		return new BSet<Integer>();
    	} else {
    		throw new UnsupportedOperationException("Error: difference from the integers is infinite.");
    	}
    }
    
    /*@ also requires true;
     	assignable \nothing;
        ensures \result.equals("NAT1");
     */
    public /*@ pure */ String toString() {
    	return "NAT1";
    }

    
    public Iterator<Integer> toBag() {
    	throw new UnsupportedOperationException("Error: a bag cannot contain the NAT1s.");		
	}
    
    public ArrayList<Integer> toSequence() {
    	throw new UnsupportedOperationException("Error: a sequence cannot contain the NAT1s.");		
	}
	
    /*@ also public exceptional_behavior
        requires true;
        assignable \nothing;
        signals (UnsupportedOperationException);
     */
	public /*@ pure */ Object [] toArray() {
    	throw new UnsupportedOperationException("Error: an array cannot contain the NAT1s.");		
	}
	
    /*@ also public exceptional_behavior
        requires true;
        assignable \nothing;
        signals (UnsupportedOperationException);
     */
	public /*@ pure */ Iterator<Integer> iterator() {
	   	throw new UnsupportedOperationException("Error: the NAT1s are not iterable.");		
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
    	throw new UnsupportedOperationException("Error: can't compute POW(NAT1).");    	
    }
    
    /*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
     */
    public /*@ pure */ BSet<BSet<Integer>> pow1() {
    	throw new UnsupportedOperationException("Error: can't compute POW1(NAT1).");    	
    }
    
    /*@ also requires true;
     	assignable \nothing;
        ensures \result <==> parts.length == 1 && parts[0] instanceof NAT1;
     */
    public /*@ pure */ boolean NAT1_partition(BSet<Integer> ... parts) {
    	return (parts.length == 1 && parts[0] instanceof NAT1);
    }
    
    /*@ also public exceptional_behavior
        requires true;
        assignable \nothing;
        signals (UnsupportedOperationException);
     */
    public /*@ pure */ Integer max() {
    	throw new UnsupportedOperationException("Error: can't compute max of NAT1.");    	    	
    }
    
    /* also requires true;
     	assignable \nothing;
       ensures \result == 1;
     */
    public /*@ pure */ Integer min() {
    	return 1;    	    	
    }
}
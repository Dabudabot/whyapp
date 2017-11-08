package eventb_prelude;

import java.util.Iterator;

/** representation of the Event-B identity relation
 * @author Tim Wahls & Nestor Catano & Victor Rivera
 */

public class ID<E> extends BRelation<E, E> {
	public ID() {}

	/*@ also requires true;
	 	assignable \nothing;
	    ensures \result <==> true;
	 */
	public /*@ pure */ boolean isaFunction() {
		return true;
	}

	/*@ also requires true;
	 	assignable \nothing;
        ensures \result.has(key) && \result.int_size() == 1;
	 */
	public /*@ pure */ BSet<E> elementImage(E key) {
		return BSet.singleton(key);
	}

	/*@ also requires true;
	 	assignable \nothing;
        ensures \result.equals(keys);
	 */
	public /*@ pure */ BSet<E> image(BSet<E> keys) {
		return keys;
	}

	/*@ also requires true;
	 	assignable \nothing;
    	ensures \result.equals(this);
	 */
	public /*@ pure */ BRelation<E, E> inverse() {
		return (BRelation) super.clone();
	}

	/*@ also requires true;
	 	assignable \nothing;
        ensures \result.has(value) && \result.int_size() == 1;
	 */
	public /*@ pure */ BSet<E> inverseElementImage(E value) {
		return new BSet<E>(value);
	}

	/*@ also requires true;
	 	assignable \nothing;
        ensures \result.equals(values);
	 */
	public /*@ pure */ BSet<E> inverseImage(BSet<E> values) {
		return values;
	}

	/*@ also requires true;
	 	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isDefinedAt(E key) {
		return true;
	}

	/*@ also public exceptional_behavior
	      requires true;
	      assignable \nothing;
          signals (UnsupportedOperationException);
	 */
	public /*@ pure */ boolean add(E key, E value) {
		throw new UnsupportedOperationException("Error: cannot add to the identity relation.");
	}

	/*@ also public exceptional_behavior
          requires true;
          assignable \nothing;
          signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BRelation<E, E> insert(Pair<E, E> pair) {
		throw new UnsupportedOperationException("Error: cannot add to the identity relation.");
	}

	/*@ also public exceptional_behavior
	 	  requires true;
          assignable \nothing;
          signals (UnsupportedOperationException);
	 */
	public /*@ pure */ int int_size() {
		throw new UnsupportedOperationException("Error: the identity relation is infinite.");
	}

	/*@ also requires true;
	 	assignable \nothing;
	    ensures \result <==> key.equals(value);
	 */
	public /*@ pure */ boolean has(E key, E value) {
		return key.equals(value);
	}

	/*@ also requires true;
	 	assignable \nothing;
        ensures \result <==> pair.fst().equals(pair.snd());
	 */
	public /*@ pure */ boolean has(Pair<E, E> pair) {
		return pair.fst().equals(pair.snd());
	}

	/*@ also requires true;
	 	assignable \nothing;
	    ensures \result <==> false;
	 */
	public /*@ pure */ boolean isEmpty() {
		return false;
	}

	/*@ also requires !(obj instanceof ID);
	      assignable \nothing;
	      ensures \result <==> false;
	    also requires obj instanceof ID;
	      assignable \nothing;
	      ensures \result <==> true;
	 */
	public /*@ pure */ boolean equals(Object obj) {
		return obj instanceof ID;
	}

	/*@ also requires true;
	 	assignable \nothing;
	    ensures \result == "ID".hashCode();
	 */
	public /*@ pure */ int hashCode() {
		return "ID".hashCode();
	}

	/*@ also public exceptional_behavior
    	requires true;
    	assignable \nothing;
    	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BSet<E> domain() {
		throw new UnsupportedOperationException("Error: cannot find the domain of the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BSet<E> range() {
		throw new UnsupportedOperationException("Error: cannot find the range of the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BRelation<E, E> removeFromDomain(E key) {
		throw new UnsupportedOperationException("Error: cannot remove from the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ boolean remove(E key, E value) {
		throw new UnsupportedOperationException("Error: cannot remove from the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ boolean remove(Pair<E, E> pair) {
		throw new UnsupportedOperationException("Error: cannot remove from the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ boolean remove(Object obj) {
		throw new UnsupportedOperationException("Error: cannot remove from the identity relation.");
	}

	/*@ also requires true;
	 	assignable \nothing;
	    ensures \result.equals(otherRel);
	 */
	public /*@ pure */ <D> BRelation<D, E> compose(BRelation<D, E> otherRel) {
		return otherRel;
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BRelation<E, E> union(BSet<Pair<E, E>> otherRel) {
		throw new UnsupportedOperationException("Error: cannot union with the identity relation.");
	}

	/*@ also requires true;
	 	assignable \nothing;
	    ensures (\forall Pair<E, E> pair; \result.has(pair) <==>
	      otherRel.has(pair) && pair.fst().equals(pair.snd()));
	 */
	public /*@ pure */ BRelation<E, E> intersection(BSet<Pair<E, E>> otherRel) {
		BRelation<E, E> res = new BRelation<E, E>();
		for (Pair<E, E> pair : otherRel) {
			if (pair.fst().equals(pair.snd())) {
				res = res.insert(pair);
			}
		}
		return res;
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BRelation<E, E> difference(BSet<Pair<E, E>> otherRel) {
		throw new UnsupportedOperationException("Error: cannot remove from the identity relation.");
	}

	/*@ also requires true;
	 	assignable \nothing;
        ensures (\forall E e; dom.has(e); \result.has(e, e));
	 */
	public /*@ pure */ BRelation<E, E> restrictDomainTo(BSet<E> dom) {
		return BRelation.cross(dom, dom);
	}

	/*@ also requires true;
	 	assignable \nothing;
        ensures (\forall E e; ran.has(e); \result.has(e, e));
	 */
	public /*@ pure */ BRelation<E, E> restrictRangeTo(BSet<E> ran) {
		return BRelation.cross(ran, ran);
	}

	/*@ also requires true;
	 	assignable \nothing;
	    ensures \result.equals("ID".toString());
	 */
	public /*@ pure */ String toString() {
		return "ID";
	}

	/*@ also public exceptional_behavior
          requires true;
          assignable \nothing;
          signals (UnsupportedOperationException);
	 */
	public /*@ non_null @*/ Iterator<Pair<E,E>> associations() {
		throw new UnsupportedOperationException("Error: cannot iterate over the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ Iterator<Pair<E,E>> iterator() {
		throw new UnsupportedOperationException("Error: cannot iterate over the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ non_null @*/ BSet<Pair<E,E>> toSet() {
		throw new UnsupportedOperationException("Error: a set cannot contain the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ non_null @*/ BSet<Pair<E, BSet<E>>> imagePairSet() {
		throw new UnsupportedOperationException("Error: cannot iterate over the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ non_null @*/ Iterator<Pair<E,E>> imagePairs() {
		throw new UnsupportedOperationException("Error: cannot iterate over the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ non_null @*/ Iterator<E> domainElements()
	{
		throw new UnsupportedOperationException("Error: cannot iterate over the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ non_null @*/ Iterator<E> rangeElements()
	{
		throw new UnsupportedOperationException("Error: cannot iterate over the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BRelation<E, E> toFunction() {
		throw new UnsupportedOperationException("Error: a function cannot contain the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BRelation<E, E> domainSubtraction(BSet<E> domain) {
		throw new UnsupportedOperationException("Error: cannot remove from the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BRelation<E, E> rangeSubtraction(BSet<E> range) {
		throw new UnsupportedOperationException("Error: cannot remove from the identity relation.");
	}

	/*@ also requires true;
     	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isTotal(BSet<E> domain, BSet<E> range) {
		return true;
	}

	/*@ also requires true;
     	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isSurjective(BSet<E> domain, BSet<E> range) {
		return true;
	}	

	/*@ also requires true;
     	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isTotalSurjective(BSet<E> domain, BSet<E> range) {
		return true;
	}

	/*@ also requires true;
     	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isFunction(BSet<E> dom, BSet<E> ran) {
		return true;
	}

	/*@ also requires true;
     	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isRelation(BSet<E> dom, BSet<E> ran) {
		return true;
	}

	/*@ also requires true;
     	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isTotalFunction(BSet<E> dom, BSet<E> ran) {
		return true;
	}

	/*@ also requires true;
     	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isPartialInjection(BSet<E> dom, BSet<E> ran) {
		return true;
	}

	/*@ also requires true;
     	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isTotalInjection(BSet<E> dom, BSet<E> ran) {
		return true;
	}

	/*@ also requires true;
     	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isPartialSurjection(BSet<E> dom, BSet<E> ran) {
		return true;
	}

	/*@ also requires true;
     	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isTotalSurjection(BSet<E> dom, BSet<E> ran) {
		return true;
	}

	/*@ also requires true;
     	assignable \nothing;
        ensures \result <==> true;
	 */
	public /*@ pure */ boolean isInjection(BSet<E> dom, BSet<E> ran) {
		return true;
	}

	/*@ also requires true;
	 	assignable \nothing;
        ensures \result.equals(otherRel);
	 */
	public /*@ pure */ <D> BRelation<E, D> backwardCompose(BRelation<E, D> otherRel) {
		return otherRel;
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BRelation<E, E> override(BRelation<E, E> other) {
		throw new UnsupportedOperationException("Error: cannot remove from the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ <D> BRelation<E, Pair<E, D>> parallel(BRelation<E, D> other) {
		throw new UnsupportedOperationException("Error: cannot parallel the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ <K1, V1> BRelation<Pair<E, K1>, Pair<E, V1>> directProd(BRelation<K1, V1> other) {
		throw new UnsupportedOperationException("Error: cannot direct product with the identity relation.");
	}

	/*@ also requires true;
	 	assignable \nothing;
        ensures \result.equals(key);
	 */
	public /*@ pure */ E apply(E key) {
		return key;
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BSet<BSet<Pair<E, E>>> pow() {
		throw new UnsupportedOperationException("Error: cannot compute powerset of the identity relation.");
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ BSet<BSet<Pair<E, E>>> pow1() {
		throw new UnsupportedOperationException("Error: cannot compute powerset of the identity relation.");
	}

	/*@ also requires true;
	 	assignable \nothing;
	    ensures \result <==> false;
	 */
	public /*@ pure */ boolean finite() {
		return false;
	}

	/** inherited specs for containsAll, sub and supersets and choose are correct */

	public boolean containsAll(java.util.Collection<?> col) {
		for (Object obj : col) {
			if (obj instanceof Pair) {
				Pair<E,E> pair = (Pair<E,E>) obj;
				if (!pair.fst().equals(pair.snd())) return false;
			}
			else return false;
		}
		return true;
	}

	public boolean isSubset(BSet<Pair<E, E>> other) {
		if (other instanceof ID) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isProperSubset(BSet<Pair<E, E>> other) {
		return false;
	}

	public boolean isSuperset(BSet<Pair<E, E>> other) {
		if (other instanceof ID) return true;
		for (Pair<E, E> pair : other) {
			if (!pair.fst().equals(pair.snd())) return false;
		}
		return true;
	}

	public boolean isProperSuperset(BSet<Pair<E, E>> other) {
		if (other instanceof ID) return false;
		return isSuperset(other);
	}

	/*@ also public exceptional_behavior
	requires true;
	assignable \nothing;
	signals (UnsupportedOperationException);
	 */
	public /*@ pure */ Pair<E, E> choose() {
		throw new UnsupportedOperationException("Error: cannot choose from the identity relation.");
	}

}
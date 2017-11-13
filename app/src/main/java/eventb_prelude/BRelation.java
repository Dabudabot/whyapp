package eventb_prelude;

import java.util.TreeSet;
import java.util.Iterator;

/** a class to model Event-B relations in Java 
 * @author Tim Wahls & Nestor Catano & Victor Rivera
 */

public class BRelation<K, V> extends BSet<Pair<K, V>> {
	public static BRelation EMPTY = new BRelation();
	
		/*@ requires true;
		 	ensures this.isEmpty();
		 */
		public BRelation() { }
		
		/*@ requires true;
		 	ensures this.equals(elems);
		 */
		BRelation(BRelation<K,V> elems) {
			this();
			for (Pair<K,V> e : elems)
				add(e);
		}
		
		/*@ requires true;
		 	assignable this.*;
		  	ensures (\forall K k; (\forall V v; this.has(k, v) <==> 
		       			(\exists int i; 0 <= i && i < pairs.length;
		          			pairs[i].fst().equals(k) && pairs[i].snd().equals(v))));
		 */
		public BRelation(Pair<K,V> ... pairs) {
			this();
			for (Pair<K,V> pair : pairs) {
				add(pair);
			}
		}
		
		/*@ requires true;
	 		assignable this.*;
	  		ensures (\forall Pair<K,V> p; this.has(p) <==> s.contains(p));
		 */
		public BRelation(BSet<Pair<K,V>> s) {
			this();
			addAll(s);
		}
		
		/*@ requires true;
		 	assignable \nothing;
		    ensures \result.int_size() == 1 && \result.has(key, value);
		 */
		public /*@ pure */ static <KK, VV> BRelation<KK, VV> singleton(KK key, VV value) {
			return new BRelation<KK, VV>(BRelation.singleton(key, value));
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures \result.int_size() == 1 && \result.has(pair);
	     */
		public /*@ pure */ static <KK, VV> BRelation<KK, VV> singleton(Pair<KK, VV> pair) {
			return BRelation.singleton(pair.fst(), pair.snd());
		}

		/*@ requires true;
		 	assignable \nothing;
		    ensures (\forall V v; \result.has(v) <==> this.has(key, v));
		 */
		public /*@ pure */ BSet<V> elementImage(K key) {
			BSet<V> res = new BSet<V>();
			for(Pair<K,V> p : this) {
				if(p.fst().equals(key))
					res.add(p.snd());
			}
			return res;
		}

		/*@ requires true;
		 	assignable \nothing;
	        ensures (\forall K key; keys.has(key); 
	          (\forall V v; \result.has(v) <==> this.has(key, v)));
	     */
		public /*@ pure */ BSet<V> image(BSet<K> keys) {
			BSet<V> res = new BSet<V>(); // the empty set
			
			for(K k : keys) {
				res.unionInPlace(elementImage(k));
			}
			return res;
		}
		
		/* 	requires true;
		 	assignable \nothing;
		   	ensures (\forall K key; (\forall V value; 
		     	this.has(key, value) <==> \result.has(value, key)));
		 */
		public /*@ pure */ BRelation<V, K> inverse() {
			BRelation<V, K> res = new BRelation<V, K>();
			
			for(Pair<K,V> pair : this) {
				res.add(new Pair<V,K>(pair.snd(), pair.fst()));
			}
			return res;
		}
		
		
		/*@ requires true;
		 	assignable \nothing;
		    ensures \result <==> this.isaFunction();
		 */
		public /*@ pure */ boolean isaFunction() {
			for(Pair<K,V> pair : this) {
				if(!elementImage(pair.fst()).isSingleton())
					return false;
			}
			return true;
		}
		
		/*@ requires true;
		 	assignable \nothing;
		   ensures (\forall K key; \result.has(key) <==> this.has(key, value));
		 */
		public /*@ pure */ BSet<K> inverseElementImage(V value) {
			BSet<K> res = new BSet<K>();
			for(Pair<K,V> pair : this) {
				if (pair.snd().equals(value))
					res.add(pair.fst());
			}
			return res;
		}

		/*@ requires true;
		 	assignable \nothing;
		   	ensures (\forall V value; values.has(value);
		     	(\forall K key; \result.has(key) <==> this.has(key, value)));
		 */
		public /*@ pure */ BSet<K> inverseImage(BSet<V> values) {
			BSet<K> res = new BSet<K>();
			for(V value : values)
				res.unionInPlace(inverseElementImage(value));
			
			return res;
		}
		
		/*@ requires true;
		 	assignable \nothing;
		    ensures \result <==> (\exists V value; this.has(key, value));
		 */
		public /*@ pure */ boolean isDefinedAt(K key) {
			for(Pair<K,V> pair : this) {
				if (pair.fst().equals(key)) return true;
			}
			return false;
		}

		/*@ requires true;
		 	assignable this.*;
		 	ensures this.has(pair);
		 */
		public boolean add(Pair<K,V> pair) {
			return super.add(pair);
		}
		
		/*@ requires true;
		 	assignable this.*;
		 	ensures this.has(new Pair<K,V>(key,value)); 
		 */
		public boolean add(K key, V value) {
			Pair<K,V> pair = new Pair<K,V>(key,value);
			return add(pair);
		}
		
		/*@ also
		    requires true;  
		    assignable \nothing;
		    ensures \result.equals(insert(pair.fst(), pair.snd()));
		 */
		public /*@ pure */ BRelation<K, V> insert(Pair<K, V> pair) {
			BSet<Pair<K,V>> s = super.insert(pair);
			return new BRelation<K,V>(s);
		}
				
		/*@ requires true;
		 	assignable this.*;
		 	ensures \result.has(new Pair<K,V>(key,value)); 
		 */
		public BRelation<K, V> insert(K key, V value) {
			return insert(new Pair<K,V>(key,value));
		}		
		
		/*@ 	also 
		 	requires true;
		  	assignable \nothing;
		    ensures \result == this.int_size();
		 */
		public /*@ pure */ int int_size() {
			return size();
		}
		
		/*@ requires true;
		 	assignable \nothing;
		    ensures \result <==> this.has(key, value);
		 */
		public /*@ pure */ boolean has(K key, V value) {
			return super.has(new Pair<K,V>(key, value));
		}
		
		public Object clone() {
			BRelation<K,V> res = new BRelation<K,V>();
			res.addAll(this);
			return res;
		}
		
		/*@ 	also 
		 	requires !(obj instanceof BSet);
			assignable \nothing;
		    ensures \result <==> false;
		    	also 
		    requires obj instanceof BSet;
		    assignable \nothing;
		    ensures \result <==> (\forall Pair<K, V> pair; this.has(pair) <==> ((BSet) obj).has(pair));  
		 */
		public /*@ pure */ boolean equals(Object obj) {
			if (obj instanceof ID) {
				return false;
			} else if (obj instanceof BRelation) {
				BRelation<K, V> rel = (BRelation<K, V>) obj;
				return super.equals(rel); 
			} else if (obj instanceof BSet) {
				try {
					BSet<Pair<K, V>> set = (BSet<Pair<K, V>>) obj;
					if (set.size() == int_size()) {
						for (Pair<K, V> pair : set) {
							if (!has(pair)) return false;
						}
						return true;
					}
				} catch (ClassCastException cse) {
					return false;
				}
			}
			return false;
		}
		
		/*@ also 
		    requires true;
		    assignable \nothing;
		    ensures \result == this.hashCode();
		 */
		public /*@ pure */ int hashCode() {
			return super.hashCode();
		}
		
		/*@ requires true;
		    assignable \nothing;
		    ensures (\forall K key; \result.has(key) <==> this.isDefinedAt(key));
		 */
		public /*@ pure */ BSet<K> domain() {
			BSet<K> res = new BSet<K>();
			for(Pair<K,V> pair : this) {
				res.add(pair.fst());
			}
			return res;
		}
		
		/*@ requires true;
		 	assignable \nothing;
		    ensures (\forall V value; \result.has(value) <==> (\exists K key; this.has(key, value)));
		 */
		public /*@ pure */ BSet<V> range() {
			BSet<V> res = new BSet<V>();
			for(Pair<K,V> pair : this) {
				res.insertInPlace(pair.snd());
			}			
			return res;
		}		
		
		/*@ requires true;
		 	assignable this.*;
		 	ensures ! \result.has(pair);
		 */
		public BRelation<K, V> delete(Pair<K,V> pair) {			
			BRelation<K,V> res = new BRelation<K,V>();

			for(Pair<K,V> p : this) {
				if(!pair.equals(p))
					res.insertInPlace(p);
			}
			return res;
		}		

		/*@ requires true;
		 	assignable this.*;
		 	ensures ! this.has(new Pair<K,V>(key,value));
		 */
		public BRelation<K, V> delete(K key, V value) {
			return delete(new Pair<K,V>(key,value));
		}
		
		public boolean remove(Object obj) {
			if (obj instanceof Pair) {
				Pair<K,V> pair = (Pair<K,V>) obj;
				return super.remove(pair);
			}
			return false;
		}
		
		/*@ requires true;
	 		assignable this.*;
	 		ensures ! this.has(new Pair<K,V>(key,value));
		 */
		public boolean remove(K key, V value) {
			return remove(new Pair<K,V>(key,value));
		}
		
		/*@ requires true;
		 	assignable \nothing;
		    ensures (\forall Pair<D, V> rp; \result.has(rp) <==>
		      (\exists Pair<K, V> tp; this.has(tp);
		        (\exists Pair<D, K> op; otherRel.has(op);
		          op.snd().equals(tp.fst()) && rp.snd().equals(tp.snd()) && tp.fst().equals(op.fst()))));
		 */
		public /*@ pure */ <D> BRelation<D, V> compose(BRelation<D, K> otherRel) {
			if (otherRel instanceof ID) {
				return (BRelation<D, V>) this;
			} else {
				BRelation<D, V> res = new BRelation<D, V>();
				for (Pair<D, K> pair : otherRel) {
					for (V val : elementImage(pair.snd())) {
						res.add(pair.fst(), val);
					}
				}
				return res;
			}
		}
		
		/*@ requires true;
		 	assignable \nothing;
		    ensures (\forall Pair<K, V> pair; \result.has(pair) <==>
		      this.has(pair) || otherRel.has(pair));
		 */
		public /*@ pure */ BRelation<K, V> union(BSet<Pair<K, V>> otherRel) {
			if (otherRel instanceof ID) {
				throw new UnsupportedOperationException("Error: cannot union with the identity relation.");
			}
			BRelation<K,V> res = new BRelation<K,V>();
			res.addAll(this);
			res.addAll(otherRel);
			return res;
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures (\forall Pair<K, V> e; \result.has(e) <==>
	          (\exists int i; 0 <= 1 && i < sets.length; sets[i].has(e)));
	     */
		public /*@ pure */ static <K, V> BRelation<K, V> union(BRelation<K, V> ... sets) {
			BRelation<K, V> res = new BRelation<K, V>();
			for (BRelation<K, V> set : sets) {
				res.unionInPlace(set);
			}
			return res;
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures (\forall Pair<K, V> pair; \result.has(pair) <==>
	          this.has(pair) && otherRel.has(pair));
	     */
		public /*@ pure */ BRelation<K, V> intersection(BSet<Pair<K, V>> otherRel) {
			if (otherRel instanceof ID) {
				return ((ID) otherRel).intersection(this);
			}
			BRelation<K, V> res = new BRelation<K, V>();
			for (Pair<K, V> pair : otherRel) {
				if (this.has(pair)) {
					res.add(pair);
				}
			}
			return res;
		}

		/*@ requires true;
		 	assignable \nothing;
	        ensures (\forall Pair<K, V> e; \result.has(e) <==>
	          (\forall int i; 0 <= 1 && i < sets.length; sets[i].has(e)));
	     */
		public /*@ pure */ static <K, V> BRelation<K, V> intersection(BRelation<K, V> ... sets) {
			BRelation<K, V> res = sets[0];
			for (int i = 1; i < sets.length; i++) {
				res.intersectionInPlace(sets[i]);
			}
			return res;
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures (\forall Pair<K, V> pair; \result.has(pair) <==>
	          this.has(pair) && !otherRel.has(pair));
	     */
		public /*@ pure */ BRelation<K, V> difference(BSet<Pair<K, V>> otherRel) {
			BRelation<K, V> res = new BRelation<K,V>();
			res.addAll(this);
			
			for (Pair<K, V> pair : otherRel) 
				res.remove(pair);
				
			return res;
		}
		
		/*@ also 
		 	  requires true;
		 	  assignable \nothing;
		      ensures \result.equals(this.toString());
		 */
		public /*@ pure */ String toString() {
			return super.toString();
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures \result.equals(this.associations());
	     */
		public /*@ pure @*/ Iterator<Pair<K, V>> associations() {
			return super.iterator();
		}
		
		/*@ also
		  	  requires true;
		 	  assignable \nothing;
	          ensures \result.equals(this.iterator());
	     */
	    public /*@ pure */ Iterator<Pair<K,V>> iterator() {
	    	return super.iterator();
	    }
	    
	    /*@ requires true;
	     	assignable \nothing;
	        ensures (\forall Pair<K, V> pair;
	          \result.has(pair) <==> this.has(pair));
	     */
	    public /*@ pure non_null @*/ BSet<Pair<K, V>> toSet() {
	    	BSet<Pair<K, V>> res = new BSet<Pair<K, V>>();
			for(Pair<K,V> pair : this) {
				res.add(pair);
			}
	    	return res;
	    }
	    
	    /*@ requires true;
	     	assignable \nothing;
	        ensures (\forall Pair<K, BSet<V>> pair; \result.has(pair); 
	           this.isDefinedAt(pair.fst()) && pair.snd().equals(this.elementImage(pair.fst())));
	      */
	    public /*@ pure non_null @*/ BSet<Pair<K, BSet<V>>> imagePairSet() {
	    	BSet<K> domain = domain();
	    	BSet<Pair<K, BSet<V>>> res = new BSet<Pair<K, BSet<V>>>();
	    	
	    	for ( K key : domain) {
	    		Pair<K, BSet<V>> pair = new Pair<K, BSet<V>>(key,elementImage(key));
	    		res.add(pair);
	    	}
	    	return res;
	    }
	    
	    /*@ requires true;
	     	assignable \nothing;
	        ensures \result.equals(this.imagePairs());
	     */
	    public /*@ pure non_null @*/Iterator<Pair<K,V>> imagePairs() {
	    	return super.iterator();
	    }
	    
	    /*@ requires true;
	     	assignable \nothing;
	        ensures \result.equals(this.domainElements());
	     */
	    public /*@ pure non_null @*/ Iterator<K> domainElements() {
	    	return domain().iterator();
	    }
	    
	    /*@ requires true;
	     	assignable \nothing;
	        ensures \result.equals(this.rangeElements());
	     */
	    public /*@ pure non_null @*/ Iterator<V> rangeElements() {
	    	return range().iterator();
	    }
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures (\forall Pair<K, V> pair; 
	          \result.has(pair) <==> this.has(pair) && dom.has(pair.fst()))
	       && \result.domain().equals(dom.intersection(this.domain()))
	       && (\forall V value; \result.range().has(value) <==>
	           (\exists K k; dom.has(k); this.has(k, value)));
	    */
		public /*@ pure */ BRelation<K, V> restrictDomainTo(BSet<K> dom) {
			BRelation<K,V> res = new BRelation<K,V>();
			for(Pair<K,V> pair : this) {
				if (dom.has(pair.fst())) 
					res.add(pair);
			}
			return res;
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures (\forall Pair<K, V> pair; 
	          \result.has(pair) <==> this.has(pair) && ran.has(pair.snd()))
	       && \result.range().equals(ran.intersection(this.range()))
	       && (\forall K k; \result.domain().has(k) <==>
	         (\exists V v; ran.has(v); this.has(k, v)));
	    */
		public /*@ pure */ BRelation<K, V> restrictRangeTo(BSet<V> ran) {
			BRelation<K,V> res = new BRelation<K,V>();
			for(Pair<K,V> pair : this) {
				if (ran.has(pair.snd())) 
					res.add(pair);
			}
			return res;
		}
	    
	    /*@ requires true;
	     	assignable \nothing;
	        ensures \result.equals(this.toFunction());
	     */
	    public /*@ pure */ BRelation<K,V> toFunction() {
	    	return restrictDomainTo(domain());
	    }
	    
	    /*@ requires true;
	     	assignable \nothing;
	        ensures \result.equals(this.restrictDomainTo(this.domain().difference(domain)));
	     */
	    public /*@ pure */ BRelation<K, V> domainSubtraction(BSet<K> domain) {
	    	return restrictDomainTo(domain().difference(domain));
	    }
	    
	    /*@ requires true;
	     	assignable \nothing;
	        ensures \result.equals(restrictRangeTo(range().difference(range)));
	     */
	    public /*@ pure */ BRelation<K, V> rangeSubtraction(BSet<V> range) {
	    	return restrictRangeTo(range().difference(range));
	    }
	    
	    /*@ requires true;
	     	assignable \nothing;
	        ensures \result.equals(otherRel.compose(this));
	     */
		public /*@ pure */ <D> BRelation<K, D> backwardCompose(BRelation<V, D> otherRel) {
			return otherRel.compose(this);
		}

		/*@ requires true;
		 	assignable \nothing;
		    ensures \result.equals(other.union(domainSubtraction(other.domain())));
		 */
		public /*@ pure */ BRelation<K, V> override(BRelation<K, V> other) {
			return other.union(domainSubtraction(other.domain()));
		}
		
		/*@ requires true;
		 	assignable \nothing;
		    ensures (\forall Pair<K, Pair<V, D>> pair; \result.has(pair) <==>
		       (\exists Pair<K, V> tp; this.has(tp);
		         (\exists Pair<K, D> op; other.has(op);
		           tp.fst().equals(op.fst()) && tp.fst().equals(pair.fst()) && 
		             pair.snd().equals(new Pair<V, D>(tp.snd(), op.snd())))));
		 */
		public /*@ pure */ <D> BRelation<K, Pair<V, D>> parallel(BRelation<K, D> other) {
			BRelation<K, Pair<V, D>> res = new BRelation<K, Pair<V, D>>();
			
			for(Pair<K,V> pair : this) {
				for (D val : other.elementImage(pair.fst())) {
					res.add(pair.fst(), new Pair<V, D>(pair.snd(), val));
				}
			}
			return res;
		}
		
		/*@ requires true;
		 	assignable \nothing;
		    ensures (\forall Pair<Pair<K, K1>, Pair<V, V1>> pair;
		      \result.has(pair) <==> this.has(pair.fst()) && other.has(pair.snd()));
		 */
		public /*@ pure */ <K1, V1> BRelation<Pair<K, K1>, Pair<V, V1>> directProd(BRelation<K1, V1> other) {
			BRelation<Pair<K, K1>, Pair<V, V1>> res = new BRelation<Pair<K, K1>, Pair<V, V1>>();
			
			for(Pair<K,V> pair : this) {
				for (Pair<K1, V1> pair2 : other) {
					res.add(new Pair<K, K1>(pair.fst(), pair2.fst()),
							      new Pair<V, V1>(pair.snd(), pair2.snd()));
				}
			}
			return res;
		}
	    
		/*@ requires true;
		 	assignable \nothing;
		    ensures \result <==> dom.equals(domain()) && range().isSubset(ran);
		 */
	    public /*@ pure */ boolean isTotal(BSet<K> dom, BSet<V> ran) {
	    	return dom.equals(domain()) && range().isSubset(ran);
	    }
		
	    
		/*@ requires true;
		 	assignable \nothing;
	        ensures \result <==> domain().isSubset(dom) && ran.equals(range());
	    */
	    public /*@ pure */ boolean isSurjective(BSet<K> dom, BSet<V> ran) {
	    	return domain().isSubset(dom) && ran.equals(range());
		}	
	    
		/*@ requires true;
		 	assignable \nothing;
	        ensures \result <==> isTotal(dom, ran) && isSurjective(dom, ran);
	     */
	    public /*@ pure */ boolean isTotalSurjective(BSet<K> dom, BSet<V> ran) {
	    	return isTotal(dom, ran) && isSurjective(dom, ran);
	    }
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures \result <==> isaFunction() && isRelation(dom, ran);
	     */
		public /*@ pure */ boolean isFunction(BSet<K> dom, BSet<V> ran) {
			return isaFunction() && isRelation(dom, ran);
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures \result <==> domain().isSubset(dom) && range().isSubset(ran);
	     */
		public /*@ pure */ boolean isRelation(BSet<K> dom, BSet<V> ran) {
			return domain().isSubset(dom) && range().isSubset(ran);
		}

		/*@ requires true;
		 	assignable \nothing;
	        ensures \result <==> isaFunction() && isTotal(dom, ran) && range().isSubset(ran);
	     */
		public /*@ pure */ boolean isTotalFunction(BSet<K> dom, BSet<V> ran) {
			return isaFunction() && isTotal(dom, ran) && range().isSubset(ran);
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures \result <==> isaFunction() && inverse().isaFunction() && isRelation(dom, ran);
	     */
		public /*@ pure */ boolean isPartialInjection(BSet<K> dom, BSet<V> ran) {
			return isaFunction() && inverse().isaFunction() && isRelation(dom, ran);			
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures \result <==> isPartialInjection(dom, ran) && isTotal(dom, ran);
	     */
		public /*@ pure */ boolean isTotalInjection(BSet<K> dom, BSet<V> ran) {
			return isPartialInjection(dom, ran) && isTotal(dom, ran);
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures \result <==> isaFunction() && isSurjective(dom, ran);
	     */
		public /*@ pure */ boolean isPartialSurjection(BSet<K> dom, BSet<V> ran) {
			return isaFunction() && isSurjective(dom, ran);
		}

		/*@ requires true;
		 	assignable \nothing;
	        ensures \result <==> isTotalFunction(dom, ran) && isPartialSurjection(dom, ran);
	     */
		public /*@ pure */ boolean isTotalSurjection(BSet<K> dom, BSet<V> ran) {
			return isTotalFunction(dom, ran) && isPartialSurjection(dom, ran);
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures \result <==> isPartialInjection(dom, ran) && isPartialSurjection(dom, ran);
	     */
		public /*@ pure */ boolean isInjection(BSet<K> dom, BSet<V> ran) {
			return isPartialInjection(dom, ran) && isPartialSurjection(dom, ran);
		}
		
		/*@ public normal_behavior
		      requires this.isaFunction();
		      assignable \nothing;
		      ensures \result.equals(this.elementImage(key).choose());
		    also public exceptional_behavior
		      requires !this.isaFunction();
		      assignable \nothing;
		      signals (IllegalStateException);
		 */
		public /*@ pure */ V apply(K key) {
			//return elementImage(key).choose();
			for(Pair<K,V> p : this) {
				if(p.fst().equals(key))
					return p.snd();
			}
			return null;
		}
		
		/*@ also 
		 	requires true;
		 	assignable \nothing;
		    ensures (\forall BSet<Pair<K, V>> s; \result.has(s) <==> s.isSubset(this));
		 */
		public /*@ pure */ BSet<BSet<Pair<K, V>>> pow() {
			return super.pow();
		}
			
		/*@ also 
		 	requires true;
		 	assignable \nothing;
	        ensures (\forall BSet<Pair<K, V>> s; \result.has(s) <==> s.isSubset(this) && !s.isEmpty());
	     */
		public /*@ pure */ BSet<BSet<Pair<K, V>>> pow1() {
			return super.pow1();
		}

	    /*@ requires true;
	     	assignable \nothing;
	        ensures \result <==> (\forall Pair<K, V> e; c.contains(e); this.has(e));
	     */
		public /*@ pure */ boolean containsAll(java.util.Collection<?> c) {
			return super.containsAll(c);
		}
		
	    /*@ requires true;
	     	assignable \nothing;
	        ensures \result <==> (\forall Pair<K, V> e; this.has(e); other.has(e));
	     */
		public /*@ pure */ boolean isSubset(BSet<Pair<K, V>> other) {	
			for(Pair<K,V> pair : this) {
				if (!other.has(pair)) return false;
			}
			return true;
		}
		
		/*@ requires true;
		 	assignable \nothing;
	        ensures \result <==> this.isSubset(other) && !this.equals(other);
		 */
		public /*@ pure */ boolean isProperSubset(BSet<Pair<K, V>> other) {
			return this.isSubset(other) && !this.equals(other);
		}
				
		public /*@ pure */ Pair<K, V> choose() {
			if(isSingleton())
				throw new IllegalStateException("Error: no elements in an empty set");
			return super.choose();
		}

		/*@ requires true;
		 	assignable \nothing;
		    ensures \result <==> true;
		 */
		public /*@ pure */ boolean finite() {
			return true;
		}
		
		/*@ requires true;
		 	assignable \nothing;
		    ensures \result <==> false;
		 */
		public /*@ pure */ boolean infinite() {
			return false;
		}

		/*@ requires true;
		 	assignable \nothing;
		    ensures (\forall Pair<K, V> e; \result.has(e) <==>
		      domain.has(e.fst()) && range.has(e.snd()));
		 */
		public /*@ pure */ static <K, V> BRelation<K, V> cross(BSet<K> domain, BSet<V> range) {
			BRelation<K, V> res = new BRelation<K, V>();
			for (K key : domain) {
				for (V value : range) {
					res.add(key, value);
				}
			}
			return res;
		}

}
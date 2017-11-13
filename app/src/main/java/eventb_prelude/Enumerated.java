package eventb_prelude;

/** Enumerated type in Java
 * @author Tim Wahls & Nestor Catano & Victor Rivera
 */

public class Enumerated extends BSet<Integer> {
	/*@	requires true;
	  	assignable this.*;
	 	ensures (\forall int i; low <= i && i <= hi; this.has(i)) 
		         				&& this.int_size() == (hi - low) + 1;
	*/
	public Enumerated(int low, int hi) {
		for (int i = low; i <= hi; i++) {
			add(new Integer(i));
		}
	}
}
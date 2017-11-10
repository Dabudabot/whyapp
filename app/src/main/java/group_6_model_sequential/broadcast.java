package group_6_model_sequential; 

import eventb_prelude.*;
import Util.Utilities;

public class broadcast{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public broadcast(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(b_u) && !b_ul.has(b_u) && b_ul.isSubset(machine.get_user()) && machine.CONTENT.has(b_c) && !machine.get_content().has(b_c) && !BRelation.cross(new BSet<Integer>(b_u),b_ul).isSubset(machine.get_muted()) && !BRelation.cross(b_ul,new BSet<Integer>(b_u)).isSubset(machine.get_muted()) && !machine.get_toreadcon().domain().has(b_c) && !machine.get_owner().domain().has(b_c) &&  (\forall Integer b_uu;machine.get_chatcontent().domain().has(b_uu) && !machine.get_chatcontent().apply(b_uu).domain().has(b_c)) &&  (\forall Integer b_i;new Enumerated(new Integer(1),machine.get_contentsize()).has(b_i) && machine.get_chatcontentseq().domain().has(b_i) && !machine.get_chatcontentseq().apply(b_i).domain().has(b_c)) &&  (\forall Integer b_uu;machine.get_chatcontent().domain().has(b_uu) && !machine.get_chatcontent().apply(b_uu).domain().has(b_c))); */
	public /*@ pure */ boolean guard_broadcast( Integer b_c, Integer b_u, BSet<Integer> b_ul) {
		return (machine.get_user().has(b_u) && !b_ul.has(b_u) && b_ul.isSubset(machine.get_user()) && machine.CONTENT.has(b_c) && !machine.get_content().has(b_c) && !BRelation.cross(new BSet<Integer>(b_u),b_ul).isSubset(machine.get_muted()) && !BRelation.cross(b_ul,new BSet<Integer>(b_u)).isSubset(machine.get_muted()) && !machine.get_toreadcon().domain().has(b_c) && !machine.get_owner().domain().has(b_c) && true && true && true);
	}

	/*@ public normal_behavior
		requires guard_broadcast(b_c,b_u,b_ul);
		assignable machine.content, machine.chat, machine.chatcontent, machine.inactive, machine.toread, machine.toreadcon, machine.owner, machine.contentsize, machine.chatcontentseq;
		ensures guard_broadcast(b_c,b_u,b_ul) &&  machine.get_content().equals(\old((machine.get_content().union(new BSet<Integer>(b_c))))) &&  machine.get_chat().equals(\old((machine.get_chat().union(BRelation.cross(b_ul,new BSet<Integer>(b_u)).union(BRelation.cross(new BSet<Integer>(b_u),b_ul)))))) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().override(new BRelation<Integer,Pair<BRelation<Integer,Integer>,Integer>>(new Pair<Integer,Pair<BRelation<Integer,Integer>,BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>>>(b_u,(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (machine.get_chatcontent().domain().has(b_u) && machine.get_chatcontent().apply(b_u).domain().has(cc)); e.equals(new Pair<BRelation<Integer,Integer>,ERROR>(cc,machine.get_chatcontent().apply(b_u).apply(cc))))}).union(new BRelation<Integer,BSet<Integer>>(new Pair<Integer,BSet<Integer>>(b_c,(BRelation.cross(new BSet<Integer>(b_u),b_ul).union(BRelation.cross(b_ul,new BSet<Integer>(b_u)))))))))))))) &&  machine.get_inactive().equals(\old((machine.get_inactive().union((BRelation.cross(b_ul,new BSet<Integer>(b_u)).union(BRelation.cross(new BSet<Integer>(b_u),b_ul))).difference(machine.get_active()))))) &&  machine.get_toread().equals(\old((machine.get_toread().union(BRelation.cross(b_ul,new BSet<Integer>(b_u)).difference(machine.get_active()))))) &&  machine.get_toreadcon().equals(\old((machine.get_toreadcon().override(new BRelation<Integer,BSet<Integer>>(new Pair<Integer,BSet<Integer>>(b_c,BRelation.cross(b_ul,new BSet<Integer>(b_u)).difference(machine.get_active()))))))) &&  machine.get_owner().equals(\old((machine.get_owner().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(b_c,b_u)))))) &&  machine.get_contentsize() == \old(new Integer(machine.get_contentsize() + 1)) &&  machine.get_chatcontentseq().equals(\old((machine.get_chatcontentseq().override(new BRelation<Integer,BRelation<Integer,BSet<Integer>>>(new Pair<Integer,BRelation<Integer,BSet<Integer>>>(new Integer(machine.get_contentsize() + 1),new BRelation<Integer,BSet<Integer>>(new Pair<Integer,BSet<Integer>>(b_c,(BRelation.cross(new BSet<Integer>(b_u),b_ul).union(BRelation.cross(b_ul,new BSet<Integer>(b_u)))))))))))); 
	 also
		requires !guard_broadcast(b_c,b_u,b_ul);
		assignable \nothing;
		ensures true; */
	public void run_broadcast( Integer b_c, Integer b_u, BSet<Integer> b_ul){
		if(guard_broadcast(b_c,b_u,b_ul)) {
			BSet<Integer> content_tmp = machine.get_content();
			BRelation<Integer,Integer> chat_tmp = machine.get_chat();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,BRelation<Integer,Integer>> toreadcon_tmp = machine.get_toreadcon();
			BRelation<Integer,Integer> owner_tmp = machine.get_owner();
			Integer contentsize_tmp = machine.get_contentsize();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontentseq_tmp = machine.get_chatcontentseq();

			machine.set_content((content_tmp.union(new BSet<Integer>(b_c))));
			machine.set_chat((chat_tmp.union(BRelation.cross(b_ul,new BSet<Integer>(b_u)).union(BRelation.cross(new BSet<Integer>(b_u),b_ul)))));
			machine.set_chatcontent(null); // Set Comprehension: feature not supported by EventB2Java
			machine.set_inactive((inactive_tmp.union((BRelation.cross(b_ul,new BSet<Integer>(b_u)).union(BRelation.cross(new BSet<Integer>(b_u),b_ul))).difference(machine.get_active()))));
			machine.set_toread((toread_tmp.union(BRelation.cross(b_ul,new BSet<Integer>(b_u)).difference(machine.get_active()))));
		//	machine.set_toreadcon((toreadcon_tmp.override(new BRelation<Integer,BSet<Integer>>(new Pair<Integer,BSet<Integer>>(b_c,BRelation.cross(b_ul,new BSet<Integer>(b_u)).difference(machine.get_active()))))));
			machine.set_owner((owner_tmp.union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(b_c,b_u)))));
			machine.set_contentsize(new Integer(contentsize_tmp + 1));
		//	machine.set_chatcontentseq((chatcontentseq_tmp.override(new BRelation<Integer,BRelation<Integer,BSet<Integer>>>(new Pair<Integer,BRelation<Integer,BSet<Integer>>>(new Integer(contentsize_tmp + 1),new BRelation<Integer,BSet<Integer>>(new Pair<Integer,BSet<Integer>>(b_c,(BRelation.cross(new BSet<Integer>(b_u),b_ul).union(BRelation.cross(b_ul,new BSet<Integer>(b_u)))))))))));

			System.out.println("broadcast executed b_c: " + b_c + " b_u: " + b_u + " b_ul: " + b_ul + " ");
		}
	}

}

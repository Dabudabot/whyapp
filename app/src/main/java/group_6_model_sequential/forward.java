package group_6_model_sequential; 

import eventb_prelude.*;
import Util.Utilities;

public class forward{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public forward(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(f_u) && !f_ul.has(f_u) && f_ul.isSubset(machine.get_user()) && machine.CONTENT.has(f_c) && !machine.get_content().has(f_c) && !BRelation.cross(new BSet<Integer>(f_u),f_ul).isSubset(machine.get_muted()) && !BRelation.cross(f_ul,new BSet<Integer>(f_u)).isSubset(machine.get_muted()) && f_ul.isSubset(machine.get_chat().image(new BSet<Integer>(f_u))) && !machine.get_toreadcon().domain().has(f_c) && !machine.get_owner().domain().has(f_c) &&  (\forall Integer f_uu;machine.get_chatcontent().domain().has(f_uu) && !machine.get_chatcontent().apply(f_uu).domain().has(f_c)) &&  (\forall Integer f_i;new Enumerated(new Integer(1),machine.get_contentsize()).has(f_i) && machine.get_chatcontentseq().domain().has(f_i) && !machine.get_chatcontentseq().apply(f_i).domain().has(f_c)) &&  (\forall Integer f_uu;machine.get_chatcontent().domain().has(f_uu) && !machine.get_chatcontent().apply(f_uu).domain().has(f_c))); */
	public /*@ pure */ boolean guard_forward( Integer f_c, Integer f_u, BSet<Integer> f_ul) {
		return (machine.get_user().has(f_u) && !f_ul.has(f_u) && f_ul.isSubset(machine.get_user()) && machine.CONTENT.has(f_c) && !machine.get_content().has(f_c) && !BRelation.cross(new BSet<Integer>(f_u),f_ul).isSubset(machine.get_muted()) && !BRelation.cross(f_ul,new BSet<Integer>(f_u)).isSubset(machine.get_muted()) && f_ul.isSubset(machine.get_chat().image(new BSet<Integer>(f_u))) && !machine.get_toreadcon().domain().has(f_c) && !machine.get_owner().domain().has(f_c) && true && true && true);
	}

	/*@ public normal_behavior
		requires guard_forward(f_c,f_u,f_ul);
		assignable machine.content, machine.chat, machine.chatcontent, machine.inactive, machine.toread, machine.toreadcon, machine.owner, machine.chatcontentseq, machine.contentsize;
		ensures guard_forward(f_c,f_u,f_ul) &&  machine.get_content().equals(\old((machine.get_content().union(new BSet<Integer>(f_c))))) &&  machine.get_chat().equals(\old((machine.get_chat().union(BRelation.cross(f_ul,new BSet<Integer>(f_u)))))) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().override(new BRelation<Integer,Pair<BRelation<Integer,Integer>,Integer>>(new Pair<Integer,Pair<BRelation<Integer,Integer>,BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>>>(f_u,(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (machine.get_chatcontent().domain().has(f_u) && machine.get_chatcontent().apply(f_u).domain().has(cc)); e.equals(new Pair<BRelation<Integer,Integer>,ERROR>(cc,machine.get_chatcontent().apply(f_u).apply(cc))))}).union(new BRelation<Integer,BSet<Integer>>(new Pair<Integer,BSet<Integer>>(f_c,(BRelation.cross(new BSet<Integer>(f_u),f_ul).union(BRelation.cross(f_ul,new BSet<Integer>(f_u)))))))))))))) &&  machine.get_inactive().equals(\old((machine.get_inactive().union(BRelation.cross(f_ul,new BSet<Integer>(f_u)).difference(machine.get_active()))))) &&  machine.get_toread().equals(\old((machine.get_toread().union(BRelation.cross(f_ul,new BSet<Integer>(f_u)).difference(machine.get_active()))))) &&  machine.get_toreadcon().equals(\old((machine.get_toreadcon().override(new BRelation<Integer,Pair<Integer,BSet<Integer>>>(new Pair<BSet<Integer>,Pair<BRelation<Integer,Integer>,Pair<Integer,ERROR>>>(f_c,new Best<Integer>(new JMLObjectSet {Integer uu | (\exists Integer e; (f_ul.has(uu) && !machine.get_active().has(new Pair<Integer,ERROR>(uu,f_u))); e.equals(new Pair<Integer,ERROR>(uu,f_u)))}))))))) &&  machine.get_owner().equals(\old((machine.get_owner().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(f_c,f_u)))))) &&  machine.get_chatcontentseq().equals(\old((machine.get_chatcontentseq().override(new BRelation<Integer,BRelation<Integer,Pair<Integer,BSet<Integer>>>>(new Pair<Integer,BRelation<BSet<Integer>,Pair<BSet<Integer>,Pair<Integer,ERROR>>>>(new Integer(machine.get_contentsize() + 1),new BRelation<Integer,Pair<Integer,BSet<Integer>>>(new Pair<BSet<Integer>,Pair<BSet<Integer>,Pair<Integer,ERROR>>>(f_c,(new Best<Integer>(new JMLObjectSet {Integer uu | (\exists Integer e; (f_ul.has(uu)); e.equals(new Pair<Integer,ERROR>(f_u,uu)))}).union(new Best<Integer>(new JMLObjectSet {Integer uu | (\exists Integer e; (f_ul.has(uu)); e.equals(new Pair<Integer,ERROR>(uu,f_u)))}))))))))))) &&  machine.get_contentsize() == \old(new Integer(machine.get_contentsize() + 1)); 
	 also
		requires !guard_forward(f_c,f_u,f_ul);
		assignable \nothing;
		ensures true; */
	public void run_forward( Integer f_c, Integer f_u, BSet<Integer> f_ul){
		if(guard_forward(f_c,f_u,f_ul)) {
			BSet<Integer> content_tmp = machine.get_content();
			BRelation<Integer,Integer> chat_tmp = machine.get_chat();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,BRelation<Integer,Integer>> toreadcon_tmp = machine.get_toreadcon();
			BRelation<Integer,Integer> owner_tmp = machine.get_owner();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontentseq_tmp = machine.get_chatcontentseq();
			Integer contentsize_tmp = machine.get_contentsize();

			machine.set_content((content_tmp.union(new BSet<Integer>(f_c))));
			machine.set_chat((chat_tmp.union(BRelation.cross(f_ul,new BSet<Integer>(f_u)))));
			machine.set_chatcontent(null); // Set Comprehension: feature not supported by EventB2Java
			machine.set_inactive((inactive_tmp.union(BRelation.cross(f_ul,new BSet<Integer>(f_u)).difference(machine.get_active()))));
			machine.set_toread((toread_tmp.union(BRelation.cross(f_ul,new BSet<Integer>(f_u)).difference(machine.get_active()))));
			machine.set_toreadcon(null); // Set Comprehension: feature not supported by EventB2Java
			machine.set_owner((owner_tmp.union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(f_c,f_u)))));
			machine.set_chatcontentseq(null); // Set Comprehension: feature not supported by EventB2Java
			machine.set_contentsize(new Integer(contentsize_tmp + 1));

			System.out.println("forward executed f_c: " + f_c + " f_u: " + f_u + " f_ul: " + f_ul + " ");
		}
	}

}

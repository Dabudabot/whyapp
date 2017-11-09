package group_6_model_sequential;

import eventb_prelude.*;
import Util.*;
//@ model import org.jmlspecs.models.JMLObjectSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class machine3{
	private static final Integer max_integer = Utilities.max_integer;
	private static final Integer min_integer = Utilities.min_integer;

	create_chat_session evt_create_chat_session = new create_chat_session(this);
	broadcast evt_broadcast = new broadcast(this);
	delete_chat_session evt_delete_chat_session = new delete_chat_session(this);
	unmute_chat evt_unmute_chat = new unmute_chat(this);
	select_chat evt_select_chat = new select_chat(this);
	unselect_chat evt_unselect_chat = new unselect_chat(this);
	forward evt_forward = new forward(this);
	reading evt_reading = new reading(this);
	delete_content evt_delete_content = new delete_content(this);
	mute_chat evt_mute_chat = new mute_chat(this);
	chatting evt_chatting = new chatting(this);
	remove_content evt_remove_content = new remove_content(this);


	/******Set definitions******/
	//@ public static constraint CONTENT.equals(\old(CONTENT)); 
	public static final BSet<Integer> CONTENT = new Enumerated(min_integer,max_integer);

	//@ public static constraint USER.equals(\old(USER)); 
	public static final BSet<Integer> USER = new Enumerated(min_integer,max_integer);


	/******Constant definitions******/


	/******Axiom definitions******/


	/******Variable definitions******/
	/*@ spec_public */ private BRelation<Integer,Integer> active;

	/*@ spec_public */ private BRelation<Integer,Integer> chat;

	/*@ spec_public */ private BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent;

	/*@ spec_public */ private BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontentseq;

	/*@ spec_public */ private BSet<Integer> content;

	/*@ spec_public */ private Integer contentsize;

	/*@ spec_public */ private BRelation<Integer,Integer> inactive;

	/*@ spec_public */ private BRelation<Integer,Integer> muted;

	/*@ spec_public */ private BRelation<Integer,Integer> owner;

	/*@ spec_public */ private BRelation<Integer,Integer> readChatContentSeq;

	/*@ spec_public */ private BRelation<Integer,Integer> toread;

	/*@ spec_public */ private BRelation<Integer,BRelation<Integer,Integer>> toreadcon;

	/*@ spec_public */ private BSet<Integer> user;




	/******Invariant definition******/
	/*@ public invariant
		user.isSubset(USER) &&
		content.isSubset(CONTENT) &&
		 chat.domain().isSubset(user) && chat.range().isSubset(user) && BRelation.cross(user,user).has(chat) &&
		 active.domain().isSubset(user) && active.range().isSubset(user) && active.isaFunction() && BRelation.cross(user,user).has(active) &&
		 muted.domain().isSubset(user) && muted.range().isSubset(user) && BRelation.cross(user,user).has(muted) &&
		active.isSubset(chat) &&
		muted.isSubset(chat) &&
		(muted.intersection(active)).equals(BSet.EMPTY) &&
		 chatcontent.domain().isSubset(user) && chatcontent.range().isSubset(BRelation.cross(content,((BRelation.cross(user,user)).pow()))) && chatcontent.isaFunction() && BRelation.cross(user,BRelation.cross(content,((BRelation.cross(user,user)).pow()))).has(chatcontent) &&
		 (\forall Integer u;  (\forall Integer c;  (\forall Integer u1;  (\forall Integer u2;((chatcontent.domain().has(u) && chatcontent.apply(u).domain().has(c) && chatcontent.apply(u).apply(c).has(new Pair<Integer,Integer>(u1,u2))) ==> (u.equals(u1) || u.equals(u2))))))) &&
		 (\forall Integer u;  (\forall Integer c;  (\forall Integer u1;  (\forall Integer u2;((chatcontent.domain().has(u) && chatcontent.apply(u).domain().has(c) && chatcontent.apply(u).apply(c).has(new Pair<Integer,Integer>(u1,u2))) ==> (chat.has(new Pair<Integer,Integer>(u1,u2)))))))) &&
		 (\forall Integer u1;  (\forall Integer u2;((user.has(u1) && user.has(u2) && chat.has(new Pair<Integer,Integer>(u1,u2))) ==> (!u1.equals(u2))))) &&
		 (\forall Pair<Integer,Integer> cs1;  (\forall Pair<Integer,Integer> cs2;  (\forall Integer u1;  (\forall Integer u2;((user.has(u1) && user.has(u2) && cs1.equals(new Pair<Integer,Integer>(u1,u2)) && cs2.equals(new Pair<Integer,Integer>(u1,u2)) && chat.has(cs1) && chat.has(cs2)) ==> (cs1.equals(cs2))))))) &&
		 toread.domain().isSubset(user) && toread.range().isSubset(user) && BRelation.cross(user,user).has(toread) &&
		toread.isSubset(chat) &&
		inactive.isSubset(chat) &&
		(active.union(toread.union(inactive))).equals(chat) &&
		(active.intersection(toread)).equals(BSet.EMPTY) &&
		(active.intersection(inactive)).equals(BSet.EMPTY) &&
		 toreadcon.domain().isSubset(content) && toreadcon.range().isSubset(((BRelation.cross(user,user)).pow())) && toreadcon.isaFunction() && BRelation.cross(content,((BRelation.cross(user,user)).pow())).has(toreadcon) &&
		 (\forall Integer u;  (\forall Integer c;  (\forall Integer u1;  (\forall Integer u2;((chatcontent.domain().has(u) && content.has(c) && chatcontent.apply(u).domain().has(c) && chat.has(new Pair<Integer,Integer>(u1,u2)) && chatcontent.apply(u).apply(c).has(new Pair<Integer,Integer>(u1,u2)) && toreadcon.domain().has(c) && toreadcon.apply(c).has(new Pair<Integer,Integer>(u1,u2))) ==> (toread.has(new Pair<Integer,Integer>(u1,u2)))))))) &&
		toreadcon.domain().isSubset(content) &&
		 owner.domain().equals(content) && owner.range().isSubset(user) && owner.isaFunction() && BRelation.cross(content,user).has(owner) &&
		owner.domain().equals(content) &&
		 (\forall Integer us;  (\forall Integer con;((user.has(us) && content.has(con) && chatcontent.domain().has(us) && chatcontent.apply(us).domain().has(con) && owner.domain().has(con)) ==> (us.equals(owner.apply(con)))))) &&
		(contentsize).compareTo(new Integer(0)) >= 0 &&
		 chatcontentseq.domain().isSubset(new Enumerated(new Integer(1),contentsize)) && chatcontentseq.range().isSubset(BRelation.cross(content,((BRelation.cross(user,user)).pow()))) && chatcontentseq.isaFunction() && BRelation.cross(new Enumerated(new Integer(1),contentsize),BRelation.cross(content,((BRelation.cross(user,user)).pow()))).has(chatcontentseq) &&
		 (\forall Integer i;  (\forall Integer c1;  (\forall Integer c2;((new Enumerated(new Integer(1),contentsize).has(i) && chatcontentseq.domain().has(i) && chatcontentseq.apply(i).domain().has(c1) && chatcontentseq.apply(i).domain().has(c2)) ==> (c1.equals(c2)))))) &&
		 (\forall Integer i;  (\forall Integer c; (\exists Integer u;((new Enumerated(new Integer(1),contentsize).has(i) && chatcontentseq.domain().has(i) && new BSet<Integer>(c).equals(chatcontentseq.apply(i).domain()) && chatcontent.apply(u).domain().has(c) && chatcontent.domain().has(u)) ==> (chatcontentseq.apply(i).isSubset(chatcontent.apply(u))))))) &&
		 (\forall Integer c1;  (\forall Integer c2;  (\forall Integer i;  (\forall Integer j;((new Enumerated(new Integer(1),contentsize).has(i) && chatcontentseq.domain().has(i) && new Enumerated(new Integer(1),contentsize).has(j) && chatcontentseq.domain().has(j) && chatcontentseq.apply(i).domain().has(c1) && chatcontentseq.apply(j).domain().has(c2) && c1.equals(c2)) ==> (i.equals(j))))))) &&
		 readChatContentSeq.domain().isSubset(new Enumerated(new Integer(1),contentsize)) && readChatContentSeq.range().isSubset(content) && readChatContentSeq.isaFunction() && BRelation.cross(new Enumerated(new Integer(1),contentsize),content).has(readChatContentSeq); */


	/******Getter and Mutator methods definition******/
	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.owner;*/
	public /*@ pure */ BRelation<Integer,Integer> get_owner(){
		return this.owner;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.owner;
	    ensures this.owner == owner;*/
	public void set_owner(BRelation<Integer,Integer> owner){
		this.owner = owner;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.contentsize;*/
	public /*@ pure */ Integer get_contentsize(){
		return this.contentsize;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.contentsize;
	    ensures this.contentsize == contentsize;*/
	public void set_contentsize(Integer contentsize){
		this.contentsize = contentsize;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.active;*/
	public /*@ pure */ BRelation<Integer,Integer> get_active(){
		return this.active;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.active;
	    ensures this.active == active;*/
	public void set_active(BRelation<Integer,Integer> active){
		this.active = active;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.chatcontentseq;*/
	public /*@ pure */ BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> get_chatcontentseq(){
		return this.chatcontentseq;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.chatcontentseq;
	    ensures this.chatcontentseq == chatcontentseq;*/
	public void set_chatcontentseq(BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontentseq){
		this.chatcontentseq = chatcontentseq;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.content;*/
	public /*@ pure */ BSet<Integer> get_content(){
		return this.content;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.content;
	    ensures this.content == content;*/
	public void set_content(BSet<Integer> content){
		this.content = content;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.toread;*/
	public /*@ pure */ BRelation<Integer,Integer> get_toread(){
		return this.toread;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.toread;
	    ensures this.toread == toread;*/
	public void set_toread(BRelation<Integer,Integer> toread){
		this.toread = toread;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.inactive;*/
	public /*@ pure */ BRelation<Integer,Integer> get_inactive(){
		return this.inactive;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.inactive;
	    ensures this.inactive == inactive;*/
	public void set_inactive(BRelation<Integer,Integer> inactive){
		this.inactive = inactive;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.chatcontent;*/
	public /*@ pure */ BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> get_chatcontent(){
		return this.chatcontent;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.chatcontent;
	    ensures this.chatcontent == chatcontent;*/
	public void set_chatcontent(BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent){
		this.chatcontent = chatcontent;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.chat;*/
	public /*@ pure */ BRelation<Integer,Integer> get_chat(){
		return this.chat;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.chat;
	    ensures this.chat == chat;*/
	public void set_chat(BRelation<Integer,Integer> chat){
		this.chat = chat;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.readChatContentSeq;*/
	public /*@ pure */ BRelation<Integer,Integer> get_readChatContentSeq(){
		return this.readChatContentSeq;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.readChatContentSeq;
	    ensures this.readChatContentSeq == readChatContentSeq;*/
	public void set_readChatContentSeq(BRelation<Integer,Integer> readChatContentSeq){
		this.readChatContentSeq = readChatContentSeq;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.toreadcon;*/
	public /*@ pure */ BRelation<Integer,BRelation<Integer,Integer>> get_toreadcon(){
		return this.toreadcon;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.toreadcon;
	    ensures this.toreadcon == toreadcon;*/
	public void set_toreadcon(BRelation<Integer,BRelation<Integer,Integer>> toreadcon){
		this.toreadcon = toreadcon;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.muted;*/
	public /*@ pure */ BRelation<Integer,Integer> get_muted(){
		return this.muted;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.muted;
	    ensures this.muted == muted;*/
	public void set_muted(BRelation<Integer,Integer> muted){
		this.muted = muted;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.user;*/
	public /*@ pure */ BSet<Integer> get_user(){
		return this.user;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.user;
	    ensures this.user == user;*/
	public void set_user(BSet<Integer> user){
		this.user = user;
	}



	/*@ public normal_behavior
	    requires true;
	    assignable \everything;
	    ensures
		user.isEmpty() &&
		content.isEmpty() &&
		chat.isEmpty() &&
		active.isEmpty() &&
		chatcontent.isEmpty() &&
		muted.isEmpty() &&
		toread.isEmpty() &&
		inactive.isEmpty() &&
		toreadcon.isEmpty() &&
		owner.isEmpty() &&
		contentsize == 0 &&
		chatcontentseq.isEmpty() &&
		readChatContentSeq.isEmpty();*/
	public machine3(){
		user = new BSet<Integer>();
		content = new BSet<Integer>();
		chat = new BRelation<Integer,Integer>();
		active = new BRelation<Integer,Integer>();
		chatcontent = new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>();
		muted = new BRelation<Integer,Integer>();
		toread = new BRelation<Integer,Integer>();
		inactive = new BRelation<Integer,Integer>();
		toreadcon = new BRelation<Integer,BRelation<Integer,Integer>>();
		owner = new BRelation<Integer,Integer>();
		contentsize = 0;
		chatcontentseq = new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>();
		readChatContentSeq = new BRelation<Integer,Integer>();

	}
}
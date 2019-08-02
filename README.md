# WARNIGN: NEVER USE THIS AS TEMPLATE OF GOOD ANDROID PROJECT. THIS PROJECT WAS CREATED ONLY TO TRY FORMAL METHODS (MATHEMATICAL PROVE) IN SOFTWARE DESIGN!

# whyapp
WhyApp is a WhatsApp clone to pass Models course

Innopolis University
Models of Software Systems
Requirements Specification Document

Introduction
This document presents the software requirements of WhatsApp2, a freeware instant messenger service for Smart
Phones. The software development of WhatsApp2 is based on the use of Formal Methods (mathematical)
techniques. We elicited our requirements by direcltying installing and using the WhatsApp app that is available
fromt the Android store. Our requirements don’t cater for the Web version of WhatsApp, but only for the Android
version of it.

Glossary
App - Android app
Content - Videos, photos, text, etc.
Local invariant - Property about a chat session
System invariant - Property about the working of several chat sessions

Product Description

Basic Functionality for Chat Sessions (machine0)
US-01 create_chat_session
create_chat_session As a user, I want to create a chat session so as to chat with another user
Acceptance criterion Given Chat session between Me and Another-User does not exist
Given Another-User is in my contact list
When I select a create chat session with Another-User
Then Chat session between Me and Another-User is created

US-02 select_chat
select_chat As a user, I want to select a chat session so as to chat with A-User
Acceptance criterion Given Chat session between Me and A-User exists
Given Chat session between Me and A-User is not active
When I select chat session with A-User
Then Chat session between Me and A-User is made active

US-03 chatting
chatting As a user, I want to send a text message or a content during a chat session
with another user so as to communicate with her
Acceptance criterion Given Chat session with Another-User is active
When Text is typed or content is produced and sent by Me
Then Text or content is made available to Another-User

US-04a delete_content
delete_content As a user, I want to delete some text or content exchanged with another
user during a chat session so as to unclutter a chat conversation
Acceptance criterion Given Content exists and has been received by Me
Given Me is not the sender but the receiver of the content
When Me selects to delete the content he has received
Then Content is removed only from chat session between Me and
Another-User, but not from the chat session between Another-User
and Me

US-04b remove_content
remove_content As a user, I want to delete some content exchanged with another user
during a chat session so as to unclutter a chat conversation
Acceptance criterion Given Content exists and has been sent by Me
Given Me is the sender of the content
When Me selects to remove the content he has sent
Then Content is removed only from chat session between Me and
Another-User, but not from the chat session between Another-User
and Me.

US-05 delete_chat_session
delete_chat_session As a user, I want to delete a chat session so as to keep uncluttered my
communication with other users
Acceptance criterion Given Chat session between Me and Another-User exists
When I select to delete the active chat session
Then Chat session that relates Me and Another-User is deleted as well
as its associated content and text

US-06 mute_chat
mute_chat As a user, I want to mute a chat session to prevent communication with
another user
Acceptance criterion Given Chat session between Me and Another-User exists 
When I select to mute a chat session
Then Chat session is muted and no communication from Me to the
muted user is permitted (or vice-versa)

US-07 unmute_chat
unmute_chat As a user, I want to unmute a chat session as to reestablish communication
with another user
Acceptance criterion Given Chat session between Me and Another-User is muted
When I select to unmute a chat session
Then Chat session is reestablished so communication from/to that
chat session is possible

US-08 broadcast
broadcast As a user, I want to broadcast a content to a group of users for quick
communication
Acceptance criterion Given Other-Users exist in my contact list
Given ME has access to the content
When I decide to broadcast the content to Other-Users
Then the content is sent to Other-Users

US-09 forward
forward As a user, I want to forward a content to a group of users for quick
communication
Acceptance criterion Given Other-Users exist in my contact list
Given ME has access to the content
Given respective chats between ME and Other-Users exist
When I decide to forward the content to Other-Users
Then the content is sent to Other-Users

US-10 uselect_chat
uselect_chat As a user, I want to unselect a chat session so as to chat with Another-User
Acceptance criterion Given Chat session between Me and A-User exists
Given Chat session between Me and A-User is active
When I unselect chat current session A-User
Then Chat session between Me and A-User is made inactive

Local Invariants for Basic Functionality of Chat a Sessions
1. A chat session between two users has a set of associated content available to both of them.
2. For a chat session, it’s never the case that some content is available to one user but not to the other.
3. Content is associated to a chat session only if one the users of the session has sent the content
to the other user (or vice-versa).
4. Several chat sessions can be created, but only one (or none) created chat session may be active.
5. Only a created chat session may be made active, however, a chat session can be created but
inactive.
6. A chat session relates exactly two (different) users.
7. Chat communication with a muted user is no feasible. I can try to send text or content to a muted
user but then it produces no effect.
8. A user cannot be muted and active at the same time.
 
System Invariants for Basic Functionality of Chat Sessions
1. Several chat sessions can coexist within the system.
2. Chat sessions are uniquely identified throughout the system.
3. Users are uniquely identified throughout the system.
4. Only one chat session maximum can be established between two different users.
5. Chat sessions are not symmetric. That is, the fact that user A has a created session to chat with
user B, does not necessarily mean that user B has a created session to chat with user A. And,
the fact that user A has an active chat session with user B does not necessarily means that user
B has an active chat session with user B.
6. It’s never the case that chat content exists associated to users for which no chat instance exists.
7. A content item is uniquely identified throughout the whole system.

Extended Functionality with Read/Unread Status (machine1)
EX-01 Read Stamp
Read Stamp As a user, I want to know which chat sessions I have read as to keep track
of new information
Acceptance criterion Given A chat session between Me and Another-User exists
When Any time
Then A Read/Unread stamp for that chat session is produced

EX-xx Time Stamps
Time Stamps As a user, I want to know the time stamp of a text message or content that I
have received as to better comprehend my chat discussions
Acceptance criterion Given A chat session between Me and Another-User exists
When I select to read a chat session with Another-User
Then Time stamps for each content or each piece of text in a chat are
produced

Local Invariants for Extended Functionality with Read/Unread Status
1. A chat session has an associated “read” or “unread” status.
2. Sent content has an associated “unread” time stamp by default until the content receiver decides
to read the chat.
3. A chat session has a “read” status if each piece of text or content that it contains has a “read”
status. If some chat content has associated an “unread” status then the whole chat session has a “unread” status associated.

System Invariants for Extended Functionality with Read/Unread Chats
1. For each chat session between User-A and User-B, the session as perceived by User-A reads
the same as the same session as perceived by User-B.

Extended Functionality with Ownership (machine2)
Local Invariants for Extended Functionality with Content Ownership
1. If a user sends some content, then she will be considered the owner of that content.
2. Each content item has an owner.
3. Each user can see any content that she owns

System Invariants for Extended Functionality with Content Ownership
1. The owner of some content is unique, that is, two different users cannot own the same content.

Extended Functionality with Implementation Details (machine3)
EX-02 reading_chat
reading_chat As a user, I want to read a chat session
Acceptance criterion Given Chat session between Me and Another-User exists
When I read a chat session between Me and Another-User
Then Content of chat session between Me and Another-user is made
available to Me

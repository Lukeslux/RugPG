package rugpg.telegram;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *	A class representing a message.
 *
 *	@author Yannick Stoffers <a href= "mailto:yannick@svcover.nl">yannick@svcover.nl</a>
 *	@version 1.0
 */
public class Message 
{
	/** The text contained in the message. */
	public final String text;

	/** The UNIX timestamp when the message was sent. */
	public final int date;

	/** The message id. */
	public final int id;

	/** Identity of the sender. */
	public final Sender sender;

	/** The chat in which the message was sent. */
	public final Chat chat;

	/**
	 *	Private constructor with parameters for each property.
	 *
	 *	@param text The text of the message.
	 *	@param date The date on which the message was sent.
	 *	@param id Identifier of the message.
	 *	@param sender The sender of the message.
	 *	@param chat The chat in which the message was sent.
	 */
	private Message (String text, int date, int id, Sender sender, Chat chat)
	{
		this.text = text;
		this.date = date;
		this.id = id;
		this.sender = sender;
		this.chat = chat;
	}

	/** 
	 *	Convernience constructor for json representation.
	 *
	 *	@param message The json representation of the message.
	 */
	public Message (JsonObject message)
	{
		this (
			message.getString ("text", ""), 
			message.getInt ("date"), 
			message.getInt ("message_id"), 
			new Sender (message.getJsonObject ("from")), 
			new Chat (message.getJsonObject ("chat"))
			);
	}

	/** Creates a message with the provided text, for the specified chat. 
	 *	<p>
	 *	This constructor is meant for messages that are to be send. Hence the lack of a sender, id 
	 *	and date argument.
	 *
	 *	@param text The text that the message should contain.
	 *	@param chat The chat to which the message must be send.
	 */
	public Message (String text, Chat chat)
	{
		this (text, 0, 0, null, chat);
	}

	/**
	 *	Constructs a json object out of this message.
	 *	<p>
	 *	The result is meant to be send, hence the lack of the date, sender and id properties.
	 *
	 *	@return A json object containing the text and chat id.
	 */
	public JsonObject toJson ()
	{
		return Json.createObjectBuilder ()
			.add ("chat_id", this.chat.id)
			.add ("text", this.text)
			.build ();
	}

	/**
	 *	Easy and pretty printing of messages.
	 *
	 *	@return String representation of this message.
	 */
	public String toString ()
	{
		return this.text 
			+ "\nby: " 
			+ this.sender 
			+ "\nin: " 
			+ this.chat;
	}
}

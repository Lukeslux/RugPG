package rugpg.telegram;

import javax.json.JsonObject;

/**
 *	Enum for representing the type of chat.
 *
 *	@author Yannick Stoffers <a href= "mailto:yannick@svcover.nl">yannick@svcover.nl</a>
 *	@version 1.0
 */
enum Type 
{
	PRIVATE, GROUP, SUPERGROUP, CHANNEL;
}

/**
 *	Class representing the chat.
 *	<p>
 *	This class only contains the information that all types of chat have in common: id and type 
 *	parameter.
 *
 *	@author Yannick Stoffers <a href= "mailto:yannick@svcover.nl">yannick@svcover.nl</a>
 *	@version 1.0
 */
public class Chat 
{
	/** The chat id. */
	public final int id;

	/** The type of chat it is. */
	public final Type type;

	/** 
	 *	Constuctor with all the parameters of the clas. 
	 *
	 *	@param id The chat id.
	 *	@param type The type of chat it concerns.
	 */
	public Chat (int id, String type)
	{
		this.id = id;
		this.type = Type.valueOf (type);
	}

	/**
	 *	Convenience constructor for json representation.
	 *
	 *	@param chat The json representation of the chat.
	 */
	public Chat (JsonObject chat)
	{
		this (chat.getInt ("id"), chat.getString ("type").toUpperCase ());
	}

	/**
	 *	Nice and easy printing.
	 *
	 *	@return The string representation of the chat.
	 */
	public String toString ()
	{
		return this.id + " " + this.type;
	}
}

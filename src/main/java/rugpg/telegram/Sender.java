package rugpg.telegram;

import javax.json.JsonObject;

/**
 *	Class representing the sender of a message.
 *
 *	@author Yannick Stoffers <a href= "mailto:yannick@svcover.nl">yannick@svcover.nl</a>
 *	@version 1.0
 */
public class Sender 
{
	/** Chat id. */
	public final int id;

	/** First name of the sender. Could also be a bot name. */
	public final String firstName;

	/**
	 *	Constructor containing all parameters of a sender.
	 *
	 *	@param firstName The first name of the sender. Could also be a bot name.
	 *	@param id The id of the user that is the sender.
	 */
	public Sender (String firstName, int id)
	{
		this.firstName = firstName;
		this.id = id;
	}

	/**
	 *	Convenience constructor for json representation.
	 *
	 *	@param sender Json representation of the sender.
	 */
	public Sender (JsonObject sender) 
	{
		this (sender.getString ("first_name"), sender.getInt ("id"));
	}

	/**
	 *	Nice and easy printing.
	 *
	 *	@return String representation of the sender.
	 */
	public String toString ()
	{
		return this.firstName;
	}
}

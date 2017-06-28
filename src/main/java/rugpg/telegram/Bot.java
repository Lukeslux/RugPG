package rugpg.telegram;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.ProtocolException;

import java.util.ArrayList;
import java.util.Collection;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**	
 *	This class functions as a bridge between the application and the Telegram Bot API. 
 *	<p>
 *	Since, there is no need to have multiple instances of this class, it has been designed using the 
 *	singleton pattern.
 *	
 *	@author Yannick Stoffers <a href= "mailto:yannick@svcover.nl">yannick@svcover.nl</a>
 *	@version 1.0
 */
public class Bot 
{
	/** The only instance, publicly accessible. */
	public static final Bot instance = new Bot ();

	/** The API token, used to identify the bot. */
	private finalString apiToken;

	/** Declaring this constructor makes the default constructor inaccessible. */
	private Bot () {}

	/** 
	 *	Sets the API token. This method must be called upon starting the application. 
	 *	@param token The unique bot token.
	 */
	public void setApiToken (String token)
	{
		this.apiToken = token;
	}

	/**
	 *	Retrieves all messages from the server.
	 *
	 *	@return A collection of messages.
	 */
	public Collection <Message> getUpdates ()
	{
		ArrayList <Message> set = new ArrayList <> ();
		try 
		{
			HttpURLConnection connection = (HttpURLConnection) new URL (
				"https://api.telegram.org/bot" 
				+ this.apiToken 
				+ "/getUpdates")
				.openConnection ();
			connection.setRequestMethod ("GET");
			connection.connect ();

			JsonReader reader = Json.createReader (connection.getInputStream ());
			JsonArray contents = reader.readObject ().getJsonArray ("result");			

			for (JsonObject update : contents.getValuesAs (JsonObject.class))
				set.add (new Message (update.getJsonObject ("message")));
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace ();
		}
		catch (ProtocolException e)
		{
			e.printStackTrace ();
		}
		catch (IOException e)
		{
			e.printStackTrace ();
		}
		return set;
	}

	/**
	 *	Sends the provided message to the recipient as indicated by the message itself. 
	 *	
	 *	@param message The message to be sent.
	 *	@return Returns whether the message has been delivered successfully.
	 */
	public boolean sendMessage (Message message)
	{
		int responseCode = 0;
		try 
		{
			HttpURLConnection connection = (HttpURLConnection) new URL (
				"https://api.telegram.org/bot" 
				+ this.apiToken 
				+ "/sendMessage")
				.openConnection ();
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput (true);
			connection.setRequestMethod ("POST");
			connection.connect ();

			BufferedWriter writer = new BufferedWriter (
				new OutputStreamWriter (connection.getOutputStream ()));
			writer.write (message.toJson ().toString ());
			writer.close ();

			responseCode = connection.getResponseCode ();

			JsonReader reader = Json.createReader (connection.getInputStream ());
			JsonObject response = reader.readObject ();

			System.out.println (
				responseCode 
				+ ": \"" 
				+ response.getJsonObject ("result").getString ("text") 
				+ "\" " 
				+ response.getJsonObject ("result").getJsonObject ("chat").getInt ("id"));
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace ();
		}
		catch (ProtocolException e)
		{
			e.printStackTrace ();
		}
		catch (IOException e)
		{
			e.printStackTrace ();
		}
		return responseCode == 200;
	}
}

package rugpg;

import rugpg.telegram.Bot;
import rugpg.telegram.Message;

/** 
 *	Main class of the RugPG, or "The RUG Role Playing Game Bot". 
 *	<p>
 *	This class contains the main method, nothing more. The program must be started by providing the 
 *	bot token as a CLI argument, as it would not make sense to start this program without linking it
 *	to the Telegram bot. In case you wish to modify this class, or use anything of this product, 
 *	make sure to keep the requirement that the API token must be provided. 
 *	<p>
 *	In order to obtain a bot token, go chat with the "BotFather" (@BotFather on Telegram). 
 *
 *	@author Yannick Stoffers <a href= "mailto:yannick@svcover.nl">yannick@svcover.nl</a>
 *	@version 1.0
 */
public class RugPG
{
	public static void main (String ... args)
	{
		Bot.instance.setApiToken (args[0]);
		for (Message m : Bot.instance.getUpdates ())
		{
			System.out.println (m);
			Bot.instance.sendMessage (m);
		}
	}
}

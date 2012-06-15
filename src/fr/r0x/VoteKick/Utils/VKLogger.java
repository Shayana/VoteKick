package fr.r0x.VoteKick.Utils;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;


import fr.r0x.VoteKick.Main.Main;

public class VKLogger
{
	protected Main plugin;
	protected Logger log;
	private PluginDescriptionFile description;
	
	public VKLogger(Main plugin)
	{
		this.plugin = plugin;
		this.log = Logger.getLogger("minecraft");
		this.description = plugin.getDescription();
		
	}

	
	public void enabled()
	{
		log.info("[" + description.getName() + " version " + description.getVersion() + " by "+ description.getAuthors()	+ " enabled !");
	}
	
	public void disabled()
	{
		log.info("[" + description.getPrefix() +"]" +" "+  description.getName() + " version " + description.getVersion() + " by "+ description.getAuthors()	+ " disabled !");
	}
	
	public void error()
	{
		log.severe("[" + description.getPrefix() + "]" + " "+ description.getName() + " critical error, disabling plugin.");
		plugin.getPluginLoader().disablePlugin(plugin);
	}
	

}

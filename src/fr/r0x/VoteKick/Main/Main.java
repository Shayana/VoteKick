package fr.r0x.VoteKick.Main;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;


import fr.r0x.VoteKick.Storage.Bans;
import fr.r0x.VoteKick.Storage.Kicks;
import fr.r0x.VoteKick.Storage.LogFile;
import fr.r0x.VoteKick.Utils.Configuration;
import fr.r0x.VoteKick.Utils.Messages;
import fr.r0x.VoteKick.Utils.VKLogger;

public class Main extends JavaPlugin
{
	public Configuration config;
	public Messages msg;
	public VKLogger log;
	public LogFile register;
	public Commands cmd;
	public Bans bansstorage;
	public Kicks kicksstorage;
	
	public HashMap<Player, Integer> kicks;
	public HashMap<Player, Integer> bans;
	public HashMap<Player, Integer> tempbans;
	public HashMap<Player, Player> votes;
	
	
@Override
	public void onEnable()
	{
		this.config = new Configuration(this);
		this.log = new VKLogger(this);
		this.msg = new Messages(this);
		this.cmd = new Commands(this);
		
		this.kicks = new HashMap<Player, Integer>();
		this.bans = new HashMap<Player, Integer>();
		this.tempbans = new HashMap<Player, Integer>();
		this.votes = new HashMap<Player, Player>();
		
		this.register = new LogFile(this);
		this.kicksstorage = new Kicks(this);
		this.bansstorage = new Bans(this);
		
		log.enabled();
	}

@Override
	public void onDisable()
	{
		log.disabled();
	}	

@EventHandler
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		return cmd.onCommand(sender, command, label, args);
	}
	
}

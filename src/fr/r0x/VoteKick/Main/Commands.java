package fr.r0x.VoteKick.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.r0x.VoteKick.Vote.Kick;

public class Commands implements Listener{
	
	protected Main plugin;
	public Commands(Main plugin)
	{
		this.plugin = plugin;
	}
	
	
@EventHandler

public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
{

	if (!(sender instanceof Player)){
		sender.sendMessage(plugin.getDescription().getPrefix() + " Connect to the game to perform this command !");
	}
//VoteKick
	if(label.equalsIgnoreCase("votekick"))
	{
		if (!(args.length == 1))
		{
			sender.sendMessage(plugin.getCommand(label).getUsage());
	return true;
		}
		if (!Bukkit.getPlayer(args[0]).isOnline())
		{
			sender.sendMessage(plugin.msg.noPlayer(args[0]));
	return true;
		}
		
		Player p = (Player) sender;
		Player voted = Bukkit.getPlayer(args[0]);
		Kick kick = new Kick(plugin, p, voted, "Kick");
		kick.vote();
	return true;
	}
	return true;
}
}

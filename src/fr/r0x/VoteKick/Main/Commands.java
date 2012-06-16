package fr.r0x.VoteKick.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.r0x.VoteKick.Vote.Ban;
import fr.r0x.VoteKick.Vote.Kick;
import fr.r0x.VoteKick.Vote.Stay;
import fr.r0x.VoteKick.Vote.TempBan;

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
	
	if (args.length < 1)
	{
		sender.sendMessage(plugin.getCommand(label).getUsage());
	return true;
	}
	if (Bukkit.getPlayer(args[0]) == null)
	{
		sender.sendMessage(plugin.msg.noPlayer(args[0]));
	return true;
	}
		
	Player voter = (Player) sender;
	Player voted = Bukkit.getPlayer(args[0]);
	
	
	if(label.equalsIgnoreCase("votekick"))
	{
		Kick kick = new Kick(plugin, voter, voted, "Kick");
		if (!plugin.kicks.containsKey(voted))
		{
		if (args.length > 1)
		{
			StringBuilder build = new StringBuilder();
			for (int i =0; i < args.length; i++)
			{
				if(i >= 2)
				{
					build.append(args[i-1]);
					build.append(" ");
				}
			}
			kick.setReason(build.toString());
		}
		}
		kick.kick();
	return true;
	}
	
	if(label.equalsIgnoreCase("voteban")){
		Ban ban = new Ban(plugin, voter, voted, "Ban");
		if (args.length > 1)
		{
			StringBuilder build = new StringBuilder();
			for (int i = 0; i < args.length; i++)
			{
				if(i >= 1)
				{
					build.append(args[i]);
					build.append(" ");
				}
			}
			ban.setReason(build.toString());
		}
		ban.ban();
	return true;
	}
	
	if(label.equalsIgnoreCase("votetempban"))
	{
		if (args.length < 2)
		{
			voter.sendMessage(plugin.getCommand(label).getUsage());
		return true;
		}
		int time = 0;
		try
		{
			time = Integer.parseInt(args[1]);
		}
		catch(NumberFormatException e)
		{
			voter.sendMessage(plugin.getCommand(label).getUsage());
		return true;
		}
		TempBan temp = new TempBan(plugin, voter, voted, "TempBan");
		if(args.length > 2)
		{
			StringBuilder build = new StringBuilder();
			for (int i = 0; i < args.length; i++)
			{
				if (i >= 3)
				{
				build.append(args[i -1]);
				build.append(" ");
				}
			}
			temp.setReason(build.toString());
		}
		temp.setTime(time);
		temp.tempban();
		return true;
	}
	
	if(label.equalsIgnoreCase("votestay"))
	{
		Stay stay = new Stay(plugin, voter, voted, "Stay");
		stay.stay();
	return true;
	}
	
	if(label.equalsIgnoreCase("votecancel"))
	{
		if(!voter.hasPermission("votekick.admin"))
		{
			voter.sendMessage(plugin.msg.noPerm());
		return true;
		}
		else
		{
			if(plugin.kicks.containsKey(voted))
			{
				Kick kick = new Kick(plugin, voter, voted, "Kick");
				kick.accomplish();
				Bukkit.broadcastMessage(plugin.msg.VoteCanceled(voted));
				for(Player p : Bukkit.getOnlinePlayers())
				{
					if(p.hasPermission("votekick.notify"))
						{
						p.sendMessage(plugin.msg.hasCancelled(voter, voted));
						}
				}
			return true;
			}
			if(plugin.bans.containsKey(voted))
			{
				Ban ban = new Ban(plugin, voter, voted, "Ban");
				ban.accomplish();
				Bukkit.broadcastMessage(plugin.msg.VoteCanceled(voted));
				for(Player p : Bukkit.getOnlinePlayers())
				{
					if(p.hasPermission("votekick.notify"))
						{
						p.sendMessage(plugin.msg.hasCancelled(voter, voted));
						}
				}
			return true;
			}
			if(plugin.tempbans.containsKey(voted))
			{
				TempBan temp = new TempBan(plugin, voter, voted, "TempBan");
				temp.accomplish();
				for(Player p : Bukkit.getOnlinePlayers())
				{
					if(p.hasPermission("votekick.notify"))
						{
						p.sendMessage(plugin.msg.hasCancelled(voter, voted));
						}
				}
			return true;
			}
			else
			{
				voter.sendMessage(plugin.msg.noVote(voter));
			return true;
			}
			
		}
	}
	
	if(label.equalsIgnoreCase("voteforce"))
	{
		if(!voter.hasPermission("votekick.admin"))
		{
			voter.sendMessage(plugin.msg.noPerm());
		return true;
		}
		if(plugin.kicks.containsKey(voted))
		{
			Kick kick = new Kick(plugin, voter, voted, "Kick");
			voted.kickPlayer(plugin.msg.kickedscr());
			plugin.kicksstorage.Kick(kick);
			kick.accomplish();
		}
		if(plugin.bans.containsKey(voted))
		{
			Ban ban = new Ban(plugin, voter, voted, "Kick");
			voted.kickPlayer(plugin.msg.kickedscr());
			plugin.bansstorage.Ban(ban);
			ban.accomplish();
		}
		if(plugin.tempbans.containsKey(voted))
		{
			TempBan ban = new TempBan(plugin, voter, voted, "Kick");
			voted.kickPlayer(plugin.msg.kickedscr());
			plugin.tempbansstorage.Tempban(ban);
			ban.accomplish();
		}
		else
		{
			voter.sendMessage(plugin.msg.noVote(voted));
		}
	}
	return true;
}
}

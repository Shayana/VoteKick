package fr.r0x.votekick.commandsexecutors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;
import fr.r0x.votekick.Vote.Ban;
import fr.r0x.votekick.Vote.Kick;
import fr.r0x.votekick.Vote.TempBan;
import fr.r0x.votekick.Vote.UnBan;

public class VoteCancel   implements CommandExecutor {

	protected Main plugin;
	private Player voter;
	private Player voted;
	
	public VoteCancel(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		voter = (Player) sender;
		if(!voter.hasPermission("votekick.admin"))
		{
			voter.sendMessage(plugin.msg.noPerm());
		return true;
		}
		
		if(args.length < 1)
		{
			sender.sendMessage(command.getDescription() + ": " + command.getUsage());
		return true;
		}
		
	
		
		//Cancel a voteUnban
		
		if(plugin.unbans.containsKey(args[0]))
		{
			UnBan unb = new UnBan(plugin, voter, args[0], "UnBan");
			unb.accomplish();
			for(Player p : Bukkit.getOnlinePlayers())
			{
				if(!plugin.muted.contains(p))	p.sendMessage(plugin.msg.VoteCanceled(args[0]));
				if(p.hasPermission("votekick.notify"))	p.sendMessage(plugin.msg.hasCancelled(voter, voted));
			}
		return true;
		}		
		
		try
		{
			voted = Bukkit.getPlayer(args[0]);
					
		}
		catch(NullPointerException e)
		{
			voter.sendMessage(plugin.msg.noPlayer(args[0]));
		}
		
		
		//Cancel a voteKick
		
		if(plugin.kicks.containsKey(voted))
		{
			voted = Bukkit.getPlayer(args[0]);
			Kick kick = new Kick(plugin, voter, voted, "Kick");
			kick.accomplish();
			for(Player p : Bukkit.getOnlinePlayers())
			{
				if(!plugin.muted.contains(p)) p.sendMessage(plugin.msg.VoteCanceled(voted.getName()));
				if(p.hasPermission("votekick.notify")) p.sendMessage(plugin.msg.hasCancelled(voter, voted));
			}
		return true;
		}
		
		
		//Cancel a voteBan
		
		if(plugin.bans.containsKey(voted))
		{
			Ban ban = new Ban(plugin, voter, voted, "Ban");
			ban.accomplish();
				
			for(Player p : Bukkit.getOnlinePlayers())
			{
				if(!plugin.muted.contains(p))	p.sendMessage(plugin.msg.VoteCanceled(voted.getName()));
				if(p.hasPermission("votekick.notify"))	p.sendMessage(plugin.msg.hasCancelled(voter, voted));
			}
		return true;
		}
		
		
		//Cance a voteTempBan
		
		if(plugin.tempbans.containsKey(voted))
		{
			TempBan temp = new TempBan(plugin, voter, voted, "TempBan");
			temp.accomplish();
			
			for(Player p : Bukkit.getOnlinePlayers())
				{
				if(!plugin.muted.contains(p))	p.sendMessage(plugin.msg.VoteCanceled(voted.getName()));
				if(p.hasPermission("votekick.notify"))	p.sendMessage(plugin.msg.hasCancelled(voter, voted));
				}
		return true;
		}
		
		
		//No vote against args[0]
		else
		{
			voter.sendMessage(plugin.msg.noVote(voter));
		return true;
		}
	}

}

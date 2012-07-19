package fr.r0x.votekick.commandsexecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.r0x.votekick.Main.Main;
import fr.r0x.votekick.Vote.Ban;
import fr.r0x.votekick.Vote.Kick;
import fr.r0x.votekick.Vote.TempBan;
import fr.r0x.votekick.Vote.UnBan;

public class VoteForce extends VoteCommand implements CommandExecutor	
{

	public VoteForce(Main plugin) {
		super(plugin);
		setVote("VoteForce");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!validate(sender, command, label, args))
		{
			return true;
		}
		
		if(!voter.hasPermission("votekick.admin"))
		{
			voter.sendMessage(plugin.msg.noPerm());
		return true;
		}
		if(plugin.kicks.containsKey(voted))
		{
			vote = new Kick(plugin, voter, voted, "Kick");
			voted.kickPlayer(plugin.msg.kickedscr(vote));
			plugin.kicksstorage.Kick(vote);
			vote.accomplish();
			return true;
		}
		
		
		if(plugin.bans.containsKey(voted))
		{
			vote = new Ban(plugin, voter, voted, "Kick");
			voted.kickPlayer(plugin.msg.kickedscr(vote));
			plugin.bansstorage.Ban(vote);
			vote.accomplish();
			return true;
		}
		
		
		if(plugin.tempbans.containsKey(voted))
		{
			vote = new TempBan(plugin, voter, voted, "Kick");
			voted.kickPlayer(plugin.msg.kickedscr(vote));
			plugin.tempbansstorage.Tempban((TempBan)vote);
			vote.accomplish();
			return  true;
		}
		
		
		if(plugin.unbans.containsKey(voted))
		{
			UnBan unban = new UnBan(plugin, voter, null, "Unban");
			plugin.bansstorage.unBan(unban);
			unban.accomplish();
			return true;
		}
		else
		{
			voter.sendMessage(plugin.msg.noVote(voted));
			return true;
		}
	}

}

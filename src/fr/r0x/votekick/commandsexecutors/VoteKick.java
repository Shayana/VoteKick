package fr.r0x.votekick.commandsexecutors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.r0x.votekick.Main.Main;
import fr.r0x.votekick.Vote.Kick;

public class VoteKick extends VoteCommand implements CommandExecutor {
	
	public VoteKick(Main plugin)
	{
		super(plugin);
		this.setVote("Kick");
	}

	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
	if(!validate(sender, command, label, args))
	{
		return true;
	}
		this.vote = new Kick(plugin, voted, voted, voteType);
		
		try
		{
			voted = Bukkit.getPlayer(args[0]);
		}
		catch(NullPointerException e)
		{
			sender.sendMessage(plugin.msg.noPlayer(args[0]));
			return true;
		}
		
		vote = new Kick(plugin, voter, voted, "Kick");
		
		if (!plugin.kicks.containsKey(voted) && args.length > 1)
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
			vote.setReason(build.toString());
		}
		((Kick) vote).kick();
		return true;
	}
}

package fr.r0x.votekick.commandsexecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.r0x.votekick.Main.Main;
import fr.r0x.votekick.Vote.Ban;

public class VoteBan extends VoteCommand implements CommandExecutor {

	public VoteBan(Main plugin) {
		super(plugin);
		this.setVote("Ban");
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(!validate(sender, command, label, args))
		{
			return true;
		}
		this.vote = new Ban(plugin, voter, voted, "Ban");
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
			this.vote.setReason(build.toString());
		}
	((Ban) this.vote).ban();
	return true;
	}

}

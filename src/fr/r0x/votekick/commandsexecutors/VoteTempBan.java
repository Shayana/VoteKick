package fr.r0x.votekick.commandsexecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import fr.r0x.votekick.Main.Main;
import fr.r0x.votekick.Vote.TempBan;

public class VoteTempBan extends VoteCommand implements CommandExecutor {

	public VoteTempBan(Main plugin)
	{
		super(plugin);
		
	}

	private int time = 0;
	private TempBan temp;
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length < 2)
		{
			sender.sendMessage(command.getDescription() + ": " + command.getUsage());
		return true;
		}
		
		if(!validate(sender, command, label, args))
		{
			return true;
		}
		
		try
		{
			Integer.parseInt(args[1]);
		}
		catch(NumberFormatException e)
		{
			voter.sendMessage(plugin.getCommand(label).getUsage());
		return true;
		}
		
		this.time = Integer.parseInt(args[1]);
		
		temp = new TempBan(plugin, voter, voted, "TempBan");
		
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
}

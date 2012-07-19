package fr.r0x.votekick.commandsexecutors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;
import fr.r0x.votekick.Vote.Stay;

public class VoteStay implements CommandExecutor {

	public Main plugin;
	protected Player voter;
	protected Player voted;
	
	public VoteStay(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(args.length < 1)
		{
			sender.sendMessage(command.getDescription() + ": " + command.getUsage());
		return true;
		}

		try
		{
			voted = Bukkit.getPlayer(args[0]);
		}
		catch(NullPointerException e)
		{
			sender.sendMessage(plugin.msg.noPlayer(args[0]));
		return true;
		}
		
		voter = (Player) sender;
		Stay stay = new Stay(plugin, voter, voted, "Stay");
		stay.stay();
	return true;
	}

}

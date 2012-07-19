package fr.r0x.votekick.commandsexecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;
import fr.r0x.votekick.Vote.UnBan;

public class VoteUnban implements CommandExecutor {
	
	protected Main plugin;
	private Player voter;
	private String name;
	private UnBan unban;
	
	public VoteUnban(Main plugin) {
		this.plugin = plugin;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		
		if(args.length < 1)
		{
			sender.sendMessage(command.getDescription() + ": " + command.getUsage());
		return true;
		}
		name = args[0];
		voter = (Player) sender;
		unban = new UnBan(plugin, voter, name, "Unban");
		unban.unban();
	return true;
	}

}

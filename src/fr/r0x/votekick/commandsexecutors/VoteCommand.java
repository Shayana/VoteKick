package fr.r0x.votekick.commandsexecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;
import fr.r0x.votekick.Vote.Vote;

public class VoteCommand implements CommandExecutor{
	
	protected Main plugin;
	protected Player voter;
	protected Player voted;
	protected String voteType;
	protected Vote vote;
	protected Command cmd;
	protected CommandSender sender;
	protected String[] args;
	
	public VoteCommand(Main plugin)
	{
		this.plugin = plugin;
	}
	
	
	public void setVote(String vote)
	{
		this.voteType = vote;
	}
	
	public boolean validate(CommandSender sender, Command command,
			String label, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Please connect to Minecraft to vote against a player");
			return false;
		}
		if (args.length < 1)
		{
			sender.sendMessage(plugin.msg.name() + command.getDescription() +": "+ command.getUsage());
			return false;
		}		
		if(plugin.getServer().getPlayer(args[0]) == null)
		{
			sender.sendMessage(plugin.msg.noPlayer(args[0]));
			return false;			
		}
		voted = plugin.getServer().getPlayer(args[0]);
		voter = (Player) sender;
		return true;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return true;}
}

package fr.r0x.votekick.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;
import fr.r0x.votekick.Vote.Vote;

public class Configuration {
	
	protected Main plugin;
	
	public Configuration(Main plugin)
	{
		this.plugin = plugin;		
	}
	
	
	public void setupConfig()
	{
		if(!plugin.getConfig().contains("Percentage"))
		{
		plugin.getConfig().addDefault("Percentage", true);
		plugin.getConfig().createSection("MinimumOnline");
		plugin.getConfig().addDefault("MinimumOnline.ban", 5);
		plugin.getConfig().addDefault("MinimumOnline.kick", 5);
		plugin.getConfig().addDefault("MinimumOnline.tempban", 5);
		plugin.getConfig().addDefault("MinimumOnline.unban", 5);
		
		plugin.getConfig().createSection("VotesNeeded");
		plugin.getConfig().addDefault("VotesNeeded.ban", 50);
		plugin.getConfig().addDefault("VotesNeeded.kick", 50);
		plugin.getConfig().addDefault("VotesNeeded.tempban", 50);
		plugin.getConfig().addDefault("VotesNeeded.unban", 50);
		plugin.getConfig().addDefault("VoteLimits", true);
		
		plugin.getConfig().createSection("MaxVotes");
		plugin.getConfig().addDefault("MaxVotes.ban", 15);
		plugin.getConfig().addDefault("MaxVotes.kick", 15);
		plugin.getConfig().addDefault("MaxVotes.tempban", 15);
		plugin.getConfig().addDefault("MaxVotes.unban", 15);
		plugin.getConfig().addDefault("MaxVotes.stay", 15);
		
		plugin.getConfig().addDefault("UseTimer", true);
		plugin.getConfig().createSection("Delay");
		plugin.getConfig().addDefault("Delay.ban", 60);
		plugin.getConfig().addDefault("Delay.kick", 60);
		plugin.getConfig().addDefault("Delay.tempban", 60);
		plugin.getConfig().addDefault("Delay.unban", 60);
		
		plugin.getConfig().addDefault("UseMaxTime", true);
		plugin.getConfig().addDefault("MaxTimeTempBan", 120);
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
		}
	}
	
	public int votesNeeded(Vote vote)
	{
		if (plugin.getConfig().getBoolean("Percentage"))
		{	
			 int total = plugin.getConfig().getInt("VotesNeeded."+ vote.getVote().toLowerCase());
			 int canvote = 0;
			 for (Player p : Bukkit.getOnlinePlayers())
			 {
				 if (p.hasPermission("votekick."+ vote.getVote().toLowerCase()) && p != vote.getVoted())
				 {
					 canvote ++;
				 }
			 }
			 return ((total * canvote) / 100);
		}
		else
		{
			return plugin.getConfig().getInt("VotesNeeded."+ vote.getVote().toLowerCase());
		}
	}
	
	public boolean votelimit()
	{
		if(plugin.getConfig().getBoolean("VoteLimits"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public int maxVotes(Vote vote)
	{
		return plugin.getConfig().getInt("MaxVotes." + vote.getVote().toLowerCase());
	}
	
	
	
	
	public boolean useMaxTime()
	{
		if(plugin.getConfig().getBoolean("UseMaxTime"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int maxTime()
	{
		return Integer.parseInt(plugin.getConfig().getString("MaxTimeTempBan"));
	}
	
	
	
	public boolean doTimer()
	{
		return plugin.getConfig().getBoolean("UseTimer");
	}
	
	public int Timer(Vote vote)
	{
		return plugin.getConfig().getInt("Delay."+vote.getVote().toLowerCase()) * 20;
	}
	
	
	
	public int minPlayers(Vote vote)
	{
		return plugin.getConfig().getInt("MinimumOnline."+ vote.getVote().toLowerCase());
	}

}

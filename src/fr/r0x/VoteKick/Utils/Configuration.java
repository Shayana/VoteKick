package fr.r0x.VoteKick.Utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.r0x.VoteKick.Main.Main;
import fr.r0x.VoteKick.Vote.Vote;

public class Configuration {
	
	protected Main plugin;
	protected FileConfiguration config;
	
	public Configuration(Main plugin)
	{
		this.plugin = plugin;
		this.config = plugin.getConfig();
		
	}
	
	public int votesNeeded(Vote vote)
	{
		if (config.getBoolean("Percentage"))
		{	
			 int total = config.getInt("VotesNeeded."+ vote.getVote());
			 int canvote = 0;
			 for (Player p : Bukkit.getOnlinePlayers())
			 {
				 if (p.hasPermission("votekick."+ vote.getVote().toLowerCase()));
					 {
					 canvote ++;
					 }
			 }
			 return ((canvote / 100) * total);
			}
		else
		{
			return config.getInt("VotesNeeded."+ vote.getVote().toLowerCase());
		}
	}
	
	
	public int maxVotes(Vote vote)
	{
		return config.getInt("MaxVotes."+vote.getVote());
	}
	
	public int maxTime()
	{
		return config.getInt("MaxTimeTempBan");
	}
	
	public boolean doTimer()
	{
		return config.getBoolean("UseTimer");
	}
	public int Timer()
	{
		return config.getInt("VoteDelay");
	}

}

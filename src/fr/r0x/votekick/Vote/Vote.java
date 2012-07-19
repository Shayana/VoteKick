package fr.r0x.votekick.Vote;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;

public class Vote {

	protected Main plugin;
	protected Player voter;
	protected Player voted;
	protected String player;
	
	protected String type;
	protected String reason;
	protected int time;
	protected int remaining;
	
	protected HashMap<Player, Integer> map;
	protected HashMap<Player, ArrayList<Player>> voters;
	protected HashMap<String, Integer> unmap;
	protected HashMap<String, ArrayList<Player>> unvoters;
		
	public Vote(Main plugin, Player voter, Player voted, String vote)
	{
		this.plugin = plugin;
		this.voted = voted;
		this.voter = voter;
		this.type = vote;
		this.voters = plugin.votes;
	}
	
	public Vote(Main plugin, Player voter, String voted, String vote)
	{
		this.plugin = plugin;
		this.voter = voter;
		this.player = voted;
		this.type = vote;
		this.unmap = plugin.unbans;
		this.unvoters = plugin.unvotes;
	}
	
	public void setReason(String reason)
	{
		 plugin.reasons.put(voted, reason);
	}
	
	
	public void setMap(HashMap<Player, Integer> map)
	{
		this.map = map;
	}
		
	public void setList(HashMap<Player, ArrayList<Player>> list)
	{
		this.voters = list;
	}
	
	public HashMap<Player, Integer> getMap()
	{
		return map;
	}
	
	
	public String getPlayer()
	{
		return player;
	}
	
	public String getVote()
	{
		return type;
	}
	
	public Player getVoted()
	{
		return voted;
	}
	
	public Player getVoter()
	{
		return voter;
	}
	
	public String getReason()
	{
		return plugin.reasons.get(voted);
	}
	
	
	public int getTime()
	{
		return time;
	}
	
	public int getRemaining()
	{
		return remaining;
	}
	
	
	public ArrayList<String> getList()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		for (Entry<Player, ArrayList<Player>> entry : voters.entrySet())
		{
			if (entry.getKey().equals(voted))
			{
				for (Player p : entry.getValue())
				{
					list.add(p.getName());
				}
				
			}
		}
		return list;
	}
	
	
	
	public boolean canVote()
	{
		if (voter == voted)
		{
			voter.sendMessage(plugin.msg.forYourself());
			return false;
		}
			
		if (!voter.hasPermission("votekick."+ getVote().toLowerCase()) && !voter.hasPermission("votekick.vote") && !voter.hasPermission("votekick.admin"))
		{
			voter.sendMessage(plugin.msg.noPerm());
		return false;
		}
		
		if(voted.hasPermission("votekick.protected"))
		{
			voter.sendMessage(plugin.msg.Protected(voted));
		return false;
		}
		
		if(!map.containsKey(voted) && !voter.hasPermission("votekick.start"))
		{
			voter.sendMessage(plugin.msg.cantStart());
			return false;
		}
		
		if (plugin.config.votelimit() && (plugin.register.getCount(voter, this) >= plugin.config.maxVotes(this)))
		{
		voter.sendMessage(plugin.msg.maxVotes());
		return false;
		}
		
		if (voters.containsKey(voted) && voters.get(voted).contains(voter))
		{
			voter.sendMessage(plugin.msg.allreadyVoted(voted));
		return false;
		}
		
		
		return true;
	}
	
	
	
	public void vote()
	{
//Let's report this vote !
		if(voters.containsKey(voted))
		{
			ArrayList<Player> list = voters.get(voted);
			list.add(voter);
		voters.remove(voted);
		voters.put(voted, list);
		}
		if (!voters.containsKey(voted))
		{
			ArrayList<Player> list = new ArrayList<Player>();
			list.add(voter);
			voters.put(voted, list);
		}
		plugin.register.addCount(voter, this);
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if (p.hasPermission("votekick.notify")){
				p.sendMessage(plugin.msg.hasVoted(voter, voted));
			}
		}
		
//Already a vote ?
		
		if(map.containsKey(voted))
		{
			int i = map.get(voted);
			map.put(voted, i + 1);
		}
		
//Or no vote yet ?
		
		else
		{
			map.put(voted, 1);
			for (Player p : Bukkit.getOnlinePlayers())
			{
				if(!plugin.muted.contains(p))
				{
					if (getVote().equals("Kick")) p.sendMessage(plugin.msg.VoteStartKick(this));
					else if (getVote().equals("Ban")) p.sendMessage(plugin.msg.VoteStartBan(this));
					else if (getVote().equals("TempBan")) p.sendMessage(plugin.msg.VoteStartTempBan(this));
				}		
			}
			if (plugin.config.doTimer())
			{
				timer();
			}
		}
		remaining = plugin.config.votesNeeded(this) - map.get(voted);
	
	}
	
	public void accomplish()
	{
		map.remove(voted);
		plugin.reasons.remove(voted);
		voters.remove(voted);
		
	}
	
	
	public void timer()
	{
		int timer = plugin.config.Timer(this);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
		{
			   public void run()
			   {
				   if (!map.containsKey(voted))
				   {
				return;
				   }
				   for(Player p : plugin.getServer().getOnlinePlayers())
				   {
					   if(!plugin.muted.contains(p))
					   {
						   p.sendMessage(plugin.msg.Timeout(voted));
					   }
				   }
				   
			       accomplish();   
				  
				   
			   }
		}, (long)timer);
	}
	
	
	
}

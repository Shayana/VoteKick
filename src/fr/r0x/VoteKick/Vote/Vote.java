package fr.r0x.VoteKick.Vote;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.r0x.VoteKick.Main.Main;

public class Vote {

	protected Main plugin;
	protected Player voter;
	protected Player voted;
	
	protected String type;
	protected String reason;
	protected int time;
	protected int remaining;
	
	public HashMap<Player, Integer> map;
	public HashMap<Player, ArrayList<Player>> voters;
	
	protected boolean can = true;
	
	public Vote(Main plugin, Player voter, Player voted, String vote)
	{
		this.plugin = plugin;
		this.voted = voted;
		this.voter = voter;
		this.type = vote;
		this.voters = plugin.votes;
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
			can = false;
			return false;
		}
		if (!voter.hasPermission("votekick."+ getVote().toLowerCase()) && !voter.hasPermission("votekick.admin"))
		{
			voter.sendMessage(plugin.msg.noPerm());
			can = false;
		return false;
		}
		if(voted.hasPermission("votekick.protected"))
		{
			voter.sendMessage(plugin.msg.Protected(voted));
			can = false;
		return false;
		}
		if (plugin.config.votelimit() && plugin.register.getCount(voter, this) >= plugin.config.maxVotes(this))
		{
		voter.sendMessage(plugin.msg.maxVotes());
		can = false;
		return false;
		}
		if (voters.containsKey(voted) && voters.get(voted).contains(voter))
		{
			voter.sendMessage(plugin.msg.allreadyVoted(voted));
			can = false;
		return false;
		}
		else
		{
			can = true;
		return true;
		}
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
			if (getVote().equals("Kick"))
			{
				Bukkit.broadcastMessage(plugin.msg.VoteStartKick(voted));
			}
			else if (getVote().equals("Ban"))
			{
				Bukkit.broadcastMessage(plugin.msg.VoteStartBan(voted));
			}
			else if (getVote().equals("TempBan"))
			{
				Bukkit.broadcastMessage(plugin.msg.VoteStartTempBan(voter, getTime()));
			}
			if (plugin.config.doTimer())
			{
				timer();
			}
		}
		remaining = plugin.config.votesNeeded(this) - map.get(voted);
		if(remaining > 0)
		{
		Bukkit.broadcastMessage(String.valueOf(remaining));
		}
	
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
				   Bukkit.broadcastMessage(plugin.msg.Timeout(voted));
			       accomplish();   
				  
				   
			   }
		}, (long)timer);
	}
	
	
	
}

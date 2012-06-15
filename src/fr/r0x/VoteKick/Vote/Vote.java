package fr.r0x.VoteKick.Vote;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	public HashMap<Player, Integer> map;
	public HashMap<Player, Player> voters;
	
	public Vote(Main plugin, Player voter, Player voted, String vote)
	{
		this.plugin = plugin;
		this.voted = voted;
		this.voter = voter;
		this.type = vote;
	}
	
	public void setReason(String reason)
	{
		this.reason = reason;
	}
	
	
	public void setMap(HashMap<Player, Integer> map)
	{
		this.map = map;
	}
	
	public void setList(HashMap<Player, Player> list)
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
		return reason;
	}
	
	
	
	
	
	public List<String> getList()
	{
		List<String> list = new ArrayList<String>();
		
		for (Entry<Player, Player> entry : voters.entrySet())
		{
			if (entry.getValue().equals(voted) && entry.getKey().equals(voter))
			{
				list.add(entry.getKey().getName());
			}
		}
		return list;
	}
	
	
	
	public boolean canVote()
	{
		if (!voter.hasPermission("votekick."+getVote().toLowerCase()))
		{
			voter.sendMessage(plugin.msg.noPerm());
		return false;
		}
		if(voted.hasPermission("votekick.protected"))
		{
			voter.sendMessage(plugin.msg.Protected(voted));
		return false;
		}
		if (plugin.config.maxVotes(this) >= plugin.register.getCount(voter, this))
		{
		voter.sendMessage(plugin.msg.maxVotes());
		return false;
		}
		if (voters.containsKey(voter) && voters.get(voter).equals(voted))
		{
			voter.sendMessage(plugin.msg.allreadyVoted(voted));
		return false;
		}
		else
		{
		return true;
		}
	}
	
	
	
	public void vote()
	{
		if (!canVote())
		{
		return;
		}
				
//Let's report this vote !
		
		voters.put(voter, voted);
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
			map.put(voted, i++);
			Bukkit.broadcastMessage(plugin.msg.remainingVotes(voted));
		}
		
//Or no vote yet ?
		
		else
		{
			map.put(voted, 1);
			Bukkit.broadcastMessage(plugin.msg.VoteStartKick(voted));
		}
	
	}
	
	public void accomplish()
	{
		map.remove(voted);
		for (Entry<Player, Player> entry : voters.entrySet())
		{
			if (entry.getKey().equals(voted) && entry.getValue().equals(voter)){
				voters.remove(entry);
			}
		}
	}
	
	
	
}

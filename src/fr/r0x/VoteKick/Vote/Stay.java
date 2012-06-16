package fr.r0x.VoteKick.Vote;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.r0x.VoteKick.Main.Main;

public class Stay extends Vote{

	public Stay(Main plugin, Player voter, Player voted, String vote)
	{
		super(plugin, voter, voted, vote);
	}

	
	public void stay()
	{
		if (plugin.kicks.containsKey(voted))
		{
			setMap(plugin.kicks);
		}
		else if(plugin.bans.containsKey(voted))
		{
			setMap(plugin.bans);
		}
		else if(plugin.tempbans.containsKey(voted))
		{
			setMap(plugin.tempbans);
		}
		else{
			voter.sendMessage(plugin.msg.noVote(voted));
			return;
		}
		if(canVote())
		{
			int votes = map.get(voted);
			map.put(voted, votes - 1);
			ArrayList<Player> list = voters.get(voted);
			list.add(voter);
			voters.put(voted, list);
			Bukkit.broadcastMessage(plugin.msg.remainingVotes(this));
			for (Player p : Bukkit.getOnlinePlayers())
			{
				if(p.hasPermission("votekick.notify"))
				{
					p.sendMessage(plugin.msg.hasSupported(voter, voted));
				}
			}
			
		}
	}

	
}
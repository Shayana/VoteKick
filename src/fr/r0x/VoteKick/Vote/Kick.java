package fr.r0x.VoteKick.Vote;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.r0x.VoteKick.Main.Main;

public class Kick extends Vote{


	public Kick(Main plugin, Player voter, Player voted, String vote)
	{
		super(plugin, voter, voted, vote);
		
	}
	
		

	public void kick()
	{
		setMap(plugin.kicks);
		setList(plugin.votes);
		vote();
		if(map.get(voted) >= plugin.config.votesNeeded(this))
		{
			voted.kickPlayer(plugin.msg.kickedscr());
			Bukkit.broadcastMessage(plugin.msg.kickedbrd(voted));
			plugin.kicksstorage.Kick(this);
			accomplish();
		}

	}
	
}


package fr.r0x.VoteKick.Vote;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.r0x.VoteKick.Main.Main;

public class Ban extends Vote{


	public Ban(Main plugin, Player voter, Player voted, String vote)
	{
		super(plugin, voter, voted, vote);
		
	}
	
		

	public void kick()
	{
		setMap(plugin.bans);
		setList(plugin.votes);
		vote();
		if(map.get(voted) >= plugin.config.votesNeeded(this))
		{
			voted.kickPlayer(plugin.msg.bannedscr());
			Bukkit.broadcastMessage(plugin.msg.bannedscr());
			plugin.bansstorage.Ban(this);
			accomplish();
		}

	}
	
}


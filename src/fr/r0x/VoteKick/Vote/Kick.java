package fr.r0x.VoteKick.Vote;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.r0x.VoteKick.Main.Main;

public class Kick extends Vote{


	public Kick(Main plugin, Player voter, Player voted, String vote)
	{
		super(plugin, voter, voted, vote);
		setMap(plugin.kicks);
		setList(plugin.votes);
	}
	
		

	public void kick()
	{
		if (!canVote()){
			return;
		}
		vote();
		if(can && remaining == 0)
		{
			voted.kickPlayer(plugin.msg.kickedscr());
			Bukkit.broadcastMessage(plugin.msg.kickedbrd(voted));
			plugin.kicksstorage.Kick(this);
			accomplish();
		}

	}
	
}


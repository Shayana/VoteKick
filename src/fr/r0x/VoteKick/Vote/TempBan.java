package fr.r0x.VoteKick.Vote;


import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.r0x.VoteKick.Main.Main;

public class TempBan extends Vote{

	protected int time;
	
	public TempBan(Main plugin, Player voter, Player voted, String vote)
	{
		super(plugin, voter, voted, vote);
		setMap(plugin.tempbans);
		setList(plugin.votes);
	}
	
	public void setTime(int t)
	{
		int time;
		if(t > plugin.config.maxTime())
		{
			time = plugin.config.maxTime();
		}
		else
		{
			time = t;
		}
		int i = Calendar.getInstance().get(Calendar.MINUTE);
		this.time = i + time;
	}
	
	public int getTime()
	{
		return time;
	}
		

	public void tempban()
	{
		
		vote();
		if(map.get(voted) >= plugin.config.votesNeeded(this))
		{
			 voted.kickPlayer(plugin.msg.tempbanscr(this));
			 plugin.tempbansstorage.Tempban(this);
		Bukkit.broadcastMessage(plugin.msg.tempbanbrd(this));
			accomplish();
		}

	}
	
}


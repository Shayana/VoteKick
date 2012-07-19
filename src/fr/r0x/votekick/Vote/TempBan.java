package fr.r0x.votekick.Vote;


import java.util.Calendar;

import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;

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
		if( plugin.config.useMaxTime() && t > plugin.config.maxTime())
		{
			time = plugin.config.maxTime();
		}
		else
		{
			time = t;
		}
		
		this.time = time;
	}
	
	public int getTime()
	{
		return time;
	}
		

	public void tempban()
	{
		if (!canVote())
		{
		return;
		}
		vote();
		if(this.remaining == 0)
		{
			voted.kickPlayer(plugin.msg.tempbanscr(this));
			plugin.tempbansstorage.Tempban(this);
			for(Player p : plugin.getServer().getOnlinePlayers())
			{
				if(!plugin.muted.contains(p))
				{
					p.sendMessage(plugin.msg.tempbanbrd(this));
				}
			}
			
			accomplish();
		}
	}
	
	
}


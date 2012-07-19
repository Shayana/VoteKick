package fr.r0x.votekick.Vote;



import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;

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
		if(remaining == 0)
		{
			voted.kickPlayer(plugin.msg.kickedscr(this));
			for (Player p : plugin.getServer().getOnlinePlayers())
			{
				if(!plugin.muted.contains(p))
				{
					p.sendMessage(plugin.msg.kickedbrd(this));
				}
			}
			plugin.kicksstorage.Kick(this);
			accomplish();
		}

	}
	
}


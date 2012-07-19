package fr.r0x.votekick.Vote;


import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;

public class Ban extends Vote{


	public Ban(Main plugin, Player voter, Player voted, String vote)
	{
		super(plugin, voter, voted, vote);
		setMap(plugin.bans);
		setList(plugin.votes);
	}
	
		

	public void ban()
	{
		if (canVote())
		{
		vote();
			if(remaining == 0)
			{
				voted.kickPlayer(plugin.msg.bannedscr(this));
				for (Player p : plugin.getServer().getOnlinePlayers())
					{
						if(!plugin.muted.contains(p))
						{
							p.sendMessage(plugin.msg.bannedbrd(this));
						}
					}
				plugin.bansstorage.Ban(this);
				accomplish();
			}
		}
	}
	
}


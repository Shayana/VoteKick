package fr.r0x.votekick.Vote;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;

public class UnBan extends Vote {

	public UnBan(Main plugin, Player voter, String voted, String vote)
	{
		super(plugin, voter, voted, vote);
	}
	
	
	public void unban()
	{
		if (canVote())
		{
			vote();
			if(remaining == 0)
			{
			plugin.bansstorage.unBan(this);
			accomplish();
			}
		}
		return;
	}
	
	
	@Override
	public boolean canVote()
	{
		if (this.voter.getName() == this.player)
		{
			voter.sendMessage(plugin.msg.forYourself());
			return false;
		}
		
		if (!voter.hasPermission("votekick."+ getVote().toLowerCase()) && !voter.hasPermission("votekick.vote") && !voter.hasPermission("votekick.admin"))
		{
			voter.sendMessage(plugin.msg.noPerm());
		return false;
		}
		
		if(!plugin.bansstorage.isBanned(player) && !(plugin.tempbansstorage.isBanned(player)))
		{
			voter.sendMessage(plugin.msg.notBanned(player));
			return false;
		}
		
		if(!plugin.unbans.containsKey(player) && !voter.hasPermission("votekick.start"))
		{
			voter.sendMessage(plugin.msg.cantStart());
			return false;
		}
		
		if (plugin.config.votelimit() && plugin.register.getCount(voter, this) >= plugin.config.maxVotes(this))
		{
		voter.sendMessage(plugin.msg.maxVotes());
		return false;
		}
		
		if (plugin.unvotes.containsKey(player) && plugin.unvotes.get(player).contains(voter))
		{
			voter.sendMessage(plugin.msg.allreadyVoted(player));
		return false;
		}
		return true;
	}
	
	@Override
	public void vote()
	{
		if(plugin.unbans.containsKey(player))
		{
			plugin.unbans.put(player, plugin.unbans.get(player) + 1);
		}
		
		else
		{
			plugin.unbans.put(player, 1);
			for(Player p : Bukkit.getOnlinePlayers())
			{
				if(!plugin.muted.contains(p))
				{
					p.sendMessage(plugin.msg.remainingVotes(this));
					if(p.hasPermission("votekick.notify"))	p.sendMessage(plugin.msg.hasUbanned(this));
					
				}
			}
		}
		
		
	}
	
	@Override
	public void accomplish()
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(!plugin.muted.contains(p)){
				p.sendMessage(plugin.msg.isUnbanned(player));
			}
		}
		plugin.unbans.remove(player);
		plugin.unvotes.remove(player);
		if(plugin.tempbansstorage.isBanned(player))
		{
			plugin.tempbansstorage.unBan(this);
		}
		if(plugin.bansstorage.isBanned(player))
		{
			plugin.bansstorage.unBan(this);
		}
		
	}

}

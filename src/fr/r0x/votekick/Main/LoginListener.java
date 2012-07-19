package fr.r0x.votekick.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class LoginListener implements Listener {

	protected Main plugin;
	
	public LoginListener(Main plugin)
	{
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event)
	{
	Player p = event.getPlayer();
	if(plugin.bansstorage.isBanned(p.getName()))
	{
		event.setKickMessage(plugin.msg.bannedscr(null));
		event.setResult(Result.KICK_BANNED);
		return;
	}
	else if(plugin.tempbansstorage.isBanned(p.getName()))
	{
		event.setKickMessage("You are temporary banned from this server");
		event.setResult(Result.KICK_BANNED);
		return;
	}
	else 
	{
		return;
	}
	}
}

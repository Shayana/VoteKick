package fr.r0x.VoteKick.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.r0x.VoteKick.Main.Main;
import fr.r0x.VoteKick.Vote.Vote;

public class Bans {

	protected Main plugin;
	private FileConfiguration bans = null;
	private File bansFile = null;
	
	
	public Bans(Main plugin)
	{
		this.plugin = plugin;
	}
	
	
	public void reloadBansFile()
	{
	    if (bansFile == null)
	    {
	    	bansFile = new File(plugin.getDataFolder(), "bans.yml");
	    }
	    bans = YamlConfiguration.loadConfiguration(bansFile);
	    
	    if (!bansFile.exists())
	    {
	    	InputStream defbansFile = plugin.getResource("bans.yml");
	    	if (defbansFile != null)
	    	{
	    		YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defbansFile);
	    		bans.setDefaults(defConfig);
	    	}
	    }
	    try {
			bans.save(bansFile);
		} catch (IOException e) {
			System.out.println("Error during bans saving, disabling VoteKick");
			e.printStackTrace();
			plugin.getPluginLoader().disablePlugin(plugin);
		}
	   
	}
	
	
	public FileConfiguration getBans()
	{
		if(bans == null)
		{
			reloadBansFile();
		}
		return bans;
	}
	
	
	public void Ban(Vote vote)
	{
		String path = vote.getVoted().getName();
		getBans().createSection(path);
		getBans().set(path + ".Date", plugin.register.date());
		getBans().set(path + ".Reason", vote.getReason());
		getBans().set(path + ".Voters", vote.getList());
		try 
		{
			bans.save(bansFile);
		}
		catch(IOException e)
		{
			System.out.println("Error during bans saving, disabling VoteKick");
			e.printStackTrace();
			plugin.getPluginLoader().disablePlugin(plugin);
		}
	}
	
	
	public boolean isBanned(Player p)
	{
		if (getBans().contains(p.getName()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	
	public String getReason(Player p)
	{
		String path = p.getName();
		return getBans().getString(path+".Reason");
	}
	
	
}

package fr.r0x.VoteKick.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.r0x.VoteKick.Main.Main;
import fr.r0x.VoteKick.Vote.Vote;

public class Kicks {

	protected Main plugin;
	private FileConfiguration kicks = null;
	private File kicksFile = null;
	
	
	public Kicks(Main plugin)
	{
		this.plugin = plugin;
	}
	
	
	public void reloadKicksFile()
	{
	    if (kicksFile == null)
	    {
	    	kicksFile = new File(plugin.getDataFolder(), "kicks.yml");
	    }
	    kicks = YamlConfiguration.loadConfiguration(kicksFile);
	    
	    if (!kicksFile.exists())
	    {
	    	InputStream defkicksFile = plugin.getResource("kicks.yml");
	    	if (defkicksFile != null)
	    	{
	    		YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defkicksFile);
	    		kicks.setDefaults(defConfig);
	    	}
	    }
	    try {
			kicks.save(kicksFile);
		} catch (IOException e) {
			System.out.println("Error during Kicks saving, disabling VoteKick");
			e.printStackTrace();
			plugin.getPluginLoader().disablePlugin(plugin);
		}
	   
	}
	
	
	public FileConfiguration getBans()
	{
		if(kicks == null)
		{
			reloadKicksFile();
		}
		return kicks;
	}
	
	
	public void Kick(Vote vote)
	{
		String path = vote.getVoted().getName();
		getBans().createSection(path);
		getBans().set(path + ".Date", plugin.register.date());
		getBans().set(path + ".Reason", vote.getReason());
		getBans().set(path + ".Voters", vote.getList());
		try {
			kicks.save(kicksFile);
		} catch (IOException e) {
			System.out.println("Error during Kicks saving, disabling VoteKick");
			e.printStackTrace();
			plugin.getPluginLoader().disablePlugin(plugin);
		}
	}
	
	
	
}

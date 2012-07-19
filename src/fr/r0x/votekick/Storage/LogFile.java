package fr.r0x.votekick.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;
import fr.r0x.votekick.Vote.Vote;


public class LogFile 
{

	protected Main plugin;
	public LogFile(Main plugin)
	{
	this.plugin = plugin;
	}

private FileConfiguration log = null;
private File logFile = null;


	public void reloadLogFile() 
	{
		if (logFile == null)
		{
    	logFile = new File(plugin.getDataFolder(), "register.yml");
		}
		
		log = YamlConfiguration.loadConfiguration(logFile);
    
		if (!logFile.exists())
		{
			InputStream deflogfile = plugin.getResource("register.yml");
			if (deflogfile != null)
			{
    			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(deflogfile);
    			log.setDefaults(defConfig);
    		}
		}
		try {
			log.save(logFile);
		} catch (IOException e) {
			System.out.println("Error during Loggin saving, disabling VoteKick");
			e.printStackTrace();
			plugin.getPluginLoader().disablePlugin(plugin);
		}
    
    
	}




//Lists Getters	
	public FileConfiguration getLog() {
		if (log == null)
		{
			reloadLogFile();
		}
		return log;
	}
	
	
	
//System Date	
	public String date()
	{
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		return day+":"+month+":"+year;
	}
	
	
//Log the date everyday
	public void LogDate()
	{
		
		if (getLog().getConfigurationSection(date()) == null)
		{
			getLog().createSection(date());
			getLog().addDefault(date(), "Bans");
			getLog().addDefault(date(), "TempBans");
			getLog().addDefault(date(), "Kicks");
		}
	}
	
	
	
//Add one vote to a player
	public void addCount(Player p, Vote vote)
	{
		String player = p.getName();
		String path = date() + "." + vote.getVote() + "." + player;
		
		if(getLog().get(path) != null)
		{
			getLog().set(path, getCount(p, vote) +1 );
		}
		else
		{
			getLog().set(path, 1);
		}
		try {
			log.save(logFile);
		} catch (IOException e) {
			System.out.println("Error during Loggin saving, disabling VoteKick");
			e.printStackTrace();
			plugin.getPluginLoader().disablePlugin(plugin);
		}
	
	}
	
//Get the vote count of a player	
	public int getCount(Player p, Vote vote)
	{
		String player = p.getName();
		String path = date() + "." + vote.getVote() + "." + player;
		int votes = 0;
		if(getLog().get(path) != null)
		{
			votes = getLog().getInt(path);
		}
		else{
			votes = 0;
		}
		return votes;
	}
}




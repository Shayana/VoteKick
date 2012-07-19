package fr.r0x.votekick.Main;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.r0x.votekick.Storage.*;
import fr.r0x.votekick.Utils.*;
import fr.r0x.votekick.commandsexecutors.*;

public class Main extends JavaPlugin implements CommandExecutor
{
	//Utils
	public Configuration config;
	public Messages msg;
	public VKLogger log;
	public LoginListener listen;
	
	//Storage
	public LogFile register;
	public Bans bansstorage;
	public Kicks kicksstorage;
	public TempBans tempbansstorage;
	
	//CommandExecutors
	public VoteKick vk;
	public VoteBan vb;
	public VoteTempBan vt;
	public VoteUnban vu;
	public VoteStay vs;
	public VoteCancel vc;
	public VoteForce vf;
	
	//Maps
	public ArrayList<Player> muted;
	public HashMap<Player, Integer> kicks;
	public HashMap<Player, Integer> bans;
	public HashMap<Player, Integer> tempbans;
	public HashMap<String, Integer> unbans;
	public HashMap<Player, ArrayList<Player>> votes;
	public HashMap<String, ArrayList<Player>> unvotes;
	public HashMap<Player, String> reasons;
	
	
	
@Override
	public void onEnable()
	{
		
		//Construct utils
		this.config = new Configuration(this);
		this.log = new VKLogger(this);
		this.msg = new Messages(this);
		this.listen = new LoginListener(this);

		//Construct Storage
		this.register = new LogFile(this);
		this.kicksstorage = new Kicks(this);
		this.bansstorage = new Bans(this);
		this.tempbansstorage = new TempBans(this);
		
		//Construct executors
		this.vk = new VoteKick(this);
		this.vb = new VoteBan(this);
		this.vt = new VoteTempBan(this);
		this.vs = new VoteStay(this);
		this.vf = new VoteForce(this);
		this.vu = new VoteUnban(this);
		this.vc = new VoteCancel(this);
				
		//Construct Maps
		this.muted = new ArrayList<Player>();
		this.kicks = new HashMap<Player, Integer>();
		this.bans = new HashMap<Player, Integer>();
		this.tempbans = new HashMap<Player, Integer>();
		this.unbans = new HashMap<String, Integer>();
		this.votes = new HashMap<Player, ArrayList<Player>>();
		this.unvotes = new HashMap<String, ArrayList<Player>>();
		this.reasons = new HashMap<Player, String>();
		
		//Get default storage files
		this.config.setupConfig();
		this.msg.reloadLanguage();
		this.kicksstorage.reloadKicksFile();
		this.bansstorage.reloadBansFile();
		this.tempbansstorage.reloadtempbansFile();
				
		//Register commands
		this.getCommand("votecancel").setExecutor(vc);
		this.getCommand("voteforce").setExecutor(vf);
		this.getCommand("votekick").setExecutor(vk);
		this.getCommand("voteban").setExecutor(vb);
		this.getCommand("voteunban").setExecutor(vu);
		this.getCommand("votetempban").setExecutor(vt);
		this.getCommand("votestay").setExecutor(vs);
		this.getCommand("votemute").setExecutor(this);
		
		
		//Finally started
		log.enabled();
	}

@Override
	public void onDisable()
	{
		log.disabled();
	}	



@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] ags)
{
	if(!(sender instanceof Player))
	{
		sender.sendMessage("Connect to the game to perform this command");
		return true;
	}
	Player p = (Player) sender;
	if(muted.contains(p))
	{
		muted.remove(p);
		p.sendMessage(msg.unMuted());
		return true;
	}
	muted.add(p);
	p.sendMessage(msg.muted());
	return true;
}

}

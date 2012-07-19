package fr.r0x.votekick.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.r0x.votekick.Main.Main;
import fr.r0x.votekick.Vote.TempBan;
import fr.r0x.votekick.Vote.UnBan;
import fr.r0x.votekick.Vote.Vote;

public class Messages {

	protected Main plugin;
	public Messages(Main plugin){
		this.plugin = plugin;
	}
	
	private FileConfiguration language;
	private File languageFile;
	private InputStream defLangFile;
	private YamlConfiguration defConfig ;
	private String message;
		
		
	public String name()
	{
		return ChatColor.BLUE +"["+ plugin.getDescription().getName()+"] ";
	}
	
	public void reloadLanguage()
	{
	    if (languageFile == null)
	    {
	    	languageFile = new File(plugin.getDataFolder(), "language.yml");
	    }
	    language = YamlConfiguration.loadConfiguration(languageFile);
	    
	    if (!languageFile.exists())
	    {
	    	defLangFile = plugin.getResource("language.yml");
	    	if (defLangFile != null)
	    	{
	    	 defConfig = YamlConfiguration.loadConfiguration(defLangFile);
	    	language.setDefaults(defConfig);
	    	language.options().copyDefaults(true) ;   
	    	}
	    }
	    try
	    {
			language.save(languageFile);
		}
	    catch (IOException e)
	    {
			System.out.println("Error while saving language file, disabling plugin");
			e.printStackTrace();
			plugin.getPluginLoader().disablePlugin(plugin);
		} 
	}
	
	public FileConfiguration getLang()
	{
		if(language == null){
			reloadLanguage();
		}
		return language;
	}
	

	
	//Infos....................
	
	public String VoteStartBan(Vote vote)
	{
		message = name() + ChatColor.GRAY + getLang().getString("VoteStart.Ban")
				.replaceAll("%player", vote.getVoted().getDisplayName());
		if(vote.getReason() != null)
		{
			message = message + " Reason: " + vote.getReason();
		}
		return message;
	}
	
	public String VoteStartKick(Vote vote)
	{
		message = name() + ChatColor.GRAY + getLang().getString("VoteStart.Kick")
				.replaceAll("%player", vote.getVoted().getDisplayName());
		if(vote.getReason() != null)
		{
			message = message + " Reason: " + vote.getReason();
		}
		return message;
	}
	
	public String VoteStartTempBan(Vote vote)
	{
		message =  name() + getLang().getString("VoteStart.TempBan")
				.replaceAll("%player", vote.getVoted().getDisplayName())
				.replaceAll("%time", String.valueOf(vote.getTime()));
		if(vote.getReason() != null)
		{
			message = message + " Reason: " + vote.getReason();
		}
		return message;
	}
	
	public String VoteCanceled(String p)
	{
		return name() + ChatColor.GRAY + getLang().getString("Infos.VoteCancelled")
				.replaceAll("%player", p);
	}
	
	public String remainingVotes(Vote v){
		message = name() + ChatColor.GRAY + getLang().getString("Infos.RemainingVotes");
			if(!v.getVote().equalsIgnoreCase("UnBan"))
				{
				message = message.replaceAll("%player", v.getVoted().getDisplayName());
				}
			else
			{
			message = message.replaceAll("%player", v.getPlayer());
			}
			return message +" " + v.getRemaining();
	}
	
	
	public String isUnbanned(String p)
	{
		return name() + ChatColor.GRAY + getLang().getString("Infos.UnBanned")
				.replaceAll("%player",  p);
	}

	
	public String isBanned(Player p)
	{
		return  name() + ChatColor.GRAY + getLang().getString("Infos.Banned")
				.replaceAll("%player", p.getDisplayName());
	}
	
	public String Timeout(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Infos.TimeOut")
				.replaceAll("%player", p.getDisplayName());
		
	}
	
	
	public String muted()
	{
		return name() + ChatColor.GRAY + getLang().getString("Infos.Muted");
	}
	
	public String unMuted()
	{
		return name() + ChatColor.GRAY + getLang().getString("Infos.UnMuted");
	}
	
	
	
	//Errors..................
	
	public String noPerm(){
		return name() + ChatColor.GRAY + getLang().getString("Errors.NotPermission");
	}
	
	public String allreadyVoted(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Errors.AllreadyVoted")
				.replaceAll("%player", p.getDisplayName());
	}
	
	public String allreadyVoted(String p)
	{
		return name() + ChatColor.GRAY + getLang().getString("Errors.AllreadyVoted")
		.replaceAll("%player", p);
		
	}
	
	public String noPlayer(String p) {
		return name() + ChatColor.GRAY + getLang().getString("Errors.PlayerNotFound")
				.replaceAll("%player", p);
	}
	
	public String noVote(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Errors.VoteNotFound")
				.replaceAll("%player", p.getDisplayName());
	}
	

	public String Protected(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Errors.TargetProtected")
				.replaceAll("%player", p.getDisplayName());
	}
	
	public String maxVotes(){
		return name() + ChatColor.GRAY + getLang().getString("Errors.TooManyVotes");
	}
	
	public String forYourself(){
		return name() + ChatColor.GRAY + getLang().getString("Errors.VoteforYourself");
	}
	
	public String cantStart()
	{
		return name() + ChatColor.GRAY + getLang().getString("Errors.CantStart");
	}
	
	
	
	
	
	
	//Notify............
	
	
	public String hasVoted(Player v, Player k){
		return name() + ChatColor.GRAY + getLang().getString("Notify.Voted")
				.replaceAll("%voter", v.getDisplayName())
				.replaceAll("%player", k.getDisplayName());
	}
	
	public String hasSupported(Player v, Player k){
		return name() + ChatColor.GRAY + getLang().getString("Notify.Supported")
				.replaceAll("%player", k.getDisplayName())
				.replaceAll("%voter", v.getDisplayName());
	}
	
	public String hasUbanned(UnBan un) {
		return name() + ChatColor.GRAY + getLang().getString("Notify.UnBanned")
				.replaceAll("%voter", un.getVoter().getDisplayName())
				.replaceAll("%player", un.getPlayer());
	}
	
	public String hasCancelled(Player v, Player k){
		return name() + ChatColor.GRAY + getLang().getString("Notify.Cancelled")
				.replaceAll("%voter", v.getDisplayName())
				.replaceAll("%player", k.getDisplayName());
	}
	
	public String hasForced(Player v, Player k){
		return name() + ChatColor.GRAY + getLang().getString("Notify.Forced")
				.replaceAll("%voter", v.getDisplayName())
				.replaceAll("%player", k.getDisplayName());
	}
	
	
	//Screens................
	
	public String bannedscr(Vote vote){
		message =  getLang().getString("KickScreen.Banned");
		if(vote.getReason() != null)
		{
			message = message + " Reason:" +  vote.getReason();
		}
		return message;
	}
	
	public String kickedscr(Vote vote){
		message = getLang().getString("KickScreen.Kicked");
		if(vote.getReason() != null)
		{
			message = message + " Reason:" +  vote.getReason();
		}
		return message;
	}
	
	public String tempbanscr(Vote vote) {
		message = name() + ChatColor.GRAY + getLang().getString("KickScreen.TempBanned")
				.replaceAll("%player", vote.getVoted().getDisplayName())
				.replaceAll("%time", String.valueOf(vote.getTime()));
		if(vote.getReason() != null)
		{
			message = message + " Reason:" +  vote.getReason();
		}
		return message;
	}
	
	public String bannedbrd(Vote vote){
		message = name() + ChatColor.GRAY + getLang().getString("Infos.Banned")
				.replaceAll("%player", vote.getVoted().getDisplayName());
		if(vote.getReason() != null)
		{
			message = message + " Reason:" +  vote.getReason();
		}
		return message;
	}

	
	public String kickedbrd(Vote vote){
		message = name() + ChatColor.GRAY + getLang().getString("Infos.Kicked")
				.replaceAll("%player", vote.getVoted().getDisplayName());
		if(vote.getReason() != null)
		{
			message = message + " Reason:" +  vote.getReason();
		}
		return message;
	}
	
	public String tempbanbrd(TempBan tempBan) {
		message = name() + ChatColor.GRAY + getLang().getString("Infos.TempBanned")
				.replaceAll("%player", tempBan.getVoted().getDisplayName())
				.replaceAll("%time", String.valueOf((tempBan.getTime())));
		if(tempBan.getReason() != null)
		{
			message = message + " Reason : " + tempBan.getReason();
		}
		return message;
			
	}

	public String notBanned(String player)
	{
		return name() + (ChatColor.GRAY + getLang().getString("Errors.NotBanned").replaceAll("%player", player));
	}

	
	
	

	
	
}

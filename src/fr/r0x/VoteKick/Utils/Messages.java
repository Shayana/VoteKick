package fr.r0x.VoteKick.Utils;

import java.io.File;
import java.io.InputStream;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.r0x.VoteKick.Main.Main;
import fr.r0x.VoteKick.Vote.TempBan;
import fr.r0x.VoteKick.Vote.Vote;

public class Messages {

	protected Main plugin;
	public Messages(Main plugin){
		this.plugin = plugin;
	}
	
	protected FileConfiguration language;
	protected File languageFile;
	
	
	public String name() {
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
	    	InputStream defbansFile = plugin.getResource("language.yml");
	    	if (defbansFile != null)
	    	{
	    		YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defbansFile);
	    		language.setDefaults(defConfig);
	    	}
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
	
	public String VoteStartBan(Player p){
		return name() + ChatColor.GRAY + getLang().getString("VoteStart.Ban")
				.replaceAll("%player%", p.getDisplayName());
	}
	
	public String VoteStartKick(Player p){
		return name() + ChatColor.GRAY + getLang().getString("VoteStart.Kick")
				.replaceAll("%player", p.getDisplayName());
	}
	
	public String VoteStartTempBan(Player p, Integer i){
		return name() + getLang().getString("VoteStart.TempBan")
				.replaceAll("%player%", p.getDisplayName()).replaceAll("%time%", String.valueOf(i));
	}
	
	public String VoteCanceled(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Infos.VoteCancelled")
				.replaceAll("%player%", p.getDisplayName());
	}
	
	public String remainingVotes(Vote v){
		return name() + ChatColor.GRAY + getLang().getString("Infos.RemainingVotes")
				.replaceAll("%player%", v.getVoted().getDisplayName());

	}
	

	
	public String isBanned(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Infos.Banned")
				.replaceAll("%player%", p.getDisplayName());
	}
	
	public String Timeout(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Infos.Timeout")
				.replaceAll("%player", p.getDisplayName());
	}
	
	
	
	//Errors..................
	
	public String noPerm(){
		return name() + ChatColor.GRAY + getLang().getString("Errors.NotPermission");
	}
	
	public String allreadyVoted(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Errors.AllreadyVoted")
				.replaceAll("%player%", p.getDisplayName());
	}
	
	public String noPlayer(String p) {
		return name() + ChatColor.GRAY + getLang().getString("Errors.PlayerNotFound")
				.replaceAll("%player%", p);
	}
	
	public String noVote(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Errors.VoteNotFound")
				.replaceAll("%player%", p.getDisplayName());
	}
	

	public String Protected(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Errors.TargetProtected")
				.replaceAll("%player%", p.getDisplayName());
	}
	
	public String maxVotes(){
		return name() + ChatColor.GRAY + getLang().getString("Errors.TooManyVotes");
	}
	
	
	
	
	
	
	//Notify............
	
	
	public String hasVoted(Player v, Player k){
		return name() + ChatColor.GRAY + getLang().getString("Notify.Voted")
				.replaceAll("%voter%", v.getDisplayName()).replaceAll("%player%", k.getDisplayName());
	}
	
	public String hasSupported(Player v, Player k){
		return name() + ChatColor.GRAY + getLang().getString("Notify.Supported")
				.replaceAll("%player%", k.getDisplayName()).replaceAll("%voter%", v.getDisplayName());
	}
	
	public String hasCancelled(Player v, Player k){
		return name() + ChatColor.GRAY + getLang().getString("Notify.Cancelled")
				.replaceAll("%voter%", v.getDisplayName()).replaceAll("%player%", k.getDisplayName());
	}
	
	public String hasForced(Player v, Player k){
		return name() + ChatColor.GRAY + getLang().getString("Notify.Forced")
				.replaceAll("%voter%", v.getDisplayName()).replaceAll("%player%", k.getDisplayName());
	}
	
	
	//Screens................
	
	public String bannedscr(){
		return getLang().getString("KickScreen.Banned");
	}
	
	public String kickedscr(){
		return getLang().getString("KickScreen.Kicked");
	}
	
	public String bannedbrd(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Infos.Banned")
				.replaceAll("%player%", p.getDisplayName());
	}

	public String tempbanscr(TempBan tempBan) {
		return name() + ChatColor.GRAY + getLang().getString("KickScreen.TempBan")
				.replaceAll("%player", tempBan.getVoted().getDisplayName()).replaceAll("%time", String.valueOf(tempBan.getTime()));
	}
	
	public String kickedbrd(Player p){
		return name() + ChatColor.GRAY + getLang().getString("Infos.Kicked")
				.replaceAll("%player%", p.getDisplayName());
	}
	
	public String tempbanbrd(TempBan tempBan) {
		return name() + ChatColor.GRAY + getLang().getString("Infos.TempBanned")
				.replaceAll("%player", tempBan.getVoted().getDisplayName())
				.replaceAll("%time", String.valueOf((tempBan.getTime())));
	}
	
	
	

	
	
}

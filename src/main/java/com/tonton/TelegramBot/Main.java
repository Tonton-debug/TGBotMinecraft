package com.tonton.TelegramBot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
public class Main extends JavaPlugin implements Listener {
	TelegramBotsApi botsApi;
	BotSession botSession;
	Bot bot=new Bot();
	List<Player> _joinPlayers=new ArrayList<Player>();
	List<Player> _leavePlayers=new ArrayList<Player>();
	 @Override
	    public void onEnable(){
		 getServer().getPluginManager().registerEvents(this, this);
		DataBase.Init(getDataFolder());
	        try {
	        	botsApi = new TelegramBotsApi(DefaultBotSession.class);
	        	botSession= botsApi.registerBot(bot);
	        } catch (TelegramApiException e) {
	        	System.out.println("ERRROR:"+e.getMessage());
	           
	        }
	    }
	    @Override
	    public void onDisable(){
	    	DataBase.Get().CloseConnection();
	    		botSession.stop();
	    		
	    }
	    @EventHandler
	    public void OnMove(PlayerMoveEvent e)
	    {
	    	 if (e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockY() == e.getFrom().getBlockY() && e.getTo().getBlockZ() == e.getFrom().getBlockZ()) return; //The player hasn't moved
	    	 if(!_joinPlayers.contains(e.getPlayer())) return;
	    	 _joinPlayers.remove(e.getPlayer());
	    	 _leavePlayers.add(e.getPlayer());
	    	 bot.SendNotice("НА СЕРВЕР ЗАШЁЛ ИГРОК:"+e.getPlayer().getName()+"\nТекущий онлайн:"+Bukkit.getOnlinePlayers().size());
	    }
	    @EventHandler
	    public void OnLeave(PlayerQuitEvent e)
	    {
	    	if(_joinPlayers.contains(e.getPlayer()))
	    		_joinPlayers.remove(e.getPlayer());
	    	if(!_leavePlayers.contains(e.getPlayer())) return;
	    	_leavePlayers.remove(e.getPlayer());
	    	bot.SendNotice(e.getPlayer().getName()+" ПОКИНУЛ ЭТОТ БРЕННЫЙ МИР\nТекущий онлайн:"+(Bukkit.getOnlinePlayers().size()-1));
	    }
	    @EventHandler
	    public void OnJoin(PlayerJoinEvent e)
	    {
	    	_joinPlayers.add(e.getPlayer());
	    	
	    }

}

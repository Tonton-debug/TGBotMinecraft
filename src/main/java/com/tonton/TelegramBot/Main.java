package com.tonton.TelegramBot;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
	    public void OnJoin(PlayerJoinEvent e)
	    {
	    	bot.SendNotice();
	    }

}

package com.tonton.TelegramBot;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;



public class Main extends JavaPlugin {
	TelegramBotsApi botsApi;
	BotSession botSession;
	Bot bot=new Bot();
	 @Override
	    public void onEnable(){
	        try {
	        	botsApi = new TelegramBotsApi(DefaultBotSession.class);
	        	botSession= botsApi.registerBot(bot);
	        } catch (TelegramApiException e) {
	        	System.out.println("ERRROR:"+e.getMessage());
	           
	        }
	    }
	    @Override
	    public void onDisable(){
	    		botSession.stop();
	    }

}

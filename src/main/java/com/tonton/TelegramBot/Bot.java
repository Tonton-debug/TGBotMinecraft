package com.tonton.TelegramBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
	public void SendNotice() {
		List<Long> ids=DataBase.Get().GetAllId();
		String resultText="НА СЕРВЕР ЗАШЁЛ ИГРОК:"+((Player) Bukkit.getOnlinePlayers().toArray()[Bukkit.getOnlinePlayers().size()-1]).getName()+"\nТекущий онлайн:"+Bukkit.getOnlinePlayers().size();
		for(long id:ids) {
		  	 SendMessage sendMessage = new SendMessage();
		     sendMessage.enableHtml(true);
		     sendMessage.setChatId(id);
		     sendMessage.setText(resultText);
		     try {
		      	execute(sendMessage);
		      	
		      } catch (TelegramApiException e) {
		          System.out.println("ERRORRRRR Exception: "+ e.toString());
		      }
		}
	}
    @Override
    public void onUpdateReceived(Update update) {
	String s = update.getMessage().getText();
	SendMessage(update.getMessage().getChatId(),update.getMessage().getText());
    }
    @Override
    public String getBotUsername() {
        return "BesBot";
    }
    Random random=new Random();
    public synchronized  void SendMessage(long id,String message) {
    	if(message==null)
    	{
    		message="";
    	}
   	 SendMessage sendMessage = new SendMessage();
     sendMessage.enableHtml(true);
     sendMessage.setChatId(id);
    System.out.println("D:"+message+" "+message.split("/").length);
    String exitMessage="ААА??? ЧТО ГОВОРИШЬ? ДЕРЖИ ГАЙД НА БОТА ДРУЖОК ПИРАЖОК:\n /online - показаь текущий онлайн сервера\n/noticeon - включить уведомления\n /noticeoff - выключить уведомления\n Ну и всё. Хули ты ещё хотел то?";
    
     if(message.startsWith("/")) {
     	String command=message.split("/")[1];
     	switch(command) {
     	case "online":
     		String onlineMessage="Текущий онлайн:"+Bukkit.getOnlinePlayers().size();
     		if(Bukkit.getOnlinePlayers().size() !=0)
     			onlineMessage+="\nИгроки на сервере:";
     		for(Object plObj:Bukkit.getOnlinePlayers().toArray()) {
     			Player pl=(Player)plObj;
     			String ip=random.nextInt(10,100)+"."+random.nextInt(10,100)+"."+random.nextInt(10,100)+"."+random.nextInt(10,100);
     			onlineMessage+="\n"+pl.getName()+" АЙПИ:"+ip;
     		}
     		exitMessage=onlineMessage;
     		
     		break;
     	case "noticeon":
     		
     		exitMessage=DataBase.Get().CreateNotice(id, true)?"Успешно":"Не успешно";
     		break;
     	case "noticeoff":
     		
     		exitMessage=DataBase.Get().RemoveNotice(id)?"Успешно":"Не успешно";
     		break;
     	}
     }
     sendMessage.setText(exitMessage);
     try {
     	execute(sendMessage);
     	
     } catch (TelegramApiException e) {
         System.out.println("ERRORRRRR Exception: "+ e.toString());
     }
    }
    @Override
    public String getBotToken() {
        return "E";
    }
}

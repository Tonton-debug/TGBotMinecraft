package com.tonton.TelegramBot;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


public class DataBase {
private static DataBase _base;
public static void Init(File file) {
	_base=new DataBase(file);
}
public static DataBase Get() {
	return _base;
}
private Connection _con;
private DataBase(File file) {
	if(file.mkdirs()) {
		file.mkdirs();
    }
	 File dbFile = new File(file + File.separator + "zombie.db");
	 IntDB(dbFile);
}
public void CloseConnection() {
	try {
		_con.close();
		System.out.println("CLOSING CONNECTION");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		 System.out.println(e);
		e.printStackTrace();
	}
}
private void IntDB(File file) {
	 try{
		 if(!file.exists()) 
			 file.createNewFile();
		   Class.forName("org.sqlite.JDBC");
		
		   _con = DriverManager.getConnection("jdbc:sqlite:"+file);
		  try(Statement state= _con.createStatement()){
			  state.execute("CREATE TABLE if not exists 'Listeners' ('Id' BIGINT NOT NULL, 'Notice1' BOOLEAN,PRIMARY KEY('Id'));");
		  };
		   System.out.println("База Подключена!");
    }
    catch(Exception ex){
        System.out.println("Connection failed...");
         
        System.out.println(ex);
    }
}
public List<Long> GetAllId(){
	List<Long> ids=new ArrayList<Long>();
	try(PreparedStatement prepare=_con.prepareStatement("SELECT * FROM Listeners")){
	ResultSet resultSet = prepare.executeQuery();
	while(resultSet.next()){
		long id = resultSet.getLong(1);       
		ids.add(id);
	}
	return ids;
	}catch (SQLException e) {
		 System.out.println("Error has:"+e.getMessage());
		// TODO Auto-generated catch block
		e.printStackTrace();
		return new ArrayList<Long>();
	}
}
public boolean HasId(long id) {
	try(PreparedStatement prepare=_con.prepareStatement("SELECT * FROM Listeners WHERE Id = '"+id+"'")){
		ResultSet resSet = prepare.executeQuery();
		return resSet.next();
	}catch (SQLException e) {
		 System.out.println("Error has:"+e.getMessage());
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
}
public boolean RemoveNotice(long id) {
	if(!HasId(id))
		return false;
	try(PreparedStatement prepare=_con.prepareStatement("DELETE FROM Listeners WHERE Id='"+id+"'")){
		prepare.executeUpdate();
		return true;
	}catch (SQLException e) {
		 System.out.println("Error remove:"+e.getMessage());
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	return false;
}
public boolean ChangeNotice(long id,Boolean state) {
	try {
	if(!HasId(id))
		return false;
	try(PreparedStatement prepare=_con.prepareStatement("UPDATE Listeners SET Notice1 =" +state+" WHERE Id = '"+id+"'")){
		prepare.executeUpdate();
		return true;
	}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		 System.out.println("Error change:"+e.getMessage());
		e.printStackTrace();
		return false;
	}
}
public boolean CreateNotice(long id,Boolean state) {
	if(HasId(id))
		return false;
	
	try(PreparedStatement prepare=_con.prepareStatement("INSERT INTO 'Listeners' ('Id', 'Notice1') VALUES ('"+id+"',"+state+"); ")){
		prepare.execute();
	System.out.println("ADDED ID:"+id);
	return true;
	}catch (SQLException e) {
		 System.out.println("Error add:"+e.getMessage());
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;
}
}

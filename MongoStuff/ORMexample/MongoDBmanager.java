package ORMexample;

import java.awt.List;
import java.util.Collection;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import ORMexample.daoimpl.ProfileDaoImpl;
import ORMexample.daoimpl.GameDaoImpl;
import ORMexample.daoimpl.PlayDaoImpl;
import ORMexample.model.DBgame;
import ORMexample.model.DBprofile;
import ORMexample.model.DBplay;

public class MongoDBmanager {
	private GameDaoImpl gmDao = null;
	private ProfileDaoImpl proDao = null;
	private PlayDaoImpl playDao = null;
	private MongoClient mongoClient = null;
	private MongoDatabase database = null;
	private static MongoDBmanager mongoDBmgr = null;
	MongoCollection<Document> coll = null;

	public MongoDBmanager() {
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
			database = mongoClient.getDatabase("testdb");
			if(database!=null) {
				System.out.println("Connect to testdb Database Successful");
				gmDao = new GameDaoImpl(database, this);
				proDao = new ProfileDaoImpl(database, this);
				playDao = new PlayDaoImpl(database, this);
			}
			else System.out.println("Database NOT found!");
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			mongoClient.close();
		}
		catch (MongoException e) {
			e.printStackTrace();
		}
	}
	// A singleton design pattern 
	public static MongoDBmanager getInstance(){
		if(mongoDBmgr==null){
			mongoDBmgr = new MongoDBmanager();
		}
		return mongoDBmgr;
	}

	public DBprofile findProfile(String pName) {
		return proDao.findProfile(pName);
	}

	public Collection<DBgame> findAllGames(Integer playerId) {
		return gmDao.findGames(playerId);
	}
	
	public Collection<DBplay> findAllPlays(Long gameId) {
		return playDao.findPlays(gameId);
	}

}

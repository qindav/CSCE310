package ORMexample.daoimpl;

import ORMexample.MongoDBmanager;
import ORMexample.dao.GameDao;
import ORMexample.model.DBgame;

import java.util.ArrayList;
import java.util.Collection;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class GameDaoImpl implements GameDao {

    //list is working as a database

	private MongoDatabase database = null;
	private MongoDBmanager mDBmgr = null;
	private MongoCollection<Document> coll = null;
	ArrayList<DBgame> games = null;
	
    public GameDaoImpl(MongoDatabase db, MongoDBmanager mgr) {
    	database = db;
    	mDBmgr = mgr;
    	coll = database.getCollection("games");
        if(coll!=null)
        	System.out.println("Select Profiles Collection Successful"); //should throw an exception
        else System.out.println("games collection NOT found!");
        games = null;	//load games from real database as needed
    }


	@Override
	public Collection<DBgame> findGames(Integer playerId) {
		BasicDBObject query = new BasicDBObject();
		query.put("player_id", playerId);
		FindIterable<Document> docs = coll.find(query);
		if(docs != null) {
			games = new ArrayList<DBgame>();
	        for (Document d : docs) {
				games.add(new DBgame(playerId, d)); //complete the mapping of a document to POJO
			}
	        return games;
		}
		return null;
	}

	
	public void InsertName() {
		BasicDBObject query = new BasicDBObject();
		
	}
	 
}
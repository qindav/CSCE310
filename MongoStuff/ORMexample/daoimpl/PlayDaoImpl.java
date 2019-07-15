package ORMexample.daoimpl;

import ORMexample.MongoDBmanager;
import ORMexample.dao.GameDao;
import ORMexample.dao.PlayDao;
import ORMexample.model.DBplay;

import java.util.ArrayList;
import java.util.Collection;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class PlayDaoImpl implements PlayDao {

    //list is working as a database

	private MongoDatabase database = null;
	private MongoDBmanager mDBmgr = null;
	private MongoCollection<Document> coll = null;
	ArrayList<DBplay> plays = null;
	
    public PlayDaoImpl(MongoDatabase db, MongoDBmanager mgr) {
    	database = db;
    	mDBmgr = mgr;
    	coll = database.getCollection("plays");
        if(coll!=null)
        	System.out.println("Select Playing Collection Successful"); //should throw an exception
        else System.out.println("plays collection NOT found!");
        plays = null;	//load games from real database as needed
    }


	@Override
	public Collection<DBplay> findPlays(Long gameId) {
		BasicDBObject query = new BasicDBObject();
		query.put("game_id", gameId);
		FindIterable<Document> docs = coll.find(query).sort(new BasicDBObject("qtr", 1));
		if(docs != null) {
			plays = new ArrayList<DBplay>();
	        for (Document d : docs) {
	        	plays.add(new DBplay(gameId, d)); //complete the mapping of a document to POJO
			}
	        return plays;
		}
		return null;
	}

}
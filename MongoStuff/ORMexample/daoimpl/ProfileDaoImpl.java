package ORMexample.daoimpl;


import ORMexample.MongoDBmanager;
import ORMexample.dao.ProfileDao;
import ORMexample.model.DBprofile;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class ProfileDaoImpl implements ProfileDao {
    //list is working as a database
	private MongoDatabase database = null;
	private MongoDBmanager mDBmgr = null;
	private MongoCollection<Document> coll = null;

    public ProfileDaoImpl ( MongoDatabase db, MongoDBmanager mgr) {
    	database = db;
    	mDBmgr = mgr;
    	coll = database.getCollection("profiles");
        if(coll!=null)
        	System.out.println("Select Profiles Collection Successful"); //should throw an exception
        else System.out.println("profiles ollection NOT found!");
        //load games from real database
    }

	public DBprofile findProfile(String pName) {
		BasicDBObject query = new BasicDBObject();
		query.put("name", pName );
		FindIterable<Document> docs = coll.find(query);
		if(docs != null) {
			Document doc = docs.first();
	        Integer id = doc.getInteger("player_id");
	        return new DBprofile(id, pName, doc);	//cheating since I did not map the whole doc
		}
		return null;
	}
	
	public DBprofile findProfileByID(String player_id) {
		BasicDBObject query = new BasicDBObject();
		query.put("name", pName );
		FindIterable<Document> docs = coll.find(query);
		if(docs != null) {
			Document doc = docs.first();
	        Integer id = doc.getInteger("player_id");
	        return new DBprofile(id, pName, doc);	//cheating since I did not map the whole doc
		}
		return null;
	}
}
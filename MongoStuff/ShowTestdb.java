import java.util.Arrays;

import org.bson.Document;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.Block;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Filters;

public class ShowTestdb {
	 Block<Document> printBlock = new Block<Document>() {
	        @Override
	        public void apply(final Document document) {
	            System.out.println(document.toJson());
	        }
	    };

	//@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
			MongoCollection<Document> coll = null;
			MongoCollection<Document> collProfile = null;  
 	    	MongoCollection<Document> collGames = null;  
			MongoDatabase database = mongoClient.getDatabase("testdb");
			if(database!=null) {
				
				System.out.println("Connect to Database Successful");
				coll = database.getCollection("dbcollec");
				collProfile = database.getCollection("profiles");
				collGames = database.getCollection("games");
		        if(coll!=null)
		        	System.out.println("Select Collection Successful");
		        else System.out.println("Collection NOT found!");
			}
			else System.out.println("Database NOT found!");
			/////////////////////////////////////////////////
			//Important part here
			/////////////////////////////////////////////////
			AggregateIterable<Document> output = collGames.aggregate(
				Arrays.asList(
					Aggregates.match(Filters.ne("defense_interceptions", 0)),
					Aggregates.match(Filters.eq("year", "1990")),
					Aggregates.match(Filters.eq("team", "SEA")),
					Aggregates.project(
				              Projections.fields(
				                    Projections.excludeId(),
				                    Projections.include("player_id"),
				                    Projections.include("game_number")
				              )
				          )
				)
			);
			for (Document dbObject : output)
			    System.out.println(dbObject);
			
		} catch (Exception e) {
			e.printStackTrace();
 		} finally {
 			if(mongoClient != null)
 				mongoClient.close();
 		}
	}
}
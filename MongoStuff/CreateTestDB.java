import java.util.Arrays;

import org.bson.Document;

//http://mongodb.github.io/mongo-java-driver/3.6/driver/getting-started/quick-start/
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CreateTestDB {

   public static void main( String args[] ){
	  MongoClient mongoClient = null;
      try{   
		mongoClient = new MongoClient( "localhost");// 27017 
		MongoCollection<Document> coll = null;
		MongoDatabase db = mongoClient.getDatabase("testdb");
		System.out.println("Database Connection Successful!");
		MongoCollection<Document> collec = db.getCollection("dbcollec");
		Document doc = new Document("name", "MongoDB")
	                .append("type", "database")
	                .append("count", 1)
	                .append("versions", Arrays.asList("v4.0", "v3.6", "v2.6"))
	                .append("info", new Document("x", 203).append("y", 102));
        collec.insertOne(doc);
        System.out.println("Document is inserted successfully");
	    }catch(Exception e){
	     System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	  }
      finally {
    	  if(mongoClient!=null)
    		  mongoClient.close();
      }
      
   }
}
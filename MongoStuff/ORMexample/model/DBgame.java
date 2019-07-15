package ORMexample.model;

import java.io.Serializable;

import org.bson.Document;

//model class to represent a single document in a collection, or a row in a table
public class DBgame implements Serializable {

    private Integer playerId;//use wrapper data type, since a DB field can be null (primitive types cannot be null
    Document doc;

    public DBgame(Integer id, Document d) {
        this.playerId = id;
        this.doc = d;
    }

    public int getPlayerId() {
        return playerId;
    }

	public String getString(String fieldName) {
		return doc.getString(fieldName);
	}

	public Integer getInteger(String fieldName) {
		return doc.getInteger(fieldName);
	}
}
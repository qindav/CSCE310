package ORMexample.model;

import java.io.Serializable;

import org.bson.Document;

//model class to represent a single document in a collection, or a row in a table
public class DBprofile implements Serializable {

    private Integer playerId;	//use wrapper data type, since a DB field can be null (primitive types cannot be null
    private String playerName;
    private Document doc;

    public DBprofile() {
    }

    public DBprofile(Integer id, String playerName, Document doc) {
        this.playerId = id;
        this.playerName = playerName;
        this.doc = doc;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }
	public String getString(String fieldName) {
		return doc.getString(fieldName);
	}

	public Integer getInteger(String fieldName) {
		return doc.getInteger(fieldName);
	}
}

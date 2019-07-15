package ORMexample.model;

import java.io.Serializable;

import org.bson.Document;

//model class to represent a single document in a collection, or a row in a table
public class DBplay implements Serializable {

    private Long game_id; //ID may overflow
    private Document doc;

    public DBplay() {
    }

    public DBplay(Long _game_id, Document doc) {
        this.game_id = _game_id;
        this.doc = doc;
    }
    
    public Long getGameId() {
        return game_id;
    }
	public String getString(String fieldName) {
		return doc.getString(fieldName);
	}

	public Integer getInteger(String fieldName) {
		return doc.getInteger(fieldName);
	}
}

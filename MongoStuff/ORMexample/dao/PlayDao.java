package ORMexample.dao;

import java.util.Collection;

import ORMexample.model.DBplay;

public interface PlayDao {

	Collection<DBplay> findPlays(Long gameId);
}

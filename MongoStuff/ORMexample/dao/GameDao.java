package ORMexample.dao;

import java.util.Collection;

import ORMexample.model.DBgame;

public interface GameDao {

	Collection<DBgame> findGames(Integer playerId);
}

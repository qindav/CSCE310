package ORMexample.dao;

import ORMexample.model.DBprofile;

public interface ProfileDao {

	DBprofile findProfile(String pName);
}
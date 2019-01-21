package com.ilmale.doodlejump.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface OurDao {

    @Insert
    void setUser(User user);
}

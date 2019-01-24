package com.ilmale.doodlejump.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface OurDao {

    @Insert
    void setUser(User user);

    @Query("select * from User")
    public List<User> getUsers();

    @Delete
    public void deleteUser(User user);

    @Update
    public void updateUser(User user);

    @Query("UPDATE User SET Password=:newPass WHERE Email=:email AND Password=:oldPass")
    public void updatePass(String email, String newPass, String oldPass);


}

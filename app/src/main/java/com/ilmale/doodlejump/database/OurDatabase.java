package com.ilmale.doodlejump.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities={User.class}, version=1)
public abstract class OurDatabase extends RoomDatabase {

    public abstract OurDao ourDao();

}

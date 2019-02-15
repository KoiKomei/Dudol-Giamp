package com.ilmale.doodlejump.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

/*
* FINITO IN DISUSO, SERVIVA PER IL ROOM DATABASE
*
* */

@Database(entities={User.class, Negozio.class, Possiede.class}, version=4, exportSchema = true)
public abstract class OurDatabase extends RoomDatabase {

    public abstract OurDao ourDao();

}

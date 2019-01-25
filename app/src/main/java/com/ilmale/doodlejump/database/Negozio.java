package com.ilmale.doodlejump.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Negozio")
public class Negozio {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="Id_oggetto")
    private int id;

    @ColumnInfo(name="Nome")
    private String name;

    public int getId(){return id;}

    public void setId(int id){this.id=id;}

    public String getName(){return name;}

    public void setName(String name){this.name=name;}
}

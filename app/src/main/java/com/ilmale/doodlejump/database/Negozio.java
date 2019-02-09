package com.ilmale.doodlejump.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Negozio")
public class Negozio {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name="Nome")
    private String name;

    @ColumnInfo(name="Costo")
    private int costo;

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public String getName(){return name;}

    public void setName(String name){this.name=name;}
}

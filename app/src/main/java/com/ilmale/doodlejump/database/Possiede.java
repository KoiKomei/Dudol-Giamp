package com.ilmale.doodlejump.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Possiede", foreignKeys = {@ForeignKey(entity=User.class, parentColumns = "Email", childColumns = "user_email", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Negozio.class, parentColumns = "Id_oggetto", childColumns = "oggetto")}
)
public class Possiede {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="posses_id")
    private int id;

    @ColumnInfo(name="user_email")
    private String email;

    @ColumnInfo(name="oggetto")
    private int oggetto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOggetto() {
        return oggetto;
    }

    public void setOggetto(int oggetto) {
        this.oggetto = oggetto;
    }
}

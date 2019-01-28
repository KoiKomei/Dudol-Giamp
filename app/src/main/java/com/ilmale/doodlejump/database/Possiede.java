package com.ilmale.doodlejump.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Possiede", foreignKeys = @ForeignKey(entity=User.class, parentColumns = "Email", childColumns = "user_email", onDelete = ForeignKey.CASCADE))
public class Possiede {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="user_email")
    private String email;

    @ColumnInfo(name="Bob")
    private boolean bob;

    @ColumnInfo(name="oggetto2")
    private boolean oggetto2;

    public boolean isBob() {
        return bob;
    }

    public void setBob(boolean bob) {
        this.bob=bob;
    }

    public boolean isOggetto2() {
        return oggetto2;
    }

    public void setOggetto2(boolean oggetto2) {
        this.oggetto2 = oggetto2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}

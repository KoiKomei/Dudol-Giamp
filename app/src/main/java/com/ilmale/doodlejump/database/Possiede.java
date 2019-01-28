package com.ilmale.doodlejump.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@Entity(tableName = "Possiede", foreignKeys = @ForeignKey(entity=User.class, parentColumns = "Email", childColumns = "user_email", onDelete = ForeignKey.CASCADE))
public class Possiede {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="user_email")
    private String email;

    @ColumnInfo(name="bob")
    private boolean bob;

    @ColumnInfo(name="blue_bob")
    private boolean bluebob;

    @ColumnInfo(name="jungle_bob")
    private boolean junglebob;

    @ColumnInfo(name="bunny_bob")
    private boolean bunnybob;

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public boolean isBob() {
        return bob;
    }

    public void setBob(boolean bob) {
        this.bob = bob;
    }

    public boolean isBluebob() {
        return bluebob;
    }

    public void setBluebob(boolean bluebob) {
        this.bluebob = bluebob;
    }

    public boolean isJunglebob() {
        return junglebob;
    }

    public void setJunglebob(boolean junglebob) {
        this.junglebob = junglebob;
    }

    public boolean isBunnybob() {
        return bunnybob;
    }

    public void setBunnybob(boolean bunnybob) {
        this.bunnybob = bunnybob;
    }
}

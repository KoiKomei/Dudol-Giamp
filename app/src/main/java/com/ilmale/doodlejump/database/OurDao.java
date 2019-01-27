package com.ilmale.doodlejump.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface OurDao {

    /*                */
    /*Roba dell'utente*/
    /*                */

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

    @Query("UPDATE User SET Money=:newValue WHERE Email=:email AND Money=:oldValue")
    public void updateMoney(String email, int newValue, int oldValue);

    /*                */
    /*Roba del negozio*/
    /*                */

    @Insert
    void setNegozio(Negozio negozio);

    @Query("select * from Negozio")
    public List<Negozio> getNegozio();

    @Query("UPDATE Negozio SET Nome=:name WHERE Id_oggetto=:id")
    public void updateName(String name, int id);

    @Query("UPDATE Negozio SET Image=:url WHERE Id_oggetto=:id")
    public void updateImage(String url, int id);

    @Query("UPDATE Negozio SET Costo=:costo WHERE Id_oggetto=:id")
    public void updateCosto(int costo, int id);


    /*                                                 */
    /*Roba di quella roba che abbiamo chiamato Possiede*/
    /*                                                 */

    @Insert
    void setPossiede(Possiede possiede);

    @Query("select * from Possiede")
    public List<Possiede> getPossiede();

    @Delete
    public void deletePossiede(Possiede possiede);

   /* @Query("SELECT posses_id from Possiede where MAX(posses_id)")
    public int getMaxId();*/

}

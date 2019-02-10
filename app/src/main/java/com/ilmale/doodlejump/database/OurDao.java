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

    @Query("select Email from User")
    public List<String> getEmail();

    @Update
    public void updateUser(User user);

    @Query("DELETE FROM User WHERE Email=:email AND Password=:pass")
    public void deleteUser(String email, String pass);

    @Query("UPDATE User SET Password=:newPass WHERE Email=:email AND Password=:oldPass")
    public void updatePass(String email, String newPass, String oldPass);

    @Query("UPDATE User SET Money=:newValue WHERE Email=:email AND Money=:oldValue")
    public void updateMoney(String email, int newValue, int oldValue);

    @Query("select Latitudine from User WHERE Email=:email")
    public double userLat(String email);

    @Query("select Longitudine from User WHERE Email=:email")
    public double userLong(String email);

    @Query("UPDATE User set Punteggio=:newPunteggio WHERE Email=:email")
    public int updatePunteggio(int newPunteggio, String email);

    @Query("UPDATE User set Latitudine=:newLat WHERE Email=:email")
    public void updateLat(double newLat, String email);

    @Query("UPDATE User set Longitudine=:newLong WHERE Email=:email")
    public void updateLong(double newLong, String email);

    @Query("Select * from User WHERE Email=:email")
    public User getUtente(String email);

    /*                */
    /*Roba del negozio*/
    /*                */

    @Insert
    void setNegozio(Negozio negozio);

    @Query("select * from Negozio")
    public List<Negozio> getNegozio();


    /*
    @Query("select Costo from Negozio where Nome=:name")
    public void getPrezzoBob(String name);
    /*Nomi Bob attuali:
    Bob
    BlueBob
    JungleBob
    BunnyBob
     */


    /*                                                 */
    /*Roba di quella roba che abbiamo chiamato Possiede*/
    /*                                                 */

    @Insert
    void setPossiede(Possiede possiede);

    @Query("select * from Possiede")
    public List<Possiede> getPossiede();

    @Delete
    public void deletePossiede(Possiede possiede);

    @Query("UPDATE Possiede SET blue_bob=:newValue WHERE user_email=:email")
    public void updateBlue(boolean newValue, String email);

    @Query("UPDATE Possiede SET jungle_bob=:newValue WHERE user_email=:email")
    public void updateJungle(boolean newValue, String email);

    @Query("UPDATE Possiede SET bunny_bob=:newValue WHERE user_email=:email")
    public void updateBunny(boolean newValue, String email);

    @Query("select bob from Possiede WHERE user_email=:email")
    public boolean getBob(String email);

    @Query("select blue_bob from Possiede WHERE user_email=:email")
    public boolean getBlue(String email);

    @Query("select jungle_bob from Possiede WHERE user_email=:email")
    public boolean getJungle(String email);

    @Query("select bunny_bob from Possiede WHERE user_email=:email")
    public boolean getBunny(String email);

}

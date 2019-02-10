package com.ilmale.doodlejump.database;

import java.util.List;
import com.ilmale.doodlejump.RegisterActivity;
import com.ilmale.doodlejump.database.Possiede;

public class ItemHandler {


   public ItemHandler(){
      //default perché si
   }

   public void insertItem(int id, String name, String url, int costo){
      Negozio neg=new Negozio();


      neg.setName(name);

      neg.setCosto(costo);

      //RegisterActivity.db.ourDao().setNegozio(neg);

   }

  

   /*public void insertPossiede(String email, int oggetto){
      Possiede pos=new Possiede();
      int id=RegisterActivity.db.ourDao().getMaxId();
      id=id+1;
      pos.setId(id);
      pos.setEmail(email);
      pos.setOggetto(oggetto);

      RegisterActivity.db.ourDao().setPossiede(pos);

   }*/

}

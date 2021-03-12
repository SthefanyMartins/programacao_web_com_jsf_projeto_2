package br.edu.ifnmg.carros.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class FabricaConexao {
    
   private static EntityManagerFactory entityManagerFactory;
   
   public EntityManager getConnection(){
       entityManagerFactory = Persistence.createEntityManagerFactory("MyPU");
       return entityManagerFactory.createEntityManager();
   }

}

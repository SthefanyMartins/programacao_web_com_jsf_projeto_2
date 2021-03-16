package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CarroDAO implements CrudDAO<Carro>{
    
    @Override
    public void salvar(Carro carro) throws ErroSistema{
        EntityManager entityManager = new FabricaConexao().getConnection();
        try{    
            entityManager.getTransaction().begin();
            if(carro.getId() == null){
                entityManager.persist(carro);
            }else{
                entityManager.merge(carro);
            }
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            throw new ErroSistema("Erro ao salvar o carro!", e);
        }finally{
            entityManager.close();
        }
    }
    
    @Override
    public void deletar(Carro carro) throws ErroSistema{
        EntityManager entityManager = new FabricaConexao().getConnection();
        try {
            carro = entityManager.find(Carro.class, carro.getId());
            entityManager.getTransaction().begin();
            entityManager.remove(carro);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new ErroSistema("Erro ao deletar o carro!", e);
        }finally{
            entityManager.close();
        }
    }
    
    @Override
    public List<Carro> buscar() throws ErroSistema{
        EntityManager entityManager = new FabricaConexao().getConnection();
        List<Carro> carros = null;
        try{
            String selectAll = "select c from Carro c";
            TypedQuery<Carro> tipedQuery = entityManager.createQuery(selectAll, Carro.class);
            carros = tipedQuery.getResultList();
        }catch(Exception e){
            throw new ErroSistema("Erro ao buscar os carros!", e);
        }finally{
            entityManager.close();
        }
        return carros;
    }

    @Override
    public List<Carro> buscar(Carro entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

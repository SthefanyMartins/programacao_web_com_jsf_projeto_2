package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Usuario;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;



public class UsuarioDAO implements CrudDAO<Usuario>{

    @Override
    public void salvar(Usuario entidade) throws ErroSistema {
         EntityManager entityManager = new FabricaConexao().getConnection();
        try{    
            entityManager.getTransaction().begin();
            if(entidade.getId() == null){
                entityManager.persist(entidade);
            }else{
                entityManager.merge(entidade);
            }
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            throw new ErroSistema("Erro ao deletar o usuário!", e);
        }finally{
            entityManager.close();
        }
    }

    @Override
    public void deletar(Usuario entidade) throws ErroSistema {
        EntityManager entityManager = new FabricaConexao().getConnection();
        try {
            entidade = entityManager.find(Usuario.class, entidade.getId());
            entityManager.getTransaction().begin();
            entityManager.remove(entidade);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new ErroSistema("Erro ao deletar o usuario!", e);
        }finally{
            entityManager.close();
        }
    }

    @Override
    public List<Usuario> buscar() throws ErroSistema {
        EntityManager entityManager = new FabricaConexao().getConnection();
        List<Usuario> usuarios = null;
        try{
            String selectAll = "select u from Usuario u";
            TypedQuery<Usuario> tipedQuery = entityManager.createQuery(selectAll, Usuario.class);
            usuarios = tipedQuery.getResultList();
        }catch(Exception e){
            throw new ErroSistema("Erro ao buscar os usuarios!", e);
        }finally{
            entityManager.close();
        }
        return usuarios;
    }
    
    
    
}

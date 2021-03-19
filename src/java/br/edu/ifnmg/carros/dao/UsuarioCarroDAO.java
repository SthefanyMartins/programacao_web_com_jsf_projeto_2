package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.UsuarioCarro;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UsuarioCarroDAO implements CrudDAO<UsuarioCarro>{
    @Override
    public void salvar(UsuarioCarro entidade) throws ErroSistema {
         EntityManager entityManager = new FabricaConexao().getConnection();
        try{    
            entityManager.getTransaction().begin();
            entityManager.persist(entidade);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            throw new ErroSistema("Erro ao salvar usuário!", e);
        }finally{
            entityManager.close();
        }
    }

    @Override
    public void deletar(UsuarioCarro entidade) throws ErroSistema {
        EntityManager entityManager = new FabricaConexao().getConnection();
        try {
            entidade = entityManager.find(UsuarioCarro.class, entidade.getId());
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
    public List<UsuarioCarro> buscar() throws ErroSistema {
//        EntityManager entityManager = new FabricaConexao().getConnection();
        List<UsuarioCarro> usuariosCarros = null;
//        try{
//            String selectAll = "select uc from UsuarioCarro uc";
//            TypedQuery<UsuarioCarro> tipedQuery = entityManager.createQuery(selectAll, UsuarioCarro.class);
//            usuariosCarros = tipedQuery.getResultList();
//        }catch(Exception e){
//            throw new ErroSistema("Erro ao buscar os usuarios!", e);
//        }finally{
//            entityManager.close();
//        }
        return usuariosCarros;
    }


    @Override
    public List<UsuarioCarro> buscar(Integer id) throws ErroSistema {
        System.out.println("ID = " + id);
        EntityManager entityManager = new FabricaConexao().getConnection();
        List<UsuarioCarro> usuariosCarros = new ArrayList<UsuarioCarro>();
        try{
            String select = "select uc from UsuarioCarro uc JOIN uc.carro where uc.usuario = " + id;
            TypedQuery<UsuarioCarro> tipedQuery = entityManager.createQuery(select, UsuarioCarro.class);
            usuariosCarros = tipedQuery.getResultList();
            usuariosCarros.forEach(uc -> System.out.println(uc.getCarro().getModelo() + ", " + uc.getUsuario().getLogin()));
            
        }catch(Exception ex){
            throw new ErroSistema("Erro ao buscar os carros do usuario!", ex);
        }finally{
            entityManager.close();
        }
        return usuariosCarros;
    }

    @Override
    public UsuarioCarro buscarUm(Integer e) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
}

package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.entidade.Usuario;
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
            if(entidade.getId() == null){
                entityManager.persist(entidade);
            }else{
                entityManager.merge(entidade);
            }
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
        EntityManager entityManager = new FabricaConexao().getConnection();
        List<UsuarioCarro> usuariosCarros = null;
        try{
            String selectAll = "select uc from UsuarioCarro uc";
            TypedQuery<UsuarioCarro> tipedQuery = entityManager.createQuery(selectAll, UsuarioCarro.class);
            usuariosCarros = tipedQuery.getResultList();
        }catch(Exception e){
            throw new ErroSistema("Erro ao buscar os usuarios!", e);
        }finally{
            entityManager.close();
        }
        return usuariosCarros;
    }

    @Override
    public List<UsuarioCarro> buscar(UsuarioCarro entidade) throws ErroSistema {
        EntityManager entityManager = new FabricaConexao().getConnection();
        Usuario usuario;
        List<UsuarioCarro> usuariosCarros = new ArrayList<UsuarioCarro>();
        try{
            String select = "SELECT c FROM Carro c INNER JOIN UsuarioCarro uc ON (c.id = uc.carro_id) WHERE uc.usuario_id = :idUsuario";
            TypedQuery<UsuarioCarro> tipedQuery = entityManager.createQuery(select, UsuarioCarro.class).setParameter("idUsuario", entidade.getId().getUsuarioId());
            usuariosCarros = tipedQuery.getResultList();
            usuario = buscarUsuario(entidade);
        }catch(Exception e){
            throw new ErroSistema("Erro ao buscar os carros do usuario!", e);
        }finally{
            entityManager.close();
        }
        return usuariosCarros;
    }
    
    public Usuario buscarUsuario(UsuarioCarro usuarioCarro) throws ErroSistema{
        EntityManager entityManager = new FabricaConexao().getConnection();
        Usuario usuario = new Usuario();
        try{
            String select = "select u from Usuario u where id = :idUsuario";
            usuario = entityManager.createQuery(select, Usuario.class).setParameter("idUsuario", usuarioCarro.getId().getUsuarioId()).getSingleResult();
        }catch(Exception ex){
            throw new ErroSistema("Erro ao buscar os carros do usuario!", ex);
        }finally{
            entityManager.close();
        }
        return usuario;
    }
    
}

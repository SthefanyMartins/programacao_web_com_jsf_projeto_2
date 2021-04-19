package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.entidade.Telefone;
import br.edu.ifnmg.carros.entidade.Usuario;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UsuarioDAO implements CrudDAO<Usuario>{

    @Override
    public void salvar(Usuario entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Não foi possível salvar o usuário!"); 
    }
    
    @Override
    public void salvar(Usuario entidade, List<Telefone> telefonesDeletados) throws ErroSistema {
        EntityManager entityManager = new FabricaConexao().getConnection();
        try{    
            entityManager.getTransaction().begin();
            for(Telefone t : telefonesDeletados){
                if(t.getId() != null){
                    Telefone tel = entityManager.find(Telefone.class, t.getId());
                    tel.setUsuario(null);
                    entityManager.merge(tel);
                    entityManager.remove(tel);
                }
            }         
            System.out.println(entidade.getTelefones().size());
            if(entidade.getId()== null){
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
        Usuario u = new Usuario();
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

  
    @Override
    public Usuario buscarUm(Integer id) throws ErroSistema {
        EntityManager entityManager = new FabricaConexao().getConnection();
        Usuario usuario;
        try{
            entityManager.getTransaction().begin();
            usuario = entityManager.find(Usuario.class, id);
            entityManager.getTransaction().commit();
            System.out.println(usuario);
        }catch(Exception e){
            throw new ErroSistema("Erro ao buscar o usuario!", e);
        }finally{
            entityManager.close();
        }
        return usuario;
    }
    
    @Override
    public List<Carro> retornarCarros(Usuario u){
        return u.getCarros();
    }
    
    @Override
    public List<Telefone> retornarTelefones(Usuario u) throws ErroSistema{
        EntityManager entityManager = new FabricaConexao().getConnection();
        List<Telefone> telefones = null;
        try{
            String jpql = "Select t from Telefone t where t.usuario = :id";
            TypedQuery<Telefone> typedQuery = entityManager.createQuery(jpql, Telefone.class);
            typedQuery.setParameter("id", u);
            
            telefones = typedQuery.getResultList();
        }catch(Exception e){
                throw new ErroSistema("Erro ao buscar os telefones!", e);
        }finally{
            entityManager.close();
        }
        return telefones;
    }
    
   public List<Usuario> buscarPorLogin(String login) throws ErroSistema{
        EntityManager entityManager = new FabricaConexao().getConnection();
        List<Usuario> usuarios = null;
        try{
            String jpql = "select u from Usuario u where u.login like :login";
            TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
            typedQuery.setParameter("login", "%" + login + "%");
            usuarios = typedQuery.getResultList();
        }catch(Exception e){
            throw new ErroSistema("Erro ao buscar os carros!", e);
        }finally{
            entityManager.close();
        }
        return usuarios;
    }
}

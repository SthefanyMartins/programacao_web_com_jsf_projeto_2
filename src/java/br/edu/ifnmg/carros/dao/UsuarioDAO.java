package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.entidade.Usuario;
import br.edu.ifnmg.carros.entidade.UsuarioCarro;
import br.edu.ifnmg.carros.entidade.UsuarioCarroKey;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;



public class UsuarioDAO implements CrudEntidadeSimplesDAO<Usuario>{

    @Override
    public void salvar(Usuario entidade) throws ErroSistema {
         EntityManager entityManager = new FabricaConexao().getConnection();
        try{    
            entityManager.getTransaction().begin();
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
    public void salvarCarros(Integer idUsuario, Integer idCarro) throws ErroSistema {
        if(existeEntidade(idUsuario, idCarro)){
            EntityManager entityManager = new FabricaConexao().getConnection();
            Usuario usuario;
            Carro carro;
            UsuarioCarro usuarioCarro = new UsuarioCarro();
           try{    
               entityManager.getTransaction().begin();
               usuario = entityManager.find(Usuario.class, idUsuario);
               carro = entityManager.find(Carro.class, idCarro);
               usuarioCarro.setUsuario(usuario);
               usuarioCarro.setCarro(carro);
               usuarioCarro.setId(new UsuarioCarroKey(idUsuario, idCarro));
               entityManager.persist(usuarioCarro);
               entityManager.getTransaction().commit();
           }catch(Exception e){
               entityManager.getTransaction().rollback();
               throw new ErroSistema("Erro ao salvar carro!", e);
           }finally{
               entityManager.close();
           }
        }else{
            throw new ErroSistema("O usuário já tem esse carro!");
        }
    }
    
    public boolean existeEntidade(Integer idUsuario, Integer idCarro) throws ErroSistema{
        EntityManager entityManager = new FabricaConexao().getConnection();
        UsuarioCarro usuarioCarro = null;
        Boolean valor;
        try{
            UsuarioCarroKey uck = new UsuarioCarroKey();
            uck.setUsuarioId(idUsuario);
            uck.setCarroId(idCarro);
            entityManager.getTransaction().begin();
            usuarioCarro = entityManager.find(UsuarioCarro.class, uck);
            entityManager.getTransaction().commit();
            entityManager.close();
        }catch(Exception ex){
            throw new ErroSistema("Erro ao buscar os carros do usuario!", ex);
        }finally{
            if(usuarioCarro == null){
                valor = true;
            }else{
                valor = false;
            }
        }
        return valor;
    }

    @Override
    public void deletarCarros(Integer idUsuario, Integer idCarro) throws ErroSistema {
        EntityManager entityManager = new FabricaConexao().getConnection();
        UsuarioCarro usuarioCarro;
        try {
           UsuarioCarroKey uck = new UsuarioCarroKey();
           uck.setUsuarioId(idUsuario);
           uck.setCarroId(idCarro);
           entityManager.getTransaction().begin();
           usuarioCarro = entityManager.find(UsuarioCarro.class, uck);
           entityManager.remove(usuarioCarro);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new ErroSistema("Erro ao deletar o usuario!", e);
        }finally{
            entityManager.close();
        }
    }
}

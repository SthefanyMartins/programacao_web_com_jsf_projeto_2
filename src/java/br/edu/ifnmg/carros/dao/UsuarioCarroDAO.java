package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.entidade.Usuario;
import br.edu.ifnmg.carros.entidade.UsuarioCarro;
import br.edu.ifnmg.carros.entidade.UsuarioCarroKey;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.List;
import javax.persistence.EntityManager;

public class UsuarioCarroDAO implements CrudEntidadeCompostaDAO<UsuarioCarro>{
    @Override 
    public void salvar(Integer idUsuario, Integer idCarro) throws ErroSistema {
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

    @Override
    public void deletar(Integer idUsuario, Integer idCarro) throws ErroSistema {
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
    
    @Override
    public void deletarPorIdUsuario(List<Usuario> u) throws ErroSistema{
        EntityManager entityManager = new FabricaConexao().getConnection();
        UsuarioCarro usuarioCarro = new UsuarioCarro();
        try {
           for(Usuario usuario : u){
               String delete = "Delete uc from UsuarioCarro uc where uc.id_usuario = " + usuario.getId();
               entityManager.createQuery(delete).executeUpdate();
           }
           
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new ErroSistema("Erro ao deletar o usuario!", e);
        }finally{
            entityManager.close();
        }
    }
    
    @Override
    public void deletarPorIdCarro(List<Carro> c) throws ErroSistema{
        EntityManager entityManager = new FabricaConexao().getConnection();
        UsuarioCarro usuarioCarro = new UsuarioCarro();
        try {
           String delete = "Delete uc from UsuarioCarro uc where id_carro = " ;
           entityManager.createQuery(delete);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new ErroSistema("Erro ao deletar o usuario!", e);
        }finally{
            entityManager.close();
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

    
    

    

    
 
       
}

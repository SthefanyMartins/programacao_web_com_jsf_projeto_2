package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.entidade.Usuario;
import br.edu.ifnmg.carros.entidade.UsuarioCarro;
import br.edu.ifnmg.carros.entidade.UsuarioCarroKey;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import javax.persistence.EntityManager;

public class CarroUsuarioDAO implements CrudEntidadeCompostaDAO<UsuarioCarro>{
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
               throw new ErroSistema("Erro ao salvar usuário!", e);
           }finally{
               entityManager.close();
           }
        }else{
            throw new ErroSistema("O carro já tem esse usuário!");
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

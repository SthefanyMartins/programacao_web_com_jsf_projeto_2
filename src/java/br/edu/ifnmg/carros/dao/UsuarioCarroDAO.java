package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.entidade.Usuario;
import br.edu.ifnmg.carros.entidade.UsuarioCarro;
import br.edu.ifnmg.carros.entidade.UsuarioCarroKey;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UsuarioCarroDAO implements CrudEntidadeCompostaDAO<UsuarioCarro>{
    @Override
    public void salvar(Integer idUsuario, Integer idCarro) throws ErroSistema {
        System.out.println("Valor idCarro Dao: " + idCarro);
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
    public List<UsuarioCarro> buscar(Integer id) throws ErroSistema {
        System.out.println("ID = " + id);
        EntityManager entityManager = new FabricaConexao().getConnection();
        Usuario usuario;
        UsuarioCarro usuarioC = new UsuarioCarro();
        List<UsuarioCarro> usuariosCarros = new ArrayList<UsuarioCarro>();
        try{
            try{
                    entityManager.getTransaction().begin();
                    usuario = entityManager.find(Usuario.class, id);
                    entityManager.getTransaction().commit();
                    System.out.println(usuario.getLogin());
                }catch(Exception e){
                    throw new ErroSistema("Erro ao buscar o usuario!", e);
                }
            String select = "select uc from UsuarioCarro uc JOIN uc.carro where uc.usuario = " + id;
            TypedQuery<UsuarioCarro> tipedQuery = entityManager.createQuery(select, UsuarioCarro.class);
            usuariosCarros = tipedQuery.getResultList();
            usuariosCarros.forEach(uc -> System.out.println(uc.getCarro().getModelo() + ", " + uc.getUsuario().getLogin()));
            if(usuariosCarros.size() < 1){
                usuarioC.setUsuario(usuario);
                usuariosCarros.add(usuarioC);
            }
            System.out.println("Buscar Usuario dentro de usuariocarro  "+usuariosCarros.get(0));
        }catch(Exception ex){
            throw new ErroSistema("Erro ao buscar os carros do usuario!", ex);
        }finally{
            entityManager.close();
        }
        return usuariosCarros;
    }

    @Override
    public List<UsuarioCarro> buscarParaAdicionar() throws ErroSistema {
        EntityManager entityManager = new FabricaConexao().getConnection();
        List<Carro> carros = new ArrayList<Carro>();
        UsuarioCarro uCarro = new UsuarioCarro();
        List<UsuarioCarro> usuariosCarros = new ArrayList<UsuarioCarro>();
        try{
            String select = "select c from Carro c";
            TypedQuery<Carro> tipedQuery = entityManager.createQuery(select, Carro.class);
            carros = tipedQuery.getResultList();
            carros.forEach(carro -> System.out.println(carro.getModelo()));
            for(Carro carro : carros){
                uCarro.setCarro(carro);
                usuariosCarros.add(uCarro);
                uCarro = new UsuarioCarro();
            }
        }catch(Exception ex){
            throw new ErroSistema("Erro ao buscar os carros do usuario!", ex);
        }finally{
            entityManager.close();
        }
        return usuariosCarros;
    }
       
}

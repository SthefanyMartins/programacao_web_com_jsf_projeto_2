package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.bean.UsuarioBean;
import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.entidade.Usuario;
import br.edu.ifnmg.carros.entidade.UsuarioCarro;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CarroDAO implements CrudEntidadeSimplesDAO<Carro>{
    
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
        UsuarioBean usuarioBean = new UsuarioBean();
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        List<Integer> listaDeletarUsuario = new ArrayList<Integer>();
        try {
            carro = entityManager.find(Carro.class, carro.getId());
            listaUsuarios = carro.getUsuarios();
            entityManager.getTransaction().begin();
            for(Usuario usuario : listaUsuarios){
                listaDeletarUsuario.add(usuario.getId());
            }
            UsuarioCarro usuarioCarro = new UsuarioCarro();
            usuarioBean.setListaDeletar(listaDeletarUsuario);
            usuarioBean.setIdCarro(Integer.toString(carro.getId()));
            usuarioBean.deleteCarro("carro");
            
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
    public Carro buscarUm(Integer id) throws ErroSistema {
        EntityManager entityManager = new FabricaConexao().getConnection();
        Carro carro;
        try{
            entityManager.getTransaction().begin();
            carro = entityManager.find(Carro.class, id);
            entityManager.getTransaction().commit();
            System.out.println(carro.getUsuarios());
        }catch(Exception e){
            throw new ErroSistema("Erro ao buscar o carro!", e);
        }finally{
            entityManager.close();
        }
        return carro;
    }

    @Override
    public void salvarCarros(Integer idUsuario, Integer idCarro) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarCarros(Integer idUsuario, Integer idCarro) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


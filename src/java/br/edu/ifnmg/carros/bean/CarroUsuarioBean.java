package br.edu.ifnmg.carros.bean;

import br.edu.ifnmg.carros.dao.UsuarioCarroDAO;
import br.edu.ifnmg.carros.entidade.UsuarioCarro;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CarroUsuarioBean extends CrudCompostoBean<UsuarioCarro, UsuarioCarroDAO>{

    private UsuarioCarroDAO usuarioCarroDAO;
    private String id;
    
    public void init(){
        setIdUsuario(Integer.parseInt(id));
    }
    
    public Integer retornaIdInteiro(){
        return Integer.parseInt(id);
    }
    
    @Override
    public UsuarioCarroDAO getDao() {
        if(usuarioCarroDAO == null){
            usuarioCarroDAO = new UsuarioCarroDAO();
        }
        return usuarioCarroDAO;
    }
    
    @Override
    public UsuarioCarro criarNovaEntidade() {
        return new UsuarioCarro();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

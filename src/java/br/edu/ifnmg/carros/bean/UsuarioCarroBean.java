package br.edu.ifnmg.carros.bean;


import br.edu.ifnmg.carros.dao.UsuarioCarroDAO;
import br.edu.ifnmg.carros.entidade.UsuarioCarro;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class UsuarioCarroBean extends CrudBean<UsuarioCarro, UsuarioCarroDAO>{

    private UsuarioCarroDAO usuarioCarroDAO;
    
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
    
    
}

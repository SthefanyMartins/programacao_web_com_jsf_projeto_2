package br.edu.ifnmg.carros.bean;

import br.edu.ifnmg.carros.dao.UsuarioDAO;
import br.edu.ifnmg.carros.entidade.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class UsuarioBean extends CrudBean<Usuario, UsuarioDAO>{

    private UsuarioDAO usuarioDAO;
     
    @Override
    public UsuarioDAO getDao() {
        if(usuarioDAO == null){
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }

    @Override
    public Usuario criarNovaEntidade() {
        return new Usuario();
    }
    
    public String gerenciarCarrosUrl(Usuario usuario) {
        return "/gerenciar_usuario_carro.jsf?faces-redirect=true&amp;includeViewParams=true&id=" + usuario.getId();
    }
    
}

package br.edu.ifnmg.carros.bean;

import br.edu.ifnmg.carros.dao.CrudEntidadeCompostaDAO;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class CrudCompostoBean<E, D extends CrudEntidadeCompostaDAO> {
    private E entidade;
    private List<E> entidadeA;
    private List<E> entidades;
    private Integer idUsuario;
    private Integer idCarro;
    
    public void salvar(){
        System.out.println("Valor idCarro: " + idCarro);
        try {
            getDao().salvar(idUsuario, idCarro);
            entidade = criarNovaEntidade();
            adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void delete(E entidade){
        try {
            getDao().deletar(entidade);
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        
    }
    
    public void buscarEntidades(Integer id){
        idUsuario = id;
        if(id == null){
            adicionarMensagem("Não foi possível encontrar a entidade!", FacesMessage.SEVERITY_WARN);
        }
        try {
            entidades = getDao().buscar(id);
           if(entidades == null || entidades.size() < 1){
                adicionarMensagem("Não há dados cadastrados!", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void buscarParaAdicionar(){
        try {
            entidadeA = getDao().buscarParaAdicionar();
           if(entidadeA == null || entidades.size() < 1){
                adicionarMensagem("Não há dados cadastrados!", FacesMessage.SEVERITY_WARN);
            }
           System.out.println("CrudCompostoBean buscarParaAdicionar EntidadeA: " + entidadeA);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro){
        FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }
    
    //getters e setters
    public E getEntidade() {
        return entidade;
    }
    public void setEntidade(E entidade) {
        this.entidade = entidade;
    }
    public List<E> getEntidades() {
        return entidades;
    }
    public void setEntidades(List<E> entidades) {
        this.entidades = entidades;
    }
    public List<E> getEntidadeA() {
        return entidadeA;
    }
    public void setEntidadeA(List<E> entidadeA) {
        this.entidadeA = entidadeA;
    }
    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Integer getIdCarro() {
        return idCarro;
    }
    public void setIdCarro(Integer idCarro) {
        this.idCarro = idCarro;
    }
    
    
    //Responsável por criar os métodos nas classes bean
    public abstract D getDao();
    public abstract E criarNovaEntidade();
}

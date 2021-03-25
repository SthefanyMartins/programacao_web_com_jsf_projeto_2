package br.edu.ifnmg.carros.bean;

import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import br.edu.ifnmg.carros.dao.CrudEntidadeSimplesDAO;

public abstract class CrudSimplesBean<E, D extends CrudEntidadeSimplesDAO> {
    
    private String estadoTela; // insere, edita, busca
    private E entidade;
    private List<E> entidades;
    private String idUsuario;
    private String idCarro;
    
    public CrudSimplesBean(){
        estadoTela = "buscar";
        buscar();
    }
    
    public void mandaSalvarUsuarioCarro(){
        UsuarioCarroBean usuarioCarroBean = new UsuarioCarroBean();
        usuarioCarroBean.setIdUsuario(Integer.parseInt(idUsuario));
        usuarioCarroBean.setIdCarro(Integer.parseInt(idCarro));
        usuarioCarroBean.salvar();
        buscarEntidade();
    }
    
    public void mandaDeletarUsuarioCarro(String id){
        idCarro = id;
        UsuarioCarroBean usuarioCarroBean = new UsuarioCarroBean();
        usuarioCarroBean.setIdUsuario(Integer.parseInt(idUsuario));
        usuarioCarroBean.setIdCarro(Integer.parseInt(idCarro));
        usuarioCarroBean.delete();
        buscarEntidade();
    }
    
    public void novo(){
        entidade = criarNovaEntidade();
        mudarParaInseri();
    }
    
    public void salvar(){
        try {
            getDao().salvar(entidade);
            entidade = criarNovaEntidade();
            adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            mudarParaBusca();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void editar(E entidade){
        this.entidade = entidade;
        mudarParaEdita();
    }
    
    public void delete(E entidade){
        try {
            getDao().deletar(entidade);
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
            mudarParaBusca();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        } 
    }
    
    public void buscar(){
        if(isBusca() == false){
            mudarParaBusca();
            return;
        }
        try {
            entidades = getDao().buscar();
            if(entidades == null || entidades.size() < 1){
                adicionarMensagem("Não há dados cadastrados!", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
   
        
    public void buscarEntidade(){
        Integer id = Integer.parseInt(idUsuario);
        if(id == null){
            adicionarMensagem("Não foi possível encontrar a entidade!", FacesMessage.SEVERITY_WARN);
        }
        try {
            entidade = (E) getDao().buscarUm(id);
            if(entidade == null){
                adicionarMensagem("Não há dados cadastrados!", FacesMessage.SEVERITY_WARN);
            }
            
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
    public String getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getIdCarro() {
        return idCarro;
    }
    public void setIdCarro(String idCarro) {
        this.idCarro = idCarro;
    }
    
    //Responsável por criar os métodos nas classes bean
    public abstract D getDao();
    public abstract E criarNovaEntidade();
    
    //metodos de consulta / controle da tela
    public boolean isInseri(){
        return "inserir".equals(estadoTela);
    }
    public boolean isEdita(){
        return "editar".equals(estadoTela);
    }
    public boolean isBusca(){
        return "buscar".equals(estadoTela);
    }
    
    //metodos para mudar o estado da tela
    public void mudarParaInseri(){
        estadoTela = "inserir";
    }
    public void mudarParaEdita(){
        estadoTela = "editar";
    }
    public void mudarParaBusca(){
        estadoTela = "buscar";
        buscar();
    }
}

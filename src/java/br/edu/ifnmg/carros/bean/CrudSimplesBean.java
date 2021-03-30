package br.edu.ifnmg.carros.bean;

import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import br.edu.ifnmg.carros.dao.CrudEntidadeSimplesDAO;
import java.util.ArrayList;

public abstract class CrudSimplesBean<E, D extends CrudEntidadeSimplesDAO> {
    
    private String estadoTela; // insere, edita, busca
    private E entidade;
    private List<E> entidades;
    private String idUsuario;
    private String idCarro;
    private String valorEntidade;
    private List<Integer> listaDeletar = new ArrayList<Integer>();
    
    public CrudSimplesBean(){
        estadoTela = "buscar";
        buscar();
    }
    
    public void novo(){
        entidade = criarNovaEntidade();
        mudarParaInseri();
    }
    
    public void botaoSalvar(){ 
        salvar();
        if(valorEntidade == "usuario"){
            salvarCarro();
            deleteCarro("usuario");
        }
        buscar();
    }
    
    public void pegarListaDeletar(String id){
        listaDeletar.add(Integer.parseInt(id));
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
    
    public void salvarCarro(){
        try {
            Integer carro = Integer.parseInt(idCarro);
            if(carro > 0){
                getDao().salvarCarros(Integer.parseInt(idUsuario), carro);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void editar(E entidade, String id){
        this.entidade = entidade;
        if(valorEntidade == "usuario"){
            idUsuario = id;
        }
        System.out.println("Editar idUsuario: " + idUsuario);
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
    
    public void deleteCarro(String a){
        for(Integer item : listaDeletar){
            try {
                if(a == "carro"){
                    getDao().deletarCarros(item, Integer.parseInt(idCarro));
                }else{
                    getDao().deletarCarros(Integer.parseInt(idUsuario), item);
                }
            } catch (ErroSistema ex) {
                Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
                adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
            }
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
    public String getValorEntidade() {
        return valorEntidade;
    }
    public void setValorEntidade(String valorEntidade) {
        this.valorEntidade = valorEntidade;
    }
    public List<Integer> getListaDeletar() {
        return listaDeletar;
    }
    public void setListaDeletar(List<Integer> listaDeletar) {
        this.listaDeletar = listaDeletar;
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

package br.edu.ifnmg.carros.bean;

import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import br.edu.ifnmg.carros.dao.CrudEntidadeSimplesDAO;
import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.entidade.Telefone;
import br.edu.ifnmg.carros.entidade.Usuario;
import java.util.ArrayList;

public abstract class CrudSimplesBean<E, D extends CrudEntidadeSimplesDAO> {
    
    private String estadoTela; // insere, edita, busca
    private String idUsuario;
    private String idCarro;
    private String numero;
    private String tipo;
    private String valorEntidade;
    private String statusTelefone;
    private String mascara;
    private Boolean campoMask = true;
    private E entidade;
    private List<E> entidades;
    private Telefone telefone;
    private List<Telefone> listaTelefonesDeletados = new ArrayList<Telefone>();
    
    public CrudSimplesBean(){
        estadoTela = "buscar";
        setStatusTelefone("novo");
        buscar();
    }
    
    public void novo(){
        setEntidade(criarNovaEntidade());
        mudarParaInseri();
    }
    
    public void botaoSalvar(){ 
        salvar();
        buscar();
    }
    
    public void salvar(){
        try {
            if(getValorEntidade() == "usuario"){
                getDao().salvar(getEntidade(), getListaTelefonesDeletados());
            }else{
                getDao().salvar(getEntidade());
            }
            setEntidade(criarNovaEntidade());
            adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            mudarParaBusca();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void editar(E entidade, String id){
        setEntidade(entidade);
        if("usuario".equals(getValorEntidade())){
            setIdUsuario(id);
            retornaCarros(entidade);
            retornaTelefones(entidade);
        }
        mudarParaEdita();
    }
    
    public void editarTelefone(Telefone tel){
        setTipo(tel.getTipo()); 
        mudarMascara();
        setNumero(tel.getNumero());
        setStatusTelefone("editar");
        setTelefone(tel);
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
            setEntidades(getDao().buscar());
            if(getEntidades() == null || getEntidades().size() < 1){
                adicionarMensagem("Não há dados cadastrados!", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void buscarEntidade(String id1){
        Integer id = Integer.parseInt(id1);
        if(id == null){
            adicionarMensagem("Não foi possível encontrar a entidade!", FacesMessage.SEVERITY_WARN);
        }
        try {
            setEntidade((E) getDao().buscarUm(id));
            if(getEntidade() == null){
                adicionarMensagem("Não há dados cadastrados!", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudSimplesBean.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
//*************************************************CARROS DO USUARIO*************************************************
   public void retornaCarros(E entidade){
       ((Usuario)getEntidade()).setCarros(getDao().retornarCarros(getEntidade()));
    }

   public void pegarListaDeletar(Carro carro){
        ((Usuario)getEntidade()).getCarros().remove(carro);
    }
    
    public void pegarListaAdicionar(){
        CarroBean carroBean = new CarroBean();
        carroBean.buscarEntidade(getIdCarro());
        Carro carro = carroBean.getEntidade();
        ((Usuario)getEntidade()).getCarros().add(carro);
        setIdCarro("0");
    }
    
//*************************************************TELEFONES DO USUARIO*************************************************
    public void retornaTelefones(E entidade){
        ((Usuario)getEntidade()).setTelefones(getDao().retornarTelefones(getEntidade()));
    }
    
    public void adicionarTelefones(){
        if("novo".equals(getStatusTelefone())){
            setTelefone(new Telefone());
        }else{//quando estiver editando um telefone
            ((Usuario) getEntidade()).getTelefones().remove(getTelefone());
        }
        getTelefone().setNumero(getNumero());
        getTelefone().setTipo(getTipo());
        getTelefone().setUsuario(((Usuario)getEntidade()));
        ((Usuario) getEntidade()).getTelefones().add(getTelefone());
        setTipo("");
        setNumero("");
        setStatusTelefone("novo");
    }
    
    public void deletarTelefones(Telefone tel){
        //getListaTelefonesDeletados().add(tel);
        ((Usuario)getEntidade()).getTelefones().remove(tel);
    }
    
    public void mudarMascara(){
        if("Telefone".equals(getTipo())){
            setMascara("(99)9999-9999");
            setCampoMask(false);
        }else if("Celular".equals(getTipo())){
            setMascara("(99)99999-9999");
            setCampoMask(false);
        }else{
            setCampoMask(true);
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
    public Telefone getTelefone() {
        return telefone;
    }
    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public List<Telefone> getListaTelefonesDeletados() {
        return listaTelefonesDeletados;
    }
    public void setListaTelefonesDeletados(List<Telefone> listaTelefonesDeletados) {
        this.listaTelefonesDeletados = listaTelefonesDeletados;
    }
    public String getStatusTelefone() {
        return statusTelefone;
    }
    public void setStatusTelefone(String statusTelefone) {
        this.statusTelefone = statusTelefone;
    }
        public String getMascara() {
        return mascara;
    }
    public void setMascara(String mascara) {
        this.mascara = mascara;
    }
    public Boolean getCampoMask() {
        return campoMask;
    }
    public void setCampoMask(Boolean campoMask) {
        this.campoMask = campoMask;
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

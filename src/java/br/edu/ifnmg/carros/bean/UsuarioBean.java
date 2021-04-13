package br.edu.ifnmg.carros.bean;

import br.edu.ifnmg.carros.dao.UsuarioDAO;
import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.entidade.Telefone;
import br.edu.ifnmg.carros.entidade.Usuario;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class UsuarioBean extends CrudBean<Usuario, UsuarioDAO>{

    private UsuarioDAO usuarioDAO;
    private String idUsuario;
    private String idCarro;
    private String numero;
    private String tipo;
    private String statusTelefone;
    private String mascara;
    private Boolean campoMask = true;
    private Telefone telefone;
    private List<Telefone> listaTelefonesDeletados = new ArrayList<Telefone>();
    
    public UsuarioBean(){
        setStatusTelefone("novo");
    }
    
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
    
    @Override
    public void salvar(){
        try {
            getDao().salvar(getEntidade(), getListaTelefonesDeletados());
            setEntidade(criarNovaEntidade());
            adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            mudarParaBusca();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    @Override
    public void editar(Usuario entidade, String id){
        setEntidade(entidade);
        setIdUsuario(id);
        retornaCarros(entidade);
        retornaTelefones(entidade);
        mudarParaEdita();
    }
    
//*************************************************CARROS DO USUARIO*************************************************
    public void retornaCarros(Usuario entidade){
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
    public void retornaTelefones(Usuario entidade){
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
    
    public void editarTelefone(Telefone tel){
        setTipo(tel.getTipo()); 
        mudarMascara();
        setNumero(tel.getNumero());
        setStatusTelefone("editar");
        setTelefone(tel);
    }
    
    public void deletarTelefones(Telefone tel){
        getListaTelefonesDeletados().add(tel);
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
    
    //GETTERS E SETTERS
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
    public String getStatusTelefone() {
        return statusTelefone;
    }
    public void setStatusTelefone(String statusTelefone) {
        this.statusTelefone = statusTelefone;
    }
    public Telefone getTelefone() {
        return telefone;
    }
    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }
    public List<Telefone> getListaTelefonesDeletados() {
        return listaTelefonesDeletados;
    }
    public void setListaTelefonesDeletados(List<Telefone> listaTelefonesDeletados) {
        this.listaTelefonesDeletados = listaTelefonesDeletados;
    }
}

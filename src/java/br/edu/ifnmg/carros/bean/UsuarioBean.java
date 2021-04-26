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
    private String login;
    private String guardaSenha;
    private String numero;
    private String tipo;
    private String statusTelefone;
    private String mascara;
    private Boolean campoMask = true;
    private Telefone telefone;
    private List<Telefone> listaTelefonesDeletados = new ArrayList<Telefone>();
    
    public UsuarioBean(){
        setStatusTelefone("novo");
        mudarParaBusca();
        buscar();
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
            String testaLogin = getEntidade().getLogin().trim();
            String testaSenha = getEntidade().getSenha().trim();
            if("".equals(testaLogin) || testaLogin.length() == 0){
                adicionarMensagem("Digite um login válido!", FacesMessage.SEVERITY_ERROR);
            }else if(testaLoginExiste(getEntidade().getLogin())){
                adicionarMensagem("Esse login já existe! Digite um login válido!", FacesMessage.SEVERITY_ERROR);
            }else if(getEntidade().getId() == null && ("".equals(testaSenha) || testaSenha.length() == 0)){
                adicionarMensagem("Digite uma senha válida!", FacesMessage.SEVERITY_ERROR);
            }else{
                if("".equals(testaSenha) || testaSenha.length() == 0){
                    getEntidade().setSenha(getGuardaSenha());
                }
                getDao().salvar(getEntidade(), getListaTelefonesDeletados());
                setEntidade(criarNovaEntidade());
                adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
                mudarParaBusca();
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public Boolean testaLoginExiste(String login){
        List<Usuario> usuarios = getEntidades();
        Boolean valida = false;
        for(Usuario u : usuarios){
            if(u.getLogin().equals(login)){
                valida = true;
            }
        }
        return valida;
    }
    
    @Override
    public void editar(Usuario entidade, String id){
        setEntidade(entidade);
        setIdUsuario(id);
        setGuardaSenha(entidade.getSenha());
        retornaCarros(entidade);
        retornaTelefones(entidade);
        mudarParaEdita();
    }
    
    public void buscarComFiltro() throws ErroSistema{
        try {
            setEntidades(getDao().buscarPorLogin(getLogin()));
            if(getEntidades() == null || getEntidades().size() < 1){
                adicionarMensagem("Não há dados cadastrados encontrados!", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CarroBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void limparBusca(){
        setLogin("");
    }
//*************************************************CARROS DO USUARIO*************************************************
    public void retornaCarros(Usuario entidade){
       ((Usuario)getEntidade()).setCarros(getDao().retornarCarros(getEntidade()));
    }

    public void pegarListaDeletar(Carro carro){
        ((Usuario)getEntidade()).getCarros().remove(carro);
    }
    
    public void pegarListaAdicionar(){
        List<Carro> carros = ((Usuario)getEntidade()).getCarros();
        Integer id = Integer.parseInt(getIdCarro());
        if("0".equals(getIdCarro()) ){
            adicionarMensagem("Selecione um carro!", FacesMessage.SEVERITY_ERROR);
            return;
        }
        for(Carro c : carros){
            if(c.getId() == id){
                adicionarMensagem("Esse usuário já tem esse carro!", FacesMessage.SEVERITY_ERROR);
                return;
            }
        }
        CarroBean carroBean = new CarroBean();
        carroBean.buscarEntidade(getIdCarro());
        Carro carro = carroBean.getEntidade();
        ((Usuario)getEntidade()).getCarros().add(carro);
        setIdCarro("0");
    }
    
    
//*************************************************TELEFONES DO USUARIO*************************************************
    public void retornaTelefones(Usuario usuario){
        try {
            ((Usuario)getEntidade()).setTelefones(getDao().retornarTelefones(usuario));
        } catch (ErroSistema ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void adicionarTelefones(){
        if(!"Celular".equals(getTipo()) && !"Telefone".equals(getTipo())){
            adicionarMensagem("Selecione um tipo de telefone!", FacesMessage.SEVERITY_ERROR);
            return;
        }else if("".equals(getNumero()) || getNumero() == null){
            adicionarMensagem("Digite um telefone válido!", FacesMessage.SEVERITY_ERROR);
            return;
        }
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
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getGuardaSenha() {
        return guardaSenha;
    }
    public void setGuardaSenha(String guardaSenha) {
        this.guardaSenha = guardaSenha;
    }
    
}

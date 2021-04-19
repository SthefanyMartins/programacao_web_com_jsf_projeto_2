package br.edu.ifnmg.carros.bean;

import br.edu.ifnmg.carros.dao.CarroDAO;
import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CarroBean extends CrudBean<Carro, CarroDAO>{

    private CarroDAO carroDAO;
    private String buscaModelo;
    private String buscaFabricante;
    private String buscaCor;
    private Integer buscaAno;

    
    @Override
    public CarroDAO getDao() {
        if(carroDAO == null){
            carroDAO = new CarroDAO();
        }
        return carroDAO;
    }

    @Override
    public Carro criarNovaEntidade() {
        return new Carro();
    }
    
    @Override
    public void salvar(){
        try {
            String testaModelo = getEntidade().getModelo().trim();
            String testaFabricante = getEntidade().getFabricante().trim();
            String testaCor = getEntidade().getCor().trim();
            if("".equals(testaModelo) || testaModelo.length() == 0){
                adicionarMensagem("Digite um modelo válido!", FacesMessage.SEVERITY_ERROR);
            }else if("".equals(testaFabricante) || testaFabricante.length() == 0){
                adicionarMensagem("Digite um fabricante válido!", FacesMessage.SEVERITY_ERROR);
            }else if("".equals(testaCor) || testaCor.length()== 0){
                adicionarMensagem("Digite uma cor válida!", FacesMessage.SEVERITY_ERROR);
            }else if(getEntidade().getAno() == null){
                adicionarMensagem("Digite um ano válido!", FacesMessage.SEVERITY_ERROR);
            }else if (getEntidade().getAno().compareTo(new Date()) > 0){
                adicionarMensagem("Digite um ano válido!", FacesMessage.SEVERITY_ERROR);
            }else{
                getDao().salvar(getEntidade());
                setEntidade(criarNovaEntidade());
                adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
                mudarParaBusca();
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void buscarComFiltro() throws ErroSistema{
        try {
            setEntidades(getDao().buscarPorCategorias(getBuscaModelo(), getBuscaFabricante(), getBuscaCor(), getBuscaAno()));
            if(getEntidades() == null || getEntidades().size() < 1){
                adicionarMensagem("Não há dados cadastrados encontrados!", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CarroBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void limparBusca(){
        setBuscaModelo("");
        setBuscaFabricante("");
        setBuscaCor("");
        setBuscaAno(null);
    }
    
    //GETTERS E SETTERS
    public String getBuscaModelo() {
        return buscaModelo;
    }
    public void setBuscaModelo(String buscaModelo) {
        this.buscaModelo = buscaModelo;
    }

    public String getBuscaFabricante() {
        return buscaFabricante;
    }
    public void setBuscaFabricante(String buscaFabricante) {
        this.buscaFabricante = buscaFabricante;
    }

    public String getBuscaCor() {
        return buscaCor;
    }
    public void setBuscaCor(String buscaCor) {
        this.buscaCor = buscaCor;
    }

    public Integer getBuscaAno() {
        return buscaAno;
    }
    public void setBuscaAno(Integer buscaAno) {
        this.buscaAno = buscaAno;
    }

}

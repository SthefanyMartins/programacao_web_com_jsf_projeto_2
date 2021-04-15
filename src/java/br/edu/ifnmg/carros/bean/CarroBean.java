package br.edu.ifnmg.carros.bean;

import br.edu.ifnmg.carros.dao.CarroDAO;
import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
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

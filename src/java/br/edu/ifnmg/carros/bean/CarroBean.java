package br.edu.ifnmg.carros.bean;

import br.edu.ifnmg.carros.dao.CarroDAO;
import br.edu.ifnmg.carros.entidade.Carro;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CarroBean {
    
    private Carro carro = new Carro();
    private List<Carro> carros = new ArrayList<>();
    
    public void adicionar(){
        carros.add(carro);
        new CarroDAO().salvar(carro);
        carro = new Carro();
    }

    //get / set carro
    public Carro getCarro() {
        return carro;
    }
    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    //get / set carros
    public List<Carro> getCarros() {
        return carros;
    }
    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }
    
    
}

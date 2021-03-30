package br.edu.ifnmg.carros.bean;

import br.edu.ifnmg.carros.dao.CarroDAO;
import br.edu.ifnmg.carros.entidade.Carro;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class CarroBean extends CrudSimplesBean<Carro, CarroDAO>{

    private CarroDAO carroDAO;
    
    @Override
    public CarroDAO getDao() {
        if(carroDAO == null){
            carroDAO = new CarroDAO();
        }
        setValorEntidade("carro");
        return carroDAO;
    }

    @Override
    public Carro criarNovaEntidade() {
        return new Carro();
    }
}

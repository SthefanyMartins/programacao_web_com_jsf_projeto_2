package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.io.Serializable;
import java.util.List;

public interface CrudEntidadeSimplesDAO<E> extends Serializable{ //E representa minha entidade
    
    public void salvar(E entidade)throws ErroSistema;
    public void deletar(E entidade) throws ErroSistema;
    public List<E> buscar() throws ErroSistema;
    public List<E> buscar(Integer e) throws ErroSistema;
    public E buscarUm(Integer e) throws ErroSistema;
}

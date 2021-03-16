package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.util.List;

public interface CrudDAO<E> { //E representa minha entidade
    
    public void salvar(E entidade)throws ErroSistema;
    public void deletar(E entidade) throws ErroSistema;
    public List<E> buscar() throws ErroSistema;
    public List<E> buscar(E entidade) throws ErroSistema;
}

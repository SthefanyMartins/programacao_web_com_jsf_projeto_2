package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.entidade.Telefone;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.io.Serializable;
import java.util.List;

public interface CrudDAO<E> extends Serializable{ //E representa minha entidade                  
    public void salvar(E entidade)throws ErroSistema;
    public void salvar(E entidade, List<Telefone> telefonesDeletados)throws ErroSistema;
    public void deletar(E entidade) throws ErroSistema;
    public List<E> buscar() throws ErroSistema;
    public E buscarUm(Integer e) throws ErroSistema;
    public List<Carro> retornarCarros(E u);
    public List<Telefone> retornarTelefones(E u);
}

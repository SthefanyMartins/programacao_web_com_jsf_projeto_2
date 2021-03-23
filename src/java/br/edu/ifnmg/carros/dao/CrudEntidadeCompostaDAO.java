package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.io.Serializable;
import java.util.List;

public interface CrudEntidadeCompostaDAO <E> extends Serializable{
    public void salvar(Integer n1, Integer n2)throws ErroSistema;
    public void deletar(Integer n1, Integer n2) throws ErroSistema;
}

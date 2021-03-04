package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.util.FabricaConexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarroDAO {
    
    public void salvar(Carro carro){
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareCall("INSERT INTO `carro`(`modelo`,`fabricante`,`cor`,`ano`) " +
                    "VALUES(?,?,?,?);");
            ps.setString(1, carro.getModelo());
            ps.setString(2, carro.getFabricante());
            ps.setString(3, carro.getCor());
            ps.setDate(4, new Date(carro.getAno().getTime()));
            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

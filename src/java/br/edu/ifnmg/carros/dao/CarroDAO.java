package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarroDAO {
        
    public void salvar(Carro carro) throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if(carro.getId() == null){
                ps = conexao.prepareStatement("INSERT INTO `carro`(`modelo`,`fabricante`,`cor`,`ano`) " +
                        "VALUES(?,?,?,?);");
            }else{
                ps = conexao.prepareStatement("UPDATE  carro SET modelo=?, fabricante=?, cor=?, ano=? WHERE id=?");
                ps.setInt(5, carro.getId());
            }
            ps.setString(1, carro.getModelo());
            ps.setString(2, carro.getFabricante());
            ps.setString(3, carro.getCor());
            ps.setDate(4, new Date(carro.getAno().getTime()));
            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            //Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErroSistema("Erro ao salvar o carro!", ex);
        }
    }
    
    public void deletar(Integer idCarro) throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM carro WHERE id=?");
            ps.setInt(1, idCarro);
            ps.execute();
        } catch (SQLException ex) {
            //Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErroSistema("Erro ao deletar o carro!", ex);
        }
    }
    
    public List<Carro> buscar() throws ErroSistema{
        try {
                Connection conexao = FabricaConexao.getConexao();
                PreparedStatement ps = conexao.prepareStatement("SELECT * FROM  carro");
                ResultSet resultSet = ps.executeQuery();
                List<Carro> carros = new ArrayList<>();
                while(resultSet.next()){
                    Carro carro = new Carro();
                    carro.setId(resultSet.getInt("id"));
                    carro.setModelo(resultSet.getString("modelo"));
                    carro.setFabricante(resultSet.getString("fabricante"));
                    carro.setCor(resultSet.getString("cor"));
                    carro.setAno(resultSet.getDate("ano"));
                    carros.add(carro);
                }
                return carros;
            } catch (SQLException ex) {
            //Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErroSistema("Erro ao buscar os carros!", ex);
        }
    }
}

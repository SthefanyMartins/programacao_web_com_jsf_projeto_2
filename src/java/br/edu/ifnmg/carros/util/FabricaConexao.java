package br.edu.ifnmg.carros.util;

import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {
    
    private static Connection conexao;
    private static final String URL_CONEXAO = "jdbc:mysql://localhost:3306/sistema_carros";
    private static final String USUARIO = "sistema_carros";
    private static final String SENHA = "654321";

    public static Connection getConexao() throws ErroSistema {
        
        if(conexao == null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL_CONEXAO + "?useTimezone=true&serverTimezone=America/Sao_Paulo&user="+USUARIO+"&password="+SENHA);
            } catch (SQLException ex) {
                //Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
                throw new ErroSistema("Não foi possível conectar ao banco de dados!",ex);
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
                throw new ErroSistema("O driver do banco de dados não foi encontrado!",ex);
            }   
        }
        return conexao;
    }
    
    public static void fecharConexao()throws ErroSistema{
        if(conexao != null){
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException ex) {
                //Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
                throw new ErroSistema("Erro ao fechar a conexão com banco de dados!",ex);
            }
        }
    }
    
}

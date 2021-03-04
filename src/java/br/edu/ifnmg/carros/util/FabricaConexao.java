package br.edu.ifnmg.carros.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FabricaConexao {
    
    private static Connection conexao;
    private static final String URL_CONEXAO = "jdbc:mysql://localost/sistema_carros";
    private static final String USUARIO = "root";
    private static final String SENHA = "123456";

    public static Connection getConexao() {
        
        if(conexao == null){
           
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            } catch (SQLException ex) {
                Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("\nNao foi possivel conectar ao Banco de Dados.\n");
                return null;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("O driver expecificado nao foi encontrado.");
                return null;
            }   
        }
        return conexao;
    }
    
    public static void fecharConexao(){
        if(conexao != null){
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException ex) {
                Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}

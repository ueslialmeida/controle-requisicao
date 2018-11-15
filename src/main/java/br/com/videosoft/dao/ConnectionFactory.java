package br.com.videosoft.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsável por criar uma conexão com o banco de dados.
 * @author Uesli Almeida
 */
public class ConnectionFactory {

    // Dados para conexão com o banco de dados
    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:./database";
    private static final String USER = "";
    private static final String PASSWORD = "";
    
    /**
     * Cria uma conexão com o banco de dados.
     * @return Connection conn
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException{
        
        Connection conn = null;
        
        try{
            Class.forName(DRIVER);
        }
        catch(ClassNotFoundException e){
            System.out.print(e.getMessage());
        }
        
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
        }
        
        return conn;
        
    } // fim do método getConnection
    
    /**
     * Encerra uma conexão com o banco de dados.
     * @param conn connection
     * @param stmt statement
     * @param rs resultset
     * @throws SQLException 
     */
    public static void closeConnection(Connection conn, 
            Statement stmt, ResultSet rs) throws SQLException{
        
        if(conn != null)
            conn.close();
        
        if(stmt != null)
            stmt.close();
        
        if(rs != null)
            rs.close();
        
    } // fim do método closeConnection
    
} // fim da classe ConnectionFactory

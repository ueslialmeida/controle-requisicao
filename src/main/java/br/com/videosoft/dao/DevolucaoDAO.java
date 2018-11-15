package br.com.videosoft.dao;

import br.com.videosoft.model.Devolucao;
import br.com.videosoft.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por manipular os dados de uma Devolução no banco de dados
 * a classe implementa uma interface que assina os métodos padrões disponíveis
 * para inserção, recuperação de dados.
 * @author Uesli Almeida
 */
public class DevolucaoDAO implements MovimentacaoDAO{

    /**
     * Recupera as devoluções existentes no banco de dados.
     * @return lista de Devolucao
     */
    @Override
    public List<Devolucao> load() {
        
        // chama o método filter com argumentos nulos para que seja carregado todas as devoluções
        return filter(null, null); 
        
    } // fim do método load

    /**
     * Está classe não implementa este método.
     * @return 
     */
    @Override
    public List<Produto> loadProdutos() {
        
        return null;
        
    } // fim do método loadProdutos

    /**
     * Grava uma devolução no banco de dados.
     * @param o objeto Devolucao
     * @param products lista de Produto
     * @param edit flag de modo edição
     */
    @Override
    public void store(Object o, List<Produto> products, boolean edit) {
        
        try(Connection conn = ConnectionFactory.getConnection()){
            
            conn.setAutoCommit(false);
            
            String query = "";
            
            if(!edit){ // se não for uma edição faz um insert
                query = "INSERT INTO Devolucao "
                    + "(codigo, data, observacao)"
                    + " VALUES(?,?,?);";
            
                long lastInsertedId = 0; // último ID inserido no BD
            
                try(PreparedStatement stmt = conn.prepareStatement(query)){
                    Devolucao d = (Devolucao) o;
                
                    // seta os parâmetros da string query
                    stmt.setInt(1, d.getCodigo());
                    stmt.setDate(2, java.sql.Date.valueOf(d.getData()));
                    stmt.setString(3, d.getObservacao());
                    stmt.executeUpdate(); // executa a query no banco
                } // fim do bloco try
                
                conn.commit();
                
                try (PreparedStatement stmt = conn.prepareStatement("SELECT LAST_INSERT_ID();")) {
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            lastInsertedId = rs.getLong(1); // recupera o último ID adicionado no BD
                        } // fim do bloco while
                    } // fim do bloco try
                } // fim do bloco try
                
                // instancia um objeto ProdutoDAO e chama o método que grava os produtos
                ProdutoDAO produtoDao = new ProdutoDAO();
                produtoDao.storeProdutoDevolucao(lastInsertedId, products);
                
            } // fim do bloco if
            else{
                query = "UPDATE Devolucao "
                    + "SET observacao=? WHERE id=?;";
                
                try(PreparedStatement stmt = conn.prepareStatement(query)){
                    Devolucao d = (Devolucao) o;
                
                    // seta os parâmetro da string query
                    stmt.setString(1, d.getObservacao());
                    stmt.setLong(2, d.getId());
                    stmt.executeUpdate(); // executa a query no banco
                    
                    conn.commit(); 
                } // fim do bloco try
            } // fim do bloco else
            
        }
        catch(SQLException e){
            throw new DAOException(e);
        } // fim do bloco try/catch
        
    } // fim do méteodo store

    /**
     * Recupera os dados no BD a partir de parâmetros para filtragem.
     * @param date data da devolução
     * @param text algum termo como responsável ou produto
     * @return lista de Devolucao
     */
    @Override
    public List<Devolucao> filter(String date, String text) {
        
        try(Connection conn = ConnectionFactory.getConnection()){
            
            String query = "SELECT * FROM Devolucao d";
            
            if(date != null || text != null){ // verifica se foi informado uma data ou termo
                query += " LEFT JOIN ItemDevolucao i ON d.id = i.id_devolucao "
                        + "WHERE d.data = ? "
                        + "OR i.descricao LIKE UPPER(?);";
            } // fim do bloco if
            
            try(PreparedStatement stmt = conn.prepareStatement(query)){
                
                if(date != null || text != null){ // verifica se foi informado uma data ou termo
                    if(date != null){ // verifica se foi informada uma data
                        stmt.setDate(1, java.sql.Date.valueOf(date)); // seta o parâmetro da query
                    }
                    else{
                        stmt.setDate(1, null); // se não foi informada uma data o parâmetro é nulo
                    }
                    stmt.setString(2, "%" + text + "%"); // seta o segundo parâmetro da query
                } // fim do bloco if/else          
                
                try(ResultSet rs = stmt.executeQuery()){
                    List<Devolucao> devolucoes = new ArrayList<>();
                    
                    while(rs.next()){
                        Devolucao d = new Devolucao();
                        d.setId(rs.getInt("id"));
                        d.setCodigo(rs.getInt("codigo"));
                        d.setData(rs.getString("data"));
                        d.setProdutos(null);
                        d.setObservacao(rs.getString("observacao"));
                        devolucoes.add(d);
                    } // fim do bloco while
                    
                    return devolucoes;
                } // fim do bloco try
            } // fim do bloco try
        }
        catch(SQLException e){
            throw new DAOException(e);
        } // fim do bloco try/catch
        
    } // fim do método filter
    
} // fim da classe DevolucaoDAO
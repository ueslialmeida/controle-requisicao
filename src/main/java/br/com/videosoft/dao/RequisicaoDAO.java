package br.com.videosoft.dao;

import br.com.videosoft.model.Produto;
import br.com.videosoft.model.Requisicao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por manipular os dados de uma Requisição no banco de dados
 * a classe implementa uma interface que assina os métodos padrões disponíveis
 * para inserção, recuperação de dados.
 * @author Uesli Almeida
 */
public class RequisicaoDAO implements MovimentacaoDAO {

    /**
     * Recupera as devoluções existentes no banco de dados.
     * @return lista de Requisicao
     */
    @Override
    public List<Requisicao> load() {
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
     * Grava uma requisição no banco de dados.
     * @param o objeto Requisicao
     * @param products lista de Produto
     * @param edit flag de modo edição
     */
    @Override
    public void store(Object o, List<Produto> products, boolean edit) {
        
        try(Connection conn = ConnectionFactory.getConnection()){
            
            conn.setAutoCommit(false);
            
            String query = "";
            
            if(!edit){ // se não for uma edição faz um insert
                query = "INSERT INTO Requisicao "
                    + "(codigo, data, responsavel, observacao)"
                    + " VALUES(?,?,?,?);";
            
                long lastInsertedId = 0; // último ID inserido no BD
            
                try(PreparedStatement stmt = conn.prepareStatement(query)){
                    Requisicao r = (Requisicao) o;
                
                    // seta os parâmetros da string query
                    stmt.setInt(1, r.getCodigo());
                    stmt.setDate(2, java.sql.Date.valueOf(r.getData()));
                    stmt.setString(3, r.getResponsavel());
                    stmt.setString(4, r.getObservacao());
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
                produtoDao.storeProdutoRequisicao(lastInsertedId, products);
            } // fim do bloco if
            else{
                query = "UPDATE Requisicao "
                    + "SET observacao=? WHERE id=?;";
                
                try(PreparedStatement stmt = conn.prepareStatement(query)){
                    Requisicao r = (Requisicao) o;
                
                    // seta os parâmetro da string query
                    stmt.setString(1, r.getObservacao());
                    stmt.setLong(2, r.getId());
                    stmt.executeUpdate(); // executa a query no banco
                    
                    conn.commit();
                } // fim do bloco try
            } // fim do bloco else
        }
        catch(SQLException e){
            throw new DAOException(e);
        } // fim do bloco try/catch
        
    } // fim do método store

    /**
     * Recupera os dados no BD a partir de parâmetros para filtragem.
     * @param date data da requisição
     * @param text algum termo como responsável ou produto
     * @return lista de Requisicao
     */
    @Override
    public List<Requisicao> filter(String date, String text) {
        
        try(Connection conn = ConnectionFactory.getConnection()){
            
            String selectQuery = "SELECT * FROM Requisicao r";
            
            if(date != null || text != null){ // verifica se foi informado uma data ou termo
                selectQuery += " LEFT JOIN ItemRequisicao i ON r.id = i.id_requisicao "
                        + "WHERE r.data = ? "
                        + "OR r.responsavel LIKE UPPER(?) "
                        + "OR i.descricao LIKE UPPER(?);";
            } // fim do bloco if
            
            try(PreparedStatement stmt = conn.prepareStatement(selectQuery)){
                
                if(date != null || text != null){ // verifica se foi informado uma data ou termo
                    
                    if(date != null){ // verifica se foi informada uma data
                        stmt.setDate(1, java.sql.Date.valueOf(date)); // seta o parâmetro da query
                    }
                    else{
                        stmt.setDate(1, null); // se não foi informada uma data o parâmetro é nulo
                    } // fim do bloco if/else
                    
                    stmt.setString(2, "%" + text + "%"); // seta o segundo parâmetro da query
                    stmt.setString(3, "%" + text + "%"); // seta o terceiro parâmetro da query
                    
                } // fim do bloco if    
                
                try(ResultSet rs = stmt.executeQuery()){
                    List<Requisicao> requisicoes = new ArrayList<>();
                    
                    while(rs.next()){
                        Requisicao r = new Requisicao();
                        r.setId(rs.getInt("id"));
                        r.setCodigo(rs.getInt("codigo"));
                        r.setData(rs.getString("data"));
                        r.setResponsavel(rs.getString("responsavel"));
                        r.setProdutos(null);
                        r.setObservacao(rs.getString("observacao"));
                        requisicoes.add(r);
                    } // fim do bloco while
                    
                    return requisicoes;
                } // fim do bloco try
            } // fim do bloco try
        }
        catch(SQLException e){
            throw new DAOException(e);
        } // fim do bloco try/catch
        
    } // fim do método filter
    
} // fim da classe RequisicaoDAO
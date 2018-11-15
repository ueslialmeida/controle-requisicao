package br.com.videosoft.dao;

import br.com.videosoft.model.Produto;
import br.com.videosoft.util.NumberUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por manipular os dados de um Produto no banco de dados
 * a classe implementa uma interface que assina os métodos padrões disponíveis
 * para inserção, recuperação de dados.
 * @author Uesli Almeida
 */
public class ProdutoDAO implements MovimentacaoDAO{

    /**
     * Está classe não implementa este método. 
     * @return
     */
    @Override
    public List<Produto> load() {
        return null;
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
     * Recupera os produtos associados a uma Requisição.
     * @param idRequisicao ID de uma requisição existente no BD
     * @return lista de Produto
     */
    public List<Produto> loadProdutoRequisicao(long idRequisicao){
        
        try(Connection conn = ConnectionFactory.getConnection()){
            String query = "SELECT * FROM ItemRequisicao WHERE id_requisicao=?;";
            
            try(PreparedStatement stmt = conn.prepareStatement(query)){
                stmt.setLong(1, idRequisicao); // seta o parâmetro da query
                
                try(ResultSet rs = stmt.executeQuery()){
                    List<Produto> produtos = new ArrayList<>();
                    
                    while(rs.next()){
                        Produto p = new Produto();
                        
                        p.setId(rs.getLong("id"));
                        p.setIdRequisicao(rs.getLong("id_requisicao"));
                        p.setCodigoReduzido(rs.getLong("cod_reduzido"));
                        p.setDescricao(rs.getString("descricao"));
                        p.setQuantidade(rs.getInt("qtde"));
                        produtos.add(p);
                    } // fim do bloco while
                    
                    return produtos;
                } // fim do bloco try
            } // fim do bloco try
        }
        catch(SQLException e){
            throw new DAOException(e);
        } // fim do bloco try/catch
        
    } // fim do método loadProdutoRequisicao
    
    /**
     * Recupera os produtos associados a uma Requisição.
     * @param idDevolucao ID de uma devolução existente no BD
     * @return lista de Produto
     */
    public List<Produto> loadProdutoDevolucao(long idDevolucao){
        
        try(Connection conn = ConnectionFactory.getConnection()){
            String selectQuery = "SELECT * FROM ItemDevolucao WHERE id_devolucao=?;";
            
            try(PreparedStatement stmt = conn.prepareStatement(selectQuery)){
                stmt.setLong(1, idDevolucao); // seta o parâmetro da query
                
                try(ResultSet rs = stmt.executeQuery()){
                    List<Produto> produtos = new ArrayList<>();
                    
                    while(rs.next()){
                        Produto p = new Produto();
                        
                        p.setId(rs.getLong("id"));
                        p.setIdRequisicao(rs.getLong("id_devolucao"));
                        p.setCodigoReduzido(rs.getLong("cod_reduzido"));
                        p.setDescricao(rs.getString("descricao"));
                        p.setQuantidade(rs.getInt("qtde"));
                        produtos.add(p);
                    } // fim do bloco while
                    
                    return produtos;
                } // fim do bloco try
            } // fim do bloco try
        }
        catch(SQLException e){
            throw new DAOException(e);
        } // fim do bloco try/catch
        
    } // fim do método loadProdutoDevolucao

    /**
     * Está clasee não implementea este método.
     * @param o
     * @param items
     * @param edit 
     */
    @Override
    public void store(Object o, List<Produto> items, boolean edit) {}
    
    /**
     * Grava no banco de dados os produtos associados a uma requisição.
     * @param idRequisicao ID da requisição adicionada
     * @param products lista de Produto
     */
    public void storeProdutoRequisicao(long idRequisicao, List<Produto> products){
        
        try(Connection conn = ConnectionFactory.getConnection()){
            conn.setAutoCommit(false);
            
            String query = "INSERT INTO ItemRequisicao "
                    + "(id_requisicao, cod_reduzido, descricao, qtde)"
                    + " VALUES (?,?,?,?);";
            
            try(PreparedStatement stmt = conn.prepareStatement(query)){
                for(Produto p:products){ // itera na lista de produtos
                    
                    // seta os parâmetros da query
                    stmt.setLong(1, idRequisicao);
                    stmt.setLong(2, p.getCodigoReduzido());
                    stmt.setString(3, p.getDescricao());
                    stmt.setInt(4, p.getQuantidade());
                    stmt.executeUpdate(); // executa a query
                } // fim do bloco for
            } // fim do bloco try
            
            // Atualiza a tabela de movimentação de produtos
            String mergeQuery = "MERGE INTO MovimentacaoProduto "
                    + "(cod_reduzido, descricao, qtde) KEY(cod_reduzido) "
                    + "VALUES (?, ?, (SELECT IFNULL(SELECT qtde FROM MovimentacaoProduto WHERE cod_reduzido=?, 0) + ?));";
            
            try(PreparedStatement stmt = conn.prepareStatement(mergeQuery)){
                for(Produto p:products){ // itera na lista de produtos
                    
                    // seta os parâmetros da query
                    stmt.setLong(1, p.getCodigoReduzido());
                    stmt.setString(2, p.getDescricao());
                    stmt.setLong(3, p.getCodigoReduzido());
                    stmt.setInt(4, p.getQuantidade());
                    stmt.executeUpdate(); // executa a query
                } // fim do bloco for
            } // fim do bloco try
            
            conn.commit();
        }
        catch(SQLException e){
            throw new DAOException(e);
        } // fim do bloco try/catch
        
    } // fim do método storeProdutoRequisicao
    
    /**
     * Grava no banco de dados os produtos associados a uma requisição.
     * @param idDevolucao ID da devolução adicionada
     * @param products lista de Produto
     */
    public void storeProdutoDevolucao(long idDevolucao, List<Produto> products){
        
        try(Connection conn = ConnectionFactory.getConnection()){
            conn.setAutoCommit(false);
            
            String query = "INSERT INTO ItemDevolucao "
                    + "(id_devolucao, cod_reduzido, descricao, qtde)"
                    + "VALUES (?,?,?,?);";
            
            try(PreparedStatement stmt = conn.prepareStatement(query)){
                for(Produto p:products){// itera na lista de produtos
                    
                    // seta os parâmetros da query
                    stmt.setLong(1, idDevolucao);
                    stmt.setLong(2, p.getCodigoReduzido());
                    stmt.setString(3, p.getDescricao());
                    stmt.setInt(4, p.getQuantidade());
                    stmt.executeUpdate(); // executa a query
                } // fim do bloco for
            } // fim do bloco try
            
            // Atualiza a tabela de movimentação de produtos
            String mergeQuery = "MERGE INTO MovimentacaoProduto "
                    + "(cod_reduzido, descricao, qtde) KEY(cod_reduzido) "
                    + "VALUES (?, ?, (SELECT IFNULL(SELECT qtde FROM MovimentacaoProduto WHERE cod_reduzido=?, 0) - ?));";
            
            try(PreparedStatement stmt = conn.prepareStatement(mergeQuery)){
                for(Produto p:products){ // itera na lista de produtos
                    
                    // seta os parâmetros da query
                    stmt.setLong(1, p.getCodigoReduzido());
                    stmt.setString(2, p.getDescricao());
                    stmt.setLong(3, p.getCodigoReduzido());
                    stmt.setInt(4, p.getQuantidade());
                    stmt.executeUpdate(); // executa a query
                } // fim do bloco for
            } // fim do bloco try
            
            conn.commit();
        }
        catch(SQLException e){
            throw new DAOException(e);
        } // fim do bloco try/catch
        
    } // fim do método storeProdutoDevolucao
    
    /**
     * Recupera todos os produtos com informações de saldo.
     * @return lista de Produto
     */
    public List<Produto> getMovimentacaoProduto(){
        
        List<Produto> produtos = new ArrayList<>();
                    
        try(Connection conn = ConnectionFactory.getConnection()){
            String query = "SELECT * FROM MovimentacaoProduto;";
            
            try(PreparedStatement stmt = conn.prepareStatement(query)){
                
                try(ResultSet rs = stmt.executeQuery()){           
                    
                    while(rs.next()){
                        Produto p = new Produto();
                        
                        p.setCodigoReduzido(rs.getLong("cod_reduzido"));
                        p.setDescricao(rs.getString("descricao"));
                        p.setQuantidade(rs.getInt("qtde"));
                        produtos.add(p);
                    } // fim do bloco while

                } // fim do bloco try
            } // fim do bloco try

            return produtos;
        }
        catch(SQLException e){
            throw new DAOException(e);
        } // fim do bloco try/catch
        
    } // fim do método getMovimentacaoProduto
    
    /**
     * Recupera todos os produtos com informações de saldo a partir de um filtro.
     * @param text termo passado para filtragem (código produto ou descrição)
     * @return lista de Produto
     */
    public List<Produto> getMovimentacaoProduto(String text){
        
        List<Produto> produtos = new ArrayList<>();
                    
        try(Connection conn = ConnectionFactory.getConnection()){
            String query = "SELECT * FROM MovimentacaoProduto "
                    + "WHERE cod_reduzido=? OR descricao LIKE UPPER(?);";
            
            try(PreparedStatement stmt = conn.prepareStatement(query)){
                
                if(NumberUtils.isInteger(text)){ // verifica se o que foi passado foi um número
                    stmt.setInt(1, Integer.parseInt(text)); // converte para int e seta o parâmetro
                }
                else{
                    stmt.setInt(1, 0); // se não foi passado um número passa um 0 como padrão
                }
                
                stmt.setString(2, "%" + text + "%"); // seta o segundo parâmtro da query
                
                try(ResultSet rs = stmt.executeQuery()){           
                    
                    while(rs.next()){
                        Produto p = new Produto();
                        
                        p.setCodigoReduzido(rs.getLong("cod_reduzido"));
                        p.setDescricao(rs.getString("descricao"));
                        p.setQuantidade(rs.getInt("qtde"));
                        produtos.add(p);
                    } // fim do bloco while

                } // fim do bloco try
            } // fim do bloco try

            return produtos;
        }
        catch(SQLException e){
            throw new DAOException(e);
        } // fim do bloco try/catch
        
    } // fim do método getMovimentacaoProduto
    
    /**
     * Verifica se o produto que está sendo devolvido foi requisitado anteriormente 
     * ou se a quantidade informada não é superior a que foi requisitada.
     * @param codigoReduzido código do produto
     * @param quantidade quantidade informada na devolução
     * @return 
     */
    public boolean isAvailable(long codigoReduzido, int quantidade){
        
         try(Connection conn = ConnectionFactory.getConnection()){
            String query = "SELECT qtde FROM MovimentacaoProduto "
                    + "WHERE cod_reduzido=?;";
            
            try(PreparedStatement stmt = conn.prepareStatement(query)){
                
                stmt.setLong(1, codigoReduzido);
                
                try(ResultSet rs = stmt.executeQuery()){           
                    
                    if(rs.next())
                        if(quantidade <= rs.getInt(1))
                            return true;
                } // fim do bloco try
            } // fim do bloco try

            return false;
        }
        catch(SQLException e){
            throw new DAOException(e);
        } // fim do bloco try/catch
         
    } // fim do método isAvailable

    /**
     * Esta classe não implementa este método.
     * @param date
     * @param text
     * @return 
     */
    @Override
    public List<Produto> filter(String date, String text) {
        return null;
    } // fim do método filter
    
} // fim da classe ProdutoDAO
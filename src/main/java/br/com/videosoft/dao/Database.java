package br.com.videosoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Classe responsável por criar o banco de dados.
 * @author Uesli Almeida
 */
public class Database {

    /**
     * Cria o banco de dados da aplicação.
     */
    public static void createDatabase() {

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);

            String query = ""
                    + "CREATE TABLE IF NOT EXISTS Requisicao(\n"
                    + "id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,\n"
                    + "codigo BIGINT UNSIGNED NOT NULL,\n"
                    + "data DATE NOT NULL,\n"
                    + "responsavel VARCHAR(50) NOT NULL,\n"
                    + "observacao TEXT\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS Devolucao(\n"
                    + "id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,\n"
                    + "codigo BIGINT UNSIGNED NOT NULL,\n"
                    + "data DATE NOT NULL,\n"
                    + "observacao TEXT\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS ItemRequisicao(\n"
                    + "id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,\n"
                    + "id_requisicao BIGINT UNSIGNED NOT NULL,\n"
                    + "cod_reduzido BIGINT UNSIGNED NOT NULL,\n"
                    + "descricao VARCHAR(100) NOT NULL,\n"
                    + "qtde INT UNSIGNED NOT NULL,\n"
                    + "FOREIGN KEY (id_requisicao) REFERENCES Requisicao(id)\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS ItemDevolucao(\n"
                    + "id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,\n"
                    + "id_devolucao BIGINT UNSIGNED NOT NULL,\n"
                    + "cod_reduzido BIGINT UNSIGNED NOT NULL,\n"
                    + "descricao VARCHAR(100) NOT NULL,\n"
                    + "qtde INT UNSIGNED NOT NULL,\n"
                    + "FOREIGN KEY (id_devolucao) REFERENCES Devolucao(id)\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS MovimentacaoProduto(\n"
                    + "cod_reduzido BIGINT UNSIGNED NOT NULL PRIMARY KEY,\n"
                    + "descricao VARCHAR(100) NOT NULL,\n"
                    + "qtde INT UNSIGNED NOT NULL\n"
                    + ");";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.execute();
            }

        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } // fim do bloco try/catch
        
    } // fim do método createDatabase
    
} // fim da classe Database
package br.com.videosoft.dao;

/**
 * Classe responsável por lançar exceções realcionadas a manipulação de dados
 * no banco de dados.
 * @author Uesli Almeida
 */
public class DAOException extends RuntimeException{
    
    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
} // fim da classe DAOException

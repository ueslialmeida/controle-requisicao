package br.com.videosoft.dao;

import br.com.videosoft.model.Produto;
import java.util.List;

/**
 * Iterface para acesso e armazenamento dos dados da apliacação.
 * @author Uesli Almeida
 */
public interface MovimentacaoDAO {
    
    /**
     * Recupera os objetos existentes no banco de dados.
     * @return lista de objetos
     */
    List<?> load();
    
    /**
     * Recupera os produtos associados a um objeto.
     * @return 
     */
    List<Produto> loadProdutos();
    
    /**
     * Grava o objeto e os produtos no banco de dados.
     * @param o objeto
     * @param products lista de Produto 
     * @param edit flag que indica modo edição
     */
    void store(Object o, List<Produto> products, boolean edit);
    
    /**
     * Recupera informações filtradas com base em critérios.
     * @param date pesquisa requisições/devoluções por data
     * @param text pesquisa requisições/devoluções por texto
     * @return lista de objetos
     */
    List<?> filter(String date, String text);
    
} // fim da interface MovimentacaoDAO
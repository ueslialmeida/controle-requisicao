package br.com.videosoft.model;

/**
 * Classe modelo para objetos Produto
 * @author Uesli Almeida
 */
public class Produto {

    /**
     * Atributos da classe
     */
    private long id;
    private long idRequisicao;
    private long codigoReduzido;
    private String descricao;
    private int quantidade;
    
    /**
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * @return idRequisicao
     */
    public long getIdRequisicao() {
        return idRequisicao;
    }

    /**
     * @param idRequisicao
     */
    public void setIdRequisicao(long idRequisicao) {
        this.idRequisicao = idRequisicao;
    }

    /**
     * @return codigoReduzido
     */
    public long getCodigoReduzido() {
        return codigoReduzido;
    }

    /**
     * @param codigoReduzido
     */
    public void setCodigoReduzido(long codigoReduzido) {
        this.codigoReduzido = codigoReduzido;
    }

    /**
     * @return descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
} // fim da classe Produto
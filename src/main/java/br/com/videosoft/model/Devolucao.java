package br.com.videosoft.model;

import java.util.List;

/**
 * Classe modelo para objetos Devolucao
 * @author Uesli Almeida
 */
public class Devolucao {

    /**
     * Atributos da classe
     */
    private long id;
    private int codigo;
    private String data;
    private List<Produto> produtos;
    private String observacao;
    
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
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return produtos
     */
    public List<Produto> getProdutos() {
        return produtos;
    }

    /**
     * @param produtos
     */
    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    /**
     * @return observacao
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * @param observacao
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
} // fim da classe Devolucao
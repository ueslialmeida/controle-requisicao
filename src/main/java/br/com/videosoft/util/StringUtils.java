package br.com.videosoft.util;

/**
 * Classe que contém métodos para se trabalhar com textos.
 * @author Uesli Almeida
 */
public class StringUtils {
    
    /**
     * Verifica se uma string passada está vazia.
     * @param str string a ser verificada
     * @return 
     */
    public static boolean isEmpty(String str){
        
        if(str == null)
            return false;
        
        return str.trim().length() == 0;
        
    } // fim do método isEmpty
    
    /**
     * Recupera o separador de linha do S.O.
     * @return separador de linha
     */
    public static String newLine(){
        return System.getProperty("line.separator");
    } // fim do método newLine
    
} // fim da classe StringUtils
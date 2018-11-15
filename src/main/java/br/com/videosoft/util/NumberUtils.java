package br.com.videosoft.util;

/**
 * Classe que contém métodos para se trabalhar com números.
 * @author Uesli Almeida
 */
public class NumberUtils {
    
    /**
     * Testa se é possível converter a string passada para int.
     * @param str string contendo número para conversão
     * @return 
     */
    public static boolean isInteger(String str){
        
        if(!StringUtils.isEmpty(str)){
            try{
                Integer.parseInt(str);
                return true;
            }
            catch(NumberFormatException e){
                // não trata a exception
            } // fim do bloco try/catch
        } // fim do bloco if
        
        return false;
    } // fim do método isInteger
    
} // fim da classe NumberUtils
package br.com.videosoft.main;

import br.com.videosoft.dao.Database;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principal da aplicação, contém métodos de inicialização da aplicação.
 * @author Uesli Almeida
 */
public class Main extends Application {

    /**
     * Inicializa a interface gráfica da aplicação.
     * @param stage janela da aplicação
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        // chama o método que cria as tabelas do banco de dados
        Database.createDatabase();
        
        // carrega o arquivo de interface gráfica (FXML) da aplicação
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/TelaPrincipal.fxml"));
        
        // cria uma 'cena' que vai ser adicionada à janela
        Scene scene = new Scene(root);
        
        // carrega o arquivo CSS (estilos) personalizado para a 'cena'
        scene.getStylesheets().add("/styles/telaprincipal.css");
        
        stage.setTitle("Controle de Requisição de Componentes");
        stage.setMinWidth(1000); // largura mínima da janeja
        stage.setMinHeight(600); // altura mínima da janela
        stage.setScene(scene); // seta a 'cena' na janela
        stage.show(); // exibe a janela
    } // fim do método start

    /**
     * Método de entrada da aplicação.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    } // fim do método main
    
} // fim da classe Main
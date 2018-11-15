package br.com.videosoft.controller;

import br.com.videosoft.dao.DevolucaoDAO;
import br.com.videosoft.dao.ProdutoDAO;
import br.com.videosoft.dao.RequisicaoDAO;
import br.com.videosoft.model.Devolucao;
import br.com.videosoft.model.Produto;
import br.com.videosoft.model.Requisicao;
import br.com.videosoft.util.StringUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

/**
 * Controller da tela principal da aplicação.
 * @author Uesli Almeida
 */
public class TelaPrincipalController {

    /**
     * Controles das abas
     */
    @FXML
    private Tab tabRequisicao;
    @FXML
    private Tab tabDevolucao;
    @FXML
    private Tab tabMovimentacaoDeProdutos;
    
    /**
     * Controles da aba Requisição
     */
    @FXML
    private DatePicker dtpPesquisaRequisicao;
    @FXML
    private TextField txtPesquisaRequisicao;
    @FXML
    private Button btnPesquisarRequisicao;
    @FXML
    private Button btnAddRequisicao;
    @FXML
    private Button btnEditarRequisicao;
    @FXML
    private Button btnSalvarRequisicao;
    @FXML
    private Button btnCancelarRequisicao;
    @FXML
    private TableView<Requisicao> tbvRequisicao;
    @FXML
    private TableColumn<Requisicao, Long> colIdRequisicao;
    @FXML
    private TableColumn<Requisicao, Long> colCodRequisicao;
    @FXML
    private TableColumn<Requisicao, String> colDataRequisicao;
    @FXML
    private TableColumn<Requisicao, String> colResponsavel;
    @FXML
    private TextField txtIdRequisicao;
    @FXML
    private TextField txtCodRequisicao;
    @FXML
    private DatePicker dtpDataRequisicao;
    @FXML
    private TextField txtResponsavel;
    @FXML
    private TextField txtCodigoProdutoRequisicao;
    @FXML
    private TextField txtQuantidadeProdutoRequisicao;
    @FXML
    private TextField txtDescricaoProdutoRequisicao;
    @FXML
    private Button btnAddProdutoRequisicao;
    @FXML
    private TableView<Produto> tbvProdutoRequisicao;
    @FXML
    private TableColumn<Produto, Long> colCodProdutoRequisicao;
    @FXML
    private TableColumn<Produto, Integer> colQtdeProdutoRequisicao;
    @FXML
    private TableColumn<Produto, String> colDescricaoProdutoRequisicao;
    @FXML
    private Button btnRemoverProdutoRequisicao;
    @FXML
    private TextArea txtObsRequisicao;
    
    /**
     * Controles da aba Devolução
     */
    @FXML
    private DatePicker dtpPesquisaDevolucao;
    @FXML
    private TextField txtPesquisaDevolucao;
    @FXML
    private Button btnPesquisarDevolucao;
    @FXML
    private Button btnAddDevolucao;
    @FXML
    private Button btnEditarDevolucao;
    @FXML
    private Button btnSalvarDevolucao;
    @FXML
    private Button btnCancelarDevolucao;
    @FXML
    private TableView<Devolucao> tbvDevolucao;
    @FXML
    private TableColumn<Requisicao, Long> colIdDevolucao;
    @FXML
    private TableColumn<Requisicao, Long> colCodDevolucao;
    @FXML
    private TableColumn<Requisicao, String> colDataDevolucao;
    @FXML
    private TextField txtIdDevolucao;
    @FXML
    private TextField txtCodDevolucao;
    @FXML
    private DatePicker dtpDataDevolucao;
    @FXML
    private TextField txtCodigoProdutoDevolucao;
    @FXML
    private TextField txtQuantidadeProdutoDevolucao;
    @FXML
    private TextField txtDescricaoProdutoDevolucao;
    @FXML
    private Button btnAddProdutoDevolucao;
    @FXML
    private TableView<Produto> tbvProdutoDevolucao;
    @FXML
    private TableColumn<Produto, Long> colCodProdutoDevolucao;
    @FXML
    private TableColumn<Produto, Integer> colQtdeProdutoDevolucao;
    @FXML
    private TableColumn<Produto, String> colDescricaoProdutoDevolucao;
    @FXML
    private Button btnRemoverProdutoDevolucao;
    @FXML
    private TextArea txtObsDevolucao;
    
    /**
     * Controles da aba Movimentação de Produtos
     */
    @FXML
    private TextField txtFiltroProdutos;
    @FXML
    private Button btnFiltrarProdutos;
    @FXML
    private TableView<Produto> tbvProdutosSaldo;
    @FXML
    private TableColumn<Produto, Long> colCodReduzidoProdutoSaldo;
    @FXML
    private TableColumn<Produto, String> colDescricaoProdutoSaldo;
    @FXML
    private TableColumn<Produto, Integer> colSaldo;
    
    /**
     * Propriedade que indica modo de edição
     */
    private boolean editMode = false;
    
    /**
     * Requisição ativa no momento (selecionada ou edição)
     */
    private Requisicao currentRequisicao;
    
    /**
     * Objeto para manipulação de dados
     */
    private RequisicaoDAO requisicaoDao;
    
    /**
     * Devolução ativa no momento (selecionada ou edição)
     */
    private Devolucao currentDevolucao;
    
    /**
     * Objeto para manipulação de dados
     */
    private DevolucaoDAO devolucaoDao;
    
    /**
     * Objeto para manipulação de dados
     */
    private ProdutoDAO produtoDao;
    
    /**
     * Lista de produtos da requisição
     */
    private List<Produto> produtosRequisicao;
    
    /**
     * Lista de produtos da devolução
     */
    private List<Produto> produtosDevolucao;
    
    /**
     * Inicializa a classe controladora.
     * Inicializa alguns controladores e objetos
     */
    @FXML
    public void initialize() {

        // Objetos para manipulação de dados
        requisicaoDao = new RequisicaoDAO();
        devolucaoDao = new DevolucaoDAO();
        produtoDao = new ProdutoDAO();
        produtosRequisicao = new ArrayList<>();
        produtosDevolucao = new ArrayList<>();
        
        // Carrega as informações do banco de dados
        loadRequisicao(false);
        loadDevolucao(false);
        loadMovimentacaoProdutos(false);
        
        // Chama o método que preenche os campos ao selecionar uma requisição
        tbvRequisicao.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> selectItemTableViewRequisicoes(newValue));
        
        // Chama o método que preenche os campos ao selecionar uma devolução
        tbvDevolucao.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> selectItemTableViewDevolucoes(newValue));
        
        // Desabilita os componentes na inicialização
        normalModeTabRequisicao();
        normalModeTabDevolucao();
        
        // Seta a data atual como padrão para o campo
        dtpDataRequisicao.setValue(LocalDate.now());
        dtpDataDevolucao.setValue(LocalDate.now());
        
        /**
         * Chama o método que checa se existe caracteres não permitidos
         * em campos numericos
         */
        checkNumericField(txtCodRequisicao);
        checkNumericField(txtCodigoProdutoRequisicao);
        checkNumericField(txtQuantidadeProdutoRequisicao);
        checkNumericField(txtCodDevolucao);
        checkNumericField(txtCodigoProdutoDevolucao);
        checkNumericField(txtQuantidadeProdutoDevolucao);
        

         // Chama o método que transforma o texto para uppercase
        setTextFieldToUpper(txtPesquisaRequisicao);
        setTextFieldToUpper(txtPesquisaDevolucao);
        setTextFieldToUpper(txtResponsavel);
        setTextFieldToUpper(txtDescricaoProdutoRequisicao);
        setTextFieldToUpper(txtDescricaoProdutoDevolucao);
        setTextFieldToUpper(txtFiltroProdutos);
        
    } // fim do método initialize
    
    /**
     * Manipula o botão Pesquisar Requisição.
     * @param event 
     */
    @FXML
    public void handleButtonPesquisarRequisicao(ActionEvent event){
        
        if(dtpPesquisaRequisicao.getValue() == null && 
                txtPesquisaRequisicao.getText() == null){
            loadRequisicao(false); // se os campos estiverem vazios carrega tudo
        }
        else{
            loadRequisicao(true); // se os campos estiverem preenchidos faz a busca
        } // fim do bloco if/else
        
    } // fim do método handleButtonPesquisarRequisicao
    
    /**
     * Manipula o botão Adicionar Requisição.
     * @param event 
     */
    @FXML
    public void handleButtonAddRequisicao(ActionEvent event){
        
        clearFields();
        editModeTabRequisicao(true); // trava os controles da Devolução
        
    } // fim do método handleButtonAddRequisicao
    
    /**
     * Manipula o botão Editar Requisição.
     * @param event 
     */
    @FXML
    public void handleButtonEditarRequisicao(ActionEvent event){
        
        String errorMessage = "";
  
        if(currentRequisicao != null && !StringUtils.isEmpty(txtIdRequisicao.getText())){
            editModeTabRequisicao(false);
            editMode = true;
        }
        else{
            errorMessage = "Selecione uma requisição para editar.";
            showValidationError(errorMessage);
        } // fim do bloco if/else
        
    } // fim do método handleButtonEditarRequisicao
    
    /**
     * Manipula o botão Salvar Requisição.
     * @param event 
     */
    @FXML
    public void handleButtonSalvarRequisicao(ActionEvent event){
        
        String errorMessage = validateFormRequisicao();
        
        if(!errorMessage.isEmpty()){
            showValidationError(errorMessage);
            return;
        }
        
        currentRequisicao = new Requisicao();
        
        // se estiver em modo de edição passa o ID contido no campo ID
        if(editMode)
            currentRequisicao.setId(Integer.parseInt(txtIdRequisicao.getText()));
        
        currentRequisicao.setCodigo(Integer.parseInt(txtCodRequisicao.getText()));
        currentRequisicao.setData(dtpDataRequisicao.getValue().toString());
        currentRequisicao.setResponsavel(txtResponsavel.getText());
        currentRequisicao.setProdutos(tbvProdutoRequisicao.getItems());
        currentRequisicao.setObservacao(txtObsRequisicao.getText());
        
        // chama o método que grava a requisição passando os produtos requisitados
        requisicaoDao.store(currentRequisicao, tbvProdutoRequisicao.getItems(), editMode);
        
        loadRequisicao(false); // atualiza o tableview de requisições
        loadMovimentacaoProdutos(false); // atualiza o tableview de saldo de produtos
        normalModeTabRequisicao(); // libera os controles para modo normal
        
    } // fim do método handleButtonSalvarRequisicao
    
    /**
     * Manipula o botão Cancelar a edição/adição.
     * @param event 
     */
    @FXML
    public void handleButtonCancelarRequisicao(ActionEvent event){
        
        normalModeTabRequisicao(); // libera os controles
        
    } // fim do método handleButtonCancelarRequisicao
    
    /**
     * Manipula o botão Adicionar Produto da Requisição 
     * não é possível adicionar produto de uma requisição já realizada.
     * @param event 
     */
    @FXML
    public void handleButtonAddProdutoRequisicao(ActionEvent event){
        
        // chama o método que valida o formulário de produtos
        String errorMessage = validateFormProdutoRequisicao();
        
        // se houver mensagem de erro ela é exibida e o método encerra
        if(!errorMessage.isEmpty()){
            showValidationError(errorMessage);
            return;
        }
        
        Produto p = new Produto();
        p.setCodigoReduzido(Integer.parseInt(txtCodigoProdutoRequisicao.getText()));
        p.setDescricao(txtDescricaoProdutoRequisicao.getText());
        p.setQuantidade(Integer.parseInt(txtQuantidadeProdutoRequisicao.getText()));
        
        // passa um produto para o método que adiciona no tableview
        setTableViewProdutoRequisicao(p);
        clearFormProduto(); // limpa o formulário de produtos
        
    } // fim do método handleButtonAddProdutoRequisicao
    
    /**
     * Manipula o botão Remover Produto da Requisição
     * não é possível remover produtos de uma requisição já realizada.
     * @param event 
     */
    @FXML
    public void handleButtonRemoverProdutoRequisicao(ActionEvent event){
        
        // armazena o indice da linha selecionada
        int index = tbvProdutoRequisicao.getSelectionModel().getSelectedIndex();
        
        if(index >= 0){
            // remove o produto da lista passando o objeto recuperado do tableview
            produtosRequisicao.remove(
                    tbvProdutoRequisicao.getSelectionModel()
                    .getSelectedItem()); 
            tbvProdutoRequisicao.getItems().remove(index); // remove o objeto do tableview
            tbvProdutoRequisicao.refresh(); // atualiza o tableview
        } // fim do bloco if
        
    } // fim do método handleButtonRemoverProdutoRequisicao
    
    /**
     * Manipula o botão Pesquisar Devolução.
     * @param event 
     */
    @FXML
    public void handleButtonPesquisarDevolucao(ActionEvent event){
        
        if(dtpPesquisaDevolucao.getValue() == null && 
                txtPesquisaDevolucao.getText() == null){
            loadDevolucao(false); // retorna resultados sem filtro
        }
        else{
            loadDevolucao(true); // retorna resultados filtrados
        } // fim do bloco if/else
        
    } // fim do método handleButtonPesquisarDevolucao
    
    /**
     * Manipula o butão Adicionar Devolução.
     * @param event 
     */
    @FXML
    public void handleButtonAddDevolucao(ActionEvent event){
        
        clearFields();
        editModeTabDevolucao(true); // chama o método que trava os controles da aba requisição
        
    } // fim do método handleButtonAddDevolucao
    
    /**
     * Manipula o botão Editar Devolução.
     * @param event 
     */
    @FXML
    public void handleButtonEditarDevolucao(ActionEvent event){
        
        String errorMessage = "";
        
        // se houve uma devolução selecionada então o modo edição é ativado
        if(currentDevolucao != null && !StringUtils.isEmpty(txtIdDevolucao.getText())){
            editModeTabDevolucao(false);
            editMode = true;
        }
        else{
            errorMessage = "Selecione uma devolução para editar.";
            showValidationError(errorMessage); // exibe a mensagem de erro
        } // fim do bloco if/else
        
    } // fim do método handleButtonEditarDevolucao
    
    /**
     * Manipula o botão Salvar Devolução.
     * @param event 
     */
    @FXML
    public void handleButtonSalvarDevolucao(ActionEvent event){
        
        String errorMessage = validateFormDevolucao();
        
        if(!errorMessage.isEmpty()){
            showValidationError(errorMessage);
            return;
        }
        
        currentDevolucao = new Devolucao();
        
        // se estiver em modo de edição passa o ID contido no campo ID
        if(editMode)
            currentDevolucao.setId(Integer.parseInt(txtIdDevolucao.getText()));
        
        currentDevolucao.setCodigo(Integer.parseInt(txtCodDevolucao.getText()));
        currentDevolucao.setData(dtpDataDevolucao.getValue().toString());
        currentDevolucao.setProdutos(tbvProdutoDevolucao.getItems());
        currentDevolucao.setObservacao(txtObsDevolucao.getText());
        
        // chama o método que grava a devolução no banco de dados passando o objeto e os produtos
        devolucaoDao.store(currentDevolucao, tbvProdutoDevolucao.getItems(), editMode);
        
        loadDevolucao(false); // carrega as devoluções sem filtro
        loadMovimentacaoProdutos(false); // carrega a movimentação de produtos sem filtro
        normalModeTabDevolucao(); // habilita os controles
        
    } // fim do método handleButtonSalvarDevolucao
    
    /**
     * Manipula o botão Cancelar Devolução.
     * @param event 
     */
    @FXML
    public void handleButtonCancelarDevolucao(ActionEvent event){
        
        normalModeTabDevolucao(); // habilita os controles
        
    } // fim do método handleButtonCancelarDevolucao
    
    /**
     * Manipula o botão Adicionar Produtos Devolução
     * não é possível adicionar um produto em uma devolução já realizada.
     * @param event 
     */
    @FXML
    public void handleButtonAddProdutoDevolucao(ActionEvent event){
        
        String errorMessage = validateFormProdutoDevolucao();
        
        // se houver mensagens de erro ela é exibida e o método é encerrado
        if(!errorMessage.isEmpty()){
            showValidationError(errorMessage);
            return;
        }
        
        Produto p = new Produto();
        p.setCodigoReduzido(Integer.parseInt(txtCodigoProdutoDevolucao.getText()));
        p.setDescricao(txtDescricaoProdutoDevolucao.getText());
        p.setQuantidade(Integer.parseInt(txtQuantidadeProdutoDevolucao.getText()));
        
        setTableViewProdutoDevolucao(p); // adiciona o produto no tableview
        clearFormProduto(); // limpa o formulário de produto
        
    } // fim do método handleButtonAddProdutoDevolucao
    
    /**
     * Manipula o botão Remover Produto Devolução
     * não é possível remover produtos de uma devolução já realizada.
     * @param event 
     */
    @FXML
    public void handleButtonRemoverProdutoDevolucao(ActionEvent event){
        
        // recupera o indice de um produto selecionado no tableview
        int index = tbvProdutoDevolucao.getSelectionModel().getSelectedIndex();
        
        if(index >= 0){
            // remove o objeto da lista de produtos passando o objeto recuperado do tableview
            produtosDevolucao.remove(
                    tbvProdutoDevolucao.getSelectionModel()
                    .getSelectedItem());
            tbvProdutoDevolucao.getItems().remove(index); // remove o produto do tableview
            tbvProdutoDevolucao.refresh(); // atualiza o tableview
        } // fim do bloco if
        
    } // fim do método handleButtonRemoverProdutoDevolucao
    
    /**
     * Manipula o botão Filtrar Produtos em movimentação de produtos.
     * @param event 
     */
    @FXML
    public void handleButtonFiltrarProdutos(ActionEvent event){
        
        if(txtFiltroProdutos.getText() == null){
            loadMovimentacaoProdutos(false); // recupera os dados sem filtro
        }
        else{
            loadMovimentacaoProdutos(true); // recupera os dados filtrados
        } // fim do bloco if/else
        
    } // fim do método handleButtonFiltrarProdutos
    
    /**
     * Manipula a opção sair do menu
     */
    @FXML
    public void sair(){
        
        Platform.exit(); // encerra a aplicação
        
    } // fim do método sair
    
    /**
     * Carrega as requisições podendo ser filtradas.
     * @param filter 
     */
    private void loadRequisicao(boolean filter){
        
        List<Requisicao> requisicoes;
        
        if(!filter){
            // chama o método que recupera os dados do banco
            requisicoes = (List<Requisicao>)requisicaoDao.load(); 
        }
        else{
            String date = null;
            String text = null;
            
            if(dtpPesquisaRequisicao.getValue() != null){
                date = dtpPesquisaRequisicao.getValue().toString();
                dtpPesquisaRequisicao.setValue(null);
            }
            
            if(txtPesquisaRequisicao.getText() != null){
                text = txtPesquisaRequisicao.getText();
                txtPesquisaRequisicao.setText(null);
            }
            
            // chama o método que recupera os dados filtrados
            requisicoes = (List<Requisicao>) requisicaoDao.filter(date, text);
        } // fim do bloco if/else
        
        // faz o binding das colunas do tableview com as propriedades do objeto
        colIdRequisicao.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCodRequisicao.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colDataRequisicao.setCellValueFactory(new PropertyValueFactory<>("data"));
        colResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
        
        ObservableList<Requisicao> list = FXCollections.observableArrayList(requisicoes);
        tbvRequisicao.setItems(list); // adiciona os objetos no tableview
        
    } // fim do método loadRequisicao
    
    /**
     * Carrega as devoluções podendo ser filtradas.
     * @param filter 
     */
    private void loadDevolucao(boolean filter){
        
        List<Devolucao> devolucoes;
        
        if(!filter){
            // chama o método que recupera os dados do banco
            devolucoes = (List<Devolucao>)devolucaoDao.load();
        }
        else{
            String date = null;
            String text = null;
            
            if(dtpPesquisaDevolucao.getValue() != null){
                date = dtpPesquisaDevolucao.getValue().toString();
                dtpPesquisaDevolucao.setValue(null);
            }
            
            if(txtPesquisaDevolucao.getText() != null){
                text = txtPesquisaDevolucao.getText();
                txtPesquisaDevolucao.setText(null);
            }
            
            // chama o método que recupera os dados filtrados
            devolucoes = (List<Devolucao>) devolucaoDao.filter(date, text);
        } // fim do bloco if/else
        
        // faz o binding das colunas do tableview com as propriedades do objeto
        colIdDevolucao.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCodDevolucao.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colDataDevolucao.setCellValueFactory(new PropertyValueFactory<>("data"));
        
        ObservableList<Devolucao> list = FXCollections.observableArrayList(devolucoes);
        tbvDevolucao.setItems(list); // adiciona os objetos no tableview
        
    } // fim do método loadDevolucao
    
    /**
     * Carrega os produtos da requisição passada por parâmetro.
     * @param id 
     */
    private void loadProdutoRequisicao(long id){
        
        List<Produto> produtos;
        
        // chama o método que recupera os dados do banco
        produtos = (List<Produto>) produtoDao.loadProdutoRequisicao(id);
        
        // faz o binding das colunas do tableview com as propriedades do objeto
        colCodProdutoRequisicao.setCellValueFactory(new PropertyValueFactory<>("codigoReduzido"));
        colQtdeProdutoRequisicao.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colDescricaoProdutoRequisicao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        ObservableList<Produto> list = FXCollections.observableArrayList(produtos);
        tbvProdutoRequisicao.setItems(list); // adiciona os objetos no tableview
        
    } // fim do método loadProdutoRequisicao
    
    /**
     * Carrega os produtos da devolução passada por parâmetro.
     * @param id 
     */
    private void loadProdutoDevolucao(long id){
        
        List<Produto> produtos;
        
        // chama o método que recupera os dados do banco
        produtos = (List<Produto>) produtoDao.loadProdutoDevolucao(id);
        
        // faz o binding das colunas do tableview com as propriedades do objeto
        colCodProdutoDevolucao.setCellValueFactory(new PropertyValueFactory<>("codigoReduzido"));
        colQtdeProdutoDevolucao.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colDescricaoProdutoDevolucao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        ObservableList<Produto> list = FXCollections.observableArrayList(produtos);
        tbvProdutoDevolucao.setItems(list); // adiciona os objetos no tableview
        
    } // fim do método loadProdutoDevolucao
    
    /**
     * Carrega os produtos e informações de saldo podendo ser filtrado.
     * @param filter 
     */
    private void loadMovimentacaoProdutos(boolean filter){
        
        List<Produto> produtos;
        
        if(!filter){
            // chama o método que recupera os dados do banco sem filtro
            produtos = (List<Produto>)produtoDao.getMovimentacaoProduto();
        }
        else{
            String text = "";
            
            if(txtFiltroProdutos.getText() != null){
                text = txtFiltroProdutos.getText();
            }
            
            // chama o método que carrega os dados do banco filtrado
            produtos = (List<Produto>)produtoDao.getMovimentacaoProduto(text);
        } // fim do bloco if/else
        
        // faz o binding das colunas com as propriedades do objeto
        colCodReduzidoProdutoSaldo.setCellValueFactory(new PropertyValueFactory<>("codigoReduzido"));
        colDescricaoProdutoSaldo.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        
        ObservableList<Produto> list = FXCollections.observableArrayList(produtos);
        tbvProdutosSaldo.setItems(list); // adiciona os produtos no tableview
        
    } // fim do método loadMovimentacaoProdutos
    
    /**
     * Adiciona um produto recebido por parâmetro no tableview
     * de produtos da requisição.
     * @param p 
     */
    private void setTableViewProdutoRequisicao(Produto p){
        
        produtosRequisicao.add(p);
        
        // faz o binding das colunas do tableview com as propriedades do objeto
        colCodProdutoRequisicao.setCellValueFactory(new PropertyValueFactory<>("codigoReduzido"));
        colQtdeProdutoRequisicao.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colDescricaoProdutoRequisicao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        ObservableList<Produto> list = FXCollections.observableArrayList(produtosRequisicao);
        tbvProdutoRequisicao.setItems(list); // adiciona o objeto no tableview
        
    } // fim do método setTableViewProdutoRequisicao
    
    /**
     * Adiciona um produto recebido por parâmetro no tableview
     * de produtos da devolução.
     * @param p 
     */
    private void setTableViewProdutoDevolucao(Produto p){
        
        produtosDevolucao.add(p);
        
        // faz o binding das colunas do tableview com as propriedades do objeto
        colCodProdutoDevolucao.setCellValueFactory(new PropertyValueFactory<>("codigoReduzido"));
        colQtdeProdutoDevolucao.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colDescricaoProdutoDevolucao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        ObservableList<Produto> list = FXCollections.observableArrayList(produtosDevolucao);
        tbvProdutoDevolucao.setItems(list); // adiciona o objeto no tableview
        
    } // fim do método setTableViewProdutoDevolucao
    
    /**
     * Recupera os valores do objeto selecionado no tableview de requisições.
     * @param requisicao 
     */
    private void selectItemTableViewRequisicoes(Requisicao requisicao){
        
        if(requisicao != null){
            currentRequisicao = requisicao;
            txtIdRequisicao.setText(String.valueOf(requisicao.getId()));
            txtCodRequisicao.setText(String.valueOf(requisicao.getCodigo()));
            dtpDataRequisicao.setValue(LocalDate.parse(requisicao.getData()));
            txtResponsavel.setText(requisicao.getResponsavel());
            txtObsRequisicao.setText(requisicao.getObservacao());
            loadProdutoRequisicao(requisicao.getId());
        } // fim do bloco if
        
    } // fim do método selectItemTableViewRequisicoes
    
    /**
     * Recupera os valores do objeto selecionado no tableview de devoluções.
     * @param devolucao 
     */
    private void selectItemTableViewDevolucoes(Devolucao devolucao){
        
        if(devolucao != null){
            currentDevolucao = devolucao;
            txtIdDevolucao.setText(String.valueOf(devolucao.getId()));
            txtCodDevolucao.setText(String.valueOf(devolucao.getCodigo()));
            dtpDataDevolucao.setValue(LocalDate.parse(devolucao.getData()));
            txtObsDevolucao.setText(devolucao.getObservacao());
            loadProdutoDevolucao(devolucao.getId());
        } // fim do bloco if
        
    } // fim do método selectItemTableViewDevolucoes
    
    /**
     * Controla o estado dos componentes da aba Devolução 
     * quando não está ocorrendo edições na aba de Requisição.
     */
    private void normalModeTabRequisicao(){
        
        tabDevolucao.setDisable(false);
        dtpPesquisaRequisicao.setDisable(false);
        txtPesquisaRequisicao.setDisable(false);
        btnPesquisarRequisicao.setDisable(false);
        tbvRequisicao.setDisable(false);
        btnAddRequisicao.setDisable(false);
        btnEditarRequisicao.setDisable(false);
        btnSalvarRequisicao.setDisable(true);
        btnCancelarRequisicao.setDisable(true);
        txtIdRequisicao.setEditable(false);
        txtIdRequisicao.setDisable(true);
        txtCodRequisicao.setEditable(false);
        dtpDataRequisicao.setDisable(true);
        txtResponsavel.setEditable(false);
        txtCodigoProdutoRequisicao.setEditable(false);
        txtQuantidadeProdutoRequisicao.setEditable(false);
        txtDescricaoProdutoRequisicao.setEditable(false);
        btnAddProdutoRequisicao.setDisable(true);
        tbvProdutoRequisicao.setEditable(false);
        btnRemoverProdutoRequisicao.setDisable(true);
        txtObsRequisicao.setEditable(false);
        
    } // fim do método normalModeTabRequisicao
    
    /**
     * Controla o estado dos componentes da aba Requisição 
     * quando não está ocorrendo edições na aba de Devolução.
     */
    private void normalModeTabDevolucao(){
        
        tabRequisicao.setDisable(false);
        dtpPesquisaDevolucao.setDisable(false);
        txtPesquisaDevolucao.setDisable(false);
        btnPesquisarDevolucao.setDisable(false);
        tbvDevolucao.setDisable(false);
        btnAddDevolucao.setDisable(false);
        btnEditarDevolucao.setDisable(false);
        btnSalvarDevolucao.setDisable(true);
        btnCancelarDevolucao.setDisable(true);
        txtIdDevolucao.setEditable(false);
        txtIdDevolucao.setDisable(true);
        txtCodDevolucao.setEditable(false);
        dtpDataDevolucao.setDisable(true);
        txtCodigoProdutoDevolucao.setEditable(false);
        txtQuantidadeProdutoDevolucao.setEditable(false);
        txtDescricaoProdutoDevolucao.setEditable(false);
        btnAddProdutoDevolucao.setDisable(true);
        tbvProdutoDevolucao.setEditable(false);
        btnRemoverProdutoDevolucao.setDisable(true);
        txtObsDevolucao.setEditable(false);
        
    } // fim do método normalModeTabDevolucao
    
    /**
     * Trava os componentes da aba Devolução quando uma Requisição
     * está em modo edição ou inserção.
     * @param insert flag para modo inserção
     */
    private void editModeTabRequisicao(boolean insert){
        
        tabDevolucao.setDisable(true);
        dtpPesquisaRequisicao.setDisable(true);
        txtPesquisaRequisicao.setDisable(true);
        btnPesquisarRequisicao.setDisable(true);
        tbvRequisicao.setDisable(true);
        btnAddRequisicao.setDisable(true);
        btnEditarRequisicao.setDisable(true);
        btnSalvarRequisicao.setDisable(false);
        btnCancelarRequisicao.setDisable(false);
        txtIdRequisicao.setEditable(false);
        txtIdRequisicao.setDisable(true);
        tbvProdutoRequisicao.setEditable(false);
        
        if(insert){
            txtCodRequisicao.setEditable(true);
            dtpDataRequisicao.setDisable(false);
            txtResponsavel.setEditable(true);
            txtCodigoProdutoRequisicao.setEditable(true);
            txtQuantidadeProdutoRequisicao.setEditable(true);
            txtDescricaoProdutoRequisicao.setEditable(true);
            btnAddProdutoRequisicao.setDisable(false);
            btnRemoverProdutoRequisicao.setDisable(false);
            
            editMode = false;
        } // fim do bloco if
        
        txtObsRequisicao.setEditable(true);
        
    } // fim do método editModeTabRequisicao
    
    /**
     * Trava os componentes da aba Requisição quando uma Devolução
     * está em modo edição ou inserção.
     * @param insert flag para modo inserção
     */
    private void editModeTabDevolucao(boolean insert){
        
        tabRequisicao.setDisable(true);
        dtpPesquisaDevolucao.setDisable(true);
        txtPesquisaDevolucao.setDisable(true);
        btnPesquisarDevolucao.setDisable(true);
        tbvDevolucao.setDisable(true);
        btnAddDevolucao.setDisable(true);
        btnEditarDevolucao.setDisable(true);
        btnSalvarDevolucao.setDisable(false);
        btnCancelarDevolucao.setDisable(false);
        txtIdDevolucao.setEditable(false);
        txtIdDevolucao.setDisable(true);
        tbvProdutoDevolucao.setEditable(false);
        
        if(insert){
            txtCodDevolucao.setEditable(true);
            dtpDataDevolucao.setDisable(false);
            txtCodigoProdutoDevolucao.setEditable(true);
            txtQuantidadeProdutoDevolucao.setEditable(true);
            txtDescricaoProdutoDevolucao.setEditable(true);
            btnAddProdutoDevolucao.setDisable(false);
            btnRemoverProdutoDevolucao.setDisable(false);
            
            editMode = false;
        } // fim do bloco if
        
        txtObsDevolucao.setEditable(true);
        
    } // fim do método editModeTabDevolucao
    
    /**
     * Limpa os campos dos formulários.
     */
    private void clearFields(){
        
        txtIdRequisicao.clear();
        txtCodRequisicao.clear();
        dtpDataRequisicao.setValue(LocalDate.now());
        txtResponsavel.clear();
        txtCodigoProdutoRequisicao.clear();
        txtQuantidadeProdutoRequisicao.clear();
        txtDescricaoProdutoRequisicao.clear();
        tbvProdutoRequisicao.getItems().clear();
        txtObsRequisicao.clear();
        txtIdDevolucao.clear();
        txtCodDevolucao.clear();
        dtpDataDevolucao.setValue(LocalDate.now());
        txtCodigoProdutoDevolucao.clear();
        txtQuantidadeProdutoDevolucao.clear();
        txtDescricaoProdutoDevolucao.clear();
        tbvProdutoDevolucao.getItems().clear();
        txtObsDevolucao.clear();
        
    } // fim do método clearFields
    
    /**
     * Limpa os campos do formulário de adição de produtos.
     */
    private void clearFormProduto(){
        
        txtCodigoProdutoRequisicao.clear();
        txtQuantidadeProdutoRequisicao.clear();
        txtDescricaoProdutoRequisicao.clear();
        txtCodigoProdutoDevolucao.clear();
        txtQuantidadeProdutoDevolucao.clear();
        txtDescricaoProdutoDevolucao.clear();
        
    } // fim do método clearFormProduto
    
    /**
     * Valida os dados informados no formulário de Requisição.
     * @return mensagem de erro
     */
    private String validateFormRequisicao(){
        
        StringBuilder errorMessage = new StringBuilder();
        
        if(StringUtils.isEmpty(txtCodRequisicao.getText())){
            errorMessage.append("Informe o código da requisição.")
                    .append(StringUtils.newLine());
        }
        
        if(StringUtils.isEmpty(dtpDataRequisicao.getValue().toString())){
            errorMessage.append("Informe uma data válida para a requisição.")
                    .append(StringUtils.newLine());
        }
        
        if(StringUtils.isEmpty(txtResponsavel.getText())){
            errorMessage.append("Informe o responsável.")
                    .append(StringUtils.newLine());
        }
        
        if(tbvProdutoRequisicao.getItems().isEmpty()){
            errorMessage.append("Adicione no mínimo um produto.")
                    .append(StringUtils.newLine());
        }
        
        return errorMessage.toString();
        
    } // fim do método validateFormRequisicao
    
    /**
     * Valida os dados informados no formulário de Devolução.
     * @return mensagem de erro
     */
    private String validateFormDevolucao(){
        
        StringBuilder errorMessage = new StringBuilder();
        
        if(StringUtils.isEmpty(txtCodDevolucao.getText())){
            errorMessage.append("Informe o código da devolução.")
                    .append(StringUtils.newLine());
        }
        
        if(StringUtils.isEmpty(dtpDataDevolucao.getValue().toString())){
            errorMessage.append("Informe uma data válida para a devolução.")
                    .append(StringUtils.newLine());
        }
        
        if(tbvProdutoDevolucao.getItems().isEmpty()){
            errorMessage.append("Adicione no mínimo um produto.")
                    .append(StringUtils.newLine());
        }
        
        return errorMessage.toString();
        
    } // fim do método validateFormDevolucao
    
    /**
     * Valida os dados informados no formulário de adição de produtos
     * de uma requisição.
     * @return mensagem de erro
     */
    private String validateFormProdutoRequisicao(){
        
        StringBuilder errorMessage = new StringBuilder();
        
        if(StringUtils.isEmpty(txtCodigoProdutoRequisicao.getText())){
            errorMessage.append("Informe o código do produto.")
                    .append(StringUtils.newLine());
        }
        
        if(StringUtils.isEmpty(txtQuantidadeProdutoRequisicao.getText())){
            errorMessage.append("Informe a quantidade do produto.")
                    .append(StringUtils.newLine());
        }
        else if(Integer.parseInt(txtQuantidadeProdutoRequisicao.getText()) < 1){
            errorMessage.append("A quantidade mínima não pode ser menor que 1.")
                    .append(StringUtils.newLine());
        }
        
        if(StringUtils.isEmpty(txtDescricaoProdutoRequisicao.getText())){
            errorMessage.append("Informe a descrição do produto.")
                    .append(StringUtils.newLine());
        }
        
        return errorMessage.toString();
        
    } // fim do método validateFormProdutoRequisicao
    
    /**
     * Valida os dados informados no formulário de adição de produtos
     * de uma devolução.
     * @return mensagem de erro
     */
    private String validateFormProdutoDevolucao(){
        
        StringBuilder errorMessage = new StringBuilder();
        
        if(StringUtils.isEmpty(txtCodigoProdutoDevolucao.getText())){
            errorMessage.append("Informe o código do produto.")
                    .append(StringUtils.newLine());
        }
        
        if(StringUtils.isEmpty(txtQuantidadeProdutoDevolucao.getText())){
            errorMessage.append("Informe a quantidade do produto.")
                    .append(StringUtils.newLine());
        }
        else if(Integer.parseInt(txtQuantidadeProdutoDevolucao.getText()) < 1){
            errorMessage.append("A quantidade mínima não pode ser menor que 1.")
                    .append(StringUtils.newLine());
        }
        
        if(StringUtils.isEmpty(txtDescricaoProdutoDevolucao.getText())){
            errorMessage.append("Informe a descrição do produto.")
                    .append(StringUtils.newLine());
        }
        
        if (!StringUtils.isEmpty(txtCodigoProdutoDevolucao.getText())
                && !StringUtils.isEmpty(txtQuantidadeProdutoDevolucao.getText())) {

            /**
             * testa se o produto informado já foi requisitado ou se a quantidade
             * que se está tentando devolver é permitida para que não se tenha 
             * produtos com saldos negativos
             */
            if (!produtoDao.isAvailable(
                    Long.parseLong(txtCodigoProdutoDevolucao.getText()),
                    Integer.parseInt(txtQuantidadeProdutoDevolucao.getText()))) {
                errorMessage.append("Você está tentando devolver um produto que ainda não foi requisitado ou "
                        + "com uma quantidade superior a que foi requisitada anteriormente.")
                        .append(StringUtils.newLine());
            } // fim do bloco if
        } // fim do bloco if
        
        return errorMessage.toString();
        
    } // fim do método validateFormProdutoDevolucao
    
    /**
     * Exibe um alerta com as mensagens de erro.
     * @param message mensagem de erro a ser exibida
     */
    private void showValidationError(String message){
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro de Validação");
        alert.setHeaderText("Não foi possível executar a ação");
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
        
    } // fim do método showValidationError
    
    /**
     * Recebe um campo de texto como parâmetro e verifica se o conteúdo
     * digitado são números.
     * @param field 
     */
    private void checkNumericField(TextField field){
        
        field.textProperty().addListener((ObservableValue<? extends String> observable, 
                String oldValue, String newValue) -> {
            if(!newValue.matches("\\d*")){ // se não for número
                field.setText(newValue.replaceAll("[^\\d]", "")); // o caractere é apagado
            }
            
        }); // fim do addListener
        
    } // fim do método checkNumericField
    
    /**
     * Transforma os campos de texto para caixa alta.
     * @param field 
     */
    private void setTextFieldToUpper(TextField field){
        
        field.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(field.getText() != null)
                        field.setText(newValue.toUpperCase());
                } // fim da expressão
        ); // fim do addListener
        
    } // fim do método setTextFieldToUpper
    
} // fim da classe TelaPrincipalController
package com.biblioteca.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.biblioteca.domain.Autor;
import com.biblioteca.domain.EnumGenero;
import com.biblioteca.domain.Livro;
import com.formdev.flatlaf.FlatLightLaf;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

public class LivroFormPanel extends JPanel {

    private LivroPresentationModel livroModel;
    private LivroListModel listModel; 

    private JTextField idField;
    private JTextField tituloField;
    
    private JTextField isbnField;
    private final String[] blocoHash = {"000","000","000","000"};
    
    private JTextField yearField;
    private JTextField autorIdField;
    
    private JComboBox<Autor> autorComboBox;
    private JComboBox<EnumGenero> generoComboBox;

    private SelectionInList<Autor> autoresSelection;
    
    private JLabel imagePreviewLabel;
    private JButton selectImageButton;
    private JButton removeImageButton;

    private JButton saveButton;
    private JButton cleanButton;
    
    private Runnable onSaveCallback;
    
    public LivroFormPanel() {
        super(new BorderLayout());
        initComponents();
        refreshAutoresComboBox();
        initListeners();
        initImageListener();
        buildPanel();
    }
    
    public void setOnSaveCallBack(Runnable onSaveCallback) {
    	this.onSaveCallback = onSaveCallback;
    }

    public void setLivro(Livro livro) {
        this.livroModel.setBean(livro);
    }

    private void initComponents() {
        livroModel = new LivroPresentationModel(new Livro());
        listModel = new LivroListModel();

        idField = BasicComponentFactory.createLongField(livroModel.getIdModel());
        idField.setEditable(false);
        idField.setEnabled(false);
        autorIdField = new JTextField();
        autorIdField.setEditable(false);
        autorIdField.setEnabled(false);
        
        tituloField = BasicComponentFactory.createTextField(livroModel.getTituloModel());
        
        isbnField = BasicComponentFactory.createTextField(livroModel.getIsbnModel());
        isbnField.setEditable(false);
        isbnField.setEnabled(false);
        
        NumberFormat formatoAno = NumberFormat.getInstance();
        formatoAno.setGroupingUsed(false);
        
        yearField = BasicComponentFactory.createIntegerField(livroModel.getYearModel(),formatoAno);

        generoComboBox = BasicComponentFactory.createComboBox(livroModel.getGeneroSelection());

        livroModel.setListaAutores(listModel.getAutoresList().getList());
        autorComboBox = BasicComponentFactory.createComboBox(livroModel.getAutoresSelection());
        

        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setPreferredSize(new Dimension(100, 140));
        imagePreviewLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePreviewLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePreviewLabel.setText("Sem Capa");
        
        selectImageButton = new JButton("Selecionar Capa");
        removeImageButton = new JButton("Remover Capa");

        saveButton = new JButton("Salvar");
        cleanButton = new JButton("Limpar");
    }
    
    public void refreshAutoresComboBox() {
    	listModel.loadAutores();
    	livroModel.setListaAutores(listModel.getAutoresList().getList());
    }

    private void buildPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        JLabel titleLabel = new JLabel("Cadastro de Livro");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(new JSeparator(), BorderLayout.SOUTH);

        FormLayout layout = new FormLayout(
            "right:pref, 10dlu, 25dlu, 5dlu, fill:120dlu:grow, 20dlu, center:pref", //coluna	
            "7*(p, 4dlu), p" //linha
        );

        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.border(new EmptyBorder(20, 20, 20, 20));
        
        
        builder.append("ID:", idField, 3);
        builder.add(imagePreviewLabel, "7, 1, 1, 7"); 
        builder.nextLine(2);

        builder.append("Título:", tituloField, 3);
        builder.nextLine(2);
     
        builder.addLabel("Autor:", CC.xy(1, 5));
        builder.add(autorIdField, CC.xy(3,5));
        builder.add(autorComboBox, CC.xy(5, 5));
        builder.nextLine(2);


        builder.append("Gênero:", generoComboBox,3);
        builder.nextLine(2);
        
        builder.add(selectImageButton, "7, 9");
        
        builder.append("Ano:", yearField,3);
        builder.nextLine(2);

        builder.append("ISBN:", isbnField,3);
        builder.nextLine(2);

        ButtonBarBuilder btnBuilder = new ButtonBarBuilder();
        btnBuilder.addGlue();
        btnBuilder.addButton(saveButton);
        btnBuilder.addRelatedGap();
        btnBuilder.addButton(cleanButton);
        
        JPanel buttonPanel = btnBuilder.getPanel();
        buttonPanel.setBorder(new EmptyBorder(20,20,20,20));
        

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(builder.getPanel(), BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
    

    private void initListeners() {
    	
    	livroModel.getTituloModel().addValueChangeListener(evt -> {
    		
    		blocoHash[0] = livroModel.gerarHashCurto(livroModel.getTituloModel().getValue());
    		
    		atualizarIsbn();
    		
    	});
    	
    	livroModel.getGeneroModel().addValueChangeListener(evt -> {
    		
    		blocoHash[2] = livroModel.gerarHashCurto(livroModel.getGeneroModel().getValue());
    		
    		atualizarIsbn();
    	});
    	
    	livroModel.getYearModel().addValueChangeListener(evt -> {
    		
    		blocoHash[3] = livroModel.gerarHashCurto(livroModel.getYearModel().getValue());
    		
    		atualizarIsbn();
    		
    	});
    	
    	
    	livroModel.getAutorModel().addValueChangeListener(evt -> {
    		
    		blocoHash[1] = livroModel.gerarHashCurto(livroModel.getAutorModel().getValue());
    		
    		atualizarIsbn();
    		
    		Autor novoAutor = (Autor) evt.getNewValue();
    		
    		if (novoAutor != null && novoAutor.getId() != null) {
    			autorIdField.setText(String.valueOf(novoAutor.getId()));
    		} else {
    			autorIdField.setText("");
    		}
    	});
    	
    	
        selectImageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDragEnabled(true);
            chooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "png", "jpeg"));
            
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (livroModel.validarImagem(file)) {
                	livroModel.converterImagemParaBytes(file);
                	updateImagePreview();
                	
                } else {
                	String nomeArquivo = chooser.getName(file);
                	int indicePonto = nomeArquivo.lastIndexOf('.');
                	if (indicePonto > 0 && indicePonto < nomeArquivo.length() -1) {
                		String extensao = nomeArquivo.substring(indicePonto + 1);
                		JOptionPane.showMessageDialog(this, "O tipo de extensão " + extensao +
                			" não é aceito. \n \n O arquivo deve ser do tipo: \n .jpg | .png | .jpeg");
                	}
                }
            }
        });

        saveButton.addActionListener(e -> {
            String erros = livroModel.getErrosValidacao();
            if (!erros.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Os seguintes campos não foram preenchidos corretamente: \n" +
            			erros, "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
            	livroModel.salvarLivro();
                JOptionPane.showMessageDialog(this, "Livro salvo com sucesso!");
                
                if (onSaveCallback != null) {
                	onSaveCallback.run();
                }
                
                livroModel.setBean(new Livro());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cleanButton.addActionListener(e -> livroModel.limpar());
    }

	private void atualizarIsbn() {
		String blocoHashCompleto = String.join("-", blocoHash);
		
		livroModel.getIsbnModel().setValue(blocoHashCompleto);
	}

    private void initImageListener() {
        livroModel.addPropertyChangeListener("capaImagem", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateImagePreview();
            }
        });
        
        livroModel.addPropertyChangeListener("bean", e -> updateImagePreview());
    }
    

    private void updateImagePreview() {
        byte[] imagemBytes = (byte[]) livroModel.getImageModel().getValue();
        if (imagemBytes != null && imagemBytes.length > 0) {
            ImageIcon icon = new ImageIcon(imagemBytes);
            Image img = icon.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
            imagePreviewLabel.setIcon(new ImageIcon(img));
            imagePreviewLabel.setText("");
        } else {
            imagePreviewLabel.setIcon(null);
            imagePreviewLabel.setText("Sem Capa");
        }
    }
    
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Teste Livro Panel");
            frame.add(new LivroFormPanel());
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
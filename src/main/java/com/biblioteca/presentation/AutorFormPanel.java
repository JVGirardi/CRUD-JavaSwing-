package com.biblioteca.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.biblioteca.domain.Autor;
import com.biblioteca.domain.Nacionalidade;
import com.biblioteca.persistence.AutorDAO;
import com.formdev.flatlaf.FlatLightLaf;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class AutorFormPanel extends JPanel{
	
	private AutorPresentationModel autorModel;
	
	private JTextField idField;
	private JTextField nameTextField;
	private JComboBox<Nacionalidade> nationalityComboBox;
	private JButton saveButton;
	private JButton cleanButton;
	
	public AutorFormPanel() {
		initComponents();
		buildPanel();
		initListeners();
	}
	
	private void initComponents() {
		autorModel = new AutorPresentationModel(new Autor());
		
		nameTextField = BasicComponentFactory.createTextField(autorModel.getNameModel());
		nationalityComboBox = BasicComponentFactory.createComboBox(autorModel.getNacionalidadeSelection());
		idField = BasicComponentFactory.createLongField(autorModel.getId());
		idField.setEditable(false);
		idField.setEnabled(false);
		
		saveButton = new JButton("Salvar");
		cleanButton = new JButton("Limpar");
	}
	
	
	private void buildPanel() {
		
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBorder(new EmptyBorder(20,20,10,20));
		
		JLabel titleLabel = new JLabel("Cadastro de autor");
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		titleLabel.setForeground(new Color(50, 50, 50));
		
		headerPanel.add(titleLabel, BorderLayout.WEST);
		headerPanel.add(new JSeparator(), BorderLayout.SOUTH);
		
		FormLayout layout = new FormLayout("right:pref, 10dlu, fill:100dlu:grow");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(20, 20, 20, 20));
		
		builder.appendSeparator("Dados: ");
		builder.nextLine();
		builder.append("ID", idField);
		builder.nextLine();
		builder.append("Nome Completo", nameTextField);
		builder.nextLine();
		builder.append("Nacionalidade", nationalityComboBox);
		
		ButtonBarBuilder barBuilder = new ButtonBarBuilder();
		barBuilder.addGlue();
		barBuilder.addButton(cleanButton);
		barBuilder.addRelatedGap();
		barBuilder.addButton(saveButton);
		
		JPanel buttonBarPanel = new JPanel();
		buttonBarPanel.setBorder(new EmptyBorder(20,20,20,20));
		buttonBarPanel.add(barBuilder.getPanel());
		
		this.setLayout(new BorderLayout());
		this.add(headerPanel, BorderLayout.NORTH);
		this.add(builder.getPanel(), BorderLayout.CENTER);
		this.add(buttonBarPanel, BorderLayout.SOUTH);
		
	}
	
	private void initListeners() {
		
		cleanButton.addActionListener(e -> {
			boolean temNome = isNotBlank(autorModel.getNameModel().getValue());
			boolean temNacionalidade = isNotBlank(autorModel.getNationalityModel().getValue());
			
			if (temNome || temNacionalidade) {
				autorModel.setBean(new Autor());
			} else {
				JOptionPane.showMessageDialog(this, "Não há campos para serem limpos.");
			}
		});
		
		saveButton.addActionListener(e -> {
			Autor autorAtual = autorModel.getBean();
			System.out.println(autorAtual.getName() + "--" + autorAtual.getNationality());
			if (!autorModel.getErrosValidacao().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Os seguintes campos não foram preenchidos corretamente: \n" +
			autorModel.getErrosValidacao(), "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
				return;
			};
			
			try {
				AutorDAO dao = new AutorDAO();
				
				Autor autorSaved = dao.saveOrUpdate(autorModel.getBean());
				autorModel.setBean(autorSaved);
				JOptionPane.showMessageDialog(this, "O autor " + autorModel.getNameModel().getValue().toString() + "foi cadastrado com sucesso");
				autorModel.setBean(new Autor());
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
			}
		});
	}
	
	
	public boolean isNotBlank(Object value) {
		return value != null && !value.toString().trim().isEmpty();
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
		} catch (Exception e) {
		}
		SwingUtilities.invokeLater(() -> {
			
			JFrame frame = new JFrame("teste");
			frame.add(new AutorFormPanel());
			frame.pack();
			frame.setResizable(false);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}

}

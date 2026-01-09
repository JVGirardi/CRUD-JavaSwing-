package com.biblioteca.presentation;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.biblioteca.domain.Autor;
import com.biblioteca.domain.Livro;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;

public class LivroFormPanel extends JPanel{
	
	private LivroPresentationModel livroModel;
	private LivroListModel listModel;
	
	private SelectionInList<Autor> autoresSelection;
	
	private JTextField idField;
	private JTextField tituloField;
	
	private JTextField autorIdField;
	private JComboBox comboAutores;
	
	
	private JTextField isbnField;
	
	private JTextField yearField;
	
	private JTextField imageField;
	private JButton imageButton;
	private JLabel previewImageLabel;
	
	
	private JButton saveButton;
	private JButton cleanButton;
	
	
	
	
	
	
	
	
	
	public void initComponents() {
		listModel = new LivroListModel();
		livroModel = new LivroPresentationModel(new Livro());
		
		autoresSelection = new SelectionInList<Autor>();
		autoresSelection = listModel.getAutoresList();
		
		idField = BasicComponentFactory.createLongField(livroModel.getIdModel());
		idField.setEditable(false);
		idField.setEnabled(false);
		
		tituloField = BasicComponentFactory.createTextField(livroModel.getTituloModel());
		autorIdField = BasicComponentFactory.createTextField(livroModel.getAutorModel());
		
		
		
		
		
	}
	
	public void acionarEscolhaDeImagem() {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			livroModel.carregarImagem(chooser.getSelectedFile());
		}
	}
	
	

}

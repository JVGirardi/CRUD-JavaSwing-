package com.biblioteca.presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.formdev.flatlaf.FlatLightLaf;

public class MainFrame2 extends JFrame {

    private CardLayout rootLayout;
    private JPanel rootPanel;

    private LoginPanel loginPanel;
    private JPanel systemPanel;

    private CardLayout contentLayout;
    private JPanel contentPanel;
    private JTree menuTree;

    private ClientFormPanel clientFormPanel;
    private ClientTablePanel clientTablePanel;
    private AutorFormPanel autorFormPanel;
    private AutorTablePanel autorTablePanel;
    private LivroFormPanel livroFormPanel;
    private LivroTablePanel livroTablePanel;
    private EmprestimoFormPanel emprestimoFormPanel;
    private EmprestimoTablePanel emprestimoTablePanel;

    private static final String VIEW_CLIENTES_LISTA = "Listagem de Clientes";
    private static final String VIEW_CLIENTES_FORM = "Cadastro de Clientes";
    private static final String VIEW_AUTORES_LISTA = "Listagem de Autores";
    private static final String VIEW_AUTORES_FORM = "Cadastro de Autores";
    private static final String VIEW_LIVROS_LISTA = "Listagem de Livros";
    private static final String VIEW_LIVROS_FORM = "Cadastro de Livros";
    private static final String VIEW_EMPRESTIMOS_LISTA = "Listagem de Empréstimos";
    private static final String VIEW_EMPRESTIMOS_NOVO = "Novo Empréstimo";

    public MainFrame2() {
        this.setTitle("Sistema de Gerenciamento de Biblioteca");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024, 700); 
        this.setLocationRelativeTo(null);
        
        initComponents();
        buildUI();
    }

    private void initComponents() {
        rootLayout = new CardLayout();
        rootPanel = new JPanel(rootLayout);

        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        loginPanel = new LoginPanel();
        loginPanel.setOnLoginSuccess(() -> {
            rootLayout.show(rootPanel, "Sistema");
            navigateTo(VIEW_EMPRESTIMOS_LISTA); 
        });

        initClientPanels();
        initAutorPanels();
        initLivroPanels();
        initEmprestimoPanels();

        initMenuTree();
    }

    private void initClientPanels() {
        clientFormPanel = new ClientFormPanel();
        clientTablePanel = new ClientTablePanel(c -> {
            clientFormPanel.setClient(c);
            navigateTo(VIEW_CLIENTES_FORM);
        });

        clientFormPanel.setOnSaveCallBack(() -> {
            clientTablePanel.refreshTable();
            navigateTo(VIEW_CLIENTES_LISTA);
        });

        contentPanel.add(clientTablePanel, VIEW_CLIENTES_LISTA);
        contentPanel.add(clientFormPanel, VIEW_CLIENTES_FORM);
    }

    private void initAutorPanels() {
        autorFormPanel = new AutorFormPanel();
        autorTablePanel = new AutorTablePanel(a -> {
            autorFormPanel.setAutor(a);
            navigateTo(VIEW_AUTORES_FORM);
        });

        autorFormPanel.setOnSaveCallBack(() -> {
            autorTablePanel.refreshTable();
            navigateTo(VIEW_AUTORES_LISTA);
        });
        
        autorFormPanel.setOnSaveLoadAutores(() -> livroFormPanel.refreshAutoresComboBox());
        autorTablePanel.setOnRemoveLoadAutores(() -> livroFormPanel.refreshAutoresComboBox());

        contentPanel.add(autorTablePanel, VIEW_AUTORES_LISTA);
        contentPanel.add(autorFormPanel, VIEW_AUTORES_FORM);
    }

    private void initLivroPanels() {
        livroFormPanel = new LivroFormPanel();
        livroTablePanel = new LivroTablePanel(l -> {
            livroFormPanel.setLivro(l);
            navigateTo(VIEW_LIVROS_FORM);
        });

        livroFormPanel.setOnSaveCallBack(() -> {
            livroTablePanel.refreshTable();
            navigateTo(VIEW_LIVROS_LISTA);
        });

        contentPanel.add(livroTablePanel, VIEW_LIVROS_LISTA);
        contentPanel.add(livroFormPanel, VIEW_LIVROS_FORM);
    }

    private void initEmprestimoPanels() {
        this.emprestimoFormPanel = new EmprestimoFormPanel();
        this.emprestimoTablePanel = new EmprestimoTablePanel();
        
        this.emprestimoFormPanel.onLendRefreshTablePanel(() -> {
            this.emprestimoTablePanel.refreshTable(); 
            navigateTo(VIEW_EMPRESTIMOS_LISTA);
        });
        
        this.emprestimoTablePanel.onReturnRefreshFormPanel(() -> {
        	
        });

        contentPanel.add(emprestimoTablePanel, VIEW_EMPRESTIMOS_LISTA);
        contentPanel.add(emprestimoFormPanel, VIEW_EMPRESTIMOS_NOVO);
    }

    private void initMenuTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Biblioteca");

        DefaultMutableTreeNode nodeEmprestimos = new DefaultMutableTreeNode("Empréstimos");
        nodeEmprestimos.add(new DefaultMutableTreeNode(VIEW_EMPRESTIMOS_LISTA));
        nodeEmprestimos.add(new DefaultMutableTreeNode(VIEW_EMPRESTIMOS_NOVO));

        DefaultMutableTreeNode nodeClientes = new DefaultMutableTreeNode("Clientes");
        nodeClientes.add(new DefaultMutableTreeNode(VIEW_CLIENTES_LISTA));
        nodeClientes.add(new DefaultMutableTreeNode(VIEW_CLIENTES_FORM));

        DefaultMutableTreeNode nodeLivros = new DefaultMutableTreeNode("Livros");
        nodeLivros.add(new DefaultMutableTreeNode(VIEW_LIVROS_LISTA));
        nodeLivros.add(new DefaultMutableTreeNode(VIEW_LIVROS_FORM));

        DefaultMutableTreeNode nodeAutores = new DefaultMutableTreeNode("Autores");
        nodeAutores.add(new DefaultMutableTreeNode(VIEW_AUTORES_LISTA));
        nodeAutores.add(new DefaultMutableTreeNode(VIEW_AUTORES_FORM));

        root.add(nodeEmprestimos);
        root.add(nodeClientes);
        root.add(nodeLivros);
        root.add(nodeAutores);

        menuTree = new JTree(root);
        menuTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        menuTree.setRowHeight(30); 
        menuTree.setBorder(new EmptyBorder(10, 10, 10, 10)); 
        
        for (int i = 0; i < menuTree.getRowCount(); i++) {
            menuTree.expandRow(i);
        }

        menuTree.setCellRenderer(new MenuTreeCellRenderer());

        menuTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) menuTree.getLastSelectedPathComponent();
            
            if (node == null) return;

            if (node.isLeaf()) {
                String viewName = node.getUserObject().toString();
                contentLayout.show(contentPanel, viewName);
                
                if(viewName.equals(VIEW_EMPRESTIMOS_LISTA)) emprestimoTablePanel.refreshTable();
            }
        });
    }

    private void navigateTo(String viewName) {
        contentLayout.show(contentPanel, viewName);
        
        DefaultTreeModel model = (DefaultTreeModel) menuTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        
        java.util.Enumeration<?> e = root.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (node.getUserObject().toString().equals(viewName)) {
                menuTree.setSelectionPath(new TreePath(node.getPath()));
                break;
            }
        }
    }

    public void buildUI() {
        systemPanel = new JPanel(new BorderLayout());
        
        JScrollPane treeScroll = new JScrollPane(menuTree);
        treeScroll.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, java.awt.Color.LIGHT_GRAY));
        treeScroll.setPreferredSize(new Dimension(250, 0)); 

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, contentPanel);
        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(0); 
        splitPane.setResizeWeight(0.0); 

        systemPanel.add(splitPane, BorderLayout.CENTER);

        rootPanel.add(loginPanel, "Login");
        rootPanel.add(systemPanel, "Sistema");

        this.add(rootPanel);
        
        rootLayout.show(rootPanel, "Login");
    }

    private static class MenuTreeCellRenderer extends DefaultTreeCellRenderer {
        
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {
            
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            String text = node.getUserObject().toString();
            
            if (!leaf) {
                if (expanded) {
                    setIcon(UIManager.getIcon("Tree.openIcon"));
                } else {
                    setIcon(UIManager.getIcon("Tree.closedIcon"));
                }
                setFont(getFont().deriveFont(java.awt.Font.BOLD)); // Negrito para categorias
            } else {
                if (text.contains("Cadastro") || text.contains("Novo")) {
                    setIcon(UIManager.getIcon("FileView.fileIcon")); 
                } else if (text.contains("Lista")) {
                    setIcon(UIManager.getIcon("FileView.computerIcon")); 
                } else {
                    setIcon(UIManager.getIcon("FileView.fileIcon"));
                }
                setFont(getFont().deriveFont(java.awt.Font.PLAIN));
            }
            
            return this;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("Tree.font", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)); 
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame2 frame = new MainFrame2();
            frame.setVisible(true);
        });
    }
}
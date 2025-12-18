package com.biblioteca.presentation;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;

public class MainFrame extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainContainer;
    
    public MainFrame() {
        super("Sistema de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Tamanho inicial maior
        setLocationRelativeTo(null);
        
        // Inicializa o layout de cartões
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        
        // --- TELA 1: LOGIN ---
        LoginPanel loginPanel = new LoginPanel();
        
        // --- TELA 2: GESTÃO DE CLIENTES (COM ABAS) ---
        ClientManagementPanel managementPanel = new ClientManagementPanel();
        
        // --- LOGICA DE TROCA DE TELA ---
        // Aqui dizemos: Quando o login der certo...
        loginPanel.setOnLoginSuccess(() -> {
            // ...troque o cartão atual para o cartão chamado "APP"
            cardLayout.show(mainContainer, "APP");
            // Centraliza a janela novamente, caso o tamanho mude (opcional)
            setLocationRelativeTo(null);
        });
        
        // Adicionamos as telas ao "baralho" com um nome (String) identificador
        mainContainer.add(loginPanel, "LOGIN");
        mainContainer.add(managementPanel, "APP");
        
        // Adiciona o container principal à Janela
        this.add(mainContainer);
        
        // Mostra a tela de Login primeiro
        cardLayout.show(mainContainer, "LOGIN");
    }

    public static void main(String[] args) {
        // Configuração do Tema (FlatLaf)
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
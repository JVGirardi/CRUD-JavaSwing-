package com.biblioteca.presentation;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import com.biblioteca.domain.Client;

public class ClientManagementPanel extends JPanel {
    
    // CORREÇÃO: O nome da classe anterior era ClientTablePanel, não ClientListPanel
    private ClientTablePanel listPanel; 
    private ClientFormPanel formPanel;
    private JTabbedPane tabbedPane;

    public ClientManagementPanel() {
        setLayout(new BorderLayout());
        
        tabbedPane = new JTabbedPane();
        
        // 1. Instancia o Form
        formPanel = new ClientFormPanel();
        
        // Callback: Ao salvar, atualiza a lista e volta para a aba 0
        formPanel.setOnSaveCallBack(() -> {
            if(listPanel != null) listPanel.refreshTable();
            tabbedPane.setSelectedIndex(0); 
        });

        // 2. Instancia a Tabela (Lista)
        // Listener: Ao clicar em editar, preenche o form e vai para a aba 1
        listPanel = new ClientTablePanel(new ClientTablePanel.ListListener() {
            @Override
            public void onEdit(Client client) {
                formPanel.setClient(client);
                tabbedPane.setSelectedIndex(1);
            }
        });

        // 3. Adiciona as abas
        tabbedPane.addTab("Listagem de Clientes", listPanel);
        tabbedPane.addTab("Cadastro / Edição", formPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
}
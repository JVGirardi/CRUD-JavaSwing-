package com.biblioteca.persistence;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.biblioteca.domain.Emprestimo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ReportManager {
	
	
	public void gerarRelatorio(String nomeArquivoJasper, List<?> dados) {
        try {
            InputStream reportStream = getClass().getResourceAsStream("/reports/" + nomeArquivoJasper);
            if (reportStream == null) {
                System.out.println("Arquivo .jasper não encontrado!");
                return;
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("nmSistema", "Relatório de Clientes");
            
            InputStream logoStream = getClass().getResourceAsStream("/images/1_logorodape.png");
            if (logoStream != null) {
            	parametros.put("imagemLogo", logoStream);
            } else {
            	System.out.println("ERRO: Logo não encontrada");
            	parametros.put("imagemLogo", null);
            }
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parametros, dataSource);

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Visualização de Relatório");
            viewer.setVisible(true);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
	
	
	public static void main(String[] args) {
		ReportManager jasper = new ReportManager();
		EmprestimoDAO dao = new EmprestimoDAO();
		List<Emprestimo> historicoEmprestimo = dao.listarHistoricoDeEmprestimo();
		jasper.gerarRelatorio("relatorioEmprestimos.jasper", historicoEmprestimo);
	}
}



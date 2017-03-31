package br.com.sysm.jasperreports.jreportbuilder.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.sysm.jasperreports.jreportbuilder.Relatorio;

/**
 * Session Bean implementation class RelatorioService
 */
@Stateless
@LocalBean
public class RelatorioService {

	@EJB
    private RelatorioRepository repository;
	private ArrayList<String> completedSubReports = new ArrayList<String>(30);
	private Throwable subReportException = null;
    public RelatorioService() {
    }

    public Relatorio persistRelatorio(Relatorio rel) {
    	return repository.persistRelatorio(rel);
    }
    
    public Relatorio updateRelatorio(Relatorio rel) {
    	return repository.updateRelatorio(rel);
    }
    
    public void removeRelatorio(Relatorio rel) {
    	repository.removeRelatorio(rel);
    }
    
    public List<Relatorio> getRelatorios() {
    	return repository.getRelatorios();
    }
    
    public byte[] executarRelatorio(Relatorio rel) throws Exception {
    	Class.forName("com.mysql.jdbc.Driver");
    	Connection mySQLConnection = DriverManager.getConnection("jdbc:mysql//localhost:3306/jasperrepots", "jasper", "jasper");
    	
    }
    
    private
    
    
}

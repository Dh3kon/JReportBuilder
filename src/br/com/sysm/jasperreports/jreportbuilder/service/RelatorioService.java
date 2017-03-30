package br.com.sysm.jasperreports.jreportbuilder.service;

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
    
    
}

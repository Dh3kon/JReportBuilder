package br.com.sysm.jasperreports.jreportbuilder.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.sysm.jasperreports.jreportbuilder.Relatorio;

/**
 * Session Bean implementation class RelatorioRepository
 */
@Stateless
@LocalBean
public class RelatorioRepository implements Serializable{

	@PersistenceContext
	private EntityManager em;
	private static final long serialVersionUID = 1L;

	/**
     * Default constructor. 
     */
    public RelatorioRepository() {
    }
    
    public Relatorio persistRelatorio(Relatorio rel) {
    	em.persist(rel);
    	return rel;
    }
    
    public Relatorio updateRelatorio(Relatorio rel) {
    	return em.merge(rel);
    }
    
    public void removeRelatorio(Relatorio rel) {
    	em.remove(rel);
    }
    
    public List<Relatorio> getRelatorios() {
    	Query qr = em.createQuery("select rel from Relatorio rel");
    	return qr.getResultList();
    }

}

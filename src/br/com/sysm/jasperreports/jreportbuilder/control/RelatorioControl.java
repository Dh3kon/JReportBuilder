package br.com.sysm.jasperreports.jreportbuilder.control;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.sysm.jasperreports.jreportbuilder.Relatorio;
import br.com.sysm.jasperreports.jreportbuilder.service.RelatorioService;

@ManagedBean
@SessionScoped
public class RelatorioControl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private RelatorioService relatorioService;
	public RelatorioControl() {
	}
	
	public List<Relatorio> getRelatorios() {
		return relatorioService.getRelatorios();
	}
}

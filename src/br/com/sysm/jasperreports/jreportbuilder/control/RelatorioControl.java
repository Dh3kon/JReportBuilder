package br.com.sysm.jasperreports.jreportbuilder.control;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

import br.com.sysm.jasperreports.jreportbuilder.Relatorio;
import br.com.sysm.jasperreports.jreportbuilder.service.RelatorioService;

@ManagedBean
@SessionScoped
public class RelatorioControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private RelatorioService relatorioService;
	private Relatorio selectedRelatorio;
	private Part arquivoLayout;

	public RelatorioControl() {
	}

	public String doStartNovo() {
		selectedRelatorio = new Relatorio();
		return "/novo.faces";
	}
	
	public String doCancelar() {
		return "/index.faces";
	}
	
	public void doExecutarRelatorio(Relatorio rel) throws Exception {
		byte[] arqResultado = relatorioService.executarRelatorio(rel);
	}
	
	public String doFinishNovo() throws IOException {
		byte[] arqByte = new byte [(int) arquivoLayout.getSize()];
		arquivoLayout.getInputStream().read(arqByte);
		getSelectedRelatorio().setRelatorio(arqByte);
		relatorioService.persistRelatorio(getSelectedRelatorio());
		return "/index.faces";
	}

	public Relatorio getSelectedRelatorio() {
		return selectedRelatorio;
	}

	public void setSelectedRelatorio(Relatorio selectedRelatorio) {
		this.selectedRelatorio = selectedRelatorio;
	}

	public Part getArquivoLayout() {
		return arquivoLayout;
	}

	public void setArquivoLayout(Part arquivoLayout) {
		this.arquivoLayout = arquivoLayout;
	}

	public List<Relatorio> getRelatorios() {
		return relatorioService.getRelatorios();
	}

}

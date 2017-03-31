package br.com.sysm.jasperreports.jreportbuilder.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.sysm.jasperreports.jreportbuilder.Relatorio;
import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JREllipse;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRGenericElement;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRStaticText;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRElementsVisitor;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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
    	File tmpDir =  File.createTempFile("tmp", "RelatorioDir");
    	tmpDir.mkdirs();
    	ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(rel.getRelatorio()));
    	ZipEntry ze = zis.getNextEntry();
    	while(ze != null) {
    		String arquivo = ze.getName();
    		File novoArquivo = new File(tmpDir.getAbsolutePath() + File.separator + arquivo);
    		new File(novoArquivo.getParent()).mkdirs();
    		System.out.println("Descomprimrindo " + arquivo +"...");
    	}
    }
    
    public JasperReport compileReport(String reportsPath,String reportName) throws Throwable{
  	  JasperDesign jasperDesign = JRXmlLoader.load(reportsPath + reportName + ".jrxml");
  	  JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
  	  JRSaver.saveObject(jasperReport, reportsPath + reportName + ".jasper");
  	  System.out.println("Saving compiled report to: " + reportsPath + reportName + ".jasper");
  	  //Compile sub reports
  	  JRElementsVisitor.visitReport(jasperReport, new JRVisitor(){
  	    @Override
  	    public void visitBreak(JRBreak breakElement){}

  	    @Override
  	    public void visitChart(JRChart chart){}

  	    @Override
  	    public void visitCrosstab(JRCrosstab crosstab){}

  	    @Override
  	    public void visitElementGroup(JRElementGroup elementGroup){}

  	    @Override
  	    public void visitEllipse(JREllipse ellipse){}

  	    @Override
  	    public void visitFrame(JRFrame frame){}

  	    @Override
  	    public void visitImage(JRImage image){}

  	    @Override
  	    public void visitLine(JRLine line){}

  	    @Override
  	    public void visitRectangle(JRRectangle rectangle){}

  	    @Override
  	    public void visitStaticText(JRStaticText staticText){}

  	    @Override
  	    public void visitSubreport(JRSubreport subreport){
  	      try{
  	        String expression = subreport.getExpression().getText().replace(".jasper", "");
  	        StringTokenizer st = new StringTokenizer(expression, "\"/");
  	        String subReportName = null;
  	        while(st.hasMoreTokens())
  	          subReportName = st.nextToken();
  	        if(completedSubReports.contains(subReportName)) return;
  	        completedSubReports.add(subReportName);
  	        compileReport(reportsPath,subReportName);
  	      }
  	      catch(Throwable e){
  	        subReportException = e;
  	      }
  	    }	
  	  @Override
	    public void visitTextField(JRTextField textField){}

	    @Override
	    public void visitComponentElement(JRComponentElement componentElement){}

	    @Override
	    public void visitGenericElement(JRGenericElement element){}
	  });
	  if(subReportException != null) throw new RuntimeException(subReportException);
	  return jasperReport;
	}
    
    
}

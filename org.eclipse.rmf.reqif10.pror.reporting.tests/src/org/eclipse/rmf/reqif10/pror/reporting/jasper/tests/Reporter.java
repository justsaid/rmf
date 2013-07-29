package org.eclipse.rmf.reqif10.pror.reporting.jasper.tests;



import java.beans.Encoder;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;


public class Reporter {
	
	@SuppressWarnings("unchecked")
	public static void makeMapReport(ArrayList<Map<String, ?>> specMapList) throws Exception {
		InputStream inputStream = new FileInputStream(
				"templates/jasper_report_template_back.jrxml");

		JRMapCollectionDataSource  colDataSource = new JRMapCollectionDataSource(
				specMapList);

		Map parameters = new HashMap();

		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		
		JasperReport jasperReport = JasperCompileManager
				.compileReport(jasperDesign);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				parameters, colDataSource);
		
		JasperExportManager.exportReportToPdfFile(jasperPrint,
				"templates/test_jasper.pdf");
		
//		JasperViewer.viewReport(jasperPrint,false);
	}
	
	
	public static void makeJRXMLFromMemo(JasperDesign jaspDesign) throws JRException{
		JRXmlWriter.writeReport(jaspDesign, "build/reports/noMemoReport.jrxml");
		
	}
}
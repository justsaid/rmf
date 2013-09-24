package org.eclipse.rmf.reqif10.pror.reporting.jasper.tests;



import java.beans.Encoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

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
		String path = "build/reports/noMemoReport.jrxml";
//		String path = "templates/jasper_report_template_back.jrxml";
		
		File file = new File(path);
		int count = file.getAbsoluteFile().getName().charAt(0);
		count++;
		
		InputStream inputStream = new FileInputStream(
				path);

		JRMapCollectionDataSource  colDataSource = new JRMapCollectionDataSource(
				specMapList);

		Map parameters = new HashMap();

		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		
		JasperReport jasperReport = JasperCompileManager
				.compileReport(jasperDesign);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				parameters, colDataSource);
		
		JasperExportManager.exportReportToPdfFile(jasperPrint,
				"templates/"+ file.getAbsoluteFile().getName()+".pdf");
		
		
//		JasperViewer.viewReport(jasperPrint,false);
	}
	
	
	public static void makeJRXMLFromMemo(JasperDesign jaspDesign) throws JRException{
		JRXmlWriter.writeReport(jaspDesign, "build/reports/noMemoReport.jrxml");
		
	}
}
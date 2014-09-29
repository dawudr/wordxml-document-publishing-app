package com.pearson.btec.service;

import com.pearson.btec.config.Properties;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;

public class ReportXsltTransformer {
	
	private static String slash = File.separator;
	private static String output_path = Properties.host_output_path + slash;
	private static String xsl_path = Properties.host_xsl_path + slash;
	
	public ReportXsltTransformer() {
	}

	public static String transformReportXML(String xmlFileNameinput) {
		String xmlfilename = xmlFileNameinput.substring(0, xmlFileNameinput.lastIndexOf(".")) + "-transformed.xml";
		String outputFile = output_path + xmlfilename;
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new StreamSource(xsl_path + "report.xslt"));
			transformer.transform(new StreamSource(output_path + xmlFileNameinput), new StreamResult(new FileOutputStream(outputFile)));
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return xmlfilename;
	}
	
	public static String transformReportHTML(String xmlFileNameinput) {
		String htmlfilename = xmlFileNameinput.substring(0, xmlFileNameinput.lastIndexOf(".")) + "-transformed.html";
		String outputFile = output_path + htmlfilename;
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new StreamSource(xsl_path + "reportviewhtml5.xslt"));
			transformer.transform(new StreamSource(output_path + xmlFileNameinput), new StreamResult(new FileOutputStream(outputFile)));
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return htmlfilename;
	}
	
	public static void main(String[] args) {
	    System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");  
		ReportXsltTransformer r = new ReportXsltTransformer();
		String xmlFileNameinput = "FES_nov30_2012.xml";

		r.transformReportXML(xmlFileNameinput);
		r.transformReportHTML(xmlFileNameinput);
	}
}

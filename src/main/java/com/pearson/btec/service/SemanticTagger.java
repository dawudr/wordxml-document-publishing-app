package com.pearson.btec.service;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SemanticTagger {

	private SAXBuilder builder = new SAXBuilder();
	private final Logger logger = LoggerFactory.getLogger(SemanticTagger.class);
	private String slash = File.separator;

	public Element getSemanticTagsElement(String filename) throws IOException {
		logger.info("Collecting Aptara semantic Tags from {} " , filename);

		File xmlFile = new File(filename);
		Element tagnode = null;
		
		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("tags");

			tagnode = (Element) list.get(0); 

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		return tagnode;
	}

	public static void main(String[] args) {

		String input = "C:\\Temp\\bca.fes_wr_2012_11_30.xml";
		
		SemanticTagger st = new SemanticTagger();
		try {
			
			
			
			List<Element> childs = st.getSemanticTagsElement(input).getChildren();
			for (Element tagElement : childs) {
				System.out.println("NODE: " + tagElement.getName());
				System.out.println("ID: " + tagElement.getAttributeValue("id"));
				System.out.println("TYPE: " + tagElement.getAttributeValue("type"));
				System.out.println("VALUE: " + tagElement.getValue());
			}			

		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}

}

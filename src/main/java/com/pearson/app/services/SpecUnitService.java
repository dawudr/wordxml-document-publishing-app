package com.pearson.app.services;

import com.pearson.app.dao.SpecUnitRepository;
import com.pearson.app.dto.TransformationDTO;
import com.pearson.app.model.Specunit;
import com.pearson.app.model.Transformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import static com.pearson.app.services.ValidationUtils.assertNotBlank;

@Service
public class SpecUnitService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecUnitService.class);

    @Autowired
    private SpecUnitRepository specUnitRepository;

    @Transactional
    public void addSpecUnit(Specunit specunit) {
        //assertNotBlank(specunit.getQanNo(), "SpecUnit QanNo type cannot be empty.");
        specUnitRepository.addSpecUnit(specunit);
    }

    @Transactional(readOnly = true)
    public List<Specunit> listSpecUnits() {
        return specUnitRepository.listSpecUnits();
    }

    @Transactional(readOnly = true)
    public Specunit getSpecUnitById(Integer id) {
        return specUnitRepository.getSpecUnitById(id);
    }

    @Transactional(readOnly = true)
    public Specunit getSpecUnitByQanNo(String qanNo) {
        return specUnitRepository.getSpecUnitByQanNo(qanNo);
    }

    @Transactional
    public void updateSpecUnit(Specunit specunit) {
        specUnitRepository.updateSpecUnit(specunit);
    }

    @Transactional
    public void removeSpecUnit(Integer id) {
        specUnitRepository.removeSpecUnit(id);
    }

    // Update the XML in the SpecUnit table with fields from the Transform details screen frontend
    @Transactional
    public void updateSpecUnitMetadata(TransformationDTO transformationDTO) {
        LOGGER.debug("Updating SpecUnit XML Document with form data: [{}]", transformationDTO.toString());
        // Get SpecUnit's XML
        Specunit specunit = getSpecUnitById(transformationDTO.getSpecunit());
        String xml = specunit.getUnitXML();
        String xmlResult = null;

        updateSpecUnitXMLNode(xml, transformationDTO);

        specunit.setQanNo(transformationDTO.getQanNo());
        specunit.setUnitXML(xmlResult);
        updateSpecUnit(specunit);
        LOGGER.debug("Successfully Updated SpecUnit XML Document for SpecUnitId: [{}] QAN: [{}]", transformationDTO.getSpecunit(), transformationDTO.getQanNo());
    }

    /**
     * Updates the Unit's XML Metadata section when a transformation record is updated.
     * @param xml
     * @param transformationDTO
     * @return
     */
    public String updateSpecUnitXMLNode(String xml, TransformationDTO transformationDTO) {
        String xmlResult = null;

        try {
            DocumentBuilderFactory documentBuilderFactory   = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document document = documentBuilder.parse(is);

            Node unit = document.getElementsByTagName("unit").item(0);
            NodeList nodelist = unit.getChildNodes();

            for (int i = 0; i < nodelist.getLength(); i++) {
                Node node = nodelist.item(i);

                if(node.getNodeName().equalsIgnoreCase("uan")) {
                    node.setTextContent(transformationDTO.getQanNo());
                }

                if(node.getNodeName().equalsIgnoreCase("unitnumber")) {
                    node.setTextContent(transformationDTO.getUnitNo());
                }

                if(node.getNodeName().equalsIgnoreCase("unittitle")) {
                    node.setTextContent(transformationDTO.getUnitTitle());
                }

                if(node.getNodeName().equalsIgnoreCase("author")) {
                    node.setTextContent(transformationDTO.getAuthor());
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            xmlResult = writer.getBuffer().toString();
            //LOGGER.debug("Modified Document XML: {}", xmlResult);

        } catch (ParserConfigurationException e) {
            LOGGER.error("Error updating QAN no in document XML ParserConfigurationException: {}", e);
        } catch (SAXException e) {
            LOGGER.error("Error updating QAN no in document XML SAXException: {}", e);
        } catch (IOException e) {
            LOGGER.error("Error updating QAN no in document XML IOException: {}", e);
        } catch (TransformerConfigurationException e) {
            LOGGER.error("Error updating QAN no in document XML TransformerConfigurationException: {}", e);
        } catch (TransformerException e) {
            LOGGER.error("Error updating QAN no in document XML TransformerException: {}", e);
        }

        return xmlResult;
    }


}

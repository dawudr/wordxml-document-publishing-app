package com.pearson.btec.service.importers;


import com.pearson.app.controllers.AppURIConstants;
import net.sf.saxon.s9api.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class TableContentPreProcessor {

    public static void main(String[] args) throws Exception {
        //String textXmlString = "<w:tbl xmlns:dsp=\"http://schemas.microsoft.com/office/drawing/2008/diagram\" xmlns:odx=\"http://opendope.org/xpaths\" xmlns:xdr=\"http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing\" xmlns:odgm=\"http://opendope.org/SmartArt/DataHierarchy\" xmlns:dgm=\"http://schemas.openxmlformats.org/drawingml/2006/diagram\" xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\" xmlns:ns31=\"http://schemas.openxmlformats.org/drawingml/2006/compatibility\" xmlns:ns30=\"http://schemas.openxmlformats.org/officeDocument/2006/bibliography\" xmlns:ns9=\"http://schemas.openxmlformats.org/schemaLibrary/2006/main\" xmlns:ns12=\"http://schemas.openxmlformats.org/drawingml/2006/chartDrawing\" xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:ns32=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\" xmlns:ns17=\"urn:schemas-microsoft-com:office:excel\" xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" xmlns:w15=\"http://schemas.microsoft.com/office/word/2012/wordml\" xmlns:w14=\"http://schemas.microsoft.com/office/word/2010/wordml\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" xmlns:oda=\"http://opendope.org/answers\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:odc=\"http://opendope.org/conditions\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:ns23=\"http://schemas.microsoft.com/office/2006/coverPageProps\" xmlns:odi=\"http://opendope.org/components\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:ns21=\"urn:schemas-microsoft-com:office:powerpoint\" xmlns:odq=\"http://opendope.org/questions\"><w:tblPr><w:tblW w:w=\"5141\" w:type=\"pct\"/><w:tblInd w:w=\"-5\" w:type=\"dxa\"/><w:tblBorders><w:top w:val=\"single\" w:color=\"9393B7\" w:sz=\"4\" w:space=\"0\"/><w:left w:val=\"single\" w:color=\"9393B7\" w:sz=\"4\" w:space=\"0\"/><w:bottom w:val=\"single\" w:color=\"9393B7\" w:sz=\"4\" w:space=\"0\"/><w:right w:val=\"single\" w:color=\"9393B7\" w:sz=\"4\" w:space=\"0\"/><w:insideH w:val=\"single\" w:color=\"9393B7\" w:sz=\"4\" w:space=\"0\"/><w:insideV w:val=\"single\" w:color=\"9393B7\" w:sz=\"4\" w:space=\"0\"/></w:tblBorders><w:tblLayout w:type=\"fixed\"/><w:tblLook w:firstRow=\"1\" w:lastRow=\"1\" w:firstColumn=\"1\" w:lastColumn=\"1\" w:noHBand=\"0\" w:noVBand=\"0\" w:val=\"01E0\"/></w:tblPr><w:tblGrid><w:gridCol w:w=\"3120\"/><w:gridCol w:w=\"3117\"/><w:gridCol w:w=\"3119\"/></w:tblGrid><w:tr w:rsidRPr=\"004E7C29\" w:rsidR=\"007B75EE\" w:rsidTr=\"007B75EE\" w14:paraId=\"0F70A82D\" w14:textId=\"77777777\"><w:tc><w:tcPr><w:tcW w:w=\"1667\" w:type=\"pct\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"7070A0\"/></w:tcPr><w:p w:rsidRPr=\"004E7C29\" w:rsidR=\"002B0DE1\" w:rsidP=\"007336F0\" w:rsidRDefault=\"002B0DE1\" w14:paraId=\"6098F80F\" w14:textId=\"77777777\"><w:pPr><w:pStyle w:val=\"Tablehead\"/></w:pPr><w:r w:rsidRPr=\"004E7C29\"><w:lastRenderedPageBreak/><w:t>Learning Aim</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"1666\" w:type=\"pct\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"7070A0\"/></w:tcPr><w:p w:rsidRPr=\"004E7C29\" w:rsidR=\"002B0DE1\" w:rsidP=\"007336F0\" w:rsidRDefault=\"002B0DE1\" w14:paraId=\"6771448C\" w14:textId=\"77777777\"><w:pPr><w:pStyle w:val=\"Tablehead\"/></w:pPr><w:r w:rsidRPr=\"004E7C29\"><w:t xml:space=\"preserve\">Key teaching </w:t></w:r><w:r><w:t>areas</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"1667\" w:type=\"pct\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"7070A0\"/></w:tcPr><w:p w:rsidRPr=\"004E7C29\" w:rsidR=\"002B0DE1\" w:rsidP=\"007336F0\" w:rsidRDefault=\"002B0DE1\" w14:paraId=\"7F5F8B51\" w14:textId=\"77777777\"><w:pPr><w:pStyle w:val=\"Tablehead\"/></w:pPr><w:r><w:t>Suggested s</w:t></w:r><w:r w:rsidRPr=\"004E7C29\"><w:t>ummary of assessment evidence</w:t></w:r></w:p></w:tc></w:tr><w:tr w:rsidRPr=\"00130BC7\" w:rsidR=\"007B75EE\" w:rsidTr=\"00722069\" w14:paraId=\"38EA8540\" w14:textId=\"77777777\"><w:trPr><w:trHeight w:val=\"1520\"/></w:trPr><w:tc><w:tcPr><w:tcW w:w=\"1667\" w:type=\"pct\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"DFDFE9\"/></w:tcPr><w:p w:rsidR=\"00435448\" w:rsidP=\"00435448\" w:rsidRDefault=\"00435448\" w14:paraId=\"7A65D5BA\" w14:textId=\"13B00651\"><w:pPr><w:pStyle w:val=\"LearningObjective\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"240\"/><w:tab w:val=\"left\" w:pos=\"0\"/></w:tabs><w:ind w:left=\"284\" w:right=\"0\" w:hanging=\"284\"/><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr><w:alias w:val=\"Learning Objective Ref\"/><w:tag w:val=\"learningobjectiveref\"/><w:id w:val=\"487142036\"/><w:placeholder><w:docPart w:val=\"FB78D8C2D37449E3B18BC7326550C40C\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b/></w:rPr></w:sdtEndPr><w:sdtContent><w:r w:rsidR=\"00DA2ABA\"><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr><w:t>A</w:t></w:r></w:sdtContent></w:sdt><w:r><w:rPr><w:b/></w:rPr><w:tab/></w:r><w:sdt><w:sdtPr><w:rPr><w:b/><w:color w:val=\"666699\"/><w:szCs w:val=\"22\"/></w:rPr><w:alias w:val=\"Learning Objective\"/><w:tag w:val=\"learningobjectivetext\"/><w:id w:val=\"1497454236\"/><w:placeholder><w:docPart w:val=\"A71667CC0A234C71B94FC47C7F24A33E\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"00DA2ABA\" w:rsidR=\"00DA2ABA\"><w:rPr><w:b/><w:color w:val=\"666699\"/><w:szCs w:val=\"22\"/></w:rPr><w:t>Understand the technology and characteristics of secondary machining processes that are widely used in industry</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidRPr=\"00CE33E7\" w:rsidR=\"007B75EE\" w:rsidP=\"00435448\" w:rsidRDefault=\"007B75EE\" w14:paraId=\"7129DE55\" w14:textId=\"6E5A0529\"><w:pPr><w:pStyle w:val=\"LearningObjective\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"240\"/><w:tab w:val=\"left\" w:pos=\"0\"/></w:tabs><w:ind w:left=\"0\" w:right=\"0\" w:firstLine=\"0\"/><w:rPr><w:rFonts w:eastAsia=\"Times New Roman\"/><w:b/><w:color w:val=\"666699\"/><w:szCs w:val=\"22\"/></w:rPr></w:pPr></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"1666\" w:type=\"pct\"/></w:tcPr><w:p w:rsidR=\"007B75EE\" w:rsidP=\"00D050A3\" w:rsidRDefault=\"007B75EE\" w14:paraId=\"6E435A63\" w14:textId=\"51F01D6B\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"403\" w:hanging=\"369\"/></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:alias w:val=\"Key Teaching Reference\"/><w:tag w:val=\"keyteachingref\"/><w:id w:val=\"-1592695474\"/><w:placeholder><w:docPart w:val=\"470B2942496E41058023D8171DD31A1C\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b w:val=\"false\"/></w:rPr></w:sdtEndPr><w:sdtContent><w:r w:rsidR=\"001C27A2\"><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:t>A1</w:t></w:r></w:sdtContent></w:sdt><w:r w:rsidR=\"00D050A3\"><w:t xml:space=\"preserve\"> </w:t></w:r><w:sdt><w:sdtPr><w:alias w:val=\"Key Teaching Text\"/><w:tag w:val=\"keyteachingtext\"/><w:id w:val=\"-81295495\"/><w:lock w:val=\"sdtLocked\"/><w:placeholder><w:docPart w:val=\"22C4224FEF0D4DCAAF77CBFBC99E1E3A\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"001C27A2\" w:rsidR=\"001C27A2\"><w:t>Traditional secondary machining processes</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"0FB9CC43\" w14:textId=\"50259988\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"403\" w:hanging=\"369\"/></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:alias w:val=\"Key Teaching Reference\"/><w:tag w:val=\"keyteachingref\"/><w:id w:val=\"-914544625\"/><w:placeholder><w:docPart w:val=\"B93825BB11AD4769AAD5D2C26797728E\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b w:val=\"false\"/></w:rPr></w:sdtEndPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:t>A2</w:t></w:r></w:sdtContent></w:sdt><w:r><w:t xml:space=\"preserve\"> </w:t></w:r><w:sdt><w:sdtPr><w:alias w:val=\"Key Teaching Text\"/><w:tag w:val=\"keyteachingtext\"/><w:id w:val=\"541099494\"/><w:placeholder><w:docPart w:val=\"2FD24FB0617042529136EB83A5E27568\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"001C27A2\"><w:t>Specialist secondary machining processes</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"2E24E7B7\" w14:textId=\"199CDB4C\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"0\" w:firstLine=\"0\"/></w:pPr></w:p><w:p w:rsidRPr=\"00B20F75\" w:rsidR=\"007B75EE\" w:rsidP=\"00D050A3\" w:rsidRDefault=\"007B75EE\" w14:paraId=\"28EF7D61\" w14:textId=\"6240AB5C\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs></w:pPr></w:p></w:tc><w:sdt><w:sdtPr><w:alias w:val=\"Suggested Summary Assessment Evidence\"/><w:tag w:val=\"keyteachingassessmentevidence\"/><w:id w:val=\"1535149684\"/><w:placeholder><w:docPart w:val=\"56CBFD087ECE4C518AFE64AF303F2F46\"/></w:placeholder></w:sdtPr><w:sdtContent><w:tc><w:tcPr><w:tcW w:w=\"1667\" w:type=\"pct\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"auto\"/></w:tcPr><w:p w:rsidRPr=\"00130BC7\" w:rsidR=\"007B75EE\" w:rsidP=\"007336F0\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"06A9BDFF\" w14:textId=\"6BFC2F17\"><w:pPr><w:pStyle w:val=\"Tabletext\"/></w:pPr><w:r w:rsidRPr=\"00AA6CDC\"><w:t xml:space=\"preserve\">A </w:t></w:r><w:r w:rsidRPr=\"00CE33E7\"><w:t>report focussing on three different traditional and an analysis of research case studies on three different specialist processes</w:t></w:r><w:r><w:t>.</w:t></w:r></w:p></w:tc></w:sdtContent></w:sdt></w:tr><w:tr w:rsidRPr=\"00130BC7\" w:rsidR=\"001C27A2\" w:rsidTr=\"00722069\" w14:paraId=\"41FC2808\" w14:textId=\"77777777\"><w:trPr><w:trHeight w:val=\"1558\"/></w:trPr><w:tc><w:tcPr><w:tcW w:w=\"1667\" w:type=\"pct\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"DFDFE9\"/></w:tcPr><w:p w:rsidR=\"001C27A2\" w:rsidP=\"00435448\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"67CF9345\" w14:textId=\"4900DD93\"><w:pPr><w:pStyle w:val=\"LearningObjective\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"240\"/><w:tab w:val=\"left\" w:pos=\"0\"/></w:tabs><w:ind w:left=\"284\" w:right=\"0\" w:hanging=\"284\"/><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr><w:alias w:val=\"Learning Objective Ref\"/><w:tag w:val=\"learningobjectiveref\"/><w:id w:val=\"1639068051\"/><w:placeholder><w:docPart w:val=\"CC4715ACB1A74B4AA8E286124F327E07\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b/></w:rPr></w:sdtEndPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr><w:t>B</w:t></w:r></w:sdtContent></w:sdt><w:r><w:rPr><w:b/></w:rPr><w:tab/></w:r><w:sdt><w:sdtPr><w:rPr><w:b/><w:color w:val=\"666699\"/><w:szCs w:val=\"20\"/></w:rPr><w:alias w:val=\"Learning Objective\"/><w:tag w:val=\"learningobjectivetext\"/><w:id w:val=\"803970167\"/><w:placeholder><w:docPart w:val=\"A3DFFB8976434F579C63A48FDB6F6661\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"00DA2ABA\"><w:rPr><w:b/><w:color w:val=\"666699\"/><w:szCs w:val=\"20\"/></w:rPr><w:t>Set-up traditional secondary processing machines sa</w:t></w:r><w:r><w:rPr><w:b/><w:color w:val=\"666699\"/><w:szCs w:val=\"20\"/></w:rPr><w:t>fely to manufacture a component</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidRPr=\"007B75EE\" w:rsidR=\"001C27A2\" w:rsidP=\"007336F0\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"16310C6B\" w14:textId=\"5EE54112\"><w:pPr><w:pStyle w:val=\"LearningObjective\"/><w:ind w:right=\"0\"/><w:rPr><w:rFonts w:eastAsia=\"Times New Roman\"/><w:b/><w:color w:val=\"666699\"/><w:szCs w:val=\"22\"/><w:highlight w:val=\"yellow\"/></w:rPr></w:pPr></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"1666\" w:type=\"pct\"/></w:tcPr><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"2F7E10B2\" w14:textId=\"35DA7125\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"403\" w:hanging=\"369\"/></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:alias w:val=\"Key Teaching Reference\"/><w:tag w:val=\"keyteachingref\"/><w:id w:val=\"744531919\"/><w:placeholder><w:docPart w:val=\"0C493F6D92B44F2696F510AD7493EB6C\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b w:val=\"false\"/></w:rPr></w:sdtEndPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:t>B1</w:t></w:r></w:sdtContent></w:sdt><w:r><w:t xml:space=\"preserve\"> </w:t></w:r><w:sdt><w:sdtPr><w:alias w:val=\"Key Teaching Text\"/><w:tag w:val=\"keyteachingtext\"/><w:id w:val=\"338426529\"/><w:placeholder><w:docPart w:val=\"BF4E394F8DD644CE8E711A953098DFC7\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"001C27A2\"><w:t>Health and safety requirements when setting up secondary process machines</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"5488A37B\" w14:textId=\"1F631445\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"403\" w:hanging=\"369\"/></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:alias w:val=\"Key Teaching Reference\"/><w:tag w:val=\"keyteachingref\"/><w:id w:val=\"-772866143\"/><w:placeholder><w:docPart w:val=\"05A695E1404E4551BC5D68EAAB8CB31E\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b w:val=\"false\"/></w:rPr></w:sdtEndPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:t>B2</w:t></w:r></w:sdtContent></w:sdt><w:r><w:t xml:space=\"preserve\"> </w:t></w:r><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"Unitinfonorule\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr><w:alias w:val=\"Key Teaching Text\"/><w:tag w:val=\"keyteachingtext\"/><w:id w:val=\"1102760841\"/><w:placeholder><w:docPart w:val=\"220A0C89C3C94F19AD3F5B02CEFEF051\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"Unitinfonorule\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr><w:t>Risk assessment</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"34D4FC78\" w14:textId=\"72EFF6CF\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"403\" w:hanging=\"369\"/></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:alias w:val=\"Key Teaching Reference\"/><w:tag w:val=\"keyteachingref\"/><w:id w:val=\"519436873\"/><w:placeholder><w:docPart w:val=\"570AA2EE6905424CB84A61578EA6AE92\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b w:val=\"false\"/></w:rPr></w:sdtEndPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:t>B3</w:t></w:r></w:sdtContent></w:sdt><w:r><w:t xml:space=\"preserve\"> </w:t></w:r><w:sdt><w:sdtPr><w:alias w:val=\"Key Teaching Text\"/><w:tag w:val=\"keyteachingtext\"/><w:id w:val=\"541173079\"/><w:placeholder><w:docPart w:val=\"C026A8DA8BC945C2B654C4D1B109BD2E\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"001C27A2\"><w:t>Setting up secondary process machines</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidRPr=\"007B75EE\" w:rsidR=\"001C27A2\" w:rsidP=\"00D050A3\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"72B61DBD\" w14:textId=\"77777777\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"0\"/></w:tabs><w:rPr><w:rStyle w:val=\"PlaceholderText\"/><w:rFonts w:eastAsia=\"Batang\"/><w:b/></w:rPr></w:pPr></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"1667\" w:type=\"pct\"/><w:vMerge w:val=\"restart\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"auto\"/></w:tcPr><w:sdt><w:sdtPr><w:alias w:val=\"Suggested Summary Assessment Evidence\"/><w:tag w:val=\"KTA_AssessmentEvidence\"/><w:id w:val=\"630138102\"/><w:placeholder><w:docPart w:val=\"5409D5E1CCEF4DC7B4D8A1E1A2E8B03D\"/></w:placeholder></w:sdtPr><w:sdtContent><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"24D7113E\" w14:textId=\"77777777\"><w:pPr><w:pStyle w:val=\"Tabletext\"/></w:pPr><w:r w:rsidRPr=\"00AA6CDC\"><w:t>A practical activity involving</w:t></w:r><w:r><w:t xml:space=\"preserve\"> a risk assessment and</w:t></w:r><w:r w:rsidRPr=\"00AA6CDC\"><w:t xml:space=\"preserve\"> the setting up of </w:t></w:r><w:r><w:t>at least two traditional machining processes and the machining of a</w:t></w:r><w:r w:rsidRPr=\"00AA6CDC\"><w:t xml:space=\"preserve\"> compone</w:t></w:r><w:r><w:t>nt.</w:t></w:r></w:p><w:p w:rsidRPr=\"00BB1B89\" w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"01626710\" w14:textId=\"77777777\"><w:pPr><w:pStyle w:val=\"Tabletext\"/><w:rPr><w:rStyle w:val=\"PlaceholderText\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr></w:pPr><w:r w:rsidRPr=\"00AA6CDC\"><w:t xml:space=\"preserve\">Evidence will be </w:t></w:r><w:r><w:t>a risk assessment, observation records/witness s</w:t></w:r><w:r w:rsidRPr=\"00AA6CDC\"><w:t>tatements, annotate</w:t></w:r><w:r><w:t>d photographs and drawings, set-</w:t></w:r><w:r w:rsidRPr=\"00AA6CDC\"><w:t>up planning notes</w:t></w:r><w:r><w:t>, completed quality control documents/annotated drawings, notes explaining the health and safety requirements.</w:t></w:r></w:p></w:sdtContent></w:sdt><w:p w:rsidRPr=\"00BB1B89\" w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"4F9FD6B1\" w14:textId=\"36BC7D8B\"><w:pPr><w:pStyle w:val=\"Tabletext\"/><w:rPr><w:rStyle w:val=\"PlaceholderText\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr></w:pPr></w:p></w:tc></w:tr><w:tr w:rsidRPr=\"00130BC7\" w:rsidR=\"001C27A2\" w:rsidTr=\"00722069\" w14:paraId=\"7A986487\" w14:textId=\"77777777\"><w:trPr><w:trHeight w:val=\"1537\"/></w:trPr><w:tc><w:tcPr><w:tcW w:w=\"1667\" w:type=\"pct\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"DFDFE9\"/></w:tcPr><w:p w:rsidR=\"001C27A2\" w:rsidP=\"00435448\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"4F5FD073\" w14:textId=\"785929F4\"><w:pPr><w:pStyle w:val=\"LearningObjective\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"240\"/><w:tab w:val=\"left\" w:pos=\"0\"/></w:tabs><w:ind w:left=\"284\" w:right=\"0\" w:hanging=\"284\"/><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr><w:alias w:val=\"Learning Objective Ref\"/><w:tag w:val=\"learningobjectiveref\"/><w:id w:val=\"-284196019\"/><w:placeholder><w:docPart w:val=\"E748554FE4B54029852C8F4BCE639959\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b/></w:rPr></w:sdtEndPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr><w:t>C</w:t></w:r></w:sdtContent></w:sdt><w:r><w:rPr><w:b/></w:rPr><w:tab/></w:r><w:sdt><w:sdtPr><w:rPr><w:b/><w:color w:val=\"666699\"/></w:rPr><w:alias w:val=\"Learning Objective\"/><w:tag w:val=\"learningobjectivetext\"/><w:id w:val=\"-2123527846\"/><w:placeholder><w:docPart w:val=\"3C3908D8E0174378AB99F23750890CAD\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"00D050A3\"><w:rPr><w:b/><w:color w:val=\"666699\"/></w:rPr><w:t>Carry out traditional secondary machining processes to safely manufacture a component</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidRPr=\"007B75EE\" w:rsidR=\"001C27A2\" w:rsidP=\"007336F0\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"64E5A5E5\" w14:textId=\"1BFE30E0\"><w:pPr><w:pStyle w:val=\"LearningObjective\"/><w:ind w:right=\"0\"/><w:rPr><w:rFonts w:eastAsia=\"Times New Roman\"/><w:b/><w:color w:val=\"666699\"/><w:szCs w:val=\"22\"/><w:highlight w:val=\"yellow\"/></w:rPr></w:pPr></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"1666\" w:type=\"pct\"/></w:tcPr><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"70FCF77F\" w14:textId=\"051A6712\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"403\" w:hanging=\"369\"/></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:alias w:val=\"Key Teaching Reference\"/><w:tag w:val=\"keyteachingref\"/><w:id w:val=\"-1522701913\"/><w:placeholder><w:docPart w:val=\"005F0A6980DB4FDF8C106F8238C64D65\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b w:val=\"false\"/></w:rPr></w:sdtEndPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:t>C1</w:t></w:r></w:sdtContent></w:sdt><w:r><w:t xml:space=\"preserve\"> </w:t></w:r><w:sdt><w:sdtPr><w:alias w:val=\"Key Teaching Text\"/><w:tag w:val=\"keyteachingtext\"/><w:id w:val=\"-1864827871\"/><w:placeholder><w:docPart w:val=\"01D638A3EF2E4E4FBC7BF8A97D5473E8\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"001C27A2\"><w:t>Features of traditional secondary machining processes</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"72CEABE2\" w14:textId=\"4597F582\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"403\" w:hanging=\"369\"/></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:alias w:val=\"Key Teaching Reference\"/><w:tag w:val=\"keyteachingref\"/><w:id w:val=\"1409578273\"/><w:placeholder><w:docPart w:val=\"CDDD8AAAC4B344A6BAA2A1FDA66A1129\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b w:val=\"false\"/></w:rPr></w:sdtEndPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:t>C2</w:t></w:r></w:sdtContent></w:sdt><w:r><w:t xml:space=\"preserve\"> </w:t></w:r><w:sdt><w:sdtPr><w:alias w:val=\"Key Teaching Text\"/><w:tag w:val=\"keyteachingtext\"/><w:id w:val=\"-542433327\"/><w:placeholder><w:docPart w:val=\"4CD846BB4296462EB2CBF8ECBA252D3F\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"001C27A2\"><w:t>Parameters of traditional secondary machining processes</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"792DD7B0\" w14:textId=\"3109DABE\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"403\" w:hanging=\"369\"/></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:alias w:val=\"Key Teaching Reference\"/><w:tag w:val=\"keyteachingref\"/><w:id w:val=\"-1071885692\"/><w:placeholder><w:docPart w:val=\"FE31B5057E7D4C5CB382D145B79D1BE7\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b w:val=\"false\"/></w:rPr></w:sdtEndPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:t>C3</w:t></w:r></w:sdtContent></w:sdt><w:r><w:t xml:space=\"preserve\"> </w:t></w:r><w:sdt><w:sdtPr><w:alias w:val=\"Key Teaching Text\"/><w:tag w:val=\"keyteachingtext\"/><w:id w:val=\"-637030830\"/><w:placeholder><w:docPart w:val=\"CF5C14851BC242FC84D7FB2B1D8D7725\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"001C27A2\"><w:t>Quality control methods</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidRPr=\"007B75EE\" w:rsidR=\"001C27A2\" w:rsidP=\"00D050A3\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"591A1DD4\" w14:textId=\"77777777\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"0\"/></w:tabs><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr></w:pPr></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"1667\" w:type=\"pct\"/><w:vMerge/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"auto\"/></w:tcPr><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"59F72D79\" w14:textId=\"5FD4CA51\"><w:pPr><w:pStyle w:val=\"Tabletext\"/></w:pPr></w:p></w:tc></w:tr><w:tr w:rsidRPr=\"00130BC7\" w:rsidR=\"00722069\" w:rsidTr=\"00722069\" w14:paraId=\"6FCC12A2\" w14:textId=\"77777777\"><w:trPr><w:trHeight w:val=\"1559\"/></w:trPr><w:tc><w:tcPr><w:tcW w:w=\"1667\" w:type=\"pct\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"DFDFE9\"/></w:tcPr><w:p w:rsidR=\"00435448\" w:rsidP=\"00435448\" w:rsidRDefault=\"00435448\" w14:paraId=\"5985DDEA\" w14:textId=\"7C3CDFF9\"><w:pPr><w:pStyle w:val=\"LearningObjective\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"240\"/><w:tab w:val=\"left\" w:pos=\"0\"/></w:tabs><w:ind w:left=\"284\" w:right=\"0\" w:hanging=\"284\"/><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr><w:alias w:val=\"Learning Objective Ref\"/><w:tag w:val=\"learningobjectiveref\"/><w:id w:val=\"-746111295\"/><w:placeholder><w:docPart w:val=\"4A4A72E167EF400C973758CE17F8E961\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b/></w:rPr></w:sdtEndPr><w:sdtContent><w:r w:rsidR=\"00DA2ABA\"><w:rPr><w:rStyle w:val=\"TextChar\"/><w:rFonts w:eastAsia=\"Batang\"/></w:rPr><w:t>D</w:t></w:r></w:sdtContent></w:sdt><w:r><w:rPr><w:b/></w:rPr><w:tab/></w:r><w:sdt><w:sdtPr><w:rPr><w:b/><w:color w:val=\"666699\"/></w:rPr><w:alias w:val=\"Learning Objective\"/><w:tag w:val=\"learningobjectivetext\"/><w:id w:val=\"-931192540\"/><w:placeholder><w:docPart w:val=\"C74FE0C2E8BF4B0EB87FC65D98FCAAA6\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"00D050A3\" w:rsidR=\"00D050A3\"><w:rPr><w:b/><w:color w:val=\"666699\"/></w:rPr><w:t>Review the processes used to machine a component and reflect on own performance</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidRPr=\"007B75EE\" w:rsidR=\"00722069\" w:rsidP=\"007336F0\" w:rsidRDefault=\"00722069\" w14:paraId=\"0E1C8580\" w14:textId=\"01A5B2FB\"><w:pPr><w:pStyle w:val=\"LearningObjective\"/><w:ind w:right=\"0\"/><w:rPr><w:rFonts w:eastAsia=\"Times New Roman\"/><w:b/><w:color w:val=\"666699\"/><w:szCs w:val=\"22\"/><w:highlight w:val=\"yellow\"/></w:rPr></w:pPr></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"1666\" w:type=\"pct\"/></w:tcPr><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"0CFE3E39\" w14:textId=\"4B6B0852\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"403\" w:hanging=\"369\"/></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:alias w:val=\"Key Teaching Reference\"/><w:tag w:val=\"keyteachingref\"/><w:id w:val=\"1262647539\"/><w:placeholder><w:docPart w:val=\"E93152E81FA34EA89B2855D68B4B4FA4\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b w:val=\"false\"/></w:rPr></w:sdtEndPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:t>D1</w:t></w:r></w:sdtContent></w:sdt><w:r><w:t xml:space=\"preserve\"> </w:t></w:r><w:sdt><w:sdtPr><w:alias w:val=\"Key Teaching Text\"/><w:tag w:val=\"keyteachingtext\"/><w:id w:val=\"1301730502\"/><w:placeholder><w:docPart w:val=\"447143752505416187AE6941A64CB9FA\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"001C27A2\"><w:t>Lessons learnt from machining a component</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"3C131741\" w14:textId=\"5E6101FF\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"403\" w:hanging=\"369\"/></w:pPr><w:sdt><w:sdtPr><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:alias w:val=\"Key Teaching Reference\"/><w:tag w:val=\"keyteachingref\"/><w:id w:val=\"794187597\"/><w:placeholder><w:docPart w:val=\"F9219DF964FC43E3A31A6E39359DE910\"/></w:placeholder><w:text/></w:sdtPr><w:sdtEndPr><w:rPr><w:rStyle w:val=\"DefaultParagraphFont\"/><w:b w:val=\"false\"/></w:rPr></w:sdtEndPr><w:sdtContent><w:r><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr><w:t>D2</w:t></w:r></w:sdtContent></w:sdt><w:r><w:t xml:space=\"preserve\"> </w:t></w:r><w:sdt><w:sdtPr><w:alias w:val=\"Key Teaching Text\"/><w:tag w:val=\"keyteachingtext\"/><w:id w:val=\"1775743371\"/><w:placeholder><w:docPart w:val=\"F80DBC6DB4014AE2A2EB9D87533F5D88\"/></w:placeholder><w:text/></w:sdtPr><w:sdtContent><w:r w:rsidRPr=\"001C27A2\"><w:t>Personal performance whilst machining a component</w:t></w:r></w:sdtContent></w:sdt></w:p><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"71E5B82F\" w14:textId=\"62188D4A\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"316\"/></w:tabs><w:ind w:left=\"403\" w:hanging=\"369\"/></w:pPr><w:r><w:t xml:space=\"preserve\"> </w:t></w:r></w:p><w:p w:rsidRPr=\"007B75EE\" w:rsidR=\"00722069\" w:rsidP=\"00D050A3\" w:rsidRDefault=\"00722069\" w14:paraId=\"773A4541\" w14:textId=\"77777777\"><w:pPr><w:pStyle w:val=\"KeyTeachingcoltext\"/><w:tabs><w:tab w:val=\"clear\" w:pos=\"400\"/><w:tab w:val=\"left\" w:pos=\"0\"/></w:tabs><w:rPr><w:rStyle w:val=\"KTARef\"/></w:rPr></w:pPr></w:p></w:tc><w:sdt><w:sdtPr><w:alias w:val=\"Suggested Summary Assessment Evidence\"/><w:tag w:val=\"KTA_AssessmentEvidence\"/><w:id w:val=\"-261377037\"/><w:placeholder><w:docPart w:val=\"90F8D07DF1D3489C82A631D1EE584667\"/></w:placeholder></w:sdtPr><w:sdtContent><w:tc><w:tcPr><w:tcW w:w=\"1667\" w:type=\"pct\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"auto\"/></w:tcPr><w:p w:rsidR=\"001C27A2\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"76B8F0C3\" w14:textId=\"77777777\"><w:pPr><w:pStyle w:val=\"Tabletext\"/></w:pPr><w:r w:rsidRPr=\"00AA6CDC\"><w:t xml:space=\"preserve\">A </w:t></w:r><w:r><w:t>report focussing on what went well and what did not go so well when machining a component and a conclusion of improvements that could be made.</w:t></w:r></w:p><w:p w:rsidR=\"00722069\" w:rsidP=\"001C27A2\" w:rsidRDefault=\"001C27A2\" w14:paraId=\"117FD2F1\" w14:textId=\"2FF96047\"><w:pPr><w:pStyle w:val=\"Tabletext\"/></w:pPr><w:r><w:t xml:space=\"preserve\">The report </w:t></w:r><w:r w:rsidRPr=\"00093CCD\"><w:t>to</w:t></w:r><w:r><w:t xml:space=\"preserve\"> show</w:t></w:r><w:r w:rsidRPr=\"00837D59\"><w:t xml:space=\"preserve\"> </w:t></w:r><w:r><w:t xml:space=\"preserve\">a </w:t></w:r><w:r w:rsidRPr=\"00837D59\"><w:t>professional understanding of traditional secondary machining processes</w:t></w:r><w:r><w:t>.</w:t></w:r></w:p></w:tc></w:sdtContent></w:sdt></w:tr></w:tbl>";
        String tableProcessed = null;

        String textXmlString = "<w:tbl xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\" xmlns:dgm=\"http://schemas.openxmlformats.org/drawingml/2006/diagram\" xmlns:dsp=\"http://schemas.microsoft.com/office/drawing/2008/diagram\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" xmlns:ns12=\"http://schemas.openxmlformats.org/drawingml/2006/chartDrawing\" xmlns:ns17=\"urn:schemas-microsoft-com:office:excel\" xmlns:ns21=\"urn:schemas-microsoft-com:office:powerpoint\" xmlns:ns23=\"http://schemas.microsoft.com/office/2006/coverPageProps\" xmlns:ns30=\"http://schemas.openxmlformats.org/officeDocument/2006/bibliography\" xmlns:ns31=\"http://schemas.openxmlformats.org/drawingml/2006/compatibility\" xmlns:ns32=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\" xmlns:ns9=\"http://schemas.openxmlformats.org/schemaLibrary/2006/main\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:oda=\"http://opendope.org/answers\" xmlns:odc=\"http://opendope.org/conditions\" xmlns:odgm=\"http://opendope.org/SmartArt/DataHierarchy\" xmlns:odi=\"http://opendope.org/components\" xmlns:odq=\"http://opendope.org/questions\" xmlns:odx=\"http://opendope.org/xpaths\" xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:w14=\"http://schemas.microsoft.com/office/word/2010/wordml\" xmlns:w15=\"http://schemas.microsoft.com/office/word/2012/wordml\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\" xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" xmlns:xdr=\"http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing\" mc:Ignorable=\"w14 w15\">\n" +
                "\t\t\t\t\t\t<w:tblPr>\n" +
                "\t\t\t\t\t\t\t<w:tblW w:type=\"auto\" w:w=\"0\"/>\n" +
                "\t\t\t\t\t\t\t<w:tblBorders>\n" +
                "\t\t\t\t\t\t\t\t<w:top w:color=\"auto\" w:space=\"0\" w:sz=\"4\" w:val=\"single\"/>\n" +
                "\t\t\t\t\t\t\t\t<w:left w:color=\"auto\" w:space=\"0\" w:sz=\"4\" w:val=\"single\"/>\n" +
                "\t\t\t\t\t\t\t\t<w:bottom w:color=\"auto\" w:space=\"0\" w:sz=\"4\" w:val=\"single\"/>\n" +
                "\t\t\t\t\t\t\t\t<w:right w:color=\"auto\" w:space=\"0\" w:sz=\"4\" w:val=\"single\"/>\n" +
                "\t\t\t\t\t\t\t</w:tblBorders>\n" +
                "\t\t\t\t\t\t\t<w:shd w:color=\"auto\" w:fill=\"666699\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t<w:tblLook w:firstColumn=\"1\" w:firstRow=\"1\" w:lastColumn=\"1\" w:lastRow=\"1\" w:noHBand=\"0\" w:noVBand=\"0\" w:val=\"01E0\"/>\n" +
                "\t\t\t\t\t\t</w:tblPr>\n" +
                "\t\t\t\t\t\t<w:tblGrid>\n" +
                "\t\t\t\t\t\t\t<w:gridCol w:w=\"9099\"/>\n" +
                "\t\t\t\t\t\t</w:tblGrid>\n" +
                "\t\t\t\t\t\t<w:tr w14:paraId=\"513B8AF7\" w14:textId=\"77777777\" w:rsidR=\"003605F8\" w:rsidRPr=\"00A93D00\" w:rsidTr=\"003605F8\">\n" +
                "\t\t\t\t\t\t\t<w:trPr>\n" +
                "\t\t\t\t\t\t\t\t<w:trHeight w:val=\"724\"/>\n" +
                "\t\t\t\t\t\t\t</w:trPr>\n" +
                "\t\t\t\t\t\t\t<w:tc>\n" +
                "\t\t\t\t\t\t\t\t<w:tcPr>\n" +
                "\t\t\t\t\t\t\t\t\t<w:tcW w:type=\"dxa\" w:w=\"9109\"/>\n" +
                "\t\t\t\t\t\t\t\t\t<w:shd w:color=\"auto\" w:fill=\"F3F3F3\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t</w:tcPr>\n" +
                "\t\t\t\t\t\t\t\t<w:p w14:paraId=\"02D219FC\" w14:textId=\"3A4C2D6C\" w:rsidP=\"0094262F\" w:rsidR=\"003605F8\" w:rsidRDefault=\"003605F8\" w:rsidRPr=\"00A93D00\">\n" +
                "\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"LAheadingtables\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"FFFFFF\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t<w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">Learning aim </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t<w:sdt>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:sdtPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:alias w:val=\"Learning Objective Reference\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:tag w:val=\"learningobjectiveref\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:id w:val=\"1122727030\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:lock w:val=\"sdtLocked\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:placeholder>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:docPart w:val=\"B2DE0C7891C249BA8A5B2168FC39653E\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:placeholder>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:text/>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:sdtPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:sdtContent>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidR=\"0094262F\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>A</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:sdtContent>\n" +
                "\t\t\t\t\t\t\t\t\t</w:sdt>\n" +
                "\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00A93D00\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:t>:</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"007642A8\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\"> </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t<w:sdt>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:sdtPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"548DD4\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:szCs w:val=\"19\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:alias w:val=\"Learning Objective\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:tag w:val=\"learningobjectivetext\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:id w:val=\"-334534885\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:lock w:val=\"sdtLocked\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:placeholder>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:docPart w:val=\"20745CF06C5F40B6AF4D79066F2C6201\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:placeholder>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:text/>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:sdtPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:sdtContent>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidR=\"0094262F\" w:rsidRPr=\"0094262F\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"548DD4\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:szCs w:val=\"19\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Understand the technology and characteristics of secondary machining processes that are widely used in industry</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:sdtContent>\n" +
                "\t\t\t\t\t\t\t\t\t</w:sdt>\n" +
                "\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t</w:tc>\n" +
                "\t\t\t\t\t\t</w:tr>\n" +
                "\t\t\t\t\t\t<w:tr w14:paraId=\"14CD6AE5\" w14:textId=\"77777777\" w:rsidR=\"003605F8\" w:rsidRPr=\"009932CB\" w:rsidTr=\"0094262F\">\n" +
                "\t\t\t\t\t\t\t<w:trPr>\n" +
                "\t\t\t\t\t\t\t\t<w:trHeight w:val=\"1987\"/>\n" +
                "\t\t\t\t\t\t\t</w:trPr>\n" +
                "\t\t\t\t\t\t\t<w:sdt>\n" +
                "\t\t\t\t\t\t\t\t<w:sdtPr>\n" +
                "\t\t\t\t\t\t\t\t\t<w:alias w:val=\"Unit Content\"/>\n" +
                "\t\t\t\t\t\t\t\t\t<w:tag w:val=\"unitcontent\"/>\n" +
                "\t\t\t\t\t\t\t\t\t<w:id w:val=\"104401435\"/>\n" +
                "\t\t\t\t\t\t\t\t\t<w:lock w:val=\"sdtLocked\"/>\n" +
                "\t\t\t\t\t\t\t\t\t<w:placeholder>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:docPart w:val=\"D02A048BF3314BA3B0D6C8E25D72071B\"/>\n" +
                "\t\t\t\t\t\t\t\t\t</w:placeholder>\n" +
                "\t\t\t\t\t\t\t\t</w:sdtPr>\n" +
                "\t\t\t\t\t\t\t\t<w:sdtEndPr>\n" +
                "\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t</w:sdtEndPr>\n" +
                "\t\t\t\t\t\t\t\t<w:sdtContent>\n" +
                "\t\t\t\t\t\t\t\t\t<w:tc>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:tcPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:tcW w:type=\"dxa\" w:w=\"9109\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:shd w:color=\"auto\" w:fill=\"auto\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:tcPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"4475B35A\" w14:textId=\"77777777\" w:rsidP=\"0094262F\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00F62E1A\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00F62E1A\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>A1</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00F62E1A\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tab/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Traditional secondary machining processes</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"726E63FA\" w14:textId=\"77777777\" w:rsidP=\"0094262F\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"0\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:firstLine=\"0\" w:left=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00BE436A\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Technology and characteristics</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\"> of </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">secondary </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>machining processes:</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"0FFE5753\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:hanging=\"567\" w:left=\"567\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">DrillingDawud: </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"2B68B50A\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"002B5F81\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">machine type and batch size </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00BE436A\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>including</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"002B5F81\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\"> single spindle machine</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>s e.g.</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"002B5F81\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\"> pillar </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>(</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"002B5F81\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>one off to small batch size</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>s)</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"002B5F81\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\"> and radial </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>(</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"002B5F81\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>small to medium batch size</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>s).</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"6AF608B6\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">features of the component e.g. countersinking, counter boring, spot facing, taping, through holes, </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>blind holes, reamed holes</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"7BB92447\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>accuracy of components - typical dimensional tolerances = ±0.3mm to ±0.05mm and typical surface texture = 6.3µm to 1.6µm.</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"08B1A2DF\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:hanging=\"567\" w:left=\"567\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Turning:</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"21C1E7DB\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>machine type and batch size including centre lathe (one off to small batch size) and turret (small to large batch size)</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"26E881CB\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>features of the component e.g. flat faces, diameters (such as parallel, stepped, tapered), holes (such as drilled, bored, reamed), profile forms, threads (such as internal, external), parting off, chamfers, knurls, grooves, undercuts.</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"40C81C4B\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00006B98\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>accuracy of components - typical dimensional tolerances = ±0.05mm to ±0.0125mm and typical surface texture = 3.2µm</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00006B98\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\"> to 0.8µm</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"57F23107\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:hanging=\"567\" w:left=\"567\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Milling:</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"014FB3D6\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">machine type and batch size </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00BE436A\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>including</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\"> horizontal (up-cut, down-cut) (one off to small batch size), vertical (one off to small batch size), universal (one off to small batch size)</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"69B2C77F\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00547FF0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">features of the component e.g. faces (such as flat, square, parallel, angular), steps/shoulders, </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>slots (such as open ended, enclosed/recesses, tee), holes (such as drilled, bored), profile forms (such as vee, concave, convex, gear)</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"0A9C3A2B\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>accuracy of components - typical dimensional tolerances = ±0.1mm to ±0.025mm and typical surface texture = 3.2µm to 0.8µm</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"26703932\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:hanging=\"567\" w:left=\"567\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">Grinding: </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"3A4F0822\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>machine type and batch size including surface (such as horizontal, vertical) (one off to small batch size), cylindrical (such as external, internal) (one off to small batch size), centreless (medium to large batch size), universal (one off to small batch size)</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"04F7D972\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>features of the component e.g. faces (such as flat, vertical, parallel, square to each other, shoulders and faces), slots, diameters (such as parallel, tapered), bores (such as counterbores, tapered, parallel)</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"335D9CC4\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"002A2597\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>accuracy of components - typical dimensional tolerances = ±0.0125mm to ±0.002mm and typical surface texture = 0.8µm to</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00006B98\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\"> 0.2µm</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"2953E771\" w14:textId=\"77777777\" w:rsidP=\"0094262F\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00731E6C\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00731E6C\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Sustainability characteristics of traditional secondary machining processes:</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"12E2A488\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"0004766E\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:hanging=\"567\" w:left=\"567\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"0004766E\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Energy consumption to remove material including power requirements to operate the machine, condition of machine, condition of tooling</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"0BE54B5D\" w14:textId=\"77777777\" w:rsidP=\"0094262F\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"0004766E\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:lastRenderedPageBreak/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Use and disposal of cutting fluids</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\"> and waste materia</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>ls.</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"7C15193E\" w14:textId=\"77777777\" w:rsidP=\"0094262F\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00F62E1A\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:firstLine=\"0\" w:left=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00F62E1A\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>A2</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00F62E1A\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tab/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Specialist secondary machining processes</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"50765349\" w14:textId=\"77777777\" w:rsidP=\"0094262F\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"0\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>T</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">echnology and characteristics of </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:color w:val=\"auto\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>specialist machining processes:</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"666609C8\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:hanging=\"567\" w:left=\"567\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">Presswork: </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"73923DEE\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">machine type and batch size including single action (small to medium batch size), multiple action (medium batch to mass manufacturing) </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"4C5ED277\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>features of the component e.g. blanking, notching, piercing, cropping/shearing, bending/forming</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"6CBCF492\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"009D2923\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00EA73E3\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>accuracy of components - typical dimensional tolerances = ±0.3mm to ±0.05mm.</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"27FF80F9\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:hanging=\"567\" w:left=\"567\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">Electro discharge: </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"100394B8\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>machine type and batch size including spark erosion (small to large batch size), wire erosion (small to large batch size)</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"746D1C86\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">features of the component e.g. holes, faces (such as flat, square, parallel, angular), </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>forms (such as concave, convex, profile, square/rectangular), other features (such as engraving, cavities, radii/arcs, slots)</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"18EC3E0A\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>accuracy of components - typical dimensional tolerances = ±0.1mm to ±0.05mm and typical surface texture = 6.3µm to 0.4µm.</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"3A14B4C3\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:hanging=\"567\" w:left=\"567\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">Broaching: </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"47678058\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>machine type and batch size including horizontal (one off to medium batch size), vertical (one off to medium batch size)</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"68B39233\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00B10B69\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>features of the component e.g. keyways, holes (such as flat sided, square, hexagonal, octagonal), splines</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"4BFC8D63\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00EA73E3\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">accuracy of components </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>- typical dimensional tolerances = ±0.05mm to ±0.01mm and typical surface texture = 6.3µm to 0.4µm.</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"32C8E728\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:hanging=\"567\" w:left=\"567\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\">Honing and lapping: </w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"6058BB5A\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>machine types and batch size including horizontal and vertical honing (one off to medium batch size), and rotary disc and reciprocating lapping (one off to medium batch size)</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"47185305\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>features of the component e.g. holes (such as through, blind, tapered), faces (such as flat, parallel, angular)</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"0B35D32B\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"1\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>accuracy of components - typical dimensional tolerances = ±0.01mm to ±0.005mm and typical surface texture = 0.2µm to 0.03µm.</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"2AF2BEC3\" w14:textId=\"77777777\" w:rsidP=\"0094262F\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"00CC21EA\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00C62EF4\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Sustainability characteristics of specialist secondary machining</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00CC21EA\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t xml:space=\"preserve\"> processes:</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"3CAD732C\" w14:textId=\"77777777\" w:rsidP=\"008D1034\" w:rsidR=\"0094262F\" w:rsidRDefault=\"0094262F\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:hanging=\"567\" w:left=\"567\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"00D41034\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Energy consumption to remove material including power requirements to operate the machine, condition of machine, condition of tooling</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t\t<w:p w14:paraId=\"744EF9AD\" w14:textId=\"54B0F5F5\" w:rsidP=\"008D1034\" w:rsidR=\"003605F8\" w:rsidRDefault=\"0094262F\" w:rsidRPr=\"0094262F\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:pStyle w:val=\"Topictextandhead\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:ilvl w:val=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:numId w:val=\"7\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:numPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"400\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"480\" w:val=\"clear\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:tab w:pos=\"567\" w:val=\"left\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:tabs>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:ind w:hanging=\"567\" w:left=\"567\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:pPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r w:rsidRPr=\"0094262F\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>Use and disposal of cutting fluids/electrolytes and waste mat</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<w:b w:val=\"false\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</w:rPr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<w:t>erials</w:t>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</w:r>\n" +
                "\t\t\t\t\t\t\t\t\t\t</w:p>\n" +
                "\t\t\t\t\t\t\t\t\t</w:tc>\n" +
                "\t\t\t\t\t\t\t\t</w:sdtContent>\n" +
                "\t\t\t\t\t\t\t</w:sdt>\n" +
                "\t\t\t\t\t\t</w:tr>\n" +
                "\t\t\t\t\t</w:tbl>";

        try {
            tableProcessed = TableContentPreProcessor.getTableData(textXmlString);

        } catch (SaxonApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

            //System.out.println(tableProcessed);

//            Document jdomDoc = new Document();
//            // create root element
//            // add Btec Unit model
//            Element rootElement = new Element("table");
//            rootElement = loadXMLFromString(tableProcessed);
//            jdomDoc.setRootElement(rootElement);

            // Output as XML
            // create XMLOutputter
//            XMLOutputter xml = new XMLOutputter();
//            // we want to format the xml. This is used only for demonstration. pretty formatting adds extra spaces and is generally not required.
//            xml.setFormat(Format.getPrettyFormat());
//            System.out.println(xml.outputString(jdomDoc));


            getElement(tableProcessed);
    }



        public static Element getElement(String xmlDataString) {
                //
                // Create an instance of SAXBuilder
                //
                SAXBuilder builder = new SAXBuilder();
                Element e = null;
                try {
                        //
                        // Tell the SAXBuilder to build the Document object from the
                        // InputStream supplied.
                        //
                        Document document = builder.build(
                                new ByteArrayInputStream(xmlDataString.getBytes()));

                        e = document.getRootElement();
                        Element elemCopy = (Element)e.clone();
                        elemCopy.detach();


                        Element e2 = new Element("document");
                        e2.addContent(elemCopy);
                        Document testDocument = new Document();
                        testDocument.setRootElement(e2);


                        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
                        String xmlString = outputter.outputString(testDocument);
                        System.out.println(xmlString);


                } catch (JDOMException ex) {
                        ex.printStackTrace();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }

                return e;
        }



    public static String getTableData(String xml) throws SaxonApiException, IOException {
        InputStream fileInputStreamXmlfile = new FileInputStream(new File(AppURIConstants.TRANSFORM_SCRIPTS_XQUERY_WORD_TO_TAGGEDWORDOPENXML_FILE_PATH));

        Processor processor = new Processor(false);

        net.sf.saxon.s9api.DocumentBuilder documentBuilder = processor.newDocumentBuilder();
        XdmNode document = documentBuilder.build(new StreamSource(new StringReader(xml)));

        XQueryCompiler xQueryCompiler = processor.newXQueryCompiler();
        XQueryExecutable xQueryExecutable = xQueryCompiler.compile(fileInputStreamXmlfile);

        XQueryEvaluator xQueryEvaluator = xQueryExecutable.load();

        QName name = new QName("table");
        xQueryEvaluator.setExternalVariable(name, document);
        XdmValue xdmValue = xQueryEvaluator.evaluate();

        StringBuilder sb = new StringBuilder();

        for(XdmItem item : xdmValue) {
            sb.append(item);
        }

        return sb.toString();
    }





}

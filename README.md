# BtecWordOpenXMLApp
Word to OpenXML Transformation tool for Pearson BTEC Specifactions

This is a Java Spring MVC application to upload, ingest, convert and transform Ms Word documents into:-
OpenXML - Universal XML format as used by Microsoft Word
IQS XML - which is a Pearson specific XML structure for academic and vocational qualifications.

Extraction of OpenXML is handled by opensource Docx4J library in Java.
Extraction of Semantic content is handled by mix of Java DocX4J library methods and Xquery scripting.
Transformation to IQS is handled by XSLT layer.

The app is packaged as a War and deployed onto Amazon AWS Cloud service.
Demo is available here:

http://default-environment-njnfptyyjp.elasticbeanstalk.com/index.html


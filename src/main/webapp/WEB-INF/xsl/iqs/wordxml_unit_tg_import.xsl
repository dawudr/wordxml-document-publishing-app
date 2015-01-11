<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:strip-space elements="*"/>

	<!-- GLOBAL VARIABLES -->
	<xsl:variable name="var_uan_str" select="unit/uan"/>
	<xsl:variable name="var_uan"><xsl:value-of select="translate($var_uan_str, '/', '_')"/></xsl:variable>
	
	<!-- MAIN ROOT NODE -->
	<xsl:template match="/">
		<xsl:apply-templates select="unit"/>
	</xsl:template>
	
	
	<!-- Unit Subnode -->	
	<xsl:template match="unit">
	<unit>
		<!-- uan number -->
		<uan><xsl:value-of select="$var_uan_str"/></uan>
		
		<xsl:if test="section[@title = 'Teacher guidance']">
			<xsl:element name="unitessentialguidance">
				<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
				<!-- resources -->
				<xsl:apply-templates select="section[@title = 'Resources']" mode="unitessentialguidance"/>
				
				<!-- assessment guidance -->
				<xsl:apply-templates select="section[@title = 'Assessment Guidance']" mode="unitessentialguidance"/>
				<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>						
			</xsl:element>
		</xsl:if>
	</unit>
	</xsl:template>
	<!-- End Unit Subnode -->	
	
	
	<!-- TEMPLATES -->

	<!-- unitessentialguidance -->	
	<xsl:template match="section" mode="unitessentialguidance">
		<xsl:choose>
			<xsl:when test="@title = 'Resources'">
				<p><b><xsl:value-of select="@title"/></b></p>
				<xsl:value-of select="resources" disable-output-escaping="yes"></xsl:value-of>
			</xsl:when>
			<xsl:when test="@title = 'Assessment Guidance'">
				<p><b><xsl:value-of select="@title"/></b></p>
				<xsl:value-of select="assessmentguidance" disable-output-escaping="yes"></xsl:value-of>			
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>

<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="2.0" encoding="UTF-8" indent="yes"/>
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
			
		
		<!-- assessmentcriteria -->
		<xsl:apply-templates select="section[@title = 'Assessment criteria']/table" mode="assessmentcriteria"/>

	</unit>
	</xsl:template>
	<!-- End Unit Subnode -->	
	
	

	

	
	
	<!-- TEMPLATES -->
	
	<!-- Assumption: merge the row with Assessment Critieria data only, so skip first 2 rows -->
	<!-- assessmentcriteria -->	
	<xsl:template match="table" mode="assessmentcriteria">
			<xsl:param name="var_table" select="." />
			
			<!--DEBUG: <xsl:copy-of select="$var_table"/>-->

					<xsl:for-each select="$var_table/row">
					<!--DEBUG: <xsl:copy-of select="."/>-->
						
						<xsl:for-each select="./cell[@style= 'Assessmenttabletext']">
						<!--<xsl:if test="./cell[@style= 'Assessmenttabletext']">-->
							
							<xsl:element name="assessmentcriteria">
								<xsl:attribute name="acid">
									<xsl:value-of select="$var_uan"/>
									<xsl:text>.</xsl:text>
									<xsl:value-of select="./AC_criteria_title"/>
								</xsl:attribute>
								<title><xsl:value-of select="./AC_criteria_title"/></title>
								<criteria>		
									<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
									<xsl:value-of select="./criteria"/>								
									<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
									</criteria>
									<pltsvalue>
									<key></key>
									</pltsvalue>									
							</xsl:element>
						
						<!--</xsl:if>-->
						</xsl:for-each>
					</xsl:for-each>
			
			
		
	</xsl:template>	
	
	
	
	
	<!-- description -->
	<xsl:template match="ul[@type='ac_bl1']">		
		<ul>
			<xsl:copy-of select="./*"/>
		</ul>
	</xsl:template>
	
</xsl:stylesheet>



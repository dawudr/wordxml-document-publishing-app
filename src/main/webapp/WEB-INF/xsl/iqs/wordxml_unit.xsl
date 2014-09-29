<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- COMPONENT SECTIONS TO IMPORT -->
	<!-- Unit data section -->
	<!--<xsl:import href="wordxml_unit_la_import.xsl"/>	-->
	<!--<xsl:import href="wordxml_unit_ac_import.xsl"/>-->
	<!--<xsl:import href="wordxml_unit_cg_import.xsl"/>-->
	<!--<xsl:import href="wordxml_unit_sao_import.xsl"/>-->
	<!--<xsl:import href="wordxml_unit_tg_import.xsl"/>-->

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:strip-space elements="*"/>
		
	<!-- GLOBAL VARIABLES -->
	<xsl:variable name="var_uan_str" select="unit/uan"/>
	<xsl:variable name="var_uan"><xsl:value-of select="translate($var_uan_str, '/', '_')"/></xsl:variable>
	
		
	<!-- MAIN ROOT NODE -->
	<xsl:template match="/">
<!--
			<xsl:result-document href="{$var_uan}.xml">
-->
		<xsl:apply-templates select="unit"/>
<!--
			</xsl:result-document>
-->
	</xsl:template>
	
	
	<!-- Unit Subnode -->	
	<xsl:template match="unit">
	<xsl:element name="unit">
	<xsl:attribute name="unitid"><xsl:value-of select="$var_uan"/></xsl:attribute>
	
	<!-- uan number -->
	<uan><xsl:value-of select="$var_uan_str"/></uan>
	
	<!-- unitnumber and title -->
	<unitnumber><xsl:value-of select="unitnumber"/></unitnumber>
						
	<!-- Assumption: title is after 'unitnumber:' -->
	<title><xsl:value-of select="unittitle"/></title>
							
							
							
	<!-- description -->
	<description>
		<xsl:value-of select="unitintroduction"/>
	</description>
	
	<!-- glh -->
	<glh>
		<glhvalue><xsl:value-of select="unitsize"/></glhvalue>
	</glh>
	<level><xsl:value-of select="level"/></level>
	<assessmentmode id=""><xsl:value-of select="assessmenttype"/></assessmentmode>
	
	


	<!-- printdocument -->
	<printdocument><xsl:value-of select="$var_uan"/></printdocument>
	

	</xsl:element><!-- End Unit -->
	</xsl:template>	
	<!-- End Unit Subnode -->	
	
	

	
	
	
	
	
	
	
	<!-- TEMPLATES -->
	


	
	<!-- unitsection -->
	<xsl:template match="table" mode="unitsection">
		<!-- Assumption: Test if tr/td/la_h2 element contains the text "Learning Aim" -->
		<xsl:if test="starts-with(normalize-space(tr/td/la_h2),'Learning')">
		
		<xsl:element name="unitsection">
			<xsl:attribute name="sid"><xsl:value-of select="$var_uan"/>.LO<xsl:value-of select="substring-after( substring-before( normalize-space(tr/td/la_h2) ,':' ) , 'Learning aim ')"/></xsl:attribute>

			<title><xsl:value-of select="tr/td/la_h2"/></title>
			<sectiontext>
				<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
					<xsl:apply-templates select="tr[position() = last()]/td" mode="unitsection"/>
				<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
			</sectiontext>
			
		</xsl:element>
		
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="tr[position() = last()]/td" mode="unitsection">
		<xsl:for-each select="*">
			<xsl:choose>
				<xsl:when test="name() = 'la_te_s'"><p><b><xsl:value-of select="normalize-space(.)"/></b></p></xsl:when>
				<xsl:when test="name() = 'la_te'"><p><xsl:value-of select="normalize-space(.)"/></p></xsl:when>
                <xsl:when test="name() = 'la_te_e'"><p><b><xsl:value-of select="normalize-space(.)"/></b></p></xsl:when>
				<xsl:otherwise>
				
					<!-- strip out attribute of html lists -->
					<xsl:choose>
						<xsl:when test="name() = 'ul'">
							<ul>
								<xsl:copy-of select="./*"/>
							</ul>
						</xsl:when>
						<xsl:when test="name() = 'ol'">
							<ol>
								<xsl:copy-of select="./*"/>
							</ol>
						</xsl:when>
						<xsl:otherwise><xsl:copy-of select="."/></xsl:otherwise>
					</xsl:choose>
				
				</xsl:otherwise>
			</xsl:choose>
		
		</xsl:for-each>
	
	</xsl:template>
	<!-- End unitsection -->
	
	
</xsl:stylesheet>

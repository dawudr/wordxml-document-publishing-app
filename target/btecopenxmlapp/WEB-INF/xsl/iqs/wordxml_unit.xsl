<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<!-- COMPONENT SECTIONS TO IMPORT -->
	<!-- Unit data section -->
	<xsl:import href="wordxml_unit_la_import.xsl"/>
	<xsl:import href="wordxml_unit_ac_import.xsl"/>
	<xsl:import href="wordxml_unit_cg_import.xsl"/>
	<xsl:import href="wordxml_unit_sao_import.xsl"/>
	<xsl:import href="wordxml_unit_tg_import.xsl"/>
	
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
				<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
				<xsl:value-of select="section[1]/introduction" disable-output-escaping="yes"/>
				<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
			</description>
			
			<!-- glh -->
			<glh>
				<glhvalue><xsl:value-of select="glhvalue"/></glhvalue>
			</glh>
			<level><xsl:value-of select="level"/></level>
			<assessmentmode id=""><xsl:value-of select="unittype"/></assessmentmode>
			
			
			
			<!-- learningobjective -->
			<!-- Filter out for Learning Objective tables only -->
			<xsl:choose>
				<xsl:when test="section[@title = 'Assessment criteria']">
				
					<!--AC TABLE <xsl:copy-of select="."/> END-->
					<xsl:call-template name="learningobjective_with_assessment">
						<xsl:with-param name="var_ac_table" select="section[@title = 'Assessment criteria']/table"/>
					</xsl:call-template>
					
				</xsl:when>
				<xsl:otherwise>
					
					<xsl:call-template name="learningobjective_only">
						<xsl:with-param name="var_la_table" select="section[@title = 'Learning aims']/table"/>
					</xsl:call-template>
					
				</xsl:otherwise>
			</xsl:choose>
			<!--End learningobjective -->
			
			
			<!-- criteriagradegrid -->
			<xsl:apply-templates select="section[@title = 'Assessment criteria']/table" mode="criteriagradegrid"/>
			
			<!--assessmentcriteria-->
			<xsl:apply-templates select="section[@title = 'Assessment criteria']/table" mode="assessmentcriteria"/>
			
			<!--suggestedassignment-->		
			<xsl:apply-templates select="section[@title = 'Outline Programme of Suggested Assignments']/table" mode="suggestedassignment"/>
	
			<!-- unitcontent -->
			<unitcontent>
				<!-- unitsection - tables for Learning Aim -->
				<xsl:apply-templates select="section[@title = 'Learning aims and unit content']/table" mode="unitsection"/>
			</unitcontent>
	
	
	
			<!-- unitessentialguidance -->
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
			
			
			<!-- printdocument -->
			<printdocument><xsl:value-of select="$var_uan"/></printdocument>
			
			
		</xsl:element><!-- End Unit -->
	</xsl:template>	
	<!-- End Unit Subnode -->	
	
	
	
	
	
	
	
	
	
	
	<!-- TEMPLATES -->
	
	
	
	
	<!-- unitsection -->
	<xsl:template match="table" mode="unitsection">
			
			<xsl:element name="unitsection">
				<xsl:attribute name="sid"><xsl:value-of select="$var_uan"/>.LO<xsl:value-of select="normalize-space(./row[1]/cell/learningobjectiveref)"/></xsl:attribute>
				
				<title><xsl:value-of select="./row[1]/cell/learningobjectivetext"/></title>
				<sectiontext>
					<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
					<xsl:value-of disable-output-escaping="yes" select="./row[2]/cell/unitcontent"/>
					<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
				</sectiontext>
				
			</xsl:element>
			
	</xsl:template>
	
	
</xsl:stylesheet>

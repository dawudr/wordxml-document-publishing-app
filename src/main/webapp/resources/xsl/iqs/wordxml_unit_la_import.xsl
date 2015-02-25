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
		<xsl:element name="unit">
	
				
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
			
		</xsl:element>
		<!-- End Unit -->
	</xsl:template>
	<!-- End Unit Subnode -->	
	
	
	
	
	<!-- TEMPLATES -->
	<!-- learningobjective -->	
	<xsl:template name="learningobjective_only">
		<xsl:param name="var_la_table" select="." />	
			<xsl:for-each select="$var_la_table/row">
				<xsl:if test="./cell[@column-name= 'Learning Aim']">
					<xsl:element name="learningobjective">
						<xsl:attribute name="loid"><xsl:value-of select="$var_uan"/><xsl:text>.</xsl:text>LO<xsl:value-of select="cell[@column-name= 'Learning Aim']/learningobjectiveref"/></xsl:attribute>				
									<xsl:if test="cell[@column-name= 'Learning Aim']">
										<title><xsl:value-of select="cell[@column-name= 'Learning Aim']/learningobjectiveref"/></title>
										<learningobjectivetext><xsl:value-of select="cell[@column-name= 'Learning Aim']/learningobjectivetext"/></learningobjectivetext>
									</xsl:if>
					</xsl:element>
				</xsl:if>
			</xsl:for-each>
	</xsl:template>
	
	
	
	<!-- learningobjective for assessment tr tables which are joined-->	
	<xsl:template name="learningobjective_with_assessment">
			<xsl:param name="var_ac_table" select="." />			

			<xsl:for-each select="$var_ac_table/row">
				
				
				<xsl:if test="./cell[@style= 'LAheadingtables']">
					<xsl:variable name="var_criteriacovered_index" select="position()+1"/>
					<xsl:variable name="var_criteriacovered" select="./following-sibling::*[preceding::cell[@style= 'LAheadingtables']]"/>
					<!--DEBUG:<xsl:copy-of select="$var_criteriacovered"/>END	-->
					
					
					<xsl:element name="learningobjective">
						<xsl:attribute name="loid"><xsl:value-of select="$var_uan"/><xsl:text>.</xsl:text>LO<xsl:value-of select="cell[@style= 'LAheadingtables']/learningobjectiveref"/></xsl:attribute>				
						<title><xsl:value-of select="cell[@style= 'LAheadingtables']/learningobjectiveref"/></title>
						<learningobjectivetext><xsl:value-of select="cell[@style= 'LAheadingtables']/learningobjectivetext"/></learningobjectivetext>
					</xsl:element>
					



				</xsl:if>
			</xsl:for-each>
			
	</xsl:template>
	

</xsl:stylesheet>

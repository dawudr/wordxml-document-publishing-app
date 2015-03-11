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
			
			
			<!-- assessmentcriteria -->
			<xsl:apply-templates select="section[@title = 'Outline Programme of Suggested Assignments']/table" mode="suggestedassignment"/>
			
		</unit>
	</xsl:template>
	<!-- End Unit Subnode -->	
	

	
	
	<!-- TEMPLATES -->
	
	<!-- Assumption: merge the row with Suggested assignment outlines data only, so skip first 2 rows -->
	<!-- suggestedassignment -->	
	<xsl:template match="table" mode="suggestedassignment">
			<xsl:param name="var_table" select="." />
								<!--DEBUG: Count: <xsl:value-of select="count(tr)"/>-->

			<!--DEBUG: <xsl:copy-of select="$var_table"/>-->
			
		<!--<xsl:for-each select="$var_table/row">-->
			<!--DEBUG: <xsl:copy-of select="."/>-->

		<xsl:if test="./row[1]/cell[@style='Tablehead']">
						
							<xsl:element name="suggestedassignment">
								<xsl:attribute name="assignmentid">
									<xsl:value-of select="$var_uan"/>
									<xsl:text>.</xsl:text><xsl:value-of select="./row[1]/cell/SAO_AssignmentRef"/>
								</xsl:attribute>
								<numbertitle><xsl:text>Assignment: </xsl:text><xsl:value-of select="./row[1]/cell/SAO_AssignmentRef"/></numbertitle>
								<title><xsl:value-of select="normalize-space(./row[1]/cell/SAO_AssignmentTitle)"/></title>
								<scenario>
									<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>							
									<xsl:value-of disable-output-escaping="yes" select="normalize-space(./row[2]/cell/SAOdescriptiontasks)"/>									
									<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>									
								</scenario>
								<assessmentmethod>
									<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
									<xsl:value-of disable-output-escaping="yes" select="normalize-space(./row[3]/cell/SAOretake)"/>
									<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>								
								</assessmentmethod>
								
								<xsl:variable name="var_criteriacoveredlist" select="tokenize(./row[1]/cell/learningobjective,', | and')"/>
								<xsl:variable name="var_criteriacoveredlist" select="tokenize(./row[1]/cell/learningobjectiveref,', | and')"/>


								<xsl:for-each select="$var_criteriacoveredlist">
											
									<xsl:choose>
											
									<xsl:when test="contains(., '(')">
									<xsl:element name="criteriacovered">
										<xsl:attribute name="coverage" select="'all'"/>
										<xsl:attribute name="acid">
				
							
											
											<xsl:value-of select="$var_uan"/><xsl:text>.</xsl:text>
											<xsl:value-of select="normalize-space(substring-before(.,'('))"/><!-- - DEBUG TEST1 <xsl:value-of select="."/>-->		
											</xsl:attribute>
									</xsl:element>
										
																		
									
										
									<xsl:element name="criteriacovered">
										<xsl:attribute name="coverage" select="'all'"/>
										<xsl:attribute name="acid">
											<xsl:value-of select="$var_uan"/><xsl:text>.</xsl:text>
											<xsl:value-of select="normalize-space(substring-after(  translate(., ')','')    , '(')   )"/><!-- - DEBUG TEST2 <xsl:value-of select="."/>-->		
											</xsl:attribute>
									</xsl:element>
									</xsl:when>
									
									<xsl:when test="contains(.,')')">
									<xsl:element name="criteriacovered">
										<xsl:attribute name="coverage" select="'all'"/>
										<xsl:attribute name="acid">
											<xsl:value-of select="$var_uan"/><xsl:text>.</xsl:text>
											<xsl:value-of select="normalize-space(substring-before(.,')'))"/><!--- DEBUG TEST3 <xsl:value-of select="."/>-->		
											</xsl:attribute>
									</xsl:element>
									</xsl:when>	
										
									<xsl:otherwise>		
									<xsl:element name="criteriacovered">
										<xsl:attribute name="coverage" select="'all'"/>
										<xsl:attribute name="acid">
											<xsl:value-of select="$var_uan"/><xsl:text>.</xsl:text><xsl:value-of select="normalize-space(.)"/><!--- DEBUG TEST4 <xsl:value-of select="."/>-->		
											</xsl:attribute>
									</xsl:element>
									</xsl:otherwise>	
									</xsl:choose>	
																	
								</xsl:for-each>
							</xsl:element>

			</xsl:if>
			
		<!--</xsl:for-each>-->
			

		
	</xsl:template>
	

</xsl:stylesheet>

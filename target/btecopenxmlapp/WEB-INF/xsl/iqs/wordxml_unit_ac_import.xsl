<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="2.0" encoding="UTF-8" indent="yes"/>
	<xsl:strip-space elements="*"/>
	
	<!-- GLOBAL VARIABLES -->
	<xsl:variable name="var_uan_str" select="specification/unit/uan"/>
	<xsl:variable name="var_uan"><xsl:value-of select="translate($var_uan_str, '/', '_')"/></xsl:variable>    
    
		
	<!-- Main Root node -->
	<xsl:template match="/">
		<xsl:apply-templates select="specification/unit"/>
	</xsl:template>
	
	
	<!-- Unit Subnode -->	
	<xsl:template match="unit">
	<unit>
	
		<!-- uan number -->
		<uan><xsl:value-of select="$var_uan_str"/></uan>
			
		
		<!-- assessmentcriteria -->
		<xsl:apply-templates select="table" mode="assessmentcriteria"/>

	</unit>
	</xsl:template>
	<!-- End Unit Subnode -->	
	
	

	

	
	
	<!-- TEMPLATES -->
	
	<!-- Assumption: merge the row with Assessment Critieria data only, so skip first 2 rows -->
	<!-- assessmentcriteria -->	
	<xsl:template match="table" mode="assessmentcriteria">
			<xsl:param name="var_table" select="." />
			
			<!--DEBUG: <xsl:copy-of select="$var_table"/>-->
			
		<!-- Filter out for Assessment Critieria tables only -->
		<xsl:if test="tr/td[1]/ac_h1">
			<!--DEBUG: <xsl:copy-of select="tr[position() > 2]"/>-->

			<!-- first header row Assumption: that this will never change -->
			<xsl:variable name="var_header_row" select="$var_table[1]/tr[1]/td"/>
			<!--DEBUG: <xsl:copy-of select="$var_header_row"/>-->
			
			
			<!-- Here goes some fun! -->

			<!-- assessmentcriteria: For each TD in Header TR; i.e for each column-->
			<xsl:for-each select="$var_header_row">			
				<xsl:variable name="var_td_header_row_count" select="position()"/>
				
					<!-- criteriacell: do For each TR row in that column TD; i.e go down the column -->
					<!-- Assumption: first 1 rows don't have Assesment criteria data, hence skip -->
					<xsl:for-each select="$var_table/tr">
					<!--DEBUG: <xsl:copy-of select="."/>-->
					
						<xsl:if test="position() > 2">
							<!-- Fix: test if merged cell or empty criteria id-->
							<xsl:if test="td[$var_td_header_row_count]/ac_te_e">
						
							<xsl:element name="assessmentcriteria">
								<xsl:attribute name="acid">
									<xsl:value-of select="$var_uan"/>
									<xsl:text>.</xsl:text>
									<xsl:value-of select="normalize-space(td[$var_td_header_row_count]/ac_te_e)"/>
								</xsl:attribute>
								<title><xsl:value-of select="normalize-space(td[$var_td_header_row_count]/ac_te_e)"/></title>
								<criteria>		
									<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
											<xsl:value-of select="normalize-space(td[$var_td_header_row_count]/ac_te_s)"/>								
											<p><xsl:value-of select="td[$var_td_header_row_count]/ac_te"/></p>
											<xsl:value-of select="td[$var_td_header_row_count]/ac_te_s"/>
											
											<xsl:apply-templates select="td[$var_td_header_row_count]/ul[@type='ac_bl1']"/>

											<!--<xsl:copy-of select="td[$var_td_header_row_count][not(self::ac_te) or not(self::ac_te_s)]"/>-->
									<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
									</criteria>
									<pltsvalue>
									<key></key>
									</pltsvalue>									
							</xsl:element>
							</xsl:if>
						</xsl:if>
						
					</xsl:for-each>
			</xsl:for-each>
			
			
		</xsl:if>
		
	</xsl:template>	
	
	
	
	
	<!-- description -->
	<xsl:template match="ul[@type='ac_bl1']">		
		<ul>
			<xsl:copy-of select="./*"/>
		</ul>
	</xsl:template>
	
</xsl:stylesheet>



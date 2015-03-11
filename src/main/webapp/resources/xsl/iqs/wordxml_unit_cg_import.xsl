<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:strip-space elements="*"/>
	
	<!-- GLOBAL VARIABLES -->
	<xsl:variable name="var_uan_str" select="unit/uan"/>
	<xsl:variable name="var_uan"><xsl:value-of select="translate($var_uan_str, '/', '_')"/></xsl:variable>
    <xsl:variable name="var_level_array" as="element()*">
        <Item>Pre-Level</Item>
        <Item>Pass</Item>
        <Item>Merit</Item>
        <Item>Distinction</Item>        
    </xsl:variable>	
    

	
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
			<xsl:apply-templates select="section[@title = 'Assessment criteria']/table" mode="criteriagradegrid"/>
			
		</unit>
	</xsl:template>

	
	
	<!-- TEMPLATES -->
	
	

	<!-- Assumption: merge the row with Assessment Critieria data only, so skip first 2 rows -->
	<xsl:template match="table" mode="criteriagradegrid">
		<criteriagradegrid>		
			<xsl:variable name="var_column_headers" select="./row[1]"/>	
			<xsl:variable name="var_total_count" select="count($var_column_headers/cell)"/>
			<xsl:variable name="var_curr_element" select="."/>
			
			<xsl:for-each select="$var_column_headers/cell">
				
				<criteriacolumn>
					<columnnum><xsl:value-of select="position()"/></columnnum>
					<xsl:variable name="var_curr_column_no" select="position()"/>
					<title><xsl:value-of select="."/></title>
					<grade>
						<value><xsl:value-of select="."/></value>
					</grade>
					
					<xsl:for-each select="$var_curr_element">
						
						<!--<xsl:variable name="var_learningobjectiveref" select="./row/cell/learningobjectiveref"/>-->
						<!--DEBUG:<xsl:copy-of select="$var_curr_element"></xsl:copy-of>:DEBUG-->
						<xsl:for-each select="./row/cell[$var_curr_column_no][@style='Assessmenttabletext']">					
									
<!--						
							COLUMN:<xsl:value-of select="$var_curr_column_no"/>:COLUMN
							DEBUG:<xsl:copy-of select="."/>:DEBUG
							COUNT:<xsl:value-of select="position()"/>:COUNT	
							LOREF:<xsl:value-of select="$var_learningobjectiveref"/>:LOREF
-->										
							<criteriacell>
								<rownum><xsl:value-of select="position()"/></rownum>
								<rowspan><xsl:value-of select="1"/></rowspan>
								
								<!--DEBUG:<xsl:value-of select="AC_criteria_title"></xsl:value-of>:DEBUG-->
								
								<xsl:element name="assessmentcriteria">
									<xsl:attribute name="acid" select="concat(concat($var_uan,'.'), AC_criteria_title )"/>
									<xsl:attribute name="acid" select="concat(concat($var_uan,'.'), criteriatitle )"/>
								</xsl:element>
							</criteriacell>		
						</xsl:for-each>								
					</xsl:for-each>
			
<!--					<xsl:for-each select="row">
						
						<xsl:copy-of select="."></xsl:copy-of>
						
						<xsl:choose>
							<xsl:when test="position() &lt; 0">
								
								
								
							</xsl:when>
							<xsl:when test="position() &gt; 0">
								<criteriacell>
									<rownum><xsl:value-of select="position()"/></rownum>
									<rowspan><xsl:value-of select="./cell[1]/@table-column-no"/></rowspan>
								</criteriacell>
							</xsl:when>							
						</xsl:choose>
						
					</xsl:for-each>-->
					
				</criteriacolumn>
			</xsl:for-each>
			
			
		</criteriagradegrid>
	</xsl:template>	
	

	<!-- criteriagradegrid -->
	<!-- Convert WordXML merged tables into IQS XML criteriagrid -->
	<xsl:template name="criteriagradegrid" mode="criteriagradegrid" match="table/tr[1]/td[1]/ac_h1">
		<xsl:param name="var_table" />

		<criteriagradegrid>
		
			<!-- whole table as we can't nest if statements refering to parent nodes inside copy of another for-each loop -->
			<!--<xsl:variable name="var_table" select="."/>-->
			<!-- first header row Assumtion: that this will never change -->
			<xsl:variable name="var_header_row" select="$var_table/table/tr[1]/td"/>

			<!-- Here goes some fun! -->

			<!-- criteriacolumn: For each TD in Header TR; i.e for each column-->
			<xsl:for-each select="$var_header_row">			
				<xsl:variable name="var_td_header_row_count" select="position()"/>
				<criteriacolumn>
					<xsl:variable name="columnnum" select="position()"/>
					<columnnum><xsl:value-of select="$columnnum"/></columnnum>
					<title><xsl:value-of select="normalize-space(p/ac_h1)"/></title>
					<grade>
						<value><xsl:value-of select="$var_level_array[$columnnum]"/></value>
					</grade>
					
					
						<!-- criteriacell: do For each TR row in that column TD; i.e go down the column -->
						<!-- Assumption: first 1 rows don't have Assesment criteria data, hence skip -->
						<xsl:for-each select="$var_table/table/tr">
							
							<xsl:if test="position() > 1">
								<!-- Fix: test if merged cell or empty criteria id-->
								<xsl:if test="td[$var_td_header_row_count]/ac_te_e">
								<criteriacell>
									<rownum><xsl:value-of select="position()-1"/></rownum>
									<!--TODO fix -->
									<xsl:choose>
										<xsl:when test="./td[$var_td_header_row_count]/@colspan"><colspan><xsl:value-of select="./td[$var_td_header_row_count]/@colspan"/></colspan></xsl:when>
										<xsl:otherwise></xsl:otherwise>
									</xsl:choose>
									
									<xsl:choose>
										<xsl:when test="./td[$var_td_header_row_count]/@rowspan"><rowspan><xsl:value-of select="./td[$var_td_header_row_count]/@rowspan"/></rowspan></xsl:when>
										<xsl:otherwise><rowspan>1</rowspan></xsl:otherwise>
									</xsl:choose>
									
									
									<xsl:element name="assessmentcriteria">
										<xsl:attribute name="acid" select="concat(concat($var_uan,'.'), normalize-space(td[$var_td_header_row_count]/ac_te_e))"/>
									</xsl:element>
								</criteriacell>
								</xsl:if>
							</xsl:if>
							
						</xsl:for-each>

				</criteriacolumn>
			</xsl:for-each>
		
		
		</criteriagradegrid>
	</xsl:template>
	

</xsl:stylesheet>

<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:strip-space elements="*"/>
	
	<!-- global variables -->
	<xsl:variable name="var_uan_str">M/503/6513</xsl:variable>
	<xsl:variable name="var_uan"><xsl:value-of select="replace($var_uan_str, '/', '_')"/></xsl:variable>
    <xsl:variable name="var_level_array" as="element()*">
        <Item>Pre-Level</Item>
        <Item>Pass</Item>
        <Item>Merit</Item>
        <Item>Distinction</Item>        
    </xsl:variable>	
    
		
	<!-- Main Root node -->
	<xsl:template match="/">
		<xsl:apply-templates select="specification/unit"/>
	</xsl:template>
	
	
	
	<!-- Unit Subnode -->	
	<xsl:template match="unit">
	<unit>
	
		<!-- uan number -->
		<uan><xsl:value-of select="$var_uan_str"/></uan>
					
						
		<!-- criteriagradegrid -->
		<!-- Approach here is 
		1. strip first 2 rows of all the assesment criteria tables
		2. merge into one long table
		3. call template to parse each column and output IQS. -->
		<xsl:variable name="mergetables">
		
			<!-- get only assessment criteria tables only -->
			<xsl:variable name="firstrow">
				<xsl:for-each select="table">
					<xsl:if test="./tr[1]/td[1]/ac_h1">
						<xsl:copy-of select="./tr[1]"/>
					</xsl:if>
				</xsl:for-each>						
			</xsl:variable>		
		
			<xsl:element name="table">
				<!-- Filter out for Assessment Critieria tables only -->
				<xsl:if test="table/tr[1]/td[1]/ac_h1">
					<!-- Take first header row -->
					<xsl:copy-of select="$firstrow/tr[1]"/>
					<xsl:apply-templates select="table" mode="criteriagradegrid"/>
				</xsl:if>
			</xsl:element>
		</xsl:variable>
		<!-- DEBUG: <xsl:copy-of select="$mergetables"/>-->
		
<!--	send mergedtables variable to IQS criteriagrid-->
		<xsl:if test="table/tr[1]/td[1]/ac_h1">
			<xsl:call-template name="criteriagradegrid">
				<xsl:with-param name="var_table" select="$mergetables"/>
			</xsl:call-template>
		</xsl:if>
	

	</unit>
	</xsl:template>
	<!-- End Unit Subnode -->	
	
	

	

	
	
	<!-- TEMPLATES -->
	
	
	<!-- Merge Assessment criteria tables -->
<!--	<xsl:template match="table" mode="criteriagradegrid">
	
	
	</xsl:template>
	-->
	
	
		
	
	<!-- Assumption: merge the row with Assessment Critieria data only, so skip first 2 rows -->
	<xsl:template match="table" mode="criteriagradegrid">
		<!-- Filter out for Assessment Critieria tables only -->
		<!-- DEBUG: <xsl:value-of select="tr[1]/td[1]/ac_h1"/>-->					
		<xsl:if test="tr[1]/td[1]/ac_h1">
		<xsl:copy-of select="tr[position() > 2]"/>
		</xsl:if>
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

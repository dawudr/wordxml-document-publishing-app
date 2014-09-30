<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:strip-space elements="*"/>
	
	<!-- global variables -->
	<xsl:variable name="var_uan_str">M/503/6513</xsl:variable>
	<xsl:variable name="var_uan"><xsl:value-of select="replace($var_uan_str, '/', '_')"/></xsl:variable>
    
		
	<!-- Main Root node -->
	<xsl:template match="/">
		<xsl:apply-templates select="specification/unit"/>
	</xsl:template>
	
	
	<!-- Unit Subnode -->	
	<xsl:template match="unit">
	<unit>
	
		<!-- uan number -->
		<uan><xsl:value-of select="$var_uan_str"/></uan>
			
		
		<!-- suggestedassignment -->
		<xsl:apply-templates select="table" mode="suggestedassignment"/>

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
			
		<!-- Filter out for Suggested assignment outlinestables only -->
		<xsl:if test="tr/td[1]/sao_h1">
			<!--DEBUG: <xsl:copy-of select="tr[position() > 1]"/> :DEBUG-->
			<!--DEBUG: Count: <xsl:value-of select="count(tr)"/>-->
			

			<!-- first header row Assumption: that this will never change -->
			<xsl:variable name="var_column_header" select="$var_table[1]/tr[1]/td"/>
			<!--DEBUG: <xsl:copy-of select="$var_header_row"/>-->
			
			
			<!-- Here goes some fun! -->
			<xsl:variable name="counter">1</xsl:variable>

			<!-- suggestedassignment: For each TD in Header TR; i.e for each column-->
			<!--<xsl:for-each select="$var_column_header">	-->		
				<xsl:variable name="var_td_header_row_count" select="position()-1"/>
				
					<!-- criteriacell: do For each TR row in that column TD; i.e go down the column -->
					<!-- Assumption: first 1 rows don't have Assesment criteria data, hence skip -->
					<xsl:for-each select="$var_table/tr">
<!--					DEBUG: <xsl:copy-of select="."/> :DEBUG
					DEBUG: Count: <xsl:value-of select="count($var_table/tr)"/>
					POSITION: <xsl:value-of select="position()"/>-->
					
					
						<xsl:if test="position() > 1">
						<xsl:variable name="var_assignmentid">
							<!-- Fix: sometimes assignments are in separated tables and sometimes they are merged into one table -->
							<xsl:choose>
								<xsl:when test="count(/specification/unit/table[tr/td/sao_te]) &gt; 1">
									<xsl:number count="table[tr/td/sao_te]" from="/specification/unit" level="multiple" format="1"/>
								</xsl:when>
								<xsl:otherwise><xsl:number count="tr[td/sao_te]" from="/specification/unit/table" level="multiple" format="1"/></xsl:otherwise>
							</xsl:choose>
						</xsl:variable>
						
							<xsl:element name="suggestedassignment">
								<xsl:attribute name="assignmentid">
									<xsl:value-of select="$var_uan"/>
									<xsl:text>.</xsl:text><xsl:value-of select="$var_assignmentid"/>
								</xsl:attribute>
								<numbertitle><xsl:text>Assignment: </xsl:text><xsl:value-of select="$var_assignmentid"/></numbertitle>
								<title><xsl:value-of select="normalize-space(td[2]/sao_te)"/></title>
								<scenario>
									<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
												
												
												<p><xsl:value-of select="td[3]/sao_te"/></p>
																								
												<xsl:for-each select="td[3]/*">
													<xsl:if test="name() != 'sao_te'">
													<!-- strip out attribute of html lists -->
													<xsl:choose>
														<xsl:when test="name() = 'ul'">
															<ul>
																<xsl:copy-of select="./*"/>
															</ul>
														</xsl:when>
														<xsl:otherwise><xsl:copy-of select="."/></xsl:otherwise>
													</xsl:choose>
													</xsl:if>
												</xsl:for-each>				



									<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>								
								</scenario>
								<assessmentmethod>
									<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
												<xsl:for-each select="td[4]/*">
													<!-- strip out attribute of html lists -->
													<xsl:choose>
														<xsl:when test="name() = 'ul'">
															<ul>
																<xsl:copy-of select="./*"/>
															</ul>
														</xsl:when>
														<xsl:otherwise><xsl:copy-of select="."/></xsl:otherwise>
													</xsl:choose>
												</xsl:for-each>											
									<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>									
								</assessmentmethod>
								
								<xsl:variable name="var_criteriacoveredlist" select="tokenize(td[1]/sao_te,',')"/>
								
								
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
					</xsl:for-each>
			<!--</xsl:for-each>-->
			
			
		</xsl:if>
		
	</xsl:template>
	

</xsl:stylesheet>

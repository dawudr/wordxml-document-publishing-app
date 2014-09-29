<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
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
			
		<!-- learningobjective -->
		<!-- Filter out for Learning Objective tables only -->
		<xsl:choose>
			<!-- Has assessment Criteria -->
			<xsl:when test="table/tr/td[1]/ac_h2">
				
				<xsl:for-each select="table">
					<xsl:choose>
						<!-- look for ac_h2 in 2nd row, first cell if it is assessment critieria table -->
						<xsl:when test="./tr[2]/td[1]/ac_h2">
							<!--AC TABLE <xsl:copy-of select="."/> END-->
							<xsl:call-template name="learningobjective_with_assessment">
								<xsl:with-param name="var_ac_table" select="."/>
							</xsl:call-template>
						</xsl:when>
					</xsl:choose>
				</xsl:for-each>
		
			</xsl:when>
			<xsl:otherwise>
			<!-- No assessment Criteria so just print learning objectives -->
				<xsl:for-each select="table[tr/td[1]/la_h2]">
					<xsl:call-template name="learningobjective_only"/>
				</xsl:for-each>
			</xsl:otherwise>
		</xsl:choose>

	</unit>
	</xsl:template>
	<!-- End Unit Subnode -->	
	
	
	
	
	<!-- TEMPLATES -->
	<!-- learningobjective -->	
	<xsl:template name="learningobjective_only">
		<xsl:param name="var_la_table" select="." />	
	
			<xsl:element name="learningobjective">
				<xsl:attribute name="loid"><xsl:value-of select="$var_uan"/><xsl:text>.</xsl:text>LO<xsl:number value="position()" format="AA"/></xsl:attribute>				
				<xsl:for-each select="$var_la_table/tr">
							<xsl:if test="starts-with(normalize-space(td/la_h2),'Learning')">
								<title><xsl:value-of select="substring-after( substring-before( normalize-space(td/la_h2) ,':' ) , 'Learning aim ')"/></title>
								<learningobjectivetext><xsl:value-of select="substring-after( normalize-space(td/la_h2) , ': ')"/></learningobjectivetext>
							</xsl:if>
				</xsl:for-each>
			</xsl:element>
	
	</xsl:template>
	
	
	
	<!-- learningobjective for assessment tr tables which are joined-->	
	<xsl:template name="learningobjective_with_assessment">
			<xsl:param name="var_ac_table" select="." />			

			<xsl:for-each select="$var_ac_table/tr">
					
				<xsl:if test="./td[1]/ac_h2">
					<xsl:variable name="var_criteriacovered_index" select="position()+1"/>
					<xsl:variable name="var_criteriacovered" select="./following-sibling::*[preceding::tr/td/ac_h2]"/>
					<!--DEBUG:<xsl:copy-of select="$var_criteriacovered"/>END	-->				
										
					<xsl:element name="learningobjective">
						<xsl:attribute name="loid">
							<xsl:value-of select="$var_uan"/><xsl:text>.</xsl:text>LO<xsl:value-of select="substring-after( substring-before( normalize-space(.) ,':' ) , 'Learning aim ')"/>
						</xsl:attribute>
						<title><xsl:value-of select="substring-after( substring-before( normalize-space(.) ,':' ) , 'Learning aim ')"/></title>
						<learningobjectivetext><xsl:value-of select="substring-after( normalize-space(.) , ': ')"/></learningobjectivetext>						
						
						<xsl:for-each select="$var_criteriacovered">
							<!--DEBUG:<xsl:copy-of select="."/>END-->
							<xsl:for-each select="./td">
								<xsl:if test="ac_te_e">
									<xsl:element name="criteriacovered">
										<xsl:attribute name="acid">
											<xsl:value-of select="$var_uan"/>
											<xsl:text>.</xsl:text><xsl:value-of select="normalize-space(ac_te_e)"/>
										</xsl:attribute>
									</xsl:element>
								</xsl:if>
							</xsl:for-each>
						</xsl:for-each>	
					</xsl:element>
				</xsl:if>
			</xsl:for-each>
			
	</xsl:template>
	

</xsl:stylesheet>

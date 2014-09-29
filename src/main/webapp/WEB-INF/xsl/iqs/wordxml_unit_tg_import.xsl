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
			
		
		<!-- genericfreetext -->
		<xsl:apply-templates select="." mode="unitessentialguidance"/>

	</unit>
	</xsl:template>
	<!-- End Unit Subnode -->	
	
	

	

	
	
	<!-- TEMPLATES -->
	
	<!-- genericfreetext -->	
	<xsl:template match="unit" mode="unitessentialguidance">
		<!--genericfreetext-->

<!--<xsl:copy-of select="//tg_st[text() = 'Resources']/following-sibling::tg_te[preceding::tg_st[text() = 'Assessment guidance']]"/>
-->


<xsl:for-each select="tg_st">

<xsl:choose>
	<xsl:when test="starts-with(., 'Resources')">
		<unitessentialguidance>
			<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
			<p><b><xsl:value-of select="."/></b></p>
			<!--<xsl:apply-templates select=".[text() = 'Resources']/following-sibling::tg_te[preceding::tg_st]"/>-->
			
<!--				<xsl:for-each select=".[text() = 'Resources']/following-sibling::tg_te[preceding::tg_st]">
-->				<xsl:for-each select=".[text() = 'Resources']/following-sibling::*[not(preceding::sao_t)]">
					<xsl:apply-templates select="."/>
					
					<!--<xsl:value-of select="."/>-->
				</xsl:for-each>
			
			
			<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>			
		</unitessentialguidance>
	</xsl:when>
	
<!--	<xsl:when test="fn:starts-with(., 'Assessment guidance')">
		<unitessentialguidance>
			<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
			<title><xsl:value-of select="."/></title>
			<xsl:apply-templates select=".[text() = 'Assessment guidance']/following-sibling::tg_te|tg_te_e[preceding::tg_st]"/>
			<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>					
		</unitessentialguidance>
	</xsl:when>-->
</xsl:choose>


</xsl:for-each>								


	</xsl:template>	
	
	<xsl:template match="sao_t">
			<!-- Hack: Suggested assignment outlines Blank so it doesn't print the title -->
	</xsl:template>
	
	<xsl:template match="tg_st | tg_h1"><xsl:text>
</xsl:text><p><b><xsl:value-of select="."/></b></p><xsl:text>
</xsl:text>
	</xsl:template>
	
	<xsl:template match="tg_te_e"><xsl:text>
</xsl:text><p><i><xsl:value-of select="."/></i></p><xsl:text>
</xsl:text>
	</xsl:template>
	
	<xsl:template match="tg_te"><xsl:text>
</xsl:text><p><xsl:value-of select="."/></p><xsl:text>
</xsl:text>
	</xsl:template>
	
	<xsl:template match="tg_te_s | ac_te_s">
		<p><b><xsl:value-of select="."/></b></p>
	</xsl:template>
	
	<xsl:template match="ul[@type='tg_bl1']">
			<ul>
					<xsl:for-each select="./li">
						<li>
							<xsl:value-of select="."/>
						</li>	
					</xsl:for-each>
			</ul>	
	</xsl:template>
	
</xsl:stylesheet>

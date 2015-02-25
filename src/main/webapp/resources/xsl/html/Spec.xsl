<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" indent="yes" omit-xml-declaration="yes"/>
	<xsl:template match="/">
	<xsl:result-document href="{specification/@id}.html">
		<html>
			<head>
				<title>Title</title>
			</head>
			<body>
				<table border="1" width="80%" align="center" cellpadding="5" cellspacing="1">
					<tbody>
						<tr align="left" valign="top">
							<td bgcolor="orange">
								<b>Print Document</b>
							</td>
							<td>
								<xsl:variable name="pdfprint">
									<xsl:value-of select="//printdocument"/>
								</xsl:variable>
								<xsl:element name="a">
									<xsl:attribute name="href"><xsl:value-of select="$pdfprint"/>.pdf</xsl:attribute>
									<xsl:value-of select="$pdfprint"/>
								</xsl:element>
							</td>
						</tr>
						<tr align="left" valign="top">
							<td bgcolor="orange">
								<b>Pub Code</b>
							</td>
							<td>
								<xsl:value-of select="//pubcode "/>
							</td>
						</tr>
						<tr align="left" valign="top">
							<td bgcolor="orange">
								<b>Subject</b>
							</td>
							<td>
								<xsl:value-of select="//subject/title"/>
							</td>
						</tr>
						<tr align="left" valign="top">
							<td bgcolor="orange">
								<b>Subject Type</b>
							</td>
							<td>
								<xsl:value-of select="//subject/@type"/>
							</td>
						</tr>
						<tr align="left" valign="top">
							<td bgcolor="orange">
								<b>Title</b>
							</td>
							<td>
								<xsl:value-of select="//title"/>
							</td>
						</tr>
						<tr align="left" valign="top">
							<td bgcolor="orange">
								<b>Level</b>
							</td>
							<td>
								<xsl:value-of select="//level"/>
							</td>
						</tr>
						<tr align="left" valign="top">
							<td bgcolor="orange">
								<b>Revision</b>
							</td>
							<td>
								<xsl:value-of select="//revision"/>
							</td>
						</tr>
						<tr align="left" valign="top">
							<td bgcolor="orange">
								<b>Qualification Id</b>
							</td>
							<td>
								<table border="1" cellpadding="1" cellspacing="5">
									<thead>
										<tr>
											<th>Qual ID</th>
											<th>Qual Title</th>
										</tr>
									</thead>
									<tbody>
										<xsl:for-each select="//qualref">
											<xsl:variable name="fieldvalue">
												<xsl:value-of select="./@qualid"/>
											</xsl:variable>
											<xsl:variable name="fieldvaluename">
												<xsl:value-of select="$fieldvalue"/>.xml</xsl:variable>
											<tr>
												<td>
													<xsl:element name="a">
														<xsl:attribute name="href"><xsl:value-of select="$fieldvalue"/>.html</xsl:attribute>
														<xsl:value-of select="./@qualid"/>
													</xsl:element>
												</td>
												<td>
													<xsl:value-of select="document($fieldvaluename)/qualification/title"/>
												</td>
											</tr>
										</xsl:for-each>
									</tbody>
								</table>
							</td>
						</tr>
						<tr align="left" valign="top">
							<td bgcolor="orange">
								<b>Unit Ids</b>
							</td>
							<td>
								<table border="1" cellpadding="1" cellspacing="5">
									<thead>
										<tr>
											<th>Unit ID</th>
											<th>Unit Title</th>
										</tr>
									</thead>
									<tbody>
										<xsl:for-each select="//unitref">
											<xsl:variable name="fieldvalue1">
												<xsl:value-of select="./@unitid"/>
											</xsl:variable>
											<xsl:variable name="fieldvalue1name">
												<xsl:value-of select="$fieldvalue1"/>.xml</xsl:variable>
											<tr>
												<td>
													<xsl:element name="a">
														<xsl:attribute name="href"><xsl:value-of select="$fieldvalue1"/>.html</xsl:attribute>
														<xsl:value-of select="./@unitid"/>
													</xsl:element>
												</td>
												<td>Unit&#160;<xsl:value-of select="document($fieldvalue1name)//unitNo"/>:&#160;<xsl:value-of select="document($fieldvalue1name)/unit/title"/>
												</td>
											</tr>
										</xsl:for-each>
									</tbody>
								</table>
							</td>
						</tr>
						<xsl:for-each select="//introductorytext">
						<tr>
							<td bgcolor="orange">
								<b>Introductory Text</b>
							</td>
							<td><xsl:value-of select="." disable-output-escaping="yes"/></td>
						</tr>
						</xsl:for-each>
						<xsl:for-each select="//features">
												<tr>
							<td bgcolor="orange">
								<b>Features</b>
							</td>
							<td><xsl:value-of select="." disable-output-escaping="yes"/></td>
						</tr>
						</xsl:for-each>
						<!-- Introductory Text
							Features-->
					</tbody>
				</table>
			</body>
		</html>
	</xsl:result-document>
	</xsl:template>
</xsl:stylesheet>

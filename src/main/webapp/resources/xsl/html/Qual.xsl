<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" indent="yes" omit-xml-declaration="yes"/>
	<xsl:template match="/">
		<xsl:result-document href="{qualification/@qualid}.html">
			<html>
				<head>
					<title>Title</title>
				</head>
				<body>
					<table border="1" width="80%" align="center" cellpadding="5" cellspacing="1">
						<tbody>
							<tr align="left" valign="top">
								<td bgcolor="orange">
									<b>AccCentreStartDate</b>
								</td>
								<td>
									<xsl:value-of select="//acccentrestartdate"/>
								</td>
							</tr>
							<tr align="left" valign="top">
								<td bgcolor="orange">
									<b>AccCertStartDate</b>
								</td>
								<td>
									<xsl:value-of select="//acccertstartdate"/>
								</td>
							</tr>
							<xsl:for-each select="//assessmentpurpose">
								<tr align="left" valign="top">
									<td bgcolor="orange">
										<b>Assessment Purpose</b>
									</td>
									<td>
										<xsl:value-of select="."/>
									</td>
								</tr>
							</xsl:for-each>
							<xsl:for-each select="qualification/creditvalue">
								<tr align="left" valign="top">
									<td bgcolor="orange">
										<b>Credit Value</b>
									</td>
									<td>
										<xsl:value-of select="."/>
									</td>
								</tr>
							</xsl:for-each>
							<tr align="left" valign="top">
								<td bgcolor="orange">
									<b>QAN</b>
								</td>
								<td>
									<xsl:value-of select="//qanNo"/>
								</td>
							</tr>
							<tr align="left" valign="top">
								<td bgcolor="orange">
									<b>Guided Learning Hours</b>
								</td>
								<td>
									<xsl:value-of select="//glhvalue"/>
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
									<b>Qualification Framework</b>
								</td>
								<td>
									<xsl:value-of select="//qualificationframework"/>
								</td>
							</tr>
							<tr align="left" valign="top">
								<td bgcolor="orange">
									<b>Qualification Size</b>
								</td>
								<td>
									<xsl:value-of select="//qualificationsize"/>
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
									<xsl:value-of select="//qualification/title"/>
								</td>
							</tr>
							<tr align="left" valign="top">
								<td bgcolor="orange">
									<b>RoC Text</b>
								</td>
								<td>
									<ol>
										<xsl:for-each select="//rulesofcombinationtext/rule">
											<li>
												<xsl:value-of select="." disable-output-escaping="yes"/>
											</li>
										</xsl:for-each>
									</ol>
								</td>
							</tr>
							<tr align="left" valign="middle">
								<td bgcolor="orange" colspan="2">
									<h2>Unit Groups</h2>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<xsl:for-each select="//pathway">
										<table border="1">
											<tbody>
												<tr align="left" valign="top">
													<td colspan="2">
														<b>Pathway&#160;<xsl:value-of select="substring-after(@id, 'P')"/>
														</b>:&#160;&#160;<xsl:value-of select="./title"/>
														<br/>
														<xsl:value-of select="./Description"/>
													</td>
												</tr>
												<tr align="left" valign="top">
													<td bgcolor="orange">
														<b>Rules Of Combination</b>
													</td>
													<td>
														<xsl:for-each select="./rulesofcombination/description">
															<p>
																<b>Description:</b>&#160;<xsl:value-of select="."/>
															</p>
														</xsl:for-each>
														<xsl:for-each select="./rulesofcombination/qualificationcreditvalue">
															<p>
																<b>Qualification Credit Value:</b>&#160;<xsl:value-of select="."/>
															</p>
														</xsl:for-each>
														<xsl:for-each select="./rulesofcombination/minimumcredit">
															<p>
																<b>Minimum Credit:</b>&#160;<xsl:value-of select="."/>
															</p>
														</xsl:for-each>
														<xsl:for-each select="./rulesofcombination/mandatoryunitcredit">
															<p>
																<b>Mandatory Unit Credit:</b>&#160;<xsl:value-of select="."/>
															</p>
														</xsl:for-each>
														<xsl:for-each select="./rulesofcombination/optionalunitcredit">
															<p>
																<b>Optional Unit Credit:</b>&#160;<xsl:value-of select="."/>
															</p>
														</xsl:for-each>
														<xsl:for-each select="./rulesofcombination/othersunitcredit">
															<p>
																<b>Others Unit Credit:</b>&#160;<xsl:value-of select="."/>
															</p>
														</xsl:for-each>
														<xsl:for-each select="./rulesofcombination/barredunitcombinations/barredunitcombination">
															<p>
																<b>BarredUnitCombination:</b>&#160;<xsl:value-of select="./description" disable-output-escaping="yes"/>
																<ul>
																	<li>
																		<b>Max Units:</b>&#160;<xsl:value-of select="./@maxunits"/>
																	</li>
																	<li>
																		<b>Unit Ids</b>
																		<ul>
																			<xsl:for-each select="./unitrefs/unitref">
																				<li>
																					<xsl:element name="a">
																						<xsl:attribute name="href"><xsl:value-of select="./@unitid"/>.html</xsl:attribute>
																						<xsl:value-of select="./@unitid"/>
																					</xsl:element>
																				</li>
																			</xsl:for-each>
																		</ul>
																	</li>
																</ul>
															</p>
														</xsl:for-each>
													</td>
												</tr>
												<xsl:for-each select="./qualgroup">
													<tr align="left" valign="top">
														<td bgcolor="orange">
															<b>Qual Group:&#160;</b>
															<xsl:value-of select="./groupname"/>
														</td>
														<td>
															<table border="1">
																<tbody>
																	<tr align="left" valign="top">
																		<th>UAN</th>
																		<th align="center">Unit Number</th>
																		<th>Unit Title</th>
																		<th>Credit Value</th>
																		<th>Required Status</th>
																	</tr>
																	<xsl:for-each select="qualunit">
																		<xsl:variable name="unitid" select="./unitref/@unitid"/>
																		<xsl:variable name="fieldvaluename">
																			<xsl:value-of select="$unitid"/>.xml</xsl:variable>
																		<tr align="left" valign="top">
																			<td>
																				<xsl:element name="a">
																					<xsl:attribute name="href"><xsl:value-of select="$unitid"/>.html</xsl:attribute>
																					<xsl:value-of select="$unitid"/>
																				</xsl:element>
																			</td>
																			<td align="center">
																				<xsl:value-of select="qualunittitlenum"/>
																			</td>
																			<td>
																				<xsl:value-of select="document($fieldvaluename)/unit/title"/>
																			</td>
																			<td align="center">
																				<xsl:value-of select="creditvalue"/>
																			</td>
																			<td>
																				<xsl:value-of select="requiredstatus"/>
																			</td>
																		</tr>
																	</xsl:for-each>
																</tbody>
															</table>
														</td>
													</tr>
												</xsl:for-each>
											</tbody>
										</table>
										<br/>
										<br/>
									</xsl:for-each>
								</td>
							</tr>
							<xsl:for-each select="//resources">
								<tr>
									<td bgcolor="orange">
										<b>Resources</b>
									</td>
									<td>
										<xsl:value-of select="." disable-output-escaping="yes"/>
									</td>
								</tr>
							</xsl:for-each>
							<xsl:for-each select="//aims">
								<tr>
									<td bgcolor="orange">
										<b>Aims</b>
									</td>
									<td>
										<xsl:value-of select="." disable-output-escaping="yes"/>
									</td>
								</tr>
							</xsl:for-each>
							<xsl:for-each select="//features">
								<tr>
									<td bgcolor="orange">
										<b>Features</b>
									</td>
									<td>
										<xsl:value-of select="." disable-output-escaping="yes"/>
									</td>
								</tr>
							</xsl:for-each>
							<xsl:for-each select="//progression/progressiontext">
								<tr>
									<td bgcolor="orange">
										<b>Progression Text</b>
									</td>
									<td>
										<xsl:value-of select="." disable-output-escaping="yes"/>
									</td>
								</tr>
							</xsl:for-each>
							<xsl:for-each select="//entryrequirements">
								<tr>
									<td bgcolor="orange">
										<b>
											<xsl:value-of select="./title"/>
										</b>
									</td>
									<td>
										<xsl:value-of select="./entryrequirement" disable-output-escaping="yes"/>
									</td>
								</tr>
							</xsl:for-each>
							<xsl:for-each select="//genericfreetext">
								<tr>
									<td bgcolor="orange">
										<b>Generic Free Text</b>
									</td>
									<td>
										<xsl:value-of select="." disable-output-escaping="yes"/>
									</td>
								</tr>
							</xsl:for-each>
						</tbody>
					</table>
				</body>
			</html>
		</xsl:result-document>
	</xsl:template>
</xsl:stylesheet>

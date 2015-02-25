<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" indent="yes" omit-xml-declaration="yes"/>
	<xsl:template match="/">
		<xsl:result-document href="{unit/@unitid}.html">
			<html>
				<head>
					<title>Title</title>
				</head>
				<body>
					<table border="1" width="80%" align="center" cellpadding="5" cellspacing="1">
						<tbody>
							<tr>
								<td bgcolor="orange">
									<table cellpadding="5" cellspacing="1">
										<tbody>
											<tr>
												<td>
													<h1>
														<xsl:text>Unit&#160;&#160;</xsl:text>
														<xsl:apply-templates select="//unitnumber"/>
														<xsl:text>:</xsl:text>
													</h1>
												</td>
												<td>
													<h1>
														<xsl:apply-templates select="unit/title"/>
													</h1>
												</td>
											</tr>
											<tr>
												<td>
													<h2>
														<xsl:text>Unit code: </xsl:text>
													</h2>
												</td>
												<td>
													<h2>
														<xsl:value-of select="unit/uan"/>
													</h2>
												</td>
											</tr>
											<tr>
												<td>
													<h2>
														<xsl:text>QCF Level:</xsl:text>
													</h2>
												</td>
												<td>
													<h2>
														<xsl:value-of select="unit/level"/>
													</h2>
												</td>
											</tr>
											<xsl:for-each select="unit/creditvalue">
												<tr>
													<td>
														<h2>
															<xsl:text>Credit value: </xsl:text>
														</h2>
													</td>
													<td>
														<h2>
															<xsl:apply-templates select="."/>
														</h2>
													</td>
												</tr>
											</xsl:for-each>
											<tr>
												<td>
													<h2>
														<xsl:text>Guided learning hours: </xsl:text>
													</h2>
												</td>
												<td>
													<h2>
														<xsl:apply-templates select="//glhvalue"/>
													</h2>
												</td>
											</tr>
											<tr>
												<td>
													<h2>
														<xsl:text>Assessment Mode: </xsl:text>
													</h2>
												</td>
												<td>
													<h2>
														<xsl:apply-templates select="//assessmentmode"/>
													</h2>
												</td>
											</tr>
											<tr>
												<td>
													<h2>
														<xsl:text>Print Document:</xsl:text>
													</h2>
												</td>
												<td>
													<h2>
														<xsl:variable name="print" select="//printdocument"/>
														<xsl:element name="a">
															<xsl:attribute name="href"><xsl:value-of select="$print"/>.pdf</xsl:attribute>
															<xsl:apply-templates select="$print"/>
														</xsl:element>
													</h2>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
							<xsl:for-each select="//aim">
								<tr>
									<td>
										<ul>
											<li>
												<h2>Aim and purpose</h2>
												<xsl:value-of select="." disable-output-escaping="yes"/>
											</li>
										</ul>
									</td>
								</tr>
							</xsl:for-each>
							<tr>
								<td>
									<ul>
										<li>
											<h2>Unit introduction</h2>
											<xsl:value-of select="unit/description" disable-output-escaping="yes"/>
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<td>
									<ul>
										<li>
											<h2>Learning outcomes</h2>
											<b>On completion of this unit a learner should:</b>
											<ol>
												<xsl:for-each select="//learningobjective">
													<li>
														<xsl:apply-templates select="./learningobjectivetext"/>
													</li>
												</xsl:for-each>
											</ol>
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<td>
									<h2>Unit content</h2>
									<ol>
										<xsl:for-each select="//unitsection">
											<li>
												<b>
													<xsl:value-of select="title" disable-output-escaping="yes"/>
												</b>
											</li>
											<xsl:value-of select="sectiontext" disable-output-escaping="yes"/>
											<br/>
											<br/>
										</xsl:for-each>
									</ol>
								</td>
							</tr>
							<!--<tr>
							<td>
								<h2>Assessment and grading criteria</h2>
								<xsl:text>In order to pass this unit, the evidence that the learner presents for assessment needs to demonstrate that they can meet all the learning outcomes for the unit. The assessment criteria for a pass grade describe the level of achievement required to pass this unit.</xsl:text>
								<xsl:call-template name="table"/>-->
<!--<table border="1">
<thead>
<tr>
<xsl:for-each select="//criteriagradegrid/criteriacolumn/title">
<th bgcolor="orange"><xsl:value-of select="."/>&#160;<xsl:value-of select="../grade/value"/></th>
</xsl:for-each>
</tr>
</thead>
<tbody>
<xsl:variable name="column2" select="//criteriagradegrid/criteriacolumn[2]/criteriacell"/>
<xsl:variable name="column3" select="//criteriagradegrid/criteriacolumn[3]/criteriacell"/>
<xsl:for-each select="//criteriagradegrid/criteriacolumn[1]/criteriacell">
<tr>
<xsl:choose>
<xsl:when test="./rownum = $column2/rownum">
<td><xsl:value-of select="./assessmentcriteria/@acid"/></td>
<xsl:call-template name="column2"/>
<xsl:choose>
<xsl:when test="./rownum = $column3/rownum">
<xsl:call-template name="column3"/>
</xsl:when>
<xsl:otherwise>
<td>NULL</td>
</xsl:otherwise>
</xsl:choose>
</xsl:when>
<xsl:otherwise>
<td><xsl:value-of select="./assessmentcriteria/@acid"/></td>
<td>NULL</td>
<xsl:choose>
<xsl:when test="./rownum = $column3/rownum">
<xsl:call-template name="column3"/>
</xsl:when>
<xsl:otherwise>
<td>NULL</td>
</xsl:otherwise>
</xsl:choose>
</xsl:otherwise>
</xsl:choose>
</tr>
</xsl:for-each>
	</tbody>
</table>-->
<!--</td>
</tr>-->

<!--<table border="1" width="99%">
									<tbody>
										<tr>
											<th bgcolor="orange" colspan="3" align="left">Assessment and grading criteria</th>
										</tr>
										<tr bgcolor="orange" align="left">
											<xsl:for-each select="//AssessmentGrades/AssessmentGrade">
												<th width="33%">
													<xsl:value-of select="Description" disable-output-escaping="yes"/>
												</th>
											</xsl:for-each>
										</tr>

<xsl:for-each select="//AssessmentGrades/AssessmentGrade[1]/Criteria/AssessmentCriteria">
<tr>
<xsl:choose>
<xsl:when test=".//RowNumber = $rownumber2/RowNumber">
<td><xsl:value-of select="./CriteriaText"/></td>
<xsl:call-template name="column2"/>
<xsl:choose>
<xsl:when test=".//RowNumber = $rownumber3/RowNumber">
<td><xsl:value-of select="$rownumber3/CriteriaText"/></td>
</xsl:when>
<xsl:otherwise>
<td></td>
</xsl:otherwise>
</xsl:choose>
</xsl:when>
<xsl:otherwise>
<td><xsl:value-of select="./CriteriaText"/></td>
<td></td>
<xsl:choose>
<xsl:when test=".//RowNumber = $rownumber3/RowNumber">
<td><xsl:call-template name="column3"/></td>
</xsl:when>
<xsl:otherwise>
<td></td>
</xsl:otherwise>
</xsl:choose>
</xsl:otherwise>
</xsl:choose>
</tr>
</xsl:for-each>

</tbody>
								</table>
							</td>
						</tr>-->
							<xsl:for-each select="//outlinelearningplan">
								<tr>
									<td>
										<h2>Outline learning plan</h2>The outline learning plan has been included in this unit as guidance and can be used in conjunction with the
programme of suggested assignments.<br/>
The outline learning plan demonstrates one way of planning the delivery and assessment of this unit.
	<table border="1">
											<tbody>
												<tr align="left">
													<th bgcolor="orange">
														<xsl:value-of select="//outlinelearningplan/title"/>
													</th>
												</tr>
												<xsl:for-each select="//outlinelearningplan/activity | //outlinelearningplan/assignmentref">
													<xsl:choose>
														<xsl:when test="normalize-space(.) != ''">
															<tr>
																<td>
																	<xsl:value-of select="." disable-output-escaping="yes"/>
																</td>
															</tr>
														</xsl:when>
														<xsl:otherwise>
															<xsl:variable name="assignmentid">
																<xsl:value-of select="./@assignmentid"/>
															</xsl:variable>
															<xsl:variable name="assignmenttext">
																<xsl:value-of select="//suggestedassignment[@assignmentid=$assignmentid]/title"/>
															</xsl:variable>
															<tr>
																<td>
																	<b>Assignment <xsl:value-of select="substring-after(./@assignmentid, '.')"/>
																		<xsl:text>: </xsl:text>
																	</b>
																	<xsl:value-of select="$assignmenttext" disable-output-escaping="yes"/>
																</td>
															</tr>
														</xsl:otherwise>
													</xsl:choose>
												</xsl:for-each>
											</tbody>
										</table>
									</td>
								</tr>
							</xsl:for-each>
							<tr>
								<td>
									<h2>Programme of suggested assignments</h2>
		The table below shows a programme of suggested assignments that cover the pass, merit and distinction
criteria in the assessment and grading grid. This is for guidance and it is recommended that centres either
write their own assignments or adapt any Edexcel assignments to meet local needs and resources.
<table border="1" width="">
										<tbody>
											<tr align="left">
												<th bgcolor="orange">Criteria covered</th>
												<th bgcolor="orange">Assignment title</th>
												<th bgcolor="orange">Scenario</th>
												<th bgcolor="orange">Assessment method</th>
											</tr>
											<xsl:for-each select="//suggestedassignment">
												<tr align="left" valign="top">
													<td>
														<xsl:for-each select="./criteriacovered">
															<xsl:choose>
																<xsl:when test="position()=last()">
																	<xsl:variable name="Criteria_covered_id" select="substring-after(@acid, '.')"/>
																	<xsl:value-of select="$Criteria_covered_id"/>
																</xsl:when>
																<xsl:otherwise>
																	<xsl:variable name="Criteria_covered_id" select="substring-after(@acid, '.')"/>
																	<xsl:value-of select="$Criteria_covered_id"/>
																	<xsl:text>,&#160;</xsl:text>
																</xsl:otherwise>
															</xsl:choose>
														</xsl:for-each>
													</td>
													<td>
														<xsl:value-of select="numbertitle" disable-output-escaping="yes"/>
														<xsl:text> </xsl:text>
														<xsl:value-of select="title" disable-output-escaping="yes"/>
													</td>
													<td>
														<xsl:value-of select="scenario" disable-output-escaping="yes"/>
													</td>
													<td>
														<xsl:value-of select="assessmentmethod" disable-output-escaping="yes"/>
													</td>
												</tr>
											</xsl:for-each>
										</tbody>
									</table>
								</td>
							</tr>
							<xsl:for-each select="//associatedqualifications">
								<tr>
									<td>
										<h2>Associated Qualifications</h2>
										<table cellpadding="5" border="1">
											<tbody>
												<tr>
													<th bgcolor="orange">UAN</th>
													<th bgcolor="orange">Title</th>
													<th bgcolor="orange">Level</th>
												</tr>
												<xsl:for-each select="./unitref">
													<tr>
														<td>
															<xsl:value-of select="./uan"/>
														</td>
														<td>
															<xsl:value-of select="./title"/>
														</td>
														<td>
															<xsl:value-of select="./level"/>
														</td>
													</tr>
												</xsl:for-each>
											</tbody>
										</table>
									</td>
								</tr>
							</xsl:for-each>
							<xsl:for-each select="//unitessentialguidance">
								<tr>
									<td>
										<h2>Unit Essential Guidance</h2>
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


	<!--<xsl:template name="column1" match="//criteriagradegrid/criteriacolumn[1]/criteriacell">
		<td>
			<xsl:apply-templates select="./assessmentcriteria/@acid"/>
		</td>
	</xsl:template>

	<xsl:template name="column2" match="//criteriagradegrid/criteriacolumn[2]/criteriacell">
			<xsl:if test="./rownum = //criteriagradegrid/criteriacolumn[1]/criteriacell/rownum">
				<td>
					<xsl:apply-templates select="./assessmentcriteria/@acid"/>
				</td>
			</xsl:if>
	</xsl:template>
	
	<xsl:template name="column3" match="//criteriagradegrid/criteriacolumn[3]/criteriacell">
			<xsl:if test="./rownum = //criteriagradegrid/criteriacolumn[1]/criteriacell/rownum">
				<td>
					<xsl:apply-templates select="./assessmentcriteria/@acid"/>
				</td>
			</xsl:if>
	</xsl:template>
	
	<xsl:template match="//criteriagradegrid/criteriacolumn" name="table">
	<xsl:if test="position()=1">
		<xsl:for-each select="./criteriacell">
			<tr>
				<xsl:call-template name="column1"/>
				<xsl:call-template name="column2"/>
				<xsl:call-template name="column3"/>
			</tr>
		</xsl:for-each>
	</xsl:if>
	</xsl:template>-->

</xsl:stylesheet>

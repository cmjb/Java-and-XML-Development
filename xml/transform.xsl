<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml"
	version="1.0">

<xsl:template match="/">
	<html>
		<body>
			<h1>Tests</h1>
			<hr/>
			<xsl:for-each select="tests/test">
			
				Modulecode: <xsl:value-of select="./@modulecode"/>
				<br />Date: <xsl:value-of select="./@date"/>
			
		

			<xsl:apply-templates select="./questions/@id"/> 
				<hr />
			</xsl:for-each>
		</body>
	</html>
</xsl:template>

<xsl:variable name="questionsDoc" select="document('questions.xml')" /> <!-- Variable for second Document -->

<xsl:template match="@id">
					<xsl:variable name="pointID" select="."/> <!-- Store the matched ID -->
			<h1>Question <xsl:value-of select="position()"/>: <xsl:value-of select="$questionsDoc/questions/question[@id=$pointID]/title" /></h1>
		<ol type="I">
			
						<xsl:for-each select="$questionsDoc/questions/question[@id=$pointID]/answers/answer"> <!-- Reference second document for answers with variable for id -->
				<li><input type="radio" name="home"> </input><xsl:value-of select="." /></li>
			</xsl:for-each>
		
		</ol>
</xsl:template>

</xsl:stylesheet>

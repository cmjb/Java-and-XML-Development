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
			
				
			<xsl:for-each select="questions/question"> 
		<h1>Question <xsl:value-of select="position()"/>:</h1>


				<hr />
			<h1><xsl:value-of select="./title" /></h1>
			<ol type="I">
			<xsl:for-each select="answers/answer">
			<li><input type="radio" name="home"> </input> <xsl:value-of select="." /></li>
			</xsl:for-each>
			</ol>
			</xsl:for-each>
		
			</xsl:for-each>
		</body>
	</html>
</xsl:template>

<xsl:template match="@id">

</xsl:template>

</xsl:stylesheet>

<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.1" xmlns:tvar="http://brreg/vpoint">
  <xsl:output method="xml"/>
  <xsl:template match="/">
    <xsl:apply-templates/>
  </xsl:template>
  <xsl:template match="tvar:jakke">
    <xsl:apply-templates select="document(concat(.,'.xml'))"/>
  </xsl:template>
  <xsl:template match="tvar:skjorte">
    <xsl:apply-templates select="document(concat(.,'.xml'))"/>
  </xsl:template>
  <xsl:template match="tvar:bukser">
    <xsl:apply-templates select="document(concat(.,'.xml'))"/>
  </xsl:template>
  <xsl:template match="tvar:sko">
    <xsl:apply-templates select="document(concat(.,'.xml'))"/>
  </xsl:template>
  <xsl:template match="node()[not(name()='emptyPlaceholder')]|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*" />
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>


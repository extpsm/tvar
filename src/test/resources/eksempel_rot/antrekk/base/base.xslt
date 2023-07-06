<?xml version="1.0"?>
<xsl:stylesheet  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.1"  xmlns:tvar="http://brreg/vpoint">
  <xsl:output method="xml"/>
  <xsl:template match="/">
    <xsl:apply-templates/>
  </xsl:template>
  <xsl:template match="tvar:jakke">
    <tvar:jakke>./jakke/jakkeXX</tvar:jakke>
  </xsl:template>
  <xsl:template match="tvar:skjorte">
    <tvar:skjorte>./skjorte/skjorteXX</tvar:skjorte>
  </xsl:template>
  <xsl:template match="tvar:bukser">
    <tvar:bukser>./bukser/bukserXX</tvar:bukser>
  </xsl:template>
  <xsl:template match="tvar:sko">
    <tvar:sko>./sko/skoXX</tvar:sko>
  </xsl:template>
  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>

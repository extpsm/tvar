<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.1" xmlns:tvar="http://brreg/vpoint">
  <xsl:output method="xml"/>
  <xsl:template match="/">
    <xsl:apply-templates/>
  </xsl:template>
  <xsl:template match="tvar:innsenderVegadresse|tvar:forretningsadresse|tvar:stedsadresse|tvar:varslingsadresse|tvar:innehaver|tvar:rolle1|tvar:rolle2|tvar:innehaver | tvar:aktivitet | tvar:postadresse">
    <xsl:apply-templates select="document(concat(.,'.xml'))"/>
  </xsl:template>
  <xsl:template match="node()[not(name()='emptyPlaceholder')]|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*" />
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>


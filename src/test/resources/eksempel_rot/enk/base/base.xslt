<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.1" xmlns:tvar="http://brreg/vpoint">
    <xsl:output method="xml"/>
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="tvar:innsenderVegadresse">
        <tvar:innsenderVegadresse>./innsenderVegadresse/innsenderVegadresseXX</tvar:innsenderVegadresse>
    </xsl:template>
    <xsl:template match="tvar:forretningsadresse">
        <tvar:forretningsadresse>./forretningsadresse/forretningsadresseXX</tvar:forretningsadresse>
    </xsl:template>
    <xsl:template match="tvar:forretningsadresse">
        <tvar:forretningsadresse>./forretningsadresse/forretningsadresseXX</tvar:forretningsadresse>
    </xsl:template>
    <xsl:template match="tvar:stedsadresse">
        <tvar:stedsadresse>./stedsadresse/stedsadresseXX</tvar:stedsadresse>
    </xsl:template>
    <xsl:template match="tvar:varslingsadresse">
        <tvar:varslingsadresse>./varslingsadresse/varslingsadresseXX</tvar:varslingsadresse>
    </xsl:template>
    <xsl:template match="tvar:innehaver">
        <tvar:innehaver>./innehaver/innehaverXX</tvar:innehaver>
    </xsl:template>
    <xsl:template match="tvar:rolle1">
        <tvar:rolle1>./rolle1/rolle1XX</tvar:rolle1>
    </xsl:template>
    <xsl:template match="tvar:rolle2">
        <tvar:rolle2>./rolle1/rolle2XX</tvar:rolle2>
    </xsl:template>
    <xsl:template match="tvar:innehaver">
        <tvar:innehaver>./innehaver/innehaverXX</tvar:innehaver>
    </xsl:template>
    <xsl:template match="tvar:postadresse">
        <tvar:postadresse>./postadresse/postadresseXX</tvar:postadresse>
    </xsl:template>
    <xsl:template match="tvar:aktivitet">
        <tvar:aktivitet>./aktivitet/aktivitetXX</tvar:aktivitet>
    </xsl:template>
    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
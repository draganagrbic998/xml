<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
 	xmlns:poverenik="https://github.com/draganagrbic998/xml/poverenik"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    
    <xsl:variable name="NewLine">
    <xsl:text>&#xa;</xsl:text>
	</xsl:variable>
	
	<xsl:variable name="razlog">
    <xsl:text>nije postupio / nije postupio u celosti /  u zakonskom roku</xsl:text>
	</xsl:variable>
	
	<xsl:variable name="Tab">
    <xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
	</xsl:variable>
	
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zalba-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="zalba-page">
                <fo:flow flow-name="xsl-region-body" linefeed-treatment="preserve">
                    <fo:block linefeed-treatment="preserve" text-align="center" font-family="sans-serif" font-size="14px" font-weight="bold">
                        ŽALBA KADA ORGAN VLASTI NIJE POSTUPIO/ nije postupio u celosti/ PO ZAHTEVU TRAŽIOCA U ZAKONSKOM  ROKU  (CUTANjE UPRAVE)
                    </fo:block>
                    <fo:block  text-align="justify" font-family="sans-serif" font-size="12px">
                    	<fo:inline font-weight="bold">
                   			Povereniku za informacije od javnog znacaja i zaštitu podataka o licnosti
                        </fo:inline>
                        <fo:inline>
                            <xsl:text>Adresa za poštu: </xsl:text>
                        </fo:inline>
                    </fo:block>
                    <fo:block text-align="justify" font-family="sans-serif" font-size="12px">                    	
                    	U skladu sa clanom 22. Zakona o slobodnom pristupu informacijama od javnog znacaja podnosim:
                    </fo:block>
                    <fo:block linefeed-treatment="preserve" text-align="center" font-family="sans-serif" font-size="12px">
						<fo:inline font-weight="bold">
                            Ž A L B U
                        </fo:inline>
                        <fo:inline>
                            protiv
                        </fo:inline>
                        <fo:inline>
                           <xsl:value-of select="poverenik:Zalba/poverenik:organVlasti"/>
                        </fo:inline>
                        <fo:inline>
                           (navesti naziv organa)
                        </fo:inline>
                        <fo:inline>
                            zbog toga što organ vlasti: 
                        </fo:inline>
                        <fo:inline font-weight="bold">
                          	<xsl:text>nije postupio / nije postupio u celosti /  u zakonskom roku</xsl:text>
                        </fo:inline>
                        <fo:inline>
                           (podvuci  zbog cega se izjavljuje žalba)
                        </fo:inline>
                    </fo:block>
                    <fo:block text-align="justify" font-family="sans-serif" font-size="12px">
	                	<fo:inline text-align="justify">
							po mom zahtevu  za slobodan pristup informacijama od javnog značaja koji sam podneo  tom organu  dana <xsl:value-of select="poverenik:Zalba/poverenik:datumZahteva"/> godine, a kojim sam tražio/la da mi se u skladu sa Zakonom o slobodnom pristupu informacijama od javnog značaja omogući uvid- kopija dokumenta koji sadrži informacije  o /u vezi sa :    
						</fo:inline>
                        <fo:inline>
                           <xsl:value-of select="poverenik:Zalba/osnova:detalji"/>
                        </fo:inline>
                	</fo:block>
                	<fo:block  text-align="justify" font-family="sans-serif" font-size="12px">
	                	<fo:inline><xsl:value-of select="$Tab" />Na osnovu iznetog, predlažem da Poverenik uvaži moju žalbu i omogući mi pristup traženoj/im  informaciji/ma.
						</fo:inline>
                        <fo:inline><xsl:value-of select="$Tab" />Kao dokaz , uz žalbu dostavljam kopiju zahteva sa dokazom o predaji organu vlasti.
                        </fo:inline>
                        <fo:inline font-weight="bold"><xsl:value-of select="$Tab" />Napomena: </fo:inline><fo:inline>Kod žalbe  zbog nepostupanju po zahtevu u celosti, treba priložiti i dobijeni odgovor organa vlasti.
                        </fo:inline>
                	</fo:block>
                	<fo:block-container height="1cm" width="6cm" top="17cm" left="11.2cm" position="absolute">
	              		<fo:block text-align="right" border-bottom="solid" border-bottom-width="0.2mm">
				            <xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba/osnova:ime" /> <xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba/osnova:prezime" />
				        </fo:block>
	                    <fo:block text-align="right" font-family="sans-serif" font-size="10px">
						Podnosilac žalbe / Ime i prezime	                    
						</fo:block>
	                    <fo:block text-align="right" border-bottom="solid" border-bottom-width="0.2mm">
				            <xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa/osnova:mesto" /> <xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa/osnova:ulica" /> <xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa/osnova:broj" />
				        </fo:block>
	                    <fo:block text-align="right" font-family="sans-serif" font-size="10px">
	                    adresa
	                    </fo:block>
	                    <fo:block text-align="right" border-bottom="solid" border-bottom-width="0.2mm">
				            <xsl:value-of select="poverenik:Zalba/osnova:kontakt" /> 
				        </fo:block>
	                    <fo:block text-align="right" font-family="sans-serif" font-size="10px">
	                    drugi podaci za kontakt
	                    </fo:block>
	                    <fo:block text-align="right" border-bottom="solid" border-bottom-width="0.2mm">
				            <xsl:value-of select="poverenik:Zalba/osnova:potpis" /> 
				        </fo:block>
	                    <fo:block text-align="right" font-family="sans-serif" font-size="10px">
	                    potpis
	                    </fo:block>
              		</fo:block-container>
              		<fo:block-container height="4cm" width="12cm" top="22cm" left="0cm" position="absolute">
              			<fo:block text-align="start" font-family="sans-serif"  font-size="12px">
              			U <xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa/osnova:mesto" />, dana <xsl:value-of select="poverenik:Zalba/osnova:datum" /> godine</fo:block>
              		</fo:block-container>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>

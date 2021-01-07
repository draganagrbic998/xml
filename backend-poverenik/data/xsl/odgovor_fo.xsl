<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:odgovor="https://github.com/draganagrbic998/xml/odgovor">
	
    <xsl:template match="/odgovor:Odgovor">
        <fo:root font-size="12px" text-align="justify" font-family="Times New Roman">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="odgovor-page">
                    <fo:region-body margin="0.8in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="odgovor-page">
                <fo:flow flow-name="xsl-region-body">
                
					<fo:block text-align="center" border-bottom="1px dotted black" margin-left="80px" margin-right="80px">
						<xsl:variable name="sedisteMesto" select="osnova:OrganVlasti/osnova:Adresa/osnova:mesto"></xsl:variable>
						<xsl:variable name="sedisteUlica" select="osnova:OrganVlasti/osnova:Adresa/osnova:ulica"></xsl:variable>
						<xsl:variable name="sedisteBroj" select="osnova:OrganVlasti/osnova:Adresa/osnova:broj"></xsl:variable>
						<xsl:variable name="sediste" select="concat($sedisteUlica, concat(' ', concat($sedisteBroj, concat(', ', $sedisteMesto))))"></xsl:variable>
						<xsl:value-of select="concat(osnova:OrganVlasti/osnova:naziv, concat(', ', $sediste))"></xsl:value-of>
					</fo:block>
					
					<fo:block text-align="center">
						назив и седиште органа власти
					</fo:block>
					
					<fo:block linefeed-treatment="preserve">
						&#160;
						&#160;
					</fo:block>
					
					<fo:block text-align="center" font-weight="bold" font-size="16px">
						ОДГОВОР НА ЖАЛБУ		
					</fo:block>
					
					<fo:block text-align="center" font-weight="bold" font-size="16px">
						ЗБОГ УСКРАЋЕНОГ ПРАВА ЗА ПРИСТУП ИНФОРМАЦИЈАМА ОД ЈАВНОГ ЗНАЧАЈА	
					</fo:block>
					
					<fo:block>
						<xsl:variable name="danZalbe" select="substring-after(substring-after(odgovor:datumZalbe, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecZalbe" select="substring-before(substring-after(odgovor:datumZalbe, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaZalbe" select="substring-before(odgovor:datumZalbe, '-')"></xsl:variable>
						Број жалбе: 
						<fo:inline border-bottom="0.2mm solid black">
							<xsl:value-of select="osnova:broj"></xsl:value-of>
						</fo:inline> 
						, поднешена дана
						<fo:inline border-bottom="0.2mm solid black">
							<xsl:value-of select="concat($danZalbe, concat('.', concat($mesecZalbe, concat('.', concat($godinaZalbe, '.')))))"></xsl:value-of>
						</fo:inline> 
						године.
					</fo:block>
					<fo:block>
						<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godina" select="substring-before(osnova:datum, '-')"></xsl:variable>
	            		Датум одговора органа власти:
	            		<fo:inline border-bottom="0.2mm solid black">
							<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
	            		</fo:inline>
					</fo:block>
					
					<fo:block linefeed-treatment="preserve">
						&#160;
						&#160;
					</fo:block>
					
					<fo:block text-indent="40px">
						Орган власти 
						<fo:inline border-bottom="1px solid black"><xsl:value-of select="osnova:OrganVlasti/osnova:naziv"></xsl:value-of></fo:inline>
						подноси одговор на жалбу против ускраћеног права за информацијама од јавног значаја, коју је
						<fo:inline border-bottom="underline">
							<xsl:variable name="osoba" select="osnova:Osoba"></xsl:variable>
							<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
						</fo:inline>
						поднео/ла дана 
						<fo:inline border-bottom="1px solid black">
							<xsl:variable name="danZalbe" select="substring-after(substring-after(odgovor:datumZalbe, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecZalbe" select="substring-before(substring-after(odgovor:datumZalbe, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaZalbe" select="substring-before(odgovor:datumZalbe, '-')"></xsl:variable>
							<xsl:value-of select="concat($danZalbe, concat('.', concat($mesecZalbe, concat('.', concat($godinaZalbe, '.')))))"></xsl:value-of>
						</fo:inline>
						Одговор органа власти на поднету жалбу је следећи:
					</fo:block>
					
					<fo:block>
			            <xsl:apply-templates select="osnova:Detalji"></xsl:apply-templates>
					</fo:block>		
								
					<fo:block-container position="absolute" top="9.1cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		
              		<fo:block-container position="absolute" top="9.6cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		
              		<fo:block-container position="absolute" top="10.1cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>              			
              		</fo:block-container>
              		
              		<fo:block-container position="absolute" top="10.6cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>              			
              		</fo:block-container>
              		
              		<fo:block-container position="absolute" top="11.1cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>              			
              		</fo:block-container>
              		              		
              		<fo:block-container position="absolute" top="23cm">
						<fo:block>
							<fo:inline-container inline-progression-dimension="30%">
								<fo:block></fo:block>
							</fo:inline-container>
							<fo:inline-container inline-progression-dimension="70%">
								<fo:block text-align="center" border-bottom="0.2mm solid black" margin-left="100px" margin-right="20px">
									<xsl:value-of select="osnova:Osoba/osnova:potpis"></xsl:value-of>
								</fo:block>
								<fo:block text-align="right">
									(потпис овлашћеног лица, односно руководиоца органа)
								</fo:block>
							</fo:inline-container>
						</fo:block>
              		</fo:block-container>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    
    <xsl:template match="osnova:bold">
        <fo:inline font-weight="bold"><xsl:apply-templates select="@*|node()"></xsl:apply-templates></fo:inline>
    </xsl:template>
    
    <xsl:template match="osnova:italic">
        <fo:inline font-style="italic"><xsl:apply-templates select="@*|node()"></xsl:apply-templates></fo:inline>
    </xsl:template>
    
</xsl:stylesheet>
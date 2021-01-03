<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:odgovor="https://github.com/draganagrbic998/xml/odgovor">
	
    <xsl:template match="/odgovor:Odgovor">
        <fo:root font-size="12px" text-align="justify" font-family="Times New Roman">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="odbijanje-page">
                    <fo:region-body margin="0.8in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="odbijanje-page">
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
						РЕШЕЊЕ О ОДБИЈАЊУ ЗАХТЕВА			
					</fo:block>
					
					<fo:block text-align="center" font-weight="bold" font-size="16px">
						ЗА ПРИСТУП ИНФОРМАЦИЈАМА ОД ЈАВНОГ ЗНАЧАЈА		
					</fo:block>
					
					<fo:block>
						<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godina" select="substring-before(osnova:datum, '-')"></xsl:variable>
						Број решења: 
						<fo:inline border-bottom="0.2mm solid black">
							<xsl:value-of select="osnova:broj"></xsl:value-of>
						</fo:inline> 
						, издат дана
						<fo:inline border-bottom="0.2mm solid black">
							<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
						</fo:inline> 
						године.
					</fo:block>
					
					<fo:block linefeed-treatment="preserve">
						&#160;
						&#160;
					</fo:block>
					
					<fo:block margin-left="80px" margin-right="80px" text-align-last="justify"> 
						<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
						(<fo:inline border-bottom="1px dotted black">
							<fo:leader></fo:leader>
								<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
							<fo:leader></fo:leader>
						</fo:inline>
					</fo:block>
					
					<fo:block margin-left="100px" margin-right="100px" text-align-last="justify">
						<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
						<fo:inline border-bottom="1px dotted black">
							<fo:leader></fo:leader>
								<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
							<fo:leader></fo:leader>
						</fo:inline>)
					</fo:block>

					<fo:block text-align="center">
						Име, презиме и адреса тражиоца информација од јавног значаја
					</fo:block>
					
					<fo:block linefeed-treatment="preserve">
						&#160;
						&#160;
					</fo:block>
					
					<fo:block text-indent="40px">
						Орган власти 
						<fo:inline border-bottom="1px solid black"><xsl:value-of select="osnova:OrganVlasti/osnova:naziv"></xsl:value-of></fo:inline>
						издаје решење о одбијању захтева за информацијама од јавног значаја, које је 
						<fo:inline border-bottom="underline">
							<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
							<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
						</fo:inline>
						поднео дана 
						<fo:inline border-bottom="1px solid black">
							<xsl:variable name="danZahteva" select="substring-after(substring-after(odgovor:datumZahteva, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecZahteva" select="substring-before(substring-after(odgovor:datumZahteva, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaZahteva" select="substring-before(odgovor:datumZahteva, '-')"></xsl:variable>
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
						</fo:inline>
						Обраложење одбијања захтева је следеће:
					</fo:block>
					
					<fo:block>
			            <xsl:apply-templates select="osnova:Detalji"></xsl:apply-templates>
					</fo:block>		
								
					<fo:block-container position="absolute" top="11cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		
              		<fo:block-container position="absolute" top="11.5cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		
              		<fo:block-container position="absolute" top="12cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>              			
              		</fo:block-container>
              		
              		<fo:block-container position="absolute" top="12.5cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>              			
              		</fo:block-container>
              		
              		<fo:block-container position="absolute" top="13cm">
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
									<xsl:value-of select="osnova:Gradjanin/osnova:Osoba/osnova:potpis"></xsl:value-of>
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
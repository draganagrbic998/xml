<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:poverenik="https://github.com/draganagrbic998/xml/poverenik">    
	
    <xsl:template match="/">
        <fo:root font-family="Times New Roman" font-size="12px" linefeed-treatment="preserve" text-align="justify">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zalba-cutanje-page">
                    <fo:region-body margin="0.5in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="zalba-cutanje-page">

                <fo:flow flow-name="xsl-region-body">
                                	
					<fo:block text-align="center" font-weight="bold">
						ЖАЛБА КАДА ОРГАН ВЛАСТИ <fo:inline text-decoration="underline">НИЈЕ ПОСТУПИО/ није поступио у целости/ ПО ЗАХТЕВУ</fo:inline>
						ТРАЖИОЦА У ЗАКОНСКОМ РОКУ (ЋУТАЊЕ УПРАВЕ)
					</fo:block>
					
					<fo:block margin-top="20px">
						<fo:inline font-weight="bold">Повереникy за информације од јавног значаја и заштиту података о личности</fo:inline>
						Адреса за пошту: Београд, Булевар краља Александрa бр. 15
					</fo:block>
					
					<fo:block>
						У складу са чланом 22. Закона о слободном приступу информацијама од јавног значаја подноси:
					</fo:block>
					
					<fo:block text-align="center" margin-top="30px">
						<fo:inline font-weight="bold">Ж А Л Б У</fo:inline>
						против
					</fo:block>
					
					<fo:block border-bottom="1px dotted black" border-top="1px dotted black" text-align="center" margin-top="10px">
						<xsl:value-of select="poverenik:Zalba/osnova:OrganVlasti/osnova:naziv"></xsl:value-of>
					</fo:block>
					<fo:block text-align="center" line-height="5px">
						(навести назив органа)
					</fo:block>
					
					<fo:block text-align="center">
						због тога што орган власти:
					</fo:block>
					<fo:block text-align="center" linefeed-treatment="ignore" font-weight="bold">
						<xsl:variable name="tipCutanja" select="poverenik:Zalba/poverenik:tipCutanja"></xsl:variable>
						<xsl:if test="$tipCutanja = 'nije postupio'">
						<fo:inline text-decoration="underline">није поступио</fo:inline>
						</xsl:if>
						<xsl:if test="not($tipCutanja = 'nije postupio')">
						није поступио
						</xsl:if>
						/
						<xsl:if test="$tipCutanja = 'nije postupio u celosti'">
						<fo:inline text-decoration="underline">није поступио у целости</fo:inline>
						</xsl:if>
						<xsl:if test="not($tipCutanja = 'nije postupio u celosti')">
						није поступио у целости
						</xsl:if>
						/
						<xsl:if test="$tipCutanja = 'nije postupio u zakonskom roku'">
						<fo:inline text-decoration="underline">у законском року</fo:inline>
						</xsl:if>
						<xsl:if test="not($tipCutanja = 'nije postupio u zakonskom roku')">
						у законском року
						</xsl:if>
					</fo:block>
					<fo:block text-align="center" line-height="5px">
						(подвући због чега се изјављује жалба)
					</fo:block>
					
					<fo:block linefeed-treatment="ignore" margin-top="20px">
						<xsl:variable name="datumZahteva" select="poverenik:Zalba/poverenik:datumZahteva"></xsl:variable>
						<xsl:variable name="danZahteva" select="substring-after(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecZahteva" select="substring-before(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaZahteva" select="substring-before($datumZahteva, '-')"></xsl:variable>
						по мом захтеву за слободан приступ информацијама од јавног значаја који сам поднео том органу дана 
						<fo:inline border-bottom="1px dotted black">
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
						</fo:inline>
						године, а којим сам тражио/ла да ми се у складу са Законом о слободном приступу информацијама 
						од јавног значаја омогући увид- копија документа који садржи информације о /у вези са :
					</fo:block>
					
					<fo:block margin-top="0.1cm">
						<xsl:value-of select="poverenik:Zalba/osnova:detalji"></xsl:value-of>
					</fo:block>					
					<fo:block-container position="absolute" top="12.8cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="13.3cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="13.8cm">
              			<fo:block border-bottom="1px dotted black">
	           			</fo:block>              			
              		</fo:block-container>
              		<fo:block-container position="absolute" top="14.3cm">
              			<fo:block border-bottom="1px dotted black">
	           			</fo:block>              			
              		</fo:block-container>

					<fo:block-container position="absolute" top="14.3cm">
						<fo:block text-align="center" line-height="5px">
							(опис тражене информације)
						</fo:block>
						<fo:block text-indent="40px" margin-top="20px">
							На основу изнетог, предлажем да Повереник уважи моју жалбу и омогући ми приступ траженој/им информацији/ма.
						</fo:block>
						<fo:block text-indent="40px">
							Као доказ , уз жалбу достављам копију захтева са доказом о предаји органу власти.
						</fo:block>
						<fo:block text-indent="40px">
							<fo:inline font-weight="bold">Напомена:</fo:inline> Код жалбе због непоступању по захтеву у целости, треба приложити и добијени одговор органа власти.
						</fo:block>
						<fo:block text-align="right" margin-top="20px">
							<xsl:variable name="osoba" select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
							<xsl:variable name="adresa" select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa"></xsl:variable> 
							<fo:inline-container inline-progression-dimension="60%">
								<fo:block></fo:block>
							</fo:inline-container>
							<fo:inline-container inline-progression-dimension="40%">
								<fo:block border-bottom="1px dotted black" border-top="1px dotted black">
									<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
								</fo:block>
								<fo:block line-height="5px">
									Подносилац жалбе / Име и презиме
								</fo:block>
								<fo:block border-bottom="1px dotted black">
									&#160;
								</fo:block>
								<fo:block line-height="5px">
									потпис
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="12px">
									<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
								</fo:block>
								<fo:block line-height="5px">
									адреса
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="12px">
									<xsl:value-of select="poverenik:Zalba/osnova:kontakt"></xsl:value-of>
								</fo:block>
								<fo:block line-height="5px">
									други подаци за контакт
								</fo:block>
								<fo:block border-bottom="1px dotted black">
									&#160;
								</fo:block>
								<fo:block line-height="5px">
									Потпис
								</fo:block>
							</fo:inline-container>
						</fo:block>
						<fo:block linefeed-treatment="ignore">
							<xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="godina" select="substring(substring-before(poverenik:Zalba/osnova:datum, '-'), 3, 2)"></xsl:variable>
							У<fo:inline border-bottom="1px dotted black" padding-left="5px" padding-right="5px">Novom Sadu</fo:inline>, дана
							<fo:inline border-bottom="1px dotted black">
								<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
							</fo:inline> 20<fo:inline border-bottom="1px dotted black">
								<xsl:value-of select="$godina"></xsl:value-of>
							</fo:inline>. године
							
						</fo:block>
						
					</fo:block-container>
					
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>

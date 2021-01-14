<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:zalba="https://github.com/draganagrbic998/xml/zalba">    
	
    <xsl:template match="/zalba:Zalba">
        <fo:root font-size="12px" text-align="justify" font-family="Times New Roman">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zalba-cutanje-page">
                    <fo:region-body margin="0.5in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="zalba-cutanje-page">

                <fo:flow flow-name="xsl-region-body">
                                	
					<fo:block text-align="center" font-weight="bold">
						ЖАЛБА КАДА ОРГАН ВЛАСТИ <fo:inline text-decoration="underline">НИЈЕ ПОСТУПИО/ није поступио у целости/ ПО ЗАХТЕВУ</fo:inline>
					</fo:block>
					
					<fo:block text-align="center" font-weight="bold">
						ТРАЖИОЦА У ЗАКОНСКОМ РОКУ (ЋУТАЊЕ УПРАВЕ)
					</fo:block>
					
					<fo:block>
						&#160;
					</fo:block>
					
					<fo:block font-weight="bold">
						Повереникy за информације од јавног значаја и заштиту података о личности
					</fo:block>

					<fo:block>
						Адреса за пошту: Београд, Булевар краља Александрa бр. 15
					</fo:block>
					
					<fo:block>
						&#160;
					</fo:block>
					
					<fo:block>
						У складу са чланом 22. Закона о слободном приступу информацијама од јавног значаја подноси:
					</fo:block>
					
					<fo:block>
						&#160;
					</fo:block>
					
					<fo:block text-align="center" font-weight="bold">
						Ж А Л Б У
					</fo:block>
					
					<fo:block text-align="center">
						против
					</fo:block>
					
					<fo:block>
						&#160;
					</fo:block>
					
					<fo:block text-align="center" border-bottom="1px dotted black" border-top="1px dotted black">
						<xsl:value-of select="osnova:OrganVlasti/osnova:naziv"></xsl:value-of>
					</fo:block>
					
					<fo:block text-align="center">
						(навести назив органа)
					</fo:block>
					
					<fo:block>
						&#160;
					</fo:block>
					
					<fo:block text-align="center">
						због тога што орган власти:
					</fo:block>
					
					<fo:block text-align="center" font-weight="bold">
						<xsl:variable name="tipCutanja" select="zalba:tipCutanja"></xsl:variable>
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
					
					<fo:block text-align="center">
						(подвући због чега се изјављује жалба)
					</fo:block>
					
					<fo:block>
						&#160;
					</fo:block>
					
					<fo:block>
						по мом захтеву за слободан приступ информацијама од јавног значаја који сам поднео том органу дана 
						<fo:inline border-bottom="1px dotted black">
							<xsl:variable name="danZahteva" select="substring-after(substring-after(zalba:PodaciZahteva/osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecZahteva" select="substring-before(substring-after(zalba:PodaciZahteva/osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaZahteva" select="substring-before(zalba:PodaciZahteva/osnova:datum, '-')"></xsl:variable>
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
						</fo:inline>
						године, а којим сам тражио/ла да ми се у складу са Законом о слободном приступу информацијама 
						од јавног значаја омогући увид- копија документа који садржи информације о /у вези са :
					</fo:block>
					
					<fo:block>
						<xsl:value-of select="osnova:Detalji"></xsl:value-of>
					</fo:block>					
					<fo:block-container position="absolute" top="12.2cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="12.7cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="13.2cm">
              			<fo:block border-bottom="1px dotted black">
	           			</fo:block>              			
              		</fo:block-container>
              		<fo:block-container position="absolute" top="13.7cm">
              			<fo:block border-bottom="1px dotted black">
	           			</fo:block>              			
              		</fo:block-container>

					<fo:block-container position="absolute" top="13.7cm">
					
						<fo:block text-align="center">
							(опис тражене информације)
						</fo:block>
						
						<fo:block>
							&#160;
						</fo:block>
						
						<fo:block text-indent="40px">
							На основу изнетог, предлажем да Повереник уважи моју жалбу и омогући ми приступ траженој/им информацији/ма.
						</fo:block>
						
						<fo:block text-indent="40px">
							Као доказ , уз жалбу достављам
							
							<fo:basic-link>
	               				<xsl:attribute name="external-destination">
	               					<xsl:value-of select="concat('http://localhost:4200/pdf/zahtevi/', zalba:PodaciZahteva/osnova:broj)"></xsl:value-of>
	               				</xsl:attribute>
	               				<xsl:attribute name="color">
									blue
	               				</xsl:attribute>
								копију захтева
	               			</fo:basic-link>
														
							са доказом о предаји органу власти.
						</fo:block>
						
						<fo:block text-indent="40px">
							<fo:inline font-weight="bold">Напомена:</fo:inline> Код жалбе због непоступању по захтеву у целости, треба приложити и добијени 
							
							<xsl:if test="zalba:PodaciOdluke">
							<fo:basic-link>
	               				<xsl:attribute name="external-destination">
	               					<xsl:value-of select="concat('http://localhost:4200/pdf/odluke/', zalba:PodaciOdluke/osnova:broj)"></xsl:value-of>
	               				</xsl:attribute>
	               				<xsl:attribute name="color">
									blue
	               				</xsl:attribute>
								одговор органа власти.
	               			</fo:basic-link>
							</xsl:if>
							<xsl:if test="not(zalba:PodaciOdluke)">
								одговор органа власти.
							</xsl:if>

						</fo:block>
						
						<fo:block linefeed-treatment="preserve">
							&#160;
						</fo:block>
						
						<fo:block text-align="right">
							<fo:inline-container inline-progression-dimension="60%">
								<fo:block></fo:block>
							</fo:inline-container>
							<fo:inline-container inline-progression-dimension="40%">
								<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
								<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
								<fo:block border-bottom="1px dotted black" border-top="1px dotted black">
									<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
								</fo:block>
								<fo:block>
									Подносилац жалбе / Име и презиме
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="5px">
									&#160;
								</fo:block>
								<fo:block>
									потпис
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="5px">
									<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
								</fo:block>
								<fo:block>
									адреса
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="5px">
									<xsl:value-of select="$osoba/osnova:mejl"></xsl:value-of>
								</fo:block>
								<fo:block>
									други подаци за контакт
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="5px">
									&#160;
								</fo:block>
								<fo:block>
									Потпис
								</fo:block>
							</fo:inline-container>
						</fo:block>
						
						<fo:block linefeed-treatment="preserve">
							&#160;
						</fo:block>
						
						<fo:block>
							<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="godina" select="substring(substring-before(osnova:datum, '-'), 3, 2)"></xsl:variable>
							У<fo:inline border-bottom="1px dotted black">&#160;<xsl:value-of select="osnova:Gradjanin/osnova:Adresa/osnova:mesto"></xsl:value-of>&#160;</fo:inline>, 
							дана
							<fo:inline border-bottom="1px dotted black">
								<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
							</fo:inline> 
							20<fo:inline border-bottom="1px dotted black">
								<xsl:value-of select="$godina"></xsl:value-of>
							</fo:inline>. 
							године
							
						</fo:block>
						
					</fo:block-container>
					
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>

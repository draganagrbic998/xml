<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:poverenik="https://github.com/draganagrbic998/xml/poverenik">

	<xsl:template match="/">
	
		<html>
			
			<head>
			</head>
			
			<body style="max-width: 620px; margin: auto; border: 1px solid black; padding: 50px;">

				<p style="text-align: center; font-weight: bold; font-size: 13px;">
					ЖАЛБА КАДА ОРГАН ВЛАСТИ <u>НИЈЕ ПОСТУПИО/ није поступио у целости/ ПО ЗАХТЕВУ</u><br></br>
					ТРАЖИОЦА У ЗАКОНСКОМ  РОКУ  (ЋУТАЊЕ УПРАВЕ)
				</p>
				
				<p style="text-align: justify;">
					<b>Повереникy за информације од јавног значаја и заштиту података о личности</b><br></br>
					Адреса за пошту:  Београд, Булевар краља Александрa бр. 15
				</p>
				
				<p style="text-align: justify;">
					У складу са чланом 22. Закона о слободном приступу информацијама од јавног значаја подносим:
				</p>
				
				<h3 style="text-align: center; font-weight: normal;">
					<b>Ж А Л Б У</b><br></br>
					против
				</h3>
				
				<p style="border-bottom: 1px dotted black; border-top: 1px dotted black; margin-bottom: 0; text-align: center;">
					<xsl:value-of select="poverenik:Zalba/osnova:OrganVlasti/osnova:naziv"></xsl:value-of>
				</p>
				<p style="margin-top: 0; text-align: center;">
					(навести назив органа)
				</p>
				
				<p style="text-align: center;">
					<xsl:variable name="tipCutanja" select="poverenik:Zalba/poverenik:tipCutanja"></xsl:variable>
					због тога што орган власти:<br></br>
					<b>
					<xsl:if test="$tipCutanja = 'nije postupio'">
					<u>није поступио</u>
					</xsl:if>
					<xsl:if test="not($tipCutanja = 'nije postupio')">
					није поступио
					</xsl:if>
					/
					<xsl:if test="$tipCutanja = 'nije postupio u celosti'">
					<u>није поступио у целости</u>
					</xsl:if>
					<xsl:if test="not($tipCutanja = 'nije postupio u celosti')">
					није поступио у целости
					</xsl:if>
					/
					<xsl:if test="$tipCutanja = 'nije postupio u zakonskom roku'">
					<u>у законском року</u>
					</xsl:if>
					<xsl:if test="not($tipCutanja = 'nije postupio u zakonskom roku')">
					у законском року
					</xsl:if>
					</b><br></br>
					(подвући  због чега се изјављује жалба)
				</p>
				
				<p style="text-align: justify;">
				<xsl:variable name="datumZahteva" select="poverenik:Zalba/poverenik:datumZahteva"></xsl:variable>
				<xsl:variable name="danZahteva" select="substring-after(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
				<xsl:variable name="mesecZahteva" select="substring-before(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
				<xsl:variable name="godinaZahteva" select="substring-before($datumZahteva, '-')"></xsl:variable>
				по мом захтеву  за слободан приступ информацијама од јавног значаја који сам поднео  том органу  дана
				<span style="border-bottom: 1px dotted black;">
					<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
				</span>
				године, а којим сам тражио/ла да ми се у складу са Законом о слободном приступу информацијама 
				од јавног значаја омогући увид- копија документа који садржи информације  о /у вези са :
				</p>
				
				<p style="text-align: justify; display: inline; word-break: break-all;">
					<span style="width:100%; display: inline-block; height:13pt; border-bottom: 1px dotted black;">
						<xsl:value-of select="poverenik:Zalba/osnova:detalji"></xsl:value-of>
	               	</span>
					<span style="display: inline-block; height:13pt; width:100%; border-bottom: 1px dotted black;">
	                </span>
					<span style="display: inline-block; height:13pt;width:100%; border-bottom: 1px dotted black;">
	                </span>				
	            </p>
				
				<p style="margin-top: 0; text-align: center;">
					(навести податке о захтеву и информацији/ама)
				</p>
				
				<p style="text-indent: 40px; text-align: justify; margin-bottom: 0;">
					На основу изнетог, предлажем да Повереник уважи моју жалбу и омогући ми приступ 
					траженој/им  информацији/ма.
				</p>
				<p style="text-indent: 40px; text-align: justify; margin-bottom: 0; margin-top: 0;">
					Као доказ , уз жалбу достављам копију захтева са доказом о предаји органу власти.
				</p>
				<p style="text-indent: 40px; text-align: justify; margin-top: 0;">
					<b>Напомена:</b> Код жалбе  због непоступању по захтеву у целости, треба приложити и 
					добијени одговор органа власти.
				</p>
				
				<div style="display: flex; flex-direction: row; justify-content: space-between; text-align: right;">
					<xsl:variable name="osoba" select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
					<xsl:variable name="adresa" select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa"></xsl:variable> 
					<div></div>
					<div>
						<p style="border-bottom: 1px dotted black; border-top: 1px dotted black; margin-bottom: 0;">
							<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
						</p>
						<p style="margin-top: 0;">
							Подносилац жалбе / Име и презиме
						</p>
						<p style="border-bottom: 1px dotted black; margin-bottom: 0;">
							<br></br>
						</p>
						<p style="margin-top: 0;">
							потпис
						</p>
						<p style="border-bottom: 1px dotted black; margin-bottom: 0;">
							<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
						</p>
						<p style="margin-top: 0;">
							адреса
						</p>
						<p style="border-bottom: 1px dotted black; margin-bottom: 0;">
							<xsl:value-of select="poverenik:Zalba/osnova:kontakt"></xsl:value-of>
						</p>
						<p style="margin-top: 0;">
							други подаци за контакт
						</p>
						<p style="border-bottom: 1px dotted black; margin-bottom: 0;">
							<br></br>
						</p>
						<p style="margin-top: 0;">
							Потпис
						</p>
					</div>
				</div>
				
				<div>
					<xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
					<xsl:variable name="godina" select="substring(substring-before(poverenik:Zalba/osnova:datum, '-'), 3, 2)"></xsl:variable>
					У<span style="border-bottom: 1px dotted black; padding-left: 5px; padding-right: 5px;">Novom Sadu</span>, дана
					<span style="border-bottom: 1px dotted black;">
						<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
					</span> 20<span style="border-bottom: 1px dotted black;">
						<xsl:value-of select="$godina"></xsl:value-of>
					</span>. године
					
				</div>
				
			</body>
		
		</html>
	
	</xsl:template>

</xsl:stylesheet>

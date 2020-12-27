<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:poverenik="https://github.com/draganagrbic998/xml/poverenik">

	<xsl:template match="/">
	
		<html>
			
			<head>
			</head>
			
			<body style="max-width: 600px; margin: auto; border: 1px solid black; padding: 50px;">

				<p style="text-align: center; font-weight: bold;">
					ЖАЛБА  ПРОТИВ  ОДЛУКЕ ОРГАНА  ВЛАСТИ КОЈОМ ЈЕ<br></br>
					<u>ОДБИЈЕН ИЛИ ОДБАЧЕН ЗАХТЕВ</u> ЗА ПРИСТУП ИНФОРМАЦИЈИ
				</p>
				
				<p style="text-align: justify;">
					<b>Повереникy за информације од јавног значаја и заштиту података о личности</b><br></br>
					Адреса за пошту:  Београд, Булевар краља Александрa бр. 15
				</p>
				
				<h3 style="text-align: center;">
					Ж А Л Б У
				</h3>
				
				<div style="display: flex; flex-direction: row; margin-left: 80px; margin-right: 80px;">
					<xsl:variable name="osoba" select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
					(<span style="border-bottom: 1px dotted black; flex: 1; text-align: center;">
						<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
					</span>
				</div>
				<div style="display: flex; flex-direction: row; margin-left: 100px; margin-right: 100px;">
					<xsl:variable name="adresa" select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa"></xsl:variable> 
					<span style="border-bottom: 1px dotted black; flex: 1; text-align: center;">
						<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
					</span>)
				</div>
				<p style="margin-top: 0; text-align: center;">
					Име, презиме, односно назив, адреса и седиште жалиоца
				</p>
				
				<p style="text-align: center; margin-bottom: 0;">
					против решења-закључка 
				</p>
				<div style="display: flex; flex-direction: row; margin-left: 40px; margin-right: 40px;">
					(<span style="text-align: center; flex: 1; border-bottom: 1px dotted black;">
						<xsl:value-of select="poverenik:Zalba/osnova:OrganVlasti/osnova:naziv"></xsl:value-of>
					</span>)
				</div>
				<p style="text-align: center; margin-top: 0; margin-bottom: 0;">
					(назив органа који је донео одлуку)
				</p>
				<p style="margin-top: 0;">
					<xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
					<xsl:variable name="godina" select="substring(substring-before(poverenik:Zalba/osnova:datum, '-'), 3, 2)"></xsl:variable>
					Број <span style="border-bottom: 1px dotted black;">
						<xsl:value-of select="poverenik:Zalba/poverenik:brojOdluke"></xsl:value-of>
					</span> од <span style="border-bottom: 1px dotted black;">
						<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
					</span> године.
				</p>
				
				<p style="text-align: justify; text-indent: 40px; margin-bottom: 4px;">
					<xsl:variable name="datumZahteva" select="poverenik:Zalba/poverenik:datumZahteva"></xsl:variable>
					<xsl:variable name="danZahteva" select="substring-after(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecZahteva" select="substring-before(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaZahteva" select="substring-before($datumZahteva, '-')"></xsl:variable>
					Наведеном одлуком органа власти (решењем, закључком, обавештењем у писаној форми са елементима одлуке) , 
					супротно закону, одбијен-одбачен је мој захтев који сам поднео/ла-упутио/ла дана
					<span style="border-bottom: 1px dotted black;">
						<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
					</span>
					године и тако ми ускраћено-онемогућено остваривање уставног и законског права на 
					слободан приступ информацијама од јавног значаја. Oдлуку побијам у целости, односно у делу којим
				</p>

				<p style="text-align: justify; margin-bottom: 0; margin-top: 4px; word-break: break-all;">
					<span style="width:100%; display: inline-block; height:13pt; border-bottom: 1px dotted black;">
						
						<xsl:value-of select="poverenik:Zalba/osnova:detalji"></xsl:value-of>
	               	</span>
					<span style="display: inline-block; height:13pt; width:100%; border-bottom: 1px dotted black;">
	                </span>
					<span style="display: inline-block; height:13pt; width:100%; border-bottom: 1px dotted black;">
	                </span>			
	            </p>				
				
				<p style="text-align: justify; margin-bottom: 0; margin-top: 0;">
					јер није заснована на Закону о слободном приступу информацијама од јавног значаја.
				</p>
				<p style="text-align: justify; text-indent: 40px; margin-top: 0; margin-bottom: 0;">
					На основу изнетих разлога, предлажем да Повереник уважи моју жалбу,  поништи одлука првостепеног 
					органа и омогући ми приступ траженој/им  информацији/ма.
				</p>
				<p style="text-align: justify; text-indent: 40px; margin-top: 0;">
					Жалбу подносим благовремено, у законском року утврђеном у члану 22. ст. 1. 
					Закона о слободном приступу информацијама од јавног значаја.
				</p>

				<div style="display: flex; flex-direction: row; justify-content: space-between; align-items: center;">
					<xsl:variable name="osoba" select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
					<xsl:variable name="adresa" select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
					<div style="margin-left: 40px;">
						<xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godina" select="substring(substring-before(poverenik:Zalba/osnova:datum, '-'), 3, 2)"></xsl:variable>
						<p>
							У <span style="border-bottom: 1px dotted black; padding-left: 15px; padding-right: 15px;">
								Novom Sadu
							</span>,
						</p>
						<p style="margin-left: 20px; margin-top: 30px;">
							дана <span style="border-bottom: 1px dotted black;">
								<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
							</span> 20<span style="border-bottom: 1px dotted black;">
								<xsl:value-of select="$godina"></xsl:value-of>
							</span>. године
						</p>
						
					</div>
					<div style="text-align: right;">
					
						<p style="border-bottom: 1px dotted black; margin-bottom: 0;">
							<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
						</p>
						<p style="margin-top: 0; font-size: 13px;">
							Подносилац жалбе / Име и презиме
						</p>
						<p style="border-bottom: 1px dotted black; margin-bottom: 0;">
							<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
						</p>
						<p style="margin-top: 0; font-size: 13px;">
							адреса
						</p>
						<p style="border-bottom: 1px dotted black; margin-bottom: 0;">
							<xsl:value-of select="poverenik:Zalba/osnova:kontakt"></xsl:value-of>
						</p>
						<p style="margin-top: 0; font-size: 13px;">
							други подаци за контакт
						</p>
						<p style="border-bottom: 1px dotted black; margin-bottom: 0;">
							<br></br>
						</p>
						<p style="margin-top: 0; font-size: 13px;">
							потпис
						</p>

					</div>
				</div>
				
				<p style="font-weight: bold; text-align: justify; text-indent: 20px; margin-bottom: 0;">
					Напомена:
				</p>
				<ul style="margin-top: 0; padding-left: 30px;">
					<li>
						У жалби се мора навести одлука која се побија (решење, закључак, обавештење), назив органа 
						који је одлуку донео, као и број и датум одлуке. Довољно је да жалилац наведе у жалби 
						у ком погледу је незадовољан одлуком, с тим да жалбу не мора посебно образложити. 
						Ако жалбу изјављује на овом обрасцу, додатно образложење може  посебно приложити.					
					</li>
					<li>
						Уз жалбу обавезно приложити копију поднетог захтева и доказ о његовој предаји-упућивању 
						органу као и копију одлуке органа која се оспорава жалбом.					
					</li>
				</ul>
			
			</body>
		
		</html>
	
	</xsl:template>

</xsl:stylesheet>
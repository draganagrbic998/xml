<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
version="2.0"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:organ_vlasti="https://github.com/draganagrbic998/xml/organ_vlasti"
xmlns:poverenik="https://github.com/draganagrbic998/xml/poverenik">
    <xsl:template match="/">
    
		<html>
			<head>
			
			  <style type="text/css">
			  
				div.root{
					max-width: 600px;
					font-family: "Times New Roman", Times, serif;
				}
				
			  	

			  </style>
			</head>
			<body>
			
				<div class="root">
				
					<p style="margin-bottom: 0; text-align: center;">
						<b>
						ЖАЛБА  ПРОТИВ  ОДЛУКЕ ОРГАНА  ВЛАСТИ КОЈОМ ЈЕ
						</b>
					</p>
					<p style="margin-top: 0; text-align: center;">
						<b>
							<u>ОДБИЈЕН ИЛИ ОДБАЧЕН ЗАХТЕВ</u> ЗА ПРИСТУП ИНФОРМАЦИЈИ
						</b>
					</p>
					
					<p style="margin-bottom: 0;">
						<b>
						Поверенику за информације од јавног значаја и заштиту података о личности
						</b>
					</p>
					<p style="margin-top: 0;">
					Адреса за пошту: Београд, Булевар краља Александрa бр. 15
					</p>
					<br></br>
					<h6 style="text-align: center;">
					<b>
					Ж А Л Б А
					</b>
					</h6>

					<xsl:variable name="osoba" select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
					<xsl:variable name="adresa" select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
					<div style="margin-left: 40px; margin-right: 40px; display: flex; flex-direction: row;">
					(<p style="flex: 1; border-bottom: 1px dotted black; text-align: center; margin-bottom: 0;">
						<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
					</p>
					
					</div>
					<div style="margin-left: 50px; margin-right: 50px; display: flex; flex-direction: row;">
					
					<p style="flex: 1; border-bottom: 1px dotted black; text-align: center; margin-top: 0; margin-bottom: 0;">
						<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', concat($adresa/osnova:broj, concat(', ', $adresa/osnova:mesto))))"></xsl:value-of>
					</p>
					)
					</div>
					<p style="text-align: center; margin-top: 0;">
						Име, презиме, односно назив, адреса и седиште жалиоца
					</p>
					<p style="text-align: center; margin-bottom: 0;">
					против решења-закључка
					</p> 
					<div style="display: flex; flex-direction: row; margin-left: 20px; margin-right: 20px;">
					
					(<p style="flex: 1; text-align: center; border-bottom: 1px dotted black; margin-top: 0; margin-bottom: 0;">
						<xsl:value-of select="poverenik:Zalba/poverenik:organVlasti"></xsl:value-of>
					</p>
					)
					</div>
					<p style="text-align: center; margin-top: 0; margin-bottom: 0;">
					(назив органа који је донео одлуку)
					</p>
					<p style="margin-top: 0;">
					Број <span style="border-bottom: 1px dotted black">
					<xsl:value-of select="poverenik:Zalba/poverenik:brojOdluke"></xsl:value-of>
					</span> од 
					<span style="border-bottom: 1px dotted black">
	                <xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/poverenik:datumZahteva, '-'), '-')"></xsl:variable>
	                <xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/poverenik:datumZahteva, '-'), '-')"></xsl:variable>
	                <xsl:variable name="godina" select="substring-before(poverenik:Zalba/poverenik:datumZahteva, '-')"></xsl:variable>
					<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
					</span> године.
					</p>
					
					<p style="text-align: justify; text-indent: 40px; margin-bottom: 0;">
	                <xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/poverenik:datumOdluke, '-'), '-')"></xsl:variable>
	                <xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/poverenik:datumOdluke, '-'), '-')"></xsl:variable>
	                <xsl:variable name="godina" select="substring-before(poverenik:Zalba/poverenik:datumOdluke, '-')"></xsl:variable>
					Наведеном одлуком органа власти (решењем, закључком, обавештењем у писаној форми са елементима одлуке), 
					супротно закону, одбијен-одбачен је мој захтев који сам поднео/ла-упутио/ла дана
					<span style="border-bottom: 1px dotted black">
					<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
					</span>
					године и тако ми ускраћено-онемогућено остваривање уставног и законског права на слободан приступ 
					информацијама од јавног значаја. Oдлуку побијам у целости, односно у делу којим
					<span style="border-bottom: 1px dotted black;">
					<xsl:value-of select="poverenik:Zalba/osnova:detalji"></xsl:value-of>
					</span>
					јер није заснована на Закону о слободном приступу информацијама од јавног значаја.
					</p>
					
					<p style="margin-top: 0; margin-bottom: 0; text-indent: 40px; text-align: justify;">
					На основу изнетих разлога, предлажем да Повереник уважи моју жалбу,  поништи одлука првостепеног органа и омогући ми приступ траженој/им  информацији/ма.
					</p>
					
					<p style="margin-top: 0; text-indent: 40px; text-align: justify;">
					Жалбу подносим благовремено, у законском року утврђеном у члану 22. ст. 1. Закона о слободном приступу информацијама од јавног значаја.
					</p>
					
					<div style="display: flex; flex-direction: row; justify-content: space-between; align-items: center;">
					
						<div>
			                <xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
			                <xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
			                <xsl:variable name="godina" select="substring(substring-before(poverenik:Zalba/osnova:datum, '-'), 3, 2)"></xsl:variable>
						
							<p style="text-indent: 40px;">
							У <span style="border-bottom: 1px dotted black">Новом Саду</span>, 
							
							</p>
							<p style="text-indent: 40px;">
							дана <span style="border-bottom: 1px dotted black">
			                <xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
							</span> 20<span style="border-bottom: 1px dotted black">
							<xsl:value-of select="$godina"></xsl:value-of>
							</span>. године
							
							</p>
						
						</div>
					
						<div>
						
							<p style="border-bottom: 1px dotted black; margin-bottom: 0; text-align: right;">
							
								<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
							</p>
							<p style="margin-top: 0; text-align: right;">
								Подносилац жалбе / Име и презиме
							</p>
							
							<p style="border-bottom: 1px dotted black; margin-bottom: 0; text-align: right;">
								<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', concat($adresa/osnova:broj, concat(', ', $adresa/osnova:mesto))))"></xsl:value-of>
							</p>
							<p style="margin-top: 0; text-align: right;">
								адреса
							</p>
							
							<p style="border-bottom: 1px dotted black; margin-bottom: 0; text-align: right;">
							
								<xsl:value-of select="poverenik:Zalba/osnova:kontakt"></xsl:value-of>
							</p>
							<p style="margin-top: 0; text-align: right;">
								други подаци за контакт
							</p>
							
							<p style="border-bottom: 1px dotted black; margin-bottom: 0; text-align: right;">
								asd
							</p>
							<p style="margin-top: 0; text-align: right;">
								потпис
							</p>
						
						</div>
					
					</div>
					
					<p style="margin-bottom: 0; text-indent: 20px;">
					<b>
					
					Напомена: 
					</b>
					
					</p>
					
					<ul style="padding-left: 15px;">
						<li style="text-align: justify;">
						У жалби се мора навести одлука која се побија (решење, закључак, обавештење), 
						назив органа који је одлуку донео, као и број и датум одлуке. Довољно је да жалилац 
						наведе у жалби у ком погледу је незадовољан одлуком, с тим да жалбу не мора посебно 
						образложити. Ако жалбу изјављује на овом обрасцу, додатно образложење може  посебно приложити.
						</li>
						<li style="text-align: justify;">
						Уз жалбу обавезно приложити копију поднетог захтева и доказ о његовој 
						предаји-упућивању органу као и копију одлуке органа која се оспорава жалбом.
						</li>
					</ul>
					
				</div>

			</body>	
		</html>

    </xsl:template>
</xsl:stylesheet>
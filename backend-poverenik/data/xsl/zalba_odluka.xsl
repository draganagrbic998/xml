<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:zalba="https://github.com/draganagrbic998/xml/zalba">

	<xsl:template match="/zalba:Zalba">
	
		<html>
			
			<head>
				<style>
					.root{
						max-width: 700px; 
						margin: auto; 
						border: 1px solid black; 
						padding: 50px;
						text-align: justify;
						font-family: serif;
						background-color: white;
						box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
					}
					p{
						margin: 0;
					}
					.center{
						text-align: center;
					}
					.dotted{
						border-bottom: 1px dotted black;
					}
					.bold{
						font-weight: bold;
					}
					.indent{
						text-indent: 40px;
					}
					.line{
						display: inline-block; 
						height: 13pt; 
						width: 100%; 
						border-bottom: 1px dotted black;
					}
					.flex{
						display: flex; 
						flex-direction: row; 
						justify-content: space-between;
					}
					.details{
						word-break: break-all;
					}
					.small{
						font-size: 13px;
					}
				</style>
			</head>
			
			<body>

				<div class="root">
				
					<p class="center bold small">
						ЖАЛБА  ПРОТИВ  ОДЛУКЕ ОРГАНА  ВЛАСТИ КОЈОМ ЈЕ
					</p>
	
					<p class="center bold small">
						<u>ОДБИЈЕН ИЛИ ОДБАЧЕН ЗАХТЕВ</u> ЗА ПРИСТУП ИНФОРМАЦИЈИ
					</p>
					
					<br></br>
					
					<p class="bold">
						Повереникy за информације од јавног значаја и заштиту података о личности
					</p>
	
					<p>
						Адреса за пошту:  Београд, Булевар краља Александрa бр. 15
					</p>
					
					<br></br>
					
					<p class="center bold">
						Ж А Л Б У
					</p>
					
					<br></br>
					
					<div class="flex" style="margin-left: 80px; margin-right: 80px;">
						<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
						(<span class="center dotted" style="flex: 1;">
							<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
						</span>
					</div>
					<div class="flex" style="margin-left: 100px; margin-right: 100px;">
						<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
						<span class="center dotted" style="flex: 1;">
							<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
						</span>)
					</div>
					<p class="center">
						Име, презиме, односно назив, адреса и седиште жалиоца
					</p>
					
					<br></br>
					
					<p class="center">
						против решења-закључка 
					</p>
					<div class="flex" style="margin-left: 40px; margin-right: 40px;">
						<xsl:variable name="sedisteMesto" select="osnova:OrganVlasti/osnova:Adresa/osnova:mesto"></xsl:variable>
						<xsl:variable name="sedisteUlica" select="osnova:OrganVlasti/osnova:Adresa/osnova:ulica"></xsl:variable>
						<xsl:variable name="sedisteBroj" select="osnova:OrganVlasti/osnova:Adresa/osnova:broj"></xsl:variable>
						<xsl:variable name="sediste" select="concat($sedisteUlica, concat(' ', concat($sedisteBroj, concat(', ', $sedisteMesto))))"></xsl:variable>
						(<span class="center dotted" style="flex: 1;">
							<xsl:value-of select="concat(osnova:OrganVlasti/osnova:naziv, concat(', ', $sediste))"></xsl:value-of>
						</span>)
					</div>
					<p class="center">
						(назив органа који је донео одлуку)
					</p>
					<p>
						<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godina" select="substring-before(osnova:datum, '-')"></xsl:variable>
						Број 
						<span class="dotted">
							<xsl:value-of select="zalba:brojOdluke"></xsl:value-of>
						</span> 
						од 
						<span class="dotted">
							<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
						</span> 
						године.
					</p>
					
					<br></br>
					
					<p class="indent">
						<xsl:variable name="danZahteva" select="substring-after(substring-after(zalba:datumZahteva, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecZahteva" select="substring-before(substring-after(zalba:datumZahteva, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaZahteva" select="substring-before(zalba:datumZahteva, '-')"></xsl:variable>
						Наведеном одлуком органа власти (решењем, закључком, обавештењем у писаној форми са елементима одлуке) , 
						супротно закону, одбијен-одбачен је мој захтев који сам поднео/ла-упутио/ла дана
						<span class="dotted">
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
						</span>
						године и тако ми ускраћено-онемогућено остваривање уставног и законског права на 
						слободан приступ информацијама од јавног значаја. Oдлуку побијам у целости, односно у делу којим
					</p>
	
					<p class="details">
						<span class="line">
							<xsl:copy-of select="osnova:Detalji"></xsl:copy-of>
		               	</span>
						<span class="line">
		                </span>
						<span class="line">
		                </span>			
		            </p>				
					
					<p>
						јер није заснована на Закону о слободном приступу информацијама од јавног значаја.
					</p>
					<p class="indent">
						На основу изнетих разлога, предлажем да Повереник уважи моју жалбу,  поништи одлука првостепеног 
						органа и омогући ми приступ траженој/им  информацији/ма.
					</p>
					<p class="indent">
						Жалбу подносим благовремено, у законском року утврђеном у члану 22. ст. 1. 
						Закона о слободном приступу информацијама од јавног значаја.
					</p>
					
					<br></br><br></br>
	
					<div class="flex" style="align-items: center;">
						<div>
							<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="godina" select="substring(substring-before(osnova:datum, '-'), 3, 2)"></xsl:variable>
							<p>
								У <span class="dotted">&#160;<xsl:value-of select="osnova:OrganVlasti/osnova:Adresa/osnova:mesto"></xsl:value-of>&#160;</span>,
							</p>
							<p style="margin-top: 5px;">
								дана 
								<span class="dotted">
									<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
								</span> 
								20<span class="dotted">
									<xsl:value-of select="$godina"></xsl:value-of>
								</span>. године
							</p>
						</div>
						<div style="text-align: right;">
							<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
							<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
							<p class="dotted">
								<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
							</p>
							<p>
								Подносилац жалбе / Име и презиме
							</p>
							<p class="dotted" style="margin-top: 5px;">
								<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
							</p>
							<p>
								адреса
							</p>
							<p class="dotted" style="margin-top: 5px;">
								<xsl:value-of select="$osoba/osnova:mejl"></xsl:value-of>
							</p>
							<p>
								други подаци за контакт
							</p>
							<p class="dotted" style="margin-top: 5px;">
								<xsl:value-of select="$osoba/osnova:potpis"></xsl:value-of>
							</p>
							<p>
								потпис
							</p>
	
						</div>
					</div>
					
					<br></br><br></br>
					
					<p class="bold" style="text-indent: 20px;">
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
					
				
				</div>
			
			</body>
		
		</html>
	
	</xsl:template>

</xsl:stylesheet>
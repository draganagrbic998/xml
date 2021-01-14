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
						height: 17pt; 
						border-bottom: 1px solid black;
						word-break: break-all;
					}
					.flex{
						display: flex; 
						flex-direction: row; 
						justify-content: space-between;
					}
					.small{
						font-size: 13px;
					}
				</style>
			</head>
			
			<body>

				<div class="root">
				
					<p class="center bold small">
						ЖАЛБА КАДА ОРГАН ВЛАСТИ <u>НИЈЕ ПОСТУПИО/ није поступио у целости/ ПО ЗАХТЕВУ</u>
					</p>
					
					<p class="center bold small">
						ТРАЖИОЦА У ЗАКОНСКОМ  РОКУ  (ЋУТАЊЕ УПРАВЕ)
					</p>
					
					<br></br>
					
					<p class="bold">
						Повереникy за информације од јавног значаја и заштиту података о личности
					</p>
					
					<p>
						Адреса за пошту:  Београд, Булевар краља Александрa бр. 15
					</p>
					
					<br></br>
					
					<p>
						У складу са чланом 22. Закона о слободном приступу информацијама од јавног значаја подносим:
					</p>
					
					<br></br>
									
					<p class="center bold">
						Ж А Л Б У
					</p>
					
					<p class="center">
						против
					</p>
					
					<br></br>
					
					<p class="center dotted" style="border-top: 1px dotted black;">
						<xsl:value-of select="osnova:OrganVlasti/osnova:naziv"></xsl:value-of>
					</p>
					<p class="center">
						(навести назив органа)
					</p>
					
					<br></br>
					
					<p class="center">
						због тога што орган власти:				
					</p>
					
					<p class="center bold">
						<xsl:variable name="tipCutanja" select="zalba:tipCutanja"></xsl:variable>
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
					</p>
					
					<p class="center">
						(подвући  због чега се изјављује жалба)
					</p>
					
					<br></br>
					
					<p>
					по мом захтеву  за слободан приступ информацијама од јавног значаја који сам поднео  том органу  дана
					<span class="dotted">
						<xsl:variable name="danZahteva" select="substring-after(substring-after(zalba:PodaciZahteva/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecZahteva" select="substring-before(substring-after(zalba:PodaciZahteva/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaZahteva" select="substring-before(zalba:PodaciZahteva/osnova:datum, '-')"></xsl:variable>
						<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
					</span>
					године, а којим сам тражио/ла да ми се у складу са Законом о слободном приступу информацијама 
					од јавног значаја омогући увид- копија документа који садржи информације  о /у вези са :
					</p>
					
					<p>
						<p class="line">
				            <xsl:apply-templates select="osnova:Detalji"></xsl:apply-templates>
		               	</p>
						<p class="line">
		                </p>
						<p class="line">
		                </p>			
						<p class="line">
		                </p>				
		            </p>
					
					<p class="center">
						(навести податке о захтеву и информацији/ама)
					</p>
					
					<br></br>
					
					<p class="indent">
						На основу изнетог, предлажем да Повереник уважи моју жалбу и омогући ми приступ 
						траженој/им  информацији/ма.
					</p>
					<p class="indent">
						<xsl:variable name="zahtev_link" select="concat('http://localhost:4201/html/zahtevi/', zalba:PodaciZahteva/osnova:broj)"></xsl:variable>
						Као доказ , уз жалбу достављам <a href="{$zahtev_link}">копију захтева</a> са доказом о предаји органу власти.
					</p>
					<p class="indent">
						<xsl:variable name="odluka_link" select="concat('http://localhost:4201/html/odluke/', zalba:PodaciOdluke/osnova:broj)"></xsl:variable>
						<b>Напомена:</b> Код жалбе  због непоступању по захтеву у целости, треба приложити и добијени 
						<xsl:if test="zalba:PodaciOdluke">
							<a href="{$odluka_link}">одговор органа власти.</a>
						</xsl:if>
						<xsl:if test="not(zalba:PodaciOdluke)">
							одговор органа власти.
						</xsl:if>
					</p>
					
					<br></br><br></br>
					
					<div class="flex" style="text-align: right;">
						<div>&#160;</div>
						<div>
							<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
							<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
							<p class="dotted" style="border-top: 1px dotted black;">
								<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
							</p>
							<p>
								Подносилац жалбе / Име и презиме
							</p>
							<p class="dotted" style="margin-top: 5px;">
								&#160;
							</p>
							<p>
								потпис
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
								&#160;
							</p>
							<p>
								Потпис
							</p>
						</div>
					</div>
					
					<br></br><br></br>
					
					<div>
						<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godina" select="substring(substring-before(osnova:datum, '-'), 3, 2)"></xsl:variable>
						У <span class="dotted">&#160;<xsl:value-of select="osnova:Gradjanin/osnova:Adresa/osnova:mesto"></xsl:value-of>&#160;</span>,
						дана 
						<span class="dotted">
							<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
						</span> 
						20<span class="dotted">
							<xsl:value-of select="$godina"></xsl:value-of>
						</span>. 
						године
					</div>
					
				
				</div>
				
			</body>
		
		</html>
	
	</xsl:template>
	
    <xsl:template match="osnova:bold">
        <b><xsl:apply-templates select="@*|node()"></xsl:apply-templates></b>
    </xsl:template>
    
    <xsl:template match="osnova:italic">
        <i><xsl:apply-templates select="@*|node()"></xsl:apply-templates></i>
    </xsl:template>

</xsl:stylesheet>

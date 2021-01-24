<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:odluka="https://github.com/draganagrbic998/xml/odluka">

	<xsl:template match="/odluka:Odluka">
	
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
					.underline{
						border-bottom: 1px solid black;
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
						border-bottom: 1px dotted black;
						word-break: break-all;
					}
					.flex{
						display: flex; 
						flex-direction: row; 
						justify-content: space-between;
					}
					.big{
						font-size: 20px;
					}
				</style>
			</head>
			
			<body>
	
				<div class="root">
				
					<xsl:variable name="sedisteMesto" select="osnova:OrganVlasti/osnova:Adresa/osnova:mesto"></xsl:variable>
					<xsl:variable name="sedisteUlica" select="osnova:OrganVlasti/osnova:Adresa/osnova:ulica"></xsl:variable>
					<xsl:variable name="sedisteBroj" select="osnova:OrganVlasti/osnova:Adresa/osnova:broj"></xsl:variable>
					<xsl:variable name="sediste" select="concat($sedisteUlica, concat(' ', concat($sedisteBroj, concat(', ', $sedisteMesto))))"></xsl:variable>
								
					<div class="flex center">
						<div>
							<p class="underline">
								<xsl:value-of select="osnova:OrganVlasti/osnova:naziv"></xsl:value-of>
							</p>
							<p class="underline">
								<xsl:value-of select="$sediste"></xsl:value-of>
							</p>
							<p>
								(назив и седиште органа)
							</p>
							<div class="flex">
								<div style="text-align: left;">
									<p>
										Број предмета:
									</p>
									<p>
										Датум:
									</p>
								</div>
								<div>
									<p class="underline" style="min-width: 120px;">
										<xsl:value-of select="substring-after(@about, 'https://github.com/draganagrbic998/xml/odluka/')"></xsl:value-of>
									</p>
									<p class="underline" style="min-width: 120px;">
										<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
										<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
										<xsl:variable name="godina" select="substring-before(osnova:datum, '-')"></xsl:variable>
										<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
									</p>
								</div>
							</div>
						</div>
						<div>&#160;</div>
					</div>
					
					<br></br>
				
					<div class="flex center">
						<div>
							<p class="underline" style="border-top: 1px solid black;">
								<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
								<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
							</p>
							<p class="underline">
								<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
								<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', concat($adresa/osnova:broj, concat(', ', $adresa/osnova:mesto))))"></xsl:value-of>
							</p>
							<p>
								Име и презиме / назив / и адреса подносиоца захтева
							</p>
						</div>
						<div>&#160;</div>
					</div>
					
					<br></br><br></br>
	        
					<p class="center bold big">
						О Б А В Е Ш Т Е Њ Е
					</p>
					<p class="center bold big">
						о стављању на увид документа који садржи
					</p>
					<p class="center bold big">
						тражену информацију и о изради копије
					</p>
					
					<br></br>
					
					<p class="indent">					
						<xsl:variable name="zahtev_link" select="concat('http://localhost:4200/html/zahtevi/', substring-after(odluka:datumZahteva/@href, 'https://github.com/draganagrbic998/xml/zahtev/'))"></xsl:variable>
						На основу члана 16. ст. 1. Закона о слободном приступу информацијама од јавног значаја, 
						поступајући по вашем <a href="{$zahtev_link}">захтеву за слободан приступ информацијама</a> од
						<span class="underline">
							<xsl:variable name="danZahteva" select="substring-after(substring-after(odluka:datumZahteva, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecZahteva" select="substring-before(substring-after(odluka:datumZahteva, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaZahteva" select="substring-before(odluka:datumZahteva, '-')"></xsl:variable>
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
						</span>
						год., којим сте тражили увид у документ/е са информацијама о / у вези са:
					</p>
					
					<p>
						<p class="line">
				            <xsl:apply-templates select="osnova:Detalji"></xsl:apply-templates>
		               	</p>
						<p class="line">
		                </p>
						<p class="line">
		                </p>				
		            </p>
					
					<p class="center">
						(опис тражене информације)
					</p>
					
					<br></br>
					
					<p>
						обавештавамо вас да дана
						<span class="underline">
							<xsl:variable name="datumUvida" select="odluka:Uvid/odluka:datumUvida"></xsl:variable>
							<xsl:variable name="danUvida" select="substring-after(substring-after($datumUvida, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecUvida" select="substring-before(substring-after($datumUvida, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaUvida" select="substring-before($datumUvida, '-')"></xsl:variable>
							<xsl:value-of select="concat($danUvida, concat('.', concat($mesecUvida, concat('.', concat($godinaUvida, '.')))))"></xsl:value-of>
						</span>
						, у
						<span class="underline">
							<xsl:value-of select="odluka:Uvid/odluka:pocetak"></xsl:value-of>
						</span>
						часова, односно у времену од
						<span class="underline">
							<xsl:value-of select="odluka:Uvid/odluka:pocetak"></xsl:value-of>
						</span>
						до
						<span class="underline">
							<xsl:value-of select="odluka:Uvid/odluka:kraj"></xsl:value-of>
						</span>
						часова, у просторијама органа у
						<span class="underline">
							<xsl:value-of select="$sedisteMesto"></xsl:value-of>
						</span>
						ул.
						<span class="underline">
							<xsl:value-of select="$sedisteUlica"></xsl:value-of>
						</span>
						бр.
						<span class="underline">
							<xsl:value-of select="$sedisteBroj"></xsl:value-of>
						</span>
						, канцеларија бр.
						<span class="underline">
							<xsl:value-of select="odluka:Uvid/odluka:kancelarija"></xsl:value-of>
						</span>
						можете извршити увид у документ/е у коме је садржана тражена информација. 				
					</p>
				
					<br></br>
					
					<p class="indent">
						Том приликом, на ваш захтев, може вам се издати и копија документа са траженом информацијом.
					</p>
					
					<br></br>
					
					<p>
						Трошкови су утврђени Уредбом Владе Републике Србије („Сл. гласник РС“, бр. 8/06), и то: 
						копија стране А4 формата износи 3 динара, А3 формата 6 динара, CD 35 динара, 
						дискете 20 динара, DVD 40 динара, аудио-касета – 150 динара, видео-касета 300 динара, 
						претварање једне стране документа из физичког у електронски облик – 30 динара.
					</p>
					
					<br></br>
					
					<p class="indent">
						Износ укупних трошкова израде копије документа по вашем захтеву износи
						<span class="dotted">
							<xsl:value-of select="odluka:kopija"></xsl:value-of>
						</span>
						динара и уплаћује се на жиро-рачун Буџета Републике Србије бр. 840-742328-843-30, 
						с позивом на број 97 – ознака шифре општине/града где се налази орган власти 
						(из Правилника о условима и начину вођења рачуна – „Сл. гласник РС“, 20/07... 40/10). 
					</p>
					
					<br></br><br></br>
					
					<div class="flex">
						<div>
							<p>
								Достављено:
							</p>
							<p>
								1.&#160;&#160;&#160;Именованом 
							</p>
							<p>
								2.&#160;&#160;&#160;Архиви
							</p>
						</div>
						<div>
							<br></br>
							<p>
								(М.П.)
							</p>
							<br></br>
							<p class="underline center" style="margin-left: 100px; margin-right: 20px;">
								&#160;
							</p>
							<p style="margin-left: 30px;">
								(потпис овлашћеног лица, односно руководиоца органа)
							</p>
						</div>
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

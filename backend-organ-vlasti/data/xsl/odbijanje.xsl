<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:odgovor="https://github.com/draganagrbic998/xml/odgovor">

	<xsl:template match="/odgovor:Odgovor">
	
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
					.big{
						font-size: 20px;
					}
				</style>
			</head>
			
			<body>
	
				<div class="root">
				
					<p class="center dotted" style="margin-left: 80px; margin-right: 80px;">
						<xsl:variable name="sedisteMesto" select="osnova:OrganVlasti/osnova:Adresa/osnova:mesto"></xsl:variable>
						<xsl:variable name="sedisteUlica" select="osnova:OrganVlasti/osnova:Adresa/osnova:ulica"></xsl:variable>
						<xsl:variable name="sedisteBroj" select="osnova:OrganVlasti/osnova:Adresa/osnova:broj"></xsl:variable>
						<xsl:variable name="sediste" select="concat($sedisteUlica, concat(' ', concat($sedisteBroj, concat(', ', $sedisteMesto))))"></xsl:variable>
						<xsl:value-of select="concat(osnova:OrganVlasti/osnova:naziv, concat(', ', $sediste))"></xsl:value-of>
					</p>
					
					<p class="center">
						назив и седиште органа власти
					</p>
					
					<br></br><br></br>
					
					<p class="center bold big">
						РЕШЕЊЕ О ОДБИЈАЊУ ЗАХТЕВА
					</p>
					<p class="center bold big">
						ЗА ПРИСТУП ИНФОРМАЦИЈАМА ОД ЈАВНОГ ЗНАЧАЈА
					</p>
					
					<p>
						<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godina" select="substring-before(osnova:datum, '-')"></xsl:variable>
						Број решења: 
						<span class="underline">
							<xsl:value-of select="osnova:broj"></xsl:value-of>
						</span> 
						, издат дана
						<span class="underline">
							<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
						</span> 
						године.
					</p>
					
					<br></br><br></br>
					
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
						Име, презиме и адреса тражиоца информација од јавног значаја
					</p>
					
					<br></br><br></br>
					
					<p class="indent">
						Орган власти 
						<span class="underline"><xsl:value-of select="osnova:OrganVlasti/osnova:naziv"></xsl:value-of></span>
						издаје решење о одбијању захтева за информацијама од јавног значаја, које је 
						<span class="underline">
							<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
							<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
						</span>
						поднео дана 
						<span class="underline">
							<xsl:variable name="danZahteva" select="substring-after(substring-after(odgovor:datumZahteva, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecZahteva" select="substring-before(substring-after(odgovor:datumZahteva, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaZahteva" select="substring-before(odgovor:datumZahteva, '-')"></xsl:variable>
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
						</span>
						Обраложење одбијања захтева је следеће:
					</p>
					
					<p class="details">
						<span class="line">
							<xsl:copy-of select="osnova:Detalji"></xsl:copy-of>
		               	</span>
						<span class="line">
		                </span>
						<span class="line">
		                </span>				
						<span class="line">
		                </span>				
						<span class="line">
		                </span>				
		            </p>
		            
		            <br></br><br></br><br></br><br></br>
		            
		            <div class="flex">
		            	<div></div>
		            	<div>
							<p class="underline center" style="margin-left: 100px; margin-right: 20px;">
								<xsl:value-of select="osnova:Gradjanin/osnova:Osoba/osnova:potpis"></xsl:value-of>
							</p>
							<p>
								(потпис овлашћеног лица, односно руководиоца органа)
							</p>
		            	</div>
		            </div>
					
				</div>							
				
			</body>
		
		</html>
	
	</xsl:template>

</xsl:stylesheet>

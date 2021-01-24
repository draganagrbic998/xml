<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:izvestaj="https://github.com/draganagrbic998/xml/izvestaj">

	<xsl:template match="/izvestaj:Izvestaj">
	
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
					table {
						margin: auto;
						border: 1px solid black;
					}
					th {
						margin: auto;
						text-align: center;
						min-width: 100px;
						border: 1px solid black;
					}
					td {
						margin: auto;
						text-align: center;
						min-width: 100px;
						border: 1px solid black;
					}
					.center{
						text-align: center;
					}
					.underline{
						border-bottom: 1px solid black;
					}
					.bold{
						font-weight: bold;
					}
					.indent{
						text-indent: 40px;
					}
					.big{
						font-size: 20px;
					}
					.medium{
						font-size: 18px;
					}
					.flex{
						display: flex; 
						flex-direction: row; 
						justify-content: space-between;
					}
				</style>
			</head>
			
			<body>
	
				<div class="root">

					<xsl:variable name="datum" select="osnova:datum"></xsl:variable>
					<xsl:variable name="godina" select="izvestaj:godina"></xsl:variable>

					<xsl:variable name="brojZahteva" select="izvestaj:brojZahteva"></xsl:variable>
					<xsl:variable name="brojZahtevaObavestenje" select="izvestaj:brojZahtevaObavestenje"></xsl:variable>
					<xsl:variable name="brojZahtevaUvid" select="izvestaj:brojZahtevaUvid"></xsl:variable>
					<xsl:variable name="brojZahtevaKopija" select="izvestaj:brojZahtevaKopija"></xsl:variable>
					<xsl:variable name="brojZahtevaDostava" select="izvestaj:brojZahtevaDostava"></xsl:variable>
					<xsl:variable name="brojZahtevaPosta" select="izvestaj:brojZahtevaPosta"></xsl:variable>
					<xsl:variable name="brojZahtevaEmail" select="izvestaj:brojZahtevaEmail"></xsl:variable>
					<xsl:variable name="brojZahtevaFaks" select="izvestaj:brojZahtevaFaks"></xsl:variable>
					<xsl:variable name="brojZahtevaOstalo" select="izvestaj:brojZahtevaOstalo"></xsl:variable>

					<xsl:variable name="brojOdluka" select="izvestaj:brojOdluka"></xsl:variable>
					<xsl:variable name="brojOdlukaOdobreno" select="izvestaj:brojOdlukaOdobreno"></xsl:variable>
					<xsl:variable name="brojOdlukaOdbijeno" select="izvestaj:brojOdlukaOdbijeno"></xsl:variable>

					<xsl:variable name="brojZalbi" select="izvestaj:brojZalbi"></xsl:variable>
					<xsl:variable name="brojZalbiCutanje" select="izvestaj:brojZalbiCutanje"></xsl:variable>
					<xsl:variable name="brojZalbiDelimicnost" select="izvestaj:brojZalbiDelimicnost"></xsl:variable>
					<xsl:variable name="brojZalbiOdluka" select="izvestaj:brojZalbiOdluka"></xsl:variable>
					
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
							<div class="center flex">
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
										<xsl:value-of select="substring-after(@about, 'https://github.com/draganagrbic998/xml/izvestaj/')"></xsl:value-of>
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
	        
					<p class="center bold big">
						ПРИМЕНА ЗАКОНА О СЛОБОДНОМ ПРИСТУПУ
					</p>
					<p class="center bold big">
						ИНФОРМАЦИЈАМА ОД ЈАВНОГ ЗНАЧАЈА У <xsl:value-of select="$godina"></xsl:value-of>. ГОД.
					</p>
					
					<br></br><br></br>
					
					<p class="center bold medium">
						1. ЗАХТЕВИ
					</p>
					
					<br></br>
					
					<table>
						<tr>
							<th>Тип захтева</th>
							<th>Обавештење</th>
							<th>Увид</th>
							<th>Копија</th>
							<th>Достава</th>
							<th>Укупно</th>
						</tr>
						<tr>
							<td>Број</td>
							<td><xsl:value-of select="$brojZahtevaObavestenje"></xsl:value-of></td>
							<td><xsl:value-of select="$brojZahtevaUvid"></xsl:value-of></td>
							<td><xsl:value-of select="$brojZahtevaKopija"></xsl:value-of></td>
							<td><xsl:value-of select="$brojZahtevaDostava"></xsl:value-of></td>
							<td><xsl:value-of select="$brojZahteva"></xsl:value-of></td>
						</tr>
						<tr>
							<td>%</td>					
							<xsl:choose>
								<xsl:when test="$brojZahteva = 0">
									<td>/</td>
									<td>/</td>
									<td>/</td>
									<td>/</td>
									<td>/</td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="format-number($brojZahtevaObavestenje div $brojZahteva * 100, '####0.00')"></xsl:value-of></td>
									<td><xsl:value-of select="format-number($brojZahtevaUvid div $brojZahteva * 100, '####0.00')"></xsl:value-of></td>
									<td><xsl:value-of select="format-number($brojZahtevaKopija div $brojZahteva * 100, '####0.00')"></xsl:value-of></td>
									<td><xsl:value-of select="format-number($brojZahtevaDostava div $brojZahteva * 100, '####0.00')"></xsl:value-of></td>
									<td>100</td>
								</xsl:otherwise>
							</xsl:choose>
						</tr>
					</table>
					
					<br></br><br></br>
					
					<p class="center bold medium">
						2. ПОДАЦИ О ДОСТАВАМА
					</p>
					
					<br></br>
					
					<table>
						<tr>
							<th>Тип доставе</th>
							<th>Пошта</th>
							<th>Емаил</th>
							<th>Факс</th>
							<th>Остало</th>
						</tr>
						<tr>
							<td>Број</td>
							<td><xsl:value-of select="$brojZahtevaPosta"></xsl:value-of></td>
							<td><xsl:value-of select="$brojZahtevaEmail"></xsl:value-of></td>
							<td><xsl:value-of select="$brojZahtevaFaks"></xsl:value-of></td>
							<td><xsl:value-of select="$brojZahtevaOstalo"></xsl:value-of></td>
						</tr>
						<tr>
							<td>%</td>
							<xsl:choose>
								<xsl:when test="$brojZahtevaDostava = 0">
									<td>/</td>
									<td>/</td>
									<td>/</td>
									<td>/</td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="format-number($brojZahtevaPosta div $brojZahtevaDostava * 100, '####0.00')"></xsl:value-of></td>
									<td><xsl:value-of select="format-number($brojZahtevaEmail div $brojZahtevaDostava * 100, '####0.00')"></xsl:value-of></td>
									<td><xsl:value-of select="format-number($brojZahtevaFaks div $brojZahtevaDostava * 100, '####0.00')"></xsl:value-of></td>
									<td><xsl:value-of select="format-number($brojZahtevaOstalo div $brojZahtevaDostava * 100, '####0.00')"></xsl:value-of></td>
								</xsl:otherwise>
							</xsl:choose>
						</tr>
					</table>

					<br></br><br></br>
					
					<p class="center bold medium">
						3. ЖАЛБЕ
					</p>
					
					<br></br>
					
					<table>
						<tr>
							<th>Тип жалбе</th>
							<th>Ћутање</th>
							<th>Делимичност</th>
							<th>Одбијање</th>
							<th>Укупно</th>
						</tr>
						<tr>
							<td>Број</td>
							<td><xsl:value-of select="$brojZalbiCutanje"></xsl:value-of></td>
							<td><xsl:value-of select="$brojZalbiDelimicnost"></xsl:value-of></td>
							<td><xsl:value-of select="$brojZalbiOdluka"></xsl:value-of></td>
							<td><xsl:value-of select="$brojZalbi"></xsl:value-of></td>
						</tr>
						<tr>
							<td>%</td>					
							<xsl:choose>
								<xsl:when test="$brojZalbi = 0">
									<td>/</td>
									<td>/</td>
									<td>/</td>
									<td>/</td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="format-number($brojZalbiCutanje div $brojZalbi * 100, '####0.00')"></xsl:value-of></td>
									<td><xsl:value-of select="format-number($brojZalbiDelimicnost div $brojZalbi * 100, '####0.00')"></xsl:value-of></td>
									<td><xsl:value-of select="format-number($brojZalbiOdluka div $brojZalbi * 100, '####0.00')"></xsl:value-of></td>
									<td>100</td>
								</xsl:otherwise>
							</xsl:choose>
						</tr>
					</table>

					<br></br><br></br>

					<p class="center bold medium">
						4. РЕШЕЊА О ЗАХТЕВИМА
					</p>
					
					<br></br>
					
					<p class="indent">
						Укупан број <b>решења о захтевима</b>, којима је захтев усвојен или одбијен је 
						<b><xsl:value-of select="$brojOdluka"></xsl:value-of></b>
						<xsl:choose>
							<xsl:when test="$brojZahteva = 0">
								.
							</xsl:when>
							<xsl:otherwise>
								, што представља 
								<b><xsl:value-of select="format-number($brojOdluka div $brojZahteva * 100, '####0.00')"></xsl:value-of>%</b>
								од укупног броја поднетих захтева.
							</xsl:otherwise>
						</xsl:choose>
					</p>
					
					<p class="indent">
						<xsl:if test="$brojOdluka != 0">
							Од тога, <b>усвојено</b>, односно делимично усвојених захтева је 
							<b><xsl:value-of select="$brojOdlukaOdobreno"></xsl:value-of></b> 
							(<xsl:value-of select="format-number($brojOdlukaOdobreno div $brojOdluka * 100, '####0.00')"></xsl:value-of>%),
							док је <b>одбијених</b> захтева 
							<b><xsl:value-of select="$brojOdlukaOdbijeno"></xsl:value-of></b> 
							(<xsl:value-of select="format-number($brojOdlukaOdbijeno div $brojOdluka * 100, '####0.00')"></xsl:value-of>%).
						</xsl:if>
					</p>
					
					<br></br><br></br>
					
					<div class="flex">
						<div>
							<p>
								Достављено:
							</p>
							<p>
								1.&#160;&#160;&#160;Поверенику 
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

<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:organ_vlasti="https://github.com/draganagrbic998/xml/organ_vlasti">

	<xsl:template match="/">
	
		<html>
			
			<head>
			</head>
			
			<body style="max-width: 550px; margin: auto; border: 1px solid black; padding: 50px;">
				
				<p style="border-bottom: 1px dotted black; margin-bottom: 0; text-align: center; margin-left: 20px; margin-right: 20px;">
					<xsl:variable name="naziv" select="organ_vlasti:Zahtev/osnova:OrganVlasti/osnova:naziv"></xsl:variable>
					<xsl:variable name="sediste" select="organ_vlasti:Zahtev/osnova:OrganVlasti/osnova:sediste"></xsl:variable>
					<xsl:value-of select="concat($naziv, concat(', ', $sediste))"></xsl:value-of>
				</p>
				<p style="margin-top: 0; text-align: center;">
					назив и седиште органа коме се захтев упућује
				</p>
				<br></br>
				
				<h3 style="font-weight: bold; text-align: center;">
					З А Х Т Е В<br></br>
					за приступ информацији од јавног значаја
				</h3>
				<br></br>
				
				<p style="text-indent: 40px; text-align: justify;">
					На основу члана 15. ст. 1. Закона о слободном приступу информацијама од јавног значаја 
					(„Службени гласник РС“, бр. 120/04, 54/07, 104/09 и 36/10), од горе наведеног органа захтевам:*
				</p>
				
				<ul style="padding-left: 40px; list-style-type: none;">
					<xsl:variable name="tipZahteva" select="organ_vlasti:Zahtev/organ_vlasti:tipZahteva"></xsl:variable>
					<xsl:variable name="tipDostave" select="organ_vlasti:Zahtev/organ_vlasti:tipDostave"></xsl:variable>
					<xsl:variable name="opisDostave" select="organ_vlasti:Zahtev/organ_vlasti:opisDostave"></xsl:variable>
					<li>
						<span>
						<xsl:if test="$tipZahteva = 'obavestenje'">&#9632;</xsl:if>
						<xsl:if test="not($tipZahteva = 'obavestenje')">&#9633;</xsl:if>
						</span> обавештење да ли поседује тражену информацију;
					</li>
					<li>
						<xsl:if test="$tipZahteva = 'uvid'">&#9632;</xsl:if>
						<xsl:if test="not($tipZahteva = 'uvid')">&#9633;</xsl:if>
						увид у документ који садржи тражену информацију;
					</li>
					<li>
						<xsl:if test="$tipZahteva = 'kopija'">&#9632;</xsl:if>
						<xsl:if test="not($tipZahteva = 'kopija')">&#9633;</xsl:if>
						копију документа који садржи тражену информацију;
					</li>
					<li>
						<xsl:if test="$tipZahteva = 'dostava'">&#9632;</xsl:if>
						<xsl:if test="not($tipZahteva = 'dostava')">&#9633;</xsl:if>
						достављање копије документа који садржи тражену информацију:**
						<ul style="padding-left: 40px; list-style-type: none;">
							<li>
								<xsl:if test="$tipDostave = 'posta'">&#9632;</xsl:if>
								<xsl:if test="not($tipDostave = 'posta')">&#9633;</xsl:if>
								поштом
							</li>
							<li>
								<xsl:if test="$tipDostave = 'email'">&#9632;</xsl:if>
								<xsl:if test="not($tipDostave = 'email')">&#9633;</xsl:if>
								електронском поштом
							</li>
							<li>
								<xsl:if test="$tipDostave = 'faks'">&#9632;</xsl:if>
								<xsl:if test="not($tipDostave = 'faks')">&#9633;</xsl:if>
								факсом
							</li>
							<li style="display: flex; flex-direction: row;">
								<xsl:if test="$tipDostave = 'ostalo'">&#9632;</xsl:if>
								<xsl:if test="not($tipDostave = 'ostalo')">&#9633;</xsl:if>
								на други начин:***
								<span style="border-bottom: 1px solid black; flex: 1">
									<xsl:value-of select="$opisDostave"></xsl:value-of>
								</span>
							</li>
						</ul>
					</li>
				</ul>
				
				<p style="text-indent: 40px; text-align: justify; margin-bottom: 4px;">
					Овај захтев се односи на следеће информације:
				</p>
				
				<p style="text-align: justify; margin-top: 4px; word-break: break-all; margin-bottom: 0;">
					<span style="display: inline-block; height:13pt; border-bottom: 1px solid black;">
						<span style="border-bottom: 5px solid white; color: white;">mama</span>
						<xsl:value-of select="organ_vlasti:Zahtev/osnova:detalji"></xsl:value-of>
	               	</span>					
					<span style="display: inline-block; height:13pt; width:100%; border-bottom: 1px solid black;">
	                </span>
					<span style="display: inline-block; height:13pt;width:100%; border-bottom: 1px solid black;">
	                </span>				
	            </p>

				
				<p style="text-align: justify; font-size: 12px; margin-top: 0;">
					(навести што прецизнији опис информације која се тражи као и 
					друге податке који олакшавају проналажење тражене информације)
				</p>
				
				<br></br>
				<div style="display: flex; flex-direction: row; justify-content: space-between; align-items: center;">
					<xsl:variable name="osoba" select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
					<xsl:variable name="adresa" select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
					<div>
						<xsl:variable name="dan" select="substring-after(substring-after(organ_vlasti:Zahtev/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesec" select="substring-before(substring-after(organ_vlasti:Zahtev/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godina" select="substring(substring-before(organ_vlasti:Zahtev/osnova:datum, '-'), 3, 2)"></xsl:variable>
						<p style="display: flex; flex-direction: row; margin-right: 20px;">
							У <span style="border-bottom: 1px solid black; flex: 1; text-align: center;">
								Novom Sadu
							</span>,
						</p>
						<p>
							дана <span style="border-bottom: 1px solid black;">
								<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
							</span> 20<span style="border-bottom: 1px solid black;">
								<xsl:value-of select="$godina"></xsl:value-of>
							</span>. године
						</p>
						
					</div>
					<div style="text-align: center;">
					
						<p style="border-bottom: 1px solid black; margin-bottom: 0;">
							<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
						</p>
						<p style="margin-top: 0; font-size: 13px;">
							Тражилац информације/Име и презиме
						</p>
						<p style="border-bottom: 1px solid black; margin-bottom: 0;">
							<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
						</p>
						<p style="margin-top: 0; font-size: 13px;">
							адреса
						</p>
						<p style="border-bottom: 1px solid black; margin-bottom: 0;">
							<xsl:value-of select="organ_vlasti:Zahtev/osnova:kontakt"></xsl:value-of>
						</p>
						<p style="margin-top: 0; font-size: 13px;">
							други подаци за контакт
						</p>
						<p style="border-bottom: 1px solid black; margin-bottom: 0;">
							<br></br>
						</p>
						<p style="margin-top: 0; font-size: 13px;">
							Потпис
						</p>

					</div>
				</div>
				<br></br>
				
				<p style="border-bottom: 1px solid black; width: 60%; margin-bottom: 0;"></p>
				<p style="margin-top: 0; margin-bottom: 0; font-size: 12px;">
				* У кућици означити која законска права на приступ информацијама желите да остварите.
				</p>
				<p style="margin-top: 0; margin-bottom: 0; font-size: 12px;">
				** У кућици означити начин достављања копије докумената.
				</p>
				<p style="margin-top: 0; font-size: 12px;">
				*** Када захтевате други начин достављања обавезно уписати који начин достављања захтевате.
				</p>
								
			</body>
		
		</html>
	
	</xsl:template>

</xsl:stylesheet>

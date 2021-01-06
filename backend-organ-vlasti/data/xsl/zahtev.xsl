<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:zahtev="https://github.com/draganagrbic998/xml/zahtev">

	<xsl:template match="/zahtev:Zahtev">
	
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
					ul{
						list-style-type: none;
						padding-left: 40px;
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
						display: block; 
						height: 17pt; 
						width: 100%; 
						border-bottom: 1px solid black;
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
					.big{
						font-size: 20px;
					}
				</style>
			</head>
			
			<body>
			
				<div class="root">
					<p class="center dotted" style="margin-left: 20px; margin-right: 20px;">
						<xsl:variable name="sedisteMesto" select="osnova:OrganVlasti/osnova:Adresa/osnova:mesto"></xsl:variable>
						<xsl:variable name="sedisteUlica" select="osnova:OrganVlasti/osnova:Adresa/osnova:ulica"></xsl:variable>
						<xsl:variable name="sedisteBroj" select="osnova:OrganVlasti/osnova:Adresa/osnova:broj"></xsl:variable>
						<xsl:variable name="sediste" select="concat($sedisteUlica, concat(' ', concat($sedisteBroj, concat(', ', $sedisteMesto))))"></xsl:variable>
						<xsl:value-of select="concat(osnova:OrganVlasti/osnova:naziv, concat(', ', $sediste))"></xsl:value-of>
					</p>
					
					<p class="center">
						назив и седиште органа коме се захтев упућује
					</p>
					
					<br></br><br></br><br></br>
					
					<p class="center bold big">
						З А Х Т Е В
					</p>
					
					<p class="center bold big">
						за приступ информацији од јавног значаја
					</p>
					
					<br></br><br></br>
									
					<p class="indent">
						На основу члана 15. ст. 1. Закона о слободном приступу информацијама од јавног значаја 
						(„Службени гласник РС“, бр. 120/04, 54/07, 104/09 и 36/10), од горе наведеног органа захтевам:*
					</p>
					
					<ul>
						<xsl:variable name="tipZahteva" select="zahtev:tipZahteva"></xsl:variable>
						<li>
							<xsl:if test="$tipZahteva = 'obavestenje'">&#9632;</xsl:if>
							<xsl:if test="not($tipZahteva = 'obavestenje')">&#9633;</xsl:if>
							обавештење да ли поседује тражену информацију;
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
							<ul>
								<xsl:variable name="tipDostave" select="zahtev:tipDostave"></xsl:variable>
								<xsl:variable name="opisDostave" select="zahtev:opisDostave"></xsl:variable>
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
								<li class="flex">
									<xsl:if test="$tipDostave = 'ostalo'">&#9632;</xsl:if>
									<xsl:if test="not($tipDostave = 'ostalo')">&#9633;</xsl:if>
									на други начин:***
									<span class="underline" style="flex: 1;">
										<xsl:value-of select="$opisDostave"></xsl:value-of>
									</span>
								</li>
							</ul>
						</li>
					</ul>
					
					<p class="indent">
						Овај захтев се односи на следеће информације:
					</p>
					
				<p>
					<span class="line">
				        <xsl:copy>
				            <xsl:apply-templates select="osnova:Detalji"></xsl:apply-templates>
				        </xsl:copy>
	               	</span>
					<span class="line">
					&#160;
	                </span>
					<span class="line">
					&#160;
	                </span>				
	            </p>
	
					<p class="small">
						(навести што прецизнији опис информације која се тражи као и 
						друге податке који олакшавају проналажење тражене информације)
					</p>
					
					<br></br><br></br>
					
					<div class="flex" style="align-items: center;">
						<div>
							<p>
								У <span class="underline">&#160;<xsl:value-of select="osnova:OrganVlasti/osnova:Adresa/osnova:mesto"></xsl:value-of>&#160;</span>,
							</p>
							<p style="margin-top: 5px;">
								<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
								<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
								<xsl:variable name="godina" select="substring(substring-before(osnova:datum, '-'), 3, 2)"></xsl:variable>
								дана 
								<span class="underline">
									<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
								</span> 
								20<span class="underline">
									<xsl:value-of select="$godina"></xsl:value-of>
								</span>. 
								године
							</p>
						</div>
						
						<div class="center">
							<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
							<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
							<p class="underline">
								<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
							</p>
							<p class="small">
								Тражилац информације/Име и презиме
							</p>
							<p class="underline" style="margin-top: 5px;">
								<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
							</p>
							<p class="small">
								адреса
							</p>
							<p class="underline" style="margin-top: 5px;">
								<xsl:value-of select="$osoba/osnova:mejl"></xsl:value-of>
							</p>
							<p class="small">
								други подаци за контакт
							</p>
							<p class="underline" style="margin-top: 5px;">
								<xsl:value-of select="$osoba/osnova:potpis"></xsl:value-of>
							</p>
							<p class="small">
								Потпис
							</p>
	
						</div>
					</div>
					
					<br></br><br></br><br></br><br></br>
					
					<p class="underline" style="width: 50%;"></p>
					<p class="small">
					* У кућици означити која законска права на приступ информацијама желите да остварите.
					</p>
					<p class="small">
					** У кућици означити начин достављања копије докумената.
					</p>
					<p class="small">
					*** Када захтевате други начин достављања обавезно уписати који начин достављања захтевате.
					</p>
				
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

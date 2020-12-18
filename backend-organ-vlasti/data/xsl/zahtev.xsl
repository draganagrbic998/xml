<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
xmlns:organ_vlasti="https://github.com/draganagrbic998/xml/organ_vlasti"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova">
    <xsl:template match="/">
    
		<html>
			<head>


			  <style type="text/css">
				div.root{
					width: 520px;
					font-family: "Times New Roman", Times, serif;
				}
			  	h1, h2, h3, h4, h5, h6{
			  		text-align: center;
					font-weight: bold;
			  	}
			  	li{
			  	}
			  	p.indent{
				  	text-indent: 40px;
			  	}
				ul { list-style-type: none; }
				li::before { content: "\2610"; margin-right: 0.5em; }
				li.selected::before { content: "\2611"; margin-right: 0.5em; }
				label{
					font-size: 10px;
				}
				.underline{
					border-bottom: 1px solid black;					
				}
				p.no-margin{
					margin: 0 auto;		
					padding: 0 auto;
				}
				p.inline{
					display: inline;
				}
				p.dotted{
					text-align: center;
					border-bottom: 1px dotted black;
					margin: 0 auto;
					margin-left: 50px;
					margin-right: 50px;
				}
				p.hint{
					font-size: 11px;
				}
				.center{
					text-align: center;
				}
				div.podaci{
					display: flex;
					flex-direction: row;
					justify-content: space-between;
					align-items: center;
				}
				div.gradjanin{
					text-align: center;
				}

				.test{
					display: flex;
					flex-direction: row;
				}
				.manje{
					width: 70%;
				}
				.temp{
					flex-grow: 1;
					text-indent: 5px;
				}
				p.tempic:before{
					content: "";
				  border-bottom:1px solid black;
				  width:60%;
				  display:block;

				}
				span.mesto{
					padding-left: 10px;
				}


				.mama{

					display: inline;
					border-bottom: 1px solid black;

				  margin-left: 50px;
				  word-break:break-all;
				  
				}

			  </style>
			</head>
			<body>
				<div class="root">
				<p class="dotted">
	               <xsl:value-of select="organ_vlasti:Zahtev/organ_vlasti:OrganVlasti/organ_vlasti:naziv"></xsl:value-of>, 
	               <xsl:value-of select="organ_vlasti:Zahtev/organ_vlasti:OrganVlasti/organ_vlasti:sediste"></xsl:value-of> 
				</p>
				<p class="hint center">назив и седиште органа коме се захтев упућује</p><br></br>
				
				<h6>ЗАХТЕВ<br></br>за приступ информацијама од јавног значаја</h6>
				<br></br>
				

				
				<p class="indent no-margin" style="text-align-last: justify">
				На основу члана 15. ст. 1. Закона о слободном приступу информацијама од
				</p>
				<p class="no-margin" style="text-align-last: justify">
					јавног значаја („Службени гласник РС“, бр. 120/04, 54/07, 104/09 и 36/10), од горе
				
				</p>
				<p>
				наведеног органа захтевам:*
				</p>
				
				<ul>	
                    <xsl:if test="organ_vlasti:Zahtev/organ_vlasti:tipUvida/text() = 'posedovanje'">
						<li class="selected">обавештење да ли поседује тражену информацију;</li>
                    </xsl:if>    							
                    <xsl:if test="not(organ_vlasti:Zahtev/organ_vlasti:tipUvida/text() = 'posedovanje')">
						<li>обавештење да ли поседује тражену информацију;</li>
                    </xsl:if>    					
                    <xsl:if test="organ_vlasti:Zahtev/organ_vlasti:tipUvida/text() = 'uvid'">
						<li class="selected">увид у документ који садржи тражену информацију;</li>
                    </xsl:if>    					
                    <xsl:if test="not(organ_vlasti:Zahtev/organ_vlasti:tipUvida/text() = 'uvid')">
						<li>увид у документ који садржи тражену информацију;</li>
                    </xsl:if>    				
                    <xsl:if test="organ_vlasti:Zahtev/organ_vlasti:tipUvida/text() = 'kopija'">
						<li class="selected">копију документа који садржи тражену информацију;</li>
                    </xsl:if>    					
                    <xsl:if test="not(organ_vlasti:Zahtev/organ_vlasti:tipUvida/text() = 'kopija')">
						<li>копију документа који садржи тражену информацију;</li>
                    </xsl:if>    	
                    <xsl:if test="not(organ_vlasti:Zahtev/organ_vlasti:tipUvida/text())">
						<li class="selected">достављање копије документа који садржи тражену информацију:**</li>
                    </xsl:if>    									
                    <xsl:if test="organ_vlasti:Zahtev/organ_vlasti:tipUvida/text()">
						<li>достављање копије документа који садржи тражену информацију:**</li>
                    </xsl:if>    									
					<ul>
	                    <xsl:if test="organ_vlasti:Zahtev/organ_vlasti:tipDostave/text() = 'posta'">
							<li class="selected">поштом</li>
	                    </xsl:if>    							
	                    <xsl:if test="not(organ_vlasti:Zahtev/organ_vlasti:tipDostave/text() = 'posta')">
							<li>поштом</li>
	                    </xsl:if>    		
   	                    <xsl:if test="organ_vlasti:Zahtev/organ_vlasti:tipDostave/text() = 'email'">
							<li class="selected">електронском поштом</li>
	                    </xsl:if>    							
	                    <xsl:if test="not(organ_vlasti:Zahtev/organ_vlasti:tipDostave/text() = 'email')">
							<li>електронском поштом</li>
	                    </xsl:if>    
  	                    <xsl:if test="organ_vlasti:Zahtev/organ_vlasti:tipDostave/text() = 'faks'">
							<li class="selected">факсом</li>
	                    </xsl:if>    							
	                    <xsl:if test="not(organ_vlasti:Zahtev/organ_vlasti:tipDostave/text() = 'faks')">
							<li>факсом</li>
	                    </xsl:if>    	
  	                    <xsl:if test="organ_vlasti:Zahtev/organ_vlasti:tipDostave/text() = 'ostalo'">
							<li class="selected test">на други начин:*** 
							
							<span class="underline temp">
				               <xsl:value-of select="organ_vlasti:Zahtev/organ_vlasti:opisDostave/text()"></xsl:value-of>
							</span>
							</li>
	                    </xsl:if>    							
  	                    <xsl:if test="not(organ_vlasti:Zahtev/organ_vlasti:tipDostave/text() = 'ostalo')">
							<li>на други начин:*** 
							
								<span class="underline temp">
				               <xsl:value-of select="organ_vlasti:Zahtev/organ_vlasti:opisDostave/text()"></xsl:value-of>
							</span>
							
							</li>
	                    </xsl:if>    						
					</ul>
				</ul>
				
				<p class="indent">
					Овај захтев се односи на следеће информације:
				</p>
				
				

			<p class="mama">
                <xsl:value-of select="organ_vlasti:Zahtev/osnova:detalji/text()"></xsl:value-of>
				</p>


				<p class="hint no-margin" style="text-align-last: justify">
				(навести што прецизнији опис информације која се тражи као и друге податке који олакшавају проналажење 
				</p>
				<p class="hint">
				тражене информације)
				</p>

				<div class="podaci">
					
					<div class="ostalo">

						<p class="test manje">
							У<span class="underline temp center">Novom Sadu</span>,<br></br>
						</p>
						<p>
				дана<span class="underline">
                <xsl:variable name="dan" select="substring-after(substring-after(organ_vlasti:Zahtev/osnova:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="mesec" select="substring-before(substring-after(organ_vlasti:Zahtev/osnova:datum, '-'), '-')"></xsl:variable>
                <xsl:value-of select="concat(concat(' ', concat($dan, '. ')), concat($mesec, '. '))"></xsl:value-of>
				</span>20<span class="underline">
                <xsl:value-of select="concat(substring(organ_vlasti:Zahtev/osnova:datum, 3, 2), ' ')"></xsl:value-of>
				</span>године
						</p>
					</div>

					<div class="gradjanin">
						<p class="underline no-margin">
			               <xsl:value-of select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Osoba/osnova:ime"></xsl:value-of> 
			               <xsl:value-of select="' '"></xsl:value-of>
			               <xsl:value-of select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Osoba/osnova:prezime"></xsl:value-of>
						</p>
						<p class="hint">Тражилац информације/Име и презиме</p>
			
						<p class="underline no-margin">
			               <xsl:value-of select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Adresa/osnova:ulica"></xsl:value-of>  
			               <xsl:value-of select="' '"></xsl:value-of>
			               <xsl:value-of select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Adresa/osnova:broj"></xsl:value-of>, 
			               <xsl:value-of select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Adresa/osnova:mesto"></xsl:value-of> 
						</p>
						<p class="hint">адреса</p>
			
						<p class="underline no-margin">
			               <xsl:value-of select="organ_vlasti:Zahtev/osnova:kontakt"></xsl:value-of>  
						</p>
						<p class="hint">други подаци за контакт</p>
			
						<br></br>
						<p class="underline no-margin"></p>
						<p class="hint">Потпис</p>
					</div>
				
				</div>
				
				<br></br>
				
				
				<div>
				
				<div>
				
				</div>
				
				<br></br>
				<br></br>
				<br></br>
				<p class="hint no-margin tempic">
				* У кућици означити која законска права на приступ информацијама желите да остварите.
				</p>
				<p class="hint no-margin">
				** У кућици означити начин достављања копије докумената.
				</p>
				<p class="hint no-margin">
				*** Када захтевате други начин достављања обавезно уписати који начин достављања захтевате.
				</p>
				
				</div>
				</div>

			</body>	
		</html>

    </xsl:template>
</xsl:stylesheet>

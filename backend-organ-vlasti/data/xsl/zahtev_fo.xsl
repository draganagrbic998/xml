<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
 	xmlns:organ_vlasti="https://github.com/draganagrbic998/xml/organ_vlasti"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    
    <xsl:variable name="NewLine">
    <xsl:text>&#xa;</xsl:text>
	</xsl:variable>
	
    <xsl:template match="/">
        <fo:root font-family="Times New Roman" font-size="14px">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zahtev-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="zahtev-page">
            	<fo:static-content flow-name="xsl-footnote-separator">                      
				    <fo:block text-align-last="justify">                         
				      <fo:leader leader-length="50%" rule-thickness="0.5pt" leader-pattern="rule"/>                        
				    </fo:block>
				</fo:static-content>
                <fo:flow flow-name="xsl-region-body" linefeed-treatment="preserve">
                
                	<!-- odavde pocinje nasa prica -->
                
                    <fo:block text-align="center" border-bottom="1px dotted black" margin-left="50px" margin-right="50px">
                        <xsl:variable name="naziv" select="organ_vlasti:Zahtev/organ_vlasti:OrganVlasti/organ_vlasti:naziv"></xsl:variable>
                        <xsl:variable name="sediste" select="organ_vlasti:Zahtev/organ_vlasti:OrganVlasti/organ_vlasti:sediste"></xsl:variable>
                        <xsl:value-of select="concat($naziv, concat(', ', $sediste))"></xsl:value-of> 
                    </fo:block>
                    <fo:block text-align="center" line-height="7px" font-size="12px">
                        назив и седиште органа коме се захтев упућује
                    </fo:block>

                    <fo:block text-align="center" font-size="18px" font-weight="bold" margin-top="15px">
                        ЗАХТЕВ
						за приступ информацијама од јавног значаја
                    </fo:block>

                    <fo:block linefeed-treatment="ignore" text-align="justify" text-indent="40px" margin-top="15px">
	                    На основу члана 15. ст. 1. Закона о слободном приступу информацијама од јавног значаја 
	                    („Службени гласник РС“, бр. 120/04, 54/07, 104/09 и 36/10), 
	                    од горе наведеног органа захтевам:
	                    <fo:footnote>
				           <fo:inline>*
				           </fo:inline>
				           <fo:footnote-body font-size="10px" text-indent="0px">
				             <fo:block> 
								* У кућици означити која законска права на приступ информацијама желите да остварите.			             
							</fo:block>
				           </fo:footnote-body>
				         </fo:footnote>
                    </fo:block>
                    
                    <fo:block margin-top="5px" linefeed-treatment="ignore">
	                    <fo:list-block start-indent="40px">
	                    	<xsl:variable name="tipUvida" select="organ_vlasti:Zahtev/organ_vlasti:tipUvida"></xsl:variable>
							<fo:list-item>
								 <fo:list-item-label end-indent="label-end()">
								   <fo:block>
								   		<xsl:if test="$tipUvida = 'posedovanje'">&#9679;</xsl:if>
								   		<xsl:if test="not($tipUvida = 'posedovanje')">&#9633;</xsl:if>
								   </fo:block>
								 </fo:list-item-label>
								 <fo:list-item-body start-indent="body-start()">
								   <fo:block>обавештење да ли поседује тражену информацију;</fo:block>
								 </fo:list-item-body>
							</fo:list-item>
							
							<fo:list-item>
								 <fo:list-item-label end-indent="label-end()">
								   <fo:block>
								   		<xsl:if test="$tipUvida = 'uvid'">&#9679;</xsl:if>
								   		<xsl:if test="not($tipUvida = 'uvid')">&#9633;</xsl:if>
								   </fo:block>
								 </fo:list-item-label>
								 <fo:list-item-body start-indent="body-start()">
								   <fo:block>увид у документ који садржи тражену информацију;</fo:block>
								 </fo:list-item-body>
							</fo:list-item>
			
							<fo:list-item>
								<fo:list-item-label end-indent="label-end()">
								   <fo:block>
								   		<xsl:if test="$tipUvida = 'kopija'">&#9679;</xsl:if>
								   		<xsl:if test="not($tipUvida = 'kopija')">&#9633;</xsl:if>
								   </fo:block>
								</fo:list-item-label>
								<fo:list-item-body start-indent="body-start()">
								   <fo:block>копију документа који садржи тражену информацију;</fo:block>
								</fo:list-item-body>
							</fo:list-item>
							
							<fo:list-item>
							 <fo:list-item-label end-indent="label-end()">
							   <fo:block>
							   		<xsl:if test="not($tipUvida)">&#9679;</xsl:if>
							   		<xsl:if test="$tipUvida">&#9633;</xsl:if>
							   </fo:block>
							 </fo:list-item-label>
							 <fo:list-item-body start-indent="body-start()">
							   <fo:block>достављање копије документа који садржи тражену информацију:
							   <fo:footnote>
						           <fo:inline>**
						           </fo:inline>
						           <fo:footnote-body font-size="10px">
						             <fo:block start-indent="0"> 
						               ** У кућици означити начин достављања копије докумената.
						             </fo:block>
						           </fo:footnote-body>
						         </fo:footnote>
							   <fo:list-block start-indent="80px">
			                    	<xsl:variable name="tipDostave" select="organ_vlasti:Zahtev/organ_vlasti:tipDostave"></xsl:variable>
			                    	<xsl:variable name="opisDostave" select="organ_vlasti:Zahtev/organ_vlasti:opisDostave"></xsl:variable>
									<fo:list-item>
									 <fo:list-item-label end-indent="label-end()">
								   <fo:block>
								   		<xsl:if test="$tipDostave = 'posta'">&#9679;</xsl:if>
								   		<xsl:if test="not($tipDostave = 'posta')">&#9633;</xsl:if>
								   </fo:block>
									 </fo:list-item-label>
									 <fo:list-item-body start-indent="body-start()">
									   <fo:block>поштом</fo:block>
									 </fo:list-item-body>
									</fo:list-item>
									
									<fo:list-item>
									 <fo:list-item-label end-indent="label-end()">
								   <fo:block>
								   		<xsl:if test="$tipDostave = 'email'">&#9679;</xsl:if>
								   		<xsl:if test="not($tipDostave = 'email')">&#9633;</xsl:if>
								   </fo:block>
									 </fo:list-item-label>
									 <fo:list-item-body start-indent="body-start()">
									   <fo:block>електронском поштом</fo:block>
									 </fo:list-item-body>
									</fo:list-item>
					
									<fo:list-item>
									 <fo:list-item-label end-indent="label-end()">
								   <fo:block>
								   		<xsl:if test="$tipDostave = 'faks'">&#9679;</xsl:if>
								   		<xsl:if test="not($tipDostave = 'faks')">&#9633;</xsl:if>
								   </fo:block>
									 </fo:list-item-label>
									 <fo:list-item-body start-indent="body-start()">
									   <fo:block>факсом</fo:block>
									 </fo:list-item-body>
									</fo:list-item>
									
									<fo:list-item>
									 <fo:list-item-label end-indent="label-end()">
								   <fo:block>
								   		<xsl:if test="$tipDostave = 'ostalo'">&#9679;</xsl:if>
								   		<xsl:if test="not($tipDostave = 'ostalo')">&#9633;</xsl:if>
								   </fo:block>
									 </fo:list-item-label>
									 <fo:list-item-body start-indent="body-start()">
									   <fo:block>на други начин: 
									   <fo:footnote>
								           <fo:inline>***
								           </fo:inline>
								           <fo:footnote-body font-size="10px">
								             <fo:block start-indent="0"> 
												*** Када захтевате други начин достављања обавезно уписати који начин достављања захтевате.							             
											 </fo:block>
								           </fo:footnote-body>
								         </fo:footnote>
										<fo:inline border-bottom="1px solid black">
										<xsl:value-of select="$opisDostave"></xsl:value-of>
										</fo:inline>
						         	 </fo:block>
									 </fo:list-item-body>
									</fo:list-item>						   	
							   	</fo:list-block>
							   
							   </fo:block>
							 </fo:list-item-body>
							</fo:list-item>
							
						</fo:list-block>
                    </fo:block>
                    
                    <fo:block margin-top="5px" text-indent="40px">
                    Овај захтев се односи на следеће информације: 
                    <xsl:value-of select="organ_vlasti:Zahtev/osnova:detalji"></xsl:value-of>
                    </fo:block>
                    
                    <fo:block-container position="absolute" top="17cm" left="0cm" linefeed-treatment="ignore">
	                <xsl:variable name="dan" select="substring-after(substring-after(organ_vlasti:Zahtev/osnova:datum, '-'), '-')"></xsl:variable>
	                <xsl:variable name="mesec" select="substring-before(substring-after(organ_vlasti:Zahtev/osnova:datum, '-'), '-')"></xsl:variable>
	                <xsl:variable name="godina" select="substring(organ_vlasti:Zahtev/osnova:datum, 3, 2)"></xsl:variable>
              			<fo:block>
	              			У<fo:inline border-bottom="1px solid black"> Новом Саду </fo:inline>, 
              			</fo:block>
              			<fo:block margin-top="15px">
              				<xsl:variable name="dana" select="concat($dan, concat('. ', concat($mesec, '. ')))"></xsl:variable>
	              			дана<fo:inline border-bottom="1px solid black"> 
	              			<xsl:value-of select="concat(' ', concat($dana, ' '))"></xsl:value-of> 
	              			</fo:inline>20<fo:inline border-bottom="1px solid black">
	              			<xsl:value-of select="concat($godina, ' ')"></xsl:value-of>
	              			</fo:inline>године
              			</fo:block>
              		</fo:block-container>
              		
              		<fo:block-container text-align="center" position="absolute" top="15cm" left="10cm">
              			<xsl:variable name="osoba" select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
              			<xsl:variable name="adresa" select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
	              		<fo:block border-bottom="1px solid black">
							<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
				        </fo:block>
	                    <fo:block line-height="5px" font-size="10px">
	                    Тражилац информације/Име и презиме
	                    </fo:block>
	                    <fo:block border-bottom="1px solid black" margin-top="20px">
							<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', concat($adresa/osnova:broj, concat(', ', $adresa/osnova:mesto))))"></xsl:value-of>
				        </fo:block>
	                    <fo:block line-height="5px" font-size="10px">
	                    адреса
	                    </fo:block>
	                    <fo:block border-bottom="1px solid black" margin-top="20px">
				            <xsl:value-of select="organ_vlasti:Zahtev/osnova:kontakt"></xsl:value-of> 
				        </fo:block>
	                    <fo:block line-height="5px" font-size="10px">
						други подаци за контакт
	                    </fo:block>
	                    <fo:block border-bottom="1px solid black" margin-top="20px">
				            <xsl:value-of select="organ_vlasti:Zahtev/osnova:potpis"></xsl:value-of> 
				        </fo:block>
	                    <fo:block line-height="5px" font-size="10px">
	                    Потпис
	                    </fo:block>
              		</fo:block-container>
              		
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>

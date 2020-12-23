<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:organ_vlasti="https://github.com/draganagrbic998/xml/organ_vlasti">

    <xsl:template match="/">
        <fo:root font-family="Times New Roman" font-size="14px" linefeed-treatment="preserve">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zahtev-page">
                    <fo:region-body margin="0.5in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="zahtev-page">
            	<fo:static-content flow-name="xsl-footnote-separator">                      
				    <fo:block>                         
				      <fo:leader leader-length="50%" leader-pattern="rule"></fo:leader>                        
				    </fo:block>
				</fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                                
					<fo:block text-align="center" border-bottom="1px dotted black" margin-left="20px" margin-right="20px">
						<xsl:variable name="naziv" select="organ_vlasti:Zahtev/osnova:OrganVlasti/osnova:naziv"></xsl:variable>
						<xsl:variable name="sediste" select="organ_vlasti:Zahtev/osnova:OrganVlasti/osnova:sediste"></xsl:variable>
						<xsl:value-of select="concat($naziv, concat(', ', $sediste))"></xsl:value-of>
					</fo:block>
					<fo:block text-align="center" line-height="7px">
						назив и седиште органа коме се захтев упућује
					</fo:block>
					
					<fo:block text-align="center" font-weight="bold" margin-top="20px">
						З А Х Т Е В
						за приступ информацији од јавног значаја
					</fo:block>
					
					<fo:block text-indent="40px" text-align="justify" margin-top="40px" linefeed-treatment="ignore">
						На основу члана 15. ст. 1. Закона о слободном приступу информацијама од јавног значаја 
						(„Службени гласник РС“, бр. 120/04, 54/07, 104/09 и 36/10), од горе наведеног органа захтевам:
						 <fo:footnote>
				           <fo:inline>*</fo:inline>
				           <fo:footnote-body font-size="10px" text-indent="0">
				             <fo:block> 
								* У кућици означити која законска права на приступ информацијама желите да остварите.			             
							</fo:block>
				           </fo:footnote-body>
				         </fo:footnote>
					</fo:block>
					
					<fo:list-block start-indent="40px" margin-top="10px" linefeed-treatment="ignore">
						<xsl:variable name="tipZahteva" select="organ_vlasti:Zahtev/organ_vlasti:tipZahteva"></xsl:variable>
						<xsl:variable name="tipDostave" select="organ_vlasti:Zahtev/organ_vlasti:tipDostave"></xsl:variable>
						<xsl:variable name="opisDostave" select="organ_vlasti:Zahtev/organ_vlasti:opisDostave"></xsl:variable>
						<fo:list-item>
							<fo:list-item-label end-indent="label-end()">
								<fo:block>
									<xsl:if test="$tipZahteva = 'obavestenje'">&#9632;</xsl:if>
									<xsl:if test="not($tipZahteva = 'obavestenje')">&#9633;</xsl:if>
								</fo:block>
							</fo:list-item-label>
							<fo:list-item-body start-indent="body-start()">
								<fo:block>обавештење да ли поседује тражену информацију;</fo:block>
							</fo:list-item-body>
						</fo:list-item>	
						<fo:list-item>
							<fo:list-item-label end-indent="label-end()">
								<fo:block>
									<xsl:if test="$tipZahteva = 'uvid'">&#9632;</xsl:if>
									<xsl:if test="not($tipZahteva = 'uvid')">&#9633;</xsl:if>
								</fo:block>
							</fo:list-item-label>
							<fo:list-item-body start-indent="body-start()">
								<fo:block>увид у документ који садржи тражену информацију;</fo:block>
							</fo:list-item-body>
						</fo:list-item>	
						<fo:list-item>
							<fo:list-item-label end-indent="label-end()">
								<fo:block>
									<xsl:if test="$tipZahteva = 'kopija'">&#9632;</xsl:if>
									<xsl:if test="not($tipZahteva = 'kopija')">&#9633;</xsl:if>
								</fo:block>
							</fo:list-item-label>
							<fo:list-item-body start-indent="body-start()">
								<fo:block>копију документа који садржи тражену информацију;</fo:block>
							</fo:list-item-body>
						</fo:list-item>	
						<fo:list-item>
							<fo:list-item-label end-indent="label-end()">
								<fo:block>
									<xsl:if test="$tipZahteva = 'dostava'">&#9632;</xsl:if>
									<xsl:if test="not($tipZahteva = 'dostava')">&#9633;</xsl:if>
								</fo:block>
							</fo:list-item-label>
							<fo:list-item-body start-indent="body-start()">
								<fo:block>достављање копије документа који садржи тражену информацију:
									<fo:footnote>
							           <fo:inline>**</fo:inline>
							           <fo:footnote-body font-size="10px">
							             <fo:block start-indent="0"> 
							               ** У кућици означити начин достављања копије докумената.
							             </fo:block>
							           </fo:footnote-body>
							         </fo:footnote>
								</fo:block>
								
								<fo:list-block start-indent="80px">
									<fo:list-item>
										<fo:list-item-label end-indent="label-end()">
											<fo:block>
												<xsl:if test="$tipDostave = 'posta'">&#9632;</xsl:if>
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
												<xsl:if test="$tipDostave = 'email'">&#9632;</xsl:if>
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
												<xsl:if test="$tipDostave = 'faks'">&#9632;</xsl:if>
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
												<xsl:if test="$tipDostave = 'ostalo'">&#9632;</xsl:if>
												<xsl:if test="not($tipDostave = 'ostalo')">&#9633;</xsl:if>
											</fo:block>
										</fo:list-item-label>
										<fo:list-item-body start-indent="body-start()">
											<fo:block text-align-last="justify">на други начин:
											   <fo:footnote>
										           <fo:inline>***</fo:inline>
										           <fo:footnote-body font-size="10px">
										             <fo:block start-indent="0" text-align-last="left"> 
														*** Када захтевате други начин достављања обавезно уписати који начин достављања захтевате.							             
													 </fo:block>
										           </fo:footnote-body>
										         </fo:footnote>
										         <fo:inline border-bottom="0.2mm solid black">
													<xsl:value-of select="$opisDostave"></xsl:value-of>
												    <fo:leader></fo:leader>
										         </fo:inline>
											</fo:block>
										</fo:list-item-body>
									</fo:list-item>								
								</fo:list-block>
							</fo:list-item-body>
						</fo:list-item>	
					</fo:list-block>
					
					<fo:block text-align="justify" text-indent="40px" margin-top="20px">
						Овај захтев се односи на следеће информације:
					</fo:block>
					<fo:block text-indent="40px" margin-top="0.1cm">
						<xsl:value-of select="organ_vlasti:Zahtev/osnova:detalji"></xsl:value-of>
					</fo:block>					
					<fo:block-container position="absolute" top="13.8cm" margin-left="40px" width="100%">
              			<fo:block border-bottom="0.2mm solid black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="14.4cm" width="100%">
              			<fo:block border-bottom="0.2mm solid black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="15cm" width="100%">
              			<fo:block border-bottom="0.2mm solid black">
              			</fo:block>              			
              		</fo:block-container>
              		<fo:block-container position="absolute" top="15.1cm">
	              		<fo:block text-align="justify" font-size="10px" linefeed-treatment="ignore">
	              			(навести што прецизнији опис информације која се тражи као и друге податке 
	              			који олакшавају проналажење тражене информације)
	              		</fo:block>
              		</fo:block-container>
              		
              		
              		<fo:block-container position="absolute" top="16.7cm">
						<xsl:variable name="osoba" select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
						<xsl:variable name="adresa" select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
						<xsl:variable name="dan" select="substring-after(substring-after(organ_vlasti:Zahtev/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesec" select="substring-before(substring-after(organ_vlasti:Zahtev/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godina" select="substring(substring-before(organ_vlasti:Zahtev/osnova:datum, '-'), 3, 2)"></xsl:variable>
	              		<fo:block>
	              		
	              			<fo:inline-container inline-progression-dimension="60%" linefeed-treatment="ignore">
	              				<fo:block>
									<fo:block linefeed-treatment="preserve">
										&#160;
										&#160;
									</fo:block>
		              					
									У <fo:inline border-bottom="0.2mm solid black" padding-left="5px" padding-right="5px">
										Novom Sadu 
									</fo:inline>, 
								</fo:block>
								<fo:block margin-top="15px">
									дана <fo:inline border-bottom="0.2mm solid black">
										<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
									</fo:inline> 20<fo:inline>
										<xsl:value-of select="$godina"></xsl:value-of>
									</fo:inline>. године
								</fo:block>
	              			</fo:inline-container>
	              			<fo:inline-container inline-progression-dimension="40%" text-align="center">
         						<fo:block border-bottom="0.2mm solid black">
									<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
								</fo:block>
								<fo:block font-size="11px" line-height="5px">
									Тражилац информације/Име и презиме
								</fo:block>
								<fo:block border-bottom="0.2mm solid black" margin-top="12px">
									<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
								</fo:block>
								<fo:block font-size="11px" line-height="5px">
									адреса
								</fo:block>
								<fo:block border-bottom="0.2mm solid black" margin-top="12px">
									<xsl:value-of select="organ_vlasti:Zahtev/osnova:kontakt"></xsl:value-of>
								</fo:block>
								<fo:block font-size="11px" line-height="5px">
									други подаци за контакт
								</fo:block>
								<fo:block border-bottom="0.2mm solid black" margin-top="12px">
									&#160;
								</fo:block>
								<fo:block font-size="11px" line-height="5px">
									Потпис
								</fo:block>
	              			</fo:inline-container>
	
	              		</fo:block>
              		
              		</fo:block-container>
              		              		
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>

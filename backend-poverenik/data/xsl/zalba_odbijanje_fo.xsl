<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:organ_vlasti="https://github.com/draganagrbic998/xml/organ_vlasti"
xmlns:poverenik="https://github.com/draganagrbic998/xml/poverenik"
xmlns:fo="http://www.w3.org/1999/XSL/Format" 
version="2.0">
    
    <xsl:variable name="NewLine">
    <xsl:text>&#xa;</xsl:text>
	</xsl:variable>
	
    <xsl:template match="/">
        <fo:root font-family="Times New Roman" font-size="14px">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zalba-odbijanje-page">
                    <fo:region-body margin="0.65in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="zalba-odbijanje-page">
            	<fo:static-content flow-name="xsl-footnote-separator">                      
				    <fo:block text-align-last="justify">                         
				      <fo:leader leader-length="50%" rule-thickness="0.5pt" leader-pattern="rule"/>                        
				    </fo:block>
				</fo:static-content>
                <fo:flow flow-name="xsl-region-body" linefeed-treatment="preserve">
                
                	<!-- odavde pocinje nasa prica -->
                	
                	<fo:block text-align="center" font-weight="bold">
                	
                	ЖАЛБА  ПРОТИВ  ОДЛУКЕ ОРГАНА  ВЛАСТИ КОЈОМ ЈЕ
                	<fo:inline text-decoration="underline">ОДБИЈЕН ИЛИ ОДБАЧЕН ЗАХТЕВ</fo:inline> ЗА ПРИСТУП ИНФОРМАЦИЈИ
                	</fo:block>
                	
                	<fo:block font-weight="bold" line-height="10px">
                	
                	Поверенику за информације од јавног значаја и заштиту података о личности
                	</fo:block>
                	
                	<fo:block line-height="7px">
                	Адреса за пошту: Београд, Булевар краља Александрa бр. 15
                	
                	</fo:block>
                	
                	<fo:block text-align="center" font-weight="bold">
                	
                	Ж А Л Б А
                	</fo:block>
                	
					<xsl:variable name="osoba" select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
					<xsl:variable name="adresa" select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
					
					<fo:block text-align="center" margin-top="5px">
						<fo:inline-container vertical-align="top"  inline-progression-dimension="7%">
							<fo:block>  <fo:leader leader-pattern="space" />
							</fo:block>
						</fo:inline-container>
						<fo:inline-container vertical-align="top" inline-progression-dimension="1%">
						    <fo:block>(</fo:block>
						</fo:inline-container>

						<fo:inline-container vertical-align="top" inline-progression-dimension="85%">
						    <fo:block border-bottom="1px dotted black">
								<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
						    </fo:block>
						</fo:inline-container>
						<fo:inline-container vertical-align="top"  inline-progression-dimension="7%">
							<fo:block>  <fo:leader leader-pattern="space" />
							</fo:block>
						</fo:inline-container>
					</fo:block>
					
			
					<fo:block text-align="center" margin-top="3px">
						<fo:inline-container vertical-align="top"  inline-progression-dimension="13%">
							<fo:block>  <fo:leader leader-pattern="space" />
							</fo:block>
						</fo:inline-container>
						<fo:inline-container vertical-align="top" inline-progression-dimension="73%">
						    <fo:block border-bottom="1px dotted black">
						    	<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', concat($adresa/osnova:broj, concat(', ', $adresa/osnova:mesto))))"></xsl:value-of>						    
						    </fo:block>
						</fo:inline-container>
						
						<fo:inline-container vertical-align="top" inline-progression-dimension="1%">
						    <fo:block>)</fo:block>
						</fo:inline-container>
					
						<fo:inline-container vertical-align="top"  inline-progression-dimension="13%">
							<fo:block>  <fo:leader leader-pattern="space" />
							</fo:block>
						</fo:inline-container>
					</fo:block>							
					
					<fo:block text-align="center" line-height="7px">
					Име, презиме, односно назив, адреса и седиште жалиоца
					</fo:block>
					
                    <fo:block text-align="center" line-height="10px">
                    
                    против решења-закључка
                    </fo:block>
					
					<fo:block text-align="center" margin-top="2px">
						<fo:inline-container vertical-align="top"  inline-progression-dimension="3%">
							<fo:block>  <fo:leader leader-pattern="space" />
							</fo:block>
						</fo:inline-container>
						<fo:inline-container vertical-align="top"  inline-progression-dimension="1%">
							<fo:block>(</fo:block>
						
						</fo:inline-container>
						<fo:inline-container vertical-align="top"  inline-progression-dimension="92%">
						
							<fo:block border-bottom="1px dotted black" >
							
							<xsl:value-of select="poverenik:Zalba/poverenik:organVlasti"></xsl:value-of>
							
							</fo:block>
						
						</fo:inline-container>
						<fo:inline-container vertical-align="top"  inline-progression-dimension="1%">
							<fo:block border-bottom="1px dotted black">)</fo:block>
						
						
						</fo:inline-container>
						<fo:inline-container vertical-align="top"  inline-progression-dimension="3%">
							<fo:block>  <fo:leader leader-pattern="space" />
							</fo:block>
						</fo:inline-container>
					</fo:block>
					
					<fo:block text-align="center" line-height="7px">
					(назив органа који је донео одлуку)
					</fo:block>
					
					<fo:block linefeed-treatment="ignore" margin-top="5px">
					
					Број <fo:inline border-bottom="1px dotted black">
					<xsl:value-of select="poverenik:Zalba/poverenik:brojOdluke"></xsl:value-of>
					</fo:inline>
					од <fo:inline border-bottom="1px dotted black">
	                <xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/poverenik:datumOdluke, '-'), '-')"></xsl:variable>
	                <xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/poverenik:datumOdluke, '-'), '-')"></xsl:variable>
	                <xsl:variable name="godina" select="substring-before(poverenik:Zalba/poverenik:datumOdluke, '-')"></xsl:variable>
					<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
					
					</fo:inline> године.
					</fo:block>
					
					<fo:block text-indent="40px" text-align="justify" linefeed-treatment="ignore" margin-top="10px">
					Наведеном одлуком органа власти (решењем, закључком, обавештењем у писаној форми са 
					елементима одлуке) , супротно закону, одбијен-одбачен је мој захтев који сам 
					поднео/ла-упутио/ла дана
					<fo:inline border-bottom="1px dotted black">
					
	                <xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/poverenik:datumZahteva, '-'), '-')"></xsl:variable>
	                <xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/poverenik:datumZahteva, '-'), '-')"></xsl:variable>
	                <xsl:variable name="godina" select="substring-before(poverenik:Zalba/poverenik:datumZahteva, '-')"></xsl:variable>
					<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
					</fo:inline>
					
					године и тако ми ускраћено-онемогућено 
					остваривање уставног и законског права на слободан приступ информацијама од јавног значаја. 
					Oдлуку побијам у целости, односно у делу којим
					<fo:inline border-bottom="1px dotted black">
					<xsl:value-of select="poverenik:Zalba/osnova:detalji"></xsl:value-of>
					</fo:inline>
					јер није заснована на Закону о слободном приступу информацијама од јавног значаја.
					</fo:block>
               
               		<fo:block text-indent="40px" text-align="justify">
               		На основу изнетих разлога, предлажем да Повереник уважи моју жалбу,  поништи одлука првостепеног органа и омогући ми приступ траженој/им  информацији/ма.
               		</fo:block>
              		<fo:block text-indent="40px" text-align="justify">
               		Жалбу подносим благовремено, у законском року утврђеном у члану 22. ст. 1. Закона о слободном приступу информацијама од јавног значаја.
               		</fo:block>
               		
               		<fo:block>
					
	               		<fo:inline-container vertical-align="top" inline-progression-dimension="49.9%">
						    <fo:block margin-top="20px">
						    
							    <fo:block>
								У <fo:inline border-bottom="1px dotted black">Новом Саду</fo:inline>, 
								
								</fo:block>
								
								<fo:block line-height="5px">
								
				                <xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
				                <xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
				                <xsl:variable name="godina" select="substring-before(poverenik:Zalba/osnova:datum, '-')"></xsl:variable>
								
								дана <fo:inline border-bottom="1px dotted black">
				                <xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
								</fo:inline> 20<fo:inline border-bottom="1px dotted black">
								<xsl:value-of select="$godina"></xsl:value-of>
								</fo:inline>. године
								
								</fo:block>
							
						    </fo:block>
						</fo:inline-container>
						<fo:inline-container vertical-align="top" inline-progression-dimension="49.9%">
						    <fo:block>
						    
							    <fo:block border-bottom="1px dotted black" text-align="right">
								
									<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
								</fo:block>
								<fo:block text-align="right" line-height="7px">
									Подносилац жалбе / Име и презиме
								</fo:block>
								
								<fo:block border-bottom="1px dotted black" text-align="right" margin-top="20px">
									<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', concat($adresa/osnova:broj, concat(', ', $adresa/osnova:mesto))))"></xsl:value-of>
								</fo:block>
								<fo:block text-align="right" line-height="7px">
									адреса
								</fo:block>
								
								<fo:block border-bottom="1px dotted black" text-align="right" margin-top="20px">
								
									<xsl:value-of select="poverenik:Zalba/osnova:kontakt"></xsl:value-of>
								</fo:block>
								<fo:block text-align="right" line-height="7px">
									други подаци за контакт
								</fo:block>
								
								<fo:block border-bottom="1px dotted black" text-align="right">
									asd
								</fo:block>
								<fo:block text-align="right" line-height="7px">
									потпис
								</fo:block>
						    
						    </fo:block>
						</fo:inline-container>
					
					</fo:block>
               	
               		<fo:block font-weight="bold" line-height="7px">
               		
               			Напомена:
               		
               		</fo:block>
               		
               		<fo:block  linefeed-treatment="ignore" text-align="justify">
               		
               			<fo:list-block>
               			
               				<fo:list-item>
               				
               					<fo:list-item-label end-indent="label-end()">
               						<fo:block>&#9679;</fo:block>
               					</fo:list-item-label>
               					<fo:list-item-body start-indent="body-start()">
               						<fo:block>
               						
               							У жалби се мора навести одлука која се побија (решење, закључак, обавештење), 
               							назив органа који је одлуку донео, као и број и датум одлуке. 
               							Довољно је да жалилац наведе у жалби у ком погледу је незадовољан одлуком, 
               							с тим да жалбу не мора посебно образложити. Ако жалбу изјављује на овом обрасцу, 
               							додатно образложење може  посебно приложити.
               						</fo:block>
               					
               					</fo:list-item-body>
               				
               				</fo:list-item>
               			    <fo:list-item>
               				
               					<fo:list-item-label end-indent="label-end()">
               						<fo:block>&#9679;</fo:block>
               					</fo:list-item-label>
               					<fo:list-item-body start-indent="body-start()">
               						<fo:block>
               						
               							Уз жалбу обавезно приложити копију поднетог захтева и 
               							доказ о његовој предаји-упућивању органу као и копију одлуке 
               							органа која се оспорава жалбом.
               						</fo:block>
               					
               					</fo:list-item-body>
               				
               				</fo:list-item>
               			
               			</fo:list-block>
               		
               		</fo:block>
               	
               		
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>

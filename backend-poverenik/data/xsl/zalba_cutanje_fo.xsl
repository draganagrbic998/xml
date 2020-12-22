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
        <fo:root font-family="Times New Roman" font-size="12px">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zalba-cutanje-page">
                    <fo:region-body margin="0.5in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="zalba-cutanje-page">
            	<fo:static-content flow-name="xsl-footnote-separator">                      
				    <fo:block text-align-last="justify">                         
				      <fo:leader leader-length="50%" rule-thickness="0.5pt" leader-pattern="rule"/>                        
				    </fo:block>
				</fo:static-content>
                <fo:flow flow-name="xsl-region-body" linefeed-treatment="preserve">
                
                	<!-- odavde pocinje nasa prica -->
                	
                	<fo:block text-align="center" font-weight="bold">
                	ЖАЛБА КАДА ОРГАН ВЛАСТИ <fo:inline text-decoration="underline">НИЈЕ ПОСТУПИО/ није поступио у целости/ ПО ЗАХТЕВУ</fo:inline>
                	ТРАЖИОЦА У ЗАКОНСКОМ  РОКУ  (ЋУТАЊЕ УПРАВЕ)
                	</fo:block>
                	
                	<fo:block margin-top="15px">
                	<fo:inline font-weight="bold">Повереникy за информације од јавног значаја и заштиту података о личности</fo:inline>
                	Адреса за пошту:  Београд, Булевар краља Александрa бр. 15
                	</fo:block>
                	
                	<fo:block text-align="justify">
                	У складу са чланом 22. Закона о слободном приступу информацијама од јавног значаја подносим:
                	
                	</fo:block>
                	
                	<fo:block text-align="center">
                	<fo:inline font-weight="bold">Ж А Л Б У</fo:inline>
                	против
                	</fo:block>
                	
                	<fo:block text-align="center" border-bottom="1px dotted black" border-top="1px dotted black" margin-top="10px">
   						<xsl:value-of select="poverenik:Zalba/poverenik:organVlasti"></xsl:value-of>
                		
                	</fo:block>
               		<fo:block text-align="center" line-height="7px">
               		(навести назив органа)
               		</fo:block>
               		
               		<fo:block text-align="center">
               		због тога што орган власти:
               		</fo:block>
               		<fo:block font-weight="bold" text-align="center" linefeed-treatment="ignore">
					<xsl:variable name="tipCutanja" select="poverenik:Zalba/poverenik:tipCutanja"></xsl:variable>
					<xsl:if test="$tipCutanja = 'nije_postupio'">
						<fo:inline text-decoration="underline">није поступио</fo:inline>
					</xsl:if>
					<xsl:if test="not($tipCutanja = 'nije_postupio')">
						није поступио
					</xsl:if>
					 / 
					<xsl:if test="$tipCutanja = 'nije_postupio_u_celosti'">
						<fo:inline text-decoration="underline">није поступио у целости</fo:inline>
					</xsl:if>
					<xsl:if test="not($tipCutanja = 'nije_postupio_u_celosti')">
						није поступио у целости
					</xsl:if>
					 / 
					<xsl:if test="$tipCutanja = 'nije_postupio_u_zakonskom_roku'">
						<fo:inline text-decoration="underline">у законском року</fo:inline>
					</xsl:if>
					<xsl:if test="not($tipCutanja = 'nije_postupio_u_zakonskom_roku')">
						у законском року
					</xsl:if>
               		</fo:block>
					<fo:block text-align="center" line-height="3px">
					
               		(подвући  због чега се изјављује жалба)
					</fo:block>   
					
					<fo:block linefeed-treatment="ignore" margin-top="15px" text-align="justify">
					по мом захтеву  за слободан приступ информацијама од јавног значаја који сам поднео  
					том органу  дана
					<fo:inline border-bottom="1px dotted black">
	                <xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/poverenik:datumZahteva, '-'), '-')"></xsl:variable>
	                <xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/poverenik:datumZahteva, '-'), '-')"></xsl:variable>
	                <xsl:variable name="godina" select="substring-before(poverenik:Zalba/poverenik:datumZahteva, '-')"></xsl:variable>
	                <xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
					</fo:inline>
					
					године, а којим сам тражио/ла да ми се у складу са 
					Законом о слободном приступу информацијама од јавног значаја омогући увид- копија документа 
					који садржи информације  о /у вези са :
					</fo:block>
					
					<fo:block border-bottom="1px dotted black" linefeed-treatment="ignore">
						<xsl:value-of select="poverenik:Zalba/osnova:detalji"></xsl:value-of>
					</fo:block>
					<fo:block text-align="center" line-height="7px">
					(навести податке о захтеву и информацији/ама)
					</fo:block>
					
					<fo:block text-indent="40px" margin-top="15px">
					На основу изнетог, предлажем да Повереник уважи моју жалбу и омогући ми приступ траженој/им  информацији/ма.
					</fo:block>
					<fo:block text-indent="40px">
					Као доказ , уз жалбу достављам копију захтева са доказом о предаји органу власти.
					</fo:block>
					<fo:block text-indent="40px">
					<fo:inline font-weight="bold">Напомена:</fo:inline> Код жалбе  због непоступању по захтеву у целости, треба приложити и добијени одговор органа власти.
					</fo:block>
					
					<fo:block margin-top="20px">
						<fo:inline-container vertical-align="top"  inline-progression-dimension="60%">
							<fo:block> 
							</fo:block>
						</fo:inline-container>
						<fo:inline-container vertical-align="top" inline-progression-dimension="40%">
						    <fo:block text-align="right">
						    	<fo:block border-bottom="1px dotted black" border-top="1px dotted black">
									<xsl:variable name="osoba" select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
									<xsl:variable name="adresa" select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
									<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
				
						    	</fo:block>
						    	<fo:block line-height="7px">
						    	Подносилац жалбе / Име и презиме
						    	</fo:block>
						    	
						    	<fo:block border-bottom="1px dotted black" margin-top="10px">
								<xsl:value-of select="poverenik:Zalba/osnova:potpis"></xsl:value-of>
						    	</fo:block>
						    	<fo:block line-height="7px">
						    	потпис
						    	</fo:block>
						    	
						    	<fo:block border-bottom="1px dotted black" margin-top="10px">
								<xsl:value-of select="poverenik:Zalba/osnova:kontakt"></xsl:value-of>
						    	
						    	</fo:block>
						    	<fo:block line-height="7px">
						    	други подаци за контакт
						    	</fo:block>
						    	
						    	<fo:block border-bottom="1px dotted black" margin-top="10px">
								<xsl:value-of select="poverenik:Zalba/osnova:potpis"></xsl:value-of>
						    	</fo:block>
						    	<fo:block line-height="7px">
						    	Потпис
						    	</fo:block>
						    	
						    </fo:block>
						</fo:inline-container>


					</fo:block>
					
					<fo:block linefeed-treatment="ignore" text-indent="20px"> 
					                <xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="godina" select="substring(substring-before(poverenik:Zalba/osnova:datum, '-'), 3, 2)"></xsl:variable>
					
					У <fo:inline border-bottom="1px dotted black">Новом Саду</fo:inline>, 
					дана <fo:inline border-bottom="1px dotted black">
	                <xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
					</fo:inline> 20<fo:inline border-bottom="1px dotted black">
					<xsl:value-of select="$godina"></xsl:value-of>
					</fo:inline>. године
					
					
					</fo:block>
					
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>

<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
 	xmlns:organ_vlasti="https://github.com/draganagrbic998/xml/organ_vlasti"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    
    <xsl:variable name="NewLine">
    <xsl:text>&#xa;</xsl:text>
	</xsl:variable>
	
	<xsl:variable name="Tab">
    <xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
	</xsl:variable>
	
    <xsl:template match="/">
        <fo:root font-family="Arial">
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
                
                	<!-- odavde pocinje paragrag organa vlasti -->
                    <fo:block border-bottom="1px dotted black" text-align="center" font-size="12px" margin-left="50px" margin-right="50px">
                        <xsl:variable name="naziv" select="organ_vlasti:Zahtev/organ_vlasti:OrganVlasti/organ_vlasti:naziv"></xsl:variable>
                        <xsl:variable name="sediste" select="organ_vlasti:Zahtev/organ_vlasti:OrganVlasti/organ_vlasti:sediste"></xsl:variable>
                        <xsl:value-of select="concat($naziv, concat(', ', $sediste))"></xsl:value-of> 
                    назив и седиште органа коме се захтев упућује
                    </fo:block>
                    <!-- a ovde ses zavrsava -->
                    
                    <fo:block linefeed-treatment="preserve" text-align="center" font-family="sans-serif" font-size="14px" font-weight="bold" padding="10px">
                        Z A H T E V
                        za pristup informaciji od javnog znacaja
                    </fo:block>
                    <fo:block  text-align="justify" font-family="sans-serif" font-size="12px" padding="10px">
                    <xsl:value-of select="$Tab" />Na osnovu clana 15. st. 1. Zakona o slobodnom pristupu informacijama od javnog znacaja („Službeni glasnik RS“, br. 120/04, 54/07, 104/09 i 36/10), od gore navedenog organa zahtevam:<fo:footnote>
			           <fo:inline>*
			           </fo:inline>
			           <fo:footnote-body font-size="10pt">
			             <fo:block> 
			               * U kucici oznaciti koja zakonska prava na pristup informacijama želite da ostvarite.
			             </fo:block>
			           </fo:footnote-body>
			         </fo:footnote>
                   
                    </fo:block>
                    <fo:block  font-family="sans-serif" font-size="12px">
                    <fo:list-block start-indent="3em">
						<fo:list-item>
						 <fo:list-item-label end-indent="label-end()">
						   <fo:block>&#8226;</fo:block>
						 </fo:list-item-label>
						 <fo:list-item-body start-indent="body-start()">
						   <fo:block>obaveštenje da li poseduje traženu informaciju;</fo:block>
						 </fo:list-item-body>
						</fo:list-item>
						
						<fo:list-item>
						 <fo:list-item-label end-indent="label-end()">
						   <fo:block>&#8226;</fo:block>
						 </fo:list-item-label>
						 <fo:list-item-body start-indent="body-start()">
						   <fo:block>uvid u dokument koji sadrži traženu informaciju;</fo:block>
						 </fo:list-item-body>
						</fo:list-item>
		
						<fo:list-item>
						 <fo:list-item-label end-indent="label-end()">
						   <fo:block>&#8226;</fo:block>
						 </fo:list-item-label>
						 <fo:list-item-body start-indent="body-start()">
						   <fo:block>kopiju dokumenta koji sadrži traženu informaciju;</fo:block>
						 </fo:list-item-body>
						</fo:list-item>
						
						<fo:list-item>
						 <fo:list-item-label end-indent="label-end()">
						   <fo:block>&#8226;</fo:block>
						 </fo:list-item-label>
						 <fo:list-item-body start-indent="body-start()">
						   <fo:block>dostavljanje kopije dokumenta koji sadrži traženu informaciju:<fo:footnote>
					           <fo:inline>**
					           </fo:inline>
					           <fo:footnote-body font-size="10pt" >
					             <fo:block start-indent="0"> 
					               ** U kucici oznaciti nacin dostavljanja kopije dokumenata.
					             </fo:block>
					           </fo:footnote-body>
					         </fo:footnote>
						   <fo:list-block>
								<fo:list-item>
								 <fo:list-item-label end-indent="label-end()">
								   <fo:block>&#8226;</fo:block>
								 </fo:list-item-label>
								 <fo:list-item-body start-indent="body-start()">
								   <fo:block>poštom</fo:block>
								 </fo:list-item-body>
								</fo:list-item>
								
								<fo:list-item>
								 <fo:list-item-label end-indent="label-end()">
								   <fo:block>&#8226;</fo:block>
								 </fo:list-item-label>
								 <fo:list-item-body start-indent="body-start()">
								   <fo:block>elektronskom poštom</fo:block>
								 </fo:list-item-body>
								</fo:list-item>
				
								<fo:list-item>
								 <fo:list-item-label end-indent="label-end()">
								   <fo:block>&#8226;</fo:block>
								 </fo:list-item-label>
								 <fo:list-item-body start-indent="body-start()">
								   <fo:block>faksom</fo:block>
								 </fo:list-item-body>
								</fo:list-item>
								
								<fo:list-item>
								 <fo:list-item-label end-indent="label-end()">
								   <fo:block>&#8226;</fo:block>
								 </fo:list-item-label>
								 <fo:list-item-body start-indent="body-start()">
								   <fo:block>na drugi nacin: <xsl:value-of select="organ_vlasti:Zahtev/organ_vlasti:opisDostave" /> <fo:footnote>
							           <fo:inline>***
							           </fo:inline>
							           <fo:footnote-body font-size="10pt">
							             <fo:block start-indent="0"> 
							               *** Kada zahtevate drugi nacin dostavljanja obavezno upisati koji nacin dostavljanja zahtevate.
							             </fo:block>
							           </fo:footnote-body>
							         </fo:footnote>
					         	 </fo:block>
								 </fo:list-item-body>
								</fo:list-item>						   	
						   	</fo:list-block>
						   
						   </fo:block>
						 </fo:list-item-body>
						</fo:list-item>
						
					</fo:list-block>
                    </fo:block>
                    <fo:block text-align="justify" font-family="sans-serif" font-size="12px" padding="10px">
                       <xsl:value-of select="$Tab" />Ovaj zahtev se odnosi na sledece informacije: <xsl:value-of select="organ_vlasti:Zahtev/osnova:detalji" />
                    </fo:block>
                    
                  
                    <fo:block-container height="4cm" width="12cm" top="15cm" left="0cm" position="absolute">
              			<fo:block text-align="start" font-family="sans-serif"  font-size="12px">
              			U <xsl:value-of select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Adresa/osnova:mesto" />,</fo:block>
              			<fo:block text-align="start" font-family="sans-serif"  font-size="12px">
              			dana <xsl:value-of select="organ_vlasti:Zahtev/osnova:datum" /> godine</fo:block>
              		</fo:block-container>
              		<fo:block-container height="1cm" width="6cm" top="14cm" left="11.2cm" position="absolute">
	              		<fo:block text-align="right" border-bottom="solid" border-bottom-width="0.2mm">
				            <xsl:value-of select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Osoba/osnova:ime" /> <xsl:value-of select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Osoba/osnova:prezime" />
				        </fo:block>
	                    <fo:block text-align="right" font-family="sans-serif" font-size="10px">
	                    Tražilac informacije/Ime i prezime
	                    </fo:block>
	                    <fo:block text-align="right" border-bottom="solid" border-bottom-width="0.2mm">
				            <xsl:value-of select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Adresa/osnova:ulica" /> <xsl:value-of select="organ_vlasti:Zahtev/osnova:Gradjanin/osnova:broj" />
				        </fo:block>
	                    <fo:block text-align="right" font-family="sans-serif" font-size="10px">
	                    adresa
	                    </fo:block>
	                    <fo:block text-align="right" border-bottom="solid" border-bottom-width="0.2mm">
				            <xsl:value-of select="organ_vlasti:Zahtev/osnova:kontakt" /> 
				        </fo:block>
	                    <fo:block text-align="right" font-family="sans-serif" font-size="10px">
	                    drugi podaci za kontakt
	                    </fo:block>
	                    <fo:block text-align="right" border-bottom="solid" border-bottom-width="0.2mm">
				            <xsl:value-of select="organ_vlasti:Zahtev/osnova:potpis" /> 
				        </fo:block>
	                    <fo:block text-align="right" font-family="sans-serif" font-size="10px">
	                    potpis
	                    </fo:block>
              		</fo:block-container>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>

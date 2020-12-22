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
                <fo:simple-page-master master-name="obavestenje-page">
                    <fo:region-body margin="0.5in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="obavestenje-page">
            	<fo:static-content flow-name="xsl-footnote-separator">                      
				    <fo:block text-align-last="justify">                         
				      <fo:leader leader-length="50%" rule-thickness="0.5pt" leader-pattern="rule"/>                        
				    </fo:block>
				</fo:static-content>
                <fo:flow flow-name="xsl-region-body" linefeed-treatment="preserve">
                
                	<!-- odavde pocinje nasa prica -->
                	<!-- tri linije -->
                	
                	<fo:block-container height="4cm" width="18cm" top="11.0cm" left="0cm" position="absolute">
              			<fo:block border-bottom="solid" border-bottom-width="0.2mm">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container height="4cm" width="18cm" top="11.6cm" left="0cm" position="absolute">
              			<fo:block border-bottom="solid" border-bottom-width="0.2mm">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container height="4cm" width="18cm" top="12.2cm" left="0cm" position="absolute">
              			<fo:block border-bottom="solid" border-bottom-width="0.2mm">
              			</fo:block>              			
              		</fo:block-container>
              		
              		
                	<fo:block>
                	
                		<fo:block text-align="center">
                		
                			<fo:inline-container inline-progression-dimension="45%">
                			
                				<fo:block border-bottom="1px solid black">
                				
									<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:OrganVlasti/osnova:naziv"></xsl:value-of>,
                					
                				</fo:block>
                				
                				<fo:block border-bottom="1px solid black">
                				
	                				<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:OrganVlasti/osnova:sediste"></xsl:value-of>
                					
                				</fo:block>
                				
                				
                				<fo:block line-height="5px">
                					(назив и седиште органа)
                				
                				</fo:block>
                				
                				<fo:block>
                				
          				         <fo:inline-container inline-progression-dimension="50%">
                				
                					<fo:block>
                					
                						<fo:block text-align="left">Број предмета: </fo:block>
                						<fo:block text-align="left">Датум: </fo:block>
                						
                					
                					</fo:block>
                				
                				</fo:inline-container>
                				
                				<fo:inline-container inline-progression-dimension="50%">
                				
                					<fo:block>
                					
                						<fo:block border-bottom="1px solid black">
								<xsl:value-of select="organ_vlasti:Obavestenje/osnova:broj"></xsl:value-of>
                						
                						</fo:block>
                						<fo:block border-bottom="1px solid black">
								<xsl:value-of select="organ_vlasti:Obavestenje/osnova:datum"></xsl:value-of>
                						
                						</fo:block>
                					
                					</fo:block>
                				</fo:inline-container>
                				
                				
                				</fo:block>
								                			
                			</fo:inline-container>
                			
                		
       		                <fo:inline-container inline-progression-dimension="60%">
                				<fo:block></fo:block>
                			
                				
                			
                			</fo:inline-container>
                		
                		</fo:block>
                		
                		<fo:block margin-top="20px" text-align="center">
                		
                			<fo:inline-container inline-progression-dimension="60%">
                			
                				<fo:block border-bottom="1px solid black">
                				
								<xsl:variable name="osoba" select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
								<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
                				
                				</fo:block>
                				
                				<fo:block border-bottom="1px solid black">
                				
								<xsl:variable name="adresa" select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
								<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', concat($adresa/osnova:broj, concat(', ', $adresa/osnova:mesto))))"></xsl:value-of>
                				
                				</fo:block>
                				
                				<fo:block text-align="center" line-height="3px">
                				
                				Име и презиме / назив / и адреса подносиоца захтева
                				
                				</fo:block>
                				
                			
                			</fo:inline-container>
                		
                			<fo:inline-container inline-progression-dimension="40%">
                			
                				<fo:block></fo:block>
                			</fo:inline-container>
                		
                		</fo:block>
                		
                		<fo:block font-weight="bold" text-align="center">
                		
                		О Б А В Е Ш Т Е Њ Е
                		о стављању на увид документа који садржи
                		тражену информацију и о изради копије
                		</fo:block>
                		
                		
                		<fo:block margin-top="20px" linefeed-treatment="ignore" text-indent="40px" text-align="justify">
                <xsl:variable name="dan" select="substring-after(substring-after(organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="mesec" select="substring-before(substring-after(organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="godina" select="substring-before(organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:datum, '-')"></xsl:variable>
                		На основу члана 16. ст. 1. Закона о слободном приступу информацијама од 
                		јавног значаја, поступајући по вашем захтеву за слободан приступ 
                		информацијама од
                		<fo:inline border-bottom="1px solid black">
       						<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
                		
                		</fo:inline>
                		год., којим сте тражили увид у документ/е са 
                		информацијама о / у вези са: 
                		
                		</fo:block>
                		
                		<fo:block>
                		
						<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:detalji"></xsl:value-of>
                		
                		</fo:block>
                		
                		<fo:block text-align="center" line-height="5px">
                		<xsl:value-of select="$NewLine"></xsl:value-of>
                		<xsl:value-of select="$NewLine"></xsl:value-of>
                		<xsl:value-of select="$NewLine"></xsl:value-of>
                		<xsl:value-of select="$NewLine"></xsl:value-of>
                		<xsl:value-of select="$NewLine"></xsl:value-of>
                		<xsl:value-of select="$NewLine"></xsl:value-of>
                		<xsl:value-of select="$NewLine"></xsl:value-of>
                		<xsl:value-of select="$NewLine"></xsl:value-of>
                		<xsl:value-of select="$NewLine"></xsl:value-of>
                		<xsl:value-of select="$NewLine"></xsl:value-of>
                		(опис тражене информације)
                		</fo:block>
                		
                		<fo:block linefeed-treatment="ignore" margin-top="10px" text-align="justify">
                		
                		                <xsl:variable name="dan" select="substring-after(substring-after(organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="mesec" select="substring-before(substring-after(organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="godina" select="substring-before(organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:datum, '-')"></xsl:variable>
                		
                		обавештавамо вас да дана
                		<fo:inline border-bottom="1px solid black">
                		<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
                		
                		</fo:inline>
                		, у
           		        <fo:inline border-bottom="1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:pocetak"></xsl:value-of>
                		
                		</fo:inline>
                		
                		часова, 
                		односно у времену од
						<fo:inline border-bottom="1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:pocetak"></xsl:value-of>
                		
                		</fo:inline>                		
                		до
                		<fo:inline border-bottom="1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:kraj"></xsl:value-of>
                		
                		</fo:inline>                		
                		
                		часова, у просторијама органа у 
                		
                		<fo:inline border-bottom="1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/osnova:Adresa/osnova:mesto"></xsl:value-of>
                		
                		</fo:inline>                		
                		ул.
                		
                		<fo:inline border-bottom="1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/osnova:Adresa/osnova:ulica"></xsl:value-of>
                		
                		</fo:inline>                		
                		
                		бр.
                		<fo:inline border-bottom="1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/osnova:Adresa/osnova:broj"></xsl:value-of>
                		
                		</fo:inline>                		
                		
                		, канцеларија бр. 
                		
                		<fo:inline border-bottom="1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:kancelarija"></xsl:value-of>
                		
                		</fo:inline>                		
                		
                		
                		можете извршити увид у документ/е у коме је садржана тражена информација.
                		
                		</fo:block>
                		
                		<fo:block text-indent="40px" text-align="justify" margin-top="10px">
                		Том приликом, на ваш захтев, може вам се издати и копија документа са траженом информацијом.
                		</fo:block>
                		
                		<fo:block linefeed-treatment="ignore" text-align="justify" margin-top="10px">
                		
                		Трошкови су утврђени Уредбом Владе Републике Србије („Сл. гласник РС“, бр. 8/06), 
                		и то: копија стране А4 формата износи 3 динара, А3 формата 6 динара, CD 35 динара, 
                		дискете 20 динара, DVD 40 динара, аудио-касета – 150 динара, видео-касета 300 динара, 
                		претварање једне стране документа из физичког у електронски облик – 30 динара.
                		
                		</fo:block>
                		
                		<fo:block linefeed-treatment="ignore" text-align="justify" text-indent="40px" margin-top="5px">
                		
                		Износ укупних трошкова израде копије документа по вашем захтеву износи 
                		<fo:inline border-bottom="1px dotted black">
                		<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:kopija"></xsl:value-of>
                		
                		</fo:inline>
                		динара и уплаћује се на жиро-рачун Буџета Републике Србије 
                		бр. 840-742328-843-30, с позивом на број 97 – ознака шифре општине/града 
                		где се налази орган власти (из Правилника о условима и начину вођења рачуна – 
                		„Сл. гласник РС“, 20/07... 40/10).
                		
                		</fo:block>
                		
                		
                		<fo:block margin-top="12px">
                		
                			<fo:inline-container inline-progression-dimension="30%">
                			
                				<fo:block>
                				
                					<fo:block>Достављено:</fo:block>
                					<fo:block>1.	Именованом </fo:block>
                					<fo:block>2.	Архиви</fo:block>
                					
                				</fo:block>
                			
                			</fo:inline-container>
                		
                			<fo:inline-container inline-progression-dimension="70%" margin-left="40px">
                			
                				<fo:block>
                				
                					<fo:block>
                					
                					<xsl:value-of select="$NewLine"></xsl:value-of>
                					                					<xsl:value-of select="$NewLine"></xsl:value-of>
                					
                					</fo:block>
                					
                					<fo:block margin-bottom="30px" line-height="5px">(М.П.)</fo:block>
                					
                					<fo:block padding-bottom="5px" line-height="5px" margin-left="80px" margin-right="20px" border-bottom="1px solid black" text-align="center">
							<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:potpis"></xsl:value-of>
                						
                					</fo:block>
                					
                					<fo:block line-height="5px" text-align="right">
                					(потпис овлашћеног лица, односно руководиоца органа)
                					</fo:block>
                				
                				</fo:block>
                			</fo:inline-container>
                		
                		</fo:block>
                	
                	</fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
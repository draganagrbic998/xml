<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:odluka="https://github.com/draganagrbic998/xml/odluka">
	
    <xsl:template match="/odluka:Odluka">
        <fo:root font-size="12px" text-align="justify" font-family="Times New Roman">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="obavestenje-page">
                    <fo:region-body margin="0.5in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="obavestenje-page">
                <fo:flow flow-name="xsl-region-body">
                
					<xsl:variable name="sedisteMesto" select="osnova:OrganVlasti/osnova:Adresa/osnova:mesto"></xsl:variable>
					<xsl:variable name="sedisteUlica" select="osnova:OrganVlasti/osnova:Adresa/osnova:ulica"></xsl:variable>
					<xsl:variable name="sedisteBroj" select="osnova:OrganVlasti/osnova:Adresa/osnova:broj"></xsl:variable>
					<xsl:variable name="sediste" select="concat($sedisteUlica, concat(' ', concat($sedisteBroj, concat(', ', $sedisteMesto))))"></xsl:variable>
					
					<fo:block text-align="center">
               			
               			<fo:inline-container inline-progression-dimension="40%">
               			
               				<fo:block border-bottom="0.2mm solid black">
								<xsl:value-of select="osnova:OrganVlasti/osnova:naziv"></xsl:value-of>
               				</fo:block>
               				<fo:block border-bottom="0.2mm solid black">
								<xsl:value-of select="$sediste"></xsl:value-of>
               				</fo:block>
               				<fo:block>
               					(назив и седиште органа)
               				</fo:block>
               				
               				<fo:block>
         				         <fo:inline-container inline-progression-dimension="40%">
               						<fo:block text-align="left">Број предмета: </fo:block>
               						<fo:block text-align="left">Датум: </fo:block>
	               				</fo:inline-container>
	               				<fo:inline-container inline-progression-dimension="60%">
               						<fo:block border-bottom="0.2mm solid black">
										<xsl:value-of select="osnova:broj"></xsl:value-of>
               						</fo:block>
               						<fo:block border-bottom="0.2mm solid black">
										<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
										<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
										<xsl:variable name="godina" select="substring-before(osnova:datum, '-')"></xsl:variable>
										<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
               						</fo:block>
	               				</fo:inline-container>               				
               				</fo:block>
							                			
               			</fo:inline-container>
                			
   		                <fo:inline-container inline-progression-dimension="60%">
               				<fo:block></fo:block>  			
               			</fo:inline-container>
                		
               		</fo:block>				
               		
               		<fo:block>
               			&#160;
               		</fo:block>
               		
               		<fo:block text-align="center">
   		                <fo:inline-container inline-progression-dimension="60%">
               				<fo:block border-bottom="0.2mm solid black" border-top="0.2mm solid black">
								<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
								<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
               				</fo:block>  		
               				<fo:block border-bottom="0.2mm solid black">
								<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
								<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', concat($adresa/osnova:broj, concat(', ', $adresa/osnova:mesto))))"></xsl:value-of>
               				</fo:block>  	
               				<fo:block>
								Име и презиме / назив / и адреса подносиоца захтева
               				</fo:block>		
               			</fo:inline-container>
   		                <fo:inline-container inline-progression-dimension="40%">
               				<fo:block></fo:block>  			
               			</fo:inline-container>
               		
               		</fo:block>	
               		
               		<fo:block linefeed-treatment="preserve">
               			&#160;
               		</fo:block>
               		
               		<fo:block text-align="center" font-weight="bold" font-size="16px">
               			О Б А В Е Ш Т Е Њ Е
               		</fo:block>

					<fo:block text-align="center" font-weight="bold" font-size="16px">
               			о стављању на увид документа који садржи
					</fo:block>
					
					<fo:block text-align="center" font-weight="bold" font-size="16px">
               			тражену информацију и о изради копије
					</fo:block>
               		
               		<fo:block>
               			&#160;
               		</fo:block>
               		
               		<fo:block text-indent="40px">
	               		На основу члана 16. ст. 1. Закона о слободном приступу информацијама од јавног значаја, 
	               		поступајући по вашем захтеву за слободан приступ информацијама од
	               		<fo:inline border-bottom="0.2mm solid black">
							<xsl:variable name="danZahteva" select="substring-after(substring-after(odluka:datumZahteva, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecZahteva" select="substring-before(substring-after(odluka:datumZahteva, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaZahteva" select="substring-before(odluka:datumZahteva, '-')"></xsl:variable>
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
	               		</fo:inline>
	               		год., којим сте тражили увид у документ/е са информацијама о / у вези са:
               		</fo:block>

					<fo:block>
			            <xsl:apply-templates select="osnova:Detalji"></xsl:apply-templates>
					</fo:block>					
					<fo:block-container position="absolute" top="10.6cm">
              			<fo:block border-bottom="0.2mm solid black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="11.1cm">
              			<fo:block border-bottom="0.2mm solid black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="11.6cm">
              			<fo:block border-bottom="0.2mm solid black">
              			</fo:block>              			
              		</fo:block-container>
					
					<fo:block-container position="absolute" top="11.6cm">
						<fo:block text-align="center">
							(опис тражене информације)
						</fo:block>
						
	               		<fo:block>
	               			&#160;
	               		</fo:block>
						
						<fo:block>
							обавештавамо вас да дана
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:variable name="datumUvida" select="odluka:Uvid/odluka:datumUvida"></xsl:variable>
								<xsl:variable name="danUvida" select="substring-after(substring-after($datumUvida, '-'), '-')"></xsl:variable>
								<xsl:variable name="mesecUvida" select="substring-before(substring-after($datumUvida, '-'), '-')"></xsl:variable>
								<xsl:variable name="godinaUvida" select="substring-before($datumUvida, '-')"></xsl:variable>
								<xsl:value-of select="concat($danUvida, concat('.', concat($mesecUvida, concat('.', concat($godinaUvida, '.')))))"></xsl:value-of>
							</fo:inline>
							, у
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="odluka:Uvid/odluka:pocetak"></xsl:value-of>
							</fo:inline>
							часова, односно у времену од
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="odluka:Uvid/odluka:pocetak"></xsl:value-of>
							</fo:inline>
							до
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="odluka:Uvid/odluka:kraj"></xsl:value-of>
							</fo:inline>
							часова, у просторијама органа у
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="$sedisteMesto"></xsl:value-of>
							</fo:inline>
							ул.
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="$sedisteUlica"></xsl:value-of>
							</fo:inline>
							бр.
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="$sedisteBroj"></xsl:value-of>
							</fo:inline>
							, канцеларија бр.
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="odluka:Uvid/odluka:kancelarija"></xsl:value-of>
							</fo:inline>
							можете извршити увид у документ/е у коме је садржана тражена информација.					
							
						</fo:block>
						
	               		<fo:block>
	               			&#160;
	               		</fo:block>
	               		
						<fo:block text-indent="40px">
							Том приликом, на ваш захтев, може вам се издати и копија документа са траженом информацијом.
						</fo:block>
						
	               		<fo:block>
	               			&#160;
	               		</fo:block>
	               		
						<fo:block>
							Трошкови су утврђени Уредбом Владе Републике Србије („Сл. гласник РС“, бр. 8/06), и то: 
							копија стране А4 формата износи 3 динара, А3 формата 6 динара, CD 35 динара, дискете 20 динара, 
							DVD 40 динара, аудио-касета – 150 динара, видео-касета 300 динара, 
							претварање једне стране документа из физичког у електронски облик – 30 динара.
						</fo:block>
						
	               		<fo:block>
	               			&#160;
	               		</fo:block>
	               		
						<fo:block text-indent="40px">
							Износ укупних трошкова израде копије документа по вашем захтеву износи
							<fo:inline border-bottom="0.2mm dotted black">
								<xsl:value-of select="odluka:kopija"></xsl:value-of>
							</fo:inline>
							динара и уплаћује се на жиро-рачун Буџета Републике Србије бр. 840-742328-843-30, 
							с позивом на број 97 – ознака шифре општине/града где се налази орган власти 
							(из Правилника о условима и начину вођења рачуна – „Сл. гласник РС“, 20/07... 40/10).
						</fo:block>
						
	               		<fo:block linefeed-treatment="preserve">
	               			&#160;
	               		</fo:block>
						
						<fo:block>
							<fo:inline-container inline-progression-dimension="30%">
								<fo:block>
									Достављено:
								</fo:block>
								<fo:block>
									1.&#160;&#160;&#160;Именованом 								
								</fo:block>
								<fo:block>
									2.&#160;&#160;&#160;Архиви
								</fo:block>
							</fo:inline-container>
							<fo:inline-container inline-progression-dimension="70%">
								<fo:block>
									&#160;
								</fo:block>
								<fo:block margin-left="30px">
									(М.П.)
								</fo:block>
								<fo:block>
									&#160;
								</fo:block>
								<fo:block text-align="center" border-bottom="0.2mm solid black" margin-left="100px" margin-right="20px">
									&#160;
								</fo:block>
								<fo:block text-align="right">
									(потпис овлашћеног лица, односно руководиоца органа)
								</fo:block>
							</fo:inline-container>
						</fo:block>
					</fo:block-container>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    
    <xsl:template match="osnova:bold">
        <fo:inline font-weight="bold"><xsl:apply-templates select="@*|node()"></xsl:apply-templates></fo:inline>
    </xsl:template>
    
    <xsl:template match="osnova:italic">
        <fo:inline font-style="italic"><xsl:apply-templates select="@*|node()"></xsl:apply-templates></fo:inline>
    </xsl:template>
    
</xsl:stylesheet>
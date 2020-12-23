<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:organ_vlasti="https://github.com/draganagrbic998/xml/organ_vlasti">
	
    <xsl:template match="/">
        <fo:root font-family="Times New Roman" font-size="12px" linefeed-treatment="preserve">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="obavestenje-page">
                    <fo:region-body margin="0.5in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="obavestenje-page">
                <fo:flow flow-name="xsl-region-body" linefeed-treatment="preserve">
                
					<fo:block text-align="center">
						<xsl:variable name="organVlasti" select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:OrganVlasti"></xsl:variable>
						<xsl:variable name="datum" select="organ_vlasti:Obavestenje/osnova:datum"></xsl:variable>
						<xsl:variable name="dan" select="substring-after(substring-after($datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesec" select="substring-before(substring-after($datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godina" select="substring-before($datum, '-')"></xsl:variable>
               			
               			<fo:inline-container inline-progression-dimension="40%">
               			
               				<fo:block border-bottom="0.2mm solid black">
								<xsl:value-of select="$organVlasti/osnova:naziv"></xsl:value-of>
               				</fo:block>
               				<fo:block border-bottom="0.2mm solid black">
								<xsl:value-of select="$organVlasti/osnova:sediste"></xsl:value-of>
               				</fo:block>
               				<fo:block line-height="5px">
               					(назив и седиште органа)
               				</fo:block>
               				
               				<fo:block margin-top="5px">
         				         <fo:inline-container inline-progression-dimension="40%">
               						<fo:block text-align="left">Број предмета: </fo:block>
               						<fo:block text-align="left">Датум: </fo:block>
	               				</fo:inline-container>
	               				<fo:inline-container inline-progression-dimension="60%">
               						<fo:block border-bottom="0.2mm solid black">
										<xsl:value-of select="organ_vlasti:Obavestenje/osnova:broj"></xsl:value-of>
               						</fo:block>
               						<fo:block border-bottom="0.2mm solid black">
										<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
               						</fo:block>
	               				</fo:inline-container>               				
               				</fo:block>
							                			
               			</fo:inline-container>
                			
   		                <fo:inline-container inline-progression-dimension="60%">
               				<fo:block></fo:block>  			
               			</fo:inline-container>
                		
               		</fo:block>				
               		
               		<fo:block text-align="center" margin-top="15px">
						<xsl:variable name="osoba" select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
						<xsl:variable name="adresaGradjanina" select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
   		                <fo:inline-container inline-progression-dimension="60%">
               				<fo:block border-bottom="0.2mm solid black" border-top="0.2mm solid black">
								<xsl:variable name="ime" select="$osoba/osnova:ime"></xsl:variable>
								<xsl:variable name="prezime" select="$osoba/osnova:prezime"></xsl:variable>
								<xsl:value-of select="concat($ime, concat(' ', $prezime))"></xsl:value-of>
               				</fo:block>  		
               				<fo:block border-bottom="0.2mm solid black">
								<xsl:variable name="mestoGradjanina" select="$adresaGradjanina/osnova:mesto"></xsl:variable>
								<xsl:variable name="ulicaGradjanina" select="$adresaGradjanina/osnova:ulica"></xsl:variable>
								<xsl:variable name="brojGradjanina" select="$adresaGradjanina/osnova:broj"></xsl:variable>
								<xsl:value-of select="concat($ulicaGradjanina, concat(' ', concat($brojGradjanina, concat(', ', $mestoGradjanina))))"></xsl:value-of>
               				</fo:block>  	
               				<fo:block line-height="5px">
								Име и презиме / назив / и адреса подносиоца захтева
               				</fo:block>		
               			</fo:inline-container>
   		                <fo:inline-container inline-progression-dimension="40%">
               				<fo:block></fo:block>  			
               			</fo:inline-container>
               		
               		</fo:block>	
               		
               		<fo:block text-align="center" font-weight="bold" margin-top="15px">
               			О Б А В Е Ш Т Е Њ Е
               			о стављању на увид документа који садржи
               			тражену информацију и о изради копије
               		</fo:block>
               		
               		<fo:block text-indent="40px" text-align="justify" linefeed-treatment="ignore" margin-top="15px">
						<xsl:variable name="datumZahteva" select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:datum"></xsl:variable>
						<xsl:variable name="danZahteva" select="substring-after(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecZahteva" select="substring-before(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaZahteva" select="substring-before($datumZahteva, '-')"></xsl:variable>
	               		На основу члана 16. ст. 1. Закона о слободном приступу информацијама од јавног значаја, 
	               		поступајући по вашем захтеву за слободан приступ информацијама од
	               		<fo:inline border-bottom="0.2mm solid black">
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
	               		</fo:inline>
	               		год., којим сте тражили увид у документ/е са информацијама о / у вези са:
               		</fo:block>

					<fo:block line-height="16px" margin-top="0.1cm">
						<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:detalji"></xsl:value-of>
					</fo:block>					
					<fo:block-container position="absolute" top="10.2cm">
              			<fo:block border-bottom="0.1mm solid black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="10.8cm">
              			<fo:block border-bottom="0.2mm solid black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="11.3cm">
              			<fo:block border-bottom="0.2mm solid black">
              			</fo:block>              			
              		</fo:block-container>
					
					<fo:block-container position="absolute" top="11.3cm">
						<fo:block text-align="center">
							(опис тражене информације)
						</fo:block>
						
						<fo:block text-align="justify" linefeed-treatment="ignore" margin-top="5px">
							<xsl:variable name="uvid" select="organ_vlasti:Obavestenje/organ_vlasti:Uvid"></xsl:variable>
							<xsl:variable name="adresaObavestenja" select="$uvid/osnova:Adresa"></xsl:variable>
							<xsl:variable name="datumUvida" select="$uvid/organ_vlasti:datum"></xsl:variable>
							<xsl:variable name="danUvida" select="substring-after(substring-after($datumUvida, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecUvida" select="substring-before(substring-after($datumUvida, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaUvida" select="substring-before($datumUvida, '-')"></xsl:variable>
							обавештавамо вас да дана
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="concat($danUvida, concat('.', concat($mesecUvida, concat('.', concat($godinaUvida, '.')))))"></xsl:value-of>
							</fo:inline>
							, у
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="$uvid/organ_vlasti:pocetak"></xsl:value-of>
							</fo:inline>
							часова, односно у времену од
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="$uvid/organ_vlasti:pocetak"></xsl:value-of>
							</fo:inline>
							до
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="$uvid/organ_vlasti:kraj"></xsl:value-of>
							</fo:inline>
							часова, у просторијама органа у
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="$adresaObavestenja/osnova:mesto"></xsl:value-of>
							</fo:inline>
							ул.
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="$adresaObavestenja/osnova:ulica"></xsl:value-of>
							</fo:inline>
							бр.
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="$adresaObavestenja/osnova:broj"></xsl:value-of>
							</fo:inline>
							, канцеларија бр.
							<fo:inline border-bottom="0.2mm solid black">
								<xsl:value-of select="$uvid/organ_vlasti:kancelarija"></xsl:value-of>
							</fo:inline>
							можете извршити увид у документ/е у коме је садржана тражена информација.					
							
						</fo:block>
						
						<fo:block text-indent="40px" text-align="justify" margin-top="10px">
							Том приликом, на ваш захтев, може вам се издати и копија документа са траженом информацијом.
						</fo:block>
						
						<fo:block text-align="justify" linefeed-treatment="ignore" margin-top="10px">
							Трошкови су утврђени Уредбом Владе Републике Србије („Сл. гласник РС“, бр. 8/06), и то: 
							копија стране А4 формата износи 3 динара, А3 формата 6 динара, CD 35 динара, дискете 20 динара, 
							DVD 40 динара, аудио-касета – 150 динара, видео-касета 300 динара, 
							претварање једне стране документа из физичког у електронски облик – 30 динара.
						</fo:block>
						
						<fo:block text-indent="40px" text-align="justify" margin-top="10px" linefeed-treatment="ignore">
							Износ укупних трошкова израде копије документа по вашем захтеву износи
							<fo:inline border-bottom="0.2mm dotted black">
								<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:kopija"></xsl:value-of>
							</fo:inline>
							динара и уплаћује се на жиро-рачун Буџета Републике Србије бр. 840-742328-843-30, 
							с позивом на број 97 – ознака шифре општине/града где се налази орган власти 
							(из Правилника о условима и начину вођења рачуна – „Сл. гласник РС“, 20/07... 40/10).
						</fo:block>
						
						<fo:block margin-top="15px">
							<fo:inline-container inline-progression-dimension="30%">
								<fo:block>
									Достављено:
									1.&#160;&#160;&#160;Именованом 
									2.&#160;&#160;&#160;Архиви
								</fo:block>
							</fo:inline-container>
							<fo:inline-container inline-progression-dimension="70%">
								<fo:block margin-left="40px">
									&#160;
									&#160;
									(М.П.)
								</fo:block>
								<fo:block border-bottom="0.2mm solid black" margin-top="30px" margin-left="120px" margin-right="20px">
									
								</fo:block>
								<fo:block line-height="5px" text-align="right">
									(потпис овлашћеног лица, односно руководиоца органа)
								</fo:block>
							</fo:inline-container>
						</fo:block>
					</fo:block-container>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
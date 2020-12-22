<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
version="2.0"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:organ_vlasti="https://github.com/draganagrbic998/xml/organ_vlasti"
xmlns:poverenik="https://github.com/draganagrbic998/xml/poverenik">
    <xsl:template match="/">
    
		<html>
			<head>
			
			  <style type="text/css">
			  
				div.root{
					max-width: 600px;
					min-width: 600px;
					font-family: "Times New Roman", Times, serif;
				}
			  	
				

			  </style>
			</head>
			<body>
			
				<div class="root">
				
					<div style="display: flex; flex-direction: row">
					
						<div>
						
							<div style="text-align: center">
														<p style="border-bottom: 1px solid black; margin-bottom: 0;">
								<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:OrganVlasti/organ_vlasti:naziv"></xsl:value-of>
							</p>
							<p style="border-bottom: 1px solid black; margin-top: 0; margin-bottom: 0;">
								<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:OrganVlasti/organ_vlasti:sediste"></xsl:value-of>
							</p>
							<p style="margin-top: 0; margin-bottom: 0;">
								(назив и седиште органа)
							</p>
							
							
							</div>
						
						</div>
						
						<div>
						
						</div>
					
					</div>
					<div style="display: flex; flex-direction: row;">
					
						<div style="display: flex; flex-direction: row;">
							<div>
							<p style="margin-bottom: 0; flex: 1; margin-top: 0;">
							Број предмета: 
							</p>
							<p style="margin-top: 0; flex: 1;">
							Датум: 
							</p>
							</div>
							
							<div>
							<p style="flex: 3; border-bottom: 1px solid black; margin-bottom: 0; text-align: center;">
								<xsl:value-of select="organ_vlasti:Obavestenje/osnova:broj"></xsl:value-of>
							</p>
							<p style="flex: 3; border-bottom: 1px solid black; margin-top: 0; text-align: center">
								<xsl:value-of select="organ_vlasti:Obavestenje/osnova:datum"></xsl:value-of>
							</p>
							</div>
						</div>
						
						<div></div>
					</div>
					

					<div style="display: flex; flex-direction: row; margin-top: 20px;">
					
						<div style="text-align: center;">
						
							<p style="border-top: 1px solid black; border-bottom: 1px solid black; margin-bottom: 0;">
							
								<xsl:variable name="osoba" select="organ_vlasti:Obavestenje/organ_vlasti:Podnosenje/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
								<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
								
								
							</p>
							<p style="border-bottom: 1px solid black; margin-top: 0; margin-bottom: 0;">
							
								<xsl:variable name="adresa" select="organ_vlasti:Obavestenje/organ_vlasti:Podnosenje/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
								<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', concat($adresa/osnova:broj, concat(', ', $adresa/osnova:mesto))))"></xsl:value-of>
							</p>		
							<p style="text-align: center; margin-top: 0;">
							Име и презиме / назив / и адреса подносиоца захтева
							</p>				
						</div>
					
						<div></div>
					
					</div>
					
					<br></br>
					
					<h6 style="text-align: center; font-weight: bold;">
					
					
					О Б А В Е Ш Т Е Њ Е<br></br>
					о стављању на увид документа који садржи<br></br>
					тражену информацију и о изради копије
					</h6>
					<br></br>
                <xsl:variable name="dan" select="substring-after(substring-after(organ_vlasti:Obavestenje/organ_vlasti:Podnosenje/osnova:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="mesec" select="substring-before(substring-after(organ_vlasti:Obavestenje/organ_vlasti:Podnosenje/osnova:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="godina" select="substring-before(organ_vlasti:Obavestenje/organ_vlasti:Podnosenje/osnova:datum, '-')"></xsl:variable>

					<p style="text-indent: 40px; text-align: justify; margin-bottom: 0;">
					На основу члана 16. ст. 1. Закона о слободном приступу информацијама од јавног значаја, 
					поступајући по вашем захтеву за слободан приступ информацијама од
					<span style="border-bottom: 1px solid black">
					                <xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
					
					</span>					
					год., 
					којим сте тражили увид у документ/е са информацијама о / у вези са: 
					</p>
					
					<p style="border-bottom: 1px solid black; margin-top: 0; margin-bottom: 0;">
					
						<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Podnosenje/osnova:detalji"></xsl:value-of>
					</p>
					<p style="margin-top: 0; text-align: center; margin-bottom: 0;">
					(опис тражене информације)
					</p>
                <xsl:variable name="dan" select="substring-after(substring-after(organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="mesec" select="substring-before(substring-after(organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="godina" select="substring-before(organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:datum, '-')"></xsl:variable>
					
					<p style="margin-top: 0; text-align: justify;">
					обавештавамо вас да дана
					<span style="border-bottom: 1px solid black">
		                <xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
					
					</span>
					, у
					
					<span style="border-bottom: 1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:pocetak"></xsl:value-of>
					</span>
					часова, односно у времену 
					од
					<span style="border-bottom: 1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:pocetak"></xsl:value-of>
					</span>
					до
					<span style="border-bottom: 1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:kraj"></xsl:value-of>
					</span>
					
					часова, у просторијама органа у
					<span style="border-bottom: 1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/osnova:Adresa/osnova:mesto"></xsl:value-of>
					</span>
					
					ул.
					<span style="border-bottom: 1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/osnova:Adresa/osnova:ulica"></xsl:value-of>
					</span>
					бр.
										<span style="border-bottom: 1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/osnova:Adresa/osnova:broj"></xsl:value-of>
					</span>
					
					, канцеларија бр.
					<span style="border-bottom: 1px solid black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Uvid/organ_vlasti:kancelarija"></xsl:value-of>
					</span>
					
					можете извршити увид у 
					документ/е у коме је садржана тражена информација.
					</p>
					
					<p style="text-indent: 40px; text-align: justify;">
					Том приликом, на ваш захтев, може вам се издати и копија документа са траженом информацијом.
					</p>
					
					<p style="text-align: justify;">
					Трошкови су утврђени Уредбом Владе Републике Србије („Сл. гласник РС“, бр. 8/06), 
					и то: копија стране А4 формата износи 3 динара, А3 формата 6 динара, CD 35 динара, 
					дискете 20 динара, DVD 40 динара, аудио-касета – 150 динара, видео-касета 300 динара, 
					претварање једне стране документа из физичког у електронски облик – 30 динара.
					</p>
					
					<p style="text-indent: 40px; text-align: justify;">
					Износ укупних трошкова израде копије документа по вашем захтеву износи 
					<span style="border-bottom: 1px dotted black">
		                <xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:kopija"></xsl:value-of>
					</span>
					динара и уплаћује се на жиро-рачун Буџета Републике Србије 
					бр. 840-742328-843-30, с позивом на број 97 – ознака шифре општине/града 
					где се налази орган власти (из Правилника о условима и начину вођења 
					рачуна – „Сл. гласник РС“, 20/07... 40/10).
					</p>
					
					<div style="display: flex; flex-direction: row; justify-content: space-between;">
					
						<div>
						
						<p style="margin-bottom: 0;">Достављено:</p>
						<p style="margin-bottom: 0; margin-top: 0">1.	Именованом</p>
						<p style="0; margin-top: 0">2.	Архиви</p>
					
						</div>
						
						<div style="margin-top: 20px;">
						
						<p>(М.П.)</p>
						<p style="border-bottom: 1px solid black; margin-bottom: 0; text-align: center; margin-left: 100px; margin-right: 40px;">
							<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:potpis"></xsl:value-of>
						</p>
						
						<p style="margin-left: 20px; margin-top: 0;">
						(потпис овлашћеног лица, односно руководиоца органа)
						</p>
						
						</div>
					
					</div>

				</div>

			</body>	
		</html>

    </xsl:template>
</xsl:stylesheet>

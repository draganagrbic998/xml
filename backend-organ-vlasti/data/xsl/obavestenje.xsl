<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:organ_vlasti="https://github.com/draganagrbic998/xml/organ_vlasti">

	<xsl:template match="/">
	
		<html>
			
			<head>
			</head>
			
			<body style="max-width: 550px; margin: auto; border: 1px solid black; padding: 50px;">
				
				<h3 style="text-align: center; font-weight: bold;">
					О Б А В Е Ш Т Е Њ Е<br></br>
					о стављању на увид документа који садржи<br></br>
					тражену информацију и о изради копије
				</h3>
				
				<p style="text-indent: 40px; text-align: justify;">
					<xsl:variable name="datumZahteva" select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:datum"></xsl:variable>
					<xsl:variable name="danZahteva" select="substring-after(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecZahteva" select="substring-before(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaZahteva" select="substring-before($datumZahteva, '-')"></xsl:variable>
					На основу члана 16. ст. 1. Закона о слободном приступу информацијама од јавног значаја, 
					поступајући по вашем захтеву за слободан приступ информацијама од
					<span style="border-bottom: 1px solid black;">
						<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
					</span>
					год., којим сте тражили увид у документ/е са информацијама о / у вези са:
				</p>
				
				<p style="border-bottom: 1px solid black; margin-bottom: 0;">
					<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:Zahtev/osnova:detalji"></xsl:value-of>
				</p>
				<p style="margin-top: 0; text-align: center;">
					(опис тражене информације)
				</p>
				<p style="text-align: justify">
					<xsl:variable name="uvid" select="organ_vlasti:Obavestenje/organ_vlasti:Uvid"></xsl:variable>
					<xsl:variable name="adresa" select="$uvid/osnova:Adresa"></xsl:variable>
					<xsl:variable name="datum" select="$uvid/organ_vlasti:datum"></xsl:variable>
					<xsl:variable name="dan" select="substring-after(substring-after($datum, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesec" select="substring-before(substring-after($datum, '-'), '-')"></xsl:variable>
					<xsl:variable name="godina" select="substring-before($datum, '-')"></xsl:variable>
					обавештавамо вас да дана
					<span style="border-bottom: 1px solid black;">
						<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
					</span>
					, у
					<span style="border-bottom: 1px solid black;">
						<xsl:value-of select="$uvid/organ_vlasti:pocetak"></xsl:value-of>
					</span>
					часова, односно у времену од
					<span style="border-bottom: 1px solid black;">
						<xsl:value-of select="$uvid/organ_vlasti:pocetak"></xsl:value-of>
					</span>
					до
					<span style="border-bottom: 1px solid black;">
						<xsl:value-of select="$uvid/organ_vlasti:kraj"></xsl:value-of>
					</span>
					часова, у просторијама органа у
					<span style="border-bottom: 1px solid black;">
						<xsl:value-of select="$adresa/osnova:mesto"></xsl:value-of>
					</span>
					ул.
					<span style="border-bottom: 1px solid black;">
						<xsl:value-of select="$adresa/osnova:ulica"></xsl:value-of>
					</span>
					бр.
					<span style="border-bottom: 1px solid black;">
						<xsl:value-of select="$adresa/osnova:broj"></xsl:value-of>
					</span>
					, канцеларија бр.
					<span style="border-bottom: 1px solid black;">
						<xsl:value-of select="$uvid/organ_vlasti:kancelarija"></xsl:value-of>
					</span>
					можете извршити увид у документ/е у коме је садржана тражена информација. 				
				</p>
				<p style="text-indent: 40px; text-align: justify;">
					Том приликом, на ваш захтев, може вам се издати и копија документа са траженом информацијом.
				</p>
				<p style="text-align: justify;">
					Трошкови су утврђени Уредбом Владе Републике Србије („Сл. гласник РС“, бр. 8/06), и то: 
					копија стране А4 формата износи 3 динара, А3 формата 6 динара, CD 35 динара, 
					дискете 20 динара, DVD 40 динара, аудио-касета – 150 динара, видео-касета 300 динара, 
					претварање једне стране документа из физичког у електронски облик – 30 динара.
				</p>
				<p style="text-indent: 40px; text-align: justify;">
					Износ укупних трошкова израде копије документа по вашем захтеву износи
					<span style="border-bottom: 1px dotted black;">
						<xsl:value-of select="organ_vlasti:Obavestenje/organ_vlasti:kopija"></xsl:value-of>
					</span>
					динара и уплаћује се на жиро-рачун Буџета Републике Србије бр. 840-742328-843-30, 
					с позивом на број 97 – ознака шифре општине/града где се налази орган власти 
					(из Правилника о условима и начину вођења рачуна – „Сл. гласник РС“, 20/07... 40/10). 
				</p>
				
				<div style="display: flex; flex-direction: row; justify-content: space-between;">
					<div>
						<p style="margin-bottom: 0;">
							Достављено:
						</p>
						<p style="margin-bottom: 0; margin-top: 0;">
							1.&#160;&#160;&#160;Именованом 
						</p>
						<p style="margin-top: 0;">
							2.&#160;&#160;&#160;Архиви
						</p>
					</div>
					<div style="margin-top: 15px;">
						<p>
							(М.П.)
						</p>
						<p style="border-bottom: 1px solid black; margin-bottom: 0; margin-left: 100px; margin-right: 20px;">
							<br></br>
						</p>
						<p style="margin-top: 0; font-size: 14px; margin-left: 30px;">
							(потпис овлашћеног лица, односно руководиоца органа)
						</p>
					</div>
				</div>
				
			</body>
		
		</html>
	
	</xsl:template>

</xsl:stylesheet>

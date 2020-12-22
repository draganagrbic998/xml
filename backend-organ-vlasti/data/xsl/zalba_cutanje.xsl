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
					font-family: "Times New Roman", Times, serif;
				}
			  	h1, h2, h3, h4, h5, h6{
			  		text-align: center;
					font-weight: bold;
			  	}
			  	li{
			  	}
			  	p.indent{
				  	text-indent: 40px;
			  	}
				ul { list-style-type: none; }
				li::before { content: "\2610"; margin-right: 0.5em; }
				li.selected::before { content: "\2611"; margin-right: 0.5em; }
				label{
					font-size: 10px;
				}
				.underline{
					border-bottom: 1px solid black;					
				}
				p.no-margin{
					margin: 0 auto;		
					padding: 0 auto;
				}
				p.inline{
					display: inline;
				}
				p.dotted{
					text-align: center;
					border-bottom: 1px dotted black;
					margin: 0 auto;
					margin-left: 50px;
					margin-right: 50px;
				}
				span.dotted{
					border-bottom: 1px dotted black;
				}
				p.dotted-both{
					text-align: center;
					border-bottom: 1px dotted black;
					border-top: 1px dotted black;
					margin: 0 auto;
					margin-left: 20px;
					margin-right: 20px;
					
				}
				p.hint{
					font-size: 11px;
				}
				.center{
					text-align: center;
				}
				div.podaci{
					display: flex;
					flex-direction: row;
					justify-content: space-between;
					align-items: center;
				}
				div.gradjanin{
					text-align: center;
				}

				.test{
					display: flex;
					flex-direction: row;
				}
				.manje{
					width: 70%;
				}
				.temp{
					flex-grow: 1;
					text-indent: 5px;
				}
				p.tempic:before{
					content: "";
				  border-bottom:1px solid black;
				  width:60%;
				  display:block;

				}
				span.mesto{
					padding-left: 10px;
				}


				.mama{

					display: inline;
					border-bottom: 1px dotted black;

				  word-break:break-all;
				  
				}
				
				span.thin{
				  word-break:break-all;
					font-weight: normal;
				}
				

			  </style>
			</head>
			<body>
			
				<div class="root">
				
				<p class="center">
				<b>
				ЖАЛБА КАДА ОРГАН ВЛАСТИ <u>НИЈЕ ПОСТУПИО/ није поступио у целости/ ПО ЗАХТЕВУ</u><br></br>
				ТРАЖИОЦА У ЗАКОНСКОМ  РОКУ  (ЋУТАЊЕ УПРАВЕ)
				
				</b>
				</p>
				
				<p class="no-margin">
				<b>
				Повереникy за информације од јавног значаја и заштиту података о личности
				</b>
				</p>
				<p>
				Адреса за пошту:  Београд, Булевар краља Александрa бр. 15
				</p>
				
				<p>
				У складу са чланом 22. Закона о слободном приступу информацијама од јавног значаја подносим:
				</p>
				
				<h6>Ж А Л Б У<br></br><span class="thin">против</span></h6>
				
				<p class="dotted-both">
					<xsl:value-of select="poverenik:Zalba/poverenik:organVlasti"></xsl:value-of>
				</p>
				<p class="center">
					( навести назив органа )
				</p>
				<p class="center no-margin">
				због тога што орган власти:
				</p>
				<p class="center no-margin">
				<b>
				<xsl:variable name="tipCutanja" select="poverenik:Zalba/poverenik:tipCutanja"></xsl:variable>
				<xsl:if test="$tipCutanja = 'nije_postupio'">
					<u>није поступио</u>
				</xsl:if>
				<xsl:if test="not($tipCutanja = 'nije_postupio')">
					није поступио
				</xsl:if>
				 / 
				<xsl:if test="$tipCutanja = 'nije_postupio_u_celosti'">
					<u>није поступио у целости</u>
				</xsl:if>
				<xsl:if test="not($tipCutanja = 'nije_postupio_u_celosti')">
					није поступио у целости
				</xsl:if>
				 / 
				<xsl:if test="$tipCutanja = 'nije_postupio_u_zakonskom_roku'">
					<u>у законском року</u>
				</xsl:if>
				<xsl:if test="not($tipCutanja = 'nije_postupio_u_zakonskom_roku')">
					у законском року
				</xsl:if>
				</b>
				</p>
				<p class="center">
				(подвући  због чега се изјављује жалба)
				</p>
				
				<p style="text-align: justify" class="no-margin">
				по мом захтеву за слободан приступ информацијама од јавног значаја који сам поднео том органу 
				дана
				<span class="dotted">
                <xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/poverenik:datumZahteva, '-'), '-')"></xsl:variable>
                <xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/poverenik:datumZahteva, '-'), '-')"></xsl:variable>
                <xsl:variable name="godina" select="substring-before(poverenik:Zalba/poverenik:datumZahteva, '-')"></xsl:variable>
                <xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
				</span>
				године, а којим сам тражио/ла да ми се у складу са Законом о 
				слободном приступу информацијама од јавног значаја омогући увид - копија документа који садржи 
				информације о/у вези са:
				</p>
				
				<p class="mama no-margin">
				<xsl:value-of select="poverenik:Zalba/osnova:detalji"></xsl:value-of>
				
				</p>
				
				<p class="center" style="margin-top: 0">
				(навести податке о захтеву и информацији/ама)
				</p>
				
				<p style="text-indent: 40px; text-align: justify; margin: 0 auto; text-align: justify;">
				
				На основу изнетог, предлажем да Повереник уважи моју жалбу и омогући ми приступ траженој/им  информацији/ма.
				</p>
				
				<p style="text-indent: 40px; margin: 0 auto;">
				Као доказ , уз жалбу достављам копију захтева са доказом о предаји органу власти.
				</p>
				
				<p style="margin-top: 0; text-indent: 40px; text-align: justify;">
				<b>Напомена:</b> Код жалбе  због непоступању по захтеву у целости, треба приложити и добијени одговор органа власти.
				</p>
				
				<div style="display: flex; flex-direction: row; justify-content: space-between;">
				
				<div></div>
				<div style="text-align: right">
				<xsl:variable name="osoba" select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
				<xsl:variable name="adresa" select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
				<p style="border-top: 1px dotted black; border-bottom: 1px dotted black; margin-bottom: 0">
					<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
				</p>
				<p style="margin-top: 0">
				Подносилац жалбе / Име и презиме
				</p>
				<p style="border-bottom: 1px dotted black; margin-bottom: 0">
				asd
				</p>
				<p style="margin-top: 0">
				потпис
				</p>
				
				<p style="border-bottom: 1px dotted black; margin-bottom: 0">
				<xsl:value-of select="poverenik:Zalba/osnova:kontakt"></xsl:value-of>
				</p>
				<p style="margin-top: 0">
				други подаци за контакт
				</p>
				
				<p style="border-bottom: 1px dotted black; margin-bottom: 0">
				asd
				</p>
				<p style="margin-top: 0; margin-bottom: 0">
				Потпис
				</p>
				
				</div>
				</div>
				
				<p style="margin-top: 0">
                <xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
                <xsl:variable name="godina" select="substring(substring-before(poverenik:Zalba/osnova:datum, '-'), 3, 2)"></xsl:variable>
				У <span style="border-bottom: 1px dotted black">Новом Саду</span>, 
				дана <span style="border-bottom: 1px dotted black">
                <xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
				</span> 20<span style="border-bottom: 1px dotted black">
				<xsl:value-of select="$godina"></xsl:value-of>
				</span>. године
				
				
				</p>

				</div>

			</body>	
		</html>

    </xsl:template>
</xsl:stylesheet>

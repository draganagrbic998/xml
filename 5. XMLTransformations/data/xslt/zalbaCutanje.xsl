<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
 	xmlns:poverenik="https://github.com/draganagrbic998/xml/poverenik" version="2.0">
    <xsl:template match="/">
        <html>
            <head>
                <title>Zalba Cutanje</title>
                <meta charset="UTF-8"></meta>
                <style type="text/css">
					#maincontainer {
						margin-top:20px;
						width:100%;
					    height: 80mm;
					    display:inline-block;
					}
					#leftcolumn {
					    float:left;
					    width: 300px;
					    height:50%;
					    margin-top:85mm;
					}
					#rightcolumn {
					    float:right;
					    width:300px;
					    height:50%;
					    text-align:right;
					}
                    .center {
                        text-align: center;  
                    }
                   	#vremeIMesto{
                   		white-space:pre;
                   		margin-top:50px;
                   	}
                    body { font-family: sans-serif; 
                    	
                    }
                    h1 {
                    	font-size:14pt;
                    }
                    p span {
					  	display: block;
					}
					p {
						text-align:justify;
					}
                    .page{
	                    background: white;
						width: 21cm;
						height: 29.7cm;
						display: block;
						margin: 0 auto;
						padding: 2cm;
						margin-bottom: 0.5cm;
						box-shadow: 0 0 0.5cm rgba(0,0,0,0.5);
                    }
                    .uvuceno { 
                    	text-indent: 30px; 
                    }
                    #linija{
	                    border-bottom: 1px dotted black;
						padding: 20px;
                    }
                </style>
            </head>
            <body>
            <div class="page">
                <h1 class = "center"> ЖАЛБА КАДА ОРГАН ВЛАСТИ <u>НИЈЕ ПОСТУПИО/ није поступио у целости/ ПО ЗАХТЕВУ</u> ТРАЖИОЦА У ЗАКОНСКОМ  РОКУ  (ЋУТАЊЕ УПРАВЕ)</h1>
            	<p class = "center"> <b>Повереникy за информације од јавног значаја и заштиту података о личности</b></p>
            	<p>Адреса за пошту: </p>  
            	<p>У складу са чланом 22. Закона о слободном приступу информацијама од јавног значаја подносим:</p>
            	<h1 class = "center">Ж А Л Б У</h1>
            	<p class = "center">против</p>
            	<p class = "center"><xsl:value-of select="poverenik:Zalba/poverenik:organVlasti"/></p>
            	<p class = "center">( навести назив органа)</p>
            	<p class = "center"><span>због тога што орган власти: </span>
            	<span><b>није поступио / није поступио у целости /  у законском року</b></span>
            	<span>(подвући  због чега се изјављује жалба)</span>
            	</p>
            	<p>по мом захтеву  за слободан приступ информацијама од јавног значаја који сам поднео  том органу  дана <xsl:value-of select="poverenik:Zalba/poverenik:datumZahteva"/> године, а којим сам тражио/ла да ми се у складу са Законом о слободном приступу информацијама од јавног значаја омогући увид- копија документа који садржи информације  о /у вези са :
            	<xsl:value-of select="poverenik:Zalba/osnova:detalji"/>
            	</p>
            	<p class= "center">(навести податке о захтеву и информацији/ама)</p>
            	<p class = "uvuceno">На основу изнетог, предлажем да Повереник уважи моју жалбу и омогући ми приступ траженој/им  информацији/ма.</p>
                <p class = "uvuceno">Као доказ , уз жалбу достављам копију захтева са доказом о предаји органу власти.</p>
            	<p class = "uvuceno"><b>Напомена: </b>Код жалбе  због непоступању по захтеву у целости, треба приложити и добијени одговор органа власти.</p>
            <div id="maincontainer">
					<div id="leftcolumn">
						У <xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa/osnova:mesto" />, дана <xsl:value-of select="poverenik:Zalba/osnova:datum" /> године
					</div>
				
					<div id="rightcolumn">
						<div id ="linija">
						<xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba/osnova:ime" /> <xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba/osnova:prezime" />
						</div>
						<div>
						Подносилац жалбе / Име и презиме
						</div>
						<div id ="linija">
						<xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa/osnova:mesto" /> <xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa/osnova:ulica" /> <xsl:value-of select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa/osnova:broj" />
						</div>
						<div>
						адреса
						</div>
						<div id ="linija">
						<xsl:value-of select="poverenik:Zalba/osnova:kontakt" /> 
						</div>
						<div>
						други подаци за контакт
						</div>
						<div id ="linija">
						<xsl:value-of select="poverenik:Zalba/osnova:potpis" /> 
						</div>
						<div>
						Потпис
						</div>
					</div>
				</div>
            </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
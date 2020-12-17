<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   	xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
 	xmlns:organ_vlasti="https://github.com/draganagrbic998/xml/organ_vlasti" version="2.0">
    <xsl:template match="/">
        <html>
            <head>
                <title>Zahtev</title>
                <meta charset="UTF-8"></meta>
                <style type="text/css">
					#maincontainer {
						margin-top:20px;
					    width:100%;
					    height: 60mm;
					    display:inline-block;
					}
					#leftcolumn {
					    float:left;
					    width: 300px;
					    height:50%;
					}
					#rightcolumn {
					    float:right;
					    width:300px;
					    height:50%;
					    text-align:center;
					}
                    .center {
                        text-align: center;
                        padding: 30px;
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
                    p { 
                    	text-indent: 30px; 
                    }
                    #linija{
	                    border-bottom: 1px solid black;
						padding-bottom: 5px;
                    }
                    #footnote{
                    	margin-top:100px;
                    	text-align:top;
                    }
  
                </style>
            </head>
            <body>
            <div class="page">
                <p class = "center">назив и седиште органа коме се захтев упућује</p>
                <h1 class = "center"><b>З А Х Т Е В<br></br>
                за приступ информацији од јавног значаја</b></h1>
                <p>На основу члана 15. ст. 1. Закона о слободном приступу информацијама од јавног значаја („Службени гласник РС“, бр. 120/04, 54/07, 104/09 и 36/10), од горе наведеног органа захтевам:<a href="#note1" title="U kucici oznaciti koja zakonska prava na pristup informacijama želite da ostvarite.">*</a>
                </p>
                <ul>
				  <li>обавештење да ли поседује тражену информацију;</li>
				  <li>увид у документ који садржи тражену информацију;</li>
				  <li>копију документа који садржи тражену информацију;</li>
				  <li>достављање копије документа који садржи тражену информацију:
					  <a href="#note2" title="У кућици означити начин достављања копије докумената.">**</a>
					  <ul>
					  <li>поштом</li>
					  <li>електронском поштом</li>
					  <li>факсом</li>
					  <li>на други начин: <a href="#note3" title="Када захтевате други начин достављања обавезно уписати који начин достављања захтевате.">***</a></li>
					  </ul>
				  </li>
				</ul>
				<p>Овај захтев се односи на следеће информације:
                </p>
				<div id="maincontainer">
					<div id="leftcolumn">
						<div id = "vremeIMesto">У ,<br></br>дана године</div>
					</div>
				
					<div id="rightcolumn">
						<div id ="linija">
						</div>
						<div>
						Тражилац информације/Име и презиме
						</div>
						<br></br>
						<div id ="linija">
						</div>
						<div>
						адреса
						</div>
						<br></br>
						<div id ="linija">
						</div>
						<div>
						други подаци за контакт
						</div>
						<br></br>
						<div id ="linija">
						</div>
						<div>
						Потпис
						</div>
					</div>
				</div>
				<div id= "footnote">
					<p id="note1">*У кућици означити која законска права на приступ информацијама желите да остварите.</p>
					<p id="note2">**У кућици означити која законска права на приступ информацијама желите да остварите.</p>
					<p id="note3">***Када захтевате други начин достављања обавезно уписати који начин достављања захтевате.</p>
				</div>
            </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>

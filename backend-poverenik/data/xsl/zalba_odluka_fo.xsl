<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:zalba="https://github.com/draganagrbic998/xml/zalba">    
	
    <xsl:template match="/zalba:Zalba">
        <fo:root font-size="12px" text-align="justify" font-family="Times New Roman">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zalba-odluka-page">
                    <fo:region-body margin="0.5in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="zalba-odluka-page">
                <fo:flow flow-name="xsl-region-body">
                
					<fo:block text-align="center" font-weight="bold">
						ЖАЛБА ПРОТИВ ОДЛУКЕ ОРГАНА ВЛАСТИ КОЈОМ ЈЕ
					</fo:block>
    
					<fo:block text-align="center" font-weight="bold">
						<fo:inline text-decoration="underline">ОДБИЈЕН ИЛИ ОДБАЧЕН ЗАХТЕВ</fo:inline> ЗА ПРИСТУП ИНФОРМАЦИЈИ
    				</fo:block>
    				
    				<fo:block>
    					&#160;
    				</fo:block>
               		
         			<fo:block font-weight="bold">
						Повереникy за информације од јавног значаја и заштиту података о личности
					</fo:block>

					<fo:block>
						Адреса за пошту: Београд, Булевар краља Александрa бр. 15
					</fo:block>
					
    				<fo:block>
    					&#160;
    				</fo:block>
               		
              		<fo:block text-align="center" font-weight="bold">
						Ж А Л Б У
					</fo:block>
					
    				<fo:block>
    					&#160;
    				</fo:block>
					
					<fo:block margin-left="80px" margin-right="80px" text-align-last="justify"> 
						<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
						(<fo:inline border-bottom="1px dotted black">
							<fo:leader></fo:leader>
							<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
							<fo:leader></fo:leader>
						</fo:inline>
					</fo:block>
					
					<fo:block margin-left="100px" margin-right="100px" text-align-last="justify">
						<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
						<fo:inline border-bottom="1px dotted black">
							<fo:leader></fo:leader>
								<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
							<fo:leader></fo:leader>
						</fo:inline>)
					</fo:block>
					
					<fo:block text-align="center"> 
						Име, презиме, односно назив, адреса и седиште жалиоца
					</fo:block>
					
    				<fo:block>
    					&#160;
    				</fo:block>
					
					<fo:block text-align="center">
						против решења-закључка
					</fo:block>
					
					<fo:block margin-left="40px" margin-right="40px" text-align-last="justify">
						(<fo:inline border-bottom="1px dotted black">
							<fo:leader></fo:leader>
								<xsl:value-of select="osnova:OrganVlasti/osnova:naziv"></xsl:value-of>
							<fo:leader></fo:leader>
						</fo:inline>)
					</fo:block>
					
					<fo:block text-align="center">
						(назив органа који је донео одлуку)
					</fo:block>
					
					<fo:block>
						<xsl:variable name="danOdluke" select="substring-after(substring-after(zalba:PodaciOdluke/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecOdluke" select="substring-before(substring-after(zalba:PodaciOdluke/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaOdluke" select="substring-before(zalba:PodaciOdluke/osnova:datum, '-')"></xsl:variable>
						Број 
						<fo:inline border-bottom="1px dotted black">
							<xsl:value-of select="zalba:PodaciOdluke/osnova:brojOdluke"></xsl:value-of>
						</fo:inline> 
						од 
						<fo:inline border-bottom="1px dotted black">
							<xsl:value-of select="concat($danOdluke, concat('.', concat($mesecOdluke, concat('.', concat($godinaOdluke, '.')))))"></xsl:value-of>
						</fo:inline> 
						године.
					</fo:block>
					
    				<fo:block>
    					&#160;
    				</fo:block>
               		
               		<fo:block text-indent="40px">
						<xsl:variable name="danZahteva" select="substring-after(substring-after(zalba:PodaciZahteva/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecZahteva" select="substring-before(substring-after(zalba:PodaciZahteva/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaZahteva" select="substring-before(zalba:PodaciZahteva/osnova:datum, '-')"></xsl:variable>
	               		Наведеном одлуком органа власти (решењем, закључком, обавештењем у писаној форми са елементима одлуке) , 
	               		супротно закону, одбијен-одбачен је мој захтев који сам поднео/ла-упутио/ла дана
						<fo:inline border-bottom="1px dotted black">
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
						</fo:inline>
	               		године и тако ми ускраћено-онемогућено остваривање уставног и законског права на слободан приступ 
	               		информацијама од јавног значаја. Oдлуку побијам у целости, односно у делу којим
               		</fo:block>
               		
           			<fo:block>
						<xsl:value-of select="osnova:Detalji"></xsl:value-of>
					</fo:block>					
					<fo:block-container position="absolute" top="11.2cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="11.7cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="12.2cm">
              			<fo:block border-bottom="1px dotted black">
	           			</fo:block>              			
              		</fo:block-container>
					
					<fo:block-container position="absolute" top="12.2cm">
						<fo:block>
							јер није заснована на Закону о слободном приступу информацијама од јавног значаја.
						</fo:block>
						<fo:block text-indent="40px">
							На основу изнетих разлога, предлажем да Повереник уважи моју жалбу, поништи одлука 
							првостепеног органа и омогући ми приступ траженој/им информацији/ма.
						</fo:block>
						<fo:block text-indent="40px">
							Жалбу подносим благовремено, у законском року утврђеном у члану 22. ст. 1. Закона о 
							слободном приступу информацијама од јавног значаја.
						</fo:block>
						
	    				<fo:block linefeed-treatment="preserve">
	    					&#160;
	    				</fo:block>
						
						<fo:block> 
							<fo:inline-container inline-progression-dimension="60%">
								<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
								<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
								<xsl:variable name="godina" select="substring(substring-before(osnova:datum, '-'), 3, 2)"></xsl:variable>
								<fo:block>
									<fo:block linefeed-treatment="preserve">
										&#160;
										&#160;
									</fo:block>
									У <fo:inline border-bottom="1px dotted black">&#160;<xsl:variable select="osnova:Gradjanin/osnova:Adresa/osnova:mesto"></xsl:variable>&#160;</fo:inline>,
								</fo:block>
								<fo:block margin-top="5px">
									дана 
									<fo:inline border-bottom="1px dotted black">
										<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
									</fo:inline> 
									20<fo:inline border-bottom="1px dotted black">
										<xsl:value-of select="$godina"></xsl:value-of>
									</fo:inline>. 
									године
								</fo:block>
							</fo:inline-container>
							<fo:inline-container text-align="right" inline-progression-dimension="40%">
								<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
								<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
								<fo:block border-bottom="1px dotted black">
									<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
								</fo:block>
								<fo:block>
									Подносилац жалбе / Име и презиме
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="5px">
									<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
								</fo:block>
								<fo:block>
									адреса
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="5px">
									<xsl:value-of select="$osoba/osnova:mejl"></xsl:value-of>
								</fo:block>
								<fo:block>
									други подаци за контакт
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="5px">
									&#160;
								</fo:block>
								<fo:block>
									потпис
								</fo:block>
							</fo:inline-container>
						</fo:block>
						
	    				<fo:block linefeed-treatment="preserve">
	    					&#160;
	    				</fo:block>
						
						<fo:block font-weight="bold">
							Напомена:
						</fo:block>
						
						<fo:list-block>
							<fo:list-item>
               					<fo:list-item-label end-indent="label-end()">
               						<fo:block>&#9679;</fo:block>
               					</fo:list-item-label>
               					<fo:list-item-body start-indent="body-start()">
               						<fo:block>
               							У жалби се мора навести одлука која се побија (решење, закључак, обавештење), 
               							назив органа који је одлуку донео, као и број и датум одлуке. 
               							Довољно је да жалилац наведе у жалби у ком погледу је незадовољан одлуком, 
               							с тим да жалбу не мора посебно образложити. Ако жалбу изјављује на овом обрасцу, 
               							додатно образложење може  посебно приложити.
               						</fo:block>
               					</fo:list-item-body>
							</fo:list-item>
               			    <fo:list-item>
               					<fo:list-item-label end-indent="label-end()">
               						<fo:block>&#9679;</fo:block>
               					</fo:list-item-label>
               					<fo:list-item-body start-indent="body-start()">
               						<fo:block>
               							Уз жалбу обавезно приложити копију поднетог захтева и 
               							доказ о његовој предаји-упућивању органу као и копију одлуке 
               							органа која се оспорава жалбом.
               						</fo:block>
               					</fo:list-item-body>
               				</fo:list-item>
						</fo:list-block>
					</fo:block-container>
               		
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>

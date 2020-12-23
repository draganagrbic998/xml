<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:poverenik="https://github.com/draganagrbic998/xml/poverenik">    
	
    <xsl:template match="/">
        <fo:root font-family="Times New Roman" font-size="14px" linefeed-treatment="ignore" text-align="justify">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zalba-odbijanje-page">
                    <fo:region-body margin="0.5in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="zalba-odbijanje-page">
                <fo:flow flow-name="xsl-region-body">
                
					<fo:block text-align="center" font-weight="bold" linefeed-treatment="preserve">
						ЖАЛБА ПРОТИВ ОДЛУКЕ ОРГАНА ВЛАСТИ КОЈОМ ЈЕ
						<fo:inline text-decoration="underline">ОДБИЈЕН ИЛИ ОДБАЧЕН ЗАХТЕВ</fo:inline> ЗА ПРИСТУП ИНФОРМАЦИЈИ
					</fo:block>
               		
         			<fo:block margin-top="20px" linefeed-treatment="preserve">
						<fo:inline font-weight="bold">Повереникy за информације од јавног значаја и заштиту података о личности</fo:inline>
						Адреса за пошту: Београд, Булевар краља Александрa бр. 15
					</fo:block>
               		
              		<fo:block text-align="center" margin-top="10px" font-weight="bold">
						Ж А Л Б У
					</fo:block>
					
					<fo:block text-align-last="justify" margin-top="10px" margin-left="80px" margin-right="80px"> 
						<xsl:variable name="osoba" select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
						(<fo:inline border-bottom="1px dotted black">
							<fo:leader></fo:leader>
							<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
							<fo:leader></fo:leader>
						</fo:inline>
					</fo:block>
					<fo:block text-align-last="justify" margin-left="100px" margin-right="100px">
						<xsl:variable name="adresa" select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa"></xsl:variable> 
						<fo:inline border-bottom="1px dotted black">
							<fo:leader></fo:leader>
								<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
							<fo:leader></fo:leader>
						</fo:inline>)
					</fo:block>
					
					<fo:block text-align="center"> 
						Име, презиме, односно назив, адреса и седиште жалиоца
					</fo:block>
					
					<fo:block text-align="center" margin-top="10px">
						против решења-закључка
					</fo:block>
					
					<fo:block text-align-last="justify" margin-left="40px" margin-right="40px">
						(<fo:inline border-bottom="1px dotted black">
							<fo:leader></fo:leader>
								<xsl:value-of select="poverenik:Zalba/osnova:OrganVlasti/osnova:naziv"></xsl:value-of>
							<fo:leader></fo:leader>
						</fo:inline>)
					</fo:block>
					
					<fo:block text-align="center">
						(назив органа који је донео одлуку)
					</fo:block>
					
					<fo:block>
						<xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godina" select="substring(substring-before(poverenik:Zalba/osnova:datum, '-'), 3, 2)"></xsl:variable>
						Број <fo:inline border-bottom="1px dotted black">
							<xsl:value-of select="poverenik:Zalba/poverenik:brojOdluke"></xsl:value-of>
						</fo:inline> од <fo:inline border-bottom="1px dotted black">
							<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
						</fo:inline> године.
					</fo:block>
               		
               		<fo:block text-indent="40px" margin-top="10px">
						<xsl:variable name="datumZahteva" select="poverenik:Zalba/poverenik:datumZahteva"></xsl:variable>
						<xsl:variable name="danZahteva" select="substring-after(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecZahteva" select="substring-before(substring-after($datumZahteva, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaZahteva" select="substring-before($datumZahteva, '-')"></xsl:variable>
	               		Наведеном одлуком органа власти (решењем, закључком, обавештењем у писаној форми са елементима одлуке) , 
	               		супротно закону, одбијен-одбачен је мој захтев који сам поднео/ла-упутио/ла дана
						<fo:inline border-bottom="1px dotted black">
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
						</fo:inline>
	               		године и тако ми ускраћено-онемогућено остваривање уставног и законског права на слободан приступ 
	               		информацијама од јавног значаја. Oдлуку побијам у целости, односно у делу којим
               		</fo:block>
               		
           			<fo:block>
						<xsl:value-of select="poverenik:Zalba/osnova:detalji"></xsl:value-of>
					</fo:block>					
					<fo:block-container position="absolute" top="13.4cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="14cm">
              			<fo:block border-bottom="1px dotted black">
              			</fo:block>
              		</fo:block-container>
              		<fo:block-container position="absolute" top="14.5cm">
              			<fo:block border-bottom="1px dotted black">
	           			</fo:block>              			
              		</fo:block-container>
					
					<fo:block-container position="absolute" top="14.6cm">
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
						<fo:block margin-top="25px"> 
							<xsl:variable name="osoba" select="poverenik:Zalba/osnova:Gradjanin/osnova:Osoba"></xsl:variable>
							<xsl:variable name="adresa" select="poverenik:Zalba/osnova:Gradjanin/osnova:Adresa"></xsl:variable>
							<xsl:variable name="dan" select="substring-after(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesec" select="substring-before(substring-after(poverenik:Zalba/osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="godina" select="substring(substring-before(poverenik:Zalba/osnova:datum, '-'), 3, 2)"></xsl:variable>
							<fo:inline-container inline-progression-dimension="50%">
								<fo:block linefeed-treatment="preserve">
									&#160;
									&#160;

								</fo:block>
								<fo:block margin-left="30px">
									У <fo:inline padding-left="15px" padding-right="15px" border-bottom="1px dotted black">
										Novom Sadu
									</fo:inline>,
								</fo:block>
								<fo:block margin-top="25px" margin-left="40px">
									дана <fo:inline border-bottom="1px dotted black">
										<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
									</fo:inline> 20<fo:inline border-bottom="1px dotted black">
										<xsl:value-of select="$godina"></xsl:value-of>
									</fo:inline>. године
								</fo:block>
							</fo:inline-container>
							<fo:inline-container text-align="right" inline-progression-dimension="50%">
								<fo:block border-bottom="1px dotted black">
									<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
								</fo:block>
								<fo:block>
									Подносилац жалбе / Име и презиме
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="15px">
									<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
								</fo:block>
								<fo:block>
									адреса
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="15px">
									<xsl:value-of select="poverenik:Zalba/osnova:kontakt"></xsl:value-of>
								</fo:block>
								<fo:block>
									други подаци за контакт
								</fo:block>
								<fo:block border-bottom="1px dotted black" margin-top="15px">
									&#160;
								</fo:block>
								<fo:block>
									потпис
								</fo:block>
							</fo:inline-container>
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

<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:resenje="https://github.com/draganagrbic998/xml/resenje">
	
    <xsl:template match="/resenje:Resenje">
        <fo:root font-size="12px" text-align="justify" font-family="Times New Roman">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="resenje-page">
                    <fo:region-body margin="0.5in" margin-bottom="1in"></fo:region-body>
	                <fo:region-after region-name="xsl-region-after" extent="1.5in"></fo:region-after>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="resenje-page">
	            <fo:static-content flow-name="xsl-region-after" font-size="10px" color="blue">
	          		<fo:block linefeed-treatment="preserve" margin="0.5in">
       					<fo:leader leader-length="100%" leader-pattern="rule"></fo:leader>                        
						Повереник за информације од јавног значаја и заштиту података о личности	  
						&#160;&#160;&#160;Информатор о раду 31.08.2020.године   
	    				<fo:block text-align-last="justify">
							<fo:leader></fo:leader>
							<fo:page-number></fo:page-number>     		
						</fo:block>
	          		</fo:block>
	      		</fo:static-content>
            
                <fo:flow flow-name="xsl-region-body">
                
					<xsl:variable name="status" select="resenje:status"></xsl:variable>
					<xsl:variable name="organVlasti" select="osnova:OrganVlasti/osnova:naziv"></xsl:variable>
					<xsl:variable name="sedisteMesto" select="osnova:OrganVlasti/osnova:Adresa/osnova:mesto"></xsl:variable>
					<xsl:variable name="sedisteUlica" select="osnova:OrganVlasti/osnova:Adresa/osnova:ulica"></xsl:variable>
					<xsl:variable name="sedisteBroj" select="osnova:OrganVlasti/osnova:Adresa/osnova:broj"></xsl:variable>
					<xsl:variable name="sediste" select="concat($sedisteUlica, concat(' ', concat($sedisteBroj, concat(', ', $sedisteMesto))))"></xsl:variable>
	
					<xsl:variable name="podaciZahteva" select="resenje:PodaciZahteva"></xsl:variable>
					<xsl:variable name="danZahteva" select="substring-after(substring-after($podaciZahteva/osnova:datum, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecZahteva" select="substring-before(substring-after($podaciZahteva/osnova:datum, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaZahteva" select="substring-before($podaciZahteva/osnova:datum, '-')"></xsl:variable>
					
					<xsl:variable name="podaciZalbe" select="resenje:PodaciZalbe"></xsl:variable>
					<xsl:variable name="danZalbe" select="substring-after(substring-after($podaciZalbe/resenje:datumZalbe, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecZalbe" select="substring-before(substring-after($podaciZalbe/resenje:datumZalbe, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaZalbe" select="substring-before($podaciZalbe/resenje:datumZalbe, '-')"></xsl:variable>
	
					<xsl:variable name="podaciOdluke" select="resenje:PodaciOdluke"></xsl:variable>
					<xsl:variable name="danOdluke" select="substring-after(substring-after($podaciOdluke/osnova:datum, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecOdluke" select="substring-before(substring-after($podaciOdluke/osnova:datum, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaOdluke" select="substring-before($podaciOdluke/osnova:datum, '-')"></xsl:variable>

					<xsl:variable name="zahtev_broj" select="substring-after(resenje:PodaciZahteva/@href, 'https://github.com/draganagrbic998/xml/zahtev/')"></xsl:variable>
					<xsl:variable name="odluka_broj" select="substring-after(resenje:PodaciOdluke/@href, 'https://github.com/draganagrbic998/xml/odluka/')"></xsl:variable>
					<xsl:variable name="zalba_broj" select="substring-after(resenje:PodaciZalbe/datumZalbe/@href, 'https://github.com/draganagrbic998/xml/zalba/')"></xsl:variable>
	
					<fo:block>
						<fo:inline-container>
							<fo:block>
								Решење
								<xsl:if test="$status='odobreno'">
									када је жалба основана – налаже се:
								</xsl:if>
								<xsl:if test="$status='odbijeno' and $podaciOdluke">
									– одбија се као неоснована:
								</xsl:if>
								<xsl:if test="$status='odbijeno' and not($podaciOdluke)">
									– одбија се захтев:
								</xsl:if>
								<xsl:if test="$status='odustato' or $status='obavesteno'">
									- поништава се
								</xsl:if>
							</fo:block>
						</fo:inline-container>
						<fo:inline-container>
							<fo:block></fo:block>
						</fo:inline-container>
					</fo:block>
					<fo:block>
						<fo:inline-container inline-progression-dimension="50%">
							<fo:block>
								Бр. <xsl:value-of select="substring-after(@about, 'https://github.com/draganagrbic998/xml/resenje/')"></xsl:value-of>
							</fo:block>
						</fo:inline-container>
						<fo:inline-container inline-progression-dimension="50%">
							<fo:block text-align="right">
								<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
								<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
								<xsl:variable name="godina" select="substring-before(osnova:datum, '-')"></xsl:variable>
								Датум: 
								<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
								године
							</fo:block>
						</fo:inline-container>
					</fo:block>

					<fo:block margin-top="15px">
						Повереник за информације од јавног значаја и заштиту података о личности, 
						у поступку по алби коју је изјавио AA, 
						<xsl:if test="not($podaciOdluke)">
							због непоступања				
						</xsl:if>
						<xsl:if test="$podaciOdluke">
							против одлуке				
						</xsl:if>
						органа
						<xsl:value-of select="$organVlasti"></xsl:value-of>,
						<xsl:value-of select="$sediste"></xsl:value-of>, 
						<xsl:if test="$podaciOdluke">
							број: 
							<xsl:value-of select="$odluka_broj"></xsl:value-of> 
							од 
							<xsl:value-of select="concat($danOdluke, concat('.', concat($mesecOdluke, concat('.', concat($godinaOdluke, '.')))))"></xsl:value-of>					
							године,
						</xsl:if>
						због недобијања тражених информација по његовом захтеву за приступ информацијама од јавног значаја поднетом														
						<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
						године за приступ информацијама од јавног значаја, на основу члана 35. став 1. тачка 5. 
						Закона о слободном приступу информацијама од јавног значаја 
						(„Сл. гласник РС“, бр. 120/04, 54/07, 104/09 и 36/10), а у вези са чланом 4. тачка 22. 
						Закона о заштити података о личности („Сл. гласник РС“, број 87/18), 
						као и члана 23. и члана 24. став 4. Закона о слободном приступу информацијама од јавног значаја 
						и члана 173. став 2. Закона о општем управном поступку 
						(„Сл. гласник РС“, бр. 18/2016 и 95/2018-аутентично тумачење), доноси
					</fo:block>
					
					<fo:block text-align="center" margin-top="15px">
						Р Е Ш Е Њ Е
					</fo:block>
					
					<xsl:for-each select="resenje:Odluka/resenje:Dispozitiva/resenje:Pasus">
						<fo:block text-indent="40px">
				            <xsl:number format="I"></xsl:number>&#160;<xsl:apply-templates></xsl:apply-templates>
						</fo:block >
					</xsl:for-each>
					
					<xsl:if test="$status='odustato' or $status='obavesteno'">
						<fo:block text-indent="40px">
							Поништава се жалба коју је поднео АА дана
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
							против органа власти <xsl:value-of select="$organVlasti"></xsl:value-of>,
							<xsl:value-of select="$sediste"></xsl:value-of>
						</fo:block>
					</xsl:if>
					
					<xsl:if test="$status='odbijeno'">
						<fo:block text-indent="40px">
							<xsl:if test="$podaciOdluke">
								Одбија се, као неоснована, жалба АА, 
								изјављена против одлуке органа 
								<xsl:value-of select="$organVlasti"></xsl:value-of>, 
								<xsl:value-of select="$sediste"></xsl:value-of>,
									број: 
								<xsl:value-of select="$odluka_broj"></xsl:value-of>
									од 
								<xsl:value-of select="concat($danOdluke, concat('.', concat($mesecOdluke, concat('.', concat($godinaOdluke, '.')))))"></xsl:value-of>					
									године,
									због недобијања тражених информација по његовом захтеву за приступ информацијама 
									од јавног значаја поднетом
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
								године.
							</xsl:if>
							<xsl:if test="not($podaciOdluke)">
								Одбија се, као неоснован, захтев АА, 
								за приступ информацијама од јавног значаја, поднет 
								<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
								године органу
								<xsl:value-of select="$organVlasti"></xsl:value-of>, 
								<xsl:value-of select="$sediste"></xsl:value-of>.
							</xsl:if>
						</fo:block>
					</xsl:if>
					
					<fo:block text-align="center" margin-top="15px">
						О б р а з л о ж е њ е
					</fo:block>
					
					<fo:block text-indent="40px">
						АА, као тражилац информација, изјавио је дана 
						<xsl:value-of select="concat($danZalbe, concat('.', concat($mesecZalbe, concat('.', concat($godinaZalbe, '.')))))"></xsl:value-of>					
						године жалбу Поверенику, 
							<xsl:if test="not($podaciOdluke)">
							због непоступања 						
						</xsl:if>
							<xsl:if test="$podaciOdluke">
							против одлуке
						</xsl:if>
						органа
						<xsl:value-of select="$organVlasti"></xsl:value-of>,
						<xsl:value-of select="$sediste"></xsl:value-of>,
						<xsl:if test="$podaciOdluke">
							број: 
							<xsl:value-of select="$odluka_broj"></xsl:value-of> 
							од 
							<xsl:value-of select="concat($danOdluke, concat('.', concat($mesecOdluke, concat('.', concat($godinaOdluke, '.')))))"></xsl:value-of>					
							године,
							због недобијања тражених информација
						</xsl:if>
						по његовом захтеву од 
						<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>										
							године за приступ информацијама од јавног значаја.
						<xsl:if test="not($podaciOdluke)">
							Уз жалбу је приложена
							
							<fo:basic-link>
	               				<xsl:attribute name="external-destination">
	               					<xsl:value-of select="concat('http://localhost:4201/pdf/zahtevi/', $zahtev_broj)"></xsl:value-of>
	               				</xsl:attribute>
	               				<xsl:attribute name="color">
									blue
	               				</xsl:attribute>
	               				копија поднетог захтева
	               			</fo:basic-link>
							
							са доказом о предаји органу власти.
						</xsl:if>
						<xsl:if test="$podaciZalbe/resenje:tipZalbe = 'delimicnost'">
							У жалби је навео да му предметним обавештењем орган власти није доставио тражене информације, 
							па предлаже да Повереник уважи његову жалбу и наложи да му се доставе тражене информације. 
							У прилогу је доставио
							
							<fo:basic-link>
	               				<xsl:attribute name="external-destination">
	               					<xsl:value-of select="concat('http://localhost:4201/pdf/zahtevi/', $zahtev_broj)"></xsl:value-of>
	               				</xsl:attribute>
	               				<xsl:attribute name="color">
									blue
	               				</xsl:attribute>
								копије захтева
	               			</fo:basic-link>
							и
							<fo:basic-link>
	               				<xsl:attribute name="external-destination">
	               					<xsl:value-of select="concat('http://localhost:4201/pdf/odluke/', $odluka_broj)"></xsl:value-of>
	               				</xsl:attribute>
	               				<xsl:attribute name="color">
									blue
	               				</xsl:attribute>
								ожалбеног обавештења.
	               			</fo:basic-link>
							
						</xsl:if>
						<xsl:if test="$podaciZalbe/resenje:tipZalbe = 'odluka'">
							У жалби је навео да му је издатим решењем за одбијање захтева ускраћено уставно право 
							на информације од јавног значаја и зато захтева од Повереника да наложи доставу
							тражене информације. У прилогу је доставио
							
							<fo:basic-link>
	               				<xsl:attribute name="external-destination">
	               					<xsl:value-of select="concat('http://localhost:4201/pdf/zahtevi/', $zahtev_broj)"></xsl:value-of>
	               				</xsl:attribute>
	               				<xsl:attribute name="color">
									blue
	               				</xsl:attribute>
								копије захтева
	               			</fo:basic-link>
							и
							<fo:basic-link>
	               				<xsl:attribute name="external-destination">
	               					<xsl:value-of select="concat('http://localhost:4201/pdf/odluke/', $odluka_broj)"></xsl:value-of>
	               				</xsl:attribute>
	               				<xsl:attribute name="color">
									blue
	               				</xsl:attribute>
								издатог решења о одбијању захтева
	               			</fo:basic-link>							
							.
						</xsl:if>
					</fo:block>
					
					<xsl:if test="$podaciZalbe/resenje:datumProsledjivanja">
						<fo:block text-indent="40px">
							<xsl:variable name="danProsledjivanja" select="substring-after(substring-after($podaciZalbe/resenje:datumProsledjivanja, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecProsledjivanja" select="substring-before(substring-after($podaciZalbe/resenje:datumProsledjivanja, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaProsledjivanja" select="substring-before($podaciZalbe/resenje:datumProsledjivanja, '-')"></xsl:variable>
							Поступајући по
							
							<fo:basic-link>
	               				<xsl:attribute name="external-destination">
	               					<xsl:value-of select="concat('http://localhost:4201/pdf/zalbe/', $zalba_broj)"></xsl:value-of>
	               				</xsl:attribute>
	               				<xsl:attribute name="color">
									blue
	               				</xsl:attribute>
								жалби
	               			</fo:basic-link>							
													
							, Повереник је дана 					
							<xsl:value-of select="concat($danProsledjivanja, concat('.', concat($mesecProsledjivanja, concat('.', concat($godinaProsledjivanja, '.')))))"></xsl:value-of>					
							године упутио исту на изјашњење органу
							<xsl:value-of select="$organVlasti"></xsl:value-of>,
							<xsl:value-of select="$sediste"></xsl:value-of>,
							као органу власти у смислу члана 3. Закона о слободном приступу информацијама од 
							јавног значаја
							<xsl:if test="not($podaciOdluke)">
								и затражио да се изјасни о наводима жалбе, посебно о разлозима непоступања у законском 
								року по поднетом захтеву у складу са одредбама члана 16. ст.1-9. или ст. 10. Закона, 
								остављајући рок од осам 
								дана</xsl:if><xsl:if test="resenje:Odbrana/osnova:Detalji">.</xsl:if>	
		 					<xsl:if test="not(resenje:Odbrana/osnova:Detalji)">
								, поводом чега није добио одговор.
							</xsl:if>	
						</fo:block>
					</xsl:if>
										
					<xsl:if test="resenje:Odbrana/osnova:Detalji">.
						<fo:block text-indent="40px">
							<xsl:variable name="danOdbrane" select="substring-after(substring-after(resenje:Odbrana/resenje:datumOdbrane, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecOdbrane" select="substring-before(substring-after(resenje:Odbrana/resenje:datumOdbrane, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaOdbrane" select="substring-before(resenje:Odbrana/resenje:datumOdbrane, '-')"></xsl:variable>
							У 
							
							<fo:basic-link>
	               				<xsl:attribute name="external-destination">
	               					<xsl:value-of select="concat('http://localhost:4201/pdf/odgovori/', $zalba_broj)"></xsl:value-of>
	               				</xsl:attribute>
	               				<xsl:attribute name="color">
									blue
	               				</xsl:attribute>
								одговору на жалбу 
	               			</fo:basic-link>														
							
							од 
							<xsl:value-of select="concat($danOdbrane, concat('.', concat($mesecOdbrane, concat('.', concat($godinaOdbrane, '.')))))"></xsl:value-of>					
							године, орган власти се изјаснио следећим ставом:
							<xsl:apply-templates select="resenje:Odbrana/osnova:Detalji"></xsl:apply-templates>.
						</fo:block>					
					</xsl:if>
				
					<fo:block text-indent="40px">
						По разматрању жалбе и осталих списа овог предмета, донета је 
						одлука као у диспозитиву решења из следећих разлога:
					</fo:block>
				
					<fo:block text-indent="40px">
						Увидом у списе предмета утврђено је да је АА, дана 
						<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
						године, поднео органу
						<xsl:value-of select="$organVlasti"></xsl:value-of>,
						<xsl:value-of select="$sediste"></xsl:value-of>,					
						захтев за приступ информацијама од јавног значаја, електронском поштом, 
						којим је тражио информације од јавног значаја, 
						а односе се на: <xsl:apply-templates select="$podaciZahteva/osnova:Detalji"></xsl:apply-templates>			
					</fo:block>
				
					<xsl:if test="not($podaciOdluke)">
						<fo:block text-indent="40px">
							Такође је увидом у списе предмета утврђено да по поднетом захтеву жалиоца орган власти 
							није поступио, што је био дужан да учини без одлагања, а најкасније у року од 15 дана 
							од дана пријема захтева те да га, у смислу члана 16. став 1. 
							Закона о слободном приступу информацијама од јавног значаја, обавести да ли поседује 
							тражене информације и да му, уколико исте поседује, достави копије докумената 
							у којима су оне садржане или да, у супротном, донесе решење о одбијању захтева, 
							у смислу става 10. истог члана.
						</fo:block>
					</xsl:if>
					
					<xsl:if test="$podaciOdluke">
						<fo:block text-indent="40px">
							Даље је, увидом у списе предмета утврђено да је одговором број: 
							<xsl:value-of select="$odluka_broj"></xsl:value-of> 
							од 
							<xsl:value-of select="concat($danOdluke, concat('.', concat($mesecOdluke, concat('.', concat($godinaOdluke, '.')))))"></xsl:value-of>										
							године, орган власти обавестио жалиоца следеће:
							<xsl:apply-templates select="$podaciOdluke/osnova:Detalji"></xsl:apply-templates>
						</fo:block>
						<fo:block text-indent="40px">
							Чланом 16. став 1. Закона о слободном приступу информацијама од јавног значаја 
							прописано је да је орган власти дужан да без одлагања, а најкасније у року од 
							15 дана од дана пријема захтева, тражиоца обавести о поседовању информације, 
							стави му на увид документ који садржи тражену информацију, односно изда му или упути 
							копију тог документа.
						</fo:block>
					</xsl:if>

					<xsl:if test="$status='odustato' or $status='obavesteno'">
						<xsl:variable name="danOtkazivanja" select="substring-after(substring-after($podaciZalbe/resenje:datumOtkazivanja, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecOtkazivanja" select="substring-before(substring-after($podaciZalbe/resenje:datumOtkazivanja, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaOtkazivanja" select="substring-before($podaciZalbe/resenje:datumOtkazivanja, '-')"></xsl:variable>						
						<fo:block text-indent="40px">
							<fo:inline font-weight="bold">Жалбени поступак се поништава</fo:inline> из разлога што је
							<xsl:if test="$status='odustato'">
								АА дана 
								<xsl:value-of select="concat($danOtkazivanja, concat('.', concat($mesecOtkazivanja, concat('.', concat($godinaOtkazivanja, '.')))))"></xsl:value-of>					
								послао захтев за одустајањем од поднете жалбе.
							</xsl:if>
							<xsl:if test="$status='obavesteno'">
								орган власти
								<xsl:value-of select="$organVlasti"></xsl:value-of>,
								<xsl:value-of select="$sediste"></xsl:value-of>
								дана
								<xsl:value-of select="concat($danOtkazivanja, concat('.', concat($mesecOtkazivanja, concat('.', concat($godinaOtkazivanja, '.')))))"></xsl:value-of>					
								обавестио АА електронском поштом о информацијама које је претходно 
								тражио.
							</xsl:if>
						</fo:block>
					</xsl:if>

					<xsl:for-each select="resenje:Odluka/resenje:Obrazlozenje/resenje:Pasus">
						<fo:block text-indent="40px">
							<xsl:apply-templates></xsl:apply-templates>
						</fo:block >
					</xsl:for-each>
			
					<fo:block text-indent="40px">
						Упутство о правном средству:
					</fo:block>
					
					<fo:block text-indent="40px">
						Против овог решења није допуштена жалба већ се, у складу са Законом о управним споровима, 
						може покренути управни спор тужбом Управном суду у Београду, у року од 30 дана 
						од дана пријема решења. Такса на тужбу износи 390,00 динара.
					</fo:block>
				
					<fo:block margin-top="15px">
						<fo:inline-container inline-progression-dimension="50%">
							<fo:block></fo:block>
						</fo:inline-container>
						<fo:inline-container inline-progression-dimension="50%">
							<fo:block text-align="right">
								<fo:block>
									ПОВЕРЕНИК
								</fo:block>
								<fo:block>
									<xsl:value-of select="concat(osnova:Osoba/osnova:ime, concat(' ', osnova:Osoba/osnova:prezime))"></xsl:value-of>
								</fo:block>
							</fo:block>
						</fo:inline-container>
					</fo:block>

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
    
    <xsl:template match="resenje:zakon">
        <fo:inline text-decoration="underline"><xsl:apply-templates select="@*|node()"></xsl:apply-templates></fo:inline>
    </xsl:template>
    
</xsl:stylesheet>
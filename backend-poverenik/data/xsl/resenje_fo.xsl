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
                    <fo:region-body margin="0.5in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="resenje-page">
                <fo:flow flow-name="xsl-region-body">
                
					<xsl:variable name="status" select="resenje:status"></xsl:variable>
					<xsl:variable name="organVlasti" select="osnova:OrganVlasti/osnova:naziv"></xsl:variable>
					<xsl:variable name="sedisteMesto" select="osnova:OrganVlasti/osnova:Adresa/osnova:mesto"></xsl:variable>
					<xsl:variable name="sedisteUlica" select="osnova:OrganVlasti/osnova:Adresa/osnova:ulica"></xsl:variable>
					<xsl:variable name="sedisteBroj" select="osnova:OrganVlasti/osnova:Adresa/osnova:broj"></xsl:variable>
					<xsl:variable name="sediste" select="concat($sedisteUlica, concat(' ', concat($sedisteBroj, concat(', ', $sedisteMesto))))"></xsl:variable>

					<xsl:variable name="podaciZahteva" select="resenje:PodaciZahteva"></xsl:variable>
					<xsl:variable name="danZahteva" select="substring-after(substring-after($podaciZahteva/resenje:datumZahteva, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecZahteva" select="substring-before(substring-after($podaciZahteva/resenje:datumZahteva, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaZahteva" select="substring-before($podaciZahteva/resenje:datumZahteva, '-')"></xsl:variable>
					<xsl:variable name="danZalbe" select="substring-after(substring-after($podaciZahteva/resenje:datumZalbe, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecZalbe" select="substring-before(substring-after($podaciZahteva/resenje:datumZalbe, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaZalbe" select="substring-before($podaciZahteva/resenje:datumZalbe, '-')"></xsl:variable>

					<xsl:variable name="podaciOdgovora" select="resenje:PodaciOdgovora"></xsl:variable>
					<xsl:variable name="danOdgovora" select="substring-after(substring-after($podaciOdgovora/resenje:datumOdgovora, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecOdgovora" select="substring-before(substring-after($podaciOdgovora/resenje:datumOdgovora, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaOdgovora" select="substring-before($podaciOdgovora/resenje:datumOdgovora, '-')"></xsl:variable>

					<fo:block>
						<fo:inline-container>
							<fo:block>
								Решење
								<xsl:if test="$status='odobrenо'">
									када је жалба основана – налаже се:
								</xsl:if>
								<xsl:if test="$status='odbijeno'">
									– одбија се жалба као неоснована:
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
								Бр. <xsl:value-of select="osnova:broj"></xsl:value-of>
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

					<fo:block>
						Повереник за информације од јавног значаја и заштиту података о личности, 
						у поступку по алби коју је изјавио AA, 
						<xsl:if test="not($podaciOdgovora)">
							због непоступања				
						</xsl:if>
						<xsl:if test="$podaciOdgovora">
							против одлуке				
						</xsl:if>
						органа
						<xsl:value-of select="$organVlasti"></xsl:value-of>,
						<xsl:value-of select="$sediste"></xsl:value-of>, 
						<xsl:if test="$podaciOdgovora">
							број: 
							<xsl:value-of select="$podaciOdgovora/resenje:brojOdgovora"></xsl:value-of> 
							од 
							<xsl:value-of select="concat($danOdgovora, concat('.', concat($mesecOdgovora, concat('.', concat($godinaOdgovora, '.')))))"></xsl:value-of>					
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
					
					<fo:block text-align="center">
						Р Е Ш Е Њ Е
					</fo:block>
					
					<xsl:if test="$status='odobreno'">
						<xsl:for-each select="resenje:Odluka/resenje:Dispozitiva/resenje:Pasus">
							<fo:block text-indent="40px">
					            <xsl:number format="I"></xsl:number>&#160;<xsl:value-of select="text()"></xsl:value-of>
							</fo:block >
						</xsl:for-each>
					</xsl:if>
					
					<xsl:if test="$status='odbijeno'">
						<fo:block text-indent="40px">
							<xsl:if test="$podaciOdgovora">
								Одбија се, као неоснована, жалба АА, 
								изјављена против обавештења органа 
								<xsl:value-of select="$organVlasti"></xsl:value-of>, 
								<xsl:value-of select="$sediste"></xsl:value-of>,
									број: 
								<xsl:value-of select="$podaciOdgovora/resenje:brojOdgovora"></xsl:value-of>
									од 
								<xsl:value-of select="concat($danOdgovora, concat('.', concat($mesecOdgovora, concat('.', concat($godinaOdgovora, '.')))))"></xsl:value-of>					
									године,
									због недобијања тражених информација по његовом захтеву за приступ информацијама 
									од јавног значаја поднетом
								<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
								године.
							</xsl:if>
							<xsl:if test="not($podaciOdgovora)">
								Одбија се, као неоснован, захтев АА, 
								за приступ информацијама од јавног значаја, поднет 
								<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
								године органу
								<xsl:value-of select="$organVlasti"></xsl:value-of>, 
								<xsl:value-of select="$sediste"></xsl:value-of>.
							</xsl:if>
						</fo:block>
					</xsl:if>
					
					<fo:block text-align="center">
						О б р а з л о ж е њ е
					</fo:block>
					
					<fo:block text-indent="40px">
						АА, као тражилац информација, изјавио је дана 
						<xsl:value-of select="concat($danZalbe, concat('.', concat($mesecZalbe, concat('.', concat($godinaZalbe, '.')))))"></xsl:value-of>					
						године жалбу Поверенику, 
							<xsl:if test="not($podaciOdgovora)">
							због непоступања 						
						</xsl:if>
							<xsl:if test="$podaciOdgovora">
							против одлуке
						</xsl:if>
						органа
						<xsl:value-of select="$organVlasti"></xsl:value-of>,
						<xsl:value-of select="$sediste"></xsl:value-of>,
						<xsl:if test="$podaciOdgovora">
							број: 
							<xsl:value-of select="$podaciOdgovora/resenje:brojOdgovora"></xsl:value-of> 
							од 
							<xsl:value-of select="concat($danOdgovora, concat('.', concat($mesecOdgovora, concat('.', concat($godinaOdgovora, '.')))))"></xsl:value-of>					
							године,
							због недобијања тражених информација
						</xsl:if>
						по његовом захтеву од 
						<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>										
							године за приступ информацијама од јавног значаја.
						<xsl:if test="not($podaciOdgovora)">
							Уз жалбу је приложена копија поднетог захтева са доказом о предаји органу власти.
						</xsl:if>
						<xsl:if test="$podaciOdgovora/resenje:tipOdgovora = 'obavestenje'">
							У жалби је навео да му предметним обавештењем орган власти није доставио тражене информације, 
							па предлаже да Повереник уважи његову жалбу и наложи да му се доставе тражене информације. 
							У прилогу је доставио копије захтева и ожалбеног обавештења.
						</xsl:if>
						<xsl:if test="$podaciOdgovora/resenje:tipOdgovora = 'odbijanje'">
							У жалби је навео да му је издатим решењем за одбијање захтева ускраћено уставно право 
							на информације од јавног значаја и зато захтева од Повереника да наложи доставу
							тражене информације. У прилогу је доставио копије захтева и издатог решења о одбијању захтева.
						</xsl:if>
					</fo:block>
					
					<fo:block text-indent="40px">
						<xsl:variable name="danSlanja" select="substring-after(substring-after(resenje:Odbrana/resenje:datumSlanja, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecSlanja" select="substring-before(substring-after(resenje:Odbrana/resenje:datumSlanja, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaSlanja" select="substring-before(resenje:Odbrana/resenje:datumSlanja, '-')"></xsl:variable>
						Поступајући по жалби, Повереник је дана 					
						<xsl:value-of select="concat($danSlanja, concat('.', concat($mesecSlanja, concat('.', concat($godinaSlanja, '.')))))"></xsl:value-of>					
						године упутио исту на изјашњење органу
						<xsl:value-of select="$organVlasti"></xsl:value-of>,
						<xsl:value-of select="$sediste"></xsl:value-of>,
						као органу власти у смислу члана 3. Закона о слободном приступу информацијама од 
						јавног значаја
						<xsl:if test="not($podaciOdgovora)">
							и затражио да се изјасни о наводима жалбе, посебно о разлозима непоступања у законском 
							року по поднетом захтеву у складу са одредбама члана 16. ст.1-9. или ст. 10. Закона, 
							остављајући рок од осам 
							дана</xsl:if><xsl:if test="resenje:Odbrana/resenje:odgovorOdbrane">.</xsl:if>	
	 					<xsl:if test="not(resenje:Odbrana/resenje:odgovorOdbrane)">
							, поводом чега није добио одговор.
						</xsl:if>																	
					</fo:block>
					
					<xsl:if test="resenje:Odbrana/resenje:odgovorOdbrane">.
						<fo:block text-indent="40px">
							<xsl:variable name="danOdgovora" select="substring-after(substring-after(resenje:Odbrana/resenje:datumOdgovora, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecOdgovora" select="substring-before(substring-after(resenje:Odbrana/resenje:datumOdgovora, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaOdgovora" select="substring-before(resenje:Odbrana/resenje:datumOdgovora, '-')"></xsl:variable>
							У одговору на жалбу од 
							<xsl:value-of select="concat($danOdgovora, concat('.', concat($mesecOdgovora, concat('.', concat($godinaOdgovora, '.')))))"></xsl:value-of>					
							године, орган власти се изјаснио следећим ставом:
							<xsl:value-of select="resenje:Odbrana/resenje:odgovorOdbrane"></xsl:value-of>.
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
						а односе се на: <xsl:value-of select="$podaciZahteva/osnova:Detalji"></xsl:value-of>			
					</fo:block>
				
					<xsl:if test="not($podaciOdgovora)">
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
					
					<xsl:if test="$podaciOdgovora">
						<fo:block text-indent="40px">
							Даље је, увидом у списе предмета утврђено да је одговором број: 
							<xsl:value-of select="$podaciOdgovora/resenje:brojOdgovora"></xsl:value-of> 
							од 
							<xsl:value-of select="concat($danOdgovora, concat('.', concat($mesecOdgovora, concat('.', concat($godinaOdgovora, '.')))))"></xsl:value-of>										
							године, орган власти обавестио жалиоца следеће:
							<xsl:value-of select="$podaciOdgovora/osnova:Detalji"></xsl:value-of>
						</fo:block>
						<fo:block text-indent="40px">
							Чланом 16. став 1. Закона о слободном приступу информацијама од јавног значаја 
							прописано је да је орган власти дужан да без одлагања, а најкасније у року од 
							15 дана од дана пријема захтева, тражиоца обавести о поседовању информације, 
							стави му на увид документ који садржи тражену информацију, односно изда му или упути 
							копију тог документа.
						</fo:block>
					</xsl:if>
															
					<xsl:for-each select="resenje:Odluka/resenje:Obrazlozenje/resenje:Pasus">
						<fo:block text-indent="40px">
				           <xsl:value-of select="text()"></xsl:value-of>
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
				
					<fo:block>
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
</xsl:stylesheet>
<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:dokument="https://github.com/draganagrbic998/xml/dokument">
	
    <xsl:template match="/">
        <fo:root font-family="Times New Roman" font-size="12px" linefeed-treatment="ignore" text-align="justify">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="resenje-page">
                    <fo:region-body margin="0.5in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="resenje-page">
                <fo:flow flow-name="xsl-region-body">
					<xsl:variable name="status" select="dokument:Resenje/dokument:status"></xsl:variable>
					<xsl:variable name="tipZalbe" select="dokument:Resenje/dokument:tipZalbe"></xsl:variable>
					<xsl:variable name="organVlasti" select="dokument:Resenje/osnova:OrganVlasti"></xsl:variable>
					<xsl:variable name="danOdluke" select="substring-after(substring-after(dokument:Resenje/dokument:datumOdluke, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecOdluke" select="substring-before(substring-after(dokument:Resenje/dokument:datumOdluke, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaOdluke" select="substring-before(dokument:Resenje/dokument:datumOdluke, '-')"></xsl:variable>
					<xsl:variable name="danZahteva" select="substring-after(substring-after(dokument:Resenje/dokument:datumZahteva, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecZahteva" select="substring-before(substring-after(dokument:Resenje/dokument:datumZahteva, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaZahteva" select="substring-before(dokument:Resenje/dokument:datumZahteva, '-')"></xsl:variable>
					<xsl:variable name="danZalbe" select="substring-after(substring-after(dokument:Resenje/dokument:datumZalbe, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecZalbe" select="substring-before(substring-after(dokument:Resenje/dokument:datumZalbe, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaZalbe" select="substring-before(dokument:Resenje/dokument:datumZalbe, '-')"></xsl:variable>
					<xsl:variable name="danSlanja" select="substring-after(substring-after(dokument:Resenje/dokument:Odbrana/dokument:datumSlanja, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecSlanja" select="substring-before(substring-after(dokument:Resenje/dokument:Odbrana/dokument:datumSlanja, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaSlanja" select="substring-before(dokument:Resenje/dokument:Odbrana/dokument:datumSlanja, '-')"></xsl:variable>
					<xsl:variable name="odbrana" select="dokument:Resenje/dokument:Odbrana"></xsl:variable>
					<xsl:variable name="danOdgovora" select="substring-after(substring-after(dokument:Resenje/dokument:Odbrana/dokument:datumOdgovora, '-'), '-')"></xsl:variable>
					<xsl:variable name="mesecOdgovora" select="substring-before(substring-after(dokument:Resenje/dokument:Odbrana/dokument:datumOdgovora, '-'), '-')"></xsl:variable>
					<xsl:variable name="godinaOdgovora" select="substring-before(dokument:Resenje/dokument:Odbrana/dokument:datumOdgovora, '-')"></xsl:variable>

					<fo:block>
						<fo:inline-container>
							<fo:block>
								
								Решење
								<xsl:if test="$status='odobrena'">
									када је жалба основана – налаже се:
								</xsl:if>
								<xsl:if test="$status='odbijena' and $tipZalbe='cutanje'">
									– одбија се као неоснована:
								</xsl:if>
								<xsl:if test="$status='odbijena' and $tipZalbe='odluka'">
									– одбија се захтев:
								</xsl:if>
								
							</fo:block>
						</fo:inline-container>
						<fo:inline-container>
							<fo:block>
							
							</fo:block>
						</fo:inline-container>
					</fo:block>
					<fo:block line-height="7px">
						<fo:inline-container inline-progression-dimension="50%">
							<fo:block>
								Бр. <xsl:value-of select="dokument:Resenje/osnova:broj"></xsl:value-of>
							</fo:block>
						</fo:inline-container>
						<fo:inline-container inline-progression-dimension="50%">
							<fo:block text-align="right">
								<xsl:variable name="dan" select="substring-after(substring-after(dokument:Resenje/osnova:datum, '-'), '-')"></xsl:variable>
								<xsl:variable name="mesec" select="substring-before(substring-after(dokument:Resenje/osnova:datum, '-'), '-')"></xsl:variable>
								<xsl:variable name="godina" select="substring-before(dokument:Resenje/osnova:datum, '-')"></xsl:variable>
								Датум: 
								<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
								године
							</fo:block>
						</fo:inline-container>
					</fo:block>

					<fo:block margin-top="10px">
						Повереник за информације од јавног значаја и заштиту података о личности, 
						у поступку по алби коју је изјавио AA, 
						
						<xsl:if test="$tipZalbe='cutanje'">
						због непоступања				
						</xsl:if>
						<xsl:if test="$tipZalbe='odluka'">
						против обавештења				
						</xsl:if>
						органа
						<xsl:value-of select="$organVlasti/osnova:naziv"></xsl:value-of>,
						<xsl:value-of select="$organVlasti/osnova:sediste"></xsl:value-of>, 
	
						<xsl:if test="$tipZalbe='odluka'">
							
						број: 
						<xsl:value-of select="dokument:Resenje/dokument:brojOdluke"></xsl:value-of> 
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
					
					<fo:block text-align="center" margin-top="10px">
						Р Е Ш Е Њ Е
					</fo:block>
					
					<xsl:if test="$status='odobrena'">
						<xsl:for-each select="dokument:Resenje/dokument:Dispozitiva/dokument:Pasus">
							<fo:block text-indent="40px">
									
					            <xsl:number format="I"></xsl:number>&#160;<xsl:value-of select="text()"></xsl:value-of>
							</fo:block >
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$status='odbijena'">
						<fo:block text-indent="40px">
							<xsl:if test="$tipZalbe='odluka'">
								Одбија се, као неоснована, жалба АА, 
								изјављена против обавештења органа 
								<xsl:value-of select="$organVlasti/osnova:naziv"></xsl:value-of>, 
								<xsl:value-of select="$organVlasti/osnova:sediste"></xsl:value-of>,
								број: 
								<xsl:value-of select="dokument:Resenje/dokument:brojOdluke"></xsl:value-of>
								од 
								<xsl:value-of select="concat($danOdluke, concat('.', concat($mesecOdluke, concat('.', concat($godinaOdluke, '.')))))"></xsl:value-of>					
								године,
								због недобијања тражених информација по његовом захтеву за приступ информацијама 
								од јавног значаја поднетом
								<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
								године.
							</xsl:if>
							<xsl:if test="$tipZalbe='cutanje'">
								Одбија се, као неоснован, захтев АА, 
								за приступ информацијама од јавног значаја, поднет 
								<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
								године органу
								<xsl:value-of select="$organVlasti/osnova:naziv"></xsl:value-of>, 
								<xsl:value-of select="$organVlasti/osnova:sediste"></xsl:value-of>.
							</xsl:if>
						</fo:block>
					</xsl:if>
					
					<fo:block text-align="center" margin-top="5px">
						О б р а з л о ж е њ е
					</fo:block>
					
					<fo:block text-indent="40px">
						АА, као тражилац информација, изјавио је дана 
						<xsl:value-of select="concat($danZalbe, concat('.', concat($mesecZalbe, concat('.', concat($godinaZalbe, '.')))))"></xsl:value-of>					
						године жалбу Поверенику, 
						<xsl:if test="$tipZalbe='cutanje'">
							због непоступања 						
						</xsl:if>
						<xsl:if test="$tipZalbe='odluka'">
							против обавештења
						</xsl:if>
						органа
						<xsl:value-of select="$organVlasti/osnova:naziv"></xsl:value-of>,
						<xsl:value-of select="$organVlasti/osnova:sediste"></xsl:value-of>,
						<xsl:if test="$tipZalbe='odluka'">
							број: 
							<xsl:value-of select="dokument:Resenje/dokument:brojOdluke"></xsl:value-of> 
							од 
							<xsl:value-of select="concat($danOdluke, concat('.', concat($mesecOdluke, concat('.', concat($godinaOdluke, '.')))))"></xsl:value-of>					
							године,
							због недобијања тражених информација
						</xsl:if>
						
						по његовом захтеву од 
						<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>										
						године за приступ информацијама од јавног значаја.
						<xsl:if test="$tipZalbe='cutanje'">
						Уз жалбу је приложена копија поднетог захтева са доказом о предаји органу власти.
						</xsl:if>
						<xsl:if test="$tipZalbe='odluka'">
							У жалби је навео да му предметним обавештењем орган власти није доставио тражене информације, 
							па предлаже да Повереник уважи његову жалбу и наложи да му се доставе тражене информације. 
							У прилогу је доставио копије захтева и ожалбеног обавештења.
						</xsl:if>
					
					</fo:block>
					
					<fo:block text-indent="40px">
					Поступајући по жалби, Повереник је дана 					
					<xsl:value-of select="concat($danSlanja, concat('.', concat($mesecSlanja, concat('.', concat($godinaSlanja, '.')))))"></xsl:value-of>					
					године упутио исту на изјашњење органу
					<xsl:value-of select="$organVlasti/osnova:naziv"></xsl:value-of>,
					<xsl:value-of select="$organVlasti/osnova:sediste"></xsl:value-of>,
					
					као органу власти у смислу члана 3. Закона о слободном приступу информацијама од 
					јавног значаја
					<xsl:if test="$tipZalbe='cutanje'">
						и затражио да се изјасни о наводима жалбе, посебно о разлозима непоступања у законском 
						року по поднетом захтеву у складу са одредбама члана 16. ст.1-9. или ст. 10. Закона, 
						остављајући рок од осам 
						дана</xsl:if><xsl:if test="$odbrana/dokument:odgovor">.</xsl:if>	
 					<xsl:if test="not($odbrana/dokument:odgovor)">
						, поводом чега није добио одговор.
					</xsl:if>																	
				</fo:block>
				<xsl:if test="$odbrana/dokument:odgovor">.
					<fo:block text-indent="40px">
						У одговору на жалбу од 
						<xsl:value-of select="concat($danOdgovora, concat('.', concat($mesecOdgovora, concat('.', concat($godinaOdgovora, '.')))))"></xsl:value-of>					
						године, 
						<xsl:value-of select="$odbrana/dokument:odgovor"></xsl:value-of>.
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
					<xsl:value-of select="$organVlasti/osnova:naziv"></xsl:value-of>,
					<xsl:value-of select="$organVlasti/osnova:sediste"></xsl:value-of>,					
					захтев за приступ информацијама од јавног значаја, електронском поштом, 
					којим је тражио информације од јавног значаја, 
					
					<xsl:if test="$status='odobrena'">
						ближе наведенe у ставу I диспозитива овог решења.					
					</xsl:if>
					<xsl:if test="$status='odbijena'">
						а односе се на: <xsl:value-of select="dokument:Resenje/dokument:detaljiZalbe"></xsl:value-of>			
					</xsl:if>
				</fo:block>
				
				<xsl:if test="$tipZalbe='cutanje'">
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
				
				<xsl:if test="$tipZalbe='odluka'">
					<fo:block text-indent="40px">
						Даље је, увидом у списе предмета утврђено да је одговором број: 
						<xsl:value-of select="dokument:Resenje/dokument:brojOdluke"></xsl:value-of> 
						од 
						<xsl:value-of select="concat($danOdluke, concat('.', concat($mesecOdluke, concat('.', concat($godinaOdluke, '.')))))"></xsl:value-of>										
						године, орган власти обавестио жалиоца следеће:
						<xsl:value-of select="dokument:Resenje/dokument:detaljiOdluke"></xsl:value-of>
					</fo:block>
				</xsl:if>
						
				<xsl:if test="$tipZalbe='odluka'">
				
					<fo:block text-indent="40px">
						Чланом 16. став 1. Закона о слободном приступу информацијама од јавног значаја 
						прописано је да је орган власти дужан да без одлагања, а најкасније у року од 
						15 дана од дана пријема захтева, тражиоца обавести о поседовању информације, 
						стави му на увид документ који садржи тражену информацију, односно изда му или упути 
						копију тог документа.
					</fo:block>
				
				</xsl:if>
				
				<xsl:if test="$status='odobrena'">
					<fo:block text-indent="40px">
						Имајући у виду да орган власти по захтеву жалиоца од 
						<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
						године није поступио у складу са наведеним одредбама Закона о слободном приступу информацијама 
						од јавног значаја, а да није оправдао разлоге непоступања по поднетом захтеву, 
						Повереник је у поступку по жалби, на основу члана 23. и члана 24. ст. 1. и 4. 
						Закона о слободном приступу информацијама од јавног значаја и члана 173. ст. 2. 
						Закона о општем управном поступку, одлучио као у ставу I диспозитива овог решења, 
						нашавши да је жалба основана, односно да је неспорно право жалиоца на тражене информације 
						у смислу члана 5. Закона о слободном приступу информацијама од јавног значаја, 
						по коме свако има право да му буде саопштено да ли орган власти поседује или му је 
						доступна одређена информација од јавног значаја, као и да му се информација, 
						уколико је у поседу органа, учини доступном, на начин како је то наложено 
						у ставу I диспозитива овог решења, што је у складу са одредбом члана 12. 
						Закона о слободном приступу информацијама од јавног значаја, која предвиђа могућност 
						издвајања тражене информације од јавног значаја од осталих информација садржаних 
						у документу у које орган власти није дужан да тражиоцу омогући увид, 
						јер би се доступношћу тим информацијама повредило право на приватност и заштиту 
						података о личности лица на која се тражене информације односе.
					</fo:block>
					<fo:block text-indent="40px">
						Орган <xsl:value-of select="$organVlasti/osnova:naziv"></xsl:value-of>,
						<xsl:value-of select="$organVlasti/osnova:sediste"></xsl:value-of>,					
						је дужан да о извршењу решења из става I диспозитива, обавести Повереника у складу са 
						чланом 24. став 3. Закона о слободном приступу информацијама од јавног значаја.
					</fo:block>			
					
				</xsl:if>
				
				<xsl:if test="$status='odbijena'">
					<xsl:for-each select="dokument:Resenje/dokument:Dispozitiva/dokument:Pasus">
						<fo:block text-indent="40px">
								
				           <xsl:value-of select="text()"></xsl:value-of>
						</fo:block >
					</xsl:for-each>
				</xsl:if>
				
				<xsl:if test="$status='odbijena' and $tipZalbe='odluka'">
					<fo:block text-indent="40px">
						Стога је Повереник у поступку по жалби, на основу члана 24. став 1. 
						Закона о слободном приступу информацијама од јавног значаја и 
						члана 170. став 1. тачка 1. Закона о општем управном поступку, а у вези са чланом 
						23. Закона о слободном приступу информацијама од јавног значаја, 
						одлучио као у диспозитиву овог решења.
					</fo:block>
				</xsl:if>	
				
				<xsl:if test="$status='odbijena' and $tipZalbe='cutanje'">
					<fo:block text-indent="40px">
						Како је орган власти пропустио да одлучи по поднетом захтеву, односно да донесе 
						решење и одбије поднети захтев, а није оправдао разлоге недоношења решења, 
						Повереник је, у поступку по жалби, на основу члана 23. и члана 24. ст. 1. 
						Закона о слободном приступу информацијама од јавног значаја и члана 173. ст. 2. 
						Закона о општем управном поступку одлучио као у диспозитиву овог решења, 
						односно одбио захтев жалиоца, као неоснован.					
					</fo:block>
				</xsl:if>	
				
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
						<fo:block text-align="right" linefeed-treatment="preserve">
						ПОВЕРЕНИК
						Милан Мариновић
						</fo:block>
					</fo:inline-container>
				</fo:block>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
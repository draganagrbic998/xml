<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:dokument="https://github.com/draganagrbic998/xml/dokument">

	<xsl:template match="/">
	
		<html>
			
			<head>
			</head>
			
			<body style="max-width: 600px; margin: auto; border: 1px solid black; padding: 50px; text-align: justify;">
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


				<div style="display: flex; flex-direction: row; justify-content: space-between;">
					
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
				</div>
				<div style="display: flex; flex-direction: row; justify-content: space-between;">
					<div>
						Бр. <xsl:value-of select="dokument:Resenje/osnova:broj"></xsl:value-of>
					</div>
					<div>
						<xsl:variable name="dan" select="substring-after(substring-after(dokument:Resenje/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesec" select="substring-before(substring-after(dokument:Resenje/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godina" select="substring-before(dokument:Resenje/osnova:datum, '-')"></xsl:variable>
						Датум: 
						<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
						године
					</div>
				</div>
				
				<p style="margin-bottom: 10px;">
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
				</p>
				
				<h3 style="text-align: center; font-weight: normal; margin-top: 10px; margin-bottom: 5px;">
					Р Е Ш Е Њ Е
				</h3>
				
				<xsl:if test="$status='odobrena'">
					<xsl:for-each select="dokument:Resenje/dokument:Dispozitiva/dokument:Pasus">
						<p style="word-break: break-all; text-indent: 40px; margin: 0;">
								
				            <xsl:number format="I"></xsl:number>&#160;
							<xsl:value-of select="text()"></xsl:value-of>
						</p>
					</xsl:for-each>
				</xsl:if>
				<xsl:if test="$status='odbijena'">
					<p style="text-indent: 40px; margin: 0;">
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
					</p>
				</xsl:if>
				
				<h4 style="text-align: center; font-weight: normal; margin-top: 10px; margin-bottom: 5px;">
					О б р а з л о ж е њ е
				</h4>
				
				<p style="text-indent: 40px; margin-top: 5px; margin-bottom: 0;">
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
				</p>
				
				<p style="margin: 0; text-indent: 40px;">
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
				</p>
				<xsl:if test="$odbrana/dokument:odgovor">
					<p style="text-indent: 40px; margin: 0;">
						У одговору на жалбу од 
						<xsl:value-of select="concat($danOdgovora, concat('.', concat($mesecOdgovora, concat('.', concat($godinaOdgovora, '.')))))"></xsl:value-of>					
						године, 
						<xsl:value-of select="$odbrana/dokument:odgovor"></xsl:value-of>.
					</p>					
				</xsl:if>					
				
				<p style="margin: 0; text-indent: 40px;">
					По разматрању жалбе и осталих списа овог предмета, донета је 
					одлука као у диспозитиву решења из следећих разлога:
				</p>
				
				<p style="margin: 0; text-indent: 40px;">
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
				</p>
				
				<xsl:if test="$tipZalbe='cutanje'">
					<p style="text-indent: 40px; margin: 0;">
						Такође је увидом у списе предмета утврђено да по поднетом захтеву жалиоца орган власти 
						није поступио, што је био дужан да учини без одлагања, а најкасније у року од 15 дана 
						од дана пријема захтева те да га, у смислу члана 16. став 1. 
						Закона о слободном приступу информацијама од јавног значаја, обавести да ли поседује 
						тражене информације и да му, уколико исте поседује, достави копије докумената 
						у којима су оне садржане или да, у супротном, донесе решење о одбијању захтева, 
						у смислу става 10. истог члана.
					</p>
				</xsl:if>
				
				<xsl:if test="$tipZalbe='odluka'">
					<p style="text-indent: 40px; margin: 0;">
						Даље је, увидом у списе предмета утврђено да је одговором број: 
						<xsl:value-of select="dokument:Resenje/dokument:brojOdluke"></xsl:value-of> 
						од 
						<xsl:value-of select="concat($danOdluke, concat('.', concat($mesecOdluke, concat('.', concat($godinaOdluke, '.')))))"></xsl:value-of>										
						године, орган власти обавестио жалиоца следеће:
						<xsl:value-of select="dokument:Resenje/dokument:detaljiOdluke"></xsl:value-of>
					</p>
				</xsl:if>
				
				<xsl:if test="$tipZalbe='odluka'">
				
					<p style="text-indent: 40px; margin: 0;">
						Чланом 16. став 1. Закона о слободном приступу информацијама од јавног значаја 
						прописано је да је орган власти дужан да без одлагања, а најкасније у року од 
						15 дана од дана пријема захтева, тражиоца обавести о поседовању информације, 
						стави му на увид документ који садржи тражену информацију, односно изда му или упути 
						копију тог документа.
					</p>
				
				</xsl:if>
				
				<xsl:if test="$status='odobrena'">
					<p style="text-indent: 40px; margin: 0;">
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
					</p>
					<p style="text-indent: 40px; margin: 0;">
						Орган <xsl:value-of select="$organVlasti/osnova:naziv"></xsl:value-of>,
						<xsl:value-of select="$organVlasti/osnova:sediste"></xsl:value-of>,					
						је дужан да о извршењу решења из става I диспозитива, обавести Повереника у складу са 
						чланом 24. став 3. Закона о слободном приступу информацијама од јавног значаја.
					</p>			
					
				</xsl:if>
				
				<xsl:if test="$status='odbijena'">
					<xsl:for-each select="dokument:Resenje/dokument:Dispozitiva/dokument:Pasus">
						<p style="word-break: break-all; text-indent: 40px; margin: 0;">
								
							<xsl:value-of select="text()"></xsl:value-of>
						</p>
					</xsl:for-each>
				</xsl:if>		
				
				<xsl:if test="$status='odbijena' and $tipZalbe='odluka'">
					<p style="text-indent: 40px; margin: 0;">
						Стога је Повереник у поступку по жалби, на основу члана 24. став 1. 
						Закона о слободном приступу информацијама од јавног значаја и 
						члана 170. став 1. тачка 1. Закона о општем управном поступку, а у вези са чланом 
						23. Закона о слободном приступу информацијама од јавног значаја, 
						одлучио као у диспозитиву овог решења.
					</p>
				</xsl:if>	
				
				<xsl:if test="$status='odbijena' and $tipZalbe='cutanje'">
					<p style="text-indent: 40px; margin: 0;">
						Како је орган власти пропустио да одлучи по поднетом захтеву, односно да донесе 
						решење и одбије поднети захтев, а није оправдао разлоге недоношења решења, 
						Повереник је, у поступку по жалби, на основу члана 23. и члана 24. ст. 1. 
						Закона о слободном приступу информацијама од јавног значаја и члана 173. ст. 2. 
						Закона о општем управном поступку одлучио као у диспозитиву овог решења, 
						односно одбио захтев жалиоца, као неоснован.					
					</p>
				</xsl:if>	
				
				<p style="text-indent: 40px; margin: 0;">
					Упутство о правном средству:
				</p>
				
				<p style="text-indent: 40px; margin: 0;">
					Против овог решења није допуштена жалба већ се, у складу са Законом о управним споровима, 
					може покренути управни спор тужбом Управном суду у Београду, у року од 30 дана 
					од дана пријема решења. Такса на тужбу износи 390,00 динара.
				</p>
				
				<div style="display: flex; flex-direction: row; justify-content: space-between; margin-top: 10px;">
					<span></span>
					<span style="text-align: right;">
						ПОВЕРЕНИК<br></br>
						Милан Мариновић
					</span>
				</div>
								
			</body>
		
		</html>
	
	</xsl:template>

</xsl:stylesheet>

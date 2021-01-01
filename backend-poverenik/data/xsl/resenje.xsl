<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:resenje="https://github.com/draganagrbic998/xml/resenje">

	<xsl:template match="/resenje:Resenje">
	
		<html>
			
			<head>
			
				<style>
					
					.root{
						max-width: 700px; 
						margin: auto; 
						border: 1px solid black; 
						padding: 50px;
						text-align: justify;
						font-family: serif;
						background-color: white;
						box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
					}
					.flex{
						display: flex; 
						flex-direction: row; 
						justify-content: space-between;
					}
					.center{
						text-align: center;
					}
					.indent{
						text-indent: 40px;
					}
				</style>
			
			</head>
			
			<body>
			
				<div class="root">
					
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
	
					<div class="flex">
						<div>
							Решење
							<xsl:if test="$status='odobrenо'">
								када је жалба основана – налаже се:
							</xsl:if>
							<xsl:if test="$status='odbijeno'">
								– одбија се жалба као неоснована:
							</xsl:if>
						</div>
						<div></div>
					</div>
					
					<div class="flex">
						<div>
							Бр. <xsl:value-of select="osnova:broj"></xsl:value-of>
						</div>
						<div>
							<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="godina" select="substring-before(osnova:datum, '-')"></xsl:variable>
							Датум: 
							<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
							године
						</div>
					</div>
					
					<p>
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
					</p>
					
					<p class="center">
						Р Е Ш Е Њ Е
					</p>
					
					<xsl:if test="$status='odobreno'">
						<xsl:for-each select="resenje:Odluka/resenje:Dispozitiva/resenje:Pasus">
							<p class="indent" style="word-break: break-all;">
					            <xsl:number format="I"></xsl:number>&#160;<xsl:value-of select="text()"></xsl:value-of>
							</p>
						</xsl:for-each>
					</xsl:if>
					
					<xsl:if test="$status='odbijeno'">
						<p class="indent">
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
						</p>
					</xsl:if>
					
					<p class="center">
						О б р а з л о ж е њ е
					</p>
					
					<p class="indent">
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
					</p>
					
					<p class="indent">
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
					</p>
					
						<xsl:if test="resenje:Odbrana/resenje:odgovorOdbrane">.
						<p class="indent">
							<xsl:variable name="danOdgovora" select="substring-after(substring-after(resenje:Odbrana/resenje:datumOdgovora, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecOdgovora" select="substring-before(substring-after(resenje:Odbrana/resenje:datumOdgovora, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaOdgovora" select="substring-before(resenje:Odbrana/resenje:datumOdgovora, '-')"></xsl:variable>
							У одговору на жалбу од 
							<xsl:value-of select="concat($danOdgovora, concat('.', concat($mesecOdgovora, concat('.', concat($godinaOdgovora, '.')))))"></xsl:value-of>					
							године, орган власти се изјаснио следећим ставом:
							<xsl:value-of select="resenje:Odbrana/resenje:odgovorOdbrane"></xsl:value-of>.
						</p>					
					</xsl:if>					
					
					<p class="indent">
						По разматрању жалбе и осталих списа овог предмета, донета је 
						одлука као у диспозитиву решења из следећих разлога:
					</p>
					
					<p class="indent">
						Увидом у списе предмета утврђено је да је АА, дана 
						<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
						године, поднео органу
						<xsl:value-of select="$organVlasti"></xsl:value-of>,
						<xsl:value-of select="$sediste"></xsl:value-of>,					
						захтев за приступ информацијама од јавног значаја, електронском поштом, 
						којим је тражио информације од јавног значаја, 
						а односе се на: <xsl:value-of select="$podaciZahteva/osnova:Detalji"></xsl:value-of>			
					</p>
					
					<xsl:if test="not($podaciOdgovora)">
						<p class="indent">
							Такође је увидом у списе предмета утврђено да по поднетом захтеву жалиоца орган власти 
							није поступио, што је био дужан да учини без одлагања, а најкасније у року од 15 дана 
							од дана пријема захтева те да га, у смислу члана 16. став 1. 
							Закона о слободном приступу информацијама од јавног значаја, обавести да ли поседује 
							тражене информације и да му, уколико исте поседује, достави копије докумената 
							у којима су оне садржане или да, у супротном, донесе решење о одбијању захтева, 
							у смислу става 10. истог члана.
						</p>
					</xsl:if>
					
					<xsl:if test="$podaciOdgovora">
						<p class="indent">
							Даље је, увидом у списе предмета утврђено да је одговором број: 
							<xsl:value-of select="$podaciOdgovora/resenje:brojOdgovora"></xsl:value-of> 
							од 
							<xsl:value-of select="concat($danOdgovora, concat('.', concat($mesecOdgovora, concat('.', concat($godinaOdgovora, '.')))))"></xsl:value-of>										
							године, орган власти обавестио жалиоца следеће:
							<xsl:value-of select="$podaciOdgovora/osnova:Detalji"></xsl:value-of>
						</p>
						<p class="indent">
							Чланом 16. став 1. Закона о слободном приступу информацијама од јавног значаја 
							прописано је да је орган власти дужан да без одлагања, а најкасније у року од 
							15 дана од дана пријема захтева, тражиоца обавести о поседовању информације, 
							стави му на увид документ који садржи тражену информацију, односно изда му или упути 
							копију тог документа.
						</p>
					</xsl:if>		
					
					<xsl:for-each select="resenje:Odluka/resenje:Obrazlozenje/resenje:Pasus">
						<p class="indent" style="word-break: break-all;">
							<xsl:value-of select="text()"></xsl:value-of>
						</p>
					</xsl:for-each>
									
					<p class="indent">
						Упутство о правном средству:
					</p>
					
					<p class="indent">
						Против овог решења није допуштена жалба већ се, у складу са Законом о управним споровима, 
						може покренути управни спор тужбом Управном суду у Београду, у року од 30 дана 
						од дана пријема решења. Такса на тужбу износи 390,00 динара.
					</p>
					
					<div class="flex">
						<div></div>
						<div style="text-align: right;">
							<p>
								ПОВЕРЕНИК
							</p>
							<p>
								<xsl:value-of select="concat(osnova:Osoba/osnova:ime, concat(' ', osnova:Osoba/osnova:prezime))"></xsl:value-of>
							</p>
						</div>
					</div>
					
					
				</div>
								
			</body>
		
		</html>
	
	</xsl:template>

</xsl:stylesheet>

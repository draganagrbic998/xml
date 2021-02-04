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
					p{
						margin: 0;
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
					<xsl:variable name="zalba_broj" select="substring-after(resenje:PodaciZalbe/resenje:datumZalbe/@href, 'https://github.com/draganagrbic998/xml/zalba/')"></xsl:variable>
	
					<xsl:variable name="zahtev_link" select="concat('http://localhost:4201/html/zahtevi/', $zahtev_broj)"></xsl:variable>
					<xsl:variable name="odluka_link" select="concat('http://localhost:4201/html/odluke/', $odluka_broj)"></xsl:variable>
					<xsl:variable name="zalba_link" select="concat('http://localhost:4201/html/zalbe/', $zalba_broj)"></xsl:variable>
					<xsl:variable name="odgovor_link" select="concat('http://localhost:4201/html/odgovori/', $zalba_broj)"></xsl:variable>

					<div class="flex">
						<div>
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
						</div>
						<div>&#160;</div>
					</div>
					
					<div class="flex">
						<div>						
							Бр. <xsl:value-of select="substring-after(@about, 'https://github.com/draganagrbic998/xml/resenje/')"></xsl:value-of>
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
					
					<br></br>
					
					<p>
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
					</p>
					
					<br></br>
					
					<p class="center">
						Р Е Ш Е Њ Е
					</p>
					
					<xsl:for-each select="resenje:Odluka/resenje:Dispozitiva/resenje:Pasus">
						<p class="indent" style="word-break: break-all;">
				            <xsl:number format="I"></xsl:number>&#160;<xsl:apply-templates></xsl:apply-templates>
						</p>
					</xsl:for-each>
					
					<xsl:if test="$status='odustato' or $status='obavesteno'">
						<p class="indent">
							Поништава се жалба коју је поднео АА дана
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>					
							против органа власти <xsl:value-of select="$organVlasti"></xsl:value-of>,
							<xsl:value-of select="$sediste"></xsl:value-of>.
						</p>
					</xsl:if>
					
					<xsl:if test="$status='odbijeno'">
						<p class="indent">
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
						</p>
					</xsl:if>
					
					<br></br>
					
					<p class="center">
						О б р а з л о ж е њ е
					</p>
					
					<p class="indent">
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
							Уз жалбу је приложена <a href="{$zahtev_link}">копија поднетог захтева</a> са доказом о предаји органу власти.
						</xsl:if>
						<xsl:if test="$podaciZalbe/resenje:tipZalbe = 'delimicnost'">
							У жалби је навео да му предметним обавештењем орган власти није доставио тражене информације, 
							па предлаже да Повереник уважи његову жалбу и наложи да му се доставе тражене информације. 
							У прилогу је доставио <a href="{$zahtev_link}">копије захтева</a> и <a href="{$odluka_link}">ожалбеног обавештења</a>.
						</xsl:if>
						<xsl:if test="$podaciZalbe/resenje:tipZalbe = 'odluka'">
							У жалби је навео да му је издатим решењем за одбијање захтева ускраћено уставно право 
							на информације од јавног значаја и зато захтева од Повереника да наложи доставу
							тражене информације. У прилогу је доставио <a href="{$zahtev_link}">копије захтева</a> и <a href="{$odluka_link}">издатог решења о одбијању захтева</a>.
						</xsl:if>
					</p>
					
					<xsl:if test="$podaciZalbe/resenje:datumProsledjivanja">
						<p class="indent">
							<xsl:variable name="danProsledjivanja" select="substring-after(substring-after($podaciZalbe/resenje:datumProsledjivanja, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesecProsledjivanja" select="substring-before(substring-after($podaciZalbe/resenje:datumProsledjivanja, '-'), '-')"></xsl:variable>
							<xsl:variable name="godinaProsledjivanja" select="substring-before($podaciZalbe/resenje:datumProsledjivanja, '-')"></xsl:variable>
							Поступајући по жалби, Повереник је дана 					
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
								дана</xsl:if>
		 					<xsl:if test="not(resenje:Odbrana/osnova:Detalji)">
								, поводом чега није добио одговор.
							</xsl:if>	
							<xsl:if test="resenje:Odbrana/osnova:Detalji">.
								<p class="indent">
									<xsl:variable name="danOdbrane" select="substring-after(substring-after(resenje:Odbrana/resenje:datumOdbrane, '-'), '-')"></xsl:variable>
									<xsl:variable name="mesecOdbrane" select="substring-before(substring-after(resenje:Odbrana/resenje:datumOdbrane, '-'), '-')"></xsl:variable>
									<xsl:variable name="godinaOdbrane" select="substring-before(resenje:Odbrana/resenje:datumOdbrane, '-')"></xsl:variable>
									У <a href="{$odgovor_link}">одговору на жалбу</a> од 
									<xsl:value-of select="concat($danOdbrane, concat('.', concat($mesecOdbrane, concat('.', concat($godinaOdbrane, '.')))))"></xsl:value-of>					
									године, орган власти се изјаснио следећим ставом:
									<xsl:apply-templates select="resenje:Odbrana/osnova:Detalji"></xsl:apply-templates>.
								</p>					
							</xsl:if>					
						</p>
						
					</xsl:if>
										
					<p class="indent">
						По разматрању <a href="{$zalba_link}">жалбe</a> и осталих списа овог предмета, донета је 
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
						а односе се на: <xsl:apply-templates select="$podaciZahteva/osnova:Detalji"></xsl:apply-templates>			
					</p>
					
					<xsl:if test="not($podaciOdluke)">
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
					
					<xsl:if test="$podaciOdluke">
						<p class="indent">
							Даље је, увидом у списе предмета утврђено да је одговором број: 
							<xsl:value-of select="$odluka_broj"></xsl:value-of> 
							од 
							<xsl:value-of select="concat($danOdluke, concat('.', concat($mesecOdluke, concat('.', concat($godinaOdluke, '.')))))"></xsl:value-of>										
							године, орган власти обавестио жалиоца следеће:
							<xsl:apply-templates select="$podaciOdluke/osnova:Detalji"></xsl:apply-templates>
						</p>
						<p class="indent">
							Чланом 16. став 1. Закона о слободном приступу информацијама од јавног значаја 
							прописано је да је орган власти дужан да без одлагања, а најкасније у року од 
							15 дана од дана пријема захтева, тражиоца обавести о поседовању информације, 
							стави му на увид документ који садржи тражену информацију, односно изда му или упути 
							копију тог документа.
						</p>
					</xsl:if>		
					
					<xsl:if test="$status='odustato' or $status='obavesteno'">
						<xsl:variable name="danOtkazivanja" select="substring-after(substring-after($podaciZalbe/resenje:datumOtkazivanja, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecOtkazivanja" select="substring-before(substring-after($podaciZalbe/resenje:datumOtkazivanja, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaOtkazivanja" select="substring-before($podaciZalbe/resenje:datumOtkazivanja, '-')"></xsl:variable>						
						<p class="indent">
							<b>Жалбени поступак се поништава</b> из разлога што је
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
						</p>
					</xsl:if>
					
					<xsl:for-each select="resenje:Odluka/resenje:Obrazlozenje/resenje:Pasus">
						<p class="indent" style="word-break: break-all;">
							<xsl:apply-templates></xsl:apply-templates>
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
					
					<br></br>
					
					<div class="flex">
						<div>&#160;</div>
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
	
    <xsl:template match="osnova:bold">
        <b><xsl:apply-templates select="@*|node()"></xsl:apply-templates></b>
    </xsl:template>
    
    <xsl:template match="osnova:italic">
        <i><xsl:apply-templates select="@*|node()"></xsl:apply-templates></i>
    </xsl:template>
    
    <xsl:template match="resenje:zakon">
        <u><xsl:apply-templates select="@*|node()"></xsl:apply-templates></u>
    </xsl:template>

</xsl:stylesheet>

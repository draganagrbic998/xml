<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:izvestaj="https://github.com/draganagrbic998/xml/izvestaj">
	
    <xsl:template match="/izvestaj:Izvestaj">
        <fo:root font-size="12px" text-align="justify" font-family="Times New Roman">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="izvestaj-page">
                    <fo:region-body margin="0.5in"></fo:region-body>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="izvestaj-page">
                <fo:flow flow-name="xsl-region-body">
                
					<xsl:variable name="datum" select="osnova:datum"></xsl:variable>
					<xsl:variable name="godina" select="izvestaj:godina"></xsl:variable>

					<xsl:variable name="brojZahteva" select="izvestaj:brojZahteva"></xsl:variable>
					<xsl:variable name="brojZahtevaObavestenje" select="izvestaj:brojZahtevaObavestenje"></xsl:variable>
					<xsl:variable name="brojZahtevaUvid" select="izvestaj:brojZahtevaUvid"></xsl:variable>
					<xsl:variable name="brojZahtevaKopija" select="izvestaj:brojZahtevaKopija"></xsl:variable>
					<xsl:variable name="brojZahtevaDostava" select="izvestaj:brojZahtevaDostava"></xsl:variable>
					<xsl:variable name="brojZahtevaPosta" select="izvestaj:brojZahtevaPosta"></xsl:variable>
					<xsl:variable name="brojZahtevaEmail" select="izvestaj:brojZahtevaEmail"></xsl:variable>
					<xsl:variable name="brojZahtevaFaks" select="izvestaj:brojZahtevaFaks"></xsl:variable>
					<xsl:variable name="brojZahtevaOstalo" select="izvestaj:brojZahtevaOstalo"></xsl:variable>

					<xsl:variable name="brojOdluka" select="izvestaj:brojOdluka"></xsl:variable>
					<xsl:variable name="brojOdlukaOdobreno" select="izvestaj:brojOdlukaOdobreno"></xsl:variable>
					<xsl:variable name="brojOdlukaOdbijeno" select="izvestaj:brojOdlukaOdbijeno"></xsl:variable>

					<xsl:variable name="brojZalbi" select="izvestaj:brojZalbi"></xsl:variable>
					<xsl:variable name="brojZalbiCutanje" select="izvestaj:brojZalbiCutanje"></xsl:variable>
					<xsl:variable name="brojZalbiDelimicnost" select="izvestaj:brojZalbiDelimicnost"></xsl:variable>
					<xsl:variable name="brojZalbiOdluka" select="izvestaj:brojZalbiOdluka"></xsl:variable>
										
					<fo:block text-align="center">
               			
               			<fo:inline-container inline-progression-dimension="40%">
               				
               				<fo:block>
         				         <fo:inline-container inline-progression-dimension="40%">
               						<fo:block text-align="left">Број предмета: </fo:block>
               						<fo:block text-align="left">Датум: </fo:block>
	               				</fo:inline-container>
	               				<fo:inline-container inline-progression-dimension="60%">
               						<fo:block border-bottom="0.2mm solid black">
										<xsl:value-of select="osnova:broj"></xsl:value-of>
               						</fo:block>
               						<fo:block border-bottom="0.2mm solid black">
										<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
										<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
										<xsl:variable name="godina" select="substring-before(osnova:datum, '-')"></xsl:variable>
										<xsl:value-of select="concat($dan, concat('.', concat($mesec, concat('.', concat($godina, '.')))))"></xsl:value-of>
               						</fo:block>
	               				</fo:inline-container>               				
               				</fo:block>
							                			
               			</fo:inline-container>
                			
   		                <fo:inline-container inline-progression-dimension="60%">
               				<fo:block></fo:block>  			
               			</fo:inline-container>
                		
               		</fo:block>				
               		
               		<fo:block>
               			&#160;
               		</fo:block>
               		
               		<fo:block text-align="center" font-weight="bold" font-size="16px">
               			ПРИМЕНА ЗАКОНА О СЛОБОДНОМ ПРИСТУПУ
               		</fo:block>

					<fo:block text-align="center" font-weight="bold" font-size="16px">
               			ИНФОРМАЦИЈАМА ОД ЈАВНОГ ЗНАЧАЈА У <xsl:value-of select="$godina"></xsl:value-of>. ГОД.
					</fo:block>
               		
               		<fo:block>
               			&#160;
               		</fo:block>
               		<fo:block>
               			&#160;
               		</fo:block>
               		
          			<fo:block text-align="center" font-weight="bold" font-size="14px">
						1. ЗАХТЕВИ
					</fo:block>

               		<fo:block>
               			&#160;
               		</fo:block>

					<fo:block text-align="center" display-align="center">
					<fo:inline-container inline-progression-dimension="91%">
						<fo:table>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-header>
								<fo:table-row>
									<fo:table-cell border="0.3mm solid black"><fo:block>Тип захтева</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>обавештење</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>увид</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>копија</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>достава</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>Укупно</fo:block></fo:table-cell>
							  	</fo:table-row>
							</fo:table-header>
							<fo:table-body>
							  	<fo:table-row>
							    	<fo:table-cell border="0.3mm solid black"><fo:block>Број</fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZahtevaObavestenje"></xsl:value-of></fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZahtevaUvid"></xsl:value-of></fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZahtevaKopija"></xsl:value-of></fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZahtevaDostava"></xsl:value-of></fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZahteva"></xsl:value-of></fo:block></fo:table-cell>
							  	</fo:table-row>
							  	<fo:table-row>
	  						    	<fo:table-cell border="0.3mm solid black"><fo:block>%</fo:block></fo:table-cell>
									<xsl:choose>
										<xsl:when test="$brojZahteva = 0">
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
										</xsl:when>
										<xsl:otherwise>
									    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="format-number(xs:decimal($brojZahtevaObavestenje div $brojZahteva * 100), '####0.00')"></xsl:value-of></fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="format-number(xs:decimal($brojZahtevaUvid div $brojZahteva * 100), '####0.00')"></xsl:value-of></fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="format-number(xs:decimal($brojZahtevaKopija div $brojZahteva * 100), '####0.00')"></xsl:value-of></fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="format-number(xs:decimal($brojZahtevaDostava div $brojZahteva * 100), '####0.00')"></xsl:value-of></fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>100</fo:block></fo:table-cell>
										</xsl:otherwise>
								</xsl:choose>
							  	</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:inline-container>
					</fo:block>
								
               		<fo:block>
               			&#160;
               		</fo:block>
               		<fo:block>
               			&#160;
               		</fo:block>
               		
          			<fo:block text-align="center" font-weight="bold" font-size="14px">
						2. ПОДАЦИ О ДОСТАВАМА
					</fo:block>

               		<fo:block>
               			&#160;
               		</fo:block>
               		
					<fo:block text-align="center">
					<fo:inline-container inline-progression-dimension="76%">
						<fo:table>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-header>
								<fo:table-row>
									<fo:table-cell border="0.3mm solid black"><fo:block>Тип доставе</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>Пошта</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>Емаил</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>Факс</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>Остало</fo:block></fo:table-cell>
							  	</fo:table-row>
							</fo:table-header>
							<fo:table-body>
							  	<fo:table-row>
							    	<fo:table-cell border="0.3mm solid black"><fo:block>Број</fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZahtevaPosta"></xsl:value-of></fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZahtevaEmail"></xsl:value-of></fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZahtevaFaks"></xsl:value-of></fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZahtevaOstalo"></xsl:value-of></fo:block></fo:table-cell>
							  	</fo:table-row>
							  	<fo:table-row>
	  						    	<fo:table-cell border="0.3mm solid black"><fo:block>%</fo:block></fo:table-cell>
									<xsl:choose>
										<xsl:when test="$brojZahtevaDostava = 0">
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
										</xsl:when>
										<xsl:otherwise>
									    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="format-number(xs:decimal($brojZahtevaPosta div $brojZahtevaDostava * 100), '####0.00')"></xsl:value-of></fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="format-number(xs:decimal($brojZahtevaEmail div $brojZahtevaDostava * 100), '####0.00')"></xsl:value-of></fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="format-number(xs:decimal($brojZahtevaFaks div $brojZahtevaDostava * 100), '####0.00')"></xsl:value-of></fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="format-number(xs:decimal($brojZahtevaOstalo div $brojZahtevaDostava * 100), '####0.00')"></xsl:value-of></fo:block></fo:table-cell>
										</xsl:otherwise>
								</xsl:choose>
							  	</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:inline-container>
					</fo:block>
					
               		<fo:block>
               			&#160;
               		</fo:block>
               		<fo:block>
               			&#160;
               		</fo:block>
               		
          			<fo:block text-align="center" font-weight="bold" font-size="14px">
						3. ЖАЛБЕ
					</fo:block>

               		<fo:block>
               			&#160;
               		</fo:block>
					
					<fo:block margin="auto" text-align="center" display-align="center">
					<fo:inline-container inline-progression-dimension="76%">
						<fo:table>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-column column-width="27mm"/>
							<fo:table-header>
								<fo:table-row>
									<fo:table-cell border="0.3mm solid black"><fo:block>Тип жалбе</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>Ћутање</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>Делимичност</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>Одбијање</fo:block></fo:table-cell>
									<fo:table-cell border="0.3mm solid black"><fo:block>Укупно</fo:block></fo:table-cell>
							  	</fo:table-row>
							</fo:table-header>
							<fo:table-body>
							  	<fo:table-row>
							    	<fo:table-cell border="0.3mm solid black"><fo:block>Број</fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZalbiCutanje"></xsl:value-of></fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZalbiDelimicnost"></xsl:value-of></fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZalbiOdluka"></xsl:value-of></fo:block></fo:table-cell>
							    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="$brojZalbi"></xsl:value-of></fo:block></fo:table-cell>
							  	</fo:table-row>
							  	<fo:table-row>
	  						    	<fo:table-cell border="0.3mm solid black"><fo:block>%</fo:block></fo:table-cell>
									<xsl:choose>
										<xsl:when test="$brojZalbi = 0">
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>/</fo:block></fo:table-cell>
										</xsl:when>
										<xsl:otherwise>
									    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="format-number(xs:decimal($brojZalbiCutanje div $brojZalbi * 100), '####0.00')"></xsl:value-of></fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="format-number(xs:decimal($brojZalbiDelimicnost div $brojZalbi * 100), '####0.00')"></xsl:value-of></fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block><xsl:value-of select="format-number(xs:decimal($brojZalbiOdluka div $brojZalbi * 100), '####0.00')"></xsl:value-of></fo:block></fo:table-cell>
									    	<fo:table-cell border="0.3mm solid black"><fo:block>100</fo:block></fo:table-cell>
										</xsl:otherwise>
								</xsl:choose>
							  	</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:inline-container>
					</fo:block>
					
               		<fo:block>
               			&#160;
               		</fo:block>
               		<fo:block>
               			&#160;
               		</fo:block>
               		
          			<fo:block text-align="center" font-weight="bold" font-size="14px">
						4. РЕШЕЊА О ЗАХТЕВИМА
					</fo:block>

               		<fo:block>
               			&#160;
               		</fo:block>
	               		
					<fo:block text-indent="40px">
						Укупан број <fo:inline font-weight="bold">решења о захтевима</fo:inline>, којима је захтев усвојен или одбијен је 
						<fo:inline font-weight="bold"><xsl:value-of select="$brojOdluka"></xsl:value-of></fo:inline>, што представља 
						<fo:inline font-weight="bold"><xsl:value-of select="format-number(xs:decimal($brojOdluka div $brojZahteva * 100), '####0.00')"></xsl:value-of>%</fo:inline>
						од укупног броја поднетих захтева.
					</fo:block>
					
					<fo:block text-indent="40px">
						Од тога, <fo:inline font-weight="bold">усвојено</fo:inline>, односно делимично усвојених захтева је 
						<fo:inline font-weight="bold"><xsl:value-of select="$brojOdlukaOdobreno"></xsl:value-of></fo:inline> 
						(<xsl:value-of select="format-number(xs:decimal($brojOdlukaOdobreno div $brojOdluka * 100), '####0.00')"></xsl:value-of>%),
						док је <fo:inline font-weight="bold">одбијених</fo:inline> захтева 
						<fo:inline font-weight="bold"><xsl:value-of select="$brojOdlukaOdbijeno"></xsl:value-of></fo:inline> 
						(<xsl:value-of select="format-number(xs:decimal($brojOdlukaOdbijeno div $brojOdluka * 100), '####0.00')"></xsl:value-of>%).
					</fo:block>

               		<fo:block>
               			&#160;
               		</fo:block>
               		<fo:block>
               			&#160;
               		</fo:block>
               		
					<fo:block>
						<fo:inline-container inline-progression-dimension="30%">
							<fo:block>
								Достављено:
							</fo:block>
							<fo:block>
								1.&#160;&#160;&#160;Поверенику 								
							</fo:block>
							<fo:block>
								2.&#160;&#160;&#160;Архиви
							</fo:block>
						</fo:inline-container>
						<fo:inline-container inline-progression-dimension="70%">
							<fo:block>
								&#160;
							</fo:block>
							<fo:block margin-left="30px">
								(М.П.)
							</fo:block>
							<fo:block>
								&#160;
							</fo:block>
							<fo:block text-align="center" border-bottom="0.2mm solid black" margin-left="100px" margin-right="20px">
								&#160;
							</fo:block>
							<fo:block text-align="right">
								(потпис овлашћеног лица, односно руководиоца органа)
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
    
</xsl:stylesheet>
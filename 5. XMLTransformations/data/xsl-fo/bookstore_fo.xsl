<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:b="http://www.ftn.uns.ac.rs/xpath/examples" 
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="bookstore-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="bookstore-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-family="sans-serif" font-size="24px" font-weight="bold" padding="10px">
                        Bookstore (XSL-FO)
                    </fo:block>
                    <fo:block text-indent="10px">
                        Total number of books in the bookstore: 
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="count(b:bookstore/b:book)"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="20px" font-weight="bold" padding="10px">
                        Available books:
                    </fo:block>
                    <fo:block text-indent="10px">
                        Highlighting (*) the book titles with a  
                        <fo:inline font-weight="bold">price less than $40</fo:inline>.
                    </fo:block>
                    <fo:block>
                        <fo:table font-family="serif" margin="50px auto 50px auto" border="1px">
                            <fo:table-column column-width="5%"/>
                            <fo:table-column column-width="40%"/>
                            <fo:table-column column-width="30%"/>
                            <fo:table-column column-width="10%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <fo:block>#</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <fo:block>Title</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <fo:block>Author</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <fo:block>Year</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <fo:block>Price</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:variable name="amount" select="sum(//b:book/b:price)"/>
                                <xsl:variable name="count" select="count(//b:book)"/>
                                <xsl:for-each select="b:bookstore/b:book">
                                    <xsl:sort select="@category"/>
                                    <xsl:sort select="b:price"/>
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell padding="10px">
                                            <fo:block>
                                                <xsl:value-of select="position()" />
                                            </fo:block>
                                        </fo:table-cell>
                                        
                                        <fo:table-cell padding="10px">
                                            <fo:block font-weight="bold">
                                                <xsl:choose>
                                                    <xsl:when test="b:price &lt; 40">
                                                        * <xsl:value-of select="b:title"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="b:title"/>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                                <fo:inline vertical-align="super" font-size="50%">
                                                    <xsl:value-of select="@category"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        
                                        <fo:table-cell padding="10px">
                                            <xsl:if test="count(b:author) = 1">
                                                <fo:block>- <xsl:value-of select="b:author"/></fo:block>
                                            </xsl:if>
                                            <xsl:if test="count(b:author) &gt; 1">
                                                <xsl:for-each select="b:author">
                                                    <fo:block>- <xsl:value-of select="text()"/></fo:block>
                                                </xsl:for-each>
                                            </xsl:if>
                                        </fo:table-cell>
                                        
                                        <fo:table-cell padding="10px">
                                            <fo:block>
                                                <xsl:value-of select="b:year"/>        
                                            </fo:block>
                                        </fo:table-cell>
                                        
                                        <fo:table-cell padding="10px">
                                            <fo:block>
                                                ($<xsl:value-of select="b:price"/>)
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                                <fo:table-row>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" font-size="16px" number-columns-spanned="4" padding="10px">
                                        <fo:block>Average price:</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" font-size="16px" padding="10px">
                                        <fo:block>$<xsl:value-of select="round($amount div $count * 100) div 100"/></fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>

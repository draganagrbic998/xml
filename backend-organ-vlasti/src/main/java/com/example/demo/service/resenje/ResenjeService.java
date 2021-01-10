package com.example.demo.service.resenje;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.xml.ResenjeExist;

@Service
public class ResenjeService {
	
	@Autowired
	private ResenjeExist resenjeExist;

	@Autowired
	private ResenjeMapper resenjeMapper;
	
	@Autowired
	private DOMParser domParser;

	@Autowired
	private XSLTransformer xslTransformer;

	private static final String XSL_PATH = Constants.XSL_FOLDER + "resenje.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "resenje_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "resenja" + File.separatorChar;
	
	public String retrieve() {
		//zabrani gradjaninu poziv ove metode
		ResourceSet resouces = this.resenjeExist.retrieve("/resenje:Resenje");
		return this.resenjeMapper.map(resouces);		
	}
	
	public String generateHtml(String broj) {
		Document document = this.resenjeExist.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), XSL_PATH);
		return out.toString();
	}
	
	public Resource generatePdf(String broj) {
		try {
			Document document = this.resenjeExist.load(broj);
			ByteArrayOutputStream out = this.xslTransformer.generatePdf(this.domParser.buildXml(document), XSL_FO_PATH);
			Path file = Paths.get(GEN_PATH + broj + ".pdf");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}

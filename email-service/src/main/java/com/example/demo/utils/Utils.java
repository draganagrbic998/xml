package com.example.demo.utils;

import java.io.File;
import java.util.Arrays;

public class Utils {
	
	public static final String GEN_FOLDER = "data" + File.separatorChar + "gen";
	
	public static String nextDocumentId() {
		return GEN_FOLDER + File.separatorChar + (Arrays.asList(new File(GEN_FOLDER).list()).stream().mapToInt(str -> Integer.parseInt(str.split("\\.")[0])).max().orElse(0) + 1) + "";
	}

}

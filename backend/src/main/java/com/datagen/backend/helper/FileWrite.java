package com.datagen.backend.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWrite {
	
	static String generatedDir = "src/main/resources/generated/";
	
	public static void clean(String location){
		try {
			Files.walk(Paths.get(location))
			.filter(Files::isRegularFile)
			.map(Path::toFile)
			.forEach(File::delete);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writer(String value, String location, String fileName){
		try {
			value_writer(value,location,fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void value_writer(String value, String location,String fileName ) throws IOException{
		Path path = Paths.get(location+fileName);
		if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
			Files.createFile(path);
		}
		try (
				FileWriter writer = new FileWriter(path.toFile(),true);
				PrintWriter printWriter = new PrintWriter(writer)
				) {
			printWriter.println(value);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	

}

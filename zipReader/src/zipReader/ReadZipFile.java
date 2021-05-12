package zipReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;



public class ReadZipFile {
	public static void main(String[] args) throws Exception  {
		String s = args[0];
		//var modifyAPI = "A + C:\\Users\\Akshay kumar\\Desktop\\javaTestingSample-123456\\exe.txt";
		var modifyAPI = "D - exe.txt";
		String[] arrayOfstr = modifyAPI.split(" ");
	
		var zipFilePath = "C:\\Users\\Akshay kumar\\Desktop\\javaTestingSample-123456.zip";
		var newFilePath = "C:\\Users\\Akshay kumar\\Desktop\\javaTestingSample-123456\\exe.txt";
		
		var version = getVersion(zipFilePath);
		System.out.println(version);
		var relativePath = "exe.txt";
		
		//updateZipFile(arrayOfstr, zipFilePath);
		readZipFile(zipFilePath, relativePath);
		
		//deleteFile(zipFilePath, relativePath);
		
		//addNewFile(zipFilePath, newFilePath);
		
		System.out.println(s);
	}

	

	private static void addNewFile(String zipFilePath, String newFilePath) throws Exception{
		Map<String, String> zipProperties = new HashMap<>();
		zipProperties.put("create", "false");
		zipProperties.put("encoding", "UTF-8");
		
		File zipFile = new File(zipFilePath);
		if(!zipFile.exists()) {
			System.out.println("no file exist");
			return;
		}
		
		
		try(FileSystem zipFileSystem = FileSystems.newFileSystem(zipFile.toPath(), zipProperties)) {
			
			Path destination = zipFileSystem.getPath("exe.txt");
			Path NewFile = Paths.get(newFilePath);
			Files.copy(NewFile, destination);
			System.out.println("File Added!! ");
		}
			
		
	}



	private static void updateZipFile(String[] arrayOfstr, String ZipPath) throws Exception {
		switch(arrayOfstr[0]) {
		case "D":
			if(arrayOfstr[1].charAt(0) == '-'){
				modifyZipFile(ZipPath, arrayOfstr[2]);
			}
			break;
		case "A":
			addNewFile(ZipPath, arrayOfstr[2]);
			break;
		default:
			System.out.println("nothing happened");
		}
		
	}



	private static void readZipFile(String zipFilePath, String relativePath) throws Exception {
		try {
	            ZipFile zipFile = new ZipFile(zipFilePath);
	 
	            Enumeration<? extends ZipEntry> entries = zipFile.entries();
	 
	            while (entries.hasMoreElements()) {
	                ZipEntry entry = entries.nextElement();
	                String name = entry.getName();
	                
	                System.out.println(name);
	                
	                
	            }
	 
	            zipFile.close();
	        } catch (IOException ex) {
	            System.err.println(ex);
	        }
	}
	
	private static void modifyZipFile(String zipFilePath, String str) throws Exception {
		try {
	            ZipFile zipFile = new ZipFile(zipFilePath);
	 
	            Enumeration<? extends ZipEntry> entries = zipFile.entries();
	 
	            while (entries.hasMoreElements()) {
	                ZipEntry entry = entries.nextElement();
	                String name = entry.getName();
	                if(name.contains(str)) {
	                	deleteFile(zipFilePath, str);
	                }
	                
	                
	            }
	 
	            zipFile.close();
	        } catch (IOException ex) {
	            System.err.println(ex);
	        }
	}

	private static void deleteFile(String zipFilePath, String relativePath) throws Exception {
		Map<String, String> zipProperties = new HashMap<>();
		zipProperties.put("create", "false");
		zipProperties.put("encoding", "UTF-8");
		
		File zipFile = new File(zipFilePath);
		if(!zipFile.exists()) {
			System.out.println("no file exist");
			return;
		}
		
		
		try(FileSystem zipFileSystem = FileSystems.newFileSystem(zipFile.toPath(), zipProperties)) {
			
			Path fileInZip = zipFileSystem.getPath(relativePath);
			Files.deleteIfExists(fileInZip);
			System.out.println("file Deleted");
		}
	}



	private static String getVersion(String path) {
		int endIndex = path.indexOf(".zip");
		int startIndex = endIndex;
		while(path.charAt(startIndex) != '-') {
			startIndex--;
		}
		return path.substring(startIndex+1,endIndex);
		
	}
}

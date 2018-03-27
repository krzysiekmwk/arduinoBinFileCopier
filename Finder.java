package binCopier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.*;

public class Finder {
	public static void main(String[] args) {
		String dataFolder = System.getenv("LOCALAPPDATA");
		String tmpFolder = dataFolder + "\\Temp\\";
		
		File dir = new File(tmpFolder);

		File[] findArduinoBuildDir = dir.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name)
			{
				return name.startsWith("arduino_b");
			}
		});
		
		if(findArduinoBuildDir.length > 0){
			System.out.println(findArduinoBuildDir[0].toString());
			
			File[] findBinFile = findArduinoBuildDir[0].listFiles(new FilenameFilter(){
				public boolean accept(File dir, String name){
					return name.endsWith(".bin");
				}
			});
			
			if(findBinFile.length > 0){
				System.out.println(findBinFile[0].toString());
				
				String userDir = System.getProperty("user.dir");
				String userFile = userDir += "\\file.bin";
				
				File fileInUserDir = new File(userFile);
				System.out.println(fileInUserDir.toString());
				
				/*try {
					copyFile(findBinFile[0], fileInUserDir);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
				try {
					Files.copy(findBinFile[0].toPath(), fileInUserDir.toPath(), REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;
	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();

	        // previous code: destination.transferFrom(source, 0, source.size());
	        // to avoid infinite loops, should be:
	        long count = 0;
	        long size = source.size();              
	        while((count += destination.transferFrom(source, count, size-count))<size);
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
}

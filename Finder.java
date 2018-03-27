import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
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
				
				try {
					Files.copy(findBinFile[0].toPath(), fileInUserDir.toPath(), REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
}

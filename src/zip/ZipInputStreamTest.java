package zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import common.CommonUtil;

/**
 * Zip解压缩
 * @author xugc
 *
 */
public class ZipInputStreamTest {
	
	public static void main(String[] args) throws Exception
	{
		String zipfilepath = CommonUtil.scan(".zip文件路径:");
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipfilepath));
		ZipEntry ze = null;
		int b=0;
		while((ze=zipInputStream.getNextEntry())!=null){
			if(ze.isDirectory()){
				new File(ze.getName()).mkdir();
			}else {
				FileOutputStream fos = new FileOutputStream(ze.getName());
				while((b=zipInputStream.read())!=-1){
					fos.write(b);
				}
				zipInputStream.closeEntry();
				fos.flush();
				fos.close();
			}
			
		}
	}
}

package zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

import common.CommonUtil;

/**GZip压缩流
 * @author xugc
 *
 */
public class GZIPInputStreamTest {

	public static void main(String[] args) {
		try {
			String zipfilepath = CommonUtil.scan(".gz文件路径:");
			File zipfile = new File(zipfilepath);
			FileInputStream fis = new FileInputStream(zipfile);
			GZIPInputStream gzipIs = new GZIPInputStream(fis);
			String outfilePath=zipfile.getPath().substring(0, zipfile.getPath().lastIndexOf("."));
			FileOutputStream fos = new FileOutputStream(outfilePath);
			int b=0;
			while((b=gzipIs.read())!=-1){
				fos.write(b);
			}
			gzipIs.close();
			fos.flush();
			fos.close();
			System.out.println("解压后文件路径:"+outfilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

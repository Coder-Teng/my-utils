package com.mine.utils;

import java.io.*;
import java.util.List;

/**
 * @author Teng
 * @create 2019-12-11
 */
public class FileUtil {
    public static final String defaultFilePath = "C:\\mylog.txt";

    /**
     * 遍历文件夹下所有文件
     * @param filePath 文件夹路径
     * @param fileList  文件列表 List<File>
     */
    public static void getAllFile(String filePath, List<File> fileList) {
        File f = new File(filePath);
        if (f.exists() && f.isDirectory()) {
            for (File file : f.listFiles()) {
                getAllFile(file.getAbsolutePath(), fileList);
            }
        }
        if (f.exists() && f.isFile()) {
            fileList.add(f);
        }
    }



    /**
     * 写文件（不添加换行，附加模式）
     *
     * @param filePath 文件路径（若为空则默认为c:\mylog.txt
     * @param content  写入内容
     */
    public static void write2File(String filePath , String content) {
        if (filePath == null || "".equals ( filePath )) {
            filePath = defaultFilePath;
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter ( new FileWriter ( new File ( filePath ) , true ) );
            bw.write ( content );
            bw.flush ( );
        } catch ( IOException e ) {
            e.printStackTrace ( );
        } finally {
            if (bw != null) {
                try {
                    bw.close ( );
                } catch ( IOException e ) {
                    e.printStackTrace ( );
                }
            }
        }
    }

    /**
     * 读取文件内容
     * @param filePath  文件路径
     * @return 文件内容
     */
    public static StringBuffer readFromFile(String filePath) {
        StringBuffer ret = new StringBuffer ( );
        BufferedReader br = null;
        try {
            br = new BufferedReader ( new FileReader ( new File ( filePath ) ) );
            String line = null;
            while ((line = br.readLine ( )) != null) {
                ret.append ( line );
                ret.append ( "\r\n" );
            }
        } catch ( IOException e ) {
            e.printStackTrace ( );
        } finally {
            if (br != null) {
                try {
                    br.close ( );
                } catch ( IOException e ) {
                    e.printStackTrace ( );
                }
            }
        }
        return ret;
    }
}

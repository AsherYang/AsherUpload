package com.asher.yang.upload.bussiness;

import it.sauronsoftware.ftp4j.FTPFile;

import java.util.Comparator;

/**
 * Created by AsherYang on 2016/12/5.
 * email: ouyangfan1991@gmail.com
 */
public class FileComparator implements Comparator<FTPFile> {

    @Override
    public int compare(FTPFile f1, FTPFile f2) {
        return f1.getModifiedDate().compareTo(f2.getModifiedDate());
    }
}

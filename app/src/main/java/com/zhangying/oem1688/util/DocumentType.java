package com.zhangying.oem1688.util;

import android.text.TextUtils;

public enum  DocumentType {
    // 文本文件
    txt("txt"),
    // pdf文件
    pdf("pdf"),
    // doc文件
    doc("doc", "dot", "docx", "dotx", "docm", "dotm"),
    // ppt文件
    ppt("ppt", "pot", "pps", "ppa", "pptx", "ppsx", "potx", "ppam", "pptm", "potm", "ppsm"),
    // excel文件
    xls("xls", "xlt", "xla", "xlsx", "xltx", "xlsm", "xltm", "xlam", "xlsb");

    private String[] suffixArray;            // 文件后缀名

    DocumentType(String... suffix) {
        this.suffixArray = suffix;
    }

    public String[] getSuffix() {
        return suffixArray;
    }

    // 根据后缀名获取到对应的文件类型
    public static DocumentType getBySuffix(String suffix) {
        if (TextUtils.isEmpty(suffix)) return null;
        DocumentType fileType = null;
        // 统一采用小写字母执行操作
        switch (suffix.toLowerCase()) {
            case "txt":
                fileType = txt;
                break;
            case "pdf":
                fileType = pdf;
                break;
            case "doc":
            case "dot":
            case "docx":
            case "dotx":
            case "docm":
            case "dotm":
                fileType = doc;
                break;
            case "ppt":
            case "pot":
            case "pps":
            case "ppa":
            case "pptx":
            case "ppsx":
            case "potx":
            case "ppam":
            case "pptm":
            case "potm":
            case "ppsm":
                fileType = ppt;
                break;
            case "xls":
            case "xlt":
            case "xla":
            case "xlsx":
            case "xltx":
            case "xlsm":
            case "xltm":
            case "xlam":
            case "xlsb":
                fileType = xls;
                break;
        }
        return fileType;
    }

}

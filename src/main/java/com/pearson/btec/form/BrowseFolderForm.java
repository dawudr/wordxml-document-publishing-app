package com.pearson.btec.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dawud on 14/09/14.
 * BrowserFolder Form
 */
public class BrowseFolderForm {

    // Hashmap key
    public static final String KEY_WORD_DOC = "Word";
    public static final String KEY_OPEN_XML = "OpenXml";
    public static final String KEY_IQS_XML = "IQSXml";

    // Document File extensions
    public static final String FILE_EXT_OPEN_XML = ".xml";
    public static final String FILE_EXT_IQS_XML = "-iqs.xml";


    List browseList = new ArrayList();
    // HashMap containing Keys "Word", "OpenXml", "IQSXml
    HashMap fileNamesMap = new HashMap();

    public List getBrowseList() {
        return browseList;
    }

    public void setBrowseList(List browseList) {
        this.browseList = browseList;
    }

    public HashMap getFileNamesMap() {
        return fileNamesMap;
    }

    public void setFileNamesMap(HashMap fileNamesMap) {
        this.fileNamesMap = fileNamesMap;
    }
}

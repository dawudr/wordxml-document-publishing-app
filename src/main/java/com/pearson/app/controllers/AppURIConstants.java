package com.pearson.app.controllers;

import java.io.File;

/**
 * Created by dawud on 16/02/2015.
 */
public class AppURIConstants {

    // Transformation Script path and upload folder paths
    //TODO: Move to properties files
    public static String TRANSFORM_OUTPUT_FOLDER = File.separator + "uploads" + File.separator;
    public static String TRANSFORM_SCRIPTS_XSLT_TAGGEDWORDOPENXML_PQSXML_FILE_PATH = File.separator + "resources" + File.separator +
            "xsl" + File.separator + "iqs" + File.separator + "wordxml_unit.xsl";
    public static String TRANSFORM_SCRIPTS_XQUERY_WORD_TO_TAGGEDWORDOPENXML_FILE_PATH = System.getProperty("user.dir") + File.separator +
            "src" + File.separator + "main" + File.separator + "webapp" +File.separator + "resources" + File.separator +
            "xsl" + File.separator + "openxml" + File.separator + "wordxml_unit2_table.xquery";




}

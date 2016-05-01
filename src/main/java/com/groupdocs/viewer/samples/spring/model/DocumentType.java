package com.groupdocs.viewer.samples.spring.model;

import java.util.HashMap;

/**
 * The type Document type.
 */
public class DocumentType {
    /**
     * The constant WORDS.
     */
    public static final String WORDS = "Words";
    /**
     * The constant PDF.
     */
    public static final String PDF = "Pdf";
    /**
     * The constant SLIDES.
     */
    public static final String SLIDES = "Slides";
    /**
     * The constant CELLS.
     */
    public static final String CELLS = "Cells";
    /**
     * The constant IMAGE.
     */
    public static final String IMAGE = "Image";
    /**
     * The constant EMAIL.
     */
    public static final String EMAIL = "Email";
    /**
     * The constant DIAGRAM.
     */
    public static final String DIAGRAM = "Diagram";
    /**
     * The constant PROJECT.
     */
    public static final String PROJECT = "Project";
    /**
     * The constant UNKNOWN.
     */
    public static final String UNKNOWN = "Unknown";

    /**
     * Gets document type.
     * @param fileType the file type
     * @return the document type
     */
    public static String getDocumentType(FileType fileType) {
        return getDocumentTypeMap().get(fileType);
    }

    private static HashMap<FileType, String> getDocumentTypeMap() {
        final HashMap<FileType, String> documentTypeMap = new HashMap<FileType, String>();
        documentTypeMap.put(FileType.DOC, WORDS);
        documentTypeMap.put(FileType.DOCX, WORDS);
        documentTypeMap.put(FileType.DOT, WORDS);
        documentTypeMap.put(FileType.DOCM, WORDS);
        documentTypeMap.put(FileType.DOTM, WORDS);
        documentTypeMap.put(FileType.DOTX, WORDS);
        documentTypeMap.put(FileType.TXT, WORDS);
        documentTypeMap.put(FileType.ODT, WORDS);
        documentTypeMap.put(FileType.OTT, WORDS);
        documentTypeMap.put(FileType.RTF, WORDS);
        documentTypeMap.put(FileType.XML, WORDS);
        documentTypeMap.put(FileType.HTML, WORDS);
        documentTypeMap.put(FileType.HTM, WORDS);
        documentTypeMap.put(FileType.XHTML, WORDS);
        documentTypeMap.put(FileType.MHTML, WORDS);
        documentTypeMap.put(FileType.CSV, WORDS);

        documentTypeMap.put(FileType.PDF, PDF);
        documentTypeMap.put(FileType.PCL, PDF);
        documentTypeMap.put(FileType.XPS, PDF);
        documentTypeMap.put(FileType.EPUB, PDF);

        documentTypeMap.put(FileType.PPT, SLIDES);
        documentTypeMap.put(FileType.PPTX, SLIDES);
        documentTypeMap.put(FileType.PPS, SLIDES);
        documentTypeMap.put(FileType.PPSX, SLIDES);
        documentTypeMap.put(FileType.ODP, SLIDES);

        documentTypeMap.put(FileType.XLS, CELLS);
        documentTypeMap.put(FileType.XLSX, CELLS);
        documentTypeMap.put(FileType.XLSM, CELLS);
        documentTypeMap.put(FileType.XLTX, CELLS);
        documentTypeMap.put(FileType.XLTM, CELLS);
        documentTypeMap.put(FileType.XLSB, CELLS);
        documentTypeMap.put(FileType.ODS, CELLS);

        documentTypeMap.put(FileType.PNG, IMAGE);
        documentTypeMap.put(FileType.JPG, IMAGE);
        documentTypeMap.put(FileType.GIF, IMAGE);
        documentTypeMap.put(FileType.JPEG, IMAGE);
        documentTypeMap.put(FileType.BMP, IMAGE);
        documentTypeMap.put(FileType.TIFF, IMAGE);
        documentTypeMap.put(FileType.TIF, IMAGE);
        documentTypeMap.put(FileType.ICO, IMAGE);
        documentTypeMap.put(FileType.DWG, IMAGE);
        documentTypeMap.put(FileType.PSD, IMAGE);
        documentTypeMap.put(FileType.JPE, IMAGE);

        documentTypeMap.put(FileType.MSG, EMAIL);
        documentTypeMap.put(FileType.MHT, EMAIL);
        documentTypeMap.put(FileType.EML, EMAIL);
        documentTypeMap.put(FileType.OST, EMAIL);
        documentTypeMap.put(FileType.PST, EMAIL);
        documentTypeMap.put(FileType.EMLX, EMAIL);
        documentTypeMap.put(FileType.TNEF, EMAIL);

        documentTypeMap.put(FileType.VSD, DIAGRAM);
        documentTypeMap.put(FileType.VSS, DIAGRAM);
        documentTypeMap.put(FileType.VTX, DIAGRAM);
        documentTypeMap.put(FileType.VDX, DIAGRAM);
        documentTypeMap.put(FileType.VDW, DIAGRAM);
        documentTypeMap.put(FileType.VST, DIAGRAM);
        documentTypeMap.put(FileType.VSX, DIAGRAM);
        documentTypeMap.put(FileType.VSDX, DIAGRAM);
        documentTypeMap.put(FileType.VSDM, DIAGRAM);
        documentTypeMap.put(FileType.UML, DIAGRAM);

        documentTypeMap.put(FileType.PROJECT, PROJECT);
        documentTypeMap.put(FileType.MPP, PROJECT);
        documentTypeMap.put(FileType.MPT, PROJECT);

        documentTypeMap.put(FileType.UNKNOWN, UNKNOWN);

        return documentTypeMap;
    }
}
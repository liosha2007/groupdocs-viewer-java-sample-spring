package com.groupdocs.viewer.samples.spring.model;

/**
 * The enum File type.
 */
public enum FileType {
    /**
     * Doc file type.
     */
    DOC("Doc"),
    /**
     * Docx file type.
     */
    DOCX("Docx"),
    /**
     * Dot file type.
     */
    DOT("Dot"),
    /**
     * Docm file type.
     */
    DOCM("Docm"),
    /**
     * Dotm file type.
     */
    DOTM("Dotm"),
    /**
     * Dotx file type.
     */
    DOTX("Dotx"),
    /**
     * Txt file type.
     */
    TXT("Txt"),
    /**
     * Odt file type.
     */
    ODT("Odt"),
    /**
     * Ott file type.
     */
    OTT("Ott"),
    /**
     * Rtf file type.
     */
    RTF("Rtf"),
    /**
     * Xml file type.
     */
    XML("Xml"),
    /**
     * Html file type.
     */
    HTML("Html"),
    /**
     * Htm file type.
     */
    HTM("Htm"),
    /**
     * Xhtml file type.
     */
    XHTML("Xhtml"),
    /**
     * Mhtml file type.
     */
    MHTML("Mhtml"),
    /**
     * Csv file type.
     */
    CSV("Csv"),

    /**
     * Pdf file type.
     */
    PDF("Pdf"),
    /**
     * Pcl file type.
     */
    PCL("Pcl"),
    /**
     * Xps file type.
     */
    XPS("Xps"),
    /**
     * Epub file type.
     */
    EPUB("Epub"),

    /**
     * Ppt file type.
     */
    PPT("Ppt"),
    /**
     * Pptx file type.
     */
    PPTX("Pptx"),
    /**
     * Pps file type.
     */
    PPS("Pps"),
    /**
     * Ppsx file type.
     */
    PPSX("Ppsx"),
    /**
     * Odp file type.
     */
    ODP("Odp"),

    /**
     * Xls file type.
     */
    XLS("Xls"),
    /**
     * Xlsx file type.
     */
    XLSX("Xlsx"),
    /**
     * Xlsm file type.
     */
    XLSM("Xlsm"),
    /**
     * Xltx file type.
     */
    XLTX("Xltx"),
    /**
     * Xltm file type.
     */
    XLTM("Xltm"),
    /**
     * Xlsb file type.
     */
    XLSB("Xlsb"),
    /**
     * Ods file type.
     */
    ODS("Ods"),

    /**
     * Png file type.
     */
    PNG("Png"),
    /**
     * Jpg file type.
     */
    JPG("Jpg"),
    /**
     * Gif file type.
     */
    GIF("Gif"),
    /**
     * Jpeg file type.
     */
    JPEG("Jpeg"),
    /**
     * Bmp file type.
     */
    BMP("Bmp"),
    /**
     * Tiff file type.
     */
    TIFF("Tiff"),
    /**
     * Tif file type.
     */
    TIF("Tif"),
    /**
     * Ico file type.
     */
    ICO("Ico"),
    /**
     * Dwg file type.
     */
    DWG("Dwg"),
    /**
     * Psd file type.
     */
    PSD("Psd"),
    /**
     * Jpe file type.
     */
    JPE("Jpe"),

    /**
     * Msg file type.
     */
    MSG("Msg"),
    /**
     * Mht file type.
     */
    MHT("Mht"),
    /**
     * Eml file type.
     */
    EML("Eml"),
    /**
     * Ost file type.
     */
    OST("Ost"),
    /**
     * Pst file type.
     */
    PST("Pst"),
    /**
     * Emlx file type.
     */
    EMLX("Emlx"),
    /**
     * Tnef file type.
     */
    TNEF("Tnef"),

    /**
     * Vsd file type.
     */
    VSD("Vsd"),
    /**
     * Vss file type.
     */
    VSS("Vss"),
    /**
     * Vtx file type.
     */
    VTX("Vtx"),
    /**
     * Vdx file type.
     */
    VDX("Vdx"),
    /**
     * Vdw file type.
     */
    VDW("Vdw"),
    /**
     * Vst file type.
     */
    VST("Vst"),
    /**
     * Vsx file type.
     */
    VSX("Vsx"),
    /**
     * Vsdx file type.
     */
    VSDX("Vsdx"),
    /**
     * Vsdm file type.
     */
    VSDM("Vsdm"),
    /**
     * Uml file type.
     */
    UML("Uml"),

    /**
     * Project file type.
     */
    PROJECT("Project"),
    /**
     * Mpp file type.
     */
    MPP("Mpp"),
    /**
     * Mpt file type.
     */
    MPT("Mpt"),

    /**
     * Directory file type.
     */
    DIRECTORY("[DIRECTORY_TYPE]"),

    /**
     * Unknown file type.
     */
    UNKNOWN("Unknown");

    private final String name;

    private FileType(String name) {
        this.name = name;
    }

    /**
     * To string string.
     * @return the string
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Gets value.
     * @param key the key
     * @return the value
     */
    public static FileType getValue(String key) {
        try {
            return valueOf(key.toUpperCase());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * From file name file type.
     * @param fileDisplayName the file display name
     * @return the file type
     */
    public static FileType fromFileName(String fileDisplayName) {
        if (!fileDisplayName.contains(".")) {
            return DIRECTORY;
        }
        final String substring = fileDisplayName.substring(fileDisplayName.lastIndexOf(".") + 1);
        return getValue(substring.toUpperCase());
    }
}

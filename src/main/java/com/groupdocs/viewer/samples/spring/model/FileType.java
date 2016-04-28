package com.groupdocs.viewer.samples.spring.model;

public enum FileType {
    DOC("Doc"),
    DOCX("Docx"),
    DOT("Dot"),
    DOCM("Docm"),
    DOTM("Dotm"),
    DOTX("Dotx"),
    TXT("Txt"),
    ODT("Odt"),
    OTT("Ott"),
    RTF("Rtf"),
    XML("Xml"),
    HTML("Html"),
    HTM("Htm"),
    XHTML("Xhtml"),
    MHTML("Mhtml"),
    CSV("Csv"),
    
    PDF("Pdf"),
    PCL("Pcl"),
    XPS("Xps"),
    EPUB("Epub"),
    
    PPT("Ppt"),
    PPTX("Pptx"),
    PPS("Pps"),
    PPSX("Ppsx"),
    ODP("Odp"),
    
    XLS("Xls"),
    XLSX("Xlsx"),
    XLSM("Xlsm"),
    XLTX("Xltx"),
    XLTM("Xltm"),
    XLSB("Xlsb"),
    ODS("Ods"),
    
    PNG("Png"),
    JPG("Jpg"),
    GIF("Gif"),
    JPEG("Jpeg"),
    BMP("Bmp"),
    TIFF("Tiff"),
    TIF("Tif"),
    ICO("Ico"),
    DWG("Dwg"),
    PSD("Psd"),
    JPE("Jpe"),

    MSG("Msg"),
    MHT("Mht"),
    EML("Eml"),  
    OST("Ost"),
    PST("Pst"),
    EMLX("Emlx"),
    TNEF("Tnef"),
    
    VSD("Vsd"),
    VSS("Vss"),
    VTX("Vtx"),
    VDX("Vdx"),
    VDW("Vdw"),
    VST("Vst"),
    VSX("Vsx"),
    VSDX("Vsdx"),
    VSDM("Vsdm"),
    UML("Uml"),
    
    PROJECT("Project"),
    MPP("Mpp"),
    MPT("Mpt"),
    
    DIRECTORY("[DIRECTORY_TYPE]"),
    
    UNKNOWN("Unknown");

    private final String name;

    private FileType(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
    
    public static FileType getValue(String key){
        try{
            return valueOf(key.toUpperCase());
        }catch(Exception ex){
            return null;
        }
    }

    public static FileType fromFileName(String fileDisplayName) {
        if (!fileDisplayName.contains(".")) {
            return DIRECTORY;
        }
        final String substring = fileDisplayName.substring(fileDisplayName.lastIndexOf("."));
        return getValue(substring.toUpperCase());
    }
}

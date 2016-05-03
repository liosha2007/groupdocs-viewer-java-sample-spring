<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <title>GroupDocs.Viewer for Java v3.0.0 Spring Sample</title>
        <meta charset="utf-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <script type="text/javascript" src="${BaseUrl}assets/js/libs/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="${BaseUrl}assets/js/libs/jquery-ui-1.10.3.min.js"></script>
        <script type="text/javascript" src="${BaseUrl}assets/js/libs/knockout-3.2.0.js"></script>
        <script type="text/javascript" src="${BaseUrl}assets/js/libs/turn.min.js"></script>
        <script type="text/javascript" src="${BaseUrl}assets/js/libs/modernizr.2.6.2.Transform2d.min.js"></script>
        <script type="text/javascript">
            if (!window.Modernizr.csstransforms){
                var scriptLoad = document.createElement('script');
                scriptLoad.setAttribute("type","text/javascript");
                scriptLoad.setAttribute("src", '${BaseUrl}assets/js/libs/turn.html4.min.js');
                document.getElementsByTagName("head")[0].appendChild(scriptLoad);
            }
        </script>
        <script type="text/javascript" src="${BaseUrl}assets/js/installableViewer.min.js"></script>
        <script type="text/javascript">$.ui.groupdocsViewer.prototype.applicationPath = '${BaseUrl}';</script>
        <script type="text/javascript">$.ui.groupdocsViewer.prototype.useHttpHandlers = true;</script>
        <script type="text/javascript" src="${BaseUrl}assets/js/GroupdocsViewer.all.min.js"></script>
        <script type="text/javascript" src="${BaseUrl}assets/js/libs/jquery.ui.touch-punch.js"></script>

        <link rel="stylesheet" type="text/css" href="${BaseUrl}assets/css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="${BaseUrl}assets/css/GroupdocsViewer.all.min.css"/>
        <link rel="stylesheet" type="text/css" href="${BaseUrl}assets/css/popup.css"/>
        <link rel="stylesheet" type="text/css" href="${BaseUrl}assets/css/jquery-ui-1.10.3.dialog.min.css"/>
        <!--[if IE]>
        <link rel="stylesheet" type="text/css" href="${BaseUrl}assets/css/fixes-ie.css"/>
        <![endif]-->
        <link rel="stylesheet" type="text/css" href="${BaseUrl}assets/css/fixes.css"/>
    </head>
    <body>
        <h1>GroupDocs.Viewer for Java v3.0.0 Spring Sample</h1>
        <div>
            <form enctype="multipart/form-data" method="POST" id="uploadForm" action="UploadFile">
                <input type="file" id="fileUpload" name="fileName"/>
                <input type="submit" id="submitUpload" value="Upload"/>
            </form>
        </div>
        <div id="test"></div>
        <script>
            $(function () {
                var thumbsImageBase64Encoded = null;
                $('#test').groupdocsViewer({
                    backgroundColor: '',
                    convertWordDocumentsCompletely: false,
                    currentSearchHighlightColor: '',
                    downloadPdfFile: false,
                    enableStandardErrorHandling: true,
                    fileDisplayName: '',
                    filePath: ('${FilePath}'.length > 0) ? '${FilePath}' : null,
                    height: 650,
                    ignoreDocumentAbsence: false,
                    initialZoom: 100,
                    jqueryFileDownloadCookieName: '',
                    loadAllPagesOnSearch: false,
                    localizedStrings: null,
                    minimumImageWidth: 0,
                    onlyShrinkLargePages: true,
                    openThumbnails: true,
                    preloadPagesCount: 0,
                    preloadPagesOnBrowserSide: false,
                    preventTouchEventsBubbling: false,
                    printWithWatermark: false,
                    quality: 100,
                    searchForSeparateWords: false,
                    searchHighlightColor: '',
                    showDownload: true,
                    showDownloadErrorsInPopup: false,
                    showFolderBrowser: true,
                    showHeader: true,
                    showImageWidth: false,
                    showOnePageInRow: false,
                    showPaging: true,
                    showPrint: true,
                    showSearch: true,
                    showThumbnails: true,
                    showViewerStyleControl: true,
                    showZoom: true,
                    supportPageReordering: false,
                    supportPageRotation: true,
                    useVirtualScrolling: false,
                    supportTextSelection: true,
                    thumbnailsContainerBackgroundColor: '',
                    thumbnailsContainerBorderRightColor: '',
                    thumbnailsContainerWidth: 0,
                    thumbsImageBase64Encoded: thumbsImageBase64Encoded,
                    toolbarBorderBottomColor: '',
                    toolbarButtonBorderColor: '',
                    toolbarButtonBorderHoverColor: '',
                    toolbarButtonsBoxShadowHoverStyle: '',
                    toolbarButtonsBoxShadowStyle: '',
                    toolbarInputFieldBorderColor: '',
                    treatPhrasesInDoubleQuotesAsExactPhrases: false,
                    useAccentInsensitiveSearch: false,
                    useEmScaling: false,
                    useHtmlBasedEngine: ${useHtmlBasedEngine},
                    useHtmlThumbnails: false,
                    useImageBasedPrinting: true,
                    useInnerThumbnails: true,
                    usePdfPrinting: false,
                    usePngImagesForHtmlBasedEngine: true,
                    useRtl: false,
                    viewerStyle: 1,
                    watermarkColor: '',
                    watermarkFontSize: 0,
                    watermarkPosition: '',
                    watermarkText: '',
                    width: 0,
                    zoomToFitHeight: false,
                    zoomToFitWidth: true
                });
            });
        </script>
    </body>
</html>

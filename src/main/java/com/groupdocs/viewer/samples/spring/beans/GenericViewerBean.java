package com.groupdocs.viewer.samples.spring.beans;

import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.domain.containers.FileContainer;
import com.groupdocs.viewer.domain.containers.FileTreeContainer;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.domain.options.FileTreeOptions;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.samples.spring.config.ViewerConfig;
import com.groupdocs.viewer.samples.spring.model.request.GetImageUrlsRequest;
import com.groupdocs.viewer.samples.spring.model.request.ViewDocumentRequest;
import com.groupdocs.viewer.samples.spring.model.response.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * The type Generic viewer bean.
 * @author Aleksey Permyakov (01.05.2016).
 */
public abstract class GenericViewerBean {
    /**
     * The constant UPLOAD_DIRECTORY_NAME.
     */
    public static final String UPLOAD_DIRECTORY_NAME = "upload";

    /**
     * The Viewer config.
     */
    protected final ViewerConfig viewerConfig;

    /**
     * Instantiates a new Generic viewer bean.
     * @param viewerConfig the viewer config
     */
    public GenericViewerBean(ViewerConfig viewerConfig) {
        this.viewerConfig = viewerConfig;
    }

    /**
     * Load directory structure as file tree
     * @param path the path
     * @return the load file browser tree data response
     */
    public LoadFileBrowserTreeDataResponse loadFileTree(final String path) {
        // Create/initialize image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);

        // Load file tree list for custom path
        FileTreeOptions options = new FileTreeOptions(path);

        // Load file tree list for ViewerConfig.StoragePath
        FileTreeContainer container = imageHandler.loadFileTree(options);

        final List<FileDescription> fileTree = container.getFileTree();
        final LoadFileBrowserTreeDataResponse response = new LoadFileBrowserTreeDataResponse();
        final int[] count = {1};
        for (final FileDescription fileDescription : fileTree) {
            response.getNodes().add(new FileBrowserTreeDataNode() {{
                setGuid(fileDescription.getGuid());
                setDocType(fileDescription.isDirectory() ? null : fileDescription.getDocumentType());
                setExt(fileDescription.isDirectory() ? "[DIRECTORY_TYPE]" : fileDescription.getExtension().toLowerCase());
                setFileType(fileDescription.isDirectory() ? "[DIRECTORY_TYPE]" : fileDescription.getFileType());
                setModifyTime(fileDescription.getLastModificationDate().getTime());
                setName(fileDescription.getName());
                setPath(path == null || path.isEmpty() ? fileDescription.getName() : path + "/" + fileDescription.getName());
                setIsKnown(false);
                setIsShared(false);
                setType(fileDescription.isDirectory() ? "folder" : "file");
                setSize(fileDescription.getSize());
                setId(count[0]++);
            }});
        }
        response.setCount(fileTree.size());
        return response;
    }

    /**
     * Render document view document response.
     * @param request the request
     * @return the view document response
     */
    public abstract ViewDocumentResponse renderDocument(ViewDocumentRequest request, String baseUrl);

    /**
     * Render document image byte [ ].
     * @param path      the path
     * @param width     the width
     * @param pageIndex the page index
     * @return the byte [ ]
     */
    public byte[] renderDocumentImage(String path, Integer width, Integer pageIndex) {
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);

        //Initialize ImageOptions Object
        ImageOptions options = new ImageOptions();
        if (width != null) {
            options.setWidth((int) (width * 1.7));
            options.setHeight(width * 2);
        }
        options.setPageNumber(pageIndex + 1);
        options.setCountPagesToConvert(1);

        //Get document pages in image form
        synchronized (GenericViewerBean.class) {
            List<PageImage> images = imageHandler.getPages(path, options);
            try {
                if (images.size() == 1) {
                    return StreamUtils.copyToByteArray(images.get(0).getStream());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Read document data byte [ ].
     * @param path the path
     * @return the byte [ ]
     */
    public byte[] readDocumentData(String path) {
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);
        final FileContainer file = imageHandler.getFile(path);
        try {
            return IOUtils.toByteArray(file.getStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create image urls get image urls response.
     * @param request the request
     * @return the get image urls response
     */
    public GetImageUrlsResponse createImageUrls(GetImageUrlsRequest request) {
        final String path = request.getPath();
        final Integer firstPage = request.getFirstPage();
        Integer pageCount = request.getPageCount();
        final Integer width = request.getWidth();
        final List<String> imageUrls = new ArrayList<String>();
        if (pageCount == null) { // For printing
            ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);
            pageCount = imageHandler.getPages(path).size();
        }
        for (int n = firstPage; n < firstPage + pageCount; n++) {
            StringBuilder builder = new StringBuilder("/GetDocumentPageImageHandler?")
                    .append("path=").append(path).append("&")
                    .append("pageIndex=").append(n).append("&")
                    .append("width=").append(width == null ? 800 : width);
            imageUrls.add(builder.toString());
        }
        return new GetImageUrlsResponse() {{
            setImageUrls(imageUrls);
        }};
    }


    /**
     * Rotate document rotate page response.
     * @param path          the path
     * @param pageNumber    the page number
     * @param rotationAngle the rotation angle
     * @return the rotate page response
     */
    public abstract RotatePageResponse rotateDocument(String path, int pageNumber, int rotationAngle);

    /**
     * Upload file string.
     * @param file the file
     * @return the string
     */
    public String uploadFile(MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        File uploadPath = new File(viewerConfig.getStoragePath() + File.separator + UPLOAD_DIRECTORY_NAME);
        File uploadFilePath =
                new File(uploadPath.getAbsolutePath()
                        + File.separator
                        + FilenameUtils.getBaseName(originalFilename) + "-"
                        + GregorianCalendar.getInstance().getTimeInMillis() + "."
                        + FilenameUtils.getExtension(originalFilename));
        try {
            final InputStream inputStream = file.getInputStream();
            if (!uploadPath.exists() && !uploadPath.mkdirs()) {
                throw new Exception("Can't create upload directory!");
            }
            FileUtils.writeByteArrayToFile(uploadFilePath, IOUtils.toByteArray(inputStream));
            System.out.println("File uploaded: " + uploadFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UPLOAD_DIRECTORY_NAME + "/" + uploadFilePath.getName();
    }
}

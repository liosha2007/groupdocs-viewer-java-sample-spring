package com.groupdocs.viewer.samples.spring.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Load file browser tree data response.
 */
public class LoadFileBrowserTreeDataResponse {
    private List<FileBrowserTreeDataNode> nodes;
    private Integer count;

    /**
     * Instantiates a new Load file browser tree data response.
     */
    public LoadFileBrowserTreeDataResponse() {
        nodes = new ArrayList<FileBrowserTreeDataNode>();
    }

    /**
     * Instantiates a new Load file browser tree data response.
     * @param nodes the nodes
     */
    public LoadFileBrowserTreeDataResponse(List<FileBrowserTreeDataNode> nodes) {
        this.nodes = nodes;
        this.count = nodes.size();
    }

    /**
     * Gets nodes.
     * @return the nodes
     */
    public List<FileBrowserTreeDataNode> getNodes() {
        return nodes;
    }

    /**
     * Sets nodes.
     * @param nodes the nodes
     */
    public void setNodes(List<FileBrowserTreeDataNode> nodes) {
        this.nodes = nodes;
    }

    /**
     * Add nodes.
     * @param node the node
     */
    public void addNodes(FileBrowserTreeDataNode node) {
        if (nodes != null) {
            nodes.add(node);
        }
    }

    /**
     * Gets count.
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Sets count.
     * @param count the count
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}

package com.pearson.app.dto;

import java.util.List;

/**
 *
 * JSON serializable DTO containing data concerning a meal search request.
 *
 */
public class TemplatesDTO {

    private long currentPage;
    private long totalPages;
    List<TransformationDTO> transformations;

    public TemplatesDTO(long currentPage, long totalPages, List<TransformationDTO> transformations) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.transformations = transformations;
    }

    public TemplatesDTO(List<TransformationDTO> transformations) {
        this.transformations = transformations;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<TransformationDTO> getTransformations() {
        return transformations;
    }

    public void setTransformations(List<TransformationDTO> transformations) {
        this.transformations = transformations;
    }
}

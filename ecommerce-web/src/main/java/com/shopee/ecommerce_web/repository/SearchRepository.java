package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.dto.response.CategoryResponse;
import com.shopee.ecommerce_web.dto.response.PageResponse;
import com.shopee.ecommerce_web.dto.response.ProductResponse;
import com.shopee.ecommerce_web.dto.response.TagResponse;
import com.shopee.ecommerce_web.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PageResponse<?> getAllProductsPagingSortByMultipleCategorySearch(int pageNo, int pageSize, String search) {
        // Base query
        StringBuilder sqlQuery = new StringBuilder("SELECT u FROM Product u WHERE 1=1");

        // Apply search condition if provided
        if (StringUtils.hasText(search)) {
            sqlQuery.append(" AND LOWER(u.productName) LIKE LOWER(:productName)");
        }

        // Create select query
        Query selectQuery = entityManager.createQuery(sqlQuery.toString());
        selectQuery.setFirstResult((pageNo - 1) * pageSize); // Correct offset
        selectQuery.setMaxResults(pageSize);

        if (StringUtils.hasText(search)) {
            selectQuery.setParameter("productName", "%" + search.trim() + "%");
        }

        List<Product> products = selectQuery.getResultList();

        List<ProductResponse> productResponseList = products.stream()
                .map(product -> {
                    ProductResponse productResponse = new ProductResponse();
                    productResponse.setProductId(product.getProductId());
                    productResponse.setProductName(product.getProductName());
                    productResponse.setSku(product.getSku());
                    productResponse.setPrice(product.getPrice());
                    productResponse.setDescription(product.getDescription());
                    productResponse.setProductImage(product.getProductImage());
                    productResponse.setProductWeight(product.getProductWeight());
                    productResponse.setPublished(product.getPublished());

                    // Chuyển đổi các Category thành CategoryResponse
                    List<CategoryResponse> categoryResponses = product.getCategories().stream()
                            .map(category -> {
                                CategoryResponse categoryResponse = new CategoryResponse();
                                categoryResponse.setCategoryId(category.getCategoryId());
                                categoryResponse.setCategoryName(category.getCategoryName());
                                categoryResponse.setCategoryDescription(category.getCategoryDescription());
                                categoryResponse.setCategoryImagePath(category.getCategoryImagePath());
                                categoryResponse.setActive(category.getActive());
                                return categoryResponse;
                            })
                            .collect(Collectors.toList());
                    productResponse.setCategories(categoryResponses);

                    // Chuyển đổi các Tag thành TagResponse
                    List<TagResponse> tagResponses = product.getTags().stream()
                            .map(tag -> {
                                TagResponse tagResponse = new TagResponse();
                                tagResponse.setTagId(tag.getTagId());
                                tagResponse.setTagName(tag.getTagName());
                                return tagResponse;
                            })
                            .collect(Collectors.toList());
                    productResponse.setTags(tagResponses);

                    return productResponse;
                })
                .collect(Collectors.toList());

        // Count query
        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(u) FROM Product u WHERE 1=1");
        if (StringUtils.hasText(search)) {
            sqlCountQuery.append(" AND LOWER(u.productName) LIKE LOWER(:productName)");
        }

        Query countQuery = entityManager.createQuery(sqlCountQuery.toString());
        if (StringUtils.hasText(search)) {
            countQuery.setParameter("productName", "%" + search.trim() + "%");
        }

        Long totalElements = (Long) countQuery.getSingleResult();
        int totalPage = (int) Math.ceil((double) totalElements / pageSize);

        // Return paginated response
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(totalPage)
                .items(productResponseList)
                .build();
    }

    public PageResponse<?> getAllProductsPagingSortByNameAndPrice(int pageNo, int pageSize, String search, String sortDir) {
        StringBuilder sqlQuery = new StringBuilder("SELECT u FROM Product u WHERE 1=1");

        if (StringUtils.hasText(search)) {
            sqlQuery.append(" AND LOWER(u.productName) LIKE LOWER(:productName)");
        }

        // Sắp xếp theo price tăng/giảm
        if ("desc".equalsIgnoreCase(sortDir)) {
            sqlQuery.append(" ORDER BY u.price DESC");
        } else {
            sqlQuery.append(" ORDER BY u.price ASC");
        }

        Query selectQuery = entityManager.createQuery(sqlQuery.toString());
        selectQuery.setFirstResult((pageNo - 1) * pageSize);
        selectQuery.setMaxResults(pageSize);

        if (StringUtils.hasText(search)) {
            selectQuery.setParameter("productName", "%" + search.trim() + "%");
        }

        List<Product> products = selectQuery.getResultList();

        // Convert to ProductResponse (đúng như bạn làm trước đó)
        List<ProductResponse> productResponseList = products.stream().map(product -> {
            ProductResponse pr = new ProductResponse();
            pr.setProductId(product.getProductId());
            pr.setProductName(product.getProductName());
            pr.setSku(product.getSku());
            pr.setPrice(product.getPrice());
            pr.setDescription(product.getDescription());
            pr.setProductImage(product.getProductImage());
            pr.setProductWeight(product.getProductWeight());
            pr.setPublished(product.getPublished());

            List<CategoryResponse> categoryResponses = product.getCategories().stream().map(category -> {
                CategoryResponse cr = new CategoryResponse();
                cr.setCategoryId(category.getCategoryId());
                cr.setCategoryName(category.getCategoryName());
                cr.setCategoryDescription(category.getCategoryDescription());
                cr.setCategoryImagePath(category.getCategoryImagePath());
                cr.setActive(category.getActive());
                return cr;
            }).collect(Collectors.toList());
            pr.setCategories(categoryResponses);

            List<TagResponse> tagResponses = product.getTags().stream().map(tag -> {
                TagResponse tr = new TagResponse();
                tr.setTagId(tag.getTagId());
                tr.setTagName(tag.getTagName());
                return tr;
            }).collect(Collectors.toList());
            pr.setTags(tagResponses);

            return pr;
        }).collect(Collectors.toList());

        // Count query
        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(u) FROM Product u WHERE 1=1");
        if (StringUtils.hasText(search)) {
            sqlCountQuery.append(" AND LOWER(u.productName) LIKE LOWER(:productName)");
        }

        Query countQuery = entityManager.createQuery(sqlCountQuery.toString());
        if (StringUtils.hasText(search)) {
            countQuery.setParameter("productName", "%" + search.trim() + "%");
        }

        Long totalElements = (Long) countQuery.getSingleResult();
        int totalPage = (int) Math.ceil((double) totalElements / pageSize);

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(totalPage)
                .items(productResponseList)
                .build();
    }

    public PageResponse<?> getProductsPagingSortByCategory(
            int pageNo, int pageSize, String categoryId, String sortDir) {

        StringBuilder sqlQuery = new StringBuilder("SELECT p FROM Product p JOIN p.categories c WHERE 1=1");

        if (StringUtils.hasText(categoryId)) {
            sqlQuery.append(" AND c.categoryId = :categoryId");
        }

        // Sắp xếp theo giá
        if ("desc".equalsIgnoreCase(sortDir)) {
            sqlQuery.append(" ORDER BY p.price DESC");
        } else {
            sqlQuery.append(" ORDER BY p.price ASC");
        }

        Query selectQuery = entityManager.createQuery(sqlQuery.toString());
        selectQuery.setFirstResult((pageNo - 1) * pageSize);
        selectQuery.setMaxResults(pageSize);

        if (StringUtils.hasText(categoryId)) {
            selectQuery.setParameter("categoryId", categoryId.trim());
        }

        List<Product> products = selectQuery.getResultList();

        // Query đếm tổng số
        StringBuilder countQueryStr = new StringBuilder("SELECT COUNT(p) FROM Product p JOIN p.categories c WHERE 1=1");
        if (StringUtils.hasText(categoryId)) {
            countQueryStr.append(" AND c.categoryId = :categoryId");
        }
        Query countQuery = entityManager.createQuery(countQueryStr.toString());
        if (StringUtils.hasText(categoryId)) {
            countQuery.setParameter("categoryId", categoryId.trim());
        }
        Long totalElements = (Long) countQuery.getSingleResult();
        int totalPage = (int) Math.ceil((double) totalElements / pageSize);

        // Map sang DTO
        List<ProductResponse> productResponseList = products.stream().map(product -> {
            ProductResponse pr = new ProductResponse();
            pr.setProductId(product.getProductId());
            pr.setProductName(product.getProductName());
            pr.setSku(product.getSku());
            pr.setPrice(product.getPrice());
            pr.setDescription(product.getDescription());
            pr.setProductImage(product.getProductImage());
            pr.setProductWeight(product.getProductWeight());
            pr.setPublished(product.getPublished());

            List<CategoryResponse> categoryResponses = product.getCategories().stream().map(category -> {
                CategoryResponse cr = new CategoryResponse();
                cr.setCategoryId(category.getCategoryId());
                cr.setCategoryName(category.getCategoryName());
                cr.setCategoryDescription(category.getCategoryDescription());
                cr.setCategoryImagePath(category.getCategoryImagePath());
                cr.setActive(category.getActive());
                return cr;
            }).collect(Collectors.toList());
            pr.setCategories(categoryResponses);

            List<TagResponse> tagResponses = product.getTags().stream().map(tag -> {
                TagResponse tr = new TagResponse();
                tr.setTagId(tag.getTagId());
                tr.setTagName(tag.getTagName());
                return tr;
            }).collect(Collectors.toList());
            pr.setTags(tagResponses);

            return pr;
        }).collect(Collectors.toList());

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(totalPage)
                .items(productResponseList)
                .build();
    }


}

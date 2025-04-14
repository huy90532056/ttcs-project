package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.dto.response.PageResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public PageResponse<?> getAllProductsPagingSortByMultipleCategorySearch(int pageNo, int pageSize, String search,
                                                                            String sortBy) {
        StringBuilder sqlQuery = new StringBuilder("select u from Product u where 1=1");

        if (StringUtils.hasLength(search)) {
            sqlQuery.append(" and lower(u.productName) like lower(:productName)");
            sqlQuery.append(" or lower(u.description) like lower(:description)");
            sqlQuery.append(" or lower(u.sku) like lower(:sku)");
        }

        Query selectQuery = entityManager.createQuery(sqlQuery.toString());
        selectQuery.setFirstResult(pageNo * pageSize);
        selectQuery.setMaxResults(pageSize);
        if (StringUtils.hasLength(search)) {
            selectQuery.setParameter("productName", "%" + search + "%");
            selectQuery.setParameter("description", "%" + search + "%");
            selectQuery.setParameter("sku", "%" + search + "%");
        }

        List products =  selectQuery.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder("select count(*) from Product u where 1=1");
        if (StringUtils.hasLength(search)) {
            sqlCountQuery.append(" and lower(u.productName) like lower(:productName)");
            sqlCountQuery.append(" or lower(u.description) like lower(:description)");
            sqlCountQuery.append(" or lower(u.sku) like lower(:sku)");
        }
        Query selectCountQuery = entityManager.createQuery(sqlCountQuery.toString());
        if (StringUtils.hasLength(search)) {
            selectCountQuery.setParameter("productName", "%" + search + "%");
            selectCountQuery.setParameter("description", "%" + search + "%");
            selectCountQuery.setParameter("sku", "%" + search + "%");
        }
        Long totalElements = (Long) selectCountQuery.getSingleResult();

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage((int) (totalElements / pageSize))
                .items(products)
                .build();
    }
}

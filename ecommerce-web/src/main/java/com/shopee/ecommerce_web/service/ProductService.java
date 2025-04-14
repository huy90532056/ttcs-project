package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.ProductCreationRequest;
import com.shopee.ecommerce_web.dto.request.ProductUpdateRequest;
import com.shopee.ecommerce_web.dto.response.PageResponse;
import com.shopee.ecommerce_web.dto.response.ProductResponse;
import com.shopee.ecommerce_web.entity.Category;
import com.shopee.ecommerce_web.entity.Product;
import com.shopee.ecommerce_web.entity.Tag;
import com.shopee.ecommerce_web.mapper.ProductMapper;
import com.shopee.ecommerce_web.repository.CategoryRepository;
import com.shopee.ecommerce_web.repository.ProductRepository;
import com.shopee.ecommerce_web.repository.SearchRepository;
import com.shopee.ecommerce_web.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SearchRepository searchRepository;

    // Tạo mới Product
    @CacheEvict(value = "products", allEntries = true) // Xóa cache của danh sách sản phẩm khi tạo mới
    public Product createProduct(ProductCreationRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        return productRepository.save(product);
    }

    // Lấy danh sách tất cả sản phẩm
    @Cacheable(value = "products") // Cache toàn bộ danh sách sản phẩm
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // Lấy thông tin chi tiết của một sản phẩm
    @Cacheable(value = "product", key = "#id") // Cache thông tin sản phẩm theo ID
    public ProductResponse getProduct(Long id) {
        return productMapper.toProductResponse(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found!")));
    }

    // Cập nhật thông tin sản phẩm
    @CachePut(value = "product", key = "#productId") // Cập nhật lại cache cho sản phẩm đã được chỉnh sửa
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        productMapper.updateProduct(product, productUpdateRequest);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    // Thêm category vào Product
    @CacheEvict(value = "product", key = "#productId") // Xóa cache cũ vì product đã thay đổi
    public Product addCategoryToProduct(Long productId, String categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category Not Found!"));

        product.getCategories().add(category);
        return productRepository.save(product);
    }

    // Thêm tag vào Product
    @CacheEvict(value = "product", key = "#productId") // Xóa cache cũ vì product đã thay đổi
    public Product addTagToProduct(Long productId, String tagId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag Not Found!"));

        product.getTags().add(tag);

        return productRepository.save(product);
    }

    // Xóa Product
    @CacheEvict(value = {"product", "products"}, key = "#productId", allEntries = true) // Xóa cache liên quan
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    // them 1 so phuong thuc sort

    public List<ProductResponse> getAllProductsPaging(int pageNo, int pageSize) {
        int p = 0;
        if(pageNo > 0) {
            p = pageNo - 1;
        }
        Pageable pageable = PageRequest.of(p, pageSize);

        Page<Product> products = productRepository.findAll(pageable);

        return products.stream()
                .map(product -> ProductResponse.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .sku(product.getSku())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .productImage(product.getProductImage())
                        .productWeight(product.getProductWeight())
                        .published(product.getPublished())
                        .build())
                .collect(Collectors.toList());


    }

    public List<ProductResponse> getAllProductsPagingSort(int pageNo, int pageSize, String sortBy) {
        int p = 0;
        if(pageNo > 0) {
            p = pageNo - 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if(matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(p, pageSize, Sort.by(sorts));

        Page<Product> products = productRepository.findAll(pageable);

        return products.stream()
                .map(product -> ProductResponse.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .sku(product.getSku())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .productImage(product.getProductImage())
                        .productWeight(product.getProductWeight())
                        .published(product.getPublished())
                        .build())
                .collect(Collectors.toList());


    }

    public PageResponse<?> getAllProductsPagingSortByMultipleCategory(int pageNo, int pageSize,
                                                                      String... sorts) {
        int p = 0;
        if(pageNo > 0) {
            p = pageNo - 1;
        }

        List<Sort.Order> orders = new ArrayList<>();

        for (String sortBy : sorts) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if(matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(p, pageSize, Sort.by(orders));

        Page<Product> products = productRepository.findAll(pageable);

        List<ProductResponse> productResponseList = products.stream()
                .map(product -> ProductResponse.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .sku(product.getSku())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .productImage(product.getProductImage())
                        .productWeight(product.getProductWeight())
                        .published(product.getPublished())
                        .build())
                .collect(Collectors.toList());

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(products.getTotalPages())
                .items(productResponseList)
                .build();
    }

    public PageResponse<?> getAllProductsPagingSortByMultipleCategorySearch(int pageNo, int pageSize,
                                                                      String search,
                                                                      String sortBy) {
        return searchRepository.getAllProductsPagingSortByMultipleCategorySearch(pageNo, pageSize,
                search, sortBy);
    }
}
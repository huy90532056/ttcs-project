package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.ProductCreationRequest;
import com.shopee.ecommerce_web.dto.request.ProductUpdateRequest;
import com.shopee.ecommerce_web.dto.response.CategoryResponse;
import com.shopee.ecommerce_web.dto.response.PageResponse;
import com.shopee.ecommerce_web.dto.response.ProductResponse;
import com.shopee.ecommerce_web.dto.response.TagResponse;
import com.shopee.ecommerce_web.entity.Category;
import com.shopee.ecommerce_web.entity.Product;
import com.shopee.ecommerce_web.entity.ProductVariant;
import com.shopee.ecommerce_web.entity.Tag;
import com.shopee.ecommerce_web.mapper.ProductMapper;
import com.shopee.ecommerce_web.repository.*;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private ProductVariantRepository productVariantRepository;

    // Tạo mới Product
    @CacheEvict(value = "products", allEntries = true) // Xóa cache của danh sách sản phẩm khi tạo mới
    public ProductResponse createProduct(ProductCreationRequest productRequest) {
        // Chuyển ProductCreationRequest thành Product
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setSku(productRequest.getSku());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setProductImage(productRequest.getProductImage());
        product.setProductWeight(productRequest.getProductWeight());
        product.setPublished(productRequest.getPublished());

        // Lưu Product vào database
        Product savedProduct = productRepository.save(product);

        // Tạo ProductVariant với variantName "default" và variantValue "Basic"
        ProductVariant productVariant = new ProductVariant();
        productVariant.setVariantName("default");
        productVariant.setVariantValue("Basic");
        productVariant.setProduct(savedProduct); // Thiết lập mối quan hệ với Product
        productVariant.setPrice(savedProduct.getPrice()); // Giá của ProductVariant có thể giống giá của Product
        productVariant.setStockQuantity(50); // Đặt số lượng tồn kho mặc định là 50

        // Lưu ProductVariant vào database
        productVariantRepository.save(productVariant);

        // Tạo ProductResponse thủ công từ Product đã lưu
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(savedProduct.getProductId());
        productResponse.setProductName(savedProduct.getProductName());
        productResponse.setSku(savedProduct.getSku());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setProductImage(savedProduct.getProductImage());
        productResponse.setProductWeight(savedProduct.getProductWeight());
        productResponse.setPublished(savedProduct.getPublished());

        // Map categories (nếu có)
        List<CategoryResponse> categoryResponses = Optional.ofNullable(savedProduct.getCategories())
                .orElse(Collections.emptyList()) // If null, return an empty list
                .stream()
                .map(category -> {
                    CategoryResponse categoryResponse = new CategoryResponse();
                    categoryResponse.setCategoryId(category.getCategoryId());
                    categoryResponse.setCategoryName(category.getCategoryName());
                    categoryResponse.setCategoryDescription(category.getCategoryDescription());
                    categoryResponse.setCategoryIcon(category.getCategoryIcon());
                    categoryResponse.setCategoryImagePath(category.getCategoryImagePath());
                    categoryResponse.setActive(category.getActive());
                    return categoryResponse;
                })
                .collect(Collectors.toList());
        productResponse.setCategories(categoryResponses);

        // Map tags (nếu có)
        List<TagResponse> tagResponses = Optional.ofNullable(savedProduct.getTags())
                .orElse(Collections.emptyList()) // If null, return an empty list
                .stream()
                .map(tag -> {
                    TagResponse tagResponse = new TagResponse();
                    tagResponse.setTagId(tag.getTagId());
                    tagResponse.setTagName(tag.getTagName());
                    return tagResponse;
                })
                .collect(Collectors.toList());
        productResponse.setTags(tagResponses);

        return productResponse;
    }


    // Lấy danh sách tất cả sản phẩm
    @Cacheable(value = "products") // Cache toàn bộ danh sách sản phẩm
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();

        // Chuyển đổi danh sách Product thành danh sách ProductResponse thủ công
        return products.stream()
                .map(product -> {
                    // Tạo ProductResponse từ Product
                    ProductResponse productResponse = new ProductResponse();
                    productResponse.setProductId(product.getProductId());
                    productResponse.setProductName(product.getProductName());
                    productResponse.setSku(product.getSku());
                    productResponse.setPrice(product.getPrice());
                    productResponse.setDescription(product.getDescription());
                    productResponse.setProductImage(product.getProductImage());
                    productResponse.setProductWeight(product.getProductWeight());
                    productResponse.setPublished(product.getPublished());

                    // Map categories
                    List<CategoryResponse> categoryResponses = product.getCategories().stream()
                            .map(category -> {
                                CategoryResponse categoryResponse = new CategoryResponse();
                                categoryResponse.setCategoryId(category.getCategoryId());
                                categoryResponse.setCategoryName(category.getCategoryName());
                                categoryResponse.setCategoryDescription(category.getCategoryDescription());
                                categoryResponse.setCategoryIcon(category.getCategoryIcon());
                                categoryResponse.setCategoryImagePath(category.getCategoryImagePath());
                                categoryResponse.setActive(category.getActive());
                                return categoryResponse;
                            })
                            .collect(Collectors.toList());
                    productResponse.setCategories(categoryResponses);

                    // Map tags
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
                .collect(Collectors.toList()); // Return List<ProductResponse>
    }


    // Lấy thông tin chi tiết của một sản phẩm
    @Cacheable(value = "product", key = "#id") // Cache thông tin sản phẩm theo ID
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        // Tạo ProductResponse thủ công từ Product
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(product.getProductId());
        productResponse.setProductName(product.getProductName());
        productResponse.setSku(product.getSku());
        productResponse.setPrice(product.getPrice());
        productResponse.setDescription(product.getDescription());
        productResponse.setProductImage(product.getProductImage());
        productResponse.setProductWeight(product.getProductWeight());
        productResponse.setPublished(product.getPublished());

        // Map categories
        List<CategoryResponse> categoryResponses = product.getCategories().stream()
                .map(category -> {
                    CategoryResponse categoryResponse = new CategoryResponse();
                    categoryResponse.setCategoryId(category.getCategoryId());
                    categoryResponse.setCategoryName(category.getCategoryName());
                    categoryResponse.setCategoryDescription(category.getCategoryDescription());
                    categoryResponse.setCategoryIcon(category.getCategoryIcon());
                    categoryResponse.setCategoryImagePath(category.getCategoryImagePath());
                    categoryResponse.setActive(category.getActive());
                    return categoryResponse;
                })
                .collect(Collectors.toList());
        productResponse.setCategories(categoryResponses);

        // Map tags
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
    }


    // Cập nhật thông tin sản phẩm
    @CachePut(value = "product", key = "#productId") // Cập nhật lại cache cho sản phẩm đã được chỉnh sửa
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        // Cập nhật thông tin Product từ ProductUpdateRequest
        product.setProductName(productUpdateRequest.getProductName());
        product.setPrice(productUpdateRequest.getPrice());
        product.setDescription(productUpdateRequest.getDescription());
        product.setProductImage(productUpdateRequest.getProductImage());
        product.setProductWeight(productUpdateRequest.getProductWeight());
        product.setPublished(productUpdateRequest.getPublished());

        // Lưu Product đã cập nhật
        Product updatedProduct = productRepository.save(product);

        // Tạo ProductResponse thủ công từ Product đã cập nhật
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(updatedProduct.getProductId());
        productResponse.setProductName(updatedProduct.getProductName());
        productResponse.setSku(product.getSku());
        productResponse.setPrice(updatedProduct.getPrice());
        productResponse.setDescription(updatedProduct.getDescription());
        productResponse.setProductImage(updatedProduct.getProductImage());
        productResponse.setProductWeight(updatedProduct.getProductWeight());
        productResponse.setPublished(updatedProduct.getPublished());

        // Map categories
        List<CategoryResponse> categoryResponses = updatedProduct.getCategories().stream()
                .map(category -> {
                    CategoryResponse categoryResponse = new CategoryResponse();
                    categoryResponse.setCategoryId(category.getCategoryId());
                    categoryResponse.setCategoryName(category.getCategoryName());
                    categoryResponse.setCategoryDescription(category.getCategoryDescription());
                    categoryResponse.setCategoryIcon(category.getCategoryIcon());
                    categoryResponse.setCategoryImagePath(category.getCategoryImagePath());
                    categoryResponse.setActive(category.getActive());
                    return categoryResponse;
                })
                .collect(Collectors.toList());
        productResponse.setCategories(categoryResponses);

        // Map tags
        List<TagResponse> tagResponses = updatedProduct.getTags().stream()
                .map(tag -> {
                    TagResponse tagResponse = new TagResponse();
                    tagResponse.setTagId(tag.getTagId());
                    tagResponse.setTagName(tag.getTagName());
                    return tagResponse;
                })
                .collect(Collectors.toList());
        productResponse.setTags(tagResponses);

        return productResponse;
    }


    // Thêm category vào Product
    @CacheEvict(value = "product", key = "#productId") // Xóa cache cũ vì product đã thay đổi
    public ProductResponse addCategoryToProduct(Long productId, String categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category Not Found!"));

        // Thêm category vào product
        product.getCategories().add(category);

        // Lưu sản phẩm đã cập nhật
        Product updatedProduct = productRepository.save(product);

        // Chuyển đổi sản phẩm thành ProductResponse thủ công
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(updatedProduct.getProductId());
        productResponse.setProductName(updatedProduct.getProductName());
        productResponse.setSku(updatedProduct.getSku());
        productResponse.setPrice(updatedProduct.getPrice());
        productResponse.setDescription(updatedProduct.getDescription());
        productResponse.setProductImage(updatedProduct.getProductImage());
        productResponse.setProductWeight(updatedProduct.getProductWeight());
        productResponse.setPublished(updatedProduct.getPublished());

        // Chuyển đổi danh sách Categories
        List<CategoryResponse> categoryResponses = updatedProduct.getCategories().stream()
                .map(c -> {
                    CategoryResponse categoryResponse = new CategoryResponse();
                    categoryResponse.setCategoryId(c.getCategoryId());
                    categoryResponse.setCategoryName(c.getCategoryName());
                    categoryResponse.setCategoryDescription(c.getCategoryDescription());
                    categoryResponse.setCategoryIcon(c.getCategoryIcon());
                    categoryResponse.setCategoryImagePath(c.getCategoryImagePath());
                    categoryResponse.setActive(c.getActive());
                    return categoryResponse;
                })
                .collect(Collectors.toList());
        productResponse.setCategories(categoryResponses);

        // Chuyển đổi danh sách Tags
        List<TagResponse> tagResponses = updatedProduct.getTags().stream()
                .map(tag -> {
                    TagResponse tagResponse = new TagResponse();
                    tagResponse.setTagId(tag.getTagId());
                    tagResponse.setTagName(tag.getTagName());
                    return tagResponse;
                })
                .collect(Collectors.toList());
        productResponse.setTags(tagResponses);

        return productResponse;
    }


    // Thêm tag vào Product
    @CacheEvict(value = "product", key = "#productId") // Xóa cache cũ vì product đã thay đổi
    public ProductResponse addTagToProduct(Long productId, String tagId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag Not Found!"));

        // Thêm tag vào product
        product.getTags().add(tag);

        // Lưu sản phẩm đã cập nhật
        Product updatedProduct = productRepository.save(product);

        // Chuyển đổi sản phẩm thành ProductResponse thủ công
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(updatedProduct.getProductId());
        productResponse.setProductName(updatedProduct.getProductName());
        productResponse.setSku(updatedProduct.getSku());
        productResponse.setPrice(updatedProduct.getPrice());
        productResponse.setDescription(updatedProduct.getDescription());
        productResponse.setProductImage(updatedProduct.getProductImage());
        productResponse.setProductWeight(updatedProduct.getProductWeight());
        productResponse.setPublished(updatedProduct.getPublished());

        // Chuyển đổi danh sách Categories
        List<CategoryResponse> categoryResponses = updatedProduct.getCategories().stream()
                .map(c -> {
                    CategoryResponse categoryResponse = new CategoryResponse();
                    categoryResponse.setCategoryId(c.getCategoryId());
                    categoryResponse.setCategoryName(c.getCategoryName());
                    categoryResponse.setCategoryDescription(c.getCategoryDescription());
                    categoryResponse.setCategoryIcon(c.getCategoryIcon());
                    categoryResponse.setCategoryImagePath(c.getCategoryImagePath());
                    categoryResponse.setActive(c.getActive());
                    return categoryResponse;
                })
                .collect(Collectors.toList());
        productResponse.setCategories(categoryResponses);

        // Chuyển đổi danh sách Tags
        List<TagResponse> tagResponses = updatedProduct.getTags().stream()
                .map(tmpTag -> {
                    TagResponse tagResponse = new TagResponse();
                    tagResponse.setTagId(tmpTag.getTagId());
                    tagResponse.setTagName(tmpTag.getTagName());
                    return tagResponse;
                })
                .collect(Collectors.toList());
        productResponse.setTags(tagResponses);

        return productResponse;
    }


    // Xóa Product
    @CacheEvict(value = {"product", "products"}, key = "#productId", allEntries = true) // Xóa cache liên quan
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    // Lấy danh sách sản phẩm có phân trang
    public List<ProductResponse> getAllProductsPaging(int pageNo, int pageSize) {
        int p = (pageNo > 0) ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(p, pageSize);

        Page<Product> products = productRepository.findAll(pageable);

        // Chuyển đổi danh sách Product thành ProductResponse thủ công
        return products.stream()
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

                    // Set categories
                    List<CategoryResponse> categoryResponses = product.getCategories().stream()
                            .map(category -> {
                                CategoryResponse categoryResponse = new CategoryResponse();
                                categoryResponse.setCategoryId(category.getCategoryId());
                                categoryResponse.setCategoryName(category.getCategoryName());
                                categoryResponse.setCategoryDescription(category.getCategoryDescription());
                                categoryResponse.setCategoryIcon(category.getCategoryIcon());
                                categoryResponse.setCategoryImagePath(category.getCategoryImagePath());
                                categoryResponse.setActive(category.getActive());
                                return categoryResponse;
                            })
                            .collect(Collectors.toList());
                    productResponse.setCategories(categoryResponses);

                    // Set tags
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
    }


    // Lấy danh sách sản phẩm có phân trang và sắp xếp
    public List<ProductResponse> getAllProductsPagingSort(int pageNo, int pageSize, String sortBy) {
        int p = (pageNo > 0) ? pageNo - 1 : 0;

        // Tạo danh sách các Order cho Sort
        List<Sort.Order> sorts = new ArrayList<>();

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        // Tạo Pageable với các Order đã định nghĩa
        Pageable pageable = PageRequest.of(p, pageSize, Sort.by(sorts));

        // Lấy danh sách sản phẩm từ repository
        Page<Product> products = productRepository.findAll(pageable);

        // Chuyển đổi từng Product thành ProductResponse thủ công
        return products.stream()
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
                                categoryResponse.setCategoryIcon(category.getCategoryIcon());
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
                .collect(Collectors.toList()); // Return List<ProductResponse>
    }


    // Lấy danh sách sản phẩm có phân trang, sắp xếp và lọc theo category
    public PageResponse<?> getAllProductsPagingSortByMultipleCategory(int pageNo, int pageSize, String... sorts) {
        int p = (pageNo > 0) ? pageNo - 1 : 0;

        List<Sort.Order> orders = new ArrayList<>();

        // Tạo các Order dựa trên các tham số sort
        for (String sortBy : sorts) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        // Tạo Pageable với các Order đã được xác định
        Pageable pageable = PageRequest.of(p, pageSize, Sort.by(orders));

        // Lấy danh sách sản phẩm từ repository
        Page<Product> products = productRepository.findAll(pageable);

        // Chuyển đổi từng Product thành ProductResponse thủ công
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
                                categoryResponse.setCategoryIcon(category.getCategoryIcon());
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

        // Trả về PageResponse
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(products.getTotalPages())
                .items(productResponseList)
                .build(); // Return PageResponse<ProductResponse>
    }


    // Lấy danh sách sản phẩm có phân trang, sắp xếp, lọc theo category và tìm kiếm
    public PageResponse<?> getAllProductsPagingSortByMultipleCategorySearch(int pageNo, int pageSize, String search, String sortBy) {
        return searchRepository.getAllProductsPagingSortByMultipleCategorySearch(pageNo, pageSize, search, sortBy);
    }

    public List<ProductResponse> getProductsByCategory(String categoryId) {
        List<Product> products = productRepository.findAllByCategories_CategoryId(categoryId);

        return products.stream().map(product -> {
            ProductResponse response = new ProductResponse();
            response.setProductId(product.getProductId());
            response.setProductName(product.getProductName());
            response.setSku(product.getSku());
            response.setPrice(product.getPrice());
            response.setDescription(product.getDescription());
            response.setProductImage(product.getProductImage());
            response.setProductWeight(product.getProductWeight());
            response.setPublished(product.getPublished());
            List<CategoryResponse> categoryResponses = product.getCategories().stream()
                    .map(category -> {
                        CategoryResponse categoryResponse = new CategoryResponse();
                        categoryResponse.setCategoryId(category.getCategoryId());
                        categoryResponse.setCategoryName(category.getCategoryName());
                        categoryResponse.setCategoryDescription(category.getCategoryDescription());
                        categoryResponse.setCategoryIcon(category.getCategoryIcon());
                        categoryResponse.setCategoryImagePath(category.getCategoryImagePath());
                        categoryResponse.setActive(category.getActive());
                        return categoryResponse;
                    })
                    .collect(Collectors.toList());
            response.setCategories(categoryResponses);

            // Map tags
            List<TagResponse> tagResponses = product.getTags().stream()
                    .map(tag -> {
                        TagResponse tagResponse = new TagResponse();
                        tagResponse.setTagId(tag.getTagId());
                        tagResponse.setTagName(tag.getTagName());
                        return tagResponse;
                    })
                    .collect(Collectors.toList());
            response.setTags(tagResponses);
            return response;
        }).collect(Collectors.toList());
    }

}

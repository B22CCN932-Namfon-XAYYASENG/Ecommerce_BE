package com.example.test.mapper;

import com.example.test.dto.request.ProductRequest;
import com.example.test.dto.response.ProductResponse;
import com.example.test.entity.Product;
import com.example.test.entity.ProductImage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Value("${cloudinary.cloud-name}")
    protected String cloudName;

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
//    @Mapping(target = "imagesToDelete", ignore = true)
    @Mapping(target = "productDetails", expression = "java(buildJsonToString(request))")
//    @Mapping(target = "images", expression = "java(mapImages(request.getNewImages(), product))")
    public abstract Product toProduct(ProductRequest request);

    @Mapping(target = "productDetails", expression = "java(buildStringToJson(product))")
    @Mapping(target = "category", expression = "java(buildCategoryName(product))")
    @Mapping(target = "brand", expression = "java(buildBrandName(product))")
//    @Mapping(target = "image", expression = "java(buildImageName(product))")
    @Mapping(target = "images", expression = "java(buildImageUrls(product))")
    public abstract ProductResponse toProductResponse(Product product);

    protected String buildJsonToString(ProductRequest request) {
        try {
            Map<String, Object> productDetail = request.getProductDetails();

            return new ObjectMapper().writeValueAsString(productDetail);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    protected JsonNode buildStringToJson(Product product) {
        try {
            String productDetail = product.getProductDetails();

            return new ObjectMapper().readTree(productDetail);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    protected String buildCategoryName(Product product) {
        return product.getCategory() != null
                ? product.getCategory().getName()
                : null;
    }

    protected String buildBrandName(Product product) {
        return product.getBrand() != null
                ? product.getBrand().getName()
                : null;
    }


    protected List<String> buildImageUrls(Product product) {
        String baseUrl = "https://res.cloudinary.com/" + cloudName + "/image/upload/products/";
        return product.getImages().stream()
                .map(image -> {
                    String fileName = image.getUrl();
                    if (fileName != null && fileName.contains(".")) {
                        fileName = fileName.substring(0, fileName.lastIndexOf("."));
                    }
                    return baseUrl + product.getId() + "/" + fileName;
                })
                .collect(Collectors.toList());
    }

    @AfterMapping
    protected void mapImages(@MappingTarget Product product, ProductRequest request) {
        List<MultipartFile> newImages = request.getNewImages();
        if (newImages != null && !newImages.isEmpty()) {
            List<ProductImage> productImages = newImages.stream().map(image -> {
                ProductImage productImage = new ProductImage();
                String fileName = image.getOriginalFilename();
                productImage.setUrl(fileName);
                productImage.setProduct(product);
                return productImage;
            }).collect(Collectors.toList());
            product.setImages(productImages);
        }
    }
}

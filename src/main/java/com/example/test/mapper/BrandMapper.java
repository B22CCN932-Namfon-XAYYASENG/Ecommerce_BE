package com.example.test.mapper;

import com.example.test.dto.response.BrandResponse;
import com.example.test.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    @Mapping(target = "url_logo", expression = "java(buildLogo(brand))")
    BrandResponse toBrandResponse(Brand brand);

    default String buildLogo(Brand brand) {
        return "https://ecommerce-vinhseo.s3.ap-southeast-2.amazonaws.com/brands/"
                + brand.getId() + "/"
                + brand.getLogo();
    }
}

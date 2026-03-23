package com.example.test.mapper;

import com.example.test.dto.response.BrandResponse;
import com.example.test.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;

@Mapper(componentModel = "spring")
public abstract class BrandMapper {

    @Value("${cloudinary.cloud-name}")
    protected String cloudName;
    @Mapping(target = "url_logo", expression = "java(buildLogo(brand))")
    public abstract BrandResponse toBrandResponse(Brand brand);

    protected String buildLogo(Brand brand) {
        String fileName = brand.getLogo();
        if (fileName != null && fileName.contains(".")) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return "https://res.cloudinary.com/" + cloudName + "/image/upload/brands/"
                + brand.getId() + "/"
                + fileName;
    }
}

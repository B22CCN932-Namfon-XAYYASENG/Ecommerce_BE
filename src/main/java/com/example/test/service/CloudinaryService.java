package com.example.test.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String saveImageToCloudinary(MultipartFile file, String folder) throws Exception {
        try {
            String originalFileName = file.getOriginalFilename();
            if (originalFileName != null && originalFileName.contains(".")) {
                originalFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
            }

            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", folder,
                    "public_id", originalFileName,
                    "overwrite", true,
                    "resource_type", "auto"
            ));

            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to upload image to Cloudinary: " + e.getMessage());
        }
    }

    public void deleteImageFromCloudinary(String folder, String fileName) throws Exception {
        try {
            if (fileName != null && fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            String publicId = folder + "/" + fileName;

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            System.out.println("delete success");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to delete image from Cloudinary: " + e.getMessage());
        }
    }

    public void deleteImageFromCloudinaryFolder(String folder) throws Exception {
        try {
            cloudinary.api().deleteResourcesByPrefix(folder, ObjectUtils.emptyMap());
            cloudinary.api().deleteFolder(folder, ObjectUtils.emptyMap());
            System.out.println("delete folder success");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to delete folder from Cloudinary: " + e.getMessage());
        }
    }
}

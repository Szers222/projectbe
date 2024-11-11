package tdc.edu.vn.project_mobile_be.services.impl;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class GoogleCloudStorageService {

    @Value("${google.cloud.storage.bucket-name}")
    private String bucketName;

    @Value("${google.cloud.credentials.path}")
    private String credentialsPath;

    private Storage storage;

    @PostConstruct
    public void init() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath));
        storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

           storage.create(blobInfo, file.getBytes());

        // Trả về URL công khai của file
        return String.format("https://storage.cloud.google.com/%s/%s", bucketName, fileName);
    }

    //update file
    public String updateFile(MultipartFile file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        storage.create(blobInfo, file.getBytes());

        // Trả về URL công khai của file
        return String.format("https://storage.cloud.google.com/%s/%s", bucketName, fileName);
    }

    public void deleteFile(String fileName) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        storage.delete(blobId);
    }
}

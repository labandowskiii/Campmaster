package com.campmaster.modelo.connections;
import com.campmaster.modelo.Entities.Document;
import com.campmaster.modelo.Entities.User;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;


public final class StorageConnection {
    private S3Client s3=null;
    Dotenv dotenv = Dotenv.configure().load();
    private String accessKey = dotenv.get("AWS_ACCESS_KEY");
    private String secretKey = dotenv.get("AWS_SECRET_KEY");

    public StorageConnection() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        s3 = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public String uploadDoc(Document doc, String path, User user) {
        try {
            String key = "/titulos/" + user.getDni() + "/";
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("alabac00-bucket")
                    .key(key + doc.getName())
                    .build();
            PutObjectResponse response = s3.putObject(putObjectRequest, new File(path).toPath());
            String url = s3.utilities().getUrl(builder -> builder.bucket("alabac00-bucket").key(key)).toExternalForm();
            return url;
        } catch (Exception e) {
            return null;
        }
    }

    public void downloadAuth(String downloadPath) throws URISyntaxException, IOException {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket("alabac00-bucket")
                    .key("documentos/autorizacion.pdf")
                    .build();
            s3.getObject(getObjectRequest, Paths.get(downloadPath));
            System.out.println("Archivo descargado con éxito en " + downloadPath);
        } catch (S3Exception e) {
            System.err.println("Error descargando el archivo: " + e.awsErrorDetails().errorMessage());
        }

    }

    public String uploadDoc(String path, String dni, String type) {
        try {
            String key= "documentos/" + type + "/" + dni+ "/" + type;
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("alabac00-bucket")
                    .key(key)
                    .build();
            PutObjectResponse response = s3.putObject(putObjectRequest, new File(path).toPath());
            String url = s3.utilities().getUrl(builder -> builder.bucket("alabac00-bucket").key(key)).toExternalForm();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
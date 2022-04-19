//[AWS - S3] [STEP 4] S3 Service implementation
package br.com.byamada.awsservicesapringboot.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

import static com.amazonaws.services.s3.model.CannedAccessControlList.Private;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Slf4j
@Component
public class S3ServiceImpl implements S3Service{

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.defaultBucket}")
    private String bucketName;

    @Override
    public S3Object pickObjectFromBucket(String fileKey) {
        return amazonS3.getObject(bucketName, fileKey);
    }

    @Override
    public File downloadFile(String fileKey) throws IOException {
        S3Object s3Object = pickObjectFromBucket(fileKey);
        File xml = File.createTempFile(UUID.randomUUID().toString(), "xml");
        try (S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent()){
            Files.copy(s3ObjectInputStream, xml.toPath(), REPLACE_EXISTING);
            log.info("File was imported {}", fileKey);
            return xml;
        }
    }

    @Override
    public void putFile(File file, String fileName) {
        putFIle(file, fileName, null);

    }

    @Override
    public void putFIle(File file, String fileName, String BucketName) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        try {
            String bucketName = this.bucketName;
            InputStream fileImpStream = new FileInputStream(file);
            if(bucketName != null && !bucketName.isEmpty()) {
                bucketName += "/" + bucketName.replaceAll("^\\/","/");
            }
            objectMetadata.setContentLength(file.length());
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, fileImpStream, objectMetadata).withCannedAcl(Private));
        }catch (AmazonServiceException ase) {
            log.error("Amazon Error during s3 upload ", ase);
        }catch (SdkClientException sdke) {
            log.error("Network Error during s3 upload ", sdke);
        }catch (FileNotFoundException fne) {
            log.error("File error ", fne);
        }

    }
}

//[AWS - S3] [STEP 3] Create a interface with file manage methods
package br.com.byamada.awsservicesapringboot.service;

import com.amazonaws.services.s3.model.S3Object;

import java.io.File;
import java.io.IOException;

public interface S3Service {

    S3Object pickObjectFromBucket(String fileKey);
    File downloadFile(String fileKey) throws IOException;
    void putFile(File file, String fileName);
    void putFIle(File file, String fileName, String BucketName);

}

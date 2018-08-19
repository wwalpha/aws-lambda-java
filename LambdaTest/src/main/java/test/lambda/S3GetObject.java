package test.lambda;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;

/**
 * 
 * @author remoter
 *
 */
public class S3GetObject implements Runnable {
	/** Bucket Name */
	private String bucket;
	/** Bucket Key */
	private String key;
	/** Local File Path */
	private String localFile;

	public S3GetObject(String bucket, String key, String localFile) {
		this.bucket = bucket;
		this.key = key;
		this.localFile = localFile;
	}

	@Override
	public void run() {
		File file = new File(localFile);

		if (file.exists() && file.length() != 0) {
			return;
		}

		try {
			AmazonS3 client = AmazonS3ClientBuilder.defaultClient();

			S3Object s3File = client.getObject(bucket, key);

			Files.copy(s3File.getObjectContent(), Paths.get(localFile), StandardCopyOption.REPLACE_EXISTING);
		} catch (SdkClientException | IOException e) {
			e.printStackTrace();
		}
	}
}

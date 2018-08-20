package test.lambda;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class LambdaTest {
	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(System.getenv("xxxx")).build();

	public void test2() {
		DynamoDBMapper mapper = new DynamoDBMapper(client);
	}

	public static void test() {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig.Builder().withTableNameOverride(new TableNameOverride("XXXX")).build();
		DynamoDBMapper mapper = new DynamoDBMapper(client, config);

		ExecutorService es = Executors.newWorkStealingPool();

		List<CompletableFuture<Void>> futures = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			CompletableFuture<Void> future = CompletableFuture.runAsync(putObject(), es);

			futures.add(future);
		}

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[] {}));
	}

	public static Runnable putObject() {
		return new Runnable() {

			@Override
			public void run() {
				AmazonS3 client = AmazonS3ClientBuilder.defaultClient();
				client.putObject("bucket", "key", new File(""));

			}
		};
	}
}

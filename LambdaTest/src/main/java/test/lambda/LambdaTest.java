package test.lambda;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class LambdaTest {

	public static void test() {
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

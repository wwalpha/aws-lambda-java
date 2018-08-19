package test.lambda;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amazonaws.services.s3.AmazonS3Client;

public class LambdaTest {

	public static void test() {
		ExecutorService es = Executors.newWorkStealingPool();

		List<CompletableFuture<Void>> futures = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
				AmazonS3Client client = null;
				client.putObject("bucket", "key", new File(""));
			});
			
			futures.add(future);
		}

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));
	}
}

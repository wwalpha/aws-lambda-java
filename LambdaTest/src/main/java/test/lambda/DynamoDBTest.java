package test.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class DynamoDBTest implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_1).build();

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

		DynamoDBMapper mapper = new DynamoDBMapper(client);
		AppSync table = new AppSync();
		table.setId("1a74406f-1723-46c3-9b58-bfc5f02a2896");
		table.setTimestamp("20180729131411");

		AppSync ret = mapper.load(table);

		System.out.println(ret);

		return null;
	}

}

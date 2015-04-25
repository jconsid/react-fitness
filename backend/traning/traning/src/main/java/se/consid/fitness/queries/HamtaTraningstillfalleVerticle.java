package se.consid.fitness.queries;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class HamtaTraningstillfalleVerticle extends Verticle {

	@Override
	public void start() {

		final Handler<Message<JsonObject>> hamtaTraningstillfalle = new Handler<Message<JsonObject>>() {

			@Override
			public void handle(final Message<JsonObject> request) {
				container.logger().info(request.body());

				final JsonObject query = createQuery(request.body());

				vertx.eventBus().send("test.mongodb", query, (Handler<Message<JsonObject>>) dbResponse -> {
					final JsonObject answer = dbResponse.body();
					container.logger().info(answer);

					request.reply(answer);

				});

			}
		};

		vertx.eventBus().registerHandler("query.hamta.traningstillfalle", hamtaTraningstillfalle,
				result -> container.logger().info("Hämta träningstillfälle " + result.succeeded()));
	}

	private JsonObject createQuery(JsonObject body) {
		final JsonObject query = new JsonObject();
		query.putString("action", "find");
		query.putString("collection", "traningstillfalle");
		query.putString("_id", body.getString("_id"));
		return query;
	}
}

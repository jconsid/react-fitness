package se.consid.fitness.projections;

import java.util.Iterator;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class TraningstillfallenPerAnvandareRedigeradVerticle extends Verticle {

	@Override
	public void start() {
		vertx.eventBus().registerHandler("TraningsaktivitetRegistrerad", (Handler<Message<JsonObject>>) message -> {
			container.logger().info(message.body());

			final JsonObject query = createFindQuery(createMatcher(message.body()));

			vertx.eventBus().send("test.mongodb", query, (Handler<Message<JsonObject>>) dbResponse -> {
				final JsonObject answer = dbResponse.body();
				container.logger().info(answer);

				if (answer.getArray("results").size() > 0) {
					// update
					final JsonObject update = createUpdate(message.body(), answer.getArray("results").get(0));
					vertx.eventBus().send("test.mongodb", update, (Handler<Message<JsonObject>>) dbResponse2 -> {
						container.logger().info("Update : " + dbResponse2.body());
					});
				} else {
					container.logger().warn("Projection not found");
				}

			});
		});
	}

	private JsonObject createMatcher(JsonObject body) {
		final JsonObject matcher = new JsonObject();
		matcher.putString("_id", body.getObject("inloggadanvandare").getString("_id"));
		return matcher;
	}

	private JsonObject createFindQuery(JsonObject body) {
		final JsonObject query = new JsonObject();
		query.putString("action", "find");
		query.putString("collection", "traningstillfallenperanvandare");
		query.putObject("matcher", body);
		return query;
	}

	private JsonObject createUpdate(JsonObject body, JsonObject oldObject) {
		final JsonObject update = new JsonObject();
		update.putString("action", "update");
		update.putString("collection", "traningstillfallenperanvandare");
		update.putObject("criteria", createMatcher(body));

		final Iterator<Object> iterator = oldObject.getArray("traningstillfallen").iterator();
		while (iterator.hasNext()) {
			final JsonObject ttf = (JsonObject) iterator.next();
			if (ttf.getString("_id").equals(body.getString("_id"))) {
				ttf.putString("varde", body.getString("varde"));
				ttf.putString("datum", body.getString("datum"));
				ttf.putString("namn", body.getString("namn"));
				ttf.putString("poang", body.getString("poang"));
				ttf.putString("enhet", body.getString("enhet"));
				break;
			}
		}
		update.putObject("objNew", oldObject);
		return update;
	}
}

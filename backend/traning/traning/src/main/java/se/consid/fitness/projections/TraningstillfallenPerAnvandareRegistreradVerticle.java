package se.consid.fitness.projections;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class TraningstillfallenPerAnvandareRegistreradVerticle extends Verticle {

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
					// insert
					final JsonObject insert = createInsert(message.body());
					vertx.eventBus().send("test.mongodb", insert, (Handler<Message<JsonObject>>) dbResponse2 -> {
						container.logger().info("Insert : " + dbResponse2.body());
					});
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

		final JsonObject traningstillfalle = new JsonObject();
		traningstillfalle.putString("_id", body.getString("_id"));
		traningstillfalle.putString("varde", body.getString("varde"));
		traningstillfalle.putString("datum", body.getString("datum"));
		traningstillfalle.putString("namn", body.getString("namn"));
		traningstillfalle.putString("poang", body.getString("poang"));
		traningstillfalle.putString("enhet", body.getString("enhet"));
		final JsonObject push = new JsonObject();
		push.putObject("$push", new JsonObject().putObject("traningstillfallen", traningstillfalle));
		update.putObject("objNew", push);
		return update;
	}

	private JsonObject createInsert(JsonObject body) {
		final JsonObject insert = new JsonObject();
		insert.putString("action", "save");
		insert.putString("collection", "traningstillfallenperanvandare");

		final JsonObject document = new JsonObject();
		document.putString("_id", body.getObject("inloggadanvandare").getString("_id"));
		document.putString("namn", body.getObject("inloggadanvandare").getString("namn"));
		document.putString("kontorstillhorighet", body.getObject("inloggadanvandare").getString("kontorstillhorighet"));
		final JsonArray traningstillfallen = new JsonArray();
		final JsonObject traningstillfalle = new JsonObject();
		traningstillfalle.putString("_id", body.getString("_id"));
		traningstillfalle.putString("varde", body.getString("varde"));
		traningstillfalle.putString("datum", body.getString("datum"));
		traningstillfalle.putString("namn", body.getString("namn"));
		traningstillfalle.putString("poang", body.getString("poang"));
		traningstillfalle.putString("enhet", body.getString("enhet"));
		traningstillfallen.add(traningstillfalle);
		document.putArray("traningstillfallen", traningstillfallen);
		insert.putObject("document", document);
		return insert;
	}
}

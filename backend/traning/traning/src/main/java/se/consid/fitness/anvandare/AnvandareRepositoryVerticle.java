/**
 *
 */
package se.consid.fitness.anvandare;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

/**
 */
public class AnvandareRepositoryVerticle extends Verticle {

	@Override
	public void start() {
		final Logger log = container.logger();
		final EventBus eb = vertx.eventBus();

		eb.registerHandler("anvandareRepository.load", (final Message<String> message) -> {
			final String id = message.body();

			log.info("anvandareId: " + id);

			eb.send("test.mongodb", query(id), (Handler<Message<JsonObject>>) dbResponse -> {
				final JsonArray results = dbResponse.body().getArray("results");
				final JsonObject result = results.get(0);

				log.info("dbResponse -> " + result);

				message.reply(result);
			});
		});

		log.info("AnvandareRepository started.");
	}

	private JsonObject query(final String id) {
		final JsonObject query = new JsonObject();
		query.putString("action", "find");
		query.putString("collection", "anvandare");
		query.putObject("matcher", new JsonObject().putString("_id", id));

		return query;
	}

}

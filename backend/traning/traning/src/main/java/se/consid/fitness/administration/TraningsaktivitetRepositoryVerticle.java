/**
 *
 */
package se.consid.fitness.administration;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

/**
 */
public class TraningsaktivitetRepositoryVerticle extends Verticle {

	@Override
	public void start() {
		final Logger log = container.logger();
		final EventBus eb = vertx.eventBus();

		eb.registerHandler("traningsaktivitetRepository.load", (final Message<Integer> message) -> {
			final int id = message.body();

			eb.send("test.mongodb", query(id), (Handler<Message<JsonObject>>) dbResponse -> {
				final JsonArray results = dbResponse.body().getArray("results");
				final JsonObject result = results.get(0);

				message.reply(result);
			});
		});

		log.info("TraningsaktivitetRepository started.");
	}

	private JsonObject query(final int id) {
		final JsonObject query = new JsonObject();
		query.putString("action", "find");
		query.putString("collection", "traningsaktiviteter");
		query.putObject("matcher", new JsonObject().putNumber("_id", id));

		return query;
	}
}

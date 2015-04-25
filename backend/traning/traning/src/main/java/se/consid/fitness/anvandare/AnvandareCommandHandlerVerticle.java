package se.consid.fitness.anvandare;

import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

public class AnvandareCommandHandlerVerticle extends Verticle {

	@Override
	public void start() {
		final Logger logger = container.logger();
		final EventBus eb = vertx.eventBus();

		eb.registerHandler("anvandareCommandHandler", message -> {
			final JsonObject json = (JsonObject) message.body();
			logger.info(json);
		});
	}
}

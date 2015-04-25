package se.consid.fitness.anvandare;

import static se.consid.fitness.commands.RegistreraTraningsaktivitet.from;

import java.util.concurrent.CompletableFuture;

import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

import se.consid.fitness.anvandare.domain.Traningsaktivitet;
import se.consid.fitness.commands.RegistreraTraningsaktivitet;

public class AnvandareCommandHandlerVerticle extends Verticle {

	@Override
	public void start() {
		final Logger logger = container.logger();
		final EventBus eb = vertx.eventBus();

		eb.registerHandler(
				"anvandareCommandHandler",
				message -> {
					final JsonObject json = (JsonObject) message.body();
					final RegistreraTraningsaktivitet registreraTraningsaktivitet = from(json);

					final Traningsaktivitet aktivitet = getTraningsaktivitet(registreraTraningsaktivitet
							.getTraningsaktivitetId());

					logger.info(aktivitet);
				});
	}

	private Traningsaktivitet getTraningsaktivitet(final String traningsaktivitetId) {
		final CompletableFuture<Traningsaktivitet> aktivitet = new CompletableFuture<>();

		vertx.eventBus().send("anvandareRepository.load", traningsaktivitetId, (final Message<JsonObject> message) -> {
			aktivitet.complete(Traningsaktivitet.from(message.body()));
		});

		try {
			return aktivitet.get();
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

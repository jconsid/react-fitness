package se.consid.fitness.anvandare;

import static se.consid.fitness.commands.RegistreraTraningsaktivitet.from;

import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

import se.consid.fitness.administration.domain.Traningsaktivitet;
import se.consid.fitness.anvandare.domain.Anvandare;
import se.consid.fitness.commands.RegistreraTraningsaktivitet;

public class AnvandareCommandHandlerVerticle extends Verticle {

	@Override
	public void start() {
		final Logger log = container.logger();
		final EventBus eb = vertx.eventBus();

		eb.registerHandler("anvandareCommandHandler", message -> {
			final JsonObject json = (JsonObject) message.body();
			final RegistreraTraningsaktivitet registreraTraningsaktivitet = from(json);

			getTraningsaktivitet(registreraTraningsaktivitet.getTraningsaktivitetId(), registreraTraningsaktivitet);
		});

		log.info("AnvandareCommandHandler started.");
	}

	private void getTraningsaktivitet(final int traningsaktivitetId,
			final RegistreraTraningsaktivitet registreraTraningsaktivitet) {
		vertx.eventBus().send("traningsaktivitetRepository.load", traningsaktivitetId,
				(final Message<JsonObject> message) -> {
					final Traningsaktivitet aktivitet = Traningsaktivitet.from(message.body());
					getAnvandare(registreraTraningsaktivitet, aktivitet);
				});
	}

	private void getAnvandare(final RegistreraTraningsaktivitet registreraTraningsaktivitet,
			final Traningsaktivitet aktivitet) {
		final EventBus eb = vertx.eventBus();

		eb.send("anvandareRepository.load", registreraTraningsaktivitet.getAnvandareId(), (
				final Message<JsonObject> message) -> {
			final Anvandare anvandare = Anvandare.from(message.body());
			container.logger().info("Fick " + anvandare);

			anvandare.registreraTraningsaktivitet(aktivitet, registreraTraningsaktivitet);
			anvandare.getUncommittedEvents().forEach(e -> eb.publish("TraningsaktivitetRegistrerad", e.toJson()));
		});
	}
}

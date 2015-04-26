/**
 *
 */
package se.consid.fitness;

import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

/**
 */
public class CommandVerticle extends Verticle {

	@Override
	public void start() {
		final Logger log = container.logger();
		final EventBus eb = vertx.eventBus();

		final RouteMatcher routeMatcher = new RouteMatcher();
		routeMatcher.post("/requests", req -> {
			req.bodyHandler(buffer -> {
				final String json = buffer.getString(0, buffer.length());
				final JsonObject request = new JsonObject(json);

				switch (request.getString("typ")) {
				case "registreraTraningstillfalle":
					eb.send("anvandareCommandHandler", request);
					break;

				default:
					break;
				}
			});

			req.response().setStatusCode(402).end();
		});

		vertx.createHttpServer().requestHandler(routeMatcher).listen(8100);

		log.info("Command server started.");
	}
}

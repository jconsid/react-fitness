/**
 *
 */
package se.consid.fitness;

import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

/**
 */
public class CommandVerticle extends Verticle {

	@Override
	public void start() {
		final Logger log = container.logger();

		final HttpServer server = vertx.createHttpServer();

		server.requestHandler(req -> {
			log.info("A request has arrived on the server!");
			req.response().end();
		});

		server.listen(8000);

		log.info("Command server started.");
	}
}

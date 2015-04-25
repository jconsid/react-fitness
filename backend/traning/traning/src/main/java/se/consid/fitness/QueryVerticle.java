package se.consid.fitness;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class QueryVerticle extends Verticle {

	@Override
	public void start() {
		final HttpServer httpServer = vertx.createHttpServer();
		final RouteMatcher routeMatcher = createRouteMatcher();

		httpServer.requestHandler(routeMatcher);

		httpServer.listen(8888);
	}

	private RouteMatcher createRouteMatcher() {
		final RouteMatcher rm = new RouteMatcher();

		rm.get("/anvandare/:id/traningstillfalle", new Handler<HttpServerRequest>() {
			@Override
			public void handle(final HttpServerRequest req) {

				final JsonObject query = new JsonObject();
				query.putString("_id", req.params().get("id"));

				vertx.eventBus().send("query.hamta.traningstillfalle", query, new Handler<Message<JsonObject>>() {

					@Override
					public void handle(final Message<JsonObject> dbResponse) {
						final JsonObject answer = dbResponse.body();

						req.response().headers().add("Content-Type", "application/json; charset=utf-8");
						req.response().end(answer.toString());
					}
				});

			}
		});

		return rm;
	}
}

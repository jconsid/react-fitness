package se.consid.fitness;

/**
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */

import org.vertx.java.platform.Verticle;

import se.consid.fitness.queries.camel.RouteManager;

/**
 */
public class StartVerticle extends Verticle {

	@Override
	public void start() {
		container.logger().info("Starting Verticles");

		// Command gateway
		container.deployVerticle("se.consid.fitness.CommandVerticle");

		// Administration
		container.deployVerticle("se.consid.fitness.administration.TraningsaktivitetRepositoryVerticle");

		// Träning
		container.deployVerticle("se.consid.fitness.anvandare.AnvandareCommandHandlerVerticle");
		container.deployVerticle("se.consid.fitness.anvandare.AnvandareRepositoryVerticle");

		container.deployVerticle("se.consid.fitness.queries.HamtaTraningstillfalleVerticle");
		container.deployVerticle("se.consid.fitness.QueryVerticle");

		final RouteManager routeManager = new RouteManager();
		try {
			routeManager.startRoutes();
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

}

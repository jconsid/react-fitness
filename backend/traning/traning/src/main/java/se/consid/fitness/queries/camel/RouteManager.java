package se.consid.fitness.queries.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

public class RouteManager {

	public void startRoutes() throws Exception {

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new EventPublisherRoute());

		camelContext.start();
		ProducerTemplate template = camelContext.createProducerTemplate();
		for (int i = 0; i < 1000000; i++) {
			template.sendBody("direct:start", "This is a test message");
			Thread.sleep(1000);
		}

		camelContext.stop();

	}
}
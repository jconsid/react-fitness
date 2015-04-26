package se.consid.fitness.queries.camel;

import org.apache.camel.builder.RouteBuilder;

public class EventPublisherRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("direct:start")
		  .process(new CreateEvent())
		  .to("vertx:traningstillfalleRegistrerat?pubSub=true")
		  .log("message sent to vertx:traningstillfalleRegistrerat");
		 
	}

}

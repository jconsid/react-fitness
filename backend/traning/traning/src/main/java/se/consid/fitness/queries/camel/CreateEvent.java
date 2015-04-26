package se.consid.fitness.queries.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class CreateEvent implements Processor{


	public void process(Exchange exchange)  throws Exception {
	    exchange.getIn().setBody("{\"_id\":\"1121\", \"namn\":\"kalle\"}");
	}

}

package se.consid.fitness.queries.camel;


import org.apache.camel.builder.RouteBuilder;

public class QueryRoute  extends RouteBuilder{



	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("jetty:http://localhost:8181/myapp/myservice").log("");
		
		
	}
}

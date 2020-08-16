package codefoxx.camel.samples;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class FormRoute extends RouteBuilder {
    /**
     * <b>Called on initialization to build the routes using the fluent builder
     * syntax.</b>
     * <p/>
     * This is a central method for RouteBuilder implementations to implement
     * the routes using the Java fluent builder syntax.
     *
     * @throws Exception can be thrown during configuration
     */
    @Override
    public void configure() throws Exception {
        JsonDataFormat json = new JsonDataFormat(JsonLibrary.Jackson);

        restConfiguration()
                .contextPath("/camel")
                .bindingMode(RestBindingMode.auto)
        ;

        rest("forms")
                .post()
                    .type(TokenExchangePojo.class)
                    .consumes(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .route()
                    .log("${body}")
                .endRest()
        ;

        onException(Exception.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        exchange.getMessage().setHeaders(exchange.getIn().getHeaders());
                        exchange.getMessage().setBody(String.format("error : %s", e.getMessage()));
                    }
                })
                .log(exceptionMessage().toString())
                .handled(true)
                .transform(simpleF("error : %s", exceptionMessage()))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(503))
                .end()
        ;
    }
}

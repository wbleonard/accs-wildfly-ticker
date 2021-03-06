package com.oracle.cloud.rest;

import com.oracle.cloud.util.StockDataParser;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 * REST endpoint to fetch stock price on demand
 * 
 * @author Abhishek
 */
@Path("stocks")
public class StockPriceResource {

    /**
     * Implements logic to fetch price. Uses JAX-RS client API. Handles invalid ticker symbol
     * 
     * @param ticker
     * @return the formatted text response with price
     */
    @GET
    public String getQuote(@QueryParam("ticker") final String ticker) {

        Response response = ClientBuilder.newClient().
                target("https://www.google.com/finance/info?q=NASDAQ:" + ticker).
                request().get();

        if (response.getStatus() != 200) {
            //throw new WebApplicationException(Response.Status.NOT_FOUND);
            return String.format("Could not find price for ticker %s", ticker, ". Check your symbol and try again.");
        }
        String tick = response.readEntity(String.class);
        // I'm not sure why the response is preprended with "//"...
        tick = tick.replace("//", "");
        
        //Removes JSON formatting from response...
        //tick = tick.replace("// [", "");
        //tick = tick.replace("]", "");
        
        // For a user friently response...
        //return StockDataParser.parse(tick);
        return tick;
    }

}

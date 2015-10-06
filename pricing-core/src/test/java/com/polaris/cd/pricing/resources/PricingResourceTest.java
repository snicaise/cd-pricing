package com.polaris.cd.pricing.resources;

import com.polaris.cd.pricing.api.ErrorMessage;
import com.polaris.cd.pricing.api.Pricing;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class PricingResourceTest {

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PricingResource())
            .build();

    @Test
    public void pricing_aTrip_returnAmount() throws Exception {
        Response response = resources.client()
            .target("/pricing/price")
                .queryParam("origin", "london")
                .queryParam("destination", "paris")
            .request().get();
        Pricing pricing = response.readEntity(Pricing.class);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(pricing.getPrice()).isNotNegative().isNotZero();
    }

    @Test
    public void pricing_aTrip_returnSameLocation() throws Exception {
        Response response = resources.client()
            .target("/pricing/price")
                .queryParam("origin", "london")
                .queryParam("destination", "paris")
            .request().get();
        Pricing pricing = response.readEntity(Pricing.class);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(pricing.getOrigin()).isEqualTo("london");
        assertThat(pricing.getDestination()).isEqualTo("paris");
    }

    @Test
    public void pricing_aTrip_returnJson() throws Exception {
        Response response = resources.client()
            .target("/pricing/price")
                .queryParam("origin", "london")
                .queryParam("destination", "paris")
            .request().get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void pricing_noOrigin_returnError() throws Exception {
        Response response = resources.client()
            .target("/pricing/price")
                .queryParam("destination", "paris")
            .request().get();

        assertThat(response.getStatus()).isEqualTo(400);

        ErrorMessage error = response.readEntity(ErrorMessage.class);
        assertThat(error.getMessage()).contains("origin", "mandatory");
    }

    @Test
    public void pricing_noDestination_returnError() throws Exception {
        Response response = resources.client()
            .target("/pricing/price")
                .queryParam("origin", "paris")
            .request().get();

        assertThat(response.getStatus()).isEqualTo(400);

        ErrorMessage error = response.readEntity(ErrorMessage.class);
        assertThat(error.getMessage()).contains("destination", "mandatory");
    }

    @Test
    public void pricing_sameOriginDestination_returnError() throws Exception {
        Response response = resources.client()
            .target("/pricing/price")
            .queryParam("origin", "paris")
            .queryParam("destination", "paris")
            .request().get();

        assertThat(response.getStatus()).isEqualTo(400);

        ErrorMessage error = response.readEntity(ErrorMessage.class);
        assertThat(error.getMessage()).contains("destination", "origin");
    }

}
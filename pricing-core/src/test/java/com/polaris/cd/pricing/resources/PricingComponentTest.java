package com.polaris.cd.pricing.resources;

import com.jayway.restassured.http.ContentType;
import com.polaris.cd.ComponentTests;
import com.polaris.cd.pricing.PricingApplication;
import com.polaris.cd.pricing.PricingConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 *
 */
@Category(ComponentTests.class)
public class PricingComponentTest {

    @ClassRule
    public static final DropwizardAppRule<PricingConfiguration> RULE =
            new DropwizardAppRule<>(
                    PricingApplication.class,
                    ResourceHelpers.resourceFilePath("test-server.yml")
            );

    @Test
    public void pricing_aTrip_returnAmount() {
        given()
            .port(RULE.getLocalPort())
            .queryParam("origin", "paris")
            .queryParam("destination", "london")
        .when()
            .get("/pricing/price")
        .then()
            .log().all().and()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("origin", equalTo("paris"))
            .body("destination", equalTo("london"))
            .body("price", notNullValue());
    }

}
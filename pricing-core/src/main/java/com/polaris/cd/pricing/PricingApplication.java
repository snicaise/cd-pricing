package com.polaris.cd.pricing;

import com.codahale.metrics.health.HealthCheck;
import com.polaris.cd.pricing.resources.PricingResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 *
 */
public class PricingApplication extends Application<PricingConfiguration> {

    public static void main(String[] args) throws Exception {
        new PricingApplication().run(args);
    }

    @Override
    public String getName() {
        return "pricing";
    }

    @Override
    public void initialize(Bootstrap<PricingConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
    }

    @Override
    public void run(PricingConfiguration configuration, Environment environment) {
        environment.healthChecks().register("pricing", new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return Result.healthy();
            }
        });

        environment.jersey().register(new PricingResource());
    }
}

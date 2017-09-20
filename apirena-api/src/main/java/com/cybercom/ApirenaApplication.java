package com.cybercom;

import com.cybercom.resources.GameStateResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class ApirenaApplication extends Application<ApirenaConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ApirenaApplication().run(args);
    }

    @Override
    public String getName() {
        return "apirena";
    }

    @Override
    public void initialize(final Bootstrap<ApirenaConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<ApirenaConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ApirenaConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final ApirenaConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new GameStateResource());
    }

}

package nl.hsleiden;

import com.codahale.metrics.health.HealthCheck;

public class ApiCheck extends HealthCheck {
    private final String version;

    public ApiCheck(String version) {
        this.version = version;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy("OK with version: " + this.version);
    }
}

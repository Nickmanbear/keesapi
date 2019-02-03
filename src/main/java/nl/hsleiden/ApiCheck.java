package nl.hsleiden;

import com.codahale.metrics.health.HealthCheck;

public class ApiCheck extends HealthCheck {
    private final String version;

    public ApiCheck(String version) {
        this.version = version;
    }

    @Override
    protected Result check() throws Exception {
//        if (PersonDB.getCount() == 0) {
//            return Result.unhealthy("No persons in DB! Version: " +
//                    this.version);
//        }
        return Result.healthy("OK with version: " + this.version);
    }
}

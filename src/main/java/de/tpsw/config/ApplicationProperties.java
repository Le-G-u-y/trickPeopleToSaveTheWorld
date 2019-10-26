package de.tpsw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Trick People To Save The World.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
}

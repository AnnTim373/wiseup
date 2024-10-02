package org.timofeeva.wiseup.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
}

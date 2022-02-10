package com.yoti.test.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.constraints")
@Accessors(chain = true)
public class CleaningConstraintProperties {
    @Value("${app.constraints.route-length:1000}")
    private int routeLength;
    @Value("${app.constraints.room-size-x:100}")
    private int roomSizeX;
    @Value("${app.constraints.room-size-y:100}")
    private int roomSizeY;
    @Value("${app.constraints.patch-count:1000}")
    private int patchCount;
}

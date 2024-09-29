package com.dnnsvrmln.hubservice.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class BartenderConfiguration {

    @Value("${bartenders.on.duty}")
    private int bartendersOnDuty;

}

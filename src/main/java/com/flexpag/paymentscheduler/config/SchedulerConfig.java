package com.flexpag.paymentscheduler.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
    Ativação do scheduler para a automatização dos pagamentos
 */

@Configuration
@EnableScheduling
@EnableAsync
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class SchedulerConfig {
}

package org.millida.duneconquest.configuration;

import lombok.Builder;

public class MainConfiguration extends AbstractConfiguration {
    @Builder
    public MainConfiguration() {
        super("config.yml");
    }

    @Override
    public void enable() {
        this.createConfig();
    }

    @Override
    public void disable() {
    }
}
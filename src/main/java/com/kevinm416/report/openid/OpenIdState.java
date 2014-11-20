package com.kevinm416.report.openid;

import org.openid4java.consumer.ConsumerManager;

public class OpenIdState {

    private final ConsumerManager consumerManager;
    private final DiscoveryInformationMemento discoveryInformationMemento;

    public OpenIdState(ConsumerManager consumerManager,
            DiscoveryInformationMemento discoveryInformationMemento) {
        this.consumerManager = consumerManager;
        this.discoveryInformationMemento = discoveryInformationMemento;
    }

    public ConsumerManager getConsumerManager() {
        return consumerManager;
    }

    public DiscoveryInformationMemento getDiscoveryInformationMemento() {
        return discoveryInformationMemento;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((consumerManager == null) ? 0 : consumerManager.hashCode());
        result = prime
                * result
                + ((discoveryInformationMemento == null) ? 0
                        : discoveryInformationMemento.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OpenIdState other = (OpenIdState) obj;
        if (consumerManager == null) {
            if (other.consumerManager != null) {
                return false;
            }
        } else if (!consumerManager.equals(other.consumerManager)) {
            return false;
        }
        if (discoveryInformationMemento == null) {
            if (other.discoveryInformationMemento != null) {
                return false;
            }
        } else if (!discoveryInformationMemento
                .equals(other.discoveryInformationMemento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OpenIdState [consumerManager=" + consumerManager
                + ", discoveryInformationMemento="
                + discoveryInformationMemento + "]";
    }

}

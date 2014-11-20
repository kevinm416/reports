package com.kevinm416.report.openid;

import java.util.Set;

public class DiscoveryInformationMemento {

    private final String opEndpoint;
    private final String claimedIdentifier;
    private final String delegate;
    private final String version;
    private final Set<String> types;

    public DiscoveryInformationMemento(String opEndpoint,
            String claimedIdentifier, String delegate, String version,
            Set<String> types) {
        this.opEndpoint = opEndpoint;
        this.claimedIdentifier = claimedIdentifier;
        this.delegate = delegate;
        this.version = version;
        this.types = types;
    }

    public String getOpEndpoint() {
        return opEndpoint;
    }

    public String getClaimedIdentifier() {
        return claimedIdentifier;
    }

    public String getDelegate() {
        return delegate;
    }

    public String getVersion() {
        return version;
    }

    public Set<String> getTypes() {
        return types;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((claimedIdentifier == null) ? 0 : claimedIdentifier
                        .hashCode());
        result = prime * result
                + ((delegate == null) ? 0 : delegate.hashCode());
        result = prime * result
                + ((opEndpoint == null) ? 0 : opEndpoint.hashCode());
        result = prime * result + ((types == null) ? 0 : types.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
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
        DiscoveryInformationMemento other = (DiscoveryInformationMemento) obj;
        if (claimedIdentifier == null) {
            if (other.claimedIdentifier != null) {
                return false;
            }
        } else if (!claimedIdentifier.equals(other.claimedIdentifier)) {
            return false;
        }
        if (delegate == null) {
            if (other.delegate != null) {
                return false;
            }
        } else if (!delegate.equals(other.delegate)) {
            return false;
        }
        if (opEndpoint == null) {
            if (other.opEndpoint != null) {
                return false;
            }
        } else if (!opEndpoint.equals(other.opEndpoint)) {
            return false;
        }
        if (types == null) {
            if (other.types != null) {
                return false;
            }
        } else if (!types.equals(other.types)) {
            return false;
        }
        if (version == null) {
            if (other.version != null) {
                return false;
            }
        } else if (!version.equals(other.version)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DiscoveryInformationMemento [opEndpoint=" + opEndpoint
                + ", claimedIdentifier=" + claimedIdentifier + ", delegate="
                + delegate + ", version=" + version + ", types=" + types + "]";
    }

}

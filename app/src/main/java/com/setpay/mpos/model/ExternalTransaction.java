package com.setpay.mpos.model;

/**
 * Created by Lichi on 15/07/2016.
 */
public class ExternalTransaction {

    String externalTransactionId;
    long version;
    ExternalSystem externalSystem;

    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public ExternalSystem getExternalSystem() {
        return externalSystem;
    }

    public void setExternalSystem(ExternalSystem externalSystem) {
        this.externalSystem = externalSystem;
    }
}

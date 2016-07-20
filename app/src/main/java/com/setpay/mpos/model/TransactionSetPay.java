package com.setpay.mpos.model;

/**
 * Created by Lichi on 15/07/2016.
 */
import android.os.Parcel;
import android.os.Parcelable;

public class TransactionSetPay implements Parcelable {

    private String concept;
    private String currency;
    private Double amount;
    private long datetime;
    private Receipt receipt;
    private ExternalTransaction externalTransaction;
    private Double appliedTaxPercent;
    private Double gpsLongitude;
    private Double gpsLatitude;
    private long transactionId;
    private long entityId;
    private long merchantId;
    private PaymentMethod paymentMethod;
    private TransactionType transactionType;
    private Long referred;

    @Override
    public String toString() {
        return "TransactionSetPay{" +
                "concept='" + concept + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", datetime=" + datetime +
                ", receipt=" + receipt +
                ", externalTransaction=" + externalTransaction +
                ", appliedTaxPercent=" + appliedTaxPercent +
                ", gpsLongitude=" + gpsLongitude +
                ", gpsLatitude=" + gpsLatitude +
                ", transactionId=" + transactionId +
                ", entityId=" + entityId +
                ", merchantId=" + merchantId +
                ", paymentMethod=" + paymentMethod +
                ", transactionType=" + transactionType +
                ", referred=" + referred +
                '}';
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public ExternalTransaction getExternalTransaction() {
        return externalTransaction;
    }

    public void setExternalTransaction(ExternalTransaction externalTransaction) {
        this.externalTransaction = externalTransaction;
    }

    public Double getAppliedTaxPercent() {
        return appliedTaxPercent;
    }

    public void setAppliedTaxPercent(Double appliedTaxPercent) {
        this.appliedTaxPercent = appliedTaxPercent;
    }

    public Double getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(Double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public Double getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(Double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getReferred() {
        return referred;
    }

    public void setReferred(Long referred) {
        this.referred = referred;
    }

    public TransactionSetPay(){

    }

    public TransactionSetPay(Parcel in) {
        amount = in.readDouble();
        concept = in.readString();
        currency = in.readString();
        datetime = in.readLong();
        transactionId = in.readLong();
        merchantId = in.readLong();
        entityId = in.readLong();
    }

    public static final Creator<TransactionSetPay> CREATOR = new Creator<TransactionSetPay>() {
        @Override
        public TransactionSetPay createFromParcel(Parcel in) {
            return new TransactionSetPay(in);
        }

        @Override
        public TransactionSetPay[] newArray(int size) {
            return new TransactionSetPay[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeString(concept);
        dest.writeString(currency);
        dest.writeLong(datetime);
        dest.writeLong(transactionId);
        dest.writeLong(merchantId);
        dest.writeLong(entityId);
    }
}

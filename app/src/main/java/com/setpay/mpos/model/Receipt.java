package com.setpay.mpos.model;

/**
 * Created by Lichi on 15/07/2016.
 */

import java.util.ArrayList;

public class Receipt {
    long merchantZipCode;
    String aid;
    long processorResponseCode;
    long terminalId;
    String apLabel;
    String response;
    long cardSequence;
    ArrayList saleItems;
    String merchantName;
    String entryMethod;
    String obfuscatedCardNumber;
    String cardType;
    String processorRef;
    String processorName;
    String clientVerificationMethod;
    String industryType;
    String merchantAddress;
    String tenderType;
    String merchantCity;
    String approvalCode;

    public long getMerchantZipCode() {
        return merchantZipCode;
    }

    public void setMerchantZipCode(long merchantZipCode) {
        this.merchantZipCode = merchantZipCode;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public long getProcessorResponseCode() {
        return processorResponseCode;
    }

    public void setProcessorResponseCode(long processorResponseCode) {
        this.processorResponseCode = processorResponseCode;
    }

    public long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(long terminalId) {
        this.terminalId = terminalId;
    }

    public String getApLabel() {
        return apLabel;
    }

    public void setApLabel(String apLabel) {
        this.apLabel = apLabel;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public long getCardSequence() {
        return cardSequence;
    }

    public void setCardSequence(long cardSequence) {
        this.cardSequence = cardSequence;
    }

    public ArrayList getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(ArrayList saleItems) {
        this.saleItems = saleItems;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getEntryMethod() {
        return entryMethod;
    }

    public void setEntryMethod(String entryMethod) {
        this.entryMethod = entryMethod;
    }

    public String getObfuscatedCardNumber() {
        return obfuscatedCardNumber;
    }

    public void setObfuscatedCardNumber(String obfuscatedCardNumber) {
        this.obfuscatedCardNumber = obfuscatedCardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getProcessorRef() {
        return processorRef;
    }

    public void setProcessorRef(String processorRef) {
        this.processorRef = processorRef;
    }

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public String getClientVerificationMethod() {
        return clientVerificationMethod;
    }

    public void setClientVerificationMethod(String clientVerificationMethod) {
        this.clientVerificationMethod = clientVerificationMethod;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getTenderType() {
        return tenderType;
    }

    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    public String getMerchantCity() {
        return merchantCity;
    }

    public void setMerchantCity(String merchantCity) {
        this.merchantCity = merchantCity;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }
}
package com.ecommerce.bestNutrition.model;

public class PaymentDetails {

    private String paymentMethod;
    private String paymentStatus;
    private String paymentId;
    private String mercadoPagoPaymentLinkId;
    private String mercadoPagoPaymentLinkReferenceId;
    private String mercadoPagoPaymentLinkStatus;
    private String mercadoPagoPaymentId;

    public PaymentDetails(){}

    public PaymentDetails(String paymentMethod, String paymentStatus, String paymentId, String mercadoPagoPaymentLinkId, String mercadoPagoPaymentLinkReferenceId, String mercadoPagoPaymentLinkStatus, String mercadoPagoPaymentId) {
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentId = paymentId;
        this.mercadoPagoPaymentLinkId = mercadoPagoPaymentLinkId;
        this.mercadoPagoPaymentLinkReferenceId = mercadoPagoPaymentLinkReferenceId;
        this.mercadoPagoPaymentLinkStatus = mercadoPagoPaymentLinkStatus;
        this.mercadoPagoPaymentId = mercadoPagoPaymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getMercadoPagoPaymentLinkId() {
        return mercadoPagoPaymentLinkId;
    }

    public void setMercadoPagoPaymentLinkId(String mercadoPagoPaymentLinkId) {
        this.mercadoPagoPaymentLinkId = mercadoPagoPaymentLinkId;
    }

    public String getMercadoPagoPaymentLinkReferenceId() {
        return mercadoPagoPaymentLinkReferenceId;
    }

    public void setMercadoPagoPaymentLinkReferenceId(String mercadoPagoPaymentLinkReferenceId) {
        this.mercadoPagoPaymentLinkReferenceId = mercadoPagoPaymentLinkReferenceId;
    }

    public String getMercadoPagoPaymentLinkStatus() {
        return mercadoPagoPaymentLinkStatus;
    }

    public void setMercadoPagoPaymentLinkStatus(String mercadoPagoPaymentLinkStatus) {
        this.mercadoPagoPaymentLinkStatus = mercadoPagoPaymentLinkStatus;
    }

    public String getMercadoPagoPaymentId() {
        return mercadoPagoPaymentId;
    }

    public void setMercadoPagoPaymentId(String mercadoPagoPaymentId) {
        this.mercadoPagoPaymentId = mercadoPagoPaymentId;
    }
}

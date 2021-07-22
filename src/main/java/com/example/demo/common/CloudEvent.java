package com.example.demo.common;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public abstract class CloudEvent<T extends Data> {

    String specversion;     // The version of CloudEvents Specification this message uses
    String id;              // Unique message ID
    String type;            // The type/name of this message ex: CustomerCreated, CreateCustomer
    String time;            // The time this message occurred, ex: 2019-10-12T07:20:50.52Z (RFC 3339)
    String expirationDate;  // Date after which this message is obsolete, and should NOT be taken into account, ex: 2019-10-12T07:20:50.52Z (RFC 3339)
    // FROM
    String server;          // The application name managing this message
    String source;          // The service name producing this message
    // TO
    String destination;     // The service name where the message should go, optional
    // From WHICH service/request
    String replyTo;         // The service name the reply of a request should be sent to
    String correlationId;   // The Request id when this message is its Reply
    // On behalf WHO
    String userId;          // The user that fired this message ex: system-msg-user@BA-FR
    // For WHAT purpose
    String flowName;        // The global Business Flow this message is participating in
    String flowId;          // Used in Dashboard (i.e. Kibana) to group messages by a same Flow
    // For WHICH Business Transaction
    String sagaName;        // The current Saga (Business Transaction) this message is participating in
    String sagaId;
    // On WHICH resource
    String entity;          // the aggregate root name underlying this message ex: Customer
    String subject;         // the Entity ID ex: 70635875785
    // Custom business data
    String datacontenttype = "application/json;charset=utf-8";    // Optional. The content type of data. RFC2046
    String dataschema;                                            // Optional. Identifies the schema that data adheres to (URI).
    T data;                                                       // Optional. The payload of this message.

    public CloudEvent() {}

    public void initMessage() {
        setType(this.getClass().getSimpleName());
        setTime(new Date().toGMTString());
        setId(UUID.randomUUID().toString());
    }

    public void copyFrom(CloudEvent original) {
        setType(getClass().getSimpleName());
        setTime(original.getTime());
        setId(original.getId());
        setServer(original.getServer());
        setSource(original.getSource());
        setFlowName(original.getFlowName());
        setFlowId(original.getFlowId());
        setSagaName(original.getSagaName());
        setSagaId(original.getSagaId());
        setCorrelationId(original.getCorrelationId());
        setReplyTo(original.getReplyTo());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CloudEvent<?> that = (CloudEvent<?>) o;
        return Objects.equals(id, that.id) && Objects.equals(source, that.source) && Objects.equals(type, that.type) &&
            Objects.equals(time, that.time) && Objects.equals(entity, that.entity) && Objects.equals(subject, that.subject) &&
            Objects.equals(destination, that.destination) && Objects.equals(replyTo, that.replyTo) && Objects.equals(correlationId, that.correlationId) &&
            Objects.equals(userId, that.userId) && Objects.equals(flowName, that.flowName) && Objects.equals(flowId, that.flowId) &&
            Objects.equals(datacontenttype, that.datacontenttype) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, type, time, entity, subject, destination, replyTo, correlationId, userId, flowName, flowId, datacontenttype, data);
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getSagaName() {
        return sagaName;
    }

    public void setSagaName(String sagaName) {
        this.sagaName = sagaName;
    }

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getDatacontenttype() {
        return datacontenttype;
    }

    public void setDatacontenttype(String datacontenttype) {
        this.datacontenttype = datacontenttype;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
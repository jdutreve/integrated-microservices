package com.example.demo.common;

/**
 * Used for setting bidirectional dependencies between Service and Gateway.
 */
public interface Gateway<T> {
    void setBusinessInterface(T businessInterface);
}
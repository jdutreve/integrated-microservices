package com.example.demo.frontend.backend.event;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.Data;

import java.util.Objects;

public class ClientStartedEvent extends CloudEvent<ClientStartedEvent.Payload> {

    public ClientStartedEvent() {}

    public ClientStartedEvent(String name) {
        Payload payload = new Payload(name);
        this.setData(payload);
    }

    public static class Payload extends Data {
        public String name = null;

        public Payload() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Payload payload = (Payload) o;
            return Objects.equals(name, payload.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        public Payload(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
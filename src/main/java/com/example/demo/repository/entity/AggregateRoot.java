package com.example.demo.repository.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregateRoot {

    private final List<Object> domainEvents = new ArrayList<>();

    protected void addDomainEvent(Object event) {
        domainEvents.add(event);
    }

    public List<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}

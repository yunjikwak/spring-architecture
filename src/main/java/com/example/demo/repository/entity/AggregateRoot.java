package com.example.demo.repository.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregateRoot {
    private final List<Object> domainEvents = new ArrayList<>();

    // protected -> 서브 클래스에서만 접근 가능
    // 외부에서 X -> AggregateRoot를 상속받은 클래스에서만 접근 가능
    protected void addDomainEvent(Object event) { // 이벤트 추가 메서드
        this.domainEvents.add(event);
    }

    public List<Object> getDomainEvents() { // 이벤트 조회
        // 불변 리스트 return -> 외부 수정 불가
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() { // 이벤트 초기화 메서드
        this.domainEvents.clear();
    }
}

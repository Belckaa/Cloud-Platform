package com.cloudplatform.application.port;

public interface IEventPublisher {
    void publish(Object event);
}

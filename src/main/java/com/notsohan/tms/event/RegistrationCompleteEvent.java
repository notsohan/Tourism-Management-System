package com.notsohan.tms.event;

import com.notsohan.tms.model.Tourist;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent
        extends ApplicationEvent {
    private Tourist tourist;
    private String applicationUrl;

    public RegistrationCompleteEvent(Tourist tourist,
                                     String applicationUrl) {
        super(tourist);
        this.tourist = tourist;
        this.applicationUrl = applicationUrl;
    }
}

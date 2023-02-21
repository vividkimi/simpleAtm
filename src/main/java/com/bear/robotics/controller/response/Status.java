package com.bear.robotics.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    SUCCESS("success"),
    FAIL("fail"),
    MAJOR_FAILURE("major failure(-customer/company- loss occurred)")
    ;

    private String message;
}

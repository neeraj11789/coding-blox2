package io.neeraj.p4.codingblox.constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
public class WebException extends RuntimeException{

    private Integer code;

    private Integer clientCode;

    private String message;
}

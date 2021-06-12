package io.neeraj.p4.codingblox.constant;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public enum Level {
    LOW(-50),
    MEDIUM(-30),
    HIGH(0);

    int contestMarksMap;
}

package io.neeraj.p4.codingblox.domain;

import io.neeraj.p4.codingblox.constant.Level;
import lombok.*;

@Setter
@Getter
public class Question extends BaseDomain{

    private Level level;

    private Integer score;

    public Question(@NonNull Long id, Level level, Integer score) {
        super( id );
        this.level = level;
        this.score = score;
    }
}

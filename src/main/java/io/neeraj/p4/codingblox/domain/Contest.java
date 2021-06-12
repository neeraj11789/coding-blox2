package io.neeraj.p4.codingblox.domain;

import io.neeraj.p4.codingblox.constant.Level;
import lombok.*;


@Setter
@Getter
public class Contest extends BaseDomain{

    @NonNull
    private String name;

    @NonNull
    private Level level;

    @NonNull
    private User creator;

    public Contest(@NonNull Long id, @NonNull String name, @NonNull Level level, @NonNull User creator) {
        super( id );
        this.name = name;
        this.level = level;
        this.creator = creator;
    }
}

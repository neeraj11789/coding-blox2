package io.neeraj.p4.codingblox.domain;

import lombok.*;

@Getter
@Setter
public class User extends BaseDomain{

    private String userId;

    private Integer score = 0;

    public User(@NonNull Long id, String userId) {
        super( id );
        this.userId = userId;
    }
}

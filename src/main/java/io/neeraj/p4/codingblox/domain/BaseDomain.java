package io.neeraj.p4.codingblox.domain;

import lombok.*;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class BaseDomain {

    @NonNull
    protected Long id;

    protected String externalId = UUID.randomUUID().toString();

    // @todo: Other fields like date etc
}

package io.neeraj.p4.codingblox.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ContestParticipant extends BaseDomain{

    private User participant;

    private Contest contest;

    private Integer currentScore;

    public ContestParticipant(@NonNull Long id, User participant, Contest contest, Integer currentScore) {
        super( id );
        this.participant = participant;
        this.contest = contest;
        this.currentScore = currentScore;
    }
}

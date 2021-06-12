package io.neeraj.p4.codingblox.service;

import io.neeraj.p4.codingblox.constant.Level;
import io.neeraj.p4.codingblox.domain.Contest;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface ContestService {

    // @todo : AutoIncrement id
    @NonNull Long createContest(final String contestName, final Level contestLevel, final String contestCreator);

    List<Contest> listContest(Optional<Level> optionalLevel);

    void attendContest(final Long contestId, final String userName);

    void runContest(final Long contestId, final String creatorUserId);

}

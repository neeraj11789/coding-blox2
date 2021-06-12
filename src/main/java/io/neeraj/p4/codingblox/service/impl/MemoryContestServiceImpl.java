package io.neeraj.p4.codingblox.service.impl;

import io.neeraj.p4.codingblox.constant.Level;
import io.neeraj.p4.codingblox.constant.WebException;
import io.neeraj.p4.codingblox.domain.Contest;
import io.neeraj.p4.codingblox.domain.ContestParticipant;
import io.neeraj.p4.codingblox.domain.Question;
import io.neeraj.p4.codingblox.domain.User;
import io.neeraj.p4.codingblox.service.ContestService;
import io.neeraj.p4.codingblox.service.QuestionService;
import io.neeraj.p4.codingblox.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MemoryContestServiceImpl implements ContestService {

    private static Integer counter = 0;

    private static Integer participantCounter = 0;

    private static List<Contest> contestList = new ArrayList<>();

    private static List<ContestParticipant> contestParticipantList = new ArrayList<>();

    private static Integer randomQuestionSolveCount = 2;

    @NonNull
    private UserService userService;

    @NonNull
    private QuestionService questionService;

    @Override
    public @NonNull Long createContest(String contestName, Level contestLevel, String contestCreator) {
        Contest contestObj = getContestObj( contestName, contestLevel, contestCreator );
        ContestParticipant contestParticipantObject = getContestParticipantObject( contestObj, contestCreator );
        contestList.add( contestObj );
        contestParticipantList.add( contestParticipantObject );
        return contestObj.getId();
    }

    private ContestParticipant getContestParticipantObject(Contest contestObj, String contestCreator) {
        Optional<User> user = userService.getUser( contestCreator );
        if(user.isEmpty())
            throw new WebException( 400, 4001, "Contest Creator Not Found" );
        participantCounter++;
        return new ContestParticipant( Long.valueOf( participantCounter ), user.get(), contestObj, 0);
    }

    private Contest getContestObj(String contestName, Level contestLevel, String contestCreator) {
        counter++;
        Optional<User> user = userService.getUser( contestCreator );
        if(user.isEmpty())
            throw new WebException( 400, 4001, "Contest Creator Not Found" );

        // @todo: add check for duplicate contest
        return new Contest( Long.valueOf( counter ), contestName, contestLevel, user.get() );
    }

    @Override
    public List<Contest> listContest(Optional<Level> optionalLevel) {
        List<Contest> contests = contestList;
        optionalLevel.ifPresent( level -> {
            List<Contest> collect = contestList.stream().filter( contest -> contest.getLevel().equals( level ) ).collect( Collectors.toList() );
            contests.clear();
            contests.addAll( collect );
        } );
        return contests;
    }

    @Override
    public void attendContest(Long contestId, String userName) {
        Optional<Contest> contest = findContest( contestId );
        if(contest.isEmpty())
            throw new WebException( 400, 4002, "Contest not found to attend");

        Optional<User> user = userService.getUser( userName );
        if(user.isEmpty())
            throw new WebException( 400, 4003, "User Not Found" );

        participantCounter++;

        ContestParticipant contestParticipant = new ContestParticipant( Long.valueOf( participantCounter ), user.get(), contest.get(), 0 );
        contestParticipantList.add( contestParticipant );
    }

    private Optional<Contest> findContest(Long contestId) {
        return contestList.stream().filter( contest -> contest.getId().equals( contestId ) ).findAny();
    }

    @Override
    public void runContest(Long contestId, String creatorUserId) {

        Optional<Contest> contest = findContest( contestId );
        if(contest.isEmpty())
            throw new WebException( 400, 4002, "Contest not found to attend");

        Optional<User> user = userService.getUser( creatorUserId );
        if(user.isEmpty())
            throw new WebException( 400, 4003, "User Not Found" );

        if(!contest.get().getCreator().equals( user.get() ))
            throw new WebException( 400, 4004, "Only Contest Creator can run the contest" );

        List<ContestParticipant> currentParticipants = contestParticipantList.stream().filter( contestParticipant -> contestParticipant.getContest().equals( contest.get() ) ).collect( Collectors.toList() );
        currentParticipants.forEach( p -> {
            solveRandomQuestionAndUpdateScore(p, contest.get());
        } );

    }

    private void solveRandomQuestionAndUpdateScore(ContestParticipant p, Contest contest) {

        List<Question> questionList = questionService.getQuestion( Optional.of( contest.getLevel() ) );

        if(questionList.size() < randomQuestionSolveCount)
            throw new WebException( 400, 4005, "Not having enough questions to solve" );

        Collections.shuffle( questionList );
        List<Question> subList = questionList.subList( 0, randomQuestionSolveCount );

        Integer currentContestMarks = subList.stream().map( Question::getScore ).collect( Collectors.summingInt( Integer::intValue ) );
        p.setCurrentScore( currentContestMarks );

        updateUserScore(p.getParticipant(), currentContestMarks, contest.getLevel());
    }

    private void updateUserScore(User participant, Integer currentContestMarks, @NonNull Level level) {
        User user = userService.getUser( participant.getUserId() ).get(); // @Note we will get user object here
        user.setScore( user.getScore() + currentContestMarks + level.getContestMarksMap());
    }
}

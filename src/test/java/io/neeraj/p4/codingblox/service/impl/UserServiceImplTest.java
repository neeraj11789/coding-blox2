package io.neeraj.p4.codingblox.service.impl;

import io.neeraj.p4.codingblox.constant.Level;
import io.neeraj.p4.codingblox.domain.Question;
import io.neeraj.p4.codingblox.domain.User;
import io.neeraj.p4.codingblox.service.ContestService;
import io.neeraj.p4.codingblox.service.QuestionService;
import io.neeraj.p4.codingblox.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ServiceImplTest {

    private UserService userService;

    private QuestionService questionService;

    private ContestService contestService;

    private List<User> userList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userService = new MemoryUserServiceImpl();
        questionService = new MemoryQuestionServiceImpl();
        contestService = new MemoryContestServiceImpl( userService, questionService );
    }

    @Test
    void testQuestionLevel() {
        questionService.createQuestion( Level.LOW, 10 );
        questionService.createQuestion( Level.LOW, 20 );
        questionService.createQuestion( Level.LOW, 30 );

        questionService.createQuestion( Level.MEDIUM, 50 );
        questionService.createQuestion( Level.MEDIUM, 55 );

        List<Question> question = questionService.getQuestion( Optional.of( Level.LOW ) );
        Assertions.assertEquals(question.size(), 3);
    }

    @Test
    void testQuestionAllLevel() {
        questionService.createQuestion( Level.LOW, 10 );
        questionService.createQuestion( Level.LOW, 20 );
        questionService.createQuestion( Level.LOW, 30 );

        questionService.createQuestion( Level.MEDIUM, 50 );
        questionService.createQuestion( Level.MEDIUM, 55 );

        List<Question> question  = questionService.getQuestion( Optional.empty() );
        Assertions.assertEquals(question.size(), 5);
    }

    @Test
    void testUserService() {
        userService.createUser( "neeraj" );
        Optional<User> neeraj = userService.getUser( "neeraj" );
        assertTrue( neeraj.isPresent() );
    }

    @Test
    void testAll() {
        userService.createUser( "neeraj" );
        userService.createUser( "trisha" );
        userService.createUser( "dad" );


        questionService.createQuestion( Level.LOW, 110 );
        questionService.createQuestion( Level.LOW, 120 );
        questionService.createQuestion( Level.LOW, 130 );
        questionService.createQuestion( Level.LOW, 150 );
        questionService.createQuestion( Level.LOW, 155 );
        questionService.createQuestion( Level.LOW, 160 );
        questionService.createQuestion( Level.LOW, 165 );

        questionService.createQuestion( Level.HIGH, 200);
        questionService.createQuestion( Level.HIGH, 210);
        questionService.createQuestion( Level.HIGH, 220);
        questionService.createQuestion( Level.HIGH, 230);

        Long contestId = contestService.createContest( "contest1", Level.LOW, "neeraj" );
        contestService.attendContest( contestId, "trisha" );

        contestService.runContest( contestId, "neeraj" );
        System.out.println(userService.getUser( "neeraj" ).get().getScore());
        System.out.println(userService.getUser( "trisha" ).get().getScore());

    }
}
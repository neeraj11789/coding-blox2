package io.neeraj.p4.codingblox.service.impl;

import io.neeraj.p4.codingblox.constant.Level;
import io.neeraj.p4.codingblox.domain.Question;
import io.neeraj.p4.codingblox.service.QuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryQuestionServiceImpl implements QuestionService {

    private static Integer counter = 0;

    private static List<Question> questionList = new ArrayList<>();

    @Override
    public void createQuestion(Level level, Integer score) {
        Question questionObj = getQuestionObj( level, score );
        questionList.add( questionObj );
    }

    private Question getQuestionObj(Level level, Integer score) {
        counter++;
        return new Question( Long.valueOf( counter ), level, score );
    }

    @Override
    public List<Question> getQuestion(Optional<Level> optionalLevel) {
        List<Question> questions = questionList;

        optionalLevel.ifPresent( level -> {
            List<Question> collect = questionList.stream().filter( question -> question.getLevel().equals( level ) ).collect( Collectors.toList() );
            questions.clear();
            questions.addAll( collect );
        } );

        return questions;
    }
}

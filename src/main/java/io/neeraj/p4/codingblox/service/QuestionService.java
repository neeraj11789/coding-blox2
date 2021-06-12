package io.neeraj.p4.codingblox.service;

import io.neeraj.p4.codingblox.constant.Level;
import io.neeraj.p4.codingblox.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    void createQuestion(final Level level, final Integer score);

    List<Question> getQuestion(Optional<Level> optionalLevel);
}

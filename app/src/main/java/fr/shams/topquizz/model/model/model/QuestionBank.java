package fr.shams.topquizz.model.model.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class QuestionBank implements Serializable {

    private List<Question> mQuestionList;
    private int mQuestionIndex;

    public QuestionBank(List<Question> questionList){
        this.mQuestionList = questionList;

        Collections.shuffle(mQuestionList);
    }
    public Question getCurrentQuestion(){
        return this.mQuestionList.get(mQuestionIndex);
    }

    public Question getNextQuestion(){
        mQuestionIndex ++;
        return this.mQuestionList.get(mQuestionIndex);
    }
}

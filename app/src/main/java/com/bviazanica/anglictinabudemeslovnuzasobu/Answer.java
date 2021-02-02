package com.bviazanica.anglictinabudemeslovnuzasobu;

import java.io.Serializable;

public class Answer implements Serializable {
    public String answer;
    public String phrase;
    public boolean result;
    public String rightAnswer;
    public String questionNumber;

    public Answer(String answer, String phrase, boolean result, String rightAnswer,String questionNumber) {
        this.answer = answer;
        this.phrase = phrase;
        this.result = result;
        this.rightAnswer = rightAnswer;
        this.questionNumber = questionNumber;
    }
}

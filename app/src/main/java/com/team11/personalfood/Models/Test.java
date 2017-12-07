package com.team11.personalfood.Models;


public class Test {
    private int questionId;
    private int answer1Id;
    private int answer2Id;
    private int answer3Id;
    private int answer4Id;

    private int type;

    public Test(int questionId, int answer1Id, int answer2Id, int answer3Id, int answer4Id) {
        this.questionId = questionId;
        this.answer1Id = answer1Id;
        this.answer2Id = answer2Id;
        this.answer3Id = answer3Id;
        this.answer4Id = answer4Id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getAnswer1Id() {
        return answer1Id;
    }

    public int getAnswer2Id() {
        return answer2Id;
    }

    public int getAnswer3Id() {
        return answer3Id;
    }

    public int getAnswer4Id() {
        return answer4Id;
    }
}

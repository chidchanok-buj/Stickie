package com.wireless.stickie.Interface

import com.wireless.stickie.Model.CurrentQuestion

interface IAnswerSelect {
    fun selectedAnswer(): CurrentQuestion
    fun showCorrectAnswer()
    fun disableAnswer()
    fun resetQuestion()
}
package com.wireless.stickie.Common

import com.wireless.stickie.Model.Category
import com.wireless.stickie.Model.CurrentQuestion
import com.wireless.stickie.Model.Question
import com.wireless.stickie.QuestionFragment

object Common {
    val KEY_GO_TO_QUESTION: String? = "position_go_to"
    val KEY_BACK_FROM_RESULT:String? = "back_from_result"
    val TOTAL_TIME = 20 * 60 * 1000 // 20 Minutes
    var answerSheetList: MutableList<CurrentQuestion> = ArrayList()
    var answerSheetListFiltered: MutableList<CurrentQuestion> = ArrayList()
    var questionList:MutableList<Question> = ArrayList()
    var selecedCategory: Category?=null

    var fragmentList:MutableList<QuestionFragment> = ArrayList()

    var selected_values:MutableList<String> = ArrayList()

    var timer = 0
    var right_answer_count = 0
    var wrong_answer_count = 0
    var no_answer_count = 0
    var data_question = StringBuilder()
    enum class ANSWER_TYPE {
        NO_ANSWER,
        RIGHT_ANSWER,
        WRONG_ANSWER
    }
}
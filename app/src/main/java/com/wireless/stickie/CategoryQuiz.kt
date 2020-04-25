package com.wireless.stickie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.wireless.stickie.Adapter.CategoryAdapter
import com.wireless.stickie.Common.SpacesItemDecoration
import com.wireless.stickie.DBHelper.DBHelper
import kotlinx.android.synthetic.main.activity_category_quiz.*

class CategoryQuiz : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_quiz)

//        toolbar.title = "EDMT QUIZ 2019"
//        setSupportActionBar(toolbar)

        recycler_category.setHasFixedSize(true)
        recycler_category.layoutManager = GridLayoutManager(this, 2)

        val adapter = CategoryAdapter(this, DBHelper.getInstance(this).allCategories)
        recycler_category.addItemDecoration(SpacesItemDecoration(4))
        recycler_category.adapter = adapter
    }
}

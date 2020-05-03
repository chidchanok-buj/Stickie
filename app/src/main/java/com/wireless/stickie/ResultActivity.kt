package com.wireless.stickie

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.wireless.stickie.Adapter.ResultGridAdapter
import com.wireless.stickie.Common.Common
import com.wireless.stickie.Common.SpacesItemDecoration
import com.wireless.stickie.Model.Score
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.toast
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class ResultActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private lateinit var postReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private var scores: ArrayList<Int>? = null
    private var aa: ArrayAdapter<Int>? = null
    var percentage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        LocalBroadcastManager.getInstance(this@ResultActivity)
            .registerReceiver(backToQuestion, IntentFilter(Common.KEY_BACK_FROM_RESULT))

//        toolbar.title = "RESULT"
        setSupportActionBar(toolbar)

//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //
        txt_time.text = (java.lang.String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(Common.timer.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(Common.timer.toLong()) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    Common.timer.toLong()
                )
            )
        )
                )


        txt_right_answer.text = "${Common.right_answer_count}/${Common.questionList.size}"

        btn_filter_total.text = "${Common.questionList.size}"
        btn_filter_right.text = "${Common.right_answer_count}"
        btn_filter_wrong.text = "${Common.wrong_answer_count}"
        btn_filter_no_answer.text = "${Common.no_answer_count}"

        val percent = Common.right_answer_count * 100 / Common.questionList.size
        percentage = percent
        saveScore()
        if (percent > 80) {
            txt_result.text = getString(R.string.excellent)
        } else if (percent > 70) {
            txt_result.text = getString(R.string.good)
        } else if (percent > 60) {
            txt_result.text = getString(R.string.fair)
        } else {
            txt_result.text = getString(R.string.tryagain)
        }

        // Event Button
        btn_filter_total.setOnClickListener {
            val adapter = ResultGridAdapter(this, Common.answerSheetList)
            recycler_result.adapter = adapter
        }

        btn_filter_no_answer.setOnClickListener {
            Common.answerSheetListFiltered.clear()
            for (currentQuestion in Common.answerSheetList) {
                if (currentQuestion.type == Common.ANSWER_TYPE.NO_ANSWER) {
                    Common.answerSheetListFiltered.add(currentQuestion)
                }
            }
            val adapter = ResultGridAdapter(this, Common.answerSheetListFiltered)
            recycler_result.adapter = adapter
        }

        btn_filter_wrong.setOnClickListener {
            Common.answerSheetListFiltered.clear()
            for (currentQuestion in Common.answerSheetList) {
                if (currentQuestion.type == Common.ANSWER_TYPE.WRONG_ANSWER) {
                    Common.answerSheetListFiltered.add(currentQuestion)
                }
            }
            val adapter = ResultGridAdapter(this, Common.answerSheetListFiltered)
            recycler_result.adapter = adapter
        }

        btn_filter_right.setOnClickListener {
            Common.answerSheetListFiltered.clear()
            for (currentQuestion in Common.answerSheetList) {
                if (currentQuestion.type == Common.ANSWER_TYPE.RIGHT_ANSWER) {
                    Common.answerSheetListFiltered.add(currentQuestion)
                }
            }
            val adapter = ResultGridAdapter(this, Common.answerSheetListFiltered)
            recycler_result.adapter = adapter
        }


        // Set Adapter
        val adapter = ResultGridAdapter(this, Common.answerSheetList)
        recycler_result.setHasFixedSize(true)
        recycler_result.layoutManager = GridLayoutManager(this, 4)
        recycler_result.addItemDecoration(SpacesItemDecoration(4))
        recycler_result.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.menu_do_quiz_again -> doQuizAgain()
            R.id.menu_view_answer -> viewAnswer()
            android.R.id.home -> {
                val intent = Intent(applicationContext, CategoryQuiz::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent) // Go back to Category when the user clicks on back arrow on ResultActivity
            }
            R.id.menu_score -> {
                val intent = Intent(this, ScoreActivity::class.java)
                startActivity(intent)
            }
            R.id.action_lang -> {
                showChangeLang()
            }

        }
        return true
    }

    private fun viewAnswer() {
        val returnIntent = Intent()
        returnIntent.putExtra("action", "viewanswer")
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun doQuizAgain() {
        MaterialStyledDialog.Builder(this@ResultActivity)
            .setTitle(getString(R.string.doquizagain))
            .setDescription(getString(R.string.againreally))
            .setIcon(R.drawable.ic_mood_white_24dp)
            .setNegativeText(getString(R.string.no))
            .onNegative { dialog, which -> dialog.dismiss() }
            .setPositiveText(getString(R.string.yes))
            .onPositive { dialog, which ->
                val returnIntent = Intent()
                returnIntent.putExtra("action", "doquizagain")
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
            .show()
    }

    // Save score to Firebase
    private fun saveScore() {
        mAuth = FirebaseAuth.getInstance()
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val user = mAuth!!.currentUser
        scores = ArrayList()
        aa = ArrayAdapter(this, android.R.layout.simple_list_item_1, scores!!)
        lateinit var ref: DatabaseReference
        if (user != null) {
            ref = FirebaseDatabase.getInstance().getReference(user.displayName.toString())
        } else if (acct != null) {
            ref = FirebaseDatabase.getInstance().getReference(acct.displayName.toString())
        } else {
            toast("Guest cannot see the score")
            finish()
        }
//        val ref = FirebaseDatabase.getInstance().getReference(user!!.displayName.toString())

//        val id = ref.push().key
        val id = Common.selectedCategory!!.id
        val score = Score(
            Common.selectedCategory!!.name!!,
            percentage
        )
        // add data
        ref.child(id.toString()).setValue(score).addOnCanceledListener {
            Log.d("ResultActivity", "Score Saved")
        }
    }

    internal var backToQuestion: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action!!.toString() == Common.KEY_BACK_FROM_RESULT) {
                val questionIndex = intent.getIntExtra(Common.KEY_BACK_FROM_RESULT, -1)
                goBackActivityWithQuestionIndex(questionIndex)
            }
        }
    }

    private fun goBackActivityWithQuestionIndex(questionIndex: Int) {
        val returnIntent = Intent()
        returnIntent.putExtra(Common.KEY_BACK_FROM_RESULT, questionIndex)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_result, menu)
        return true
    }


    private fun showChangeLang() {
        val listItems = arrayOf("ภาษาไทย","English")
        val mBuilder = AlertDialog.Builder(this@ResultActivity)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItems, -1) { dialog, which ->
            if (which == 0) {
                setLocate("th")
                recreate()
            } else if (which == 1) {
                setLocate("en")
                recreate()
            }
            dialog.dismiss()
        }

        val mDialog = mBuilder.create()

        mDialog.show()
    }

    private fun setLocate(Lang: String?) {
        val config = resources.configuration
        val locale = Locale(Lang)

        Locale.setDefault(locale)
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)

        recreate()
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this@ResultActivity)
            .unregisterReceiver(backToQuestion)
        super.onDestroy()
    }
}

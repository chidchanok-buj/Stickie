package com.wireless.stickie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_a_r_q_exercise.*

class ARQExercise : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_r_q_exercise)

        arqSolution.setOnClickListener {
            val mAlertDialog = AlertDialog.Builder(this@ARQExercise)
            mAlertDialog.setIcon(R.mipmap.ic_launcher_round) // set alertdialog icon
            mAlertDialog.setTitle(getString(R.string.solution)) // set alertdialog title
            mAlertDialog.setMessage(getString(R.string.congestionSolution)) // set alertdialog message
            mAlertDialog.setPositiveButton("OK") { dialog, id ->
                dialog.dismiss()
            }
            mAlertDialog.show()
        }
    }
}

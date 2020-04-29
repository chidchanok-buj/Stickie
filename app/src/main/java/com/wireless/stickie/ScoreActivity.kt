package com.wireless.stickie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_score.*
import com.wireless.stickie.Model.Score
import org.jetbrains.anko.toast


class ScoreActivity : AppCompatActivity() {

    private lateinit var postKey: String
    private lateinit var postReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private var postListener: ValueEventListener? = null
    private var listView: ListView? = null
    private var scores: ArrayList<Int>? = null
    private var aa: ArrayAdapter<Int>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        listView = findViewById(R.id.ListView)
        scores = ArrayList()
        aa = ArrayAdapter(this, android.R.layout.simple_list_item_1, scores!!)
        listView!!.setAdapter(aa)

//         Get post key from intent
//        postKey = intent.getStringExtra(EXTRA_POST_KEY)
//            ?: throw IllegalArgumentException("Must pass EXTRA_POST_KEY")

        firebaseDatabase = FirebaseDatabase.getInstance()

        // Initialize Database
        postReference = firebaseDatabase.getReference("Scores")

        clear.setOnClickListener {
            scores!!.clear()
            aa!!.notifyDataSetChanged()
        }

    }

    private fun clearScore() {

    }

    public override fun onStart() {
        super.onStart()

        // Add value event listener to the post
        // [START post_value_event_listener]
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot!!.exists()) {
                    for (sco in dataSnapshot.children) {
                        val score = sco.getValue(Score::class.java)!!
                        scores!!.add(0, score!!.percentage)
                    }
                    aa!!.notifyDataSetChanged()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                toast("loadPost:onCancelled")

            }
        }
        postReference.addValueEventListener(postListener)
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        this.postListener = postListener

    }

    companion object {

        const val EXTRA_POST_KEY = "post_key"
        private const val TAG = "ScoreActivity"
    }
}

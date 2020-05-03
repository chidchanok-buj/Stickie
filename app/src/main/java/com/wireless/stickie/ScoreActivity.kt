package com.wireless.stickie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_score.*
import com.wireless.stickie.Model.Score
import org.jetbrains.anko.toast


class ScoreActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private lateinit var postReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private var postListener: ValueEventListener? = null
    private var listView: ListView? = null
    private var sub: ListView? = null
    private var scores: ArrayList<Int>? = null
    private var subjects: ArrayList<String>? = null
    private var aa: ArrayAdapter<Int>? = null
    private var bb: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth!!.currentUser
        listView = findViewById(R.id.score)
        sub = findViewById(R.id.subject)
        scores = ArrayList()
        subjects = ArrayList()
        aa = ArrayAdapter(this, android.R.layout.simple_list_item_1, scores!!)
        bb = ArrayAdapter(this, android.R.layout.simple_list_item_1, subjects!!)
        listView!!.adapter = aa
        sub!!.adapter = bb

        val acct = GoogleSignIn.getLastSignedInAccount(this)

//         Get post key from intent
//        postKey = intent.getStringExtra(EXTRA_POST_KEY)
//            ?: throw IllegalArgumentException("Must pass EXTRA_POST_KEY")

        firebaseDatabase = FirebaseDatabase.getInstance()

        // Initialize Database
        if (user != null) {
            postReference = firebaseDatabase.getReference(user.displayName.toString())
        } else if (acct != null) {
            postReference = firebaseDatabase.getReference(acct.displayName.toString())
        } else {
            toast("Guest cannot see the score")
            finish()
        }

        // Add value event listener to the post
        // [START post_value_event_listener]
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()) {
                    for (sco in dataSnapshot.children) {
                        val score = sco.getValue(Score::class.java)!!
                        scores!!.add(0, score.percentage)
                        subjects!!.add(0, score.name.toString())
                        Log.d("ScoreActivity", "Add to Array List")
                    }
                    aa!!.notifyDataSetChanged()
                    bb!!.notifyDataSetChanged()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                toast("loadPost:onCancelled")

            }
        }
        if (user != null || acct != null) {
            postReference.addValueEventListener(postListener)
            this.postListener = postListener
        } else {
            finish()
        }
//        postReference.addValueEventListener(postListener)
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
//        this.postListener = postListener

        clear.setOnClickListener {
            clearScore()
            Log.d("ScoreActivity", "Clear")
            scores!!.clear()
            subjects!!.clear()
        }

    }

    private fun clearScore() {
        mAuth = FirebaseAuth.getInstance()
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val user = mAuth!!.currentUser
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
        ref.removeValue()
        aa!!.notifyDataSetChanged()
        bb!!.notifyDataSetChanged()


    }

    public override fun onStart() {
        super.onStart()


    }

    companion object {

        private const val TAG = "ScoreActivity"
    }
}

package ca.uwindsor.calaj.instafeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import ca.uwindsor.calaj.instafeed.models.Post
import com.google.firebase.firestore.FirebaseFirestore

private const val TAG = "PostActivity"

class PostsActivity : AppCompatActivity() {

    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        //Making query to firestore to retrieve data
        firestoreDb = FirebaseFirestore.getInstance()
        val postsReference = firestoreDb.collection("posts")
        postsReference.addSnapshotListener{ snapshot, exception ->
            if (exception != null || snapshot == null){
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener
            }
            //snapshot.toObjects(Post::class.java)
            val docs = snapshot.documents
            val docs2 = mutableListOf<Post>()
            for (document in snapshot.documents){
                Log.i(TAG, "Document ${document.id}: ${document.data}")
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_profile){
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
package tutorial.fujikawa.fuller.tutorial

import android.content.Context
import android.graphics.BitmapFactory
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import org.w3c.dom.Text

//import tutorial.fujikawa.fuller.tutorial.R.id.list_item

class MainActivity : AppCompatActivity() {
    var key_ : Int = 1
    //ここでは、list_itemに格納するデータを持つdata classを作成する
    data class TweetData(val id:String, val tweet:String,val img_id:Int )

    //ここではlist_itemの要素を持つdata classを作成する
    //data class ViewHolder(var id: TextView,var tweet: TextView,var profile_img:ImageView)
    data class ViewHolder(var view : View){
        val id = view.findViewById<TextView>(R.id.id_name)
        val txt = view.findViewById<TextView>(R.id.tweet_data)
        val img = view.findViewById<ImageView>(R.id.profile_img)
    }
    class TweetListAdapter(context: Context, tweets: List<TweetData>) : ArrayAdapter<TweetData>(context, 0, tweets) {
        private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            var holder: ViewHolder
            val data = getItem(position) as TweetData
            if (view == null) {
                view = layoutInflater.inflate(R.layout.list_item, parent, false)
                holder = ViewHolder(view)
                view.tag = holder
                holder.id.text = data.id
                holder.txt.text = data.tweet
                holder.img.setImageBitmap(BitmapFactory.decodeResource(context.resources, data.img_id))
                return view
            } else {
                holder = view.tag as ViewHolder
                holder.id.text = data.id
                holder.txt.text = data.tweet
                holder.img.setImageBitmap(BitmapFactory.decodeResource(context.resources, data.img_id))
                return view
            }
            view = layoutInflater.inflate(R.layout.list_item, parent, false)
            return view
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val id = listOf("taisuke","t_fujiS")
        val tweet = listOf("HelloWorld!","Like Twitter")
        val img_id = listOf(R.drawable.images,R.drawable.images)
        val tweets = List(id.size){i -> TweetData(id[i],tweet[i],img_id[i])}
        val img = R.drawable.images
        val adapter = TweetListAdapter(this, tweets)
        view.adapter = adapter
        //val key:String = "1"
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference()
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var id_value = dataSnapshot.child(key_.toString()).child("id").value
                var tweet_value = dataSnapshot.child(key_.toString()).child("tweet").value
                var text_id : String = id_value.toString()
                var text_tweet : String = tweet_value.toString()
                var data_image = img
                var tweet_values = TweetData(text_id,text_tweet,data_image)
                adapter.add(tweet_values)
                Count()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("onCancelled", "error:", error.toException())
            }
        })

    }
    fun Count(){
        key_+=1
    }

}

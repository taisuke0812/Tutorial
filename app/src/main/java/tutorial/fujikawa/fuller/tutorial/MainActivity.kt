package tutorial.fujikawa.fuller.tutorial

import android.content.Context
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import org.w3c.dom.Text
import tutorial.fujikawa.fuller.tutorial.R.id.view

//import tutorial.fujikawa.fuller.tutorial.R.id.list_item

class MainActivity : AppCompatActivity() {
    var key_ : Int = 1
    //ここでは、list_itemに格納するデータを持つdata classを作成する
    data class TweetData(val id:String, val tweet:String,val img_id:Int,
                         val tweet_img:Int=R.drawable.fuller,val rt:Int = R.drawable.rt,val fav :Int = R.drawable.fav,val share:Int=R.drawable.share,val comment:Int=R.drawable.coment)

    //ここではlist_itemの要素を持つdata classを作成する
    //data class ViewHolder(var id: TextView,var tweet: TextView,var profile_img:ImageView)
    data class ViewHolder(var view : View){
        val id = view.findViewById<TextView>(R.id.id_name)
        val txt = view.findViewById<TextView>(R.id.tweet_data)
        val img = view.findViewById<ImageView>(R.id.profile_img)
        val tweet_img = view.findViewById<ImageView>(R.id.tweet_img)
        val fav = view.findViewById<ImageView>(R.id.fav)
        val rt =  view.findViewById<ImageView>(R.id.rt)
        val share = view.findViewById<ImageView>(R.id.share)
        val comment = view.findViewById<ImageView>(R.id.comment)
        companion object {
            const val LAYOUT_ID = R.layout.list_item
        }
    }
    class TweetListAdapter(context: Context, tweets: List<TweetData>) : ArrayAdapter<TweetData>(context, 0, tweets) {
        private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val data = getItem(position) as TweetData
            val view = LayoutInflater.from(context).inflate(ViewHolder.LAYOUT_ID,parent,false)
            val holder = ViewHolder(view)
            holder.id.text = data.id
            holder.txt.text = data.tweet
            holder.img.setImageResource(data.img_id)
            holder.tweet_img.setImageResource(data.tweet_img)
            holder.fav.setImageResource(data.fav)
            holder.rt.setImageResource(data.rt)
            holder.share.setImageResource(data.share)
            holder.comment.setImageResource(data.comment)
            return view
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val id = listOf("@taisuke","@t_fujiS")
        val tweet = listOf("Fuller","Like Twitter App")
        val img_id = listOf(R.drawable.images,R.drawable.images)
        val url = listOf("https://5th.fuller-inc.com/assets/135837d7f8850217277dbcdc3bda5a10.png","https://prtimes.jp/i/5362/103/resize/d5362-103-721728-11.jpg")
        val tweets = List(id.size){i -> TweetData(id[i],tweet[i],img_id[i])}
        val img = R.drawable.images

        val adapter = TweetListAdapter(this, tweets)
        view.adapter = adapter

        //val key:String = "1"
        val database = FirebaseDatabase.getInstance()
        val ref = database.reference

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //直近1ツイートだけ取得
                var num = dataSnapshot.childrenCount
                key_ = num.toInt()
                SetTweet(adapter,dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("onCancelled", "error:", error.toException())
            }
        })

    }
    //keyを増やす関数
    //なんとなく関数化した
    private fun count(){
        this.key_+=1
    }
    //ツイートをリストに入れる関数
    fun SetTweet(adapter:TweetListAdapter,dataSnapshot: DataSnapshot){
        val img = R.drawable.images
        val idValue = dataSnapshot.child(key_.toString()).child("id").value
        val tweetValue = dataSnapshot.child(key_.toString()).child("tweet").value
        val tweetImg_ = dataSnapshot.child(key_.toString()).child("url").value
        val textId : String = idValue.toString()
        val textTweet : String = tweetValue.toString()
        val dataImage = img
        //var dataUrl:String = tweet_img_.toString()
        val tweetValues = TweetData(textId,textTweet,dataImage)
        adapter.add(tweetValues)
        count()
    }
}

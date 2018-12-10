package tutorial.fujikawa.fuller.tutorial

import android.content.Context
import android.graphics.BitmapFactory
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

class MainActivity : AppCompatActivity() {

    val id = listOf("taisuke","t_fujiS")
    val tweet = listOf("HelloWorld!","Like Twitter")
    val img_id = listOf(R.drawable.ic_launcher_background,R.drawable.ic_launcher_background)

    //ここでは、list_itemに格納するデータを持つdata classを作成する
    data class TweetData(val id:String, val tweet:String,val img_id:Int )
    val tweets = List(id.size){i -> TweetData(id[i],tweet[i],img_id[i])}
    //ここではlist_itemの要素を持つdata classを作成する
    data class ViewHolder(val id: TextView,val tweet: TextView,val profile_img:ImageView)

    class TweetListAdapter(context: Context, tweets: List<TweetData>) : ArrayAdapter<TweetData>(context, 0, tweets) {
        private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            var holder: ViewHolder

            if (view == null) {
                view = layoutInflater.inflate(R.layout.list_item, parent, true)
                //うまくいかへん
                /*
                holder = ViewHolder(
                    view?.id_name!!,
                    view.descTextView,
                    view.flowerImgView
                )
                */
                //view.tag = holder
                return view
            } else {
                holder = view.tag as ViewHolder
                return view
            }

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = TweetListAdapter(this, tweets)
        view.adapter = adapter

        /*
        val key:String = "text"
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference(key)

        submit.setOnClickListener{
            var data : String = textdata.text.toString()
            ref.setValue(data)
            textdata.setText("")
        }

        ref.addValueEventListener(object: ValueEventListener {
            var list : MutableList<String> = mutableListOf()
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val values : Any? = dataSnapshot.value
                val text : String = values.toString()
                /*
                var value : Map<String,String>? = values as? Map<String,String>
                list.add(value.toString())
                var text:String = value?.getOrDefault("2","hello").toString()
                */
                list.add(text)
                val adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, list)
                view.setAdapter(adapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("onCancelled", "error:", error.toException())
            }
        })

        */
    }


}

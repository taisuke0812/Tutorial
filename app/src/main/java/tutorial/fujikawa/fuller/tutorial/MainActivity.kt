package tutorial.fujikawa.fuller.tutorial

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


    }


}

package com.example.pairprogramming2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import java.util.Calendar

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var item = ""

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        item = when (pos) {
            0 -> "You Were Excited"
            1 -> "You Were Happy"
            2 -> "You Were Neutral"
            3 -> "You Were Sad"
            4 -> "You Were Angry"
            else -> "You Were Excited"
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }


    class Mood {
        var time = ""
        var mood = ""
    }

    class RecyclerViewAdapter(val moodData: ArrayList<Mood>) :
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerViewAdapter.RecyclerViewHolder {
            val viewItem =
                LayoutInflater.from(parent.context).inflate(R.layout.emotion_item, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return moodData.size
        }

        override fun onBindViewHolder(
            holder: RecyclerViewAdapter.RecyclerViewHolder,
            position: Int
        ) {
            holder.bind(moodData[position])
        }

        class RecyclerViewHolder(val viewItem: View) :
                RecyclerView.ViewHolder(viewItem) {
            fun bind(mood: Mood){
                viewItem.findViewById<TextView>(R.id.time_text).text = mood.time
                viewItem.findViewById<TextView>(R.id.info_text).text = mood.mood
            }
        }
    }

    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var moodList = ArrayList<Mood>()
        val spinner: Spinner = findViewById(R.id.Spinner_mood)

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerViewAdapter(moodList)
        recyclerview.apply {
            this.layoutManager = viewManager
            this.adapter = viewAdapter
        }

// Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter.createFromResource(
            this,
            R.array.mood_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        
        spinner.onItemSelectedListener = this

        Button_add.setOnClickListener {
            var mood = Mood()
            mood.mood = item
            mood.time = Calendar.getInstance().time.toString()
            moodList.add(mood)
            viewAdapter.notifyDataSetChanged()
        }



    }
}

package com.example.student_management.adapter

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.student_management.R
import com.example.student_management.database.Student_Database
import com.example.student_management.model.Student_Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListAdapter internal constructor(context: Context, val noteDB:Student_Database) :RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Student_Entity>()

    private val job = Job()
  fun setNote (notes: List<Student_Entity>) {
        this.notes = notes
        notifyDataSetChanged()
    }
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.rs_name)
        val textView1: TextView = view.findViewById(R.id.rs_grade)
        val imageView : ImageView = view.findViewById(R.id.imv_delete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = inflater.inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentNote = notes[position]

        holder.textView.text = currentNote.name
        holder.textView1.text = currentNote.grade
        holder.imageView.setOnClickListener {
            // Delete currentNote
uiScope.launch {
    noteDB?.studentDao()?.delete(currentNote)




    notes = noteDB?.studentDao()?.GetAll()

    notifyDataSetChanged()
}
        }
    }

    override fun getItemCount(): Int {
      return notes.size
    }

}
package com.example.student_management.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Global.getInt
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.student_management.MainActivity
import com.example.student_management.R
import com.example.student_management.database.Student_Database
import com.example.student_management.fragment2
import com.example.student_management.model.Student_Entity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Integer.parseInt
import java.lang.reflect.Method


class ListAdapter
internal constructor(val context: Context, val noteDB: Student_Database)

    :RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

  private  val MY_REREQUEST_CODE:Int =10

    private var notes = emptyList<Student_Entity>()

    private val job = Job()
  fun setNote(notes: List<Student_Entity>) {
        this.notes = notes
        notifyDataSetChanged()
    }
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.rs_name)
        val textView1: TextView = view.findViewById(R.id.rs_grade)
        val imageView : ImageView = view.findViewById(R.id.imv_menu_option)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentNote = notes[position]

        holder.textView.text =context.getString(R.string.name,currentNote.name.toString())
        holder.textView1.text = context.getString(R.string.grade,currentNote.grade.toString())





        holder.imageView.setOnClickListener {
            val popupMenu = PopupMenu(context,it)
            popupMenu.setOnMenuItemClickListener { Item ->
                when (Item.itemId) {
                    R.id.item1 -> {
                        Toast.makeText(context, "Đã ấn vào sửa", Toast.LENGTH_SHORT).show()
                        uiScope.launch {
//
               val intent = Intent(context,fragment2::class.java)
                val bundle = Bundle()
                bundle.putSerializable("student_id",currentNote.id)
                bundle.putSerializable("student_name",currentNote.name)
                bundle.putSerializable("student_grade",currentNote.grade)
                intent.putExtras(bundle)
                startActivity(context,intent,bundle)
//                startActivityForResult(fragment2(),intent,MY_REREQUEST_CODE,bundle)

            }
                       return@setOnMenuItemClickListener true

                    }
                    R.id.item2 -> {
                        MaterialAlertDialogBuilder(context,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setTitle("Thông Báo")
                            .setMessage("Bạn có chắc chắn muốn xoá sinh viên này")
                            .setNegativeButton("Huỷ") { dialog, which ->
                                // Respond to negative button press
                                return@setNegativeButton
                            }
                            .setPositiveButton("Đồng ý") { dialog, whichs ->
                                uiScope.launch {
                                    noteDB?.studentDao()?.delete(currentNote)
                                    notes = noteDB?.studentDao()?.GetAll()

                                    Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_SHORT).show()
                                    notifyDataSetChanged()
                                }
                            }
                            .show()
                        return@setOnMenuItemClickListener true


                    }
                    else -> false
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popupMenu.setForceShowIcon(true)
            } else {
                try {
                    val fields = popupMenu.javaClass.declaredFields
                    for (field in fields) {
                        if ("mPopup" == field.name) {
                            field.isAccessible = true
                            val menuPopupHelper = field[popupMenu]
                            val classPopupHelper =
                                Class.forName(menuPopupHelper.javaClass.name)
                            val setForceIcons: Method = classPopupHelper.getMethod(
                                "setForceShowIcon",
                                Boolean::class.javaPrimitiveType
                            )
                            setForceIcons.invoke(menuPopupHelper, true)
                            break
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            popupMenu.inflate(R.menu.menu_main)
            popupMenu.show()

        }

    }




    override fun getItemCount(): Int {
      return notes.size
    }

}
package com.harjot.apiintegration.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harjot.apiintegration.R
import com.harjot.apiintegration.interfaces.RecyclerInterface
import com.harjot.apiintegration.models.ResponseModel

class RecyclerAdapter(var arrayList:ArrayList<ResponseModel.Data>,var recyclerInterface: RecyclerInterface):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var tvFirstName = view.findViewById<TextView>(R.id.tvFirstName)
        var tvLastName = view.findViewById<TextView>(R.id.tvLastName)
        var tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        var item = view.findViewById<LinearLayout>(R.id.item)
        var btnEdit = view.findViewById<TextView>(R.id.btnEdit)
        var btnDelete = view.findViewById<TextView>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvFirstName.setText(arrayList[position].first_name)
        holder.tvLastName.setText(arrayList[position].last_name)
        holder.tvEmail.setText(arrayList[position].email)
        holder.item.setOnClickListener {
            recyclerInterface.listClick(position)
        }
        holder.btnEdit.setOnClickListener {
            recyclerInterface.onEditClick(position)
        }
        holder.btnDelete.setOnClickListener {
            recyclerInterface.onDeleteClick(position)
        }
    }
}
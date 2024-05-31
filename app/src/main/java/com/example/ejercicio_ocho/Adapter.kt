package com.example.ejercicio_ocho

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.RecyclerView

class Adapter (private var mainActivity: MainActivity)
    : RecyclerView.Adapter<Adapter.ViewHolderConcat>() {

    //Parte interna
    class ViewHolderConcat(item: View) : RecyclerView.ViewHolder(item) {
        var txtName: TextView = item.findViewById(R.id.txtNombre)
        var txtPhoneNumber: TextView = item.findViewById(R.id.txtTelefono)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderConcat {
        val layoutItem = LayoutInflater.from(parent.context).inflate(
            R.layout.contacto_item, parent, false)
        return ViewHolderConcat(layoutItem)
    }

    override fun getItemCount(): Int = ProvicionalData.listContact.size

    override fun onBindViewHolder(holder: ViewHolderConcat, position: Int) {
        val contact = ProvicionalData.listContact[position]
        holder.txtName.text = contact.nombre
        holder.txtPhoneNumber.text = contact.telefono
        holder.itemView.setOnClickListener {
            mainActivity.clickItem(position)
        }
    }

}
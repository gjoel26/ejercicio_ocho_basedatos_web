package com.example.ejercicio_ocho

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditActivity : AppCompatActivity() {
    var position: Int = 0
    var id: Int = 0
    lateinit var txtName: EditText
    lateinit var txtPhoneNumber: EditText
    lateinit var btnDeleteOrCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val txtTitle = findViewById<TextView>(R.id.txtTitle)
        btnDeleteOrCancel = findViewById(R.id.btnDeleteOrCancel)
        btnDeleteOrCancel.text = "Eliminar"
        txtTitle.text = "Modificar Contacto"

        position = intent.getIntExtra("position", -1)
        Log.e("Contact", "Se recibió una posición: $position")
        txtName = findViewById(R.id.txtName)
        txtPhoneNumber = findViewById(R.id.txtPhoneNomber)

        val contact = ProvicionalData.listContact.getOrNull(position)
        if (contact != null) {
            txtName.setText(contact.nombre)
            txtPhoneNumber.setText(contact.telefono)
            Toast.makeText(this, "ID ${contact.id}", Toast.LENGTH_LONG).show()
            id = contact.id
        } else {
            Toast.makeText(this, "Contacto no encontrado", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    fun save(v: View) {
        val name = txtName.text.toString()
        val phone = txtPhoneNumber.text.toString()
        val contact = Contact(id, name, phone)

        val retrofit = RetrofitApp.getRetrofit()
        val service = retrofit.create(IContact::class.java)
        val petition: Call<Boolean> = service.modifyPersona(contact)

        petition.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                val status = response.body()
                Log.v("Respuesta", "Respuesta $status")
                if (status == true) {
                    Toast.makeText(applicationContext, "Modificado", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }
        })
        finish()
    }

    fun cancelOrDelete(v: View) {
        val retrofit = RetrofitApp.getRetrofit()
        val service = retrofit.create(IContact::class.java)
        val petition = service.deletePersona(id)

        petition.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                val success = response.body()
                if (success == true) {
                    Toast.makeText(applicationContext, "Eliminado", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                Log.e("Error", t.message.toString())
            }
        })
        finish()
    }
}

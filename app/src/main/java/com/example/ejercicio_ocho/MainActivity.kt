package com.example.ejercicio_ocho

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class
MainActivity : AppCompatActivity() {

    lateinit var rcv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rcv = findViewById(R.id.rvContact)

    }

    override fun onResume() {
        super.onResume()

        val repository = RetrofitApp.getRetrofit()
        val service = repository.create(IContact::class.java)
        val petition: Call<List<Contact>> = service.getPersonas()

        petition.enqueue(object : Callback<List<Contact>> {
            override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
                val contacts = response.body()
                Log.v("Respuesta", "Numero de registros ${contacts?.size}")
                if (contacts != null) {
                    ProvicionalData.listContact = contacts as ArrayList<Contact>
                    Log.w("Contact", "Hay ${ProvicionalData.listContact.size} register contact")
                    rcv.adapter = Adapter(this@MainActivity)
                    rcv.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }

            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

        })
        /*
                Log.w("Contact", "Hay ${ProvicionalData.listContact.size} register contact")
                rcv.adapter = Adapter(this)
                rcv.layoutManager = LinearLayoutManager(this)
         */

    }

    fun btnAdd(v: View) {
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    fun clickItem(position: Int) {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }

}
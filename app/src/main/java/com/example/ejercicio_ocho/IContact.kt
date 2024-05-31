package com.example.ejercicio_ocho
import retrofit2.Call
import retrofit2.http.*

interface IContact {
    companion object {
        const val url = "http://192.168.1.73:4567" // Ajusta la URL si es necesario
    }

    @GET("/contactos")
    fun getPersonas(): Call<List<Contact>>

    @PUT("/contacto")
    fun addPersona(@Body contact: Contact): Call<Boolean>

    @POST("/modificar")
    fun modifyPersona(@Body contact: Contact): Call<Boolean>

    @DELETE("/eliminar/{id}")
    fun deletePersona(@Path("id") id: Int): Call<Boolean>
}

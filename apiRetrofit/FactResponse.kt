package ar.edu.angulos

import com.google.gson.annotations.SerializedName


//Es una clase especial para contener Datos. Kotlin "nace"
data class FactResponse(

    //Permite el mapeo de claves JSON (mediante la libreria GSON a clases Kotlin)
    @SerializedName("fact") val fact: String,
    @SerializedName("length") val length: Int

)


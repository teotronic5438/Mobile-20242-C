package ar.edu.angulos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")  // Reemplaza con la URL base de la API
            .addConverterFactory(GsonConverterFactory.create()) //Convierte las rtas JSON en objetos Kotlin usables
            .build()


        //Refelxion: realiza una implmentación de la interfzaz ApiServices que definimos nosotros. Esta intefaz contiene los métodos para
        //hacer las solicitudes GET o POST respectivamente
        //luego, creo la variable apiServiceque contendrá la instancia de ApiService y a través de esta instancia puedo llamar a los
        //métodos definidos en la interfaz

        //::class.java --> Es porque Kotlin necesita convertir la rferencia de la clase en formato Java
        //Esto lo debo hacer dado que RETROFIT FUE DESARROLLADO ORIGINALMENTE PARA JAVA!!!!!

        val apiService = retrofit.create(ApiService::class.java)

        setContentView(R.layout.linear_layout)

        val textView: TextView = findViewById(R.id.textView)
        val textView2: TextView = findViewById(R.id.textView2)

        // Llamada a la API
        val call = apiService.getFact()

        call.enqueue(object : Callback<FactResponse> {

            override fun onResponse(call: Call<FactResponse>, response: Response<FactResponse>) {

                if (response.isSuccessful) {
                    // Actualizar el TextView con el valor de "fact"

                    val fact = response.body()?.fact
                    val len = response.body()?.length
                    textView.text = fact
                    textView2.text = len.toString()
                }
            }

            override fun onFailure(call: Call<FactResponse>, t: Throwable) {
                textView.text = "Error al obtener el dato"
            }
        })



    }

}

































Se realiza la solicitud HTTP utilizando Retrofit la cual actualiza el Activity

"enqueue" realiza la llamada de forma ASÍNCRONA y maneja la rta o error en consecuencia


call.enqueue(object : Callback<FactResponse> { //Realizar la llamada a la API en un subproceso en segundo plano evitando el bloque del hilo principal

    override fun onResponse(call: Call<FactResponse>, response: Response<FactResponse>) { //Se ejecuta cuando hay respuesta desde la API

        if (response.isSuccessful) { //Quiere decir que si 200-299 es ok! -- (HTTP) Ej: recibo un 500 o 404, son codigos no satifactorios. El 200 es el OK HTTP 202, ect
            // Actualizar el TextView con el valor de "fact"

            val fact = response.body()?.fact //Obtengo el hecho curioso sobre un gato
            val len = response.body()?.length //Obtengo la logigitud
            textView.text = fact
            textView2.text = len.toString()
        }
    }


    //Si la llamada falla, ya sea xq no hay internet o xq se cayo la pagina, se ejecuta esta funcion.
    override fun onFailure(call: Call<FactResponse>, t: Throwable) {
        textView.text = "Error al obtener el dato"
    }
}




















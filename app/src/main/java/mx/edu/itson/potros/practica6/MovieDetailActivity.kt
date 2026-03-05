package mx.edu.itson.potros.practica6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MovieDetailActivity : AppCompatActivity() {

    lateinit var seatLeft: TextView
    var posMovie: Int = -1
    var tipo: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_detail)

        val iv_pelicula_image: ImageView = findViewById(R.id.iv_pelicula_imagen)
        val tv_nombre_pelicula: TextView = findViewById(R.id.tv_nombre_pelicula)
        val tv_pelicula_desc: TextView = findViewById(R.id.tv_pelicula_desc)
        val btn_buy_tickets: Button = findViewById(R.id.buyTickets)
        seatLeft = findViewById(R.id.seatLeft)

        val bundle = intent.extras
        if (bundle != null) {
            iv_pelicula_image.setImageResource(bundle.getInt("header"))
            tv_nombre_pelicula.text = bundle.getString("titulo")
            tv_pelicula_desc.text = bundle.getString("sinopsis")

            posMovie = bundle.getInt("pos", -1)
            tipo = bundle.getString("tipo")

            actualizarAsientos()
        }

        btn_buy_tickets.setOnClickListener {
            val intent = Intent(this, SeatSelectionActivity::class.java)
            intent.putExtra("name", tv_nombre_pelicula.text.toString())
            intent.putExtra("id", posMovie)
            intent.putExtra("tipo", tipo)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun actualizarAsientos() {
        if (posMovie != -1) {
            val pelicula = if (tipo == "peli") {
                CatalogActivity.peliculas[posMovie]
            } else {
                CatalogActivity.series[posMovie]
            }

            val disponibles = 20 - pelicula.seats.size
            seatLeft.text = "$disponibles seats available"
        }
    }

    override fun onRestart() {
        super.onRestart()
        actualizarAsientos()
    }
}
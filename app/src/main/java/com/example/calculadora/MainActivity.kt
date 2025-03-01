package com.example.calculadora

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val SUMA = "+"
    val RESTA = "-"
    val MULTIPLICACION = "*"
    val DIVISION = "/"
    val PORCENTAJE = "%"

    var operacionActual = ""

    var primerNumero: Double = Double.NaN
    var segundoNumero: Double = Double.NaN

    lateinit var valor: TextView
    lateinit var resultado: TextView
    lateinit var formatoDecimal: DecimalFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        formatoDecimal = DecimalFormat("#.##########")
        valor = findViewById(R.id.valor)
        resultado = findViewById(R.id.resultado)

        val botonesNumeros = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        )

        for (id in botonesNumeros) {
            findViewById<Button>(id).setOnClickListener { seleccionarNumero(it) }
        }

        val botonesOperaciones = listOf(
            R.id.buttonSuma, R.id.buttonResta, R.id.buttonX, R.id.buttonDiv, R.id.buttonPor
        )

        for (id in botonesOperaciones) {
            findViewById<Button>(id).setOnClickListener { cambiarOperador(it) }
        }

        findViewById<Button>(R.id.buttonIgual).setOnClickListener { calcular() }
        findViewById<Button>(R.id.buttonC).setOnClickListener { borrarActual() }
        findViewById<Button>(R.id.buttonCA).setOnClickListener { reiniciarCalculadora() }

        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cambiarOperador(b: View) {
        val boton: Button = b as Button

        if (valor.text.isNotEmpty()) {
            primerNumero = valor.text.toString().toDouble()
        }

        operacionActual = when (boton.text.toString().trim()) {
            "÷" -> "/"
            "x" -> "*"
            else -> boton.text.toString().trim()
        }

        resultado.text = formatoDecimal.format(primerNumero) + operacionActual
        valor.text = ""
    }

    private fun calcular() {
        if (valor.text.isNotEmpty()) {
            segundoNumero = valor.text.toString().toDouble()
            valor.text = ""

            when (operacionActual) {
                "+" -> primerNumero += segundoNumero
                "-" -> primerNumero -= segundoNumero
                "*" -> primerNumero *= segundoNumero
                "/" -> if (segundoNumero != 0.0) primerNumero /= segundoNumero
                "%" -> primerNumero %= segundoNumero
            }
            resultado.text = formatoDecimal.format(primerNumero)
        }
    }

    private fun seleccionarNumero(b: View) {
        val boton: Button = b as Button
        if (valor.text.toString() == "0") {
            valor.text = ""
        }
        valor.text = valor.text.toString() + boton.text.toString()
    }

    private fun borrarActual() {
        valor.text = ""
    }

    private fun reiniciarCalculadora() {
        primerNumero = Double.NaN
        segundoNumero = Double.NaN
        operacionActual = ""
        valor.text = ""
        resultado.text = ""
    }
}

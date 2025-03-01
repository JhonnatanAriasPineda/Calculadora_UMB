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

        // Enlazar botones numéricos con la función seleccionarNumero
        val botonesNumeros = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in botonesNumeros) {
            findViewById<Button>(id).setOnClickListener { seleccionarNumero(it) }
        }

        // Enlazar botones de operaciones con la función cambiarOperador
        val botonesOperaciones = listOf(
            R.id.btnSuma, R.id.btnResta, R.id.btnMultiplicacion, R.id.btnDivision, R.id.btnPorcentaje
        )

        for (id in botonesOperaciones) {
            findViewById<Button>(id).setOnClickListener { cambiarOperador(it) }
        }

        // Botón igual para calcular
        findViewById<Button>(R.id.btnIgual).setOnClickListener { calcular() }

        // Configurar los insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cambiarOperador(b: View) {
        val boton: Button = b as Button
        operacionActual = when (boton.text.toString().trim()) {
            "÷" -> "/"
            "x" -> "*"
            else -> boton.text.toString().trim()
        }

        resultado.text = formatoDecimal.format(primerNumero) + operacionActual
        valor.text = ""
    }

    private fun calcular() {
        if (!primerNumero.isNaN()) {
            segundoNumero = valor.text.toString().toDouble()
            valor.text = ""

            when (operacionActual) {
                "+" -> primerNumero += segundoNumero
                "-" -> primerNumero -= segundoNumero
                "*" -> primerNumero *= segundoNumero
                "/" -> primerNumero /= segundoNumero
                "%" -> primerNumero %= segundoNumero
            }
            resultado.text = formatoDecimal.format(primerNumero)
        } else {
            primerNumero = valor.text.toString().toDouble()
        }
    }

    private fun seleccionarNumero(b: View) {
        val boton: Button = b as Button
        if (valor.text.toString() == "0") {
            valor.text = ""
        }
        valor.text = valor.text.toString() + boton.text.toString()
    }
}

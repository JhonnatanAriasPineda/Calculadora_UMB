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
    val  SUMA = "+"
    val  RESTA = "-"
    val  MULTIPLICACION = "*"
    val  DIVISION = "/"
    val  PORCENTAJE = "%"

    var operacionActual = ""

    var primerNumero:Double = Double.NaN
    var segundoNumero:Double = Double.NaN


    lateinit var valor:TextView
    lateinit var resultado:TextView

    lateinit var formatoDecimal:DecimalFormat



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        formatoDecimal = DecimalFormat("#.##########")
        valor = findViewById(R.id.valor)
        resultado = findViewById(R.id.resultado)

        fun cambiarOperador(b:View){
            val boton:Button = b as Button
            if(boton.text.toString().trim()=="÷"){
                operacionActual = "/"
            }else if(boton.text.toString().trim()=="x"){
                operacionActual = "*"
            }else{
                operacionActual = boton.text.toString().trim()
            }
        }

        fun calcular(b: View){
            if(Double.NaN!=primerNumero){
                segundoNumero = valor.text.toString().toDouble()
                valor.text=""

                when(operacionActual){
                    "+" -> primerNumero = (primerNumero+segundoNumero)
                    "-" -> primerNumero = (primerNumero-segundoNumero)
                    "*" -> primerNumero = (primerNumero*segundoNumero)
                    "/" -> primerNumero = (primerNumero/segundoNumero)
                    "%" -> primerNumero = (primerNumero%segundoNumero)
                }
            }else{
                primerNumero = valor.text.toString().toDouble()
            }
        }


        fun seleccionarNumero(b: View){
            val boton:Button = b as Button
            if(valor.text.toString()=="0"){
                valor.text = ""
            }
            valor.text = valor.text.toString() + boton.text.toString()


        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
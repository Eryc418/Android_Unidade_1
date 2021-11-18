package com.example.calculadoraimc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.calculadoraimc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binDing:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        // var altura = 1.70F
        //var peso =  60.0F
        binDing = DataBindingUtil.setContentView(this, R.layout.activity_main)
       // binDing.textAltura.text = altura.toString()
        //binDing.textPeso.text = peso.toString()
        //var imc = (peso/(altura*altura))
        //binDing.MostrarIMC.text = imc.toString()
        val activityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            when(it.resultCode){
                RESULT_OK->{
                    val parametros = it.data?.extras
                    when(it.data?.extras?.getString("Escolha")){
                        "Peso"->{
                             binDing.textPeso.text = parametros?.getString("Valores").toString()
                        }
                        "Altura"->{
                            binDing.textAltura.text = parametros?.getString("Valores").toString()
                        }
                    }
                }
                RESULT_CANCELED -> {
                    Toast.makeText(this, "Voltando", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binDing.BotaoAlterarPeso.setOnClickListener {
            val i = Intent(this, ActivityAlteraDados::class.java)
            val dados = Bundle()
            dados.putString("Valores", binDing.textPeso.text.toString())
            dados.putString("Escolha", "Peso")
            i.putExtras(dados)
            activityForResult.launch(i)
        }
        binDing.BotaoAlterarAltura.setOnClickListener{
            val i = Intent(this, ActivityAlteraDados::class.java)
            val dados = Bundle()
            dados.putString("Valores", binDing.textAltura.text.toString())
            dados.putString("Escolha", "Altura")
            i.putExtras(dados)
            activityForResult.launch(i)
        }
        binDing.BotaoCalcular.setOnClickListener {
            var peso = binDing.textPeso.text.toString().toDouble()
            var altura = binDing.textAltura.text.toString().toDouble()
            var imc = (peso/(altura*altura))
            binDing.MostrarIMC.text = imc.toString()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binDing.textPeso.text = savedInstanceState.getString("ValorPeso")
        binDing.textAltura.text = savedInstanceState.getString("valorAltura")
        binDing.MostrarIMC.text = savedInstanceState.getString("valorImc")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("ValorPeso", binDing.textPeso.text.toString())
        outState.putString("valorAltura", binDing.textAltura.text.toString())
        outState.putString("valorImc",  binDing.MostrarIMC.text.toString())
    }

    override fun onStop() {
        super.onStop()
        val preferencias = getSharedPreferences("Preferencias", MODE_PRIVATE)
        val editor = preferencias.edit()
        editor.putString("ValorPeso",  binDing.textPeso.text.toString())
        editor.putString("valorAltura", binDing.textAltura.text.toString())
        editor.putString("valorImc", binDing.MostrarIMC.text.toString())
        editor.apply()
    }

    override fun onStart() {
        super.onStart()
        val preferencias = getSharedPreferences("Preferencias", MODE_PRIVATE)
        binDing.textPeso.text = preferencias.getString("ValorPeso", "0.0")
        binDing.textAltura.text = preferencias.getString("valorAltura", "0.0")
        binDing.MostrarIMC.text = preferencias.getString("valorImc", "Aperte o botão calcular!")
    }
}
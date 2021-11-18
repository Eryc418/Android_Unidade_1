package com.example.calculadoraimc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.calculadoraimc.databinding.ActivityAlteradadosBinding

class ActivityAlteraDados():AppCompatActivity() {
    lateinit var binding: ActivityAlteradadosBinding
    lateinit var escolha: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_alteradados)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alteradados)
        escolha = intent.extras?.getString("Escolha").toString()
       // bindingPricincipal = DataBindingUtil.setContentView(this, R.layout.activity_main)
        var valores =  intent.extras!!.getString("Valores")

        binding.apply {
            escolhaUser.text = escolha
            textAlterado.setText(valores)
        }
    }
    fun alterarDados(view: View){
        var i = Intent()
        var dadosAlterados = Bundle()
        dadosAlterados.putString("Escolha", escolha)
        dadosAlterados.putString("Valores", binding.textAlterado.text.toString())
        i.putExtras(dadosAlterados)
        setResult(Activity.RESULT_OK, i)
        finish()
    }
    fun Cancelar(view: View){
        finish()
    }
}
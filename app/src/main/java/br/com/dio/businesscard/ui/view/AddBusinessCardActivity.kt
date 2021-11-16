package br.com.dio.businesscard.ui.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import br.com.dio.businesscard.App
import br.com.dio.businesscard.R
import br.com.dio.businesscard.data.model.BusinessCard
import br.com.dio.businesscard.databinding.ActivityAddBusinessCardBinding
import br.com.dio.businesscard.ui.MainViewModel
import br.com.dio.businesscard.ui.MainViewModelFactory

class AddBusinessCardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBusinessCardBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    var cardColorField = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        insertListeners()
    }

    fun setCardColor(): String {
        var color = ""

        binding.chipVermelho.setOnClickListener {
            cardColorField = "#F08181"
        }

        binding.chipAmarelo.setOnClickListener {
            cardColorField = "#F1EE6B"
        }

        binding.chipVerde.setOnClickListener {
            cardColorField = "#88F081"
        }

        binding.chipCinza.setOnClickListener {
            cardColorField = "#9E9898"
        }

        binding.chipAzul.setOnClickListener {
            cardColorField = "#577DF3"
        }

        binding.chipRoxo.setOnClickListener {
            cardColorField = "#B76CF8"
        }

        color = cardColorField
        return color
    }

    private fun insertListeners() {
        binding.btnConfirm.setOnClickListener {

            val businessCard = BusinessCard(
                nome = binding.tilNome.editText?.text.toString(),
                empresa = binding.tilEmpresa.editText?.text.toString(),
                telefone = binding.tilTelefone.editText?.text.toString(),
                email = binding.tilEmail.editText?.text.toString(),
                fundoPersonalizado = setCardColor()
            )

            if (checkEditText(businessCard)) {
                mainViewModel.insert(businessCard)
                Toast.makeText(this, R.string.label_show_success, Toast.LENGTH_SHORT).show()
                finish()
            }

            else {
                Toast.makeText(this, R.string.label_show_please, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun checkEditText(businessCard: BusinessCard): Boolean {
        return !(
                TextUtils.isEmpty(businessCard.nome) ||
                TextUtils.isEmpty(businessCard.telefone) ||
                TextUtils.isEmpty(businessCard.email) ||
                TextUtils.isEmpty(businessCard.empresa) ||
                TextUtils.isEmpty(businessCard.fundoPersonalizado))
    }
}

package com.bychkovdmytro.mornhousetesttask.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bychkovdmytro.mornhousetesttask.FACT_KEY
import com.bychkovdmytro.mornhousetesttask.databinding.ActivityDetailBinding
import com.bychkovdmytro.mornhousetesttask.entities.NumbersList

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private var  number: NumbersList? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getFact()

        binding.buttonBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getFact(){
        number = intent.getSerializableExtra(FACT_KEY) as NumbersList
        binding.detailTv.text = number?.number_fact
    }

}
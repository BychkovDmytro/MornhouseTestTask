package com.bychkovdmytro.mornhousetesttask.screens

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bychkovdmytro.mornhousetesttask.BASE_URL_NUMBER
import com.bychkovdmytro.mornhousetesttask.BASE_URL_RANDOM_NUMBER
import com.bychkovdmytro.mornhousetesttask.FACT_KEY
import com.bychkovdmytro.mornhousetesttask.R
import com.bychkovdmytro.mornhousetesttask.databinding.ActivityMainBinding
import com.bychkovdmytro.mornhousetesttask.db.MainViewModel
import com.bychkovdmytro.mornhousetesttask.db.NumbersAdapter
import com.bychkovdmytro.mornhousetesttask.entities.NumbersList
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity(), NumbersAdapter.Listener {
    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NumbersAdapter
    private lateinit var editLauncher: ActivityResultLauncher<Intent>

    private val mainViewModel: MainViewModel by viewModels() {
        MainViewModel.MainViewModelFactory((this.applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkInternetConnection()
        initRcView()
        observer()
        onEditResult()

        binding.bGetNumber.setOnClickListener {
            checkInternetConnection()
            getNumber()
        }

        binding.bGetRandomNumber.setOnClickListener {
            checkInternetConnection()
            getRandomNumber()
        }
    }
    override fun onResume() {
        super.onResume()
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        if (!isNetworkAvailable) {
            val builder = MaterialAlertDialogBuilder(this)
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setTitle(R.string.alert)
            builder.setMessage(R.string.check_connection)
            builder.setCancelable(false)
            builder.setPositiveButton(R.string.ok, { dialog, which ->  Toast.makeText(this,R.string.message,
                Toast.LENGTH_SHORT).show()})
            builder.show()
        }
    }

    private val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }
            }
            return false
        }

    private fun getNumber() {
        if (binding.editNumber.text.isNullOrEmpty()) {
            Toast.makeText(this, R.string.hint, Toast.LENGTH_LONG).show()
        }
        else {
            getFactAboutNumber()
        }
    }

    private fun getFactAboutNumber() {
        val number = binding.editNumber.text.toString()
        val url = BASE_URL_NUMBER + number
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url, { responce ->
            mainViewModel.insertFact(addFact(responce))
        }, {})
        queue.add(stringRequest)

    }

    private fun getRandomNumber() {

        val url = BASE_URL_RANDOM_NUMBER
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url, { responce ->
            mainViewModel.insertFact(addFact(responce))
        }, {})
        queue.add(stringRequest)
    }

    private fun initRcView() = with (binding){
        adapter = NumbersAdapter(this@MainActivity)
        RecyclerView.adapter = adapter
    }
    private fun observer(){
        mainViewModel.allFacts.observe(this) { adapter.submitList(it) }
    }

    private fun addFact(fact: String) : NumbersList {
        return NumbersList(null, fact)
    }

    private fun onEditResult(){
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){}
    }

    override fun onClickItem(number: NumbersList) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(FACT_KEY, number)
        }
        editLauncher.launch(intent)
    }
}
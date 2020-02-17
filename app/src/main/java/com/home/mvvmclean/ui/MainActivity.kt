package com.home.mvvmclean.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.home.mvvmclean.R
import kotlinx.android.synthetic.main.activity_search.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        bn_search.setOnClickListener {
            if (et_search.text.toString().isNotEmpty())
                mainViewModel.newSearch(
                    et_search.text.toString()
                )
        }

        mainViewModel.responseData.observe(this, Observer {
            fillList(it)
        })

        mainViewModel.errorMessage.observe(this, Observer {
            Snackbar.make(et_search, it, Snackbar.LENGTH_LONG).show()
        })

        mainViewModel.progressValue.observe(this, Observer {
            if(it == View.VISIBLE){
                bn_search.visibility = View.GONE
            }else{
                bn_search.visibility = View.VISIBLE
            }
            pb_main.visibility = it
        })
    }

    fun fillList(r: List<String>) {
        val adapter = ListAdapter(r)
        ryv.adapter = adapter
    }
}


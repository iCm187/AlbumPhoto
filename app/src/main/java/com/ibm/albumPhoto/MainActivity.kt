package com.ibm.albumPhoto

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibm.albumPhoto.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewItems()
    }

    private fun setViewItems() {
        setTitle(getString(R.string.app_name))
        binding.searchBt.setOnClickListener {
            callService()
            binding.searchBt.visibility = View.INVISIBLE
            binding.progress.visibility = View.VISIBLE
        }
    }

    private fun callService() {
        val service: PhotoApi.PhotoService = PhotoApi().getClient().create(PhotoApi.PhotoService::class.java)
        val searchCriteria = binding.searchEt.text.toString() // Use binding to get the EditText's text
        val call: Call<PhotoApiResponse> = service.getPhotos("pimLpJ1qVgCY0NWL9sPvpE3wIRXj6uHglP0oTiXdFqtyef516kLZc0fB", searchCriteria)

        call.enqueue(object : Callback<PhotoApiResponse> {
            override fun onResponse(call: Call<PhotoApiResponse>, response: Response<PhotoApiResponse>) {
                processResponse(response)
                searchEnded()
            }

            override fun onFailure(call: Call<PhotoApiResponse>, t: Throwable) {
                processFailure(t)
                searchEnded()
            }
        })
    }

    private fun searchEnded() {
        binding.searchBt.visibility = View.VISIBLE
        binding.progress.visibility = View.INVISIBLE
    }

    private fun processFailure(t: Throwable) {
        Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show()
    }

    private fun processResponse(response: Response<PhotoApiResponse>) {
        val body = response.body()
        if (body?.photos?.isNotEmpty() == true) {
            val adapter = PhotoListViewAdapter(body.photos)
            binding.photoRv.adapter = adapter // Use binding for RecyclerView
            binding.photoRv.layoutManager = LinearLayoutManager(this)
        }
    }
}

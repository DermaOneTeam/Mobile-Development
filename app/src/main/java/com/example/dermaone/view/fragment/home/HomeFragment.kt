package com.example.dermaone.view.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dermaone.R
import com.example.dermaone.api.ApiConfig
import com.example.dermaone.api.responses.HistoriesResponse
import com.example.dermaone.view.classification.LoadingDialog
import com.example.dermaone.view.main.DetailHistoryActivity
import com.example.dermaone.view.profile.ProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var historyResponse: ArrayList<HistoriesResponse> = arrayListOf()
    private lateinit var adapter: HistoryAdapter

    companion object {
        fun newInstance(param1: String, param2: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("param1", param1)
            args.putString("param2", param2)
            fragment.arguments = args
            return fragment
        }
    }

    private var id: String? = null
    private var username: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString("param1")
            username = it.getString("param2")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val helloText = getView()?.findViewById<TextView>(R.id.tv_hello)
        helloText!!.text = "Hello ${username}!"

        val layoutManager = GridLayoutManager(context, 2)
        recyclerView = view.findViewById(R.id.rv_history)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = HistoryAdapter(historyResponse)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        adapter.onItemClick = { selectedItem ->
            val intent = Intent(context, DetailHistoryActivity::class.java).apply {
                putExtra("listHistory", selectedItem)
            }
            startActivity(intent)
        }

        println("id: ${id}")
        println("username: ${username}")

        val loadingDialog = LoadingDialog(requireActivity())
        try {
            loadingDialog.startLoadingDialog()
            ApiConfig.getApiService().getAllHistory(id!!).enqueue(object : Callback<ArrayList<HistoriesResponse>> {
                    override fun onResponse(call: Call<ArrayList<HistoriesResponse>>, response: Response<ArrayList<HistoriesResponse>>) {
                        val data = response.body()
                        println("response body: ${data}")
                        if (response.isSuccessful) {
                            println("response: ${response.message()}")
                            setDataToAdapter(data!!)
                            loadingDialog.dismissDialog()
                        }else{
                            println("response not success: ${response.message()}")
                            loadingDialog.dismissDialog()
                        }
                    }
                    override fun onFailure(call: Call<ArrayList<HistoriesResponse>>, t: Throwable) {
                        loadingDialog.dismissDialog()
                        println("onFailure fragment: ${t.message}")
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }catch (e: Exception){
            loadingDialog.dismissDialog()
            println("exception fragment: ${e.message}")
            Toast.makeText(context, "exception fragment: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        val profilePicture = view.findViewById<ImageView>(R.id.iv_profile_picture)
        profilePicture.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }
    }
    private fun setDataToAdapter(data: ArrayList<HistoriesResponse>){
        adapter.setData(data)
    }
}
package com.example.dermaone.view.fragment.article

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dermaone.R
import com.example.dermaone.api.ApiConfig
import com.example.dermaone.api.responses.ArticlesResponse
import com.example.dermaone.api.responses.HistoriesResponse
import com.example.dermaone.view.classification.LoadingDialog
import com.example.dermaone.view.fragment.home.HistoryAdapter
import com.example.dermaone.view.main.DetailHistoryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private var articlesResponse: ArrayList<ArticlesResponse> = arrayListOf()
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ArticleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rv_article)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ArticleAdapter(articlesResponse)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        adapter.onItemClick = { selectedItem ->
            val intent = Intent(context, DetailArticleActivity::class.java).apply {
                putExtra("listHistory", selectedItem)
            }
            startActivity(intent)
        }

        val loadingDialog = LoadingDialog(requireActivity())
        try {
            loadingDialog.startLoadingDialog()
            ApiConfig.getApiService().getArticles().enqueue(object : Callback<ArrayList<ArticlesResponse>> {
                override fun onResponse(call: Call<ArrayList<ArticlesResponse>>, response: Response<ArrayList<ArticlesResponse>>) {
                    val data = response.body()
                    println(data)
                    if(response.isSuccessful){
                        setDataToAdapter(data!!)
                        loadingDialog.dismissDialog()
                    }
                }
                override fun onFailure(call: Call<ArrayList<ArticlesResponse>>, t: Throwable) {
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
    }
    private fun setDataToAdapter(data: ArrayList<ArticlesResponse>){
        adapter.setData(data)
    }

}
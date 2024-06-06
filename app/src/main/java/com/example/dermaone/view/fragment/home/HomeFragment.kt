package com.example.dermaone.view.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dermaone.R
import com.example.dermaone.view.profile.ProfileActivity

//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
//    private var param1: String? = null
//    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var historyData: ArrayList<HistoryData>
    lateinit var historyImagesList: Array<Int>
    lateinit var historyDateList: Array<String>
    private lateinit var adapter: HistoryAdapter

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            HomeFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView = view.findViewById(R.id.rv_history)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = HistoryAdapter(historyData)
        recyclerView.adapter = adapter

        val profilePicture = view.findViewById<ImageView>(R.id.iv_profile_picture)
        profilePicture.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }
    }

    private fun getData(){

        historyData = arrayListOf<HistoryData>()

        historyImagesList = arrayOf(
            R.drawable.batman,
            R.drawable.cartoon,
            R.drawable.forest,
            R.drawable.shawshank,
            R.drawable.spiderman
        )
        historyDateList = arrayOf(
            "01/01/2024",
            "02/02/2024",
            "03/03/2024",
            "04/04/2024",
            "05/05/2024"
        )

        for(i in historyImagesList.indices){
            val data = HistoryData(historyImagesList[i], historyDateList[i])
            historyData.add(data)
        }
//        recyclerView.adapter = HistoryAdapter(historyData)
    }
}
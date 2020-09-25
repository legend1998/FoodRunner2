package com.example.foodrunner2.fragement


import FaqAdapter
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.foodrunner2.R
import com.example.foodrunner2.model.Faqs

/**
 * A simple [Fragment] subclass.
 */
class FaqsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var progresslayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    var faqlist = arrayListOf<Faqs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_faqs, container, false)

        recyclerView = view.findViewById(R.id.faqrecycler)
        progresslayout = view.findViewById(R.id.faqprogresslayout)
        progressBar = view.findViewById(R.id.faqprogress)

        progresslayout.visibility = View.VISIBLE

        faqlist.add(
            Faqs(
                "How did I find this course helpful.?",
                "before this course I want to learn android, and I tried to learn " +
                        "it from youtube and other free platform but I found that there they " +
                        "just do it without expalaining it and there is no any backgroud theories" +
                        "so that is i what i am looking for and I found it here"
            )
        )
        faqlist.add(
            Faqs("why I do this course android?",
                "As in the coming time the market of android is going to increase and oppurtunities too," +
                        " though i thought I should start it with basics and it also enhance my freelance skills.")
        )

        val layoutManager = LinearLayoutManager(activity as Context)
        val adapter = FaqAdapter(activity as Context, faqlist)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        progresslayout.visibility  = View.GONE
        return view

    }


}

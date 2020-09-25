package com.example.foodrunner2.fragement


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ShareActionProvider
import android.widget.TextView

import com.example.foodrunner2.R
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    lateinit var nameText:TextView
    lateinit var numberText :TextView
    lateinit var emailText:TextView
    lateinit var addressText :TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)

        val sharedPreferences =
            this.context?.getSharedPreferences(getString(R.string.sharedPreference),Context.MODE_PRIVATE)



        nameText = view.findViewById(R.id.profile_name)
        numberText = view.findViewById(R.id.profile_number)
        emailText = view.findViewById(R.id.profile_email)
        addressText = view.findViewById(R.id.profile_address)


        if(sharedPreferences!=null){
            nameText.text = sharedPreferences.getString("user","username")
            numberText.text =sharedPreferences.getString("mobile_number","no phone")
            emailText.text  = sharedPreferences.getString("email","not found email")
            addressText.text = sharedPreferences.getString("address","no address found")
        }


        return view
    }


}
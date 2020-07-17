package com.example.roomdatabase.fragment.add

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdatabase.R
import com.example.roomdatabase.viewModel.UserViewModel
import com.example.roomdatabase.model.User
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val firstname = addFirstname_et.text.toString()
        val lastname = addLastname_et.text.toString()
        val age = addAge_et.text

        if (checkInput(firstname,lastname, age)){

            val user = User(
                0,
                firstname,
                lastname,
                Integer.parseInt(age.toString())
            )
            mUserViewModel.addUser(user)

            Toast.makeText(requireContext(),"Succesfully added",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        }else{
            Toast.makeText(requireContext(),"Please fill out all the fields",Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkInput(firstname: String, lastname: String, age: Editable): Boolean {
        //return !(TextUtils.isEmpty(firstname) && TextUtils.isEmpty(lastname) && age.isEmpty())

        return !(firstname.isEmpty() || lastname.isEmpty() || age.isEmpty())

    }

}

package com.example.roomdatabase.fragment.update

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.roomdatabase.R
import com.example.roomdatabase.model.User
import com.example.roomdatabase.viewModel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.fragment_update.view.updateFirstname_et

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserviewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)

        mUserviewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.updateFirstname_et.setText(args.currentUser.firstname)
        view.updateLastname_et.setText(args.currentUser.lastname)
        view.updateAge_et.setText(args.currentUser.age.toString())

        view.update_btn.setOnClickListener {
            updateDataToDataBase()
        }

        return view
    }

    private fun updateDataToDataBase() {
        val firstname = updateFirstname_et.text.toString()
        val lastname = updateLastname_et.text.toString()
        val age = updateAge_et.text

        if(checkInput(firstname,lastname, age)){
            val user = User(
                0,
                firstname,
                lastname,
                Integer.parseInt(age.toString())
            )
            mUserviewModel.updateUser(user)
            Toast.makeText(requireContext(),"Succesfully updated User", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all the fields",Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkInput(firstname: String, lastname: String, age: Editable): Boolean {
        //return !(TextUtils.isEmpty(firstname) && TextUtils.isEmpty(lastname) && age.isEmpty())

        return !(firstname.isEmpty() || lastname.isEmpty() || age.isEmpty())

    }

}

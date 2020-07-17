package com.example.roomdatabase.fragment.update

import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
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

        setHasOptionsMenu(true)

        view.updateFirstname_et.setText(args.currentUser.firstname)
        view.updateLastname_et.setText(args.currentUser.lastname)
        view.updateAge_et.setText(args.currentUser.age.toString())

        view.update_btn.setOnClickListener {
            updateDataToDataBase()
        }

        view.delete_btn.setOnClickListener {
            deleteUserData()
        }

        return view
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater?.inflate(R.menu.delete_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId = R.id.delete_menu){
//
//        }
//    }

    private fun deleteUserData(){

//        val deleteUser = User(
//            args.currentUser.id,args.currentUser.firstname,args.currentUser.lastname,Integer.parseInt(args.currentUser.age.toString())
//        )
//        val deleteUser = User(
//            args.currentUser.id,firstname,lastname,Integer.parseInt(age.toString())
//        )

        /*
        * Question
        * In Line 56 and 59, I implemented different function to delete the user details
        * I understand the argument part should work i.e line 56 but why should ine 59 work??
        * Is it because of "args.currentUser.id" ?? Am stil confused */

        mUserviewModel.deleteUser(args.currentUser)
        Toast.makeText(requireContext(),"Succesfully Deleted User", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)

    }

    private fun updateDataToDataBase() {
        val firstname = updateFirstname_et.text.toString()
        val lastname = updateLastname_et.text.toString()
        val age = updateAge_et.text

        if(checkInput(firstname,lastname, age)){
            val updateUser = User(
                args.currentUser.id, firstname, lastname, Integer.parseInt(age.toString())
            )
            mUserviewModel.updateUser(updateUser)
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

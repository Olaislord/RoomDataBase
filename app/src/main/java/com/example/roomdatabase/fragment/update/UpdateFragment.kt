package com.example.roomdatabase.fragment.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentUpdateBinding
import com.example.roomdatabase.fragment.list.ListAdapter
import com.example.roomdatabase.model.User
import com.example.roomdatabase.viewModel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.fragment_update.view.updateFirstname_et

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private var userList = emptyList<User>()

    private val adapter = ListAdapter()

    private lateinit var mUserviewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentUpdateBinding.inflate(inflater, container, false)

        mUserviewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setHasOptionsMenu(true)

        binding.updateFirstnameEt.setText(args.currentUser.firstname)
        binding.updateLastnameEt.setText(args.currentUser.lastname)
        binding.updateAgeEt.setText(args.currentUser.age.toString())

        binding.updateBtn.setOnClickListener {
            updateDataToDataBase()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        if (adapter.itemCount > 0){
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes"){_,_->
                mUserviewModel.deleteUser(args.currentUser)
                Toast.makeText(requireContext(),
                    "Successfully removed: ${args.currentUser.firstname}?",
                    Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }

            builder.setNegativeButton("No"){_,_ ->}
            builder.setTitle("Delete ${args.currentUser.firstname}?")
            builder.setMessage("Are you sure you want to delete ${args.currentUser.firstname}?")
            builder.create().show()

        }else{
            Toast.makeText(requireContext(),
                "You have not saved any User yet",Toast.LENGTH_SHORT).show()

        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.roomdatabase.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentListBinding
import com.example.roomdatabase.model.User
import com.example.roomdatabase.viewModel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserViewModel: UserViewModel

    private val adapter = ListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentListBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        //Recyclerview
        //val adapter = ListAdapter()
        val recyclerView = binding.recylerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //UserView Model
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
            user.isEmpty()
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            deleteAllUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUser() {

        if (adapter.itemCount > 0){
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes"){_,_->
                mUserViewModel.deleteAllUser()
                Toast.makeText(requireContext(),
                    "Deleted All Users",Toast.LENGTH_SHORT ).show()
            }
            builder.setNegativeButton("No"){_,_->}
            builder.setTitle("Delete All Users")
            builder.setMessage("Are you sure you want to Delete all users")
            builder.create().show()
        }else{
            Toast.makeText(requireContext(),"No User data created",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

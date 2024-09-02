package com.fps69.abworkmanager.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fps69.abworkmanager.Fragments.EmployeesFragmentDirections
import com.fps69.abworkmanager.R
import com.fps69.abworkmanager.databinding.ItemEmployeeListBinding
import com.fps69.abworkmanager.dataclass.User

class EmployeeListAdapter:RecyclerView.Adapter<EmployeeListAdapter.EmployeeListViewHolder>() {
    class EmployeeListViewHolder(val binding : ItemEmployeeListBinding) : RecyclerView.ViewHolder(binding.root){

    }

    val diffUtil = object: DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeListViewHolder {
        val binding = ItemEmployeeListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EmployeeListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: EmployeeListViewHolder, position: Int) {
        val empData = differ. currentList[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(empData.userImage).into(ivEmployeeItemImage)
            tvEmployeeItemName.text  = empData.userName
        }
        holder.itemView.setOnClickListener {
              // ye navigation ke liye use kr rhe the jb Data pass nhi krna tha
//            Navigation.findNavController(it).navigate(R.id.action_employeesFragment_to_worksFragment)

            // Jb Data pass krna hai dusre fragment me to ye use kr rhe hai
            val action = EmployeesFragmentDirections.actionEmployeesFragmentToWorksFragment(empData)
            Navigation.findNavController(it).navigate(action)
        }
    }
}
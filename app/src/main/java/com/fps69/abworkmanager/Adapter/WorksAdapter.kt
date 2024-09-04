package com.fps69.abworkmanager.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fps69.abworkmanager.R
import com.fps69.abworkmanager.databinding.ItemViewWorksFragmentBinding
import com.fps69.abworkmanager.dataclass.Works

class WorksAdapter(val onUnassignedButtonClicked: (Works) -> Unit) :
    RecyclerView.Adapter<WorksAdapter.WorksViewHolder>() {
    class WorksViewHolder(val binding: ItemViewWorksFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Works>() {
        override fun areItemsTheSame(oldItem: Works, newItem: Works): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Works, newItem: Works): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorksViewHolder {
        val binding =
            ItemViewWorksFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorksViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: WorksViewHolder, position: Int) {

        val works = differ.currentList[position]
        val isExpanded = works.expanded
        holder.binding.apply {
            tvTitle.text = works.workTitle
            tvDate.text = works.workLastDate
            tvWorkDescription.text = works.workDesc
//            when(works.workStatus){
//                "1"->holder.binding.ivOval.setImageResource(R.drawable.yellow_circle)
//                "2"->holder.binding.ivOval.setImageResource(R.drawable.yellow_circle)
//                "3"->holder.binding.ivOval.setImageResource(R.drawable.red_circle)
//                else -> holder.binding.ivOval.setImageResource(R.drawable.baseline_logout_24)
//            }
            if (works.workPriority == "1") {
                holder.binding.ivOval.setImageResource(R.drawable.green_circle)
            } else if (works.workPriority == "2") {
                holder.binding.ivOval.setImageResource(R.drawable.yellow_circle)
            } else if (works.workPriority == "3") {
                holder.binding.ivOval.setImageResource(R.drawable.red_circle)
            } else {
                holder.binding.ivOval.setImageResource(R.drawable.baseline_logout_24) // Default case
            }
            when (works.workStatus) {
                "1" -> {
                    holder.binding.tvStatus.text = "Pending"
                    holder.binding.tvStatus.setTextColor(holder.binding.root.context.getColor(R.color.red))
                }

                "2" -> {
                    holder.binding.tvStatus.text = "Progress"
                    holder.binding.tvStatus.setTextColor(holder.binding.root.context.getColor(R.color.yellow))
                }

                "3" -> {
                    holder.binding.tvStatus.text = "Completed"
                    holder.binding.tvStatus.setTextColor(holder.binding.root.context.getColor(R.color.green))
                }

                else -> holder.binding.tvStatus.text = "Unknown"
            }

            tvWorkDescription.visibility = if (isExpanded) View.VISIBLE else View.GONE
            tvWorkDescriptionT.visibility = if (isExpanded) View.VISIBLE else View.GONE
            btnWorkUnassigned.visibility = if (isExpanded) View.VISIBLE else View.GONE

            constraintLayout.setOnClickListener {
                isAnyItemExpanded(position)
                Log.d("WorksAdapter", "Binding work: ${works.expanded}")
                works.expanded = !works.expanded
//                notifyItemChanged(position, 0) //  >>> Jb paylod(notifyItemChanged(expandedItemIndex, 0)) use krege tb
                notifyDataSetChanged() // Update entire list (Jb paylode use nhi kr rhe hai tb )
            }
            btnWorkUnassigned.setOnClickListener {
                onUnassignedButtonClicked(works)
            }
        }
    }

    private fun isAnyItemExpanded(position: Int) {
        val expandedItemIndex = differ.currentList.indexOfFirst {
            it.expanded
            /*
            >>>The line val expandedItemIndex = differ.currentList.indexOfFirst { it.expanded }
                is used to find the index of the first item in the differ.currentList that is
                currently expanded. */
        }
        if (expandedItemIndex >= 0 && expandedItemIndex != position) {
            differ.currentList[expandedItemIndex].expanded = false
//            notifyItemChanged(expandedItemIndex, 0)  >>> Jb paylod(notifyItemChanged(expandedItemIndex, 0)) use krege tb
            notifyDataSetChanged() // Update entire list (Jb paylode use nhi kr rhe hai tb )
        }
    }
    /*
    // Jb Paylod use krege tb isko use krte hai 

   override fun onBindViewHolder(
        holder: WorksViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty() && payloads[0] == 0) {
            holder.binding.apply {
                tvWorkDescription.visibility = View.GONE
                tvWorkDescriptionT.visibility = View.GONE
                btnWorkUnassigned.visibility = View.GONE
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    } */
}
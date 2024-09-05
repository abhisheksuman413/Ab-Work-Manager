package com.fps69.abworkmanager.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fps69.abworkmanager.R
import com.fps69.abworkmanager.databinding.ItemViewEmployeeAllWorksBinding
import com.fps69.abworkmanager.dataclass.Works
import com.google.android.material.button.MaterialButton

class EmployeeActivityAllWorksAdapter(
    val onCompletedButtonClicked: (Works, MaterialButton) -> Unit,
    val onStartingButtonClicked: (Works, MaterialButton) -> Unit
) :
    RecyclerView.Adapter<EmployeeActivityAllWorksAdapter.EmployeeActivityAllWorksViewHolder>() {
    class EmployeeActivityAllWorksViewHolder(val binding: ItemViewEmployeeAllWorksBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Works>() {
        override fun areItemsTheSame(oldItem: Works, newItem: Works): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Works, newItem: Works): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeeActivityAllWorksViewHolder {
        val binding = ItemViewEmployeeAllWorksBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmployeeActivityAllWorksViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: EmployeeActivityAllWorksViewHolder, position: Int) {
        val works = differ.currentList[position]
        val isExpanded = works.expanded
        holder.binding.apply {
            tvTitle.text = works.workTitle
            tvWorkDescription.text = works.workDesc
            tvDate.text = works.workLastDate
            when (works.workPriority) {
                "1" -> ivOval.setImageResource(R.drawable.green_circle)
                "2" -> ivOval.setImageResource(R.drawable.yellow_circle)
                "3" -> ivOval.setImageResource(R.drawable.red_circle)
            }
            when (works.workStatus) {
                "1" -> {
                    tvStatus.text = "Pending"
                    tvStatus.setTextColor(holder.binding.root.context.getColor(R.color.red))
                }

                "2" -> {
                    tvStatus.text = "In Progress"
                    tvStatus.setTextColor(holder.binding.root.context.getColor(R.color.yellow))
                }

                "3" -> {
                    tvStatus.text = "Completed"
                    tvStatus.setTextColor(holder.binding.root.context.getColor(R.color.green))
                }
            }

            tvWorkDescription.visibility = if (isExpanded) View.VISIBLE else View.GONE
            tvWorkDescriptionT.visibility = if (isExpanded) View.VISIBLE else View.GONE
            btnStarting.visibility = if (isExpanded) View.VISIBLE else View.GONE
            btnCompleted.visibility = if (isExpanded) View.VISIBLE else View.GONE

            constraintLayout.setOnClickListener {
                isAnyItemExpanded(position)
                works.expanded = !works.expanded
                notifyDataSetChanged() // Update entire list
            }
            if(tvStatus.text == "In Progress" || tvStatus.text == "Completed"){
                btnStarting.text = "In Progress"
                btnStarting.setTextColor(holder.binding.root.context.getColor(R.color.Light5))
            }
            if(tvStatus.text == "Completed"){
                btnCompleted.text = "Work Completed"
                btnStarting.setTextColor(holder.binding.root.context.getColor(R.color.Light5))
            }
            btnStarting.setOnClickListener {
                onStartingButtonClicked(works,btnStarting)
            }
            btnCompleted.setOnClickListener {
                onCompletedButtonClicked(works,btnCompleted)
            }
        }
    }

    private fun isAnyItemExpanded(position: Int) {
        val expandedItemIndex = differ.currentList.indexOfFirst {
            it.expanded
        }
        if (expandedItemIndex >= 0 && expandedItemIndex != position) {
            differ.currentList[expandedItemIndex].expanded = false
            notifyDataSetChanged() // Update entire list
        }
    }
}
package com.example.apptodo.legacy_view

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.apptodo.R
import com.example.apptodo.data.entity.TodoItem
import com.example.apptodo.utils.Relevance
import com.example.apptodo.databinding.TodoItemBinding

class ItemsAdapter(
    private val checkBoxListener: CheckBoxListener,
    private val editItemListener: EditItemListener
) : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {

    private val toDoList = ArrayList<TodoItem>()

    fun update(data: List<TodoItem>) {
        toDoList.clear()
        toDoList.addAll(data)
    }

    class ItemsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = TodoItemBinding.bind(item)

        fun bind(
            item: TodoItem, checkBoxListener: CheckBoxListener,
            editItemListener: EditItemListener
        ) = with(binding) {

            if (item.flagAchievement)
                makeItemDone(
                    descriptionText,
                    imageRelevance,
                    itemDeadline
                )
            else makeItemNotDone(
                item, descriptionText,
                imageRelevance,
                itemDeadline
            )

            descriptionText.text = item.text
            moreInformation.setOnClickListener {
                editItemListener.onItemEdit(item.id)
            }
            imageRelevance.setImageResource(item.relevance.image)

            itemDeadline.isVisible = !item.deadline.isNullOrEmpty()
            if (!item.deadline.isNullOrEmpty()) {
                itemDeadline.text = item.deadline
            }

            flagAchievement.setOnCheckedChangeListener(null)
            flagAchievement.isChecked = item.flagAchievement

            flagAchievement.setOnCheckedChangeListener { buttonView, isChecked ->
                checkBoxListener.onCheckBoxClicked(
                    item,
                    isChecked
                )
                if (isChecked)
                    makeItemDone(
                        descriptionText,
                        imageRelevance,
                        itemDeadline
                    )
                else makeItemNotDone(
                    item, descriptionText,
                    imageRelevance,
                    itemDeadline
                )
            }

            flagAchievement.buttonTintList = if (item.relevance == Relevance.URGENT)
                ResourcesConstantsEnum.URGENT_STATE_LIST.stateList
            else
                ResourcesConstantsEnum.LOW_BASE_STATE_LIST.stateList

            if (item.deadline.equals("дата")) itemDeadline.visibility = View.GONE

            imageRelevance.visibility = if(item.relevance == Relevance.BASE) View.GONE else View.VISIBLE

        }

        private fun makeItemDone(textView: TextView, image: ImageView, dateText: TextView) {
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.grey_text))
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            image.visibility = View.GONE
            dateText.setTextColor(ContextCompat.getColor(textView.context, R.color.grey_text))
            dateText.paintFlags = dateText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        private fun makeItemNotDone(
            item: TodoItem,
            textView: TextView,
            image: ImageView,
            dateText: TextView,
        ) {
            if (item.relevance != Relevance.BASE) {
                image.visibility = View.VISIBLE
            }
            else image.visibility = View.GONE
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            textView.setTextAppearance(R.style.standart_color_text);
            dateText.paintFlags = dateText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            dateText.setTextAppearance(R.style.standart_color_text);
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.bind(toDoList[position], checkBoxListener, editItemListener)

        holder.itemView.setOnClickListener {
            val itemId = toDoList[position].id
            editItemListener.onItemEdit(itemId)
        }
    }
}

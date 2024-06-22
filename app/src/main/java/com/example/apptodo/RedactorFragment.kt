package com.example.apptodo

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.apptodo.App
import com.example.apptodo.R
import com.example.apptodo.Relevance
import com.example.apptodo.TodoItem
import com.example.apptodo.databinding.FragmentRedactorBinding
import java.util.GregorianCalendar
import java.util.UUID


class RedactorFragment : Fragment() {

    private val viewModel: RedactorViewModel by viewModels() {
        RedactorViewModel.factory((requireActivity().application as App).todoItemsRepository)
    }

    companion object {
        const val EDIT_ID = "edit_item_id_key"

        fun newInstance(id: String): RedactorFragment {
            val fragment = RedactorFragment().apply {
                arguments = bundleOf(EDIT_ID to id)
            }
            return fragment
        }
    }

    private lateinit var binding: FragmentRedactorBinding

    private var isDateValid = true

    override fun onResume() {
        super.onResume()
        val relevances = resources.getStringArray(R.array.relevance_states)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_menu_item, relevances)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRedactorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var flagAchievement = false
        val idToEdit = arguments?.getString(EDIT_ID) ?: UUID.randomUUID().toString()
        if (idToEdit.isNotEmpty()) {
            viewModel.getItemInformation(idToEdit)
        }

        binding.closeButton.setOnClickListener {
            back()
        }

        binding.switchButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                showDatePickerDialog()
            } else {
                binding.dateText.visibility = View.GONE
            }
        })

        viewModel.item.observe(viewLifecycleOwner){ item ->
            binding.textDescription.setText(item.text)
            binding.autoCompleteTextView.setText(getRelevanceText(item.relevance))

            flagAchievement = item.flagAchievement
            binding.switchButton.setOnCheckedChangeListener(null)
            binding.switchButton.isChecked = !item.deadline.isNullOrEmpty()
            binding.switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    showDatePickerDialog()
                } else {
                    binding.dateText.visibility = View.GONE
                }
            }

            binding.dateText.isVisible = !item.deadline.isNullOrEmpty()
            if(!item.deadline.isNullOrEmpty()) {
                binding.dateText.text = item.deadline.toString()
            }
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteItem(idToEdit)
            back()
        }

        binding.saveText.setOnClickListener {
            val description = binding.textDescription.text
            val relevance = binding.autoCompleteTextView.text
            val deadline = binding.dateText.text.toString()

            val creatingDate = getCurrentTime()
            if (dataValidate(isDateValid, description.toString())) {
                if (binding.switchButton.isChecked){
                    val newItem = TodoItem(
                        idToEdit, description.toString(), getRelevance(relevance.toString()),
                        deadline, flagAchievement, creatingDate, creatingDate
                    )
                    viewModel.addItem(newItem)
                    back()
                }
                else{
                    val newItem = TodoItem(
                        idToEdit, description.toString(), getRelevance(relevance.toString()),
                        null, flagAchievement, creatingDate, creatingDate
                    )
                    viewModel.addItem(newItem)
                    back()
                }
            } else Toast.makeText(context, "Проверьте введенные данные", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getCurrentTime(): String {
        val calendar = GregorianCalendar()

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        return day.toString() + "/" + month.toString() + "/" + year
    }


    private fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity()
            .supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_place, fragment).commit()
    }

    private fun back(){
        requireActivity()
            .supportFragmentManager.popBackStack()
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DatePicker1,
            { view, year, monthOfYear, dayOfMonth ->
                val today = Calendar.getInstance().apply {
                    set(year, monthOfYear, dayOfMonth)
                }
                if (today.before(Calendar.getInstance())) {
                    binding.dateText.setTextColor(resources.getColor(R.color.red))
                    binding.dateText.text = "Некорректный выбор даты"
                    isDateValid = false
                } else {
                    isDateValid = true
                    binding.dateText.text =
                        dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                    binding.dateText.setTextColor(resources.getColor(R.color.primary_blue))
                }
                binding.dateText.visibility = View.VISIBLE
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun dataValidate(date: Boolean, text: String): Boolean {
        if (text.isBlank() || !date) return false
        else return true
    }

    private fun getRelevance(relevance: String): Relevance {
        if (relevance.equals("Нет")) return Relevance.BASE
        else if (relevance.equals("Низкая")) return Relevance.LOW
        else return Relevance.URGENT
    }

    private fun getRelevanceText(relevance: Relevance):String  =
        when(relevance){
            Relevance.BASE -> "Нет"
            Relevance.LOW -> "Низкая"
            else -> "Высокая"
        }


}

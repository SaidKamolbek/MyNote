package com.example.mynote.ui.insert

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynote.R
import com.example.mynote.databinding.FragmentInsertScreenBinding
import com.example.mynote.model.Note
import com.example.mynote.viewModel.NoteViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import yuku.ambilwarna.AmbilWarnaDialog
import java.text.SimpleDateFormat
import java.util.*

class InsertScreen : Fragment() {


    private var _binding: FragmentInsertScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel
    private var defaultColor: Int = Color.GREEN

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        _binding = FragmentInsertScreenBinding.inflate(inflater, container, false)

        binding.datePicker.setOnClickListener {
            openCalendarDialog()
        }
        binding.timePicker.setOnClickListener {
            openTimePicker()
        }
        val sdf = SimpleDateFormat("HH:mm")
        val currentTime = sdf.format(Date())
        binding.insertTime.text = currentTime
        val day = Calendar.getInstance().get(Calendar.DATE)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val year = Calendar.getInstance().get(Calendar.YEAR)
        binding.insertDate.text = "$day/$month/$year"
        binding.insertChangeC.setBackgroundColor(defaultColor)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.insertChangeC.setOnClickListener {
            showColorDialog()
        }
        ifNoteScreenOpens()
    }


    private fun showColorDialog() {
        val dialog = AmbilWarnaDialog(
            requireContext(),
            defaultColor,
            object : AmbilWarnaDialog.OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog?) {

                }

                override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                    defaultColor = color
                    binding.insertChangeC.setBackgroundColor(color)
                }

            })
        dialog.show()
    }

    private fun ifNoteScreenOpens() {

        // to like the note
        var state = 0
        binding.insertLike.setOnClickListener {
            state = if (state == 0) {
                binding.insertLike.setImageResource(R.drawable.ic_baseline_star_24)
                1
            } else {
                binding.insertLike.setImageResource(R.drawable.ic_baseline_star_outline_24)
                0
            }
        }


        binding.textInputLayout2.visibility = View.VISIBLE
        binding.insertTitle.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()) {
                binding.textInputLayout.error = null
            }
        }

        binding.insertAddClose.setOnClickListener {
            val title = binding.insertTitle.text.toString()
            val text = binding.insertText.text.toString()
            if (title.isEmpty()) {
                binding.textInputLayout.error = "Title cannot be empty"
            } else {
                viewModel.addNote(
                    Note(
                        id = 0,
                        title = title,
                        text = text,
                        color = defaultColor,
                        type = state,
                        date = binding.insertDate.text.toString(),
                        time = binding.insertTime.text.toString()
                    )
                )
                findNavController().navigateUp()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun openCalendarDialog() {
        val picker = DatePickerDialog(requireContext())
        picker.datePicker.minDate = System.currentTimeMillis() - 1000
        picker.setOnDateSetListener { _, year, month, dayOfMonth ->
            binding.insertDate.text = "$dayOfMonth/${month + 1}/$year"
        }
        picker.show()
    }

    private fun openTimePicker() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .build()

        picker.show(childFragmentManager, "TAG")
        picker.addOnPositiveButtonClickListener {
            binding.insertTime.text = "${picker.hour}:${picker.minute}"
        }
    }

}
package com.example.taller2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragmento_hoobies.*


class HobbiesFragmento : Fragment() {

    // Encontraremos la lista de Hobbies
    private val hobbiesNames
        get() = resources.getStringArray(R.array.list_hobbies)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // diseño de fragmento
        return inflater.inflate(R.layout.fragmento_hoobies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as HomeActivity // obtener datos de la actividad

        // Establecer datos en el texView
        hobbies_h_value.text = activity.userData!!.hobbies_text

        hobbies_h_button.setOnClickListener {
            this.getHobbies()
        }

    }
    // Obtener lista de los hobbies del usuario
    @SuppressLint("SetTextI18n")
    private fun getHobbies() {

        val activity = requireActivity() as HomeActivity
        val checked = activity.userData!!.hobbies_check
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.hobbies_textView)
        builder.setPositiveButton(R.string.agregar, null)
        builder.setCancelable(false)
        builder.setMultiChoiceItems(hobbiesNames, checked) { _, position, isChecked ->
            checked[position] = isChecked
            if(!checked.all { value-> value==false }) {
                if (isChecked) {
                    hobbies_h_value.setText(hobbies_h_value.text.toString() + hobbiesNames[position] + " | ")
                } else {
                    hobbies_h_value.setText(hobbies_h_value.text.toString().replace(hobbiesNames[position] + " | ", ""))
                }
                activity.userData!!.hobbies_text = hobbies_h_value.text.toString()
            }else{
                checked[position] = !isChecked
                Toast.makeText(requireContext(), R.string.hobbies_requirido, Toast.LENGTH_LONG).show()
            }
        }
        builder.show()
    }

}
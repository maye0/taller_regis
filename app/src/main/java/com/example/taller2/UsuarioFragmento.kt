package com.example.taller2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragmento_usuario.*

class UsuarioFragmento : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmento_usuario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as HomeActivity

        user_name_value.text = activity.userData!!.name
        user_last_name_value.text = activity.userData!!.last_name
        user_type_document_value.text = activity.userData!!.type_document
        user_document_value.text = activity.userData!!.number_document
        user_bdate_label.text = activity.userData!!.date
        user_hobies_value.text = activity.userData!!.hobbies_text
        user_password_value.text = activity.userData!!.password

    }

}
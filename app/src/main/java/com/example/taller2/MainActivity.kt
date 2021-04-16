package com.example.taller2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_inicio.*
import java.time.LocalDate
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {


    private val optionsTypeDocument: Array<String>
        get() = resources.getStringArray(R.array.name_document)

    private val acronymTypeDocument
        get() = resources.getStringArray(R.array.acronimo)

    private val hobbiesNames
        get() = resources.getStringArray(R.array.list_hobbies)

    private var hobbiesChecked:BooleanArray = booleanArrayOf()

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        hobbiesChecked = BooleanArray(hobbiesNames.size)

        this.setupButtons()
    }

    private fun resetForm() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.limpia_formu))
        builder.setMessage(getString(R.string.mensage_button_reset))
        builder.setPositiveButton(R.string.agregar) { _, _ ->
            this.cleanForm(true)
        }
        builder.setNegativeButton(R.string.cancelar, null)
        builder.setCancelable(false)
        builder.show()
    }

    private fun cleanForm(bool: Boolean) {
        name_input.setText("")
        last_name_input.setText("")
        type_document_button.text = getString(R.string.typo_documento_button)
        document_input.setText("")
        birth_date_button.text = getString(R.string.fecha_button)
        hobbies_text_choice.text = ""
        hobbiesChecked = BooleanArray(hobbiesNames.size)
        password_input.setText("")
        confirm_password_input.setText("")
        if (bool) {
            Toast.makeText(this, getText(R.string.limpia_formu_2), Toast.LENGTH_LONG).show()
        }
    }

    private fun getTypeDocument() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.Tipo_documento_textView)
        builder.setItems(this.optionsTypeDocument) { _, position ->
            type_document_button.text = acronymTypeDocument[position];
        }
        builder.show()
    }

    @SuppressLint("SetTextI18n")
    private fun getHobbies() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.hobbies_textView)
        builder.setPositiveButton(R.string.agregar, null)
        builder.setCancelable(false)
        builder.setMultiChoiceItems(hobbiesNames, hobbiesChecked) { _, position, isChecked ->
            hobbiesChecked[position] = isChecked
            if (isChecked){
                hobbies_text_choice.setText(hobbies_text_choice.text.toString() + hobbiesNames[position] + " | ")
            }else{
                hobbies_text_choice.setText(hobbies_text_choice.text.toString().replace(hobbiesNames[position]+" | ", ""))
            }
        }
        builder.show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getBirthday() {
        var year =1990
        var month =0
        var day =1

        if (birth_date_button.text.toString()!= getString(R.string.fecha_button)){
            val date = birth_date_button.text.toString().split("-")
            year = date[0].toInt()
            month = date[1].toInt()-1
            day = date[2].toInt()
        }

        val dialog = DatePickerDialog(this, { _,yearDate,monthDate,dayDate ->
            val numMonth = monthDate+1;
            birth_date_button.text = ("$yearDate-$numMonth-$dayDate")
        }, year, month, day)
        dialog.show()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun register() {

        var fieldsEmpty = " "

        fieldsEmpty+= this.validateFields(name_input, "",  getString(R.string.nombre_input))
        fieldsEmpty+= this.validateFields(last_name_input, "",  getString(R.string.Apellido_input))
        fieldsEmpty += this.validateFields(type_document_button, getString(R.string.typo_documento_button), getString(R.string.Tipo_documento_textView))
        fieldsEmpty+= this.validateFields(document_input, "",  getString(R.string.numero_doc_input))
        val isDateValidate = this.validateFields(birth_date_button, getString(R.string.fecha_button),  getString(R.string.Fecha_nacimiento_textView))

        var dateFuture: Boolean = false
        if (isDateValidate!="") {
            fieldsEmpty += isDateValidate
        } else {
            val currentDateTime = LocalDateTime.now().toLocalDate()
            val date = birth_date_button.text.toString().split("-")
            val dateUser = LocalDate.of(date[0].toInt(), date[1].toInt(),date[2].toInt())
            dateFuture = dateUser > currentDateTime
        }

        fieldsEmpty+= this.validateFields(hobbies_text_choice, "",  getString(R.string.hobbies_textView))
        fieldsEmpty+= this.validateFields(password_input, "",  getString(R.string.pass_input))
        fieldsEmpty+= this.validateFields(confirm_password_input, "",  getString(R.string.confirm_pass_input))

        if (fieldsEmpty != " ") {
            this.verifiedData(getString(R.string.campos_requeridos), fieldsEmpty, 1)
        } else if (dateFuture) {
            this.verifiedData(getString(R.string.futura_fecha), getString(R.string.futura_fecha_description), 2)
        } else if (!isPasswordSame(password_input.text.toString(), confirm_password_input.text.toString())){
            this.verifiedData(getString(R.string.diferente_passwords), getString(R.string.diferente_passwords_description), 3)
        } else {
            val user = Usuario(
                name = name_input.text.toString(),
                last_name = last_name_input.text.toString(),
                type_document = optionsTypeDocument[acronymTypeDocument.indexOf(type_document_button.text.toString())],
                number_document = document_input.text.toString(),
                birth_date = birth_date_button.text.toString(),
                hobbies_v = hobbies_text_choice.text.toString(),
                hobbies_c = this.hobbiesChecked,
                password = password_input.text.toString()
            )

            this.registerSuccess(user)
        }
    }

    private fun verifiedData(titleShow: String, textShow: String, type: Int) {
        val builder = AlertDialog.Builder(this)
        var textMessage = textShow
        if (type == 1) {
            textMessage = getString(R.string.campos_requirido_description) + textShow
        }
        builder.setTitle(titleShow)
        builder.setMessage(textMessage)
        builder.setPositiveButton(R.string.agregar, null)
        builder.setCancelable(false)
        builder.show()
    }
    private fun isPasswordSame(firstPassword: String, secondPassword: String): Boolean {
        return firstPassword == secondPassword;
    }
    private fun registerSuccess(data: Usuario) {

        val view = layoutInflater.inflate(R.layout.terminos_condiciones, null)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.titulo_terminoycondiciones))
        builder.setView(view)
        builder.setPositiveButton(getString(R.string.agregar)) { _, _ ->
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("User", data)
            startActivity(intent)
            this.cleanForm(false)
        }
        builder.setNegativeButton(getString(R.string.cancelar), null)
        builder.setCancelable(false)
        builder.show()
    }

    private fun validateFields(component: TextView, compare: String, textOut: String) :String{
        if (component.text.toString() == compare) {
            return "$textOut | "
        }
        return ""
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setupButtons() {
        reset_button.setOnClickListener {
            this.resetForm()
        }
        type_document_button.setOnClickListener{
            this.getTypeDocument()
        }
        hobbies_button.setOnClickListener{
            this.getHobbies()
        }
        birth_date_button.setOnClickListener {
            this.getBirthday()
        }
        register_button.setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                this.register()
            }
        }
    }

}
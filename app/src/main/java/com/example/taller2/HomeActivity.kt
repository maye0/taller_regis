package com.example.taller2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    var userData: Usuario? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userData = intent.getParcelableExtra<Usuario>("User")!!
        this.showFragment(UsuarioFragmento())

        home_title.text = getString(R.string.home_title, userData!!.name)

        home_button_user.setOnClickListener {
            this.showFragment(name = UsuarioFragmento())
        }

        home_button_hobbies.setOnClickListener {
            this.showFragment(name = HobbiesFragmento())
        }

        val buttonLogOut = findViewById<Button>(R.id.home_button_logout)
        buttonLogOut.setOnClickListener {
            finish()
        }

    }

    private fun showFragment(name: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_container, name, null)
        transaction.commit()
    }
}
package br.edu.scl.ifsp.sdm.contactlist.view // pacote de Views dentro do projeto ContactList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.scl.ifsp.sdm.contactlist.R
import br.edu.scl.ifsp.sdm.contactlist.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() { // classe ContactActivity herda de AppCompatActivity, que é uma activity base do suporte do Android

    // ViewBinding facilita a vinculação de views do layout XML com o código Java
    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }



    // onCreate é o método chamado quando a activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // ao substituir o método padrão definido em AppCompatActivity precisamos manter o comportamento original da superclasse para garantir a integridade do fluxo na classe ContactActivity
        // setContentView(R.layout.activity_contact)    // associa o layout XML da activity conforme foi definido especificamente em activity_contact.xml
                                                        // nesse caso as views precisarão ser vinculadas posteriormente chamando findViewById() para cada uma
        setContentView(acb.root) // o root view da ActivityContactBinding é passado para setContentView
                                                        // diferentemente do primeiro caso, as views já estarão vinculadas à instância do binding, não sendo necessário encontrar por ID individualmente

    }
}
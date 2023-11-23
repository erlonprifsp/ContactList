package br.edu.scl.ifsp.sdm.contactlist.view // pacote de Views dentro do projeto ContactList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.scl.ifsp.sdm.contactlist.R
import br.edu.scl.ifsp.sdm.contactlist.databinding.ActivityContactBinding
import br.edu.scl.ifsp.sdm.contactlist.model.Constant.EXTRA_CONTACT
import br.edu.scl.ifsp.sdm.contactlist.model.Contact

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

        //define um subtítulo para a activity
        setSupportActionBar(acb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.contact_details)

        val receivedContact = intent.getParcelableExtra< Contact>(EXTRA_CONTACT)
        receivedContact?.let { received ->
            with(acb) {
                nameEt.setText(received.name)
                addressEt.setText(received.address)
                phoneEt.setText(received.phone)
                emailEt.setText(received.email)
            }
        }

        // listener para o botão saveBt do ActivityContactBinding (acb)
        with(acb) {
            saveBt.setOnClickListener {

                // cria um objeto chamado contact para armazenar os dados informados pelo usuário
                val contact = Contact(
                    // id = hashCode(), // gera um inteiro aleatório, caso seja um novo contato
                    id = receivedContact?.id?:hashCode(),   // usada para atribuir um valor à variável id --> se a variável receivedContact tiver um valor, o valor do atributo id dessa variável será atribuído a id. Caso contrário, o valor do hashcode da variável id será atribuído a id.
                    name = nameEt.text.toString(),
                    address = addressEt.text.toString(),
                    phone = phoneEt.text.toString(),
                    email = emailEt.text.toString()
                )

                // intent para retornar os dados informados pelo usuário na activity ContactActivity
                val resultIntent = Intent() // declara uma intent vazia
                resultIntent.putExtra(EXTRA_CONTACT, contact) // objeto contact criado acima será armazenado na variável EXTRA_CONTACT
                setResult(RESULT_OK, resultIntent) // método para retornar os dados para a MainActivity carregados pela intent resultIntent
                finish() // fecha a activity ContactActivity
            }
        }
    }
}
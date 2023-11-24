package br.edu.scl.ifsp.sdm.contactlist.view // declara o pacote em que a classe MainActivity está localizada
// a classe MainActivity está localizada no subpacote view do pacote br.edu.scl.ifsp.sdm.contactlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity // importa a classe AppCompatActivity
// AppCompatActivity é a classe base para todas as atividades do AppCompat, que são atividades que utilizam o tema do AppCompat
import android.os.Bundle // importa a classe Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.scl.ifsp.sdm.contactlist.R // importa a classe R
import br.edu.scl.ifsp.sdm.contactlist.adapter.ContactAdapter
import br.edu.scl.ifsp.sdm.contactlist.adapter.ContactRvAdapter
// A classe Bundle é uma classe que representa um conjunto de pares de chave-valor. Os bundles são frequentemente usados para armazenar dados que precisam ser passados entre atividades ou entre uma atividade e um fragmento
import br.edu.scl.ifsp.sdm.contactlist.databinding.ActivityMainBinding
import br.edu.scl.ifsp.sdm.contactlist.model.Constant.EXTRA_CONTACT
import br.edu.scl.ifsp.sdm.contactlist.model.Constant.EXTRA_VIEW_CONTACT
import br.edu.scl.ifsp.sdm.contactlist.model.Contact



class MainActivity : AppCompatActivity(), OnContactClickListener { // declara a classe MainActivity como uma subclasse da classe AppCompatActivity, com isso, ela herdará todos os métodos e membros da classe AppCompatActivity
    private val amb: ActivityMainBinding by lazy { // declara uma variável privada chamada amb do tipo ActivityMainBinding
        // A expressão de delegação by lazy garante que a propriedade amb seja inicializada apenas quando for acessada pela primeira vez
        ActivityMainBinding.inflate(layoutInflater) // infla o layout activity_main.xml na memória e retorná-lo como uma instância da classe ActivityMainBinding
    }

    // Data source
    private val contactList: MutableList<Contact> = mutableListOf()

    // Adapter
    // private val contactAdapter: ArrayAdapter<String> by lazy {

    // private val contactAdapter: ContactAdapter by lazy {
    private val contactAdapter: ContactRvAdapter by lazy {
        // ContactAdapter(this, contactList)
        ContactRvAdapter(contactList, this)
    }

    // contactActivityResultLauncher
    private lateinit var carl: ActivityResultLauncher<Intent> // carl é declarado como lateinit var, ou seja, sua inicialização é adiada para depois do método onCreate(), isso permite referenciar o carl sem necessariamente já tê-lo inicializado, o que é feito posteriormente

    override fun onCreate(savedInstanceState: Bundle?) { // este método é chamado quando a atividade é criada pela primeira vez
        super.onCreate(savedInstanceState) // chama o método onCreate() da classe pai AppCompatActivity
        setContentView(amb.root) // define o conteúdo da atividade para a raiz do layout vinculado à propriedade amb

        //define um subtítulo para a activity
        setSupportActionBar(amb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.contact_list)

        // após a tela ContactActivity fechar, ela retorna um resultado
        carl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val contact =  result.data?.getParcelableExtra<Contact>(EXTRA_CONTACT)
                contact?.also{ newOrEditedContact -> // função lambda declarada dentro do escopo do also
                    if(contactList.any{ it.id == newOrEditedContact.id }) { // verifica se o contato já existe na lista de contatos

                        // edita o contato que já existe na lista
                        val position = contactList.indexOfFirst { it.id == newOrEditedContact.id } // pega a posição do contato na lista de células (view)
                        contactList[position] = newOrEditedContact
                    } else {
                        // contactList.add(contact) // adiciona o contato
                        contactList.add(newOrEditedContact) // adiciona o contato

                    }
                    // contactAdapter.add(contact.toString()) // atualiza o Adapter
                    contactAdapter.notifyDataSetChanged() // atualiza o Adapter após a alteração no DataSource
                }
            }
        }

        fillContacts() // chamada da função fillContacts()

        // amb.contactsLv.adapter = contactAdapter // define o adaptador

        amb.contactsRv.adapter = contactAdapter // define o adaptador
        amb.contactsRv.layoutManager = LinearLayoutManager(this)

        // menu de contexto
        // registerForContextMenu(amb.contactsLv) // associa a activity principal com o menu de opção de clique LONGO

        // listener para verificar se ocorreu um clique CURTO
        // amb.contactsLv.setOnItemClickListener { parent, view, position, id ->

        /*
        amb.contactsLv.setOnItemClickListener { _, _, position, _ ->

            // val contact = contactList[position]
            // val viewContactIntent = Intent(this, ContactActivity::class.java)
            // viewContactIntent.putExtra(EXTRA_CONTACT, contact)
            // startActivity(viewContactIntent)

            startActivity(Intent(this, ContactActivity::class.java).apply {
                putExtra(EXTRA_CONTACT, contactList[position])
                putExtra(EXTRA_VIEW_CONTACT, true)
            })
        }
        */


    }

    // função onCreateOptionsMenu infla e adiciona o layout do menu pré-definido no xml para que ele possa ser exibido quando o usuário interagir com o menu de opções
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // carrega o layout do menu definido no arquivo menu_main.xml e adiciona os itens desse layout para o "menu" passado como parâmetro
        return true // indica que a activity já se encarregou de criar o menu, então o framework Android não precisa tratar a criação do menu
    }

    // função onOptionsItemSelected é chamada sempre que o usuário seleciona um item do menu de opções
    override fun onOptionsItemSelected(item: MenuItem): Boolean { // função recebe como parâmetro o MenuItem que foi selecionado
       return when(item.itemId) { // usa uma expressão when como uma forma simplificada de if/else para verificar qual item foi clicado baseado no seu ID
           R.id.addContactMi -> { // se o item com ID addContactMi for clicado, a função retorna true
               // startActivity(Intent(this, ContactActivity::class.java)) // inicia uma nova activity sem tratar resultado
               carl.launch(Intent(this, ContactActivity::class.java)) // usa um ActivityResultLauncher para disparar a activity
               // a função launcher é útil quando se quer processar algum retorno da Activity
               // já startActivity serve para simplesmente abrir outra tela sem se preocupar com o retorno

               true
           }
           else -> { false }
       }
    }

    // exibe menu de opção após clique longo
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position // converte para AdapterContextMenuInfo e pega a posição da célula (view)

        return when(item.itemId) {
            R.id.removeContactMi -> {
                contactList.removeAt(position)
                contactAdapter.notifyDataSetChanged()
                Toast.makeText(this, getString(R.string.contact_removed), Toast.LENGTH_SHORT).show()
                true
            }
            R.id.editContactMi -> {
                // val contact = contactList[position] // pega o contato baseado na posição da célula (view)
                //val editContactIntent = Intent(this, ContactActivity::class.java) // cria uma intent
                //editContactIntent.putExtra(EXTRA_CONTACT, contact) //armazena os dados do contato na variável EXTRA_CONTACT
                //carl.launch(editContactIntent) // envia os dados para outra activity

                carl.launch(Intent(this, ContactActivity::class.java).apply {
                    putExtra(EXTRA_CONTACT, contactList[position])
                })

                true
            }
            else -> { false }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // unregisterForContextMenu(amb.contactsLv) // remove a associação com o menu de opção de clique longo
    }

    override fun onContactClick(position: Int) {
        Intent(this, ContactActivity::class.java).apply {
            putExtra(EXTRA_CONTACT, contactList[position])
            putExtra(EXTRA_VIEW_CONTACT, true)
        }.also {
            startActivity(it)
        }
    }

    // função que preenche o data source
    private fun fillContacts() {
        for (i in 1..10) {
            contactList.add(
                Contact(
                    i,
                    name = "Name $i",
                    address = "Endereço $i",
                    phone = "Telefone $i",
                    email = "Email $i"
                )
            )
        }
    }
}
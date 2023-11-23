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
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.scl.ifsp.sdm.contactlist.R // importa a classe R
import br.edu.scl.ifsp.sdm.contactlist.adapter.ContactAdapter
// A classe Bundle é uma classe que representa um conjunto de pares de chave-valor. Os bundles são frequentemente usados para armazenar dados que precisam ser passados entre atividades ou entre uma atividade e um fragmento
import br.edu.scl.ifsp.sdm.contactlist.databinding.ActivityMainBinding
import br.edu.scl.ifsp.sdm.contactlist.model.Constant.EXTRA_CONTACT
import br.edu.scl.ifsp.sdm.contactlist.model.Contact



class MainActivity : AppCompatActivity() { // declara a classe MainActivity como uma subclasse da classe AppCompatActivity, com isso, ela herdará todos os métodos e membros da classe AppCompatActivity
    private val amb: ActivityMainBinding by lazy { // declara uma variável privada chamada amb do tipo ActivityMainBinding
        // A expressão de delegação by lazy garante que a propriedade amb seja inicializada apenas quando for acessada pela primeira vez
        ActivityMainBinding.inflate(layoutInflater) // infla o layout activity_main.xml na memória e retorná-lo como uma instância da classe ActivityMainBinding
    }

    // Data source
    private val contactList: MutableList<Contact> = mutableListOf()

    // Adapter
    // private val contactAdapter: ArrayAdapter<String> by lazy {
    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(this, contactList)
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
                contact?.also{// função lambda declarada dentro do escopo do also
                    if(contactList.any{ it.id == contact.id }) { // verifica se o contato já existe na lista de contatos
                        // editar o contato que já existe na lista
                    } else {
                        contactList.add(contact) // adiciona o contato
                        // contactAdapter.add(contact.toString()) // atualiza o Adapter
                    }
                    contactAdapter.notifyDataSetChanged() // atualiza o Adapter após a alteração no DataSource
                }
            }
        }

        fillContacts() // chamada da função fillContacts()

        amb.contactsLv.adapter = contactAdapter // define o adaptador

        // associa a activity principal com o menu de opção de clique longo
        registerForContextMenu(amb.contactsLv)
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
        return when(item.itemId) {
            R.id.removeContactMi -> {
                true
            }
            R.id.editContactMi -> {
                true
            }
            else -> { false }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterForContextMenu(amb.contactsLv) // remove a associação com o menu de opção de clique longo
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
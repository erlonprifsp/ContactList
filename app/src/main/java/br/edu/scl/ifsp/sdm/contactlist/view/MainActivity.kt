package br.edu.scl.ifsp.sdm.contactlist.view // declara o pacote em que a classe MainActivity está localizada
// a classe MainActivity está localizada no subpacote view do pacote br.edu.scl.ifsp.sdm.contactlist

import androidx.appcompat.app.AppCompatActivity // importa a classe AppCompatActivity
// AppCompatActivity é a classe base para todas as atividades do AppCompat, que são atividades que utilizam o tema do AppCompat
import android.os.Bundle // importa a classe Bundle
// A classe Bundle é uma classe que representa um conjunto de pares de chave-valor. Os bundles são frequentemente usados para armazenar dados que precisam ser passados entre atividades ou entre uma atividade e um fragmento
import br.edu.scl.ifsp.sdm.contactlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() { // declara a classe MainActivity como uma subclasse da classe AppCompatActivity, com isso, ela herdará todos os métodos e membros da classe AppCompatActivity
    private val amb: ActivityMainBinding by lazy { // declara uma variável privada chamada amb do tipo ActivityMainBinding
        // A expressão de delegação by lazy garante que a propriedade amb seja inicializada apenas quando for acessada pela primeira vez
        ActivityMainBinding.inflate(layoutInflater) // infla o layout activity_main.xml na memória e retorná-lo como uma instância da classe ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) { // este método é chamado quando a atividade é criada pela primeira vez
        super.onCreate(savedInstanceState) // chama o método onCreate() da classe pai AppCompatActivity
        setContentView(amb.root) // define o conteúdo da atividade para a raiz do layout vinculado à propriedade amb
    }
}
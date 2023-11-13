// pacotes são usados para organizar e estruturar o código em módulos
package br.edu.scl.ifsp.sdm.contactlist.model   // declara o pacote (package) ao qual a classe pertence

// importam classes necessárias para a serialização do objeto
import android.os.Parcelable // interface do Android que permite que objetos sejam serializados e desserializados de forma eficiente
import kotlinx.parcelize.Parcelize // anotação do Kotlin usada para gerar automaticamente métodos necessários para a implementação da interface Parcelable

@Parcelize // indica que a classe pode ser serializada/desserializada usando a interface Parcelable
data class Contact(
    // atributos (propriedades) da classe Contact são declarados como variáveis mutáveis (var) e têm valores padrão definidos
    var id: Int? = -1,
    var name: String = "",
    var address: String = "",
    var phone: String = "",
    var email: String = ""
): Parcelable // implementa a interface Parcelable, indicando que ela pode ser colocada em um pacote para ser passada entre componentes do Android, como atividades e fragmentos

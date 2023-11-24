package br.edu.scl.ifsp.sdm.contactlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.edu.scl.ifsp.sdm.contactlist.R
import br.edu.scl.ifsp.sdm.contactlist.databinding.TileContactBinding
import br.edu.scl.ifsp.sdm.contactlist.model.Contact
import br.edu.scl.ifsp.sdm.contactlist.view.OnContactClickListener

class ContactRvAdapter(private val contactList: MutableList<Contact>, private val onContactClickListener: OnContactClickListener): RecyclerView.Adapter<ContactRvAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(tileContactBinding: TileContactBinding):RecyclerView.ViewHolder(tileContactBinding.root){
        val nameTv: TextView = tileContactBinding.nameTv
        val emailTv: TextView = tileContactBinding.emailTv
        val phoneTv: TextView = tileContactBinding.phoneTv

        // executado após o construtor primário
        init {
            // cria para cada célula um menu de contexto
            tileContactBinding.root.apply {
                setOnCreateContextMenuListener { menu, _, _ ->
                    (onContactClickListener as AppCompatActivity).menuInflater.inflate(R.menu.context_menu_main, menu)
                    menu.findItem(R.id.removeContactMi).setOnMenuItemClickListener {
                        onContactClickListener.onRemoveContactMenuItemClick(adapterPosition)
                        true
                    }
                }
            }
        }
    }

    // este método exige 3 métodos

    // método que retorna a quantidade de itens do data source
    override fun getItemCount() = contactList.size

    // método que pega um item do contactList e associa em uma célula reciclada ou então cria uma nova através de um holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TileContactBinding.inflate(LayoutInflater.from(parent.context), parent, false).run { // infla uma nova célula

        ContactViewHolder(this)

        // TileContactBinding.inflate(LayoutInflater.from(parent.context), parent, false).run { // infla uma nova célula
        //     ContactViewHolder(this)
        // }

    }

    // método que preenche ou substitui os valores da célula
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        contactList[position].also { contact ->
            with(holder) {
                nameTv.text = contact.name
                emailTv.text = contact.email
                phoneTv.text = contact.phone
                itemView.setOnClickListener {
                    onContactClickListener.onContactClick(position)
                }
            }
        }
    }
}
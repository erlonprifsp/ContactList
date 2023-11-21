package br.edu.scl.ifsp.sdm.contactlist.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.scl.ifsp.sdm.contactlist.R
import br.edu.scl.ifsp.sdm.contactlist.databinding.TileContactBinding
import br.edu.scl.ifsp.sdm.contactlist.model.Contact

class ContactAdapter(context: Context, private val contactList: MutableList<Contact>): ArrayAdapter<Contact>(context, R.layout.tile_contact, contactList) {

    // sobrescreve o método getView para renderizar  a exibição do conteúdo da célula
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View { // método getView infla uma célula e atualiza o seu conteúdo

        val contact = contactList[position]

        var contactTileView = convertView

        // se o contactTileView é nulo
        if (contactTileView == null) {
            val tcb = TileContactBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            contactTileView = tcb.root

            val tileContactHolder = TileContactHolder(tcb.nameTv, tcb.emailTv)
            contactTileView.tag = tileContactHolder
        }
        val holder = contactTileView.tag as TileContactHolder
        holder.nameTv.text = contact.name
        holder.emailTv.text = contact.email

        // contactTileView.findViewById<TextView>(R.id.nameTv).text = contact.name

        return contactTileView
    }
    private data class TileContactHolder(val nameTv: TextView, val emailTv: TextView)
}
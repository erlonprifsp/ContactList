package br.edu.scl.ifsp.sdm.contactlist.view

sealed interface OnContactClickListener {

    //interface clique curto
    fun onContactClick(position: Int)

    // interface clique longo - remover item
    fun onRemoveContactMenuItemClick(position: Int)

    // interface clique longo - editar item
    fun onEditContactMenuItemClick(position: Int)

}
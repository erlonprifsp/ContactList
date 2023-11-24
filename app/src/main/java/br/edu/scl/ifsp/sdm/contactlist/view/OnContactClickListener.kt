package br.edu.scl.ifsp.sdm.contactlist.view

sealed interface OnContactClickListener {

    //interface
    fun onContactClick(position: Int)
}
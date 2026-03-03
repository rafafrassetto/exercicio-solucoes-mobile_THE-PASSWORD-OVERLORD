package models

data class Requisito(
    val mensagemErro: String,
    val validacao: (String) -> Boolean
)
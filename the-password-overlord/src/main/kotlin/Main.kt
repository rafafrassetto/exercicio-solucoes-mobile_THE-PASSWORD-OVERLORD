import models.Requisito

fun main() {
    val listaDeRegras = listOf(
        Requisito("A senha deve ter no mínimo 5 caracteres.") { it.length >= 5 },
        
        Requisito("A senha deve conter pelo menos uma letra maiúscula e um número.") { senha ->
            senha.any { it.isUpperCase() } && senha.any { it.isDigit() }
        },
        
        Requisito("A senha deve conter a palavra 'satc' (não diferencia maiúsculas/minúsculas).") { senha ->
            senha.contains("satc", ignoreCase = true)
        },
        
        Requisito("A senha deve conter o ano do Hexa (2026).") { senha -> 
            senha.contains("2026") 
        },
        
        Requisito("A senha deve conter pelo menos um emoji (ex: 🏆, 🐯).") { senha ->
            senha.any { it.isSurrogate() } || Regex("[\\p{So}]").containsMatchIn(senha)
        },

        Requisito("A senha deve conter o dia atual em algarismos romanos (III).") { senha ->
            senha.contains("III")
        },
        
        Requisito("A soma de todos os números da senha deve ser exatamente igual a 20.") { senha ->
            val soma = senha.filter { it.isDigit() }.sumOf { it.digitToInt() }
            soma == 20
        },
        
        Requisito("A senha deve começar e terminar com o mesmo caractere.") { senha ->
            senha.isNotEmpty() && senha.first().lowercaseChar() == senha.last().lowercaseChar()
        }
    )

    var senhaAprovada = false

    do {
        println("\nDigite sua senha:")
        val entrada = readlnOrNull() ?: ""

        var erroEncontrado: String? = null

        for (regra in listaDeRegras) {
            if (!regra.validacao(entrada)) {
                erroEncontrado = regra.mensagemErro
                break
            }
        }

        if (erroEncontrado != null) {
            println("❌ ERRO: $erroEncontrado")
        } else {
            println("✅ SUCESSO! Senha aceita pelo Overlord.")
            senhaAprovada = true
        }
    } while (!senhaAprovada)
}
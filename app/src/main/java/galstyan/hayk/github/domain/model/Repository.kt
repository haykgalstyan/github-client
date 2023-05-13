package galstyan.hayk.github.domain.model

data class Repository(
    val name: String,
    val description: String,
    val stars: Int,
    val details: Details? = null
) {
    data class Details(
        val forks: Int,
        val primaryLanguage: String,
    )
}


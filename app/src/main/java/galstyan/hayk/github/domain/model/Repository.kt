package galstyan.hayk.github.domain.model

data class Repository(
    val id: Long,
    val name: String,
    val description: String?,
    val stars: Int,
    val forks: Int,
    val language: String?,
    val link: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Repository
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
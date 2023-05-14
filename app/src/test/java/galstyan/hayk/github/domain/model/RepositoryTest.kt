package galstyan.hayk.github.domain.model

import org.junit.Test


class RepositoryTest {

    @Test
    fun `Repository equals when id equals`() {
        val a1 = Repository(
            id = 1,
            name = "1",
            description = null,
            stars = 0, forks = 0,
            language = null,
            link = ""
        )
        val a2 = Repository(
            id = 1,
            name = "2",
            description = null,
            stars = 1, forks = 1,
            language = null,
            link = ""
        )
        val b1 = Repository(
            id = 2,
            name = "1",
            description = null,
            stars = 0, forks = 0,
            language = null,
            link = ""
        )
        assert(a1 == a2)
        assert(a1 != b1)
    }
}
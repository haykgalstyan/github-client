package galstyan.hayk.github.framework.data.github.repositories

import com.google.gson.annotations.SerializedName

class ResponseDto(
    @SerializedName("items")
    val repositories: List<RepositoryDto>
)
package hu.ait.agora.data

data class Product(
    val image: String,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val owner: User,
    val tags: List<String> = emptyList()
)

enum class Category{
    Fashion, Devices, Books, Household, Food, Others;
    companion object {
        fun getCategoryList(): List<String> {
            return values().map { it.name }
        }
    }
}
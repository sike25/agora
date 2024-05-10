package hu.ait.agora.data

data class Product(
    val image: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val owner: User = User(),
    val tags: List<String> = emptyList()
)

data class ProductWithId(
    val productId: String,
    val product: Product
)

enum class Category {
    Fashion, Devices, Books, Household, Food, Others;
    companion object {
        fun getCategoryList(): List<String> {
            return entries.map { it.name }
        }
    }
}
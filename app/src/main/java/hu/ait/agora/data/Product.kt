package hu.ait.agora.data

data class Product(
    val image: Int,
    val title: String,
    val description: String,
    //val category: Category,
    val price: Double,
    val owner: String,
)

data class Category(
    val name: String
)
package hu.ait.agora.data

data class User (
    val profilePicture: Int,
    val name: String,
    val email: String,
    val purchaseHistory: List<Product>,
    val listedItems: List<Product>
)
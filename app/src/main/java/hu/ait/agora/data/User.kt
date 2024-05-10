package hu.ait.agora.data

data class User (
    var profilePicture: String = "",
    val name: String,
    val email: String,
    var purchaseHistory: List<Product> = emptyList(),
    var listedItems: List<Product> = emptyList(),
    val firebaseUID: String
)
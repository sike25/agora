package hu.ait.agora.data

data class User (
    var profilePicture: String = "https://firebasestorage.googleapis.com/v0/b/agora-hills.appspot.com/o/gump.jpg?alt=media&token=70dd2413-5201-4922-8b49-672af2912683",
    val name: String = "",
    val email: String = "",
    var purchaseHistory: List<String> = emptyList(), // list of product ids
    var listedItems: List<String> = emptyList(),
    val firebaseUID: String = ""
)
package my.socialnetworkpage

class User(var name: String, var email: String,
           var password: String, var photo: Int,
           var idPage: String?, var education: String?,
           var city: String?, var friends: String,
           var followers: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (name != other.name) return false
        if (email != other.email) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }
}
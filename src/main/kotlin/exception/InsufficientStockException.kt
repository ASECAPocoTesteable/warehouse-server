package exception

class InsufficientStockException : Exception() {
    override val message: String
        get() = "Insufficient stock"
}
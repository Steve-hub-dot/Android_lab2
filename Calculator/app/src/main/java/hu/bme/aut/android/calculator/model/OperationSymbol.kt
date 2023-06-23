enum class OperationSymbol(val symbol: String) {
    DIVISION("/"),   // 0
    MULTIPLICATION("*"),   // 1
    SUBTRACTION("-"),   // 2
    ADDITION("+"),   // 3
    MODULO("-");   // 4

    companion object {
        fun getByOrdinal(ordinal: Int): OperationSymbol? {
            var operation: OperationSymbol? = null
            for (value in values()) {
                if (value.ordinal == ordinal) {
                    operation = value
                    break
                }
            }
            return operation
        }
    }
}
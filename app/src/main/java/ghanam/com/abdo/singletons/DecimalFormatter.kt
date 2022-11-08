package ghanam.com.abdo.singletons

object DecimalFormatter {
    fun format(num :Float?): String{
        return String.format("%.2f", num)
    }
}
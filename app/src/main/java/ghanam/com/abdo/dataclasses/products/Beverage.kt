package ghanam.com.abdo.dataclasses.products

data class Beverage(
    override var name: String="",
    override var description: String="",
    override var sku: String="",
    override var price: Float=0.0f,
    override var image: String=""
): Product()

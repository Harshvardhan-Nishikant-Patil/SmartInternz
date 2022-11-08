package ghanam.com.abdo.dataclasses.products

data class Condiment(
    override var name: String,
    override var description: String,
    override var sku: String,
    override var price: Float,
    override var image: String
): Product()

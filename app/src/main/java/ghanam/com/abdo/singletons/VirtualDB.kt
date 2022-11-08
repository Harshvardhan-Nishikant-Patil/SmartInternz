package ghanam.com.abdo.singletons

import android.os.Build
import ghanam.com.abdo.dataclasses.Item
import ghanam.com.abdo.dataclasses.PreviousOrder
import java.time.LocalDateTime

object VirtualDB {
    var items:MutableList<Item>
    var prevOrders:MutableList<PreviousOrder>
    var deliveryFee:Float=5.00f
    var currentFilter:String
    var priceFilter:String
    init
    {
        items= mutableListOf()
        prevOrders= mutableListOf()
        currentFilter="all"
        priceFilter="mintomax"
    }

    fun addItem(ite:Item):Boolean
    {
        if (checkExestence(ite.name)){
            return false
        }
        items.add(ite)
        return true

    }
    private fun checkExestence(name:String?):Boolean
    {
        var found=false
        for (ite in items){
            if (ite.name==name){
                found=true
            }
        }
        return found
    }
    fun getItemsCount():Int
    {
        return items.size
    }
    fun clearCart()
    {
        items= mutableListOf()
    }
    fun increaseItemCount(name: String?):Int
    {
        var quantity=0
        for (ite in items){
            if (ite.name==name){
                ite.quantity++
                quantity=ite.quantity
                ite.totalPrice = quantity * ite.price
                return quantity
            }
        }
        return quantity
    }
    fun decreaseItemCount(name: String?):Int
    {
        var quantity=0
        for (ite in items){
            if (ite.name==name){
                if (ite.quantity>1) {
                    ite.quantity--
                    quantity = ite.quantity
                    ite.totalPrice = quantity * ite.price
                    return quantity
                }
                if(ite.quantity<=1) {
                    return -1
                }
            }
        }
        return quantity
    }
    fun calculateTotal():Float
    {
        var total=0.0f
        for (ite in items){
            total += ite.totalPrice
        }
        return total
    }

    fun addPrevOrder()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prevOrders.add(PreviousOrder(LocalDateTime.now(),calculateTotal()+ deliveryFee))
        }
    }
}
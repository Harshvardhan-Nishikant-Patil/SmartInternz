package ghanam.com.abdo

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ghanam.com.abdo.adapters.HomeItemAdapter
import ghanam.com.abdo.databinding.FragmentHomeBinding
import ghanam.com.abdo.dataclasses.products.*
import ghanam.com.abdo.singletons.VirtualDB


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private lateinit var itemsAdapter: HomeItemAdapter
    private lateinit var allItems:MutableList<Product>
    private lateinit var filteredItems:MutableList<Product>
    private var pressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        allItems=readProductsFromJson(context)
        filteredItems=filterItems(VirtualDB.currentFilter)
        if (VirtualDB.priceFilter=="mintomax")
            sortItems(0)
        else
            sortItems(1)
        itemsAdapter = HomeItemAdapter(filteredItems,binding)
        binding.apply {
            itemsRecycler.adapter=itemsAdapter
            itemsRecycler.layoutManager = LinearLayoutManager(context)
            itemsCountText.text=VirtualDB.getItemsCount().toString()
            filterText.text=VirtualDB.currentFilter
            priceSwitch.isChecked = VirtualDB.priceFilter != "mintomax"
            priceSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                   VirtualDB.priceFilter="maxtomin"
                    view?.let { Navigation.findNavController(it).navigate(R.id.action_homeFragment_self) }
                }else{
                    VirtualDB.priceFilter="mintomax"
                    view?.let { Navigation.findNavController(it).navigate(R.id.action_homeFragment_self) }
                }
            }
            signOutButton.setOnClickListener {
                VirtualDB.clearCart()
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_loginFragment)
            }
            goToOrders.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_previousOrdersFragment)
            }
//
            cartButton.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_cartFragment)
            }
            filterAllButton.setOnClickListener{
                VirtualDB.currentFilter="all"
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_self)
            }
            filterBeverageButton.setOnClickListener{
                VirtualDB.currentFilter="bev"
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_self)
            }
            filterCannedButton.setOnClickListener{
                VirtualDB.currentFilter="can"
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_self)
            }
            filterCondimentButton.setOnClickListener{
                VirtualDB.currentFilter="con"
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_self)
            }
            filterGroceryButton.setOnClickListener{
                VirtualDB.currentFilter="gro"
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_self)
            }
            filterSnackButton.setOnClickListener{
                VirtualDB.currentFilter="sna"
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_self)
            }

        }

        return binding.root
    }


    private fun filterItems(currentFilter: String): MutableList<Product> {
        if (currentFilter=="all"){
            return allItems
        }
        val filteredList:MutableList<Product> = mutableListOf()
        when(currentFilter){
            "bev"->{
                for (ite in allItems){
                    if(ite::class.java.simpleName=="Beverage"){
                        filteredList.add(ite)
                    }
                }
            }
            "can"->{
                for (ite in allItems){
                    if(ite::class.java.simpleName=="Canned"){
                        filteredList.add(ite)
                    }
                }
            }
            "con"->{
                for (ite in allItems){
                    if(ite::class.java.simpleName=="Condiment"){
                        filteredList.add(ite)
                    }
                }
            }
            "gro"->{
                for (ite in allItems){
                    if(ite::class.java.simpleName=="Grocery"){
                        filteredList.add(ite)
                    }
                }
            }
            "sna"->{
                for (ite in allItems){
                    if(ite::class.java.simpleName=="Snack"){
                        filteredList.add(ite)
                    }
                }
            }
        }
        return filteredList
    }


    private fun readProductsFromJson(context: Context?): MutableList<Product> {

        val mapper = jacksonObjectMapper()
        var objectArrayString=""
        if (context != null) {
            objectArrayString =context.resources.openRawResource(R.raw.beverages).bufferedReader().use { it.readText() }
        }
        val bevarage = mapper.readValue<MutableList<Beverage>>(objectArrayString)

        if (context != null) {
            objectArrayString= context.resources.openRawResource(R.raw.canned).bufferedReader().use { it.readText() }
        }
        val canned = mapper.readValue<MutableList<Canned>>(objectArrayString)

        if (context != null) {
            objectArrayString= context.resources.openRawResource(R.raw.condiments).bufferedReader().use { it.readText() }
        }
        val condiment = mapper.readValue<MutableList<Condiment>>(objectArrayString)


        if (context != null) {
            objectArrayString= context.resources.openRawResource(R.raw.grocery).bufferedReader().use { it.readText() }
        }
        val grocery = mapper.readValue<MutableList<Grocery>>(objectArrayString)


        if (context != null) {
            objectArrayString= context.resources.openRawResource(R.raw.snacks).bufferedReader().use { it.readText() }
        }
        val snack = mapper.readValue<MutableList<Snack>>(objectArrayString)



        val mainList = mutableListOf<Product>()
        mainList.addAll(bevarage)
        mainList.addAll(canned)
        mainList.addAll(condiment)
        mainList.addAll(grocery)
        mainList.addAll(snack)
        return mainList

    }

    private fun sortItems(id:Int){
        //0 min to max
        val n=filteredItems.size
        var temp:Product
        if (id==0) {
            for (i in 0 until  n - 1) {
                var indexOfMin = i
                for (j in n - 1 downTo i) {
                    if (filteredItems[j].price < filteredItems[indexOfMin].price)
                        indexOfMin = j
                }
                if (i != indexOfMin) {
                    temp = filteredItems[i]
                    filteredItems[i] = filteredItems[indexOfMin]
                    filteredItems[indexOfMin] = temp
                }
            }
        }else{
            for (i in 0 until  n - 1) {
                var indexOfMin = i
                for (j in n - 1 downTo i) {
                    if (filteredItems[j].price > filteredItems[indexOfMin].price)
                        indexOfMin = j
                }
                if (i != indexOfMin) {
                    temp = filteredItems[i]
                    filteredItems[i] = filteredItems[indexOfMin]
                    filteredItems[indexOfMin] = temp
                }
            }
        }
    }

}
package ghanam.com.abdo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import ghanam.com.abdo.adapters.PreviousOrdersAdapter
import ghanam.com.abdo.databinding.FragmentPreviousOrdersBinding
import ghanam.com.abdo.singletons.VirtualDB

class PreviousOrdersFragment : Fragment() {

    lateinit var binding: FragmentPreviousOrdersBinding
    private lateinit var itemsAdapter: PreviousOrdersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        itemsAdapter = PreviousOrdersAdapter(VirtualDB.prevOrders)
        binding= FragmentPreviousOrdersBinding.inflate(inflater,container,false)
        binding.previousRecycler.adapter=itemsAdapter
        binding.previousRecycler.layoutManager = LinearLayoutManager(context)
        binding.backButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_previousOrdersFragment_to_homeFragment)
        }
        return binding.root
    }

}
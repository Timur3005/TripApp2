package com.example.tripapp2.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tripapp2.databinding.FragmentHomeBinding
import com.example.tripapp2.domain.entities.Category
import com.example.tripapp2.domain.entities.Cities
import com.example.tripapp2.domain.entities.Filters
import com.example.tripapp2.domain.entities.ShortPlaceItem
import com.example.tripapp2.domain.entities.ShortPlaceItemState
import com.example.tripapp2.presentation.TripApp
import com.example.tripapp2.presentation.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var placesAdapter: PlacesAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    private val component by lazy {
        (activity?.application as TripApp).component
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("binding doesn't exist")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        viewModel.getPlaceWithFilters(
            Filters(Cities.DEFAULT, listOf(Category.DEFAULT))
        )
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.placesFlow.collect {
                    when (it) {
                        ShortPlaceItemState.Error -> Log.d("MyTaggg", "Error")
                        ShortPlaceItemState.Loading -> Log.d("MyTaggg", "Loading")
                        is ShortPlaceItemState.ShortPlaceItemList -> {
                            placesAdapter.submitList(it.list)
                        }
                    }
                }
            }
        }
    }

    private fun setRecycler(){
        with(binding){
            rvListOfPlaces.layoutManager = LinearLayoutManager(context)
            rvListOfPlaces.adapter = placesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
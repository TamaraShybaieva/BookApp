package com.books.app.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.books.app.R
import com.books.app.databinding.FragmentMainBinding
import com.books.app.presentation.ui.adapter.GenreAdapter
import com.books.app.presentation.ui.adapter.ViewPagerAdapter
import com.books.app.presentation.ui.fragment.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.kenilt.loopingviewpager.scroller.AutoScroller
import com.kenilt.loopingviewpager.widget.LoopingViewPager
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var bannerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupRecyclerViews(view)
    }

    private fun setupRecyclerViews(view: View) {
        genreAdapter = GenreAdapter { bookId ->
            val bundle = Bundle().apply {
                putInt("bookId", bookId.id)
            }
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }

        bannerAdapter = ViewPagerAdapter { bannerSlide ->
            val bundle = Bundle().apply {
                putString("bookId", bannerSlide.id.toString())
            }
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)

        }

        view.findViewById<RecyclerView>(R.id.recyclerViewGenres).apply {
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeViewModel() {
        viewModel.genreSlides.observe(viewLifecycleOwner) {
            view?.findViewById<LoopingViewPager>(R.id.recyclerViewBanners)?.apply {
                adapter = bannerAdapter
                val autoScroller = AutoScroller(this, lifecycle, 3000)
                autoScroller.isAutoScroll = true
                val tabLayout = view?.findViewById<TabLayout>(R.id.tab_layout)
                tabLayout?.setupWithViewPager(this, true);
            }
            genreAdapter.submitList(it)
            viewModel.bannerSlides.observe(viewLifecycleOwner) {
                bannerAdapter.submitList(it)
            }

        }
    }
}
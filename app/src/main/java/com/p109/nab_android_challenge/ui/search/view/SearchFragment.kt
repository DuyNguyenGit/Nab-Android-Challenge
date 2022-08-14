package com.p109.nab_android_challenge.ui.search.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.p109.nab_android_challenge.R
import com.p109.nab_android_challenge.databinding.FragmentSearchBinding
import com.p109.nab_android_challenge.ui.search.view.listadapter.DailyWeatherListAdapter
import com.p109.nab_android_challenge.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val searchViewModel by viewModels<SearchViewModel>()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var dailyWeatherListAdapter: DailyWeatherListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)
        with(binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = searchViewModel
        }.rcvDailyList) {
            dailyWeatherListAdapter = DailyWeatherListAdapter()
            adapter = dailyWeatherListAdapter
            val dividerItemDecoration = DividerItemDecoration(
                context, (layoutManager as LinearLayoutManager).orientation
            )
            addItemDecoration(dividerItemDecoration)
        }

        searchViewModel.result.observe(viewLifecycleOwner) {
            dailyWeatherListAdapter.submitList(it.data)
        }
    }

}
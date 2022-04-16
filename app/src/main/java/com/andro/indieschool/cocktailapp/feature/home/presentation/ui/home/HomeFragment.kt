package com.andro.indieschool.cocktailapp.feature.home.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.andro.indieschool.cocktailapp.R
import com.andro.indieschool.cocktailapp.databinding.FragmentHomeBinding
import com.andro.indieschool.cocktailapp.feature.home.di.HomeModule
import com.andro.indieschool.cocktailapp.feature.home.presentation.ui.alcohol.AlcoholFragment
import com.andro.indieschool.cocktailapp.feature.home.presentation.ui.nonalcohol.NonAlcoholFragment
import com.andro.indieschool.cocktailapp.feature.home.presentation.ui.optional.OptionalAlcoholFragment
import com.andro.indieschool.cocktailapp.util.common.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.core.context.loadKoinModules

private val loadFeature by lazy { loadKoinModules(HomeModule.module) }
private fun injectFeature() = loadFeature

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onAttach(context: Context) {
        injectFeature()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragmentContent()
    }

    private fun setupFragmentContent() {
        val fragmentContents = listOf<Fragment>(
            NonAlcoholFragment.newInstance(),
            OptionalAlcoholFragment.newInstance(),
            AlcoholFragment.newInstance()
        )
        val fragmentLabels = listOf<String>(
            "Non-Alc",
            "Optional-Alc",
            "Alc"
        )
        val tabAdapter = HomePagerAdapter(
            fragments = fragmentContents,
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )
        with(binding.homeViewpager) {
            adapter = tabAdapter
            isUserInputEnabled = false
        }
        TabLayoutMediator(
            binding.homeTabLayout,
            binding.homeViewpager
        ) { tab, position ->
            tab.text = fragmentLabels[position]
        }.attach()
    }

}
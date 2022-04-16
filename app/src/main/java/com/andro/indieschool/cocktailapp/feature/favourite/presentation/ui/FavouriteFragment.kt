package com.andro.indieschool.cocktailapp.feature.favourite.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import coil.load
import coil.transform.RoundedCornersTransformation
import com.andro.indieschool.cocktailapp.R
import com.andro.indieschool.cocktailapp.databinding.FragmentFavouriteBinding
import com.andro.indieschool.cocktailapp.feature.favourite.common.FavouriteFlipperState
import com.andro.indieschool.cocktailapp.feature.favourite.di.FavouriteModule
import com.andro.indieschool.cocktailapp.feature.favourite.domain.model.DrinkFavouriteModel
import com.andro.indieschool.cocktailapp.util.common.GenericRecyclerAdapter
import com.andro.indieschool.cocktailapp.util.common.VerticalItemDecoration
import com.andro.indieschool.cocktailapp.util.common.viewBinding
import com.andro.indieschool.cocktailapp.util.extension.find
import com.andro.indieschool.cocktailapp.util.extension.showAlert
import com.andro.indieschool.cocktailapp.util.network.Resource
import com.andro.indieschool.cocktailapp.util.network.invoke
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

private val loadFeature by lazy { loadKoinModules(FavouriteModule.module) }
private fun injectFeature() = loadFeature

class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    private val binding by viewBinding(FragmentFavouriteBinding::bind)
    private val viewModel by viewModel<FavouriteViewModel>()

    private val itemAdapter by lazy(LazyThreadSafetyMode.NONE) {
        GenericRecyclerAdapter<DrinkFavouriteModel>(
            holderResId = R.layout.item_favourite_cocktail,
            onBind = { model, view ->
                setupDrinkItem(model, view)
                setupDeleteListener(model, view)
            },
            listener = { model, _, view ->
                itemListener(model, view)
            }
        )
    }

    override fun onAttach(context: Context) {
        injectFeature()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.favouriteState.collect { state ->
                when (state) {
                    is Resource.Uninitialized,
                    is Resource.Loading -> {
                        setFlipperState(FavouriteFlipperState.Loading)
                    }
                    is Resource.Success -> {
                        val data = state.invoke()
                        setFlipperState(
                            if (data.isNullOrEmpty()) {
                                FavouriteFlipperState.Empty
                            } else {
                                setDataToList(data)
                                FavouriteFlipperState.Success
                            }
                        )
                    }
                    is Resource.Error<*, *> -> {
                        val data = state.invoke()
                        setFlipperState(
                            if (data != null) FavouriteFlipperState.Success else FavouriteFlipperState.Error
                        )
                    }
                }
            }
        }
        viewModel.getAllFavourite()
        setupScreenContent()
    }

    private fun setDataToList(data: List<DrinkFavouriteModel>) {
        itemAdapter.setData(data)
    }

    private fun setFlipperState(flipperState: FavouriteFlipperState) {
        binding.flipperFavouriteDrink.displayedChild = flipperState.state
    }

    private fun setupDrinkItem(drink: DrinkFavouriteModel, itemView: View) {
        with(itemView) {
            val imageView = findViewById<ImageView>(R.id.item_iv_image_drink)
            imageView.transitionName = "drink_image_${drink.idDrink}"

            imageView.load(drink.strDrinkThumb) {
                placeholder(R.drawable.img_placeholder)
                error(R.drawable.img_placeholder)
                allowHardware(false)
                transformations(RoundedCornersTransformation())
            }
            find<TextView>(R.id.item_tv_name_drink).text = drink.strDrink
            find<TextView>(R.id.item_tv_drink_id).text = getString(R.string.label_id, drink.idDrink)
        }
    }

    private fun itemListener(drink: DrinkFavouriteModel, itemView: View) {
        val imageViewTarget = itemView.findViewById<ImageView>(R.id.item_iv_image_drink)
        val extras = FragmentNavigatorExtras(imageViewTarget to "drink_image_${drink.idDrink}")

        val toDetails = FavouriteFragmentDirections.actionFavouriteFragmentToDrinkDetailsFragment(
            drink.idDrink,
            drink.isFavourite,
            drink.strDrink
        )
        with(findNavController()) {
            currentDestination?.getAction(toDetails.actionId)?.let {
                navigate(toDetails, extras)
            }
        }
    }

    private fun setupDeleteListener(drink: DrinkFavouriteModel, itemView: View) {
        val deleteBtn = itemView.find<FrameLayout>(R.id.item_frame_delete)
        deleteBtn.setOnClickListener {
            context.showAlert(R.string.text_delete_confirmation) {
                setPositiveButton(android.R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                setNegativeButton(android.R.string.ok) { dialog, _ ->
                    viewModel.deleteFavourite(drink.idDrink)
                    dialog.dismiss()
                }
            }
        }
    }

    private fun setupScreenContent() {
        with(binding.favouriteRvListDrink) {
            layoutManager = LinearLayoutManager(context)
            adapter = itemAdapter
            addItemDecoration(VerticalItemDecoration(20))
        }
    }
}
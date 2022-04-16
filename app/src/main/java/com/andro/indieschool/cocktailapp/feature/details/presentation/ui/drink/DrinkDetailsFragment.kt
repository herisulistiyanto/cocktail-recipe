package com.andro.indieschool.cocktailapp.feature.details.presentation.ui.drink

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import androidx.transition.TransitionInflater
import coil.load
import com.andro.indieschool.cocktailapp.R
import com.andro.indieschool.cocktailapp.databinding.FragmentDrinkDetailsBinding
import com.andro.indieschool.cocktailapp.feature.details.common.DetailsFlipperState
import com.andro.indieschool.cocktailapp.feature.details.di.DetailsModule
import com.andro.indieschool.cocktailapp.feature.details.domain.model.DrinkDetailModel
import com.andro.indieschool.cocktailapp.util.PaletteUtils
import com.andro.indieschool.cocktailapp.util.common.viewBinding
import com.andro.indieschool.cocktailapp.util.extension.showAlert
import com.andro.indieschool.cocktailapp.util.extension.showSnackBar
import com.andro.indieschool.cocktailapp.util.network.Resource
import com.andro.indieschool.cocktailapp.util.network.invoke
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import kotlin.properties.Delegates

private val loadFeature by lazy { loadKoinModules(DetailsModule.module) }
private fun injectFeature() = loadFeature

class DrinkDetailsFragment : Fragment(R.layout.fragment_drink_details) {

    private val binding by viewBinding(FragmentDrinkDetailsBinding::bind)
    private val viewModel by viewModel<DrinkDetailsViewModel>()
    private var isFavourite by Delegates.observable(false) { _, old, new ->
        setupToggleFavourite(new)
    }

    private val args: DrinkDetailsFragmentArgs by navArgs<DrinkDetailsFragmentArgs>()

    override fun onAttach(context: Context) {
        injectFeature()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToggleFavourite(args.id, args.isFavourite)
        viewLifecycleOwner.lifecycleScope.run {
            observeDetailState()
            observeUpdateFavouriteState()
        }
        viewModel.getDrinkDetail(args.id)
        setupToolbar(args.name)
    }

    private fun setupToolbar(drinkName: String) {
        with(binding.detailsToolbar) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        binding.detailsCollapsingToolbarLayout.title = drinkName
    }

    private fun LifecycleCoroutineScope.observeDetailState() = with(this) {
        launchWhenStarted {
            viewModel.detailState.collect { state ->
                when (state) {
                    is Resource.Uninitialized,
                    is Resource.Loading -> {
                        setFlipperState(DetailsFlipperState.Loading)
                    }
                    is Resource.Success -> {
                        populateDetailInfo(state.invoke())
                        setFlipperState(DetailsFlipperState.Success)
                    }
                    is Resource.Error<*, *> -> {
                        val data = state.invoke()
                        Log.d(
                            "HERI 🧐",
                            "observeDetailState() Error with: state = $state, data: $data"
                        )
                        setFlipperState(
                            if (data != null) DetailsFlipperState.Success else DetailsFlipperState.Error
                        )
                    }
                }
            }
        }
    }

    private fun LifecycleCoroutineScope.observeUpdateFavouriteState() = with(this) {
        launchWhenStarted {
            viewModel.updateFavState.collect { state ->
                when (state) {
                    is Resource.Uninitialized -> {}
                    is Resource.Loading -> {
                        Log.d("HERI 🧐", "observeUpdateFavouriteState: Loading ")
                    }
                    is Resource.Success -> {
                        Log.d("HERI 🧐", "observeUpdateFavouriteState: Success ")
                        isFavourite = !isFavourite
                        if (isFavourite) {
                            binding.root.showSnackBar(
                                messageRes = R.string.text_item_added
                            ) {
                                setAction(android.R.string.ok) {
                                    this.dismiss()
                                }
                            }
                        }
                    }
                    is Resource.Error<*, *> -> {
                        Log.d(
                            "HERI 🧐",
                            "observeUpdateFavouriteState: Error -> ${state.error?.toString()}"
                        )
                    }
                }
            }
        }
    }

    private fun populateDetailInfo(model: DrinkDetailModel) {
        with(binding) {
            detailsIvDrink.load(model.strDrinkThumb) {
                placeholder(R.drawable.img_placeholder)
                error(R.drawable.img_placeholder)
                allowHardware(false)
                listener(
                    onSuccess = { _, result ->
                        Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                            detailsAppbar.background = PaletteUtils.getDominantGradient(
                                result.drawable.toBitmap(),
                                result.drawable.intrinsicHeight,
                                result.drawable.intrinsicWidth
                            )?.toDrawable(resources)
                        }
                    }
                )
            }
            layoutContent.detailsTvDrinkName.text = model.strDrink
            layoutContent.detailsTvDrinkId.text = getString(R.string.label_id, model.idDrink)
            layoutContent.detailsTvContentAlcoholType.text = model.strAlcoholic
            layoutContent.detailsTvContentCategory.text = model.strCategory
            layoutContent.detailsTvContentGlassType.text = model.strGlass
            layoutContent.detailsTvContentServing.text = model.strInstructions
            layoutContent.detailsTvContentIngredient.text = model.strIngredient
        }
    }

    private fun setFlipperState(detailsState: DetailsFlipperState) {
        binding.detailsFlipper.displayedChild = detailsState.state
    }

    private fun setupToggleFavourite(isFavourite: Boolean) {
        val iconResId = if (isFavourite) {
            R.drawable.ic_love_fill
        } else {
            R.drawable.ic_love_border
        }
        binding.layoutContent.detailsIvLike.setImageResource(iconResId)
    }

    private fun handleToggleFavourite(idDrink: String, isFavourite: Boolean) {
        this.isFavourite = isFavourite
        with(binding.layoutContent.detailsIvLike) {
            setOnClickListener {
                val newValue = !this@DrinkDetailsFragment.isFavourite
                if (!newValue) {
                    context.showAlert(R.string.text_remove_fav_confirmation) {
                        setPositiveButton(android.R.string.cancel) { dialog, _ ->
                            dialog.dismiss()
                        }
                        setNegativeButton(android.R.string.ok) { dialog, _ ->
                            viewModel.updateDrinkFavouriteStatus(idDrink, newValue)
                            dialog.dismiss()
                        }
                    }
                } else {
                    viewModel.updateDrinkFavouriteStatus(idDrink, newValue)
                }
            }
        }
    }

}
package com.andro.indieschool.cocktailapp.feature.home.presentation.ui.optional

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.andro.indieschool.cocktailapp.R
import com.andro.indieschool.cocktailapp.databinding.FragmentOptionalAlcoholBinding
import com.andro.indieschool.cocktailapp.feature.home.common.HomeFlipperState
import com.andro.indieschool.cocktailapp.feature.home.domain.model.DrinkModel
import com.andro.indieschool.cocktailapp.feature.home.presentation.ui.home.HomeFragmentDirections
import com.andro.indieschool.cocktailapp.util.common.GenericRecyclerAdapter
import com.andro.indieschool.cocktailapp.util.common.VerticalItemDecoration
import com.andro.indieschool.cocktailapp.util.common.viewBinding
import com.andro.indieschool.cocktailapp.util.extension.find
import com.andro.indieschool.cocktailapp.util.network.Resource
import com.andro.indieschool.cocktailapp.util.network.invoke
import org.koin.androidx.viewmodel.ext.android.viewModel

class OptionalAlcoholFragment : Fragment(R.layout.fragment_optional_alcohol) {

    private val binding by viewBinding(FragmentOptionalAlcoholBinding::bind)
    private val viewModel by viewModel<OptionalAlcoholViewModel>()

    private val itemAdapter by lazy(LazyThreadSafetyMode.NONE) {
        GenericRecyclerAdapter<DrinkModel>(
            holderResId = R.layout.item_cocktail,
            onBind = this::setupDrinkItem,
            listener = { model, _, view -> itemListener(model, view) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.optionalAlcoholState.collect { state ->
                when (state) {
                    is Resource.Uninitialized -> {
                        setFlipperState(HomeFlipperState.Loading)
                    }
                    is Resource.Loading -> {
                        setFlipperState(HomeFlipperState.Loading)
                    }
                    is Resource.Success -> {
                        setDataToList(state.invoke())
                        setFlipperState(HomeFlipperState.Success)
                    }
                    is Resource.Error<*, *> -> {
                        val data = state.invoke()
                        setFlipperState(
                            if (data.isNullOrEmpty()
                                    .not()
                            ) HomeFlipperState.Success else HomeFlipperState.Error
                        )
                    }
                }
            }
        }
        viewModel.getOptionalAlcoholDrinks()
        setupScreenContent()
    }

    private fun setupDrinkItem(drink: DrinkModel, itemView: View) {
        with(itemView) {
            val imageView = find<ImageView>(R.id.item_iv_image_drink)

            imageView.load(drink.strDrinkThumb) {
                placeholder(R.drawable.img_placeholder)
                error(R.drawable.img_placeholder)
                allowHardware(false)
                transformations(RoundedCornersTransformation())
            }
            find<TextView>(R.id.item_tv_name_drink).text = drink.strDrink
            find<ImageView>(R.id.item_iv_favourite).run {
                setImageResource(
                    if (drink.isFavourite) R.drawable.ic_love_fill else R.drawable.ic_love_border
                )
            }
            find<TextView>(R.id.item_tv_drink_id).text = getString(R.string.label_id, drink.idDrink)
        }
    }

    private fun itemListener(drink: DrinkModel, itemView: View) {
        val toDetails = HomeFragmentDirections.toDrinkDetailsFragment(
            drink.idDrink.orEmpty(),
            drink.isFavourite,
            drink.strDrink.orEmpty()
        )
        with(findNavController()) {
            currentDestination?.getAction(toDetails.actionId)?.let {
                navigate(toDetails)
            }
        }
    }

    private fun setupScreenContent() {
        with(binding.homeRvNonAlcohol) {
            layoutManager = LinearLayoutManager(context)
            adapter = itemAdapter
            addItemDecoration(VerticalItemDecoration(20))
        }
    }

    private fun setDataToList(data: List<DrinkModel>) {
        itemAdapter.setData(data)
    }

    private fun setFlipperState(flipperState: HomeFlipperState) {
        binding.flipperOptionalAlcoholDrink.displayedChild = flipperState.state
    }

    companion object {
        fun newInstance(): OptionalAlcoholFragment {
            return OptionalAlcoholFragment()
        }
    }

}
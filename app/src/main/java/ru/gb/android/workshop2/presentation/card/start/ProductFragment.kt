package ru.gb.android.workshop2.presentation.card.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import kotlinx.coroutines.launch
import ru.gb.android.workshop2.marketsample.R
import ru.gb.android.workshop2.marketsample.databinding.FragmentProductBinding
import ru.gb.android.workshop2.presentation.card.start.states.ProductState

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProductsViewModel>{
        FeatureServiceLocator.provideProductViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadProduct()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{ state ->
                    when{
                        state.hasError -> {showError(state.errorProvider(requireContext()))
                        viewModel.clearError()
                        }
                        state.isLoading -> renderLoading()
                        else -> renderProductState(state.productState)
                    }

                }
            }
        }
    }

    private fun renderProductState(productState: ProductState) {
        binding.image.load(productState.image)
        binding.image.visibility = View.VISIBLE

        binding.name.text = productState.name
        binding.name.visibility = View.VISIBLE

        binding.price.text = getString(R.string.price_with_arg, productState.price)
        binding.price.visibility = View.VISIBLE

        if (productState.hasDiscount) {
            binding.promo.visibility = View.VISIBLE
            binding.promo.text = productState.discount
        }else{
            binding.promo.visibility = View.GONE
        }
        binding.addToCart.visibility = View.VISIBLE
        binding.progress.visibility = View.GONE
    }

    fun showError(errorText: String) {
        Toast.makeText(
            requireContext(),
            errorText,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun hideAll(){
        binding.image.visibility = View.GONE
        binding.name.visibility = View.GONE
        binding.price.visibility = View.GONE
        binding.promo.visibility = View.GONE
        binding.addToCart.visibility = View.GONE
    }
    fun renderLoading(){
        hideAll()
        binding.progress.visibility = View.VISIBLE

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

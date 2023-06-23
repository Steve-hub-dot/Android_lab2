package hu.bme.aut.android.calculator

import CalculatorOperator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.calculator.adapter.HistoryAdapter
import hu.bme.aut.android.calculator.databinding.FragmentHistoryBinding

class HistoryFragment: Fragment(), HistoryAdapter.ClickListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init RecyclerView
        adapter = HistoryAdapter(this@HistoryFragment, CalculatorOperator.history, requireContext())
        binding.historyRecyclerView.adapter = adapter

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.delete -> {
                    val size = CalculatorOperator.history.size
                    adapter.notifyItemRangeRemoved(0, size)
                    true
                }
                else -> false
                }
            }
        }
    //H2PGRB
    override fun onClick(loadedData: String) {
        val action = HistoryFragmentDirections.actionHistoryFragmentToCalculatorFragment()
        CalculatorOperator.loadState(loadedData)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
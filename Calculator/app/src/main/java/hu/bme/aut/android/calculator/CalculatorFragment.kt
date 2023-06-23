//Adjuk meg a packaget, különben nem fogja megtalálni (fatal exception is real)
package hu.bme.aut.android.calculator
import CalculatorOperator
import Util.numberRegex
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import hu.bme.aut.android.calculator.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {
    private lateinit var numberButtons: Set<Button>
    private lateinit var operationButtons: Set<Button>
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private val calcState get() = CalculatorOperator.state


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (CalculatorOperator.state.number1.toString() != "NaN")
//            binding.consoleTextView.text = CalculatorOperator.state.number1.toString()
        setResult(CalculatorOperator.state.number1)
        initButtons()
        with(binding.consoleTextView) {
            isClickable = true
            setOnClickListener {
                val action = CalculatorFragmentDirections.actionCalculatorFragmentToHistoryFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun setResult(value: Double) {
        if (value.isNaN()) {
            binding.consoleTextView.text = ""
        } else if (value % 1.0 == 0.0) {
            binding.consoleTextView.text = value.toInt().toString()
        } else {
            binding.consoleTextView.text = String.format("%.2f",value)
        }
    }

    private fun initButtons() {
        // Init number and operation button sets
        with(binding) {
            numberButtons = setOf(
                number0Button, number1Button, number2Button,
                number3Button, number4Button, number5Button,
                number6Button, number7Button, number8Button,
                number9Button
            )

            operationButtons = setOf(
                operationDivisionButton,
                operationMultiplicationButton,
                operationSubtractionButton,
                operationAdditionButton,
                moduloButton
            )
        }

        // Init click listeners for number buttons
        //H2PGRB
        numberButtons.forEachIndexed { number, button ->
            button.setOnClickListener {
                CalculatorOperator.onNumberPressed(number)
                binding.consoleTextView.text = numberRegex.find(calcState.input)?.value ?: ""
            }
        }

        // Init click listeners for number buttons
        operationButtons.forEachIndexed { operation, button ->
            button.setOnClickListener {
                CalculatorOperator.onOperationPressed(operation)
                setResult(calcState.result)
            }
        }

        // Init click listener for sign button
        binding.signButton.setOnClickListener {
            setResult(CalculatorOperator.onSignChange())
        }

        // Init click listener for delete button
        binding.deleteButton.setOnClickListener {
            CalculatorOperator.onDelete()
            setResult(calcState.result)
        }

        // Init click listener for comma button
        binding.commaButton.setOnClickListener {
            CalculatorOperator.addComa()
            binding.consoleTextView.text = calcState.input
        }

        // Init click listener for equivalence button
        binding.operationEquivalenceButton.setOnClickListener {
            setResult(CalculatorOperator.onEquivalence())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
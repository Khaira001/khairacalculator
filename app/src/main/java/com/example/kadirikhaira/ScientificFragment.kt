package com.example.kadirikhaira

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.mariuszgromada.math.mxparser.Expression
import kotlin.math.*

class ScientificFragment : Fragment() {
    private var expression = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_scientific, container, false)
        val display = activity?.findViewById<TextView>(R.id.tvDisplay)
        val exprView = activity?.findViewById<TextView>(R.id.tvExpression)

        // Number buttons
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        buttons.forEachIndexed { i, id ->
            view.findViewById<Button>(id).setOnClickListener {
                expression += i.toString()
                display?.text = expression
            }
        }

        val funcMap = mapOf(
            R.id.btnSin    to { x: Double -> sin(Math.toRadians(x)) },
            R.id.btnCos    to { x: Double -> cos(Math.toRadians(x)) },
            R.id.btnTan    to { x: Double -> tan(Math.toRadians(x)) },
            R.id.btnLog    to { x: Double -> log10(x) },
            R.id.btnLn     to { x: Double -> ln(x) },
            R.id.btnSqrt   to { x: Double -> sqrt(x) },
            R.id.btnSquare to { x: Double -> x * x },
            R.id.btnInv    to { x: Double -> 1.0 / x },
            R.id.btnAbs    to { x: Double -> abs(x) }
        )

        funcMap.forEach { (id, fn) ->
            view.findViewById<Button>(id).setOnClickListener {
                try {
                    val result = fn(expression.toDouble())
                    exprView?.text = expression
                    expression = result.toString()
                    display?.text = expression
                } catch (e: Exception) { display?.text = "Error" }
            }
        }

        view.findViewById<Button>(R.id.btnPi).setOnClickListener {
            expression += Math.PI.toString()
            display?.text = expression
        }

        view.findViewById<Button>(R.id.btnE).setOnClickListener {
            expression += Math.E.toString()
            display?.text = expression
        }

        view.findViewById<Button>(R.id.btnFact).setOnClickListener {
            try {
                val n = expression.toInt()
                val result = (1..n).fold(1L) { acc, i -> acc * i }
                exprView?.text = "$n!"
                expression = result.toString()
                display?.text = expression
            } catch (e: Exception) { display?.text = "Error" }
        }

        view.findViewById<Button>(R.id.btnPow).setOnClickListener {
            expression += "^"
            display?.text = expression
        }

        view.findViewById<Button>(R.id.btnLParen).setOnClickListener { expression += "("; display?.text = expression }
        view.findViewById<Button>(R.id.btnRParen).setOnClickListener { expression += ")"; display?.text = expression }
        view.findViewById<Button>(R.id.btnDot).setOnClickListener { expression += "."; display?.text = expression }
        view.findViewById<Button>(R.id.btnAdd).setOnClickListener { expression += "+"; display?.text = expression }
        view.findViewById<Button>(R.id.btnSub).setOnClickListener { expression += "-"; display?.text = expression }
        view.findViewById<Button>(R.id.btnMul).setOnClickListener { expression += "*"; display?.text = expression }
        view.findViewById<Button>(R.id.btnDiv).setOnClickListener { expression += "/"; display?.text = expression }

        view.findViewById<Button>(R.id.btnAC).setOnClickListener {
            expression = ""; display?.text = "0"; exprView?.text = ""
        }

        view.findViewById<Button>(R.id.btnEqual).setOnClickListener {
            try {
                val e = Expression(expression.replace("^", "^"))
                val result = e.calculate()
                exprView?.text = expression
                expression = result.toString()
                display?.text = expression
            } catch (ex: Exception) { display?.text = "Error" }
        }

        return view
    }
}

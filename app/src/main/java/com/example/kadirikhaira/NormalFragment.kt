package com.example.kadirikhaira

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.mariuszgromada.math.mxparser.Expression

class NormalFragment : Fragment() {
    private var expression = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_normal, container, false)
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

        view.findViewById<Button>(R.id.btnDot).setOnClickListener {
            expression += "."
            display?.text = expression
        }

        view.findViewById<Button>(R.id.btnAdd).setOnClickListener {
            expression += "+"
            display?.text = expression
        }

        view.findViewById<Button>(R.id.btnSub).setOnClickListener {
            expression += "-"
            display?.text = expression
        }

        view.findViewById<Button>(R.id.btnMul).setOnClickListener {
            expression += "*"
            display?.text = expression
        }

        view.findViewById<Button>(R.id.btnDiv).setOnClickListener {
            expression += "/"
            display?.text = expression
        }

        view.findViewById<Button>(R.id.btnNeg).setOnClickListener {
            if (expression.startsWith("-")) {
                expression = expression.substring(1)
            } else {
                expression = "-$expression"
            }
            display?.text = expression
        }

        view.findViewById<Button>(R.id.btnPercent).setOnClickListener {
            expression += "/100"
            display?.text = expression
        }

        view.findViewById<Button>(R.id.btnAC).setOnClickListener {
            expression = ""
            display?.text = "0"
            exprView?.text = ""
        }

        view.findViewById<Button>(R.id.btnEqual).setOnClickListener {
            try {
                val e = Expression(expression)
                val result = e.calculate()
                exprView?.text = expression
                expression = result.toString()
                display?.text = expression
            } catch (ex: Exception) {
                display?.text = "Error"
            }
        }

        return view
    }
}

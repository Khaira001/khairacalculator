package com.example.kadirikhaira

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.*

class StatsMatrixFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_stats_matrix, container, false)
        val input = view.findViewById<EditText>(R.id.etNumbers)
        val result = view.findViewById<TextView>(R.id.tvStatsResult)

        fun getNumbers(): List<Double> {
            return input.text.toString().split(",").mapNotNull { it.trim().toDoubleOrNull() }
        }

        view.findViewById<Button>(R.id.btnMean).setOnClickListener {
            val nums = getNumbers()
            if (nums.isNotEmpty()) result.text = "Mean: ${nums.average()}"
        }

        view.findViewById<Button>(R.id.btnMedian).setOnClickListener {
            val nums = getNumbers()
            if (nums.isNotEmpty()) {
                val sorted = nums.sorted()
                val mid = sorted.size / 2
                val median = if (sorted.size % 2 == 0) (sorted[mid - 1] + sorted[mid]) / 2 else sorted[mid]
                result.text = "Median: $median"
            }
        }

        view.findViewById<Button>(R.id.btnMode).setOnClickListener {
            val nums = getNumbers()
            if (nums.isNotEmpty()) {
                val freq = nums.groupingBy { it }.eachCount()
                val maxFreq = freq.values.maxOrNull() ?: 0
                val modes = freq.filter { it.value == maxFreq }.keys
                result.text = "Mode: ${modes.joinToString(", ")}"
            }
        }

        view.findViewById<Button>(R.id.btnStdDev).setOnClickListener {
            val nums = getNumbers()
            if (nums.isNotEmpty()) {
                val mean = nums.average()
                val variance = nums.map { (it - mean).pow(2) }.average()
                result.text = "Std Dev: ${sqrt(variance)}"
            }
        }

        view.findViewById<Button>(R.id.btnVar).setOnClickListener {
            val nums = getNumbers()
            if (nums.isNotEmpty()) {
                val mean = nums.average()
                val variance = nums.map { (it - mean).pow(2) }.average()
                result.text = "Variance: $variance"
            }
        }

        view.findViewById<Button>(R.id.btnRange).setOnClickListener {
            val nums = getNumbers()
            if (nums.isNotEmpty()) result.text = "Range: ${nums.maxOrNull()!! - nums.minOrNull()!!}"
        }

        view.findViewById<Button>(R.id.btnMin).setOnClickListener {
            val nums = getNumbers()
            if (nums.isNotEmpty()) result.text = "Min: ${nums.minOrNull()}"
        }

        view.findViewById<Button>(R.id.btnMax).setOnClickListener {
            val nums = getNumbers()
            if (nums.isNotEmpty()) result.text = "Max: ${nums.maxOrNull()}"
        }

        view.findViewById<Button>(R.id.btnSum).setOnClickListener {
            val nums = getNumbers()
            if (nums.isNotEmpty()) result.text = "Sum: ${nums.sum()}"
        }

        // --- MATRIX ---
        val matResult = view.findViewById<TextView>(R.id.tvMatrixResult)

        fun getMatrix(ids: List<Int>): Array<DoubleArray> {
            val vals = ids.map { view.findViewById<EditText>(it).text.toString().toDoubleOrNull() ?: 0.0 }
            return arrayOf(doubleArrayOf(vals[0], vals[1]), doubleArrayOf(vals[2], vals[3]))
        }

        val aIds = listOf(R.id.a11, R.id.a12, R.id.a21, R.id.a22)
        val bIds = listOf(R.id.b11, R.id.b12, R.id.b21, R.id.b22)

        fun matToString(m: Array<DoubleArray>): String {
            return "[ ${m[0][0]}  ${m[0][1]} ]\n[ ${m[1][0]}  ${m[1][1]} ]"
        }

        fun det2x2(m: Array<DoubleArray>) = m[0][0] * m[1][1] - m[0][1] * m[1][0]

        view.findViewById<Button>(R.id.btnMatAdd).setOnClickListener {
            val a = getMatrix(aIds); val b = getMatrix(bIds)
            val r = Array(2) { i -> DoubleArray(2) { j -> a[i][j] + b[i][j] } }
            matResult.text = "A + B =\n${matToString(r)}"
        }

        view.findViewById<Button>(R.id.btnMatSub).setOnClickListener {
            val a = getMatrix(aIds); val b = getMatrix(bIds)
            val r = Array(2) { i -> DoubleArray(2) { j -> a[i][j] - b[i][j] } }
            matResult.text = "A - B =\n${matToString(r)}"
        }

        view.findViewById<Button>(R.id.btnMatMul).setOnClickListener {
            val a = getMatrix(aIds); val b = getMatrix(bIds)
            val r = Array(2) { i -> DoubleArray(2) { j -> a[i][0]*b[0][j] + a[i][1]*b[1][j] } }
            matResult.text = "A × B =\n${matToString(r)}"
        }

        view.findViewById<Button>(R.id.btnDetA).setOnClickListener {
            matResult.text = "det(A) = ${det2x2(getMatrix(aIds))}"
        }

        view.findViewById<Button>(R.id.btnDetB).setOnClickListener {
            matResult.text = "det(B) = ${det2x2(getMatrix(bIds))}"
        }

        view.findViewById<Button>(R.id.btnTransA).setOnClickListener {
            val a = getMatrix(aIds)
            val r = Array(2) { i -> DoubleArray(2) { j -> a[j][i] } }
            matResult.text = "Transpose A =\n${matToString(r)}"
        }

        return view
    }
}

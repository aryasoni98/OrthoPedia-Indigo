package com.example.feetwings.activity

import android.graphics.Color
import android.os.Bundle
import com.example.feetwings.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_graph.*


class GraphActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)


        setupActionBar()

        val chart1 = findViewById<LineChart>(R.id.graph_1)
        val chart2 = findViewById<LineChart>(R.id.graph_2)

        val xValue = ArrayList<String>()
        xValue.add("1 May")
        xValue.add("2 May")
        xValue.add("3 May")
        xValue.add("4 May")
        xValue.add("5 May")

        val lineEntry = ArrayList<Entry>()
        lineEntry.add(Entry(6.4F, 0))
        lineEntry.add(Entry(5.2F, 1))
        lineEntry.add(Entry(5.7F, 2))
        lineEntry.add(Entry(6.2F, 3))
        lineEntry.add(Entry(6.4F, 4))

        val lineEntry2 = ArrayList<Entry>()
        lineEntry2.add(Entry(5.4F, 0))
        lineEntry2.add(Entry(4.8F, 1))
        lineEntry2.add(Entry(5.1F, 2))
        lineEntry2.add(Entry(5.6F, 3))
        lineEntry2.add(Entry(5.5F, 4))

        val lineDataSet = LineDataSet(lineEntry, "Before Meal")
        lineDataSet.color= Color.parseColor("#800080")
        val lineDataSet2 = LineDataSet(lineEntry2, "After Meal")
        lineDataSet2.color= Color.parseColor("#008000")

        val finalData=ArrayList<LineDataSet>()
        finalData.add(lineDataSet)
        finalData.add(lineDataSet2)

        val data = LineData(xValue, finalData as List<ILineDataSet>?)

        val xAxis_1 = chart1.xAxis
        xAxis_1.position = XAxis.XAxisPosition.BOTTOM
        chart2.setDescription(null)
        chart2.legend.isEnabled=false

        val xAxis_2 = chart2.xAxis
        xAxis_2.position = XAxis.XAxisPosition.BOTTOM
        chart2.setDescription(null)
        chart2.legend.isEnabled=false




        chart1.data = data
        chart1.setBackgroundColor(getColor(R.color.color_chart_one))
        chart1.animateXY(1300, 1300)

        chart2.data = data
        chart2.setBackgroundColor(getColor(R.color.color_chart_two))
        chart2.animateXY(1300, 1300)


    }

    /**
     * function to setup the action bar of activity.
     */
    private fun setupActionBar() {
        setSupportActionBar(toolbar_graph_activity)
        toolbar_graph_activity?.setNavigationIcon(R.drawable.ic_baseline_arrow_back)
        toolbar_graph_activity?.title = "Foot Temperature"
        toolbar_graph_activity?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
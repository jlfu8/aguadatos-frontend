package com.example.aguadatos_frontend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aguadatos_frontend.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding first
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize lineChart after setting content view
        lineChart = findViewById(R.id.lineChart)
        setupLineChartData()

        // Navigation setup
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Invalidate the chart to refresh and display
        lineChart.invalidate()
    }

    private fun setupLineChartData() {
        val entries = ArrayList<Entry>()

        // Generate 10 data points with a Y value of 20
        for (i in 1..15) {
            var y = 10
            if(i%2==0){
                y = 0
            }
            entries.add(Entry(i.toFloat(), y.toFloat()))
        }
        val minY = 0
        val maxY = 30

        val dataSet = LineDataSet(entries, "Sample Data").apply {
            // Line style
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = R.color.blue // Adjust color to match your image
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)

            // Circle at a specific data point
            val circleRadius = 6f
            val circleColor = getColor(R.color.blue) // Adjust color to match your image
            setCircleColor(circleColor)
            setCircleRadius(circleRadius)

            // Filling the area below the line
            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(
                this@HomeActivity,
                R.drawable.rounded_blue
            ) // Create a gradient drawable to match your image
        }

        // Create a LineData object that will be passed to the chart
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        this.lineChart.axisLeft?.let { yAxis ->
            yAxis.axisMinimum = 0f // Set the minimum value of the y-axis to zero
            yAxis.axisMaximum = 30f // Set the maximum value of the y-axis to be a bit more than the highest entry
            yAxis.granularity = 1f // Set the granularity of the y-axis
        }
        lineChart.invalidate()
        // Customize chart options to match the provided image
        lineChart.apply {
            description.isEnabled = false // No description text
            axisRight.isEnabled = false // No right axis

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false) // No grid lines
                setDrawAxisLine(false) // No axis line
                textColor = getColor(R.color.blue) // Adjust text color to match your image
                // ... set other xAxis styles like text size, granularity, etc.
            }

            axisLeft.apply {
                setDrawGridLines(true) // Only grid lines for Y axis
                gridColor = getColor(R.color.blue) // Adjust grid color to match your image
                textColor = getColor(R.color.blue) // Adjust text color to match your image
                // ... set other axisLeft styles
            }

            legend.isEnabled = false // No legend
            setTouchEnabled(true)
            setPinchZoom(true)

            // Animation
            animateX(1500)

            // Highlight the circle at specific point
            highlightValue(13f, 0) // Provide x value for the data point you want to highlight
        }

        // Refresh the chart
        lineChart.invalidate()
    }
}
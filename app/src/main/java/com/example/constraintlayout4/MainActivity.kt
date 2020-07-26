package com.example.constraintlayout4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ViewGroup>(R.id.baseLayout).addOnLayoutChangeListener { v, _, _, _, _, _, _, _, _ ->
            (v as ViewGroup).indicateSizes()
            findViewById<TextView>(R.id.txtWidth).text =
                    "WIDTH = ${v.width} px (100 dp = ${resources.displayMetrics.density * 100} px)"
        }
    }

    private fun ViewGroup.indicateSizes() {
        for(index in 0 until childCount) {
            when(val child = getChildAt(index)) {
                is TextView -> (child.layoutParams as? LinearLayout.LayoutParams)?.let {
                    if(it.weight > 0) {
                        child.text = "${child.width} px"
                    }
                } ?: (child.layoutParams as? ConstraintLayout.LayoutParams)?.let {
                    if(it.horizontalWeight > 0 ||
                        (0 < it.matchConstraintPercentWidth && it.matchConstraintPercentWidth < 1)
                    ) {
                        child.text = "${child.width} px"
                    }
                }
                is ViewGroup -> child.indicateSizes()
            }
        }
    }
}

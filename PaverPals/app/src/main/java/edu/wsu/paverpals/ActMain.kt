//====================================================================
//
// Application: PaverPals
// Activity:    ActMain
// Course:      CSC 4992
// Homework:    1
// Author:      Vincent PICOT gr9185
// Date:        28/01/2019
// Description:
//   Application to estimates the cost to pave a driveway and the sidewalks around a house.
//
//  Inputs: -Size of the driveway in square meter
//          -Size of the sidewalks in square meter
//          -Estimate of hours to complete the work
//
//  Outputs:    -Estimated labor cost (at $50 per hour).
//              -Estimated cost to use asphalt (at $25 per square meter).
//              -Estimated cost to use concrete (at $75 per square meter).
//
//
//====================================================================
package edu.wsu.paverpals

// Import packages
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.Toast

// Import views from layout
import kotlinx.android.synthetic.main.laymain.*
import org.w3c.dom.Text

//--------------------------------------------------------------------
// class ActMain
//--------------------------------------------------------------------
class ActMain : AppCompatActivity() {

    //----------------------------------------------------------------
    // Class constants and variables
    //----------------------------------------------------------------

    // Declare application constants
    val APP_NAME = "PaverPals"
    val APP_VERSION = "1.0"

    //----------------------------------------------------------------
    // onCreate
    //----------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laymain)


        // Add text listener on the edit text to activate or deactivate the button and display toast if value is out of range
        txtDrivewaySize.addTextChangedListener(object : TextWatcher { // Create an anonymous object TextWatcher
            // Method that need to be implemented from the TextWatcher interface
            override fun afterTextChanged(str: Editable?) {
                val drivewaySize = if (str?.toString() != "") str?.toString()?.toInt() else null
                val sidewalkSize = if (txtSidewalkSize.text?.toString() != "") txtSidewalkSize.text?.toString()?.toInt() else null
                val hourEstimation = if (txtHourEstimation.text?.toString() != "") txtHourEstimation.text?.toString()?.toInt() else null

                if (drivewaySize == null || sidewalkSize == null || hourEstimation == null) {
                    deactivateBtnCalculatePrices()
                    if (drivewaySize != null && !(drivewaySize in 100..1000)) {
                        displayToast("The size of the driveway must be comprise between 100 and 1000.")
                    }
                    return
                }
                if (drivewaySize in 100..1000
                    && sidewalkSize in 100..1000
                    && hourEstimation in 10..100) {
                    activateBtnCalculatePrices()
                } else if (!(drivewaySize in 100..1000)) {
                    displayToast("The size of the driveway must be comprise between 100 and 1000.")
                    deactivateBtnCalculatePrices()
                } else {
                    deactivateBtnCalculatePrices()
                }
            }

            // Method that need to be implemented from the TextWatcher interface
            override fun beforeTextChanged(str: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // Method that need to be implemented from the TextWatcher interface
            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        // Add text listener on the edit text to activate or deactivate the button and display toast if value is out of range
        txtSidewalkSize.addTextChangedListener(object : TextWatcher { // Create an anonymous object TextWatcher
            // Method that need to be implemented from the TextWatcher interface
            override fun afterTextChanged(str: Editable?) {
                val drivewaySize = if (txtDrivewaySize.text?.toString() != "") txtDrivewaySize.text?.toString()?.toInt() else null
                val sidewalkSize = if (str?.toString() != "") str?.toString()?.toInt() else null
                val hourEstimation = if (txtHourEstimation.text?.toString() != "") txtHourEstimation.text?.toString()?.toInt() else null

                if (drivewaySize == null || sidewalkSize == null || hourEstimation == null) {
                    deactivateBtnCalculatePrices()
                    if (sidewalkSize != null && !(sidewalkSize in 100..1000)) {
                        displayToast("The size of the sidewalk must be comprise between 100 and 1000.")
                    }
                    return
                }
                if (drivewaySize in 100..1000
                    && sidewalkSize in 100..1000
                    && hourEstimation in 10..100) {
                    activateBtnCalculatePrices()
                } else if (!(sidewalkSize in 100..1000)) {
                    displayToast("The size of the sidewalk must be comprise between 100 and 1000.")
                    deactivateBtnCalculatePrices()
                } else {
                    deactivateBtnCalculatePrices()
                }
            }

            // Method that need to be implemented from the TextWatcher interface
            override fun beforeTextChanged(str: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // Method that need to be implemented from the TextWatcher interface
            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        // Add text listener on the edit text to activate or deactivate the button and display toast if value is out of range
        txtHourEstimation.addTextChangedListener(object : TextWatcher { // Create an anonymous object TextWatcher
            // Method that need to be implemented from the TextWatcher interface
            override fun afterTextChanged(str: Editable?) {
                val drivewaySize = if (txtDrivewaySize.text?.toString() != "") txtDrivewaySize.text?.toString()?.toInt() else null
                val sidewalkSize = if (txtSidewalkSize.text?.toString() != "") txtSidewalkSize.text?.toString()?.toInt() else null
                val hourEstimation = if (str?.toString() != "") str?.toString()?.toInt() else null

                if (drivewaySize == null || sidewalkSize == null || hourEstimation == null) {
                    if (hourEstimation != null && !(hourEstimation in 10..100)) {
                        displayToast("The hour estimation must be comprise between 10 and 100.")
                    }
                    deactivateBtnCalculatePrices()
                    return
                }
                if (drivewaySize in 100..1000
                    && sidewalkSize in 100..1000
                    && hourEstimation >= 10 && hourEstimation <= 100) {
                    activateBtnCalculatePrices()
                } else if (!(hourEstimation in 10..100)) {
                    displayToast("The hour estimation must be comprise between 10 and 100.")
                    deactivateBtnCalculatePrices()
                } else {
                    deactivateBtnCalculatePrices()
                }
            }

            // Method that need to be implemented from the TextWatcher interface
            override fun beforeTextChanged(str: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // Method that need to be implemented from the TextWatcher interface
            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        // Listener for the button btnCalculatePrices
        btnCalculatePrices.setOnClickListener {
            val drivewaySize = if (txtDrivewaySize.text?.toString() != "") txtDrivewaySize.text?.toString()?.toInt() else null
            val sidewalkSize = if (txtSidewalkSize.text?.toString() != "") txtSidewalkSize.text?.toString()?.toInt() else null
            val hourEstimation = if (txtHourEstimation.text?.toString() != "") txtHourEstimation.text?.toString()?.toInt() else null

            if (drivewaySize == null || sidewalkSize == null || hourEstimation == null) {
                deactivateBtnCalculatePrices()
                return@setOnClickListener
            }

            val totalSize = drivewaySize + sidewalkSize

            txtAsphaltCost.setText("${totalSize * 25}", null)
            txtConcreteCost.setText("${totalSize * 75}", null)
            txtLaborEstimation.setText("${hourEstimation * 50}", null)
        }

        // Disable the button to calculate prices
        btnCalculatePrices.isClickable = false
    }


    //----------------------------------------------------------------
    // deactivateBtnCalculatePrices
    //----------------------------------------------------------------
    // Method to deactivate the button btnCalculatePrices and change its color to grey
    //----------------------------------------------------------------
    fun deactivateBtnCalculatePrices() {
        btnCalculatePrices.setBackgroundColor( ContextCompat.getColor(this@ActMain, R.color.grey))
        btnCalculatePrices.isClickable = false
    }

    //----------------------------------------------------------------
    // activateBtnCalculatePrices
    //----------------------------------------------------------------
    // Method to activate the button btnCalculatePrices and change its color to green
    //----------------------------------------------------------------
    fun activateBtnCalculatePrices() {
        btnCalculatePrices.setBackgroundColor( ContextCompat.getColor(this@ActMain, R.color.green))
        btnCalculatePrices.isClickable = true
    }

    //----------------------------------------------------------------
    // displayToast
    //----------------------------------------------------------------
    // Method that display a toast based on the message it takes as input string
    //----------------------------------------------------------------
    fun displayToast(message: String) {
        val toast = Toast.makeText(this@ActMain, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }


}



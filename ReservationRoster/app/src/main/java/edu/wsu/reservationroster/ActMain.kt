//====================================================================
//
// Application: Reservation Roster
// Activity:    ActMain
// Course:      CSC 4992
// Homework:    3
// Author:      Vincent PICOT gr9185@wayne.edu
// Date:        03/17/2019
//
// Description:
//      Application to demonstrate the application of a Timer (Timed task).
//      We have a list of Reservation, each reservation having a waiting time
//      This waiting time is reduced randomly each run of the timed task until getting to 0
//
//====================================================================
package edu.wsu.reservationroster

/** Import packages */
import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import java.util.*
import android.widget.Toast
import kotlin.collections.ArrayList

/** Import the ALL the views from the layout laymain.xml, it is equivalent to the java findViewById method */
import kotlinx.android.synthetic.main.laymain.*

// Class ActMain
class ActMain : AppCompatActivity() {

    //----------------------------------------------------------------
    // Class constants and variables
    //----------------------------------------------------------------

    // Constant variable defining the 'dummy' data (reservations) added when we start the simulation
    private val defaultReservations = arrayListOf(Pair("Jean", 4),
        Pair("Mewen", 2),
        Pair("Vincent", 6),
        Pair("Yewon", 3),
        Pair("Jean-Louis DuChateau", 1),
        Pair("Florent", 10))

    // Optional variable corresponding to the timed task
    private var timer: Timer? = null

    private var simulationStarted: Boolean = false

    //----------------------------------------------------------------
    // onCreate
    //----------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laymain)

        val header = layoutInflater.inflate(R.layout.layheader_listview_reservations, null) as View
        listViewClientOrder.addHeaderView(header)
        showReservations()
    }

    //----------------------------------------------------------------
    // initReservations
    //----------------------------------------------------------------
    // Reset the data inside the SharedSingleton object
    // Meaning, the list of reservation is cleared and the last wait time set to 0,
    // and the 'dummy' data are inserted into the reservation list
    //----------------------------------------------------------------
    private fun initReservations() {
        SharedSingleton.lastWaitingTime = 0
        SharedSingleton.reservations.clear()
        val defaultReservationsList = ArrayList<Reservation>()
        defaultReservations.mapTo(defaultReservationsList) {
            Reservation(it.first, it.second)
        }
        SharedSingleton.reservations.addAll(defaultReservationsList)
    }

    //----------------------------------------------------------------
    // showReservations
    //----------------------------------------------------------------
    // Get a sublist from the SharedSingleton object's reservation list
    // without the reservations having a waiting time <= 0
    // This sublist is then passed to the adapter and the adapter to the listView from laymain
    //----------------------------------------------------------------
    fun showReservations() {
        val showList = ArrayList(SharedSingleton.reservations.filter {
            it.getWaitTime() > 0
        })
        showList.sortBy { it.getWaitTime() }
        val adapter = AdapterReservations(this, showList)
        listViewClientOrder.adapter = adapter
    }

    //----------------------------------------------------------------
    // makeReservation
    //----------------------------------------------------------------
    // Takes the input from textViews txtPartyName and txtNbInParty,
    // and create a new reservation with those inputs
    // The new reservation is then added to the SharedSingleton object's reservation list
    //----------------------------------------------------------------
    fun makeReservation(@Suppress("UNUSED_PARAMETER") view: View) {
        val partyName = txtPartyName.text.toString()
        val nbParty = txtNbInParty.text.toString()

        if (partyName.isEmpty() || nbParty.isEmpty()) {
            Toast.makeText(
                this@ActMain,
                "Missing information. Cannot process reservation",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (!simulationStarted) {
            Toast.makeText(
                this@ActMain,
                "Please, start the simulation before adding any reservations!",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val reservation = Reservation(partyName, nbParty.toInt())
        SharedSingleton.reservations.add(reservation)
        showReservations()
        Toast.makeText(
            this@ActMain,
            "Reservation for $partyName, party of $nbParty added.\n" +
                    "Estimated waiting time ${reservation.getWaitTime()} minutes.",
            Toast.LENGTH_LONG
        ).show()
    }

    //----------------------------------------------------------------
    // clearReservation
    //----------------------------------------------------------------
    // Set the content textViews txtPartyName and txtNbInParty to empty string
    //----------------------------------------------------------------
    fun clearReservation(@Suppress("UNUSED_PARAMETER") view: View) {
        txtPartyName.setText("")
        txtNbInParty.setText("")
    }

    //----------------------------------------------------------------
    // startTimerTask
    //----------------------------------------------------------------
    // Create a new Timer with class AutoTask and interval time of INTERVAL_TIMER_TASK
    //----------------------------------------------------------------
    fun startTimerTask(@Suppress("UNUSED_PARAMETER") view: View) {
        initReservations()
        showReservations()

        simulationStarted = true

        timer = Timer()
        timer?.schedule(
            AutoTask(this),
            SharedSingleton.INTERVAL_TIMER_TASK,
            SharedSingleton.INTERVAL_TIMER_TASK
        )

        Toast.makeText(
            this@ActMain,
            "Simulation started.",
            Toast.LENGTH_LONG
        ).show()
    }

    //----------------------------------------------------------------
    // stopTimerTask
    //----------------------------------------------------------------
    // Cancel the timed task if it exists and set timer to null
    //----------------------------------------------------------------
    fun stopTimerTask(@Suppress("UNUSED_PARAMETER") view: View) {
        timer?.cancel()
        timer = null

        simulationStarted = false

        Toast.makeText(
            this@ActMain,
            "Simulation stopped.",
            Toast.LENGTH_LONG
        ).show()
    }

    //----------------------------------------------------------------
    // updateReservations
    //----------------------------------------------------------------
    // Handler called by the AutoTask class after every task run
    //----------------------------------------------------------------
    @SuppressLint("HandlerLeak")
    val updateReservations: Handler = object : Handler() {

        //------------------------------------------------------------
        // handleMessage
        //------------------------------------------------------------
        override fun handleMessage(msg: Message) {
            showReservations()
        }

    }
}

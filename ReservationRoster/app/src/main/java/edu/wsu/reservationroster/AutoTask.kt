//====================================================================
//
// Application: Reservation Roster
// Class:       AutoTask
//
// Description:
//      Class that is used by the Timer (timed task) in ActMain
//
//====================================================================
package edu.wsu.reservationroster

/** Import packages */
import java.util.*

// Class AutoTask
/** The parenthesis after the class's name is the constructor (this is a special syntax of Kotlin) */
class AutoTask(private val parent: ActMain): TimerTask() {

    //----------------------------------------------------------------
    // run
    //----------------------------------------------------------------
    // Override method from the parent class TimerTask
    // This method is called by the Timer from ActMain periodically
    //----------------------------------------------------------------
    override fun run() {
        updateWaitTimes()
    }

    //----------------------------------------------------------------
    // updateWaitTimes
    //----------------------------------------------------------------
    // Exclusively called by the above run method
    // Get a sublist of reservations with waiting time > 0 and remove
    // time randomly between 2 and 4 to these reservation
    // Update the SharedSingleton object's lastWaitingTime accordingly
    //----------------------------------------------------------------
    private fun updateWaitTimes() {
        val showedList = ArrayList(SharedSingleton.reservations.filter {
            it.getWaitTime() > 0
        })

        for (reservation in showedList) {
            reservation.setWaitTime(reservation.getWaitTime() - (2..4).random())
        }

        // Find the maximum waiting time from the showedList, if result is null, gives 0
        SharedSingleton.lastWaitingTime =
            showedList.maxBy { it.getWaitTime() }?.getWaitTime()
                ?: 0

        parent.updateReservations.sendEmptyMessage(0)
    }

}
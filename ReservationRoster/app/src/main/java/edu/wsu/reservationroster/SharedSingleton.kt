//====================================================================
//
// Application: Reservation Roster
// Class:       SharedSingleton
//
// Description:
//      Singleton class to share data between threads
//
//====================================================================
package edu.wsu.reservationroster

// class SharedSingleton
/** kotlin allows us to create singleton easily with the attribute object
 * It has the advantage of being natively thread safe and to have a lazy initialisation */
object SharedSingleton {

    //----------------------------------------------------------------
    // Class constants and variables
    //----------------------------------------------------------------

    const val INTERVAL_TIMER_TASK: Long = 4000 // In milliseconds

    val reservations: ArrayList<Reservation> = ArrayList()

    var lastWaitingTime = 0
}
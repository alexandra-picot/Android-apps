//====================================================================
//
// Application: Reservation Roster
// Class:       Reservation
//
// Description:
//      Model class for a reservation
//
//====================================================================
package edu.wsu.reservationroster

// class Reservation
/** The parenthesis after the class's name is the constructor (this is a special syntax of Kotlin) */
class Reservation(private val partyName: String,
                  private val nbParty: Int) {

    //----------------------------------------------------------------
    // Class constants and variables
    //----------------------------------------------------------------

    private var waitTime: Int = 0
    private val reservationId: Int = SharedSingleton.reservations.size + 1

    /** In Kotlin, there are different initialization steps,
     * The first one is the constructor above, just after the class's name
     * The second one are the secondary constructor (not present here)
     * The third steps are the variable definition and init blocks,
     * those are executed in the order they are defined in the class code */
    init {
        startWaitTime()
    }

    //----------------------------------------------------------------
    // startWaitTime
    //----------------------------------------------------------------
    // Assign the waitTime attribute
    //----------------------------------------------------------------
    private fun startWaitTime() {
        waitTime = SharedSingleton.lastWaitingTime + (4..10).random()
        SharedSingleton.lastWaitingTime = waitTime
    }

    //----------------------------------------------------------------
    // setWaitTime
    //----------------------------------------------------------------
    // Setter for waitTime attribute
    //----------------------------------------------------------------
    /** Set time is the only attribute that has a setter as it's the only mutable attribute for this class */
    fun setWaitTime(waitTime: Int) {
        this.waitTime = waitTime
    }

    //----------------------------------------------------------------
    // Getters for the class's attributes
    //----------------------------------------------------------------
    // Override method of BaseAdapter parent class
    //----------------------------------------------------------------
    fun getReservationId(): Int {
        return reservationId
    }

    fun getPartyName(): String {
        return partyName
    }

    fun getNbParty(): Int {
        return nbParty
    }

    fun getWaitTime(): Int {
        return waitTime
    }

    //----------------------------------------------------------------
    // toString
    //----------------------------------------------------------------
    // Override method from the parent class
    // Return a string representing the object
    //----------------------------------------------------------------
    override fun toString(): String {
        return "Reservation(reservationId=$reservationId partyName='$partyName' nbParty=$nbParty, waitTime=$waitTime)"
    }
}
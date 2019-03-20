//====================================================================
//
// Application: Reservation Roster
// Class:       AdapterReservations
//
// Description:
//      Adapter for the reservations listview
//
//====================================================================
package edu.wsu.reservationroster

/** Import packages */
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/** Import the ALL the views contained in the layrow_listview_reservations layout */
import kotlinx.android.synthetic.main.layrow_listview_reservations.view.*

// class AdapterReservations
/** The parenthesis after the class's name is the constructor (this is a special syntax of Kotlin) */
class AdapterReservations(private val context: Context,
                          private val dataSource: ArrayList<Reservation>) : BaseAdapter() {

    //----------------------------------------------------------------
    // Class constants and variables
    //----------------------------------------------------------------

    // The view inflater gotten from context
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    //----------------------------------------------------------------
    // getCount
    //----------------------------------------------------------------
    // Override method of BaseAdapter parent class
    //----------------------------------------------------------------
    override fun getCount(): Int {
        return dataSource.size
    }

    //----------------------------------------------------------------
    // getItem
    //----------------------------------------------------------------
    // Override method of BaseAdapter parent class
    //----------------------------------------------------------------
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //----------------------------------------------------------------
    // getItemId
    //----------------------------------------------------------------
    // Override method of BaseAdapter parent class
    //----------------------------------------------------------------
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //----------------------------------------------------------------
    // getView
    //----------------------------------------------------------------
    // Override method of BaseAdapter parent class
    // Inflate a new view from layout layrow_listview_reservations
    // and insert the reservation at position 'position' from dataSource
    //----------------------------------------------------------------
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val client = getItem(position) as Reservation

        val rowView = inflater.inflate(R.layout.layrow_listview_reservations, parent, false)

        rowView.lblPartyName.text = client.getPartyName()
        rowView.lblNbParty.text = client.getNbParty().toString()
        rowView.lblWaitTime.text = client.getWaitTime().toString()

        return rowView
    }
}
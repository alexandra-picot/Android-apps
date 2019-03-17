//====================================================================
//
// Application: MemoryMagicians
// Activity:    ActMain
// Course:      CSC 4992
// Homework:    2
// Author:      Vincent PICOT
// Date:        02/11/2019
// Description:
//  Single view application with objective is to use the user's memory
//  The app contains 12 cards, content hidden, that needs to be paired
//  The user win if he/she match all pairs
//
//====================================================================

package edu.wsu.memorymagicians

// Import packages
import android.app.AlertDialog
import java.util.concurrent.ThreadLocalRandom
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast

// Import all views from the laymain layout
import kotlinx.android.synthetic.main.laymain.*


class ActMain : AppCompatActivity() {

    // Constant for the number of pairs to find in order to complete the game
    private val TOTAL_PAIRS = 6


    // Object that return random numbers
    // It is used to shuffle the imagePosition array
    private val rnd = ThreadLocalRandom.current()


    // Array that contains the IDs of the images to be displayed in the cells
    private val imageArray = arrayOf(R.drawable.ic_club, R.drawable.ic_heart,
        R.drawable.ic_diamond, R.drawable.ic_spade,
        R.drawable.ic_square, R.drawable.ic_triangle)


    // Array that contains the references of every cell of the game
    // It is initialized in the onCreate method
    private var refsImgBtn: Array<ImageButton>? = null


    // Array containing value from 0 to 6 two times each
    // Those values correspond to indexes in the imageArray
    // Index 0 in this array is "linked" to index 0 in refsImgBtn
    private var imagePosition: IntArray = intArrayOf(0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5)


    // Tracking variable for when the user select cells
    private var nbSelectedCell: Int = 0
    private var indexSelectedCells: IntArray = intArrayOf()


    // Tracking variables for the user's number of tries and matches
    // nbTries is modified every time the user select two cells
    // nbMatches is modified when the user select two cells with the same image
    private var nbTries: Int = 0
    private var nbMatches: Int = 0


    // Variables for the color of the cells when they are in the match
    // or unmatch state
    // Those variables are changed when the user click on the corresponding radio button
    private var matchColor: Int = R.color.yellow
    private var unmatchColor: Int = R.color.green


    //----------------------------------------------------------------
    // onCreate
    //----------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laymain)

        refsImgBtn = arrayOf(
            imgBtn1, imgBtn2, imgBtn3, imgBtn4,
            imgBtn5, imgBtn6, imgBtn7, imgBtn8,
            imgBtn9, imgBtn10, imgBtn11, imgBtn12
        )

        refsImgBtn!!.forEach { setImgBtnToUnmatchColor(it) }
        randomizeImages()

        txtMatches.setText(nbMatches.toString())
        txtTries.setText(nbTries.toString())
    }


    //----------------------------------------------------------------
    // resetBoard
    //----------------------------------------------------------------
    // Reset the board game
    //----------------------------------------------------------------
    fun resetBoard(@Suppress("UNUSED_PARAMETER") view: View?) {
        refsImgBtn!!.forEach { setImgBtnToUnmatchColor(it) }

        nbSelectedCell = 0
        indexSelectedCells = intArrayOf()
        nbMatches = 0
        nbTries = 0

        randomizeImages()

        txtMatches.setText(nbMatches.toString())
        txtTries.setText(nbTries.toString())

        Toast.makeText(this@ActMain, "The board has been reset", Toast.LENGTH_SHORT).show()
    }


    //----------------------------------------------------------------
    // matchImage
    //----------------------------------------------------------------
    // Function that respond to a click on a cell
    // This function contains the majority of the game's logic
    //----------------------------------------------------------------
    fun matchImage(view: View) {
        nbSelectedCell += 1
        if (nbSelectedCell >= 3) {
            resetBoard(null)
            return
        }

        val selectedCell = view as ImageButton
        val indexSelectedCell = refsImgBtn!!.indexOf(selectedCell)

        revealSelectedCell(selectedCell, indexSelectedCell)
        indexSelectedCells += indexSelectedCell

        if (nbSelectedCell == 2) {
            val handler = Handler()
            handler.postDelayed({

                if (indexSelectedCells.isEmpty()) {
                    return@postDelayed
                }

                val dialog = AlertDialog.Builder(this@ActMain)
                nbTries += 1

                if (indexSelectedCells.size < 2) {
                    return@postDelayed
                }
                else if (imagePosition[indexSelectedCells[0]] == imagePosition[indexSelectedCells[1]]) {
                    nbMatches += 1

                    dialog.setTitle("Good job!")
                    dialog.setMessage("The two images matches.\n\n" +
                            "You have ${TOTAL_PAIRS - nbMatches} pairs left to find.")
                    indexSelectedCells.forEach { setImgBtnToMatchColor(refsImgBtn!![it]) }

                } else {
                    dialog.setTitle("Too bad :(")
                    dialog.setMessage("The two images don't match.\n\n" + "" +
                            "You still have ${TOTAL_PAIRS - nbMatches} pairs left to find.")
                    indexSelectedCells.forEach { setImgBtnToUnmatchColor(refsImgBtn!![it]) }
                }
                dialog.setPositiveButton("Ok") { _, _ ->
                    if (nbMatches == TOTAL_PAIRS) {
                        resetBoard(null)
                        displayWinningDialog()
                    }
                }
                dialog.show()

                txtMatches.setText(nbMatches.toString())
                txtTries.setText(nbTries.toString())

                nbSelectedCell = 0
                indexSelectedCells = intArrayOf()


            }, 1000)
        }

    }


    //----------------------------------------------------------------
    // displayWinningDialog
    //----------------------------------------------------------------
    // Display a one button alert dialog to notifies the use won that game
    //----------------------------------------------------------------
    fun displayWinningDialog() {
        val winDialog = AlertDialog.Builder(this@ActMain)
        winDialog.setTitle("Game Over!")
        winDialog.setMessage("Congratulation, you've found all the pairs.")
        winDialog.setPositiveButton("Ok", null)
        winDialog.show()
    }

    //----------------------------------------------------------------
    // setImgBtnToUnmatchColor
    //----------------------------------------------------------------
    // Set the given cell to the unmatch color state
    // It means that the cell change to the unmatchColor color
    //----------------------------------------------------------------
    fun setImgBtnToUnmatchColor(imgBtn: ImageButton) {
        imgBtn.isEnabled = true
        imgBtn.setImageResource(android.R.color.transparent)
        imgBtn.setBackgroundResource(unmatchColor)
    }


    //----------------------------------------------------------------
    // revealSelectedCell
    //----------------------------------------------------------------
    // Reveal the image hidden in the selected cell
    // It means that the cell is disabled (to avoid multiple click on it) and change the background to white
    // and have a drawable is added corresponding to the hidden image
    //----------------------------------------------------------------
    fun revealSelectedCell(imgBtn: ImageButton, indexSelectedCell: Int) {
        imgBtn.isEnabled = false
        imgBtn.setImageDrawable(getDrawable(imageArray[imagePosition[indexSelectedCell]]))
        imgBtn.setBackgroundResource(R.color.white)
    }


    //----------------------------------------------------------------
    // setImgBtnToMatchColor
    //----------------------------------------------------------------
    // Set the given cell to the match color state
    // It means that the cell is disabled and change to the matchColor color
    //----------------------------------------------------------------
    fun setImgBtnToMatchColor(imgBtn: ImageButton) {
        imgBtn.isEnabled = false
        imgBtn.setImageResource(android.R.color.transparent)
        imgBtn.setBackgroundResource(matchColor)
    }


    //----------------------------------------------------------------
    // randomizeImages
    //----------------------------------------------------------------
    // Shuffle the imagePosition array so as to change the location
    // of each image on the board
    //----------------------------------------------------------------
    fun randomizeImages() {
        for (i in imagePosition.size - 1 downTo 0) {
            val index = rnd.nextInt(i + 1)
            val tmp = imagePosition[index]
            imagePosition[index] = imagePosition[i]
            imagePosition[i] = tmp
        }
    }


    //----------------------------------------------------------------
    // changeMatchedColor
    //----------------------------------------------------------------
    // Function that react to a touch of the match color radio buttons
    //----------------------------------------------------------------
    fun changeMatchedColor(view: View) {
        val rdBtnSelected = view as RadioButton

        when (rdBtnSelected) {
            rbYellow -> matchColor = R.color.yellow
            rbBlue -> matchColor = R.color.blue
            rbOrange -> matchColor = R.color.orange
        }
        resetBoard(null)
    }


    //----------------------------------------------------------------
    // changeUnmatchedColor
    //----------------------------------------------------------------
    // Function that react to a touch of the unmatch color radio buttons
    //----------------------------------------------------------------
    fun changeUnmatchedColor(view: View) {
        val rdBtnSelected = view as RadioButton

        when (rdBtnSelected) {
            rbRed -> unmatchColor = R.color.red
            rbGreen -> unmatchColor = R.color.green
            rbPurple -> unmatchColor = R.color.purple
        }
        resetBoard(null)
    }
}

package com.example.myapplication_memcardpro

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication_memcardpro.ui.dashboard.DashboardFragment

class MainActivity : AppCompatActivity() {

    private val cardNames = ArrayList<String>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = CardAdapter(cardNames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addCardButton = findViewById<Button>(R.id.button)
        addCardButton.setOnClickListener {
            addCard()
        }

        val dashboardFragment = DashboardFragment()

        // Pass your memory cards as an argument
        val bundle = Bundle()
        bundle.putStringArrayList("memoryCards", ArrayList(cardNames))
        dashboardFragment.arguments = bundle

        // Add the dashboardFragment
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, dashboardFragment)
        fragmentTransaction.addToBackStack(null) // Optional: Add the transaction to the back stack
        fragmentTransaction.commit()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addCard() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_card, null)
        dialogBuilder.setView(dialogView)

        val cardNameEditText = dialogView.findViewById<EditText>(R.id.cardNameEditText)
        val cardContainer = dialogView.findViewById<FrameLayout>(R.id.cardContainer)

        dialogBuilder.setPositiveButton("Create") { _, _ ->
            val cardName = cardNameEditText.text.toString()
            if (cardName.isNotEmpty()) {
                // Create the card using the cardName
                val cardView = createCardView(cardName)
                cardContainer.addView(cardView)
                cardNames.add(cardName) // Add the cardName to your list

                // Optional: Show a toast message indicating that the card has been added
                Toast.makeText(this, "Card added: $cardName", Toast.LENGTH_SHORT).show()

                // Notify the adapter that the dataset has changed
                adapter.run { notifyDataSetChanged() }
            }
        }

        dialogBuilder.setNegativeButton("Cancel") { _, _ -> }
        dialogBuilder.create().show()
    }

    private fun createCardView(cardName: String): Button {
        val cardButton = Button(this)
        cardButton.text = cardName
        // Set any additional properties you want for the card button, e.g., layout params, click listeners, etc.
        return cardButton
    }

    class CardAdapter(private val cardNames: List<String>) :
        RecyclerView.Adapter<CardAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val cardButton = itemView.findViewById<Button>(R.id.button)// This line should be inside the ViewHolder
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.cardcontainer, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.cardButton.text = cardNames[position]
        }

        override fun getItemCount(): Int {
            return cardNames.size
        }
    }
}
package com.example.petproject_app_notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject_app_notepad.db.DatabaseManager
import com.example.petproject_app_notepad.db.RecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val databaseManager = DatabaseManager(this)
    private val adapter = RecyclerViewAdapter(databaseManager.readFromDatabase(), this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onClickAddNew()
        init()
    }

    private fun init() {
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        val swipeManager = swapItem()
        swipeManager.attachToRecyclerView(recycler)
        recycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        databaseManager.openDatabase()
        adapter.updateAdapter(databaseManager.readFromDatabase())
        Toast.makeText(
            this, "${databaseManager.readFromDatabase().size}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        databaseManager.closeDatabase()
        super.onDestroy()
    }

    private fun onClickAddNew() {
        val fabAddNew = findViewById<FloatingActionButton>(R.id.floatingActionButton_add_new)
        fabAddNew.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    private fun swapItem(): ItemTouchHelper {
        return ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.layoutPosition, databaseManager)
            }
        })
    }
}

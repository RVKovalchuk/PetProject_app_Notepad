package com.example.petproject_app_notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.petproject_app_notepad.db.DatabaseManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddActivity : AppCompatActivity() {
    private val requestedCode = 10
    var uriForSave = "empty"
    private val databaseManager = DatabaseManager(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        initButtons()

    }

    private fun initButtons() {
        val fabMore = findViewById<FloatingActionButton>(R.id.floatingActionButton_more)
        val fabSave = findViewById<FloatingActionButton>(R.id.floatingActionButton_save)
        val fabAddImage = findViewById<FloatingActionButton>(R.id.floatingActionButton_add_image)
        val fabShare = findViewById<FloatingActionButton>(R.id.floatingActionButton_share)
        val imageView = findViewById<ImageView>(R.id.activity_add_image_view)
        val titleView = findViewById<EditText>(R.id.activity_add_edit_text_title)
        val descView = findViewById<EditText>(R.id.activity_add_edit_text_description)

        onClickFabMore(fabMore, fabSave, fabAddImage, fabShare)
        onClickFabAddImage(fabAddImage, imageView)
        onClickFabSave(fabSave, titleView, descView)
        onClickFabShare(fabShare, titleView, descView)
    }

    private fun onClickFabMore(
        fabMore: FloatingActionButton, fabSave: FloatingActionButton,
        fabAddImage: FloatingActionButton, fabShare: FloatingActionButton
    ) {


        var upMoreOrDownMore = 0


        fabMore.setOnClickListener {
            if (upMoreOrDownMore == 0) {
                fabShare.isVisible = true
                fabSave.isVisible = true
                fabAddImage.isVisible = true
                fabMore.setImageResource(R.drawable.ic_fab_more_down)
                upMoreOrDownMore++
            } else {
                fabShare.isVisible = false
                fabSave.isVisible = false
                fabAddImage.isVisible = false
                fabMore.setImageResource(R.drawable.ic_fab_more_up)
                upMoreOrDownMore--
            }
        }
    }

    private fun onClickFabAddImage(fabAddImage: FloatingActionButton, imageView: ImageView) {
        var addImageOrDeleteImage = 0
        fabAddImage.setOnClickListener {
            if (addImageOrDeleteImage == 0) {
                imageView.isVisible = true
                fabAddImage.setImageResource(R.drawable.ic_fab_remove_image)
                intentImage()
                addImageOrDeleteImage++
            } else {
                uriForSave = "empty"
                imageView.setImageResource(R.drawable.shape_empty)
                imageView.visibility = View.GONE
                fabAddImage.setImageResource(R.drawable.ic_fab_add_image)
                addImageOrDeleteImage--
            }
        }
    }

    private fun onClickFabSave(
        fabSave: FloatingActionButton,
        titleView: EditText,
        descView: EditText
    ) {
        fabSave.setOnClickListener {
            databaseManager.insertToDatabase(
                titleView.text.toString(),
                descView.text.toString(),
                uriForSave
            )
            Toast.makeText(this, "Запись добавлена", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun onClickFabShare(
        fabShare: FloatingActionButton,
        titleView: EditText,
        descView: EditText
    ) {
        fabShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Делюсь заметкой: \n ${titleView.text} \n ${descView.text}"
            )
            intent.type = "text/plain"
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Нет приложений, чтобы поделиться.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun intentImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, requestedCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView = findViewById<ImageView>(R.id.activity_add_image_view)
        if (requestCode == requestedCode && resultCode == Activity.RESULT_OK) {
            imageView.setImageURI(data?.data)
            uriForSave = data?.data.toString()
        }
    }
}
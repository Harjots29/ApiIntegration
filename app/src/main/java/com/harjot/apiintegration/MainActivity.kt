package com.harjot.apiintegration

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.harjot.apiintegration.adapters.RecyclerAdapter
import com.harjot.apiintegration.databinding.ActivityMainBinding
import com.harjot.apiintegration.databinding.DialogLayoutBinding
import com.harjot.apiintegration.interfaces.RecyclerInterface
import com.harjot.apiintegration.models.RecyclerModel

class MainActivity : AppCompatActivity(),RecyclerInterface {
    lateinit var binding: ActivityMainBinding
    var arrayList = ArrayList<RecyclerModel>()
    var recyclerAdapter = RecyclerAdapter(arrayList,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = recyclerAdapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        arrayList.add(RecyclerModel(
            "Harjot",
            "Singh",
            "harjot@gmail.com"
        ))
        recyclerAdapter.notifyDataSetChanged()
        //Recycler CRUD
        //Api Classes:- ApiInterface, Constants, Repo, ViewModelClass, SealedClass, RetrofitClass...

    }

    override fun listClick(position: Int) {
        var intent = Intent(this@MainActivity,NextActivity::class.java)
        startActivity(intent)
    }

    override fun onEditClick(position: Int) {
        dialog(position)
    }

    override fun onDeleteClick(position: Int) {
        var alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.setTitle("Delete Item")
        alertDialog.setMessage("Do you want to delete the item?")
        alertDialog.setCancelable(false)
        alertDialog.setNegativeButton("No") { _, _ ->
            alertDialog.setCancelable(true)
        }
        alertDialog.setPositiveButton("Yes") { _, _ ->
            if (arrayList.size == 0){
                Toast.makeText(this@MainActivity, "List Is Empty", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(
                    this@MainActivity,
                    "The item is deleted",
                    Toast.LENGTH_SHORT
                ).show()
                arrayList.removeAt(position)
                recyclerAdapter.notifyDataSetChanged()
            }
        }
        alertDialog.show()
    }
    fun dialog(position: Int){
        var dialogBinding = DialogLayoutBinding.inflate(layoutInflater)
        var dialog = Dialog(this@MainActivity).apply {
            setContentView(dialogBinding.root)
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialogBinding.etEmail.setText(arrayList[position].email)
            dialogBinding.etFirstName.setText(arrayList[position].firstName)
            dialogBinding.etLastName.setText(arrayList[position].lastName)
            dialogBinding.btnUpdate.setOnClickListener {
                arrayList[position]= RecyclerModel(
                    dialogBinding.etFirstName.text.toString(),
                    dialogBinding.etLastName.text.toString(),
                    dialogBinding.etEmail.text.toString()
                )
                recyclerAdapter.notifyDataSetChanged()
                dismiss()
            }
            show()
        }
    }
}
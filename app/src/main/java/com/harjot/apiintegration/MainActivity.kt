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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.harjot.apiintegration.adapters.RecyclerAdapter
import com.harjot.apiintegration.databinding.ActivityMainBinding
import com.harjot.apiintegration.databinding.DialogLayoutBinding
import com.harjot.apiintegration.interfaces.RecyclerInterface
import com.harjot.apiintegration.models.ResponseModel

class MainActivity : AppCompatActivity(),RecyclerInterface {
    lateinit var binding: ActivityMainBinding
    var arrayList = ArrayList<ResponseModel.Data>()
    var recyclerAdapter = RecyclerAdapter(arrayList,this)
    lateinit var vmClass:VMclass
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
        initViews()
        vmClass.apiHit()

        arrayList.add(ResponseModel.Data(
            first_name = "Harjot",
            last_name = "Singh",
            email = "harjot@gmail.com"
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
            dialogBinding.etFirstName.setText(arrayList[position].first_name)
            dialogBinding.etLastName.setText(arrayList[position].last_name)
            dialogBinding.btnUpdate.setOnClickListener {
                arrayList[position]= ResponseModel.Data(
                    first_name = dialogBinding.etFirstName.text.toString(),
                    last_name = dialogBinding.etLastName.text.toString(),
                    email = dialogBinding.etEmail.text.toString()
                )
                recyclerAdapter.notifyDataSetChanged()
                dismiss()
            }
            show()
        }
    }
    private fun initViews(){
        vmClass = ViewModelProvider(this)[VMclass::class.java]
        vmClass.getRes.observe(this){
            when(it){
                is SealedClass.Success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    arrayList.clear()
                    it.data?.data?.forEach{
                        arrayList.add(it)
                    }
                    recyclerAdapter.notifyDataSetChanged()
                }
                is SealedClass.Error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
                is SealedClass.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
                null->{
                    Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
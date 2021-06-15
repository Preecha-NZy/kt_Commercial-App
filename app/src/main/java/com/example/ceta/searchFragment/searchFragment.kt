package com.example.ceta.searchFragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ceta.R
import com.example.ceta.adapter.CarlistRecycleAdapter
import com.example.ceta.databinding.SearchFragmentBinding
import com.example.ceta.model.carListData
import com.example.ceta.query.querySearch


class searchFragment : Fragment(), CarlistRecycleAdapter.OnItemClickListener {
    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewModel: SearchViewModel

    companion object {
        fun newInstance() = searchFragment()
        fun listener() = this
    }


    @SuppressLint("NewApi", "WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.search_fragment,
            container,
            false
        )
        val args by navArgs<searchFragmentArgs>()
        val Search_list = querySearch()
        val editTextBox = binding.searchEdit

//--------------------------- use for finish search input ----------------------------------------------
        editTextBox.setOnEditorActionListener { _, actionId, _ ->
            val info: MutableList<carListData> = ArrayList()
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editTextBox.clearFocus()
                info.clear()
                if (editTextBox.text.toString() == "") {
                    Search_list.queryTopBangkok()
                    var i = 0
                    while (i < Search_list.MImage.size) {
                        info.add(carListData(Search_list.name[i],
                            Search_list.price[i],
                            Search_list.MImage[i],
                            Search_list.city[i]))
                        i++
                    }
                } else {
                    Search_list.queryCarInfo(editTextBox.text.toString())
                    var i = 0
                    while (i < Search_list.MImage.size) {
                        info.add(carListData(Search_list.name[i],
                            Search_list.price[i],
                            Search_list.MImage[i],
                            Search_list.city[i]))
                        i++
                    }
                }
                setCarlistRecycle(info)
                hideKeyboard()
                true
            } else {
                false
            }
        }

//-----------------------          default page have sample or not          -----------------------------------------
        if (args.search == "" && args.hide == "no") {
            editTextBox.onEditorAction(EditorInfo.IME_ACTION_DONE)
            editTextBox.requestFocus()
            context?.let { showKeyboard(it) }
        } else if (args.search == "" && args.hide == "yes") {
            editTextBox.setText(args.search)
            editTextBox.onEditorAction(EditorInfo.IME_ACTION_DONE)
        } else if (args.search !== "" && args.hide == "yes") {
            editTextBox.setText(args.search)
            editTextBox.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }

//------------------------------ use for back to home page -----------------------------------//
        binding.backbotton.setOnClickListener {
            hideKeyboard()
            editTextBox.onEditorAction(EditorInfo.IME_ACTION_DONE)
            findNavController().navigate(R.id.searchTohome)
        }

//---------------------------- use for end edit -----------------------------------------------//
        binding.button.setOnClickListener {
            hideKeyboard()
            editTextBox.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setCarlistRecycle(category: MutableList<carListData>) {
        binding.searchRecycler?.setLayoutManager(GridLayoutManager(activity, 2))
        binding.searchRecycler?.adapter = CarlistRecycleAdapter(category, this)
    }

    override fun clickToDetail(name: String, city: String) {
        hideKeyboard()
        val searchItem: String? = binding.searchEdit.text.toString()
        val s = searchFragmentDirections.searchTodetail("$name", "$city", "$searchItem")
        findNavController(this).navigate(s)

    }

    fun showKeyboard(context: Context) {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun findNavController(searchFragment: searchFragment): NavController {
        return searchFragment.findNavController()
    }

}


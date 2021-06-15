package com.example.ceta.reviewFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ceta.R
import com.example.ceta.databinding.ReviewFragmentBinding
import com.example.ceta.query.insertReview
import com.example.ceta.query.queryReviewImage

class reviewFragment : Fragment() {

    companion object {
        fun newInstance() = reviewFragment()
    }

    private lateinit var binding: ReviewFragmentBinding
    private lateinit var viewModel: ReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.review_fragment,
            container,
            false
        )
        val args by navArgs<reviewFragmentArgs>()
        val image = queryReviewImage(args.name.toString())
        binding.imageView.setImageBitmap(image.image)
        binding.score.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.score.rating = rating
            Log.i("apptest", fromUser.toString())
        }
        binding.backbotton.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        binding.name.setText(args.name)
        binding.city.setText(args.city)
        binding.date.setText("Qty:" + args.date.toString() + " day")
        val insert = insertReview()
        val count = insert.count
        binding.text.setOnClickListener {
        }
        binding.submit.setOnClickListener {
            val email = getEmail()
            val id: String = "R" + "%04d".format(count!! + 1)
            val name: String = args.name
            val text: String = binding.text.text.toString()
            val score: Int = binding.score.rating.toInt()
            val city: String = binding.city.text.toString()
            insert.insert_review(id, email, name, city, text, score)
            findNavController().navigate(R.id.profileFragment)
        }

        return binding.root
    }

    private fun getEmail(): String? {
        val sharedPref = requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        return sharedPref.getString("Email", "")
    }

}
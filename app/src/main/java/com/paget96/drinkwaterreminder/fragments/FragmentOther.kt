package com.paget96.drinkwaterreminder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.activities.MainActivity
import com.paget96.drinkwaterreminder.databinding.FragmentOtherBinding
import com.paget96.drinkwaterreminder.utils.SafeAttachFragment

class FragmentOther : SafeAttachFragment() {

    // Variables
    private var binding: FragmentOtherBinding? = null

    private fun viewFunction() {
        binding?.apply {
            recommended.setOnClickListener {
                /*(attached as MainActivity).replaceFragment(
                    FragmentRecommended::class.java,
                    addToStack = true,
                    transitionAnimations = true,
                    arguments = null,
                    "fragment_recommended"
                )*/
            }

            support.setOnClickListener {
                /*if (netStatUtils!!.isConnectedToInternet(attached!!) && (attached as MainActivity).billingClient!!.isReady)
                    (attached as MainActivity).replaceFragment(
                        FragmentSupport::class.java,
                        addToStack = true,
                        transitionAnimations = true,
                        arguments = null,
                        "fragment_support"
                    )*/
            }

            help.setOnClickListener { v: View? ->
                /*(attached as MainActivity).replaceFragment(
                    FragmentHelp::class.java,
                    true,
                    true,
                    null,
                    ""
                )*/
            }

            settings.setOnClickListener { v: View? ->
                (attached as MainActivity).replaceFragment(
                    FragmentSettings::class.java,
                    true,
                    true,
                    null,
                    ""
                )
            }

            about.setOnClickListener { v: View? ->
                (attached as MainActivity).replaceFragment(
                    FragmentAbout::class.java,
                    true,
                    true,
                    null,
                    ""
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        (attached as MainActivity).collapsingToolbarLayout?.title = "Other"

        binding = FragmentOtherBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewFunction()
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//
//        menu.setGroupVisible(R.id.advertising, false)
//        menu.findItem(R.id.action_help).isVisible = false
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
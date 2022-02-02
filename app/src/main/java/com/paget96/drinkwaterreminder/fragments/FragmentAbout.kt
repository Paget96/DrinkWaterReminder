package com.paget96.drinkwaterreminder.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import com.paget96.drinkwaterreminder.BuildConfig
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.activities.MainActivity
import com.paget96.drinkwaterreminder.databinding.FragmentAboutBinding
import com.paget96.drinkwaterreminder.utils.SafeAttachFragment
import com.paget96.drinkwaterreminder.utils.UiUtils.createExplanationDialog
import com.paget96.drinkwaterreminder.utils.UiUtils.openLink

class FragmentAbout: SafeAttachFragment() {

    // Variables
    private var binding: FragmentAboutBinding? = null
    private var sendIntent: Intent? = null
    private val versionName: Unit
        get() {
            binding?.versionName?.text = BuildConfig.VERSION_NAME
        }

    private fun viewFunction() {
        binding?.apply {
            inviteFriends.setOnClickListener { v: View? ->
                sendIntent = Intent()
                sendIntent!!.action = Intent.ACTION_SEND
                sendIntent!!.putExtra(
                    Intent.EXTRA_TEXT, getString(R.string.invite_text) + " " +
                            "https://play.google.com/store/apps/details?id=com.paget96.netspeedindicator"
                )
                sendIntent!!.type = "text/plain"
                startActivity(sendIntent)
            }

            betaVersion.setOnClickListener { v: View? ->
                openLink(
                    attached!!,
                    "https://play.google.com/apps/testing/com.paget96.netspeedindicator",
                    true
                )
            }
            changelog.setOnClickListener { v: View? ->
                createExplanationDialog(
                    attached,
                    R.string.changelog,
                    R.string.changelog_list
                )
            }
            translation.setOnClickListener { v: View? ->
                openLink(
                    attached!!,
                    "https://localazy.com/p/net-speed-indicator", true
                )
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (attached as MainActivity).collapsingToolbarLayout?.title = "About"

        binding = FragmentAboutBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewFunction()
        versionName
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.setGroupVisible(R.id.advertising, false)
        menu.findItem(R.id.action_help).isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
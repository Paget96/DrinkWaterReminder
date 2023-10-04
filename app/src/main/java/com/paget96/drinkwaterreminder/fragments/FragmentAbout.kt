package com.paget96.drinkwaterreminder.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.paget96.drinkwaterreminder.BuildConfig
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.activities.MainActivity
import com.paget96.drinkwaterreminder.databinding.FragmentAboutBinding
import com.paget96.drinkwaterreminder.utils.UiUtils.createExplanationDialog
import com.paget96.drinkwaterreminder.utils.UiUtils.openLink

class FragmentAbout : Fragment(R.layout.fragment_about) {

    // Variables
    private var sendIntent: Intent? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAboutBinding.bind(view)

        (requireActivity() as MainActivity).collapsingToolbarLayout?.title = "About"

        with(binding) {
            versionName.text = BuildConfig.VERSION_NAME

            inviteFriends.setOnClickListener { _ ->
                sendIntent = Intent()
                sendIntent!!.action = Intent.ACTION_SEND
                sendIntent!!.putExtra(
                    Intent.EXTRA_TEXT, getString(R.string.invite_text) + " " +
                            "https://play.google.com/store/apps/details?id=com.paget96.netspeedindicator"
                )
                sendIntent!!.type = "text/plain"
                startActivity(sendIntent!!)
            }

            betaVersion.setOnClickListener { _ ->
                openLink(
                    requireContext(),
                    "https://play.google.com/apps/testing/com.paget96.netspeedindicator",
                    true
                )
            }

            changelog.setOnClickListener { _ ->
                createExplanationDialog(
                    requireContext(),
                    R.string.changelog,
                    R.string.changelog_list
                )
            }

            translation.setOnClickListener { _ ->
                openLink(
                    requireContext(),
                    "https://localazy.com/p/net-speed-indicator",
                    true
                )
            }
        }
    }
}
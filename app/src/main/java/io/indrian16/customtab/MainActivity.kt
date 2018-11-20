package io.indrian16.customtab

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {

        const val MY_GITHUB_LINK = "https://github.com/indrian16"
    }

    private val mCustomFallback = CustomTabActivityHelper.CustomTabFallback { activity, uri ->

        try {

            val intent = Intent(Intent.ACTION_VIEW, uri)
            activity.startActivity(intent)

        } catch (e: ActivityNotFoundException) {

            e.printStackTrace()
            Toast.makeText(activity, "Not found", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_my_github.setOnClickListener{ openMyGithub() }
    }


    private fun String.toUri(): Uri {

        return Uri.parse(this)
    }

    @SuppressLint("PrivateResource")
    private fun openMyGithub() {

        val colorBlack = resources.getColor(R.color.colorBlack)

        val customTabIntent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(colorBlack)
                .setStartAnimations(this, R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
                .setExitAnimations(this, R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top)
                .build()

        val myGithubURI = MY_GITHUB_LINK.toUri()

        CustomTabActivityHelper.openCustomTab(this, customTabIntent, myGithubURI, mCustomFallback)
    }
}

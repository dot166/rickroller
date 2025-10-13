package com.j.rickroller

import android.content.Context
import android.content.Intent
import io.github.dot166.jlib.LIBTestActivity
import io.github.dot166.jlib.R
import io.github.dot166.jlib.app.jAboutActivity

class About : jAboutActivity() {
    override fun versionIntent(context: Context): Intent {
        return Intent(context, LIBTestActivity::class.java)
    }

    override fun links(): MutableList<Link?> {
        return ArrayList(
            listOf<Link?>(
                Link(
                    R.drawable.ic_github,
                    R.string.github,
                    "https://github.com/dot166/rickroller"
                )
            )
        )
    }

    override fun product(): MutableList<Contributor?> {
        return object : ArrayList<Contributor?>() {
            init {
                add(
                    Contributor(
                        "._______166",
                        Role.LeadDev,
                        "https://avatars.githubusercontent.com/u/62702353",
                        "https://github.com/dot166"
                    )
                )
            }
        }
    }

    enum class Role(val descriptionResId: Int) : Roles {
        LeadDev(R.string.maintainer);

        override fun descriptionResId(): Int {
            return this.descriptionResId
        }
    }
}

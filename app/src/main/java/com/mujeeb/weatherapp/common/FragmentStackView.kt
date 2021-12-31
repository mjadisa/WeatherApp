package com.mujeeb.weatherapp.common

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.util.AttributeSet
import android.widget.FrameLayout
import timber.log.Timber

class FragmentStackView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs), BackPressAware {
    private enum class Op {
        FORWARD, BACKWARD, RESET
    }

    var defaultFrag: (() -> Fragment)? = null
    var fragmentManager: FragmentManager? = null
    var tag = ""

    var activeFragment: Fragment? = null
    private val fragments = mutableListOf<Fragment>()

    fun push(fragment: Fragment) {
        fragments.add(fragment)
        updateUi(Op.FORWARD)
    }

    override fun onBackPressed(): Boolean {
        activeFragment?.let { activeFragment ->
            return (activeFragment as? BackPressAware)?.onBackPressed() ?: false
        } ?: return false
    }

    fun pop() {
        fragments.removeAt(fragments.size - 1)
        updateUi(Op.BACKWARD)
    }

    fun canPop() = fragments.size > 1

    fun reset() {
        if (defaultFrag == null) throw IllegalStateException("$tag - Default frag method has not be set")
        updateUi(Op.RESET)
    }

    private fun updateUi(op: Op) {
        if (fragmentManager == null) throw IllegalStateException("$tag - Fragment manager has not be set")
        val transaction = fragmentManager!!.beginTransaction()
        Timber.i("$tag - \n------------------------------------------------------\n")
        Timber.i("$tag - Transaction started for $op")
        if (activeFragment != null) {
            Timber.i("$tag - Detaching active fragment ${activeFragment!!.javaClass.simpleName}")
            transaction.detach(activeFragment!!)
        } else {
            Timber.i("$tag - id 'kotlin-android-extensions'")
        }
        when (op) {
            Op.FORWARD -> {
                activeFragment = fragments.last()
                Timber.i("$tag - Active fragment set to ${activeFragment!!.javaClass.simpleName}")
                transaction.add(id, activeFragment!!)
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                Timber.i("$tag - Added")
            }
            Op.BACKWARD -> {
                activeFragment?.let { transaction.remove(it) }
                Timber.i("$tag - Removing ${activeFragment!!.javaClass.simpleName}")
                activeFragment = fragments.last()
                Timber.i("$tag - Active fragment set to ${activeFragment!!.javaClass.simpleName}")
                transaction.attach(activeFragment!!)
                Timber.i("$tag - Attached")
            }
            Op.RESET -> {
                fragments.clear()
                activeFragment = defaultFrag!!.invoke()
                fragments.add(activeFragment!!)
                Timber.i("$tag - Active fragment set to ${activeFragment!!.javaClass.simpleName}")
                transaction.add(id, activeFragment!!)
                Timber.i("$tag - Added")
            }
        }
        Timber.i("$tag - Transaction finished, ${fragments.size} fragments stacked")
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

    fun findFragmentInStack(fragmentName: String) = fragments.find { fragment ->
        fragment.javaClass.name == fragmentName
    }
}

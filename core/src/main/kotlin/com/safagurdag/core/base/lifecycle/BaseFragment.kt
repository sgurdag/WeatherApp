package com.safagurdag.core.base.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.safagurdag.core.R
import com.safagurdag.repository.interactors.base.Reason

/**
 * Parent of all fragments.
 *
 * Purpose of [BaseFragment] is to simplify view creation and provide easy access to fragment's
 * [navController] and [binding].
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    //region Abstractions

    @LayoutRes
    abstract fun getLayoutId(): Int
    //endregion

    //region Properties

    protected lateinit var binding: T
    //endregion

    //region Functions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    /**
     * SnackBar is preffered for User interaction instead of Dialogs
     * to prevent losing more time during development :/
     */

    protected fun showSnackbarWithAction(reason: Reason, block: () -> Unit) {
        Snackbar.make(
            binding.root,
            resources.getString(reason.messageRes),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(R.string.retry) {
            block()
        }.show()
    }

    protected fun showSnackbarWithMessageAndAction(
        message: String,
        actionButton: String,
        onClick: () -> Unit
    ) {

        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_INDEFINITE
        ).setAction(actionButton) {
            onClick()
        }
            .show()
    }
    //endregion
}

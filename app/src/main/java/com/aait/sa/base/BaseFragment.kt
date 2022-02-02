package com.aait.sa.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.aait.sa.base.util.NetworkExtensionsActions
import com.aait.sa.cycles.auth_cycle.AuthActivity
import com.aait.utils.common.*
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment(),
    NetworkExtensionsActions {

    @Inject
    lateinit var progressUtil: ProgressUtil

    @Inject
    lateinit var networkHelper: NetworkHelper

    abstract val viewModel: BaseViewModel

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = inflate.invoke(inflater, container, false)
        }

        onCreateView()

        return binding.root
    }

    open fun onCreateView() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onLoad(showLoading: Boolean) {
        progressUtil.statusProgress(showLoading)
    }

    override fun onCommonError(exceptionMsgId: Int) {
        requireContext().showToast(getString(exceptionMsgId))
    }

    override fun onShowSuccessToast(msg: String?) {
        requireContext().showToast(msg, ToastType.SUCCESS)
    }

    override fun onFail(msg: String?) {
        requireContext().showToast(msg)
    }

    override fun authorizationNeedActive(msg: String) {
        requireContext().showToast(msg, ToastType.WARNING)
    }

    override fun authorizationFail() {
        requireContext().openAccountDeletedDialog {
            onLogout()
        }
    }

    private fun onLogout() {
        lifecycleScope.launchWhenCreated {
            viewModel.preferenceRepository.onLogout()
            openAuthActivity()
        }
    }

    private fun openAuthActivity() {
        Intent(requireActivity(), AuthActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK).also {
                startActivity(it)
            }
    }

}
package it.jack.lib_wrapper_mvvm.base.view

import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import it.jack.lib_wrapper_mvvm.base.viewmodel.BaseWrapperViewModel
import it.jack.lib_wrapper_mvvm.interfa.IBaseView
import it.jack.lib_wrapper_mvvm.uistate.DialogState

/**
 * @创建者 Jack
 * @创建时间 2022/8/27
 * @描述
 */
abstract class BaseMvvmActivity<VB : ViewDataBinding, VM : BaseWrapperViewModel>(override var block: (LayoutInflater) -> VB) :
    BaseWrapperActivity<VB>(block), IBaseView {

    protected abstract val mViewModel: VM

    override fun perpareWork() {
        initViewModel()
        super.perpareWork()
        observeViewModel()
    }

    private fun initViewModel() {
        //让ViewModel拥有View的生命周期感应:即mViewModel成为观察者
        lifecycle.addObserver(mViewModel);

        mViewModel.showDialogState.observe(this) {
            when (it) {
                DialogState.OnLoading -> visibleDialog()
                DialogState.OnHide -> hideDialog()
            }
        }
    }

    open fun observeViewModel() {}

}
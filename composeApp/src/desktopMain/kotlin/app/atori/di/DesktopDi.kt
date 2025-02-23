package app.atori.di

import app.atori.ui.viewmodels.MainWindowViewModel
import app.atori.ui.viewmodels.SidePanelViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val desktopAppModule = module {
    // 依赖于主模块
    includes(appModule)

    // 提供 SidePanelViewModel
    viewModel { SidePanelViewModel() }

    // 提供 MainWindowViewModel
    viewModel { MainWindowViewModel() }
}
package app.atori.di

import app.atori.data.states.DemoState
import org.koin.dsl.module
import app.atori.utils.DataUtils
import org.koin.core.module.dsl.viewModel

val appModule = module {
    // 提供 App数据库
    single { DataUtils.getAppDatabase() }

    // 提供 DemoViewModel
    // viewModel { DemoViewModel() }

    // 提供 DemoState
    single { DemoState() }
}
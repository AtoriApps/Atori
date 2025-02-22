package app.atori.di

import org.koin.dsl.module

val androidAppModule = module {
    // 依赖于主模块
    includes(appModule)
}
package com.example.u2020.data

import com.example.u2020.data.api.ProductionApiModule
import dagger.Module

@Module(includes = [DataModule::class, ProductionApiModule::class])
class ProductionDataModule {

}
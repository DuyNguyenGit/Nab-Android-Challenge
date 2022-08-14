package com.p109.nab_android_challenge.common.mapper

import com.p109.nab_android_challenge.common.basemodels.DataModel
import com.p109.nab_android_challenge.common.basemodels.DomainDataModel

interface ModelMapper<R : DataModel, T : DomainDataModel> {
    fun mapperToViewDataModel(dataModel: R): T

    fun mapperToDataModel(domainDataModel: DomainDataModel): DataModel {
        TODO("maybe not implement")
    }
}
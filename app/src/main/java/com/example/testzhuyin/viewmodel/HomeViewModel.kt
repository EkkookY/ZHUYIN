package com.example.testzhuyin.viewmodel

import com.amap.api.maps.model.LatLng
import com.melody.map.myapplication.base.BaseViewModel
import com.melody.map.myapplication.contract.HomeContract
import com.melody.map.myapplication.repo.HomeRepo
import kotlinx.coroutines.Dispatchers

class HomeViewModel : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {
    override fun createInitialState(): HomeContract.State {
        return HomeContract.State(
            isLoading = false,
            uiSettings = HomeRepo.initMapUISettings(),
            mapProperties = HomeRepo.initMapProperties(),
            ethnicityDataList = emptyList()
        )
    }

    override fun handleEvents(event: HomeContract.Event) {
        when(event) {
            is HomeContract.Event.OpenDetailPage -> {
                setEffect {
                    HomeContract.Effect.Navigation.OpenDetailPage(event.latLng,event.iconName)
                }
            }
        }
    }

    init {
        asyncLaunch(Dispatchers.IO) {
            // 这里模拟下，从本地或者网络【Dispatchers.IO】读取数据
            val dataList = HomeRepo.initEthnicityDataList()
            setState { copy(ethnicityDataList = dataList) }
        }
    }

    fun openDetailPage(latLng: LatLng, iconName: String) {
        setEvent(HomeContract.Event.OpenDetailPage(latLng,iconName))
    }


}
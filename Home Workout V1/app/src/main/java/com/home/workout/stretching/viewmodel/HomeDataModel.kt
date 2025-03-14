package com.home.workout.stretching.viewmodel

class HomeDataModel()  {


    var facilityCode: String? = null


    class CellInfoParam(){
        constructor(HeadCountSessionConfigId:Int) : this() {
            this.HeadCountSessionConfigId = HeadCountSessionConfigId
        }
        var HeadCountSessionConfigId:Int? = null
    }
}
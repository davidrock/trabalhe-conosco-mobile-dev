package com.picpay.david.davidrockpicpay.features.sendMoney

import org.junit.Test

import org.junit.Assert.*

class SendMoneyPresenterTest {

    @Test
    fun convertMoneyValueValidNumberShouldSucceed() {
        //Arrange
        var sendMoney = SendMoneyPresenter()
        var value = "R$5,00"

        //Act
        var valor = sendMoney.convertMoneyValue(value)

        //Assert
        assertNotNull(valor)

    }

    @Test
    fun convertMoneyValueInvalidNumberShouldNotSucceed() {
        //Arrange
        var sendMoney = SendMoneyPresenter()
        var value = "XA2e"

        //Act
        var valor = sendMoney.convertMoneyValue(value)

        //Assert
        assertNull(valor)

    }

}
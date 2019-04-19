package com.picpay.david.davidrockpicpay.features.sendMoney

import com.picpay.david.davidrockpicpay.DavidRockPicPayApplication
import com.picpay.david.davidrockpicpay.DavidRockPicPayApplication.Companion.boxStore
import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.features.base.BasePresenter
import com.picpay.david.davidrockpicpay.models.TransactionModel
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import com.picpay.david.davidrockpicpay.models.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_send_money.*
import java.text.DecimalFormat
import java.text.ParseException


class SendMoneyPresenter : BasePresenter<SendMoneyMvpView>() {


    fun getCreditCard() {
        //Get CreditCard from ObjectBox Database
        val box: Box<CreditCard> = boxStore.boxFor()
        mvpView?.updateCreditCardSection()
    }

    fun convertMoneyValue(value: String): Double? {
        val nf = DecimalFormat("#,###.00")

        return try {
            var valor = nf.parse(value.removePrefix("R$").removePrefix("$")).toDouble()
            valor
        } catch (ex: ParseException) {
            mvpView?.showError("Valor inválido")
            null
        }


    }

    fun sendMoney(model: TransactionModel) {
        checkViewAttached()

        model.CardNumber = model.CardNumber?.replace(" ", "")
        val call = DavidRockPicPayApplication.api.SendMoney(model)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mvpView?.hideLoading()
                    if (it != null && it.Transaction!!.Success) {
                        mvpView?.showReceipt(it)
                    } else {
                        mvpView?.showError(it.Transaction!!.Status)
                        mvpView?.hideLoading()
                    }

                }, {
                    mvpView?.showError(it.message)
                    mvpView?.hideLoading()
                })
    }
}
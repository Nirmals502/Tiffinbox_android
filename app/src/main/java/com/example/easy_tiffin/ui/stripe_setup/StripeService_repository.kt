package com.example.easy_tiffin.ui.stripe_setup

import android.content.Context
import com.easy_tiffin.R
import com.stripe.android.Stripe
import com.stripe.android.model.BankAccountTokenParams
import com.stripe.android.model.Token
import com.stripe.android.ApiResultCallback
import javax.inject.Inject



class StripeService_repository @Inject constructor(context: Context) {
    private val stripe: Stripe = Stripe(context, context.getString(R.string.stripe_publishable_key))

    suspend fun createBankAccountToken(
        params: BankAccountTokenParams,
        stripeAccountId: String? = null,
        idempotencyKey: String? = null,
        callback: ApiResultCallback<Token>
    ) {
        stripe.createBankAccountToken(params, idempotencyKey,stripeAccountId,callback)
    }
}

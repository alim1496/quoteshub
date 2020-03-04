package com.appwiz.quoteshub.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.utils.CommonUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class QuoteActions(val quote: Quote, val authorName: String): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.quote_actions_sheet, container, false)
        val favTxt: TextView = view.findViewById(R.id.action_add_fav)
        val copyTxt: TextView = view.findViewById(R.id.action_copy_quote)
        val shareTxt: TextView = view.findViewById(R.id.action_share_quote)

        favTxt.setOnClickListener {
            context?.let { it1 -> CommonUtils().favQuote(it1, quote, authorName) }
            dismiss()
        }

        copyTxt.setOnClickListener {
            context?.let { it1 -> CommonUtils().copyQuote(it1, quote, authorName) }
            dismiss()
        }

        shareTxt.setOnClickListener {
            context?.let { it1 -> CommonUtils().shareQuote(it1, quote, authorName) }
            dismiss()
        }

        return view
    }
}
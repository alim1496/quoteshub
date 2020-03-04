package com.appwiz.quoteshub.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.room.entity.FavEntity
import com.appwiz.quoteshub.utils.CommonUtils
import com.appwiz.quoteshub.viewmodels.FavoritesVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RemoveQuote(var favId: Int, var viewModel: FavoritesVM): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.remove_favorite_sheet, container, false)
        val rmvTxt: TextView = view.findViewById(R.id.tv_rmv_quote)
        rmvTxt.setOnClickListener(View.OnClickListener {
            viewModel.removeFavorite(favId)
            dismiss()
        })
        return view
    }
}
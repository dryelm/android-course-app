package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.callbacks.InfoCallback

class InfoFragment : Fragment() {
    var callback: InfoCallback? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.info_text_view)
        textView.text = getString(R.string.about_app_text).trimIndent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback?.onDestroy()
    }

    companion object {
        fun newInstance() = InfoFragment()
    }
}
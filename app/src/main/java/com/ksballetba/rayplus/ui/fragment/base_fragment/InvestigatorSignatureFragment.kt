package com.ksballetba.rayplus.ui.fragment.base_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.util.showDatePickerDialog
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_investigator_signature.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class InvestigatorSignatureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_investigator_signature, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        cl_investigator_signature.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("研究者签字","请输入研究者签字"){
                tv_investigator_signature.text = it
            }.show()
        }
        cl_investigator_signature_date.setOnClickListener {
            showDatePickerDialog(tv_investigator_signature_date,parentFragmentManager)
        }
    }
}

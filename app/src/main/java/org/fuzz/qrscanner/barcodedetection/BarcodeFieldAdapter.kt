/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fuzz.qrscanner.barcodedetection

import android.text.SpannableString
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.fuzz.qrscanner.R
import org.fuzz.qrscanner.barcodedetection.BarcodeFieldAdapter.BarcodeFieldViewHolder

/** Presents a list of field info in the detected barcode.  */
internal class BarcodeFieldAdapter(private val barcodeFieldList: List<BarcodeField>, private val clickListener: (url: String) -> Any) :
    RecyclerView.Adapter<BarcodeFieldViewHolder>() {

    internal class BarcodeFieldViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val labelView: TextView = view.findViewById(R.id.barcode_field_label)
        private val valueView: TextView = view.findViewById(R.id.barcode_field_value)

        fun bindBarcodeField(barcodeField: BarcodeField) {
            labelView.text = barcodeField.label
            val urlSpan = URLSpan(barcodeField.value)
            val s = SpannableString(barcodeField.value)
            s.setSpan(urlSpan, 0, s.length, 0)
            valueView.text = s
        }

        companion object {
            fun create(parent: ViewGroup): BarcodeFieldViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.barcode_field, parent, false)
                return BarcodeFieldViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarcodeFieldViewHolder {
        return BarcodeFieldViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BarcodeFieldViewHolder, position: Int) {
        val url = barcodeFieldList[position].value
        holder.bindBarcodeField(barcodeFieldList[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(url)
        }
    }

    override fun getItemCount(): Int =
        barcodeFieldList.size
}

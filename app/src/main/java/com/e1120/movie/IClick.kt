package com.e1120.movie

import java.text.FieldPosition

interface IClick {
    interface OnClickMovie {
        fun click(position: Int)
    }

    interface OnClickTrailer {
        fun clickTrailer(position: Int)
    }
}